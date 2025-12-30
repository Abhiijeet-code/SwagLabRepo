package testcases;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;

import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import com.swag.qa.base.TestBase;
import com.swag.qa.pages.CartPage;
import com.swag.qa.pages.HomePage;
import com.swag.qa.pages.LoginPage;
import com.swag.qa.pages.MenuPage;
import com.swag.qa.utilities.TestUtil;

public class HomePageTest extends TestBase{
	
	LoginPage loginpage;
	HomePage homepage;
	MenuPage menupage;
	CartPage cartpage;
	String beforeSorting = null;
	
	WebDriverWait wait;
	
	public HomePageTest()
	{
		super();
	}
	
	@BeforeMethod()
	public void setup(Method method)
	{
		initialization();
		
		wait = new WebDriverWait(driver ,Duration.ofSeconds(20));
		
		loginpage = new LoginPage();
		menupage = new MenuPage();
		cartpage = new CartPage();
		homepage = loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		
		//wait.until(ExpectedConditions.visibilityOfAllElements(homepage.inventorylist));
		
		wait.until(webDriver ->((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
		
		TestUtil.extentTest = TestUtil.extent.createTest(method.getName());
		
	}
	
	
	@Test(priority=1)
	public void checkTitle()
	{
	    String titleExp = homepage.homeTitle();
		Assert.assertEquals(titleExp, "Swag Labs");
		
		
	}
	
	@Test(priority =2)
	public void checkListCount()
	{
		String listCount =Integer.toString(homepage.inventoryList());
		
		Assert.assertEquals(listCount, "6");
	}
	
	
	@Test(priority =3)
	public void checksortedList()
	{
		
		beforeSorting = homepage.beforeSorting();
		
		homepage.sort("za");
		
		String afterSorting = homepage.afterSorting();
		
		Assert.assertNotEquals(beforeSorting , afterSorting);
		
	}
	
	@DataProvider(name = "CartData")
	public Object[][] getCart() throws FileNotFoundException, IOException
	{
		return TestUtil.getData("Home");
	}
	
	@Test(priority = 4, dataProvider = "CartData")
	public void addToCart(String ItemToAdd)
	{
		homepage.addToCart(ItemToAdd);
	}
	
	
	@AfterMethod
	public void tearDown(ITestResult result) {

	    
	        TestUtil.handleTestFailure(driver, result);

	    driver.quit();
	}
}
