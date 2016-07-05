package aima.gui.demo.search.util;

import java.util.Set;

import aima.core.environment.tsp.City;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.geneticFunctions.reproductiveFunctions.TwoPointsCrossoverNoRepetitionRF;
import aima.core.search.local.FlexibleGeneticAlgorithm;

public class TSPFlexibleGeneticAlgorithmInstantiator extends AbstractTSPGeneticAlgorithmInstantiator {

	@Override
	protected GeneticAlgorithm<City> instantiate(int individualLength, Set<City> finiteAlphabet,
			double mutationProbability) {
		FlexibleGeneticAlgorithm<City> sga = new FlexibleGeneticAlgorithm<City>(individualLength, finiteAlphabet, mutationProbability);
		sga.setElitism(true);
		sga.setReproductiveFunction(new TwoPointsCrossoverNoRepetitionRF<City>());
		return sga;
	}

}
