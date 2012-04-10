package net.cammann.tom.fyp.gp.commands;

import net.cammann.tom.fyp.core.Commandable;
import net.cammann.tom.fyp.core.Commandable.ORIENTATION;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

public class WallAhead extends CommandGene {
	
	public WallAhead(final GPConfiguration a_conf, final Class a_returnType)
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
	public boolean execute_boolean(final ProgramChromosome c, final int n,
			final Object[] args) {
		return isWallAhead((Commandable) args[0]);
	}
	
	@Override
	public double execute_double(final ProgramChromosome c, final int n,
			final Object[] args) {
		if (isWallAhead((Commandable) args[0])) {
			return 1;
		}
		return 0;
	}
	
	private boolean isWallAhead(final Commandable life) {
		
		final int STEP = 10;
		final int RANGE = 5;
		final int x = life.getX();
		final int y = life.getY();
		
		if (life.getOrientation() == ORIENTATION.UP) {
			if (checkNewPosition(x, y - STEP * RANGE, life)) {
				return false;
			}
		} else if (life.getOrientation() == ORIENTATION.RIGHT) {
			if (checkNewPosition(x + STEP * RANGE, y, life)) {
				return false;
			}
		} else if (life.getOrientation() == ORIENTATION.DOWN) {
			if (checkNewPosition(x, y + STEP * RANGE, life)) {
				return false;
			}
		} else {
			if (checkNewPosition(x - STEP * RANGE, y, life)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkNewPosition(final int x, final int y,
			final Commandable life) {
		if (x > life.getMap().getWidth() || x < 0) {
			return false;
		} else if (y > life.getMap().getHeight() || y < 0) {
			return false;
		}
		return true;
	}
}
