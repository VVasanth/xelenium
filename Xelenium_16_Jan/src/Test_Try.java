

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;



public class Test_Try {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		// BufferedReader br = new BufferedReader(
		  //          new FileReader("ShowGeneratedHtml.java"));

		        File f = new File("source.htm");
		        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		        bw.write("<html><head><title>Results</title></head>");
		        bw.write("<style>* { font-family: arial; }div.title { font-size:3em;font-weight: bold;padding-top: 24px;padding-left: 25px;}");
		        bw.write("h2{padding-left: 5px;margin-bottom: 0px;padding-bottom: 0;background-color: #001f47;color:#fff;border: 1px solid black;text-align:center;}");
		        bw.write("h3{padding-left: 5px;margin-top:5px;margin-bottom: 0px;font-size:1em;padding-bottom: 0;background-color: #B0C4DE;color:#000000;border: 1px solid black;text-align:left;}");
		        bw.write("div.url{padding-left: 5px;margin-top:5px;margin-right:0px;margin-bottom: 5px;background-color:#778899;color:#000000;border: 1px solid black;text-align:left;}");
		        bw.write("div.scan{padding-left: 5px;margin-right:5px;margin-top:5px;margin-bottom: 5px;background-color:#D6DBE0;color:#000000;border: 1px solid black;text-align:left;}");
		        bw.write(".fail {background-color: #C00000;border: 1px solid black;color: #fff;}");
		        bw.write(".pass {background-color:#00B050;border: 1px solid black;}");
		        bw.write("td.bar {width: 99%;background-color: #dddddd;border: 1px solid black;padding: 0;margin: 0;text-align: center;}");
		        bw.write("h4{padding-left: 5px;margin-top:5px;margin-bottom: 0px;margin-right:5px;margin-bottom:5px;font-size:1em;padding-bottom: 0;background-color: #001f47;color:#fff;border: 1px solid black;text-align:left;}</style>");
		        bw.write("<BODY>");
		        //bw.write("<div class='title'>Test Results</div>");
		        bw.write("<h2>Xelenium</h2>");
		        bw.write("<h3>XSS(Cross Site Scripting) Scanner Results</h3>");
		        Date date = new Date();
		        bw.write("<h3>Scan performed on : " + date.toString() + "</h3>");
		        //bw.write("<TABLE BORDER=1 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
	            //bw.write("<TR><TD BGCOLOR=#66699 WIDTH=27% align=\"center\"><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>XSS Scanner Results</B></FONT></TD></TR>");
		        bw.write("<div class='url'> URL : http://demo.testfire.net");
		        bw.write("<div class='scan'>Text Field : txtSearch, Button : Search");
		        bw.write("<h4> Test Summary (No of tests executed : 1)</h4>");
		        bw.write("<table style='width: 100%'><tbody><tr><td nowrap='nowrap'>Failures:</td><td class='bar'><div style='width:" +Integer.toString((0/(0+1+0))*100)+"% ;'class='fail'>0</div></td></tr>");
		        bw.write("<tr><td nowrap='nowrap'>Warnings:</td><td class='bar'><div style='width: 0%;'class='warn'>0</div></td></tr>");
		        bw.write("<tr><td>Passes:</td><td class='bar'><div style='width:" + Integer.toString((1/(0+1+0))*100) + "%;'class='pass'>1</div></td></tr></tbody></table>");
		        //bw.write("<h4>Button : Search</h4></div>");
	           // bw.write("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
               // bw.write("<TABLE BORDER=0 BGCOLOR=BLACK CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
               // bw.write("<TR COLS=2><TD BGCOLOR=#FFCC99 WIDTH=50%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Page URL</B></FONT></TD><TD BGCOLOR=#FFCC99 WIDTH=50%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>"+ "URL" + "</B></FONT></TD></TR>");
                
		     // 
		     //   String line;
		      //  while ((line=br.readLine())!=null) {
		           // bw.write(line);
		          //  bw.newLine();
		        //}

		        //bw.write("</text" + "area>");
		        bw.write("</body>");
		        bw.write("</html>");

		    //    br.close();
		        bw.close();

		        Desktop.getDesktop().browse(f.toURI());

	}

}
