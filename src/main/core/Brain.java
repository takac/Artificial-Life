package core;

import utils.Logger;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public abstract class Brain {
	
	public static final int STEP = 10;
	
	protected final ALife life;
	protected Logger log;
	
	public Brain(ALife life) {
		this.life = life;
		log = new Logger("Mover");
	}
	
	public void setVerbosity(int level) {
		log.setVerbosity(level);
	}
	
	public abstract int think();
	
}
