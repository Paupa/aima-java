package aima.core.search.local.geneticFunctions.mutationFunctions;

import java.util.ArrayList;

import aima.core.search.local.Individual;
import aima.core.search.local.geneticFunctions.AbstractGeneticFunction;
import aima.core.search.local.geneticFunctions.GeneticMutationFunction;

public class RandomSortingSublistMF<A> extends AbstractGeneticFunction implements GeneticMutationFunction<A> {

	@Override
	public Individual<A> mutate(Individual<A> cromosome) {
		// Random sorting of substring p,..,(p+c) mod individualLength
		
		int individualLength = cromosome.length();
		
		int p = randomOffset(individualLength - 1);
		int c = randomOffset(individualLength / 2) + 1;
		ArrayList<A> mutatedRepresentation = new ArrayList<A>(cromosome.getRepresentation());
		for (int i = p; i <= p + c; i++) {
			int x = i % individualLength;
			int y = (p + randomOffset(c)) % individualLength;
			A temp = mutatedRepresentation.get(x);
			mutatedRepresentation.set(x, mutatedRepresentation.get(y));
			mutatedRepresentation.set(y, temp);
		}
		return (new Individual<A>(mutatedRepresentation));
	}

}
