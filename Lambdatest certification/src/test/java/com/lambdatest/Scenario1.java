package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Scenario1 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "sindhuja.pathuri6" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "ZuYdSPebUCMNacytGObX5VGfxR1yM9xfhJiiuBcBDrMDEZ7dyO" : System.getenv("LT_ACCESS_KEY");

        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("browserName", "chrome");
        caps.setCapability("version", "latest");
        caps.setCapability("build", "TestNG With Java");
        caps.setCapability("name", m.getName() + this.getClass().getName());
        caps.setCapability("plugin", "git-testng");

        String[] Tags = new String[] { "Feature", "DragAndDrop", "Slider" };
        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
    }

    @Test
    public void Simpleformdemo() throws InterruptedException {
        
        // Open the URL
        System.out.println("Loading Url");
        driver.get("https://www.lambdatest.com/selenium-playground");
        driver.findElement(By.xpath("//a[text()='Simple Form Demo']")).click();
    	Thread.sleep(4000);    
    	String url=driver.getCurrentUrl();
    	Thread.sleep(4000);     
    	Assert.assertTrue(url.contains("simple-form-demo"));
    	Thread.sleep(4000);
    	String input="Welcome to Lambda Test";
    	Thread.sleep(4000);
    	driver.findElement(By.xpath("//input[@id=\"user-message\"]")).sendKeys(input);
    	Thread.sleep(4000);
    	driver.findElement(By.xpath("//button[text()='Get Checked Value']")).click();
    	Thread.sleep(4000);
    	String text=driver.findElement(By.xpath("//p[@id=\"message\"]")).getText();
    	Assert.assertEquals(text, input);  
    	System.out.println("u r done...."); 
    	Status = "passed";
    	
      
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }

}