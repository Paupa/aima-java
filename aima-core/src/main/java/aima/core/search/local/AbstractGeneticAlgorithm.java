package aima.core.search.local;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import aima.core.search.framework.GoalTest;
import aima.core.search.framework.Metrics;
import aima.core.util.Util;

public abstract class AbstractGeneticAlgorithm<A> {
	
	protected static final String POPULATION_SIZE = "populationSize";
	protected static final String ITERATIONS = "iterations";
	protected static final String TIME_IN_MILLISECONDS = "timeInMilliseconds";

	protected Metrics metrics = new Metrics();
	protected Individual<A> bestIndividual;

	protected int individualLength;
	protected List<A> finiteAlphabet;
	protected double mutationProbability;
	protected double crossoverProbability;
	protected boolean elitism;
	protected Random random;

	public AbstractGeneticAlgorithm(int individualLength, Set<A> finiteAlphabet,
			double mutationProbability, double crossoverProbability, boolean elitism) {
		this(individualLength, finiteAlphabet, mutationProbability, crossoverProbability, elitism,
				new Random());
	}

	public AbstractGeneticAlgorithm(int individualLength, Set<A> finiteAlphabet,
			double mutationProbability, double crossoverProbability, boolean elitism, Random random) {
		this.individualLength = individualLength;
		this.finiteAlphabet = new ArrayList<A>(finiteAlphabet);
		this.mutationProbability = mutationProbability;
		this.crossoverProbability = crossoverProbability;
		this.elitism = elitism;
		this.random = random;

		assert (this.mutationProbability >= 0.0 && this.mutationProbability <= 1.0);
		assert (this.crossoverProbability >= 0.0 && this.crossoverProbability <= 1.0);
	}
	
	public Individual<A> geneticAlgorithm(Set<Individual<A>> population,
			FitnessFunction<A> fitnessFn, GoalTest goalTest) {
		
		bestIndividual = retrieveBestIndividual(population,  fitnessFn);

		// Create a local copy of the population to work with
		population = new LinkedHashSet<Individual<A>>(population);
		// Validate the population and setup the instrumentation
		validatePopulation(population);
		clearInstrumentation();
		setPopulationSize(population.size());

		long startTime = System.currentTimeMillis();
		
		while(notFinished()) {
			
			bestIndividual = nextGeneration(population, fitnessFn);
			setIterations(getIterations() + 1);
			setTimeInMilliseconds(System.currentTimeMillis() - startTime);
		}
		
		// return the best individual in population, according to FITNESS-FN
		return bestIndividual;
	}

	protected abstract boolean notFinished();

	/**
	 * Sets the population size and number of iterations to zero.
	 */
	public void clearInstrumentation() {
		setPopulationSize(0);
		setIterations(0);
		setTimeInMilliseconds(0L);
	}

	/**
	 * Returns all the metrics of the genetic algorithm.
	 * 
	 * @return all the metrics of the genetic algorithm.
	 */
	public Metrics getMetrics() {
		return metrics;
	}

	/**
	 * Returns the population size.
	 * 
	 * @return the population size.
	 */
	public int getPopulationSize() {
		return metrics.getInt(POPULATION_SIZE);
	}

	/**
	 * Sets the population size.
	 * 
	 * @param size
	 *            the population size.
	 */
	public void setPopulationSize(int size) {
		metrics.set(POPULATION_SIZE, size);
	}

	/**
	 * Returns the number of iterations of the genetic algorithm.
	 * 
	 * @return the number of iterations of the genetic algorithm.
	 */
	public int getIterations() {
		return metrics.getInt(ITERATIONS);
	}

	/**
	 * Sets the number of iterations of the genetic algorithm.
	 * 
	 * @param cnt
	 *            the number of iterations.
	 */
	public void setIterations(int cnt) {
		metrics.set(ITERATIONS, cnt);
	}

	/**
	 * 
	 * @return the time in milliseconds that the genetic algorithm took.
	 */
	public long getTimeInMilliseconds() {
		return metrics.getLong(TIME_IN_MILLISECONDS);
	}

	/**
	 * Set the time in milliseconds that the genetic algorithm took.
	 * 
	 * @param time
	 *            the time in milliseconds that the genetic algorithm took.
	 */
	public void setTimeInMilliseconds(long time) {
		metrics.set(TIME_IN_MILLISECONDS, time);
	}

	//
	// PROTECTED METHODS
	//
	// Note: Override these protected methods to create your own desired
	// behavior.
	//
	protected Individual<A> nextGeneration(Set<Individual<A>> population,
			FitnessFunction<A> fitnessFn) {
		// new_population <- empty set
		Set<Individual<A>> newPopulation = new HashSet<Individual<A>>();

		// Represent the population as a list to simplify/optimize random
		// selection.
		List<Individual<A>> populationAsList = new ArrayList<Individual<A>>(
				population);

		// for i = 1 to SIZE(population) do
		for (int i = 0; i < population.size(); i++) {
			
			Individual<A> child = null;
			
			// x <- RANDOM-SELECTION(population, FITNESS-FN)
			Individual<A> x = randomSelection(populationAsList, fitnessFn);
			
			if(random.nextDouble() <= this.crossoverProbability) {
				// y <- RANDOM-SELECTION(population, FITNESS-FN)
				Individual<A> y = randomSelection(populationAsList, fitnessFn);
				
				// child <- REPRODUCE(x, y)
				child = reproduce(x, y);
			}
			
			else
				child = new Individual<A>(x.getRepresentation());
			
			// if (small random probability) then child <- MUTATE(child)
			if (random.nextDouble() <= this.mutationProbability) {
				child = mutate(child);
			}
			// add child to new_population
			newPopulation.add(child);
		}
		// population <- new_population
		population.clear();
		population.addAll(newPopulation);
		
		if(elitism) {
			population.add(new Individual<A>(bestIndividual.getRepresentation()));
		}

		return retrieveBestIndividual(population, fitnessFn);
	}

	// RANDOM-SELECTION(population, FITNESS-FN)
	protected Individual<A> randomSelection(List<Individual<A>> population,
			FitnessFunction<A> fitnessFn) {
		Individual<A> selected = null;

		// Determine all of the fitness values
		double[] fValues = new double[population.size()];
		for (int i = 0; i < population.size(); i++) {
			fValues[i] = fitnessFn.getValue(population.get(i));
		}

		// Normalize the fitness values
		fValues = Util.normalize(fValues);
		double prob = random.nextDouble();
		double totalSoFar = 0.0;
		for (int i = 0; i < fValues.length; i++) {
			// Are at last element so assign by default
			// in case there are rounding issues with the normalized values
			totalSoFar += fValues[i];
			if (prob <= totalSoFar) {
				selected = population.get(i);
				break;
			}
		}

		// selected may not have been assigned
		// if there was a rounding error in the
		// addition of the normalized values (i.e. did not total to 1.0)
		if (null == selected) {
			// Assign the last value
			selected = population.get(population.size() - 1);
		}

		return selected;
	}

	// function REPRODUCE(x, y) returns an individual
	// inputs: x, y, parent individuals
	protected abstract Individual<A> reproduce(Individual<A> x, Individual<A> y);

	protected abstract Individual<A> mutate(Individual<A> child);

	public Individual<A> retrieveBestIndividual(
			Set<Individual<A>> population, FitnessFunction<A> fitnessFn) {
		Individual<A> bestIndividual = null;
		double bestSoFarFValue = Double.NEGATIVE_INFINITY;

		for (Individual<A> individual : population) {
			double fValue = fitnessFn.getValue(individual);
			if (fValue > bestSoFarFValue) {
				bestIndividual = individual;
				bestSoFarFValue = fValue;
			}
		}

		return bestIndividual;
	}

	protected void validatePopulation(Set<Individual<A>> population) {
		// Require at least 1 individual in population in order
		// for algorithm to work
		if (population.size() < 1) {
			throw new IllegalArgumentException(
					"Must start with at least a population of size 1");
		}
		// String lengths are assumed to be of fixed size,
		// therefore ensure initial populations lengths correspond to this
		for (Individual<A> individual : population) {
			if (individual.length() != this.individualLength) {
				throw new IllegalArgumentException("Individual [" + individual
						+ "] in population is not the required length of "
						+ this.individualLength);
			}
		}
	}

}
