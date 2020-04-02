package com.bridgelabz.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.bridgelabz.base.TestBase;

public class LoginTest extends TestBase {
	@Test
	public void loginTest() throws InterruptedException {
		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn_CSS"))).click();
		WebElement we = driver.findElement(By.xpath(OR.getProperty("addCustBtn_Xpath")));
		Thread.sleep(10);
		Assert.assertTrue(we.isDisplayed(),"Login not successful");
											
	}

}
