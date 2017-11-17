package winiumcases;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;



public class winiumcase1 {
	WiniumDriver driver = null;
	
	@BeforeClass
	public void setUpEnvironment() throws IOException {
		DesktopOptions options = new DesktopOptions();
		options.setApplicationPath("C:\\Program Files\\Notepad++\\notepad++.exe");
		
		
		try {
			driver = new WiniumDriver(new URL("http://localhost:9999"),options);
		}catch(MalformedURLException e) {
			
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNotePadApplication() throws InterruptedException {
		//Thread.sleep(1000);
		
		driver.findElementByClassName("Scintilla").sendKeys("Esto es una prueba");
		
	}
	
	@AfterClass
	public void tearDown() throws IOException {
		driver.close();
	}

}
