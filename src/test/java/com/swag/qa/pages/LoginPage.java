package com.swag.qa.pages;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.swag.qa.base.TestBase;

import io.qameta.allure.Step;



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
	
	@Step("Get title of the page")
	public String LoginTitle() {
		return driver.getTitle();
	}

	@Step("Verify logo of the page")
	public boolean AppLogo()
	{
		return SwagLogo.isDisplayed();
	}
	
	@Step("Login with credential username : {0} and Password : {1}") // 0 and 1 defines the arguments from method
	public HomePage login(String un, String pwd)
	{
		username.sendKeys(un);
		password.sendKeys(pwd);
		//loginBtn.click();
		
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		js.executeScript("arguments[0].click();",loginBtn);
		
		return new HomePage();
	}
	
	
}
