package test.math;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import math.RotatePoints;

public class RotatePointsTest {
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void twoAngleDistanceTest() {
		assertEquals(20, RotatePoints.twoAngleDistance(350, 10), 0.001);
		assertEquals(-80, RotatePoints.twoAngleDistance(60, 340), 0.001);
		assertEquals(120, RotatePoints.twoAngleDistance(280, 40), 0.001);
		assertEquals(130, RotatePoints.twoAngleDistance(40, 170), 0.001);
		assertEquals(-140, RotatePoints.twoAngleDistance(40, 260), 0.001);
		assertEquals(160, RotatePoints.twoAngleDistance(10, 170), 0.001);
		assertEquals(-95, RotatePoints.twoAngleDistance(85, 350), 0.001);
		assertEquals(-160, RotatePoints.twoAngleDistance(170, 10), 0.001);
		assertEquals(-120, RotatePoints.twoAngleDistance(40, 280), 0.001);
	}
}