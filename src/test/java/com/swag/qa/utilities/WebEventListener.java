package com.swag.qa.utilities;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import com.swag.qa.base.TestBase;

public class WebEventListener extends TestBase implements WebDriverListener {

	  @Override
	    public void beforeGet(WebDriver driver, String url) {
	        System.out.println("Before navigating to: " + url);
	    }

	    @Override
	    public void afterGet(WebDriver driver, String url) {
	        System.out.println("Navigated to: " + url);
	    }

	    @Override
	    public void beforeClick(WebElement element) {
	        System.out.println("Trying to click on: " + element);
	    }

	    @Override
	    public void afterClick(WebElement element) {
	        System.out.println("Clicked on: " + element);
	    }

	    @Override
	    public void beforeSendKeys(WebElement element, CharSequence... keys) {
	        System.out.println("Sending keys to: " + element);
	    }

	    @Override
	    public void afterSendKeys(WebElement element, CharSequence... keys) {
	        System.out.println("Keys sent to: " + element);
	    }

	    @Override
	    public void beforeFindElement(WebDriver driver, By locator) {
	        System.out.println("Trying to find element: " + locator);
	    }

	    @Override
	    public void afterFindElement(WebDriver driver, By locator, WebElement element) {
	        System.out.println("Found element: " + locator);
	    }

	    @Override
	    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
	        System.out.println("Exception occurred: " + e.getCause());
	        
	        
	        if(target instanceof WebDriver)
	        {
	        	WebDriver driver = (WebDriver) target;
	        	try {
					TestUtil.takeScreenshotAtEndOfTest(driver);
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
	        }
	    }

	/*
	 * non overridden methods of WebListener class
	 */
	public void beforeScript(String script, WebDriver driver) {
	}

	public void afterScript(String script, WebDriver driver) {
	}

	public void beforeAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void afterAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void afterAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void beforeAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub

	}

	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub

	}

	public <X> void afterGetScreenshotAs(OutputType<X> arg0, X arg1) {
		// TODO Auto-generated method stub
		
	}

	public void afterGetText(WebElement arg0, WebDriver arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	public void afterSwitchToWindow(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	public <X> void beforeGetScreenshotAs(OutputType<X> arg0) {
		// TODO Auto-generated method stub
		
	}

	public void beforeGetText(WebElement arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	public void beforeSwitchToWindow(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

}
