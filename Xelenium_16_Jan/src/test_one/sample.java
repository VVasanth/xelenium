package test_one;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class sample {

	public static void main(String [] args) {
		// TODO Auto-generated method stub
		HtmlUnitDriver d1 = new HtmlUnitDriver();
		d1.atk_ind = "test";
		//FirefoxDriver d1 = new FirefoxDriver();
        d1.setJavascriptEnabled(true);
        //d1.get("file:///C:/Users/VVK/Documents/VVK/Selenium/Xelenium/button_tag.html");
        d1.get("http://demo.testfire.net");
        d1.findElement(By.xpath("//*[@id='txtSearch']")).sendKeys("<script>alert(123)</script>");
       WebElement btn = d1.findElement(By.xpath("//*[@id='frmSearch']/table/tbody/tr[1]/td[2]/input[2]"));
        //WebElement btn = d1.findElement(By.xpath("//button")); 
       btn.click();
	}
	
}
