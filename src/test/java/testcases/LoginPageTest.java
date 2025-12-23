package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.swag.qa.base.TestBase;
import com.swag.qa.pages.LoginPage;

public class LoginPageTest extends TestBase {

	LoginPage loginPage;
	
	public LoginPageTest()
	{
		super();
	}
	
	@BeforeMethod()
	public void setup()
	{
		initialization();
		loginPage = new LoginPage();
	}
	
	@Test(priority = 1)
	public void loginTitle()
	{
		String title = loginPage.LoginTitle();
		Assert.assertEquals(title, "Swag Labs");
	}
	
	@Test(priority = 2)
	public void Logo()
	{
		boolean flag = loginPage.AppLogo();
		Assert.assertTrue(flag);
	}
	
	@Test(priority = 3 , dataProvider = "loginData" , dataProviderClass = LoginPage.class)
	public void loginTest(String username, String password)
	{
		loginPage.login(username, password);
		
	}
	
	@AfterMethod()
	public void teardown()
	{
		driver.quit();
	}
}
