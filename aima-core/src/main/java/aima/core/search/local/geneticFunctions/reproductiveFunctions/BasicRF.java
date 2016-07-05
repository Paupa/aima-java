package aima.core.search.local.geneticFunctions.reproductiveFunctions;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.local.Individual;
import aima.core.search.local.geneticFunctions.AbstractGeneticFunction;
import aima.core.search.local.geneticFunctions.GeneticReproductiveFunction;

public class BasicRF<A> extends AbstractGeneticFunction implements GeneticReproductiveFunction<A>{

	@Override
	public Individual<A> reproduce(Individual<A> firstParent, Individual<A> secondParent) {
		// n <- LENGTH(x);
		// Note: this is = this.individualLength
		// c <- random number from 1 to n
		int individualLength = firstParent.length();
		
		int c = randomOffset(individualLength);
		// return APPEND(SUBSTRING(x, 1, c), SUBSTRING(y, c+1, n))
		List<A> childRepresentation = new ArrayList<A>();
		childRepresentation.addAll(firstParent.getRepresentation().subList(0, c));
		childRepresentation.addAll(secondParent.getRepresentation().subList(c, individualLength));

		Individual<A> child = new Individual<A>(childRepresentation);
		return child;
	}

}
