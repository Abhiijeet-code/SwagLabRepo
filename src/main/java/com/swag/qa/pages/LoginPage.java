package com.swag.qa.pages;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;

import com.swag.qa.base.TestBase;
import com.swag.qa.utilities.TestUtil;

public class LoginPage extends TestBase{

	//Page Factory : OR
	@FindBy(name = "user-name")
	@CacheLookup   						//➡ It tells Selenium to cache the WebElement after the first lookup
										//	➡ Next time, Selenium reuses the same element reference instead of finding it again

										//	🔴 Main Disadvantages of @CacheLookup
										//	1️⃣ StaleElementReferenceException (BIGGEST ISSUE)

										//	If the page is refreshed

										//	If DOM is reloaded

										//	If AJAX updates the element

										//	➡ Cached element becomes stale

	WebElement username;
	
	@FindBy(name="password")
	WebElement password;
	
	@FindBy(xpath = "//input[@type='submit']")
	WebElement loginBtn;
	
	@FindBy(xpath= "//div[contains(@class, 'login_logo')]")
	WebElement SwagLogo;
	
	//Initializing the OR
	public LoginPage()
	{
		PageFactory.initElements(driver, this);
	}
	
	public String LoginTitle() {
		return driver.getTitle();
	}

	public boolean AppLogo()
	{
		return SwagLogo.isDisplayed();
	}
	
	public HomePage login(String un, String pwd)
	{
		username.sendKeys(un);
		password.sendKeys(pwd);
		//loginBtn.click();
		
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		js.executeScript("arguments[0].click();",loginBtn);
		
		return new HomePage();
	}
	
	@DataProvider(name = "loginData")
	public Object[][] getLoginData() throws FileNotFoundException, IOException
	{
		return TestUtil.getData("Login");
	}
}
