package aima.core.search.local.geneticFunctions;

import aima.core.search.local.Individual;

public interface GeneticMutationFunction<A> {
	
	public Individual<A> mutate(Individual<A> cromosome);

}
