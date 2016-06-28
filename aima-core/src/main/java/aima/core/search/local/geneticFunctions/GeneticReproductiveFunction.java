package aima.core.search.local.geneticFunctions;

import aima.core.search.local.Individual;

public interface GeneticReproductiveFunction<A> {

	public Individual<A> reproduce(Individual<A> firstParent, Individual<A> secondParent);
}
