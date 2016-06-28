package aima.core.search.local.geneticFunctions.reproductiveImplementations;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.local.Individual;
import aima.core.search.local.geneticFunctions.AbstractGeneticFunction;
import aima.core.search.local.geneticFunctions.GeneticReproductiveFunction;

public class OnePointCrossoverNoRepetitionRF<A> extends AbstractGeneticFunction implements GeneticReproductiveFunction<A> {

	@Override
	public Individual<A> reproduce(Individual<A> firstParent, Individual<A> secondParent) {
		// n <- LENGTH(x);
		// Note: this is = this.individualLength
		// c <- random number from 1 to n
		
		int individualLength = firstParent.length();
		
		int c = randomOffset(individualLength);
		List<A> xArray = firstParent.getRepresentation();
		List<A> yArray = secondParent.getRepresentation();
		List<A> offArray = new ArrayList<A>();
		// The first substring from the first parent, order and position
		for (int i = 0; i < c; i++)
			offArray.add(xArray.get(i));
		// The remaining genes from the second parent, relative order
		for (int i = 0; i < individualLength; i++) {
			int j = 0;
			while (j < c && yArray.get(i) != xArray.get(j))
				j++;
			if (j == c) { // yArray[i] is not in offArray[0..c-1] ==
							// xArray[0..c-1]
				offArray.add(yArray.get(i));
			}
		}
		return new Individual<A>(offArray);
	}

}
