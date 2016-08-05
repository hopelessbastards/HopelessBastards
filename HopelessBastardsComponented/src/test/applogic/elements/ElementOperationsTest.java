package test.applogic.elements;

import static org.junit.Assert.*;

import java.awt.Rectangle;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import applogic.elements.ElementOperations;

public class ElementOperationsTest {

	private ElementOperations operations;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		operations = new ElementOperations();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void fogOfWarLocalLocationTest() {
		assertEquals(new Rectangle(50, 50, 50, 50),operations.fogOfWarLocalLocation(new Rectangle(100, 100, 100, 100), new Rectangle(150, 150, 50, 50)));
		assertEquals(null,operations.fogOfWarLocalLocation(new Rectangle(500, 500, 50, 50), new Rectangle(150, 150, 50, 50)));
		assertEquals(new Rectangle(0, 0, 100, 100),operations.fogOfWarLocalLocation(new Rectangle(500, 500, 500, 500), new Rectangle(400, 400, 200, 200)));
		assertEquals(null,operations.fogOfWarLocalLocation(new Rectangle(100, 100, 100, 100), new Rectangle(200, 200, 50, 50)));
		assertEquals(new Rectangle(100, 100, 900, 900),operations.fogOfWarLocalLocation(new Rectangle(100, 100, 1000, 1000), new Rectangle(200, 200, 1000, 1000)));
	}
}