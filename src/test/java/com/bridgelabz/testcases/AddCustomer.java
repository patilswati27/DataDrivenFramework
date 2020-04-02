package com.bridgelabz.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.bridgelabz.base.TestBase;
import com.bridgelabz.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author swati
 * Purpose:To get data from file and to add into account
 *
 */
public class AddCustomer extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void AddCustomer(Hashtable<String, String> data) throws InterruptedException {
		driver.findElement(By.xpath(OR.getProperty("addCustBtn_Xpath"))).click();
		Type("firstname_Css", data.get("firstname"));
		Type("lastname_Css", data.get("lastname"));
		Type("postcode_Css", data.get("postcode"));
		Click("addbtn_Xpath");
		Thread.sleep(2000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
		alert.accept();
}
}