package net.cammann.tom.fyp.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Obstacle implements MapObject {
	
	private Point p;
	private static Image img = null;
	private final double radius;
	
	public Obstacle(Point p, double radius) {
		this.p = p;
		this.radius = radius;
	}
	
	public Obstacle(double x, double y, double radius) {
		p = new Point();
		p.setLocation(x, y);
		
		this.radius = radius;
	}
	
	@Override
	public int getX() {
		return p.x;
	}
	
	@Override
	public int getY() {
		return p.y;
	}
	
	public void setX(int x) {
		p.setLocation(x, p.y);
	}
	
	public void setY(int y) {
		p.setLocation(p.x, y);
	}
	
	// TODO remove?
	public void setPosition(Point p) {
		this.p = p;
		
	}
	
	@Override
	public Point getPosition() {
		return p;
	}
	
	@Override
	public double getRadius() {
		return 0;
	}
	
	private Image getObstacleImage() {
		if (img == null) {
			
			BufferedImage b2 = new BufferedImage(10, 10,
					BufferedImage.TYPE_INT_ARGB);
			
			Graphics2D g2 = b2.createGraphics();
			g2.setColor(Color.BLACK);
			g2.fill(new Ellipse2D.Double(0, 0, 10, 10));
			
			img = b2;
			return img;
			
		} else {
			return img;
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		g2.drawImage(getObstacleImage(), getX(), getY(), null);
		
	}
}