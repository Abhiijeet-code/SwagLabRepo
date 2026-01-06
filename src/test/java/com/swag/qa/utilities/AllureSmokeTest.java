package com.swag.qa.utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Attachment;

public class AllureSmokeTest {
	
	
	    @Attachment(value = "Smoke Screenshot", type = "image/png")
	    public byte[] takeScreenshot(WebDriver driver) {
	        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	    }

	    @Test
	    public void allureScreenshotTest() {
	        WebDriver driver = new ChromeDriver();
	        driver.get("https://www.google.com");

	        takeScreenshot(driver); // ← DIRECT attachment

	        driver.quit();
	        Assert.fail("Force failure");
	    }
	}


