package aima.gui.demo.search.util;

import java.util.Set;

import aima.core.environment.tsp.City;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.BasicGeneticAlgorithm;

public class TSPBasicGeneticAlgorithmInstantiator extends AbstractTSPGeneticAlgorithmInstantiator {

	@Override
	protected GeneticAlgorithm<City> instantiate(int individualLength, Set<City> finiteAlphabet,
			double mutationProbability) {
		return new BasicGeneticAlgorithm<City>(individualLength, finiteAlphabet, mutationProbability);
	}

}
