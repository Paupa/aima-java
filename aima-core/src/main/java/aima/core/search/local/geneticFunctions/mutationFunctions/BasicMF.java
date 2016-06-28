package aima.core.search.local.geneticFunctions.mutationFunctions;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.local.Individual;
import aima.core.search.local.geneticFunctions.AbstractGeneticFunction;
import aima.core.search.local.geneticFunctions.GeneticMutationFunction;

public class BasicMF<A> extends AbstractGeneticFunction implements GeneticMutationFunction<A> {
	
	private List<A> finiteAlphabet;

	public BasicMF(List<A> finiteAlphabet) {
		this.finiteAlphabet = finiteAlphabet;
	}

	@Override
	public Individual<A> mutate(Individual<A> cromosome) {
		int mutateOffset = randomOffset(cromosome.length());
		int alphaOffset = randomOffset(finiteAlphabet.size());

		List<A> mutatedRepresentation = new ArrayList<A>(
				cromosome.getRepresentation());

		mutatedRepresentation
				.set(mutateOffset, finiteAlphabet.get(alphaOffset));

		Individual<A> mutatedChild = new Individual<A>(mutatedRepresentation);

		return mutatedChild;
	}

}
