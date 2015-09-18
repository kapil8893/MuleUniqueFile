package org.mule.application.muleuniquefile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleException;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class MuleTestFlow extends FunctionalTestCase {

	private static String srcPath;
	private static String targetPath;

	@Override
	protected String[] getConfigFiles() {
		return new String[] { "src/main/app/mule-config.xml" };
	}

	@BeforeClass
	public static void setup() {
		Properties prop = new Properties();
		InputStream pathProp = null;
		System.setProperty("mule.test.timeoutSecs", "700000");

		try {
			pathProp = new FileInputStream("src/main/resources/path.properties");
			prop.load(pathProp);
			srcPath = prop.getProperty("src.path");
			targetPath = prop.getProperty("target.path");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pathProp.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void invokeFlow() throws InterruptedException, MuleException {

		MuleClient client = muleContext.getClient();

		// write file to source destination

		try {
			client.dispatch("file://" + srcPath + "?connector=input", "Some Text File", null);

		} catch (MuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

		// sleep so that at least the polling service is invoked twice

		try {
			Thread.sleep(600000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

		// verify file count at target location
		File f = new File(targetPath);

		String arr[] = f.list();

		Assert.assertNotNull(arr);
		Assert.assertEquals("Array Length should be 1", 1, arr.length);

	}
}
