import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class test_try {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			HtmlUnitDriver d1 = new HtmlUnitDriver("xelenium1");			
			//FirefoxDriver d1 = new FirefoxDriver();
			d1.setJavascriptEnabled(true);
			//d1.get("http://www.open2test.org");
			d1.get("file:///C:/Users/vvelayudham/Desktop/Xel_button.html");
			System.out.println(d1.getCurrentUrl());
			System.out.println(d1.getTitle());
			List<WebElement> b1 = d1.findElementsByTagName("button");
			b1.get(0).click();
	}

}
