package tasks;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class SamplePageClass {
	
	ChromeDriver driver;
	
	SamplePageClass(ChromeDriver driver)
	{
		this.driver = driver;
	}

	
	SamplePageClass()
	{
		System.out.println("No arg constructor");
	}
	
	public void enterText()
	{
		driver.findElement(By.xpath("//input[@id='search']")).sendKeys("Meropenam");
	}
}
