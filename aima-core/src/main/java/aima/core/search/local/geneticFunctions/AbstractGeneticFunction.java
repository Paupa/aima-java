package aima.core.search.local.geneticFunctions;

import java.util.Random;

public abstract class AbstractGeneticFunction {
	
	protected Random random;
	
	public AbstractGeneticFunction() {
		this(new Random());
	}
	
	public AbstractGeneticFunction(Random random) {
		assert (random != null);
		
		this.random = random;
	}
	
	protected int randomOffset(int length) {
		return random.nextInt(length);
	}

}
