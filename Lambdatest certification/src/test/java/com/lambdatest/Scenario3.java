package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class Scenario3 {

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

        String[] Tags = new String[] { "Feature", "InputForm", "Submission" };
        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
    }

    public void inputFormSubmitTest() throws InterruptedException {
        
        // Open the URL
        System.out.println("Loading Url");
        driver.get("https://www.lambdatest.com/selenium-playground");

        // Click on "Input Form Submit"
        System.out.println("Clicking on input form submit");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement inputFormLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Input Form Submit")));
        inputFormLink.click();
        Thread.sleep(3000);
        
        // Click "Submit" without filling the form
        System.out.println("Clicking on submit");
        driver.findElement(By.xpath("//button[text()='Submit']")).click();
        Thread.sleep(3000);
        WebElement name = driver.findElement(By.xpath("//div[@class='form-group w-4/12 smtablet:w-full text-section pr-20 smtablet:pr-0']/input[@type='text']"));
        String Expected_validation = name.getAttribute("validationMessage");
        System.out.println(Expected_validation);
        Assert.assertEquals(Expected_validation, "Please fill out this field.", "Error message is not as expected.");     

        // Fill in the fields
        System.out.println("filling fields");
        driver.findElement(By.name("name")).sendKeys("John Doe");
        driver.findElement(By.id("inputEmail4")).sendKeys("123@gmail.com");
        driver.findElement(By.id("inputPassword4")).sendKeys("Test123");
        driver.findElement(By.id("company")).sendKeys("123@gmail.com");
        driver.findElement(By.id("websitename")).sendKeys("123@gmail.com");
        
        // Select "United States" from the Country drop-down
        System.out.println("selecting country");
        Select countryDropdown = new Select(driver.findElement(By.xpath("//div[@class='form-group w-6/12 smtablet:w-full pr-20 smtablet:pr-0']/select[@name='country']")));
        countryDropdown.selectByVisibleText("United States");
        driver.findElement(By.id("inputCity")).sendKeys("Sangareddy");
        driver.findElement(By.id("inputAddress1")).sendKeys("1-23");
        driver.findElement(By.id("inputAddress2")).sendKeys("1-23");
        driver.findElement(By.id("inputState")).sendKeys("1-23");
        driver.findElement(By.id("inputZip")).sendKeys("502001");
        
        
        // Click "Submit" after filling all fields
        System.out.println("Clicking on submit");
        driver.findElement(By.xpath("//button[text()='Submit']")).click();

        // Validate the success message
        System.out.println("validating success message");
        String successMessage = driver.findElement(By.xpath(".//p[@class='success-msg hidden']")).getText();
        Assert.assertEquals(successMessage, "Thanks for contacting us, we will get back to you shortly.", "Success message is not as expected.");

        System.out.println("Form submitted successfully.");

        // Mark the test as passed
        Status = "passed";
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
