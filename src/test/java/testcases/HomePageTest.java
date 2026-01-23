package testcases;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;

import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.swag.qa.base.TestBase;
import com.swag.qa.pages.CartPage;
import com.swag.qa.pages.HomePage;
import com.swag.qa.pages.LoginPage;
import com.swag.qa.pages.MenuPage;
import com.swag.qa.utilities.TestAllureListener;
import com.swag.qa.utilities.TestUtil;

import io.qameta.allure.*;

@Listeners({TestAllureListener.class})
public class HomePageTest extends TestBase {

	LoginPage loginpage;
	HomePage homepage;
	MenuPage menupage;
	CartPage cartpage;
	String beforeSorting = null;

	WebDriverWait wait;

	public HomePageTest() {
		super();
	}

	@BeforeMethod()
	public void setup(Method method) {
		initialization();

		wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));

		loginpage = new LoginPage();
		menupage = new MenuPage();
		cartpage = new CartPage();
		homepage = loginpage.login(prop.getProperty("username"), prop.getProperty("password"));

		// wait.until(ExpectedConditions.visibilityOfAllElements(homepage.inventorylist));

		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
				.equals("complete"));

		TestUtil.extentTest = TestUtil.extent.createTest(method.getName());

	}

	@Test(priority = 1)
	@Story("Verify Title")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify title of the page")
	public void checkTitle() {
		String titleExp = homepage.homeTitle();
		Assert.assertEquals(titleExp, "Swag Labs");
		TestAllureListener.saveScreenshotPNG(getDriver());
	}

	@Test(priority = 2)
	@Story("Verify List Count")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify total no. of items on the page")
	public void checkListCount() {
		String listCount = Integer.toString(homepage.inventoryList());

		Assert.assertEquals(listCount, "6");
		TestAllureListener.saveScreenshotPNG(getDriver());
	}

	@Test(priority = 3)
	@Story("Verify Sorting")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify the items are sorted correctly")
	public void checksortedList() {

		beforeSorting = homepage.sortedItem();

		homepage.sort("za");

		String afterSorting = homepage.sortedItem();

		Assert.assertNotEquals(beforeSorting, afterSorting);

	}

	@DataProvider(name = "CartData")
	public Object[][] getCart() throws FileNotFoundException, IOException {
		return new Object[][] { TestUtil.getRowData("Home") };
	}

	@Test(priority = 4, dataProvider = "CartData")
	@Story("Add items to Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Adding items to cart")
	public void addToCart(String[] ItemToAdd) {
		for (String item : ItemToAdd) {
			homepage.addToCart(item);
		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) {

		TestUtil.handleScreenshot(getDriver(), result);

		 
		getDriver().quit();
		    
	}
	
	
}
