package org.mule.application.muleuniquefile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class VerifyUniqueTest {
	private static final String rootPath = "C://MyData//Softwares//mule-standalone-3.7.0//mule-standalone-3.7.0//bin//temp//";

	@Before
	public void writeFileToSource() {

		// create a new file and write it to source location
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(rootPath + "src//testfile1.txt"));
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
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File f = new File(rootPath + "target//testfile1.txt");

		Long systime = new Long(System.currentTimeMillis() / 60000);
		Long fileModifiedTime = new Long(f.lastModified() / 60000);

		Assert.assertThat(systime, is(not(fileModifiedTime)));

	}
}
