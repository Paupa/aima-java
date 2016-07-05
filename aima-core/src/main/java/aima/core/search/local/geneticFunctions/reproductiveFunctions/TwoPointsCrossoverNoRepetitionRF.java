package aima.core.search.local.geneticFunctions.reproductiveFunctions;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.local.Individual;
import aima.core.search.local.geneticFunctions.AbstractGeneticFunction;
import aima.core.search.local.geneticFunctions.GeneticReproductiveFunction;

public class TwoPointsCrossoverNoRepetitionRF<A> extends AbstractGeneticFunction implements GeneticReproductiveFunction<A> {

	@Override
	public Individual<A> reproduce(Individual<A> firstParent, Individual<A> secondParent) {
		// n <- LENGTH(x);
		// Note: this is = this.individualLength
		// c <- random number from 1 to n
		
		int individualLength = firstParent.length();
		
		int p1 = randomOffset(individualLength);
		int p2 = randomOffset(individualLength);
		List<A> xArray = firstParent.getRepresentation();
		List<A> yArray = secondParent.getRepresentation();
		List<A> offArray = new ArrayList<A>(xArray);

		// Keep the substring from p1 to p2-1 to the offspring, order and
		// position
		// The remaining genes, p2 to p1-1, from the second parent, relative
		// order
		int k = p2;
		for (int i = 0; i < individualLength; i++) {
			int j = p1;
			while (j < p2 + (p2 <= p1 ? individualLength : 0) && yArray.get(i) != xArray.get(j % individualLength))
				j++;
			if (j == p2 + (p2 <= p1 ? individualLength : 0)) { // yArray[i] is
																// not in
																// offArray from
																// p1 to p2-1
				offArray.set(k % individualLength, yArray.get(i));
				k++;
			}
		}
		return new Individual<A>(offArray);
	}

}
