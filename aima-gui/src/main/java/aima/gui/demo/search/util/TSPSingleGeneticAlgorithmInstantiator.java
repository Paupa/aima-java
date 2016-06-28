package aima.gui.demo.search.util;

import java.util.Set;

import aima.core.environment.tsp.City;
import aima.core.search.local.AbstractGeneticAlgorithm;
import aima.core.search.local.SingleGeneticAlgorithm;
import aima.core.search.local.geneticFunctions.reproductiveImplementations.TwoPointsCrossoverNoRepetitionRF;

public class TSPSingleGeneticAlgorithmInstantiator extends AbstractTSPGeneticAlgorithmInstantiator {

	@Override
	protected AbstractGeneticAlgorithm<City> instantiate(int individualLength, Set<City> finiteAlphabet,
			double mutationProbability) {
		SingleGeneticAlgorithm<City> sga = new SingleGeneticAlgorithm<City>(individualLength, finiteAlphabet, mutationProbability);
		sga.setElitism(true);
		sga.setReproductiveFunction(new TwoPointsCrossoverNoRepetitionRF<City>());
		return sga;
	}

}
