package org.mule.application.muleuniquefile;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VerifyUniqueTest {
	private static final String ROOT_PATH = "C://MyData//Softwares//mule-standalone-3.7.0//mule-standalone-3.7.0//bin//temp//";

	@Before
	public void writeFileToSource() {

		// create a new file and write it to source location
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(ROOT_PATH + "src//testfile1.txt"));
			writer.write("This is first line");
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	@Test
	public void testMe() {

		try {
			Thread.sleep(600000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File f = new File(ROOT_PATH + "target");

		String arr[] = f.list(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.startsWith("testfile1.txt");
			}
		});

		Assert.assertEquals("Array Length should be 1", 1, arr.length);

	}
}
