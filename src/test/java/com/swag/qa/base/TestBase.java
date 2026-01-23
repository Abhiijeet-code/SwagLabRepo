package com.swag.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.swag.qa.utilities.TestAllureListener;
import com.swag.qa.utilities.TestUtil;
import com.swag.qa.utilities.WebEventListener;

@Listeners({TestAllureListener.class})
public class TestBase {
	
	public static Properties prop;
	public static JavascriptExecutor js;
	
	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();
	
	protected static final Logger log = LogManager.getLogger(TestBase.class);
	
	public static WebDriver getDriver()
	{
		return tdriver.get();
	}
	
	public static void setDriver(WebDriver driverRef) {
	    tdriver.set(driverRef);
	}
	
	
	public TestBase()
	{
		try
		{
			prop = new Properties();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\config\\configuration.properties");
			
			prop.load(fis);
		
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	@SuppressWarnings({ })
	public static void initialization()
	{
		WebDriver driver =null;
		
		String browserName = prop.getProperty("browser");
		if(browserName.equals("chrome"))
		{
			Map<String, Object> chromeprefs = new HashMap();
			chromeprefs.put("credentials_enable_service", false);
			chromeprefs.put("profile.password_manager_enabled", false);
			chromeprefs.put("profile.password_manager_leak_detection", false);
			ChromeOptions option = new ChromeOptions();
			option.setExperimentalOption("prefs", chromeprefs);
			driver = new ChromeDriver(option);
		}
		
		driver =new EventFiringDecorator<>(new WebEventListener()).decorate(driver);
		
		setDriver(driver);
		
		js = (JavascriptExecutor)getDriver();
		
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		
		getDriver().get(prop.getProperty("url"));
		
	}
	
	@BeforeSuite
	public void startReport() {

	    ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");

	    TestUtil.extent = new ExtentReports();
	    TestUtil.extent.attachReporter(spark);
	}

	@AfterSuite
	public void endReport() {
	    TestUtil.extent.flush();
	}

}
