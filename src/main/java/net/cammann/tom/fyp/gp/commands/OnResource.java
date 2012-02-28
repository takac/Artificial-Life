package net.cammann.tom.fyp.gp.commands;

import net.cammann.tom.fyp.core.Commandable;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

public class OnResource extends CommandGene {
	
	public OnResource(GPConfiguration a_conf, Class a_returnType)
			throws InvalidConfigurationException {
		super(a_conf, 0, a_returnType);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "IsOnResource";
	}
	
	@Override
	public double execute_double(ProgramChromosome c, int n, Object[] args) {
		Commandable life = (Commandable) args[0];
		
		if (life.getMap().hasResource(life.getPosition())) {
			return 1;
		}
		return 0;
		
	}
}
