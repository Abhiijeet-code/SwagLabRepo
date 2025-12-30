package testcases;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.swag.qa.base.TestBase;
import com.swag.qa.pages.LoginPage;
import com.swag.qa.utilities.TestUtil;

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
	public void loginTitle() {
		String title = loginPage.LoginTitle();
		Assert.assertEquals(title, "Swag Labs");
	}

	@Test(priority = 2)
	public void Logo() {
		boolean flag = loginPage.AppLogo();
		Assert.assertTrue(flag);
	}

	@Test(priority = 3, dataProvider = "loginData", dataProviderClass = LoginPage.class)
	public void loginTest(String username, String password) {
		loginPage.login(username, password);

	}

	@AfterMethod()
	public void teardown(ITestResult result) {
		
		TestUtil.handleTestFailure(driver, result);
		driver.quit();
	}

}
