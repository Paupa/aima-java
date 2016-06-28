package aima.gui.demo.search.util;

import java.util.Set;

import aima.core.environment.tsp.City;
import aima.core.search.local.AbstractGeneticAlgorithm;
import aima.core.search.local.GeneticAlgorithm;

public class TSPGeneticAlgorithmInstantiator extends AbstractTSPGeneticAlgorithmInstantiator {

	@Override
	protected AbstractGeneticAlgorithm<City> instantiate(int individualLength, Set<City> finiteAlphabet,
			double mutationProbability) {
		return new GeneticAlgorithm<City>(individualLength, finiteAlphabet, mutationProbability);
	}

}
