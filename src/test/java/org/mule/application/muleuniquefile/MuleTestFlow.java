package org.mule.application.muleuniquefile;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleException;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class MuleTestFlow extends FunctionalTestCase {

	public static final String ROOT_PATH = "C://MyData//Softwares//mule-standalone-3.7.0//mule-standalone-3.7.0//bin//temp//";

	@Override
	protected String[] getConfigFiles() {
		// TODO Auto-generated method stub
		return new String[] { "src/main/app/mule-config.xml" };
	}

	@Test
	public void invokeFlow() throws InterruptedException, MuleException {

		MuleClient client = muleContext.getClient();

		// write file to source destination

		try {
			client.dispatch("file://" + ROOT_PATH + "src?connector=input", "Some Text File", null);

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
		File f = new File(ROOT_PATH + "target");

		String arr[] = f.list();

		Assert.assertNotNull(arr);
		Assert.assertEquals("Array Length should be 1", 1, arr.length);

	}
}
