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
		if(_fitnessFunction == null)
			_fitnessFunction = new TSPFitnessFunction();
			
		return _fitnessFunction;
	}
	
	public static GoalTest getGoalTest() {
		if(_goalTest == null)
			_goalTest = new TSPGenAlgoGoalTest();
		
		return _goalTest;
	}
	
	public static Individual<City> generateRandomIndividual(List<City> cities) {
		
		List<City> copyCities = new ArrayList<>();
		copyCities.addAll(cities);
		
		List<City> individualRepresentation = new ArrayList<>();
		
		individualRepresentation.add(copyCities.get(0));
		
		copyCities.remove(0);
		
		while(!copyCities.isEmpty()) {
			City city = copyCities.get(random.nextInt(copyCities.size()));
			individualRepresentation.add(city);
			copyCities.remove(city);
		}

		Individual<City> individual = new Individual<>(individualRepresentation);
		
		return individual;
	}
	
	public static class TSPFitnessFunction implements FitnessFunction<City> {

		public double getValue(Individual<City> individual) {
			double fitness = 0;
			
			List<City> representation = individual.getRepresentation();
			
			for(int i = 0; i < representation.size() - 1; i++) {
				Integer cost = representation.get(i).getCost(representation.get(i + 1));
				
				if(cost == null)
					return Double.MAX_VALUE;
				
				fitness += cost;
			}
			
			Integer cost = representation.get(representation.size() - 1).getCost(representation.get(0));
			
			if(cost == null)
				return Double.MAX_VALUE;
			
			fitness += cost;

			return 1 / fitness;
		}
	}

	public static class TSPGenAlgoGoalTest implements GoalTest {

		@SuppressWarnings("unchecked")
		public boolean isGoalState(Object state) {
			
			List<City> representation = ((Individual<City>) state).getRepresentation();
			
			Set<City> middleCities = new HashSet<>();

			for(int i = 0; i < representation.size(); i++) {
				if(!middleCities.add(representation.get(i)))
					return false;
			}
			
			for(int i = 0; i < representation.size() - 1; i++) {
				if(representation.get(i).getCost(representation.get(i + 1)) == null)
					return false;
			}
			
			if(representation.get(representation.size() - 1).getCost(representation.get(0)) == null)
				return false;
			
			return true;
		}
	}

}
