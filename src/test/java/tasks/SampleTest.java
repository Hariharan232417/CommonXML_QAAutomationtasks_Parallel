package tasks;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class SampleTest extends SetUp{
	
	
	@Test
	public void test001()
	{
		SamplePageClass p1 = new SamplePageClass();
		p1.enterText();
	}

}
