package com.docum;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runners.Suite;

import com.docum.util.stats.StatsHelperTest;

@Suite.SuiteClasses({ StatsHelperTest.class })
public class DocumTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(DocumTests.class.getName());
		//$JUnit-BEGIN$

		//$JUnit-END$
		return suite;
	}

}
