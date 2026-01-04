package com.swag.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.swag.qa.base.TestBase;

public class MenuPage extends TestBase{

	public void menuOption(String option) {
		driver.findElement(By.xpath("//a[contains(text(),'"+option+"')]")).click();
	}
	
	public String pageTitle()
	{
		return driver.getTitle();
	}
}
