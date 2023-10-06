package tasks;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;

public class SetUp {

	
	public ChromeDriver driver;
	
	@BeforeMethod
	public void openBrowser()
	{
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		driver.get("https://www.netmeds.com/");
		
		
	}
}
