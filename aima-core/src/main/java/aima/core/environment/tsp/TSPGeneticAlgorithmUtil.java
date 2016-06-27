package aima.core.environment.tsp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import aima.core.search.framework.GoalTest;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.Individual;

public class TSPGeneticAlgorithmUtil {

	private static FitnessFunction<City> _fitnessFunction;
	private static GoalTest _goalTest;

	private static Random random = new Random();

	public static FitnessFunction<City> getFitnessFunction() {
		if (_fitnessFunction == null)
			_fitnessFunction = new TSPFitnessFunction();

		return _fitnessFunction;
	}

	public static GoalTest getGoalTest() {
		if (_goalTest == null)
			_goalTest = new TSPGenAlgoGoalTest();

		return _goalTest;
	}

	/**
	 * Generates a random individual for TSP with the given cities
	 * 
	 * @param cities The list of the cities the individual must have in its representation
	 * @return A new random individual
	 */
	public static Individual<City> generateRandomIndividual(List<City> cities) {

		List<City> copyCities = new ArrayList<>();
		copyCities.addAll(cities);

		// Shuffle all cities at random
		for (int i = 0; i < copyCities.size(); i++) {
			int j = random.nextInt(copyCities.size());
			City temp = copyCities.get(i);
			copyCities.set(i, copyCities.get(j));
			copyCities.set(j, temp);
		}

		return new Individual<City>(copyCities);
	}

	public static class TSPFitnessFunction implements FitnessFunction<City> {

		@Override
		public double getValue(Individual<City> individual) {

			double totalCost = 0;

			List<City> representation = individual.getRepresentation();

			if (TSPGeneticAlgorithmUtil.areAnyRepeatedCities(representation))
				return 0;

			int maxCost = findMaxCost(representation);

			for (int i = 0; i < representation.size() - 1; i++)
				totalCost += getCostForFitness(representation.get(i), representation.get(i + 1), maxCost);

			totalCost += getCostForFitness(representation.get(representation.size() - 1), representation.get(0),
					maxCost);

			return 1 / totalCost;
		}

		/**
		 * Finds the maximum cost existing in the passed list
		 * 
		 * @param cities List of cities where we want to find the maximum cost
		 * @return The maximum cost. If there's no maximum cost it returns null
		 */
		private Integer findMaxCost(List<City> cities) {
			Integer maxCost = null;

			for (City from : cities) {
				for (City to : cities) {

					Integer cost = from.getCost(to);
					if (cost != null && (maxCost == null || cost > maxCost))
						maxCost = cost;
				}
			}

			return maxCost;
		}

		/**
		 * Returns the cost of going from the first city to the second one.
		 * If you can't travel between these cities it returns the maxCost 
		 * passed, for the sake of calculating a fitness.
		 * 
		 * @param from The city we want to travel from.
		 * @param to The city we want to travel to.
		 * @param maxCost The cost that will be returned in case you can't
		 * travel from "from" to "to".
		 * @return The cost from going from "from" to "to" or maxCost in case
		 * you can't travel between those cities.
		 */
		private int getCostForFitness(City from, City to, int maxCost) {
			Integer cost = from.getCost(to);

			if (cost == null)
				return maxCost;

			return cost;
		}
	}

	public static class TSPGenAlgoGoalTest implements GoalTest {

		@SuppressWarnings("unchecked")
		@Override
		public boolean isGoalState(Object state) {

			List<City> representation = ((Individual<City>) state).getRepresentation();

			if (TSPGeneticAlgorithmUtil.areAnyRepeatedCities(representation))
				return false;

			for (int i = 0; i < representation.size() - 1; i++) {
				if (representation.get(i).getCost(representation.get(i + 1)) == null)
					return false;
			}

			if (representation.get(representation.size() - 1).getCost(representation.get(0)) == null)
				return false;

			return true;
		}
	}

	/**
	 * Checks if there are any repeated cities in the list.
	 * 
	 * @param cities List of cities where we want to check if there are any
	 * repeated one.
	 * @return True in case there are any repeated cities; false if there aren't.
	 */
	public static boolean areAnyRepeatedCities(List<City> cities) {

		Set<City> middleCities = new HashSet<>();

		for (int i = 0; i < cities.size(); i++) {
			if (!middleCities.add(cities.get(i)))
				return true;
		}

		return false;
	}

}
