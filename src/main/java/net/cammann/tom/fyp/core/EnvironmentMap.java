package net.cammann.tom.fyp.core;

import java.awt.Dimension;
import java.awt.Point;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public interface EnvironmentMap {
	public int getHeight();
	
	public int getWidth();
	
	public Dimension getDimension();
	
	public void setHeight(int height);
	
	public void setWidth(int width);
	
	public void addResource(Resource r);
	
	public HashedResourceMap getResourceList();
	
	public boolean hasResource(int x, int y);
	
	public boolean hasResource(Point p);
	
	public Resource getResource(int x, int y);
	
	public Resource getResource(Point p);
	
	public boolean removeResource(int x, int y);
	
	public boolean removeResource(Point p);
	
	public boolean removeResource(Resource r);
	
	public void resetMap();
	
	public void initResources();
}