package net.cammann.tom.fyp.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.lang.reflect.Method;

import junit.framework.Assert;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstractEnvironmentMap;
import net.cammann.tom.fyp.core.Commandable;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.MapObject;
import net.cammann.tom.fyp.core.Obstacle;
import net.cammann.tom.fyp.core.Resource;
import net.cammann.tom.fyp.core.SimpleResource;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the basics of the EnvironmentMap class.
 *
 * @see EnvironmentMap
 * @author TC
 *
 */
public class TestEnvironment {

	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(TestEnvironment.class);

	/**
	 * Used to setup logger in test mode.
	 */
	@BeforeClass
	public static void before() {
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
	}

	/**
	 * Simple test to assure resources are correctly placed
	 *
	 * Checks resources are correctly placed on the map. Checks their type,
	 * checks that they can be accessed Makes sure duplicate resources cannot be
	 * added Checks resource cannot be added off the map.
	 */
	@Test
	public final void resourceTest() {
		try {
			// TODO remove 'magic numbers'
			final EnvironmentMap map = TestUtils.getInstance().getBlankMap(100,
					200);
			final Resource r = new SimpleResource(new Point(10, 30));
			// Check normalcy of resource
			assertTrue(r.getPosition().equals(new Point(10, 30)));
			assertTrue(r.getResourceType().equals(Resource.ResourceType.BASIC));

			final Method removeResourceR = AbstractEnvironmentMap.class.getDeclaredMethod(
					"removeResource", new Class<?>[] { Resource.class });
			removeResourceR.setAccessible(true);

			final Method removeResourceP = AbstractEnvironmentMap.class.getDeclaredMethod(
					"removeResource", new Class<?>[] { Point.class });
			removeResourceP.setAccessible(true);

			final Method addResource = AbstractEnvironmentMap.class.getDeclaredMethod(
					"addResource", new Class<?>[] { Resource.class });
			addResource.setAccessible(true);

			final Method addObstacle = AbstractEnvironmentMap.class.getDeclaredMethod(
					"addObstacle", new Class<?>[] { Obstacle.class });
			addObstacle.setAccessible(true);

			// Check added correctly
			Object out = addResource.invoke(map, r);
			assertTrue((Boolean) out);
			final MapObject ro = map.getResourceIterator().next();
			assertTrue(ro.equals(ro));

			// Check no duplicates can be added
			Resource tmp = new SimpleResource(new Point(10, 30));
			out = addResource.invoke(map, tmp);
			assertFalse((Boolean) out);
			out = addResource.invoke(map, r);
			assertFalse((Boolean) out);

			// Assert no off map objects can be added
			tmp = new SimpleResource(new Point(-10, 10));
			out = addResource.invoke(map, tmp);
			assertFalse((Boolean) out);

			// Check resources cannot be added over an obstacle
			final Obstacle o = new Obstacle(new Point(50, 40), 5);
			addObstacle.invoke(map, o);
			tmp = new SimpleResource(new Point(50, 40));
			out = addResource.invoke(map, tmp);
			assertFalse((Boolean) out);

			// Check calories
			r.setCalories(100);
			assertTrue(r.getCalories() == 100);

			// Check removals
			// This is checked more by obstacleTest, uses same underlying object
			out = removeResourceR.invoke(map, r);
			assertTrue((Boolean) out);
			out = addResource.invoke(map, r);
			assertTrue((Boolean) out);
			out = removeResourceP.invoke(map, new Point(10, 30));
			assertTrue((Boolean) out);
		} catch (final Exception e) {
			logger.fatal("Failed in resource test", e);
			Assert.fail();
		}

	}

	/**
	 * Test validity when adding an obstacle.
	 *
	 * no duplicate positions, not off map,not on top of resource
	 */
	@Test
	public final void obstacleTest() {
		try {
			// TODO remove 'magic numbers'
			final AbstractEnvironmentMap map = (AbstractEnvironmentMap) TestUtils.getInstance()
					.getBlankMap(100, 200);

			final Method addObstacle = AbstractEnvironmentMap.class.getDeclaredMethod(
					"addObstacle", new Class<?>[] { Obstacle.class });
			addObstacle.setAccessible(true);

			final Obstacle o = new Obstacle(new Point(50, 60), 5);

			// Check normal add
			Object out = addObstacle.invoke(map, o);
			assertTrue((Boolean) out);
			// Make sure no duplicates
			out = addObstacle.invoke(map, o);
			assertFalse((Boolean) out);

			final Obstacle o2 = new Obstacle(new Point(-10, 10), 5);
			// Check cant be added off map
			out = addObstacle.invoke(map, o2);
			assertFalse((Boolean) out);

			final Resource r = new SimpleResource(new Point(80, 40));
			final Obstacle o3 = new Obstacle(new Point(80, 40), 5);

			final Method addResource = AbstractEnvironmentMap.class.getDeclaredMethod(
					"addResource", new Class<?>[] { Resource.class });
			addResource.setAccessible(true);

			out = addResource.invoke(map, r);

			// check cannot be added on top of resource
			out = addObstacle.invoke(map, o3);
			assertFalse((Boolean) out);

			final Method removeObstacle1 = AbstractEnvironmentMap.class.getDeclaredMethod(
					"removeObstacle", new Class<?>[] { Obstacle.class });
			removeObstacle1.setAccessible(true);
			final Method removeObstacle2 = AbstractEnvironmentMap.class.getDeclaredMethod(
					"removeObstacle", new Class<?>[] { Point.class });
			removeObstacle2.setAccessible(true);
			final Method removeResource = AbstractEnvironmentMap.class.getDeclaredMethod(
					"removeResource", new Class<?>[] { Resource.class });
			removeResource.setAccessible(true);

			// Check removal of obstacle
			out = removeObstacle1.invoke(map, o3);
			assertFalse((Boolean) out);
			out = removeObstacle2.invoke(map, o2.getPosition());
			assertFalse((Boolean) out);
			out = removeObstacle2.invoke(map, o.getPosition());
			assertTrue((Boolean) out);
			out = removeResource.invoke(map, r);
			assertTrue((Boolean) out);
			out = addObstacle.invoke(map, o3);
			assertTrue((Boolean) out);
			out = removeObstacle2.invoke(map, o3.getPosition());
			assertTrue((Boolean) out);
		} catch (final Exception e) {
			logger.fatal("Failed in resource test", e);
			Assert.fail();
		}

	}

	/**
	 * Test validity of Life object.
	 *
	 */
	@Test
	public final void lifeTest() {
		// TODO remove 'magic numbers'
		final EnvironmentMap map = TestUtils.getInstance()
				.getBlankMap(100, 200);

		final ALife life = TestUtils.getInstance().getBlankLife(map);
		// Check add to map
		assertTrue(map.addLife(life));
		assertTrue(map.validPosition(life.getPosition()));

		// Check add to map is bad when
		life.setX(-10);
		assertFalse(map.validPosition(life.getPosition()));

		life.setPosition(new Point(40, 70));
		logger.debug("Life is at: " + life.getPosition());
		assertTrue(life.getPosition().equals(new Point(40, 70)));
		// Point p = life.getPosition();
		// Check normalcy
		life.setOrientation(Commandable.ORIENTATION.UP);
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.UP));

		// Check move forward
		life.moveForward();
		logger.debug("Life is at: " + life.getPosition());
		assertTrue(life.getPosition().equals(new Point(40, 60)));

		// Check turns
		life.turnLeft();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.LEFT));
		life.turnLeft();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.DOWN));
		life.turnLeft();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.RIGHT));
		life.turnLeft();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.UP));

		life.turnRight();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.RIGHT));
		life.turnRight();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.DOWN));
		life.turnRight();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.LEFT));
		life.turnRight();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.UP));

		// Check energy, and energy consumption
		life.setEnergy(100);
		assertTrue(life.getEnergy() == 100);
		life.decrementEnegery(50);
		assertTrue(life.getEnergy() == 50);
		life.incrementEnergy(40);
		assertTrue(life.getEnergy() == 90);

		life.setOrientation(Commandable.ORIENTATION.UP);
		final Point p = new Point(life.getPosition());
		p.setLocation(p.x, p.y - 10);
		final Resource r = new SimpleResource(p);

		try {
			final Method addResource = AbstractEnvironmentMap.class.getDeclaredMethod(
					"addResource", new Class<?>[] { Resource.class });
			addResource.setAccessible(true);

			final Object out = addResource.invoke(map, r);
		} catch (final Exception e) {
			logger.fatal("Failed invokign add resource", e);
			Assert.fail();
		}

		r.setCalories(100);
		life.moveForward();
		logger.debug("Life position: " + life.getPosition());
		logger.debug("Resource position: " + r.getPosition());
		assertTrue(life.getPosition().equals(r.getPosition()));

		final int curNrg = life.getEnergy();
		assertTrue(map.hasResource(life.getPosition()));
		assertTrue(life.consume());
		assertTrue(life.getEnergy() == curNrg + r.getCalories());
		assertTrue(life.getEnergy() == curNrg + 100);
		assertFalse(map.hasResource(life.getPosition()));
		assertFalse(life.consume());

		// Check removal of life
		assertTrue(map.hasLife(life.getPosition()));
		assertTrue(map.removeLife(life));
		assertTrue(map.addLife(life));
		assertTrue(map.removeLife(life.getPosition()));

	}

}
