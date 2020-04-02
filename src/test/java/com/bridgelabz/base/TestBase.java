package com.bridgelabz.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.bridgelabz.utilities.ExcelReader;
import com.bridgelabz.utilities.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author swati purpose:intialize webdriver,properties file,extentreports,
 *         excel,DB,reportNG logs:.log,log4j.properties file,logger.log
 */

public class TestBase {

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger(TestBase.class.getName());// standard logger
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "//src//test//resources//excel//testData.xlsx");
	public static WebDriverWait wait;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;

	/**
	 * purpose:To load all property file
	 * 
	 * @throws IOException when file not found
	 */
	@BeforeSuite
	public void setUp() throws IOException {
		if (driver == null) {
			Properties config = new Properties();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
					+ "//src/test//resources//properties//config.properties");
			config.load(fis);
			// logs initialization
			Date d = new Date();
			System.out.println(d.toString().replace(":", "_").replace(" ", "_"));// to generate logs for every execution
			System.setProperty("current.date", d.toString().replace(":", "_").replace(" ", "_"));
			PropertyConfigurator.configure(".//src//main//java//log4j.properties");

			log.info("config file loaded!!");

			Properties OR = new Properties();
			FileInputStream fi = new FileInputStream(
					System.getProperty("user.dir") + "//src/test//resources//properties//OR.properties");
			OR.load(fi);
			log.info("OR file loaded!!");

			if (config.getProperty("browser").equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				log.info("firefox launched ");
			} else if (config.getProperty("browser").equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.info("chrome launched");
			}

			driver.get(config.getProperty("testsiteurl"));
			log.info("Navigated to: " + config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);
		}

	}

	public void Click(String locator) {
		driver.findElement(By.xpath(OR.getProperty(locator))).click();
		test.log(LogStatus.INFO, "Clicking on: " + locator);
	}

	public void Type(String locator, String value) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		test.log(LogStatus.INFO, "Typing in: " + locator + "Entered value as :" + value);
	}

	static WebElement dropdown;

	/**
	 * @param locator selenium locator using to locate element
	 * @param value   value of particular attribute
	 */
	public void select(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}

		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

		test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as " + value);

	}

	/**
	 * To close browser connection
	 */
	@AfterSuite
	public void TearDown() {
		if (driver != null)
			driver.quit();
		log.info("Test execution completed !!!");

	}

}
