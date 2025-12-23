package testcases;


import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.swag.qa.base.TestBase;
import com.swag.qa.pages.CartPage;
import com.swag.qa.pages.HomePage;
import com.swag.qa.pages.LoginPage;
import com.swag.qa.pages.MenuPage;

public class HomePageTest extends TestBase{
	
	LoginPage loginpage;
	HomePage homepage;
	MenuPage menupage;
	CartPage cartpage;
	String beforeSorting = null;
	
	public HomePageTest()
	{
		super();
	}
	
	@BeforeMethod()
	public void setup()
	{
		initialization();
		loginpage = new LoginPage();
		menupage = new MenuPage();
		cartpage = new CartPage();
		homepage = loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@Test(priority=1)
	public void checkTitle()
	{
	    String titleExp = homepage.homeTitle();
		Assert.assertEquals(titleExp, "Swag Labs");
		
		beforeSorting = homepage.beforeSorting();
	}
	
	@Test(priority =2)
	public void checkListCount()
	{
		String listCount =Integer.toString(homepage.inventoryList());
		
		Assert.assertEquals(listCount, "6");
	}
	
	@Test(priority =3)
	public void sortList()
	{
		homepage.sort("za");
	}
	
	@Test(priority =4)
	public void checkortesList()
	{
		
		String afterSorting = homepage.afterSorting();
		
		Assert.assertNotEquals(beforeSorting , afterSorting);
		
	}
	
//	@Test(priority = 5)
//	public void popUp()
//	{
//		driver.switchTo().alert().accept();
//	}
	
	@AfterMethod()
	public void teardown()
	{
		driver.quit();
	}

}
