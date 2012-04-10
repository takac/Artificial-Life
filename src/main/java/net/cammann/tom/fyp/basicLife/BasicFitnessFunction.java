package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstactLife;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.core.SimpleFitnessFunction;

import org.apache.log4j.Logger;

/**
 * 
 * @author Tom Cammann
 * 
 */
public class BasicFitnessFunction extends SimpleFitnessFunction {
	
	static Logger logger = Logger.getLogger(SimpleFitnessFunction.class);
	
	public BasicFitnessFunction(final EvolutionFactory fact) {
		super(fact);
	}
	
	@Override
	public double computeRawFitness(final ALife life) {
		double fitness = 0;
		if (life.getEnergy() > 0) {
			fitness += ((AbstactLife) life).getEnergy();
		} else {
			// fitness += ((ABug) life).getEnergy();
			fitness += 0;
		}
		logger.trace("Moves: " + life.moveCount);
		logger.trace("Raw fit: " + fitness);
		return fitness;
		
	}
}