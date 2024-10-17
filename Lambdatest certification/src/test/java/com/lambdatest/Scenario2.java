package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Scenario2 {

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
    public void dragAndDropSliderTest() throws InterruptedException {
        
    	  // Open the URL
        System.out.println("Loading Url");
        driver.get("https://www.lambdatest.com/selenium-playground");
        driver.findElement(By.xpath("(//a[@class='text-black text-size-14 hover:text-lambda-900 leading-relaxed'])[13]")).click();
		Thread.sleep(4000);
		Actions obj= new Actions(driver);
		obj.dragAndDropBy(driver.findElement(By.xpath("(//input[@type='range'])[3]")), 215, 0).perform();
		
		String value=driver.findElement(By.xpath("//output[text()='95']")).getText();
		System.out.println(value);
		Assert.assertEquals(value, "95");
		System.out.println("sussesful");
        
        // Mark the test as passed
        Status = "passed";
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }

}
