package com.pack.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import com.pack.common.pageObjects.dbMethods;


public class testbasesetup extends dbMethods{
	public WebDriver driver;
	static String driverPath = "D:\\Shiv\\Selenium\\Selenium jars\\driverpath\\";

	public WebDriver getDriver() {
		return driver;
	}

	private void setDriver(String browserType, String appURL) {
		switch (browserType) {
		case "chrome":
			driver = initChromeDriver(appURL);
			break;
		case "firefox":
			driver = initFirefoxDriver(appURL);
			break;
		default:
			System.out.println("browser : " + browserType
					+ " is invalid, Launching Firefox as browser of choice..");
			driver = initFirefoxDriver(appURL);
		}
	}

	private static WebDriver initChromeDriver(String appURL) {
		System.out.println("Launching google chrome with new profile..");
		System.setProperty("webdriver.chrome.driver", driverPath
				+ "chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	private static WebDriver initFirefoxDriver(String appURL) {
		System.out.println("Launching Firefox browser..");
		System.setProperty("webdriver.chrome.driver", driverPath
				+ "geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	/**
     * Creating new data base and table
     */
	@Override
	public void createNewDatabase(String fileName) {
		// TODO Auto-generated method stub
		super.createNewDatabase(fileName);
	}
    @Override
    public void createNewTable() {
    	// TODO Auto-generated method stub
    	super.createNewTable();
    }
    /**
     * Deleting existing data in database if any
     */
    @Override
    public void delete() {
    	// TODO Auto-generated method stub
    	super.delete();
    }
	@Parameters({ "browserType", "appURL" })
	@BeforeClass
	public void initializeTestBaseSetup(String browserType, String appURL) {
		try {
			setDriver(browserType, appURL);
			System.out.println("List of top 250 movies is found");

		} catch (Exception e) {
			System.out.println("Error....." + e.getStackTrace());
		}
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
	

