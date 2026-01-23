package com.swag.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.swag.qa.base.TestBase;

public class MenuPage extends TestBase{

	public void menuOption(String option) {
		getDriver().findElement(By.xpath("//a[contains(text(),'"+option+"')]")).click();
	}
	
	public String pageTitle()
	{
		return getDriver().getTitle();
	}
}
