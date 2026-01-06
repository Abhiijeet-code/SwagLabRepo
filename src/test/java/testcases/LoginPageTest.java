package testcases;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;

import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.swag.qa.base.TestBase;
import com.swag.qa.pages.LoginPage;
import com.swag.qa.utilities.TestAllureListener;
import com.swag.qa.utilities.TestUtil;

import io.qameta.allure.*;

@Listeners({TestAllureListener.class})
public class LoginPageTest extends TestBase {

	LoginPage loginPage;

	public LoginPageTest() {
		super();
	}

	@BeforeMethod
	public void setup(Method method) {
		initialization();
		loginPage = new LoginPage();
		TestUtil.extentTest = TestUtil.extent.createTest(method.getName());
	}


	@Test(priority = 1)
	@Story("Verify Title")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify title of the page")
	public void loginTitle() {
		String title = loginPage.LoginTitle();
		Assert.assertEquals(title, "Swag Labs");
	}

	@Test(priority = 2)
	@Story("Verify Logo")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify Logo of the application")
	public void Logo() {
		boolean flag = loginPage.AppLogo();
		Assert.assertTrue(flag);
	}

	@DataProvider(name = "loginData")
	public Object[][] getLoginData() throws FileNotFoundException, IOException
	{
		return TestUtil.getData("Login");
	}
	@Test(priority = 3, dataProvider = "loginData")
	@Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify Login with correct credentials")
	public void loginTest(String username, String password) {
		loginPage.login(username, password);
		TestAllureListener.saveScreenshotPNG(driver);	
	}

	@AfterMethod(alwaysRun = true)
	public void teardown(ITestResult result) {
		
		TestUtil.handleScreenshot(driver, result);

		 
		        driver.quit();
		    
	}

}
