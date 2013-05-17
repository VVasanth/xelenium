
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 *Solution Name: Xelenium, powered by Selenium
 *This solution can be used to identify the Reflected Cross Site Scripting (XSS) bugs in web application
 *Please refer the attached Xelenium pdf document for the instructions about the usage of the tool.
 * @author VVK
 */

public class WebScan extends Thread {
	public WebElement txtboxes[] = new WebElement[1];
	private WebElement dum_txtboxes[] = new WebElement[1];
	public int txt_ind = 0;
	
	public WebElement chkboxes[] = new WebElement[1];
	private WebElement dum_chkboxes[]= new WebElement[1];
	public int chk_ind = 0;
	
	public WebElement rdobtn[] = new WebElement[1];
	private WebElement dum_rdobtn[]= new WebElement[1];
	public int rdo_ind = 0;
	
	public WebElement btn[] = new WebElement[1];
	private WebElement dum_btn[] = new WebElement[1];
	public int btn_ind = 0;
	
	private List<WebElement> ddown_list = null;
	public WebElement ddown[] = null;
	public int ddown_ind;
	
	private List<WebElement> frms = null;
	private int frm_ind = 0;
	
	public String url;
	public HtmlUnitDriver d1 = null;
	//public FirefoxDriver d1 = null;
	//final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
	public String atk_vctr;
	private Xelenium xel = null;
	
	WebScan(String url, String avctr, Xelenium xel1){
		this.url = url;
		this.atk_vctr = avctr;
		d1 = new HtmlUnitDriver();
                
	//	d1= new FirefoxDriver();
		d1.setJavascriptEnabled(true);
		this.xel = xel1;        
                
	}
	
		
	private void set_values(){
		int i;
		if (chk_ind!=0){
			for (i=0;i<chk_ind;i++){
				chkboxes[i].click();
			}
		}
		
		if (rdo_ind!=0){
			for (i=0;i<rdo_ind;i++){
				rdobtn[i].click();
			}
		}
		
		if (ddown_ind!=0){
			for (i=0;i<ddown_ind;i++){
				java.util.List<WebElement> allOptions = ddown[i].findElements(By.tagName("option"));
				allOptions.get(1).click();
			}
		}
	}
	
        private void set_predef_values(){
            
        }
        
        
	public void run() {
            
            try{
            
            String Url_ind = url.substring(0, 1);
            url = url.substring(1);
            Map<String,String> pre_def_values = xel.predef_urls.get(Integer.parseInt(Url_ind));
            d1.get(url);
            login();
      
                
		frms = d1.findElementsByTagName("form");
		frm_ind = frms.size();
		int frm_i;
		int btn_i;
		int txt_i;
		int txt_j;
		int txt_cnt=0;
		int btn_cnt=0;
		String teststr = "abcdef";
                String atk_vctr_ind = atk_vctr.substring(0, 1);  //Extracting attack vector index position
		atk_vctr = atk_vctr.substring(1); 
		if(frm_ind!=0){
			for (frm_i=0;frm_i<frm_ind;frm_i++){
                                login();
				scan(frms.get(frm_i));			
				if ((txt_ind!=0) && (btn_ind!=0)){
					for (btn_i=0; btn_i<btn_ind;btn_i++){
						btn_cnt = btn_cnt+1; 
						for (txt_i=0;txt_i<txt_ind;txt_i++){
							txt_cnt = txt_cnt+1;
							set_values();
							for (txt_j=0;txt_j<txt_ind;txt_j++){
								if (txt_i != txt_j){
                                                                    //Setting predefined values
                                                                        String [] pre_txt_arr = (pre_def_values.get(Integer.toString(txt_j))).split("<>");
                                                                        if(pre_txt_arr.length==2){
                                                                            teststr = mod_str(pre_txt_arr[1]);
                                                                        }
                                                                       // if (pre_txt_key.equalsIgnoreCase(atk_vctr_ind))
                                                                        
									txtboxes[txt_j].sendKeys(teststr);
								}
							}
							String txt_key = txtboxes[txt_i].getAttribute("id");
							if ((txt_key == null) || (txt_key.equalsIgnoreCase(""))) {
								txt_key = txtboxes[txt_i].getAttribute("name");
							}

							if ((txt_key == null)|| (txt_key.equalsIgnoreCase(""))){
								txt_key = "Form-" + (frm_i+1) + "<>" + "TextField-" + (txt_cnt);
							}
							
							String btn_key = btn[btn_i].getAttribute("id");
							if ((btn_key == null) || (btn_key.equalsIgnoreCase(""))) {
								btn_key = btn[btn_i].getAttribute("name");
							}
                                                        
                                                        if ((btn_key == null) || (btn_key.equalsIgnoreCase(""))) {
								btn_key = btn[btn_i].getAttribute("value");
							}
							
                                                        if ((btn_key == null)|| (btn_key.equalsIgnoreCase(""))){
								btn_key = "Form-" + (frm_i+1) + "," + "Button-" + (btn_cnt);
							}
							xel.Update_Log("Attacking the text field " + txt_key + " with the attack vector " + atk_vctr);			                
							txt_key = Integer.toString(txt_cnt) + Integer.toString(btn_cnt) + atk_vctr_ind + txt_key + "<>" + btn_key ;  //attaching index # of text field with the text key
							d1.atk_ind = txt_key.substring(0, 3);
                                                        
							txtboxes[txt_i].sendKeys(atk_vctr);
							
							//System.setOut(new PrintStream(myOut));
							btn[btn_i].click();
			
							xel.stdOput = xel.myOut.toString();
                                                        String alrt_msg = atk_vctr.substring(atk_vctr.indexOf("alert(") + 6,atk_vctr.indexOf(")"));
                                                        
							if (xel.stdOput.contains(alrt_msg + d1.atk_ind)){
								xel.map.put(txt_key, "1");	
							}else{
								xel.map.put(txt_key, "0");
							}
							
							//if (frm_i==1){
							//Alert a1 = d1.switchTo().alert();
							//a1.accept();
							//}
							
							reset();
							d1.get(url);
                                                        login();
							frms = d1.findElementsByTagName("form");
							scan(frms.get(frm_i));
						}
						reset();
					}
				}
				reset();
			}
		}
                //d1.kill();
                d1.quit();
            }
            catch(Exception ex){
                xel.Update_Log("Exception occurred." + ex.toString());
            }
	}
	
        private void login(){
            String[] creds;
            String uname;
            String pwd;	
            String login_url;
            int pwd_index;
            int uname_index;
            if (!((xel.logincreds).equalsIgnoreCase(""))) {
                creds = ((xel.logincreds).split("<>"));
                uname = creds[0];
                pwd = creds[1];
                try{
                WebElement login_pwd = d1.findElementByXPath("//input[@type='password']");
                if (!login_pwd.equals(null)){
                    login_url = d1.getCurrentUrl();
                    if(!(url.equalsIgnoreCase(login_url))){
                        List<WebElement> inp_elems = d1.findElementsByTagName("input");
                        for (int i=0;i<inp_elems.size();i++){
                            if ((inp_elems.get(i).getAttribute("type").equalsIgnoreCase("password"))){
                               inp_elems.get(i-1).sendKeys(uname);
                               inp_elems.get(i).sendKeys(pwd);
                               inp_elems.get(i).submit();
                               d1.get(url);
                               break;
                            }
                        }
                    }
                }
                }
                catch(org.openqa.selenium.NoSuchElementException logexp){
                    //ignore the login exception, if the page does not contain password field
                }

                
            }
        }
        
        private String mod_str(String testStr) {
             ArrayList<String> astr = new ArrayList<String>();
                  String actStr = "";
                  int sind = 0;
                  int eind = 0;
                  sind = testStr.indexOf("<",eind);
                  if((sind!=0)&&(sind!=-1)){
                      astr.add(testStr.substring(0, sind));
                  }
                  
                 while(sind!=-1){
                    sind = testStr.indexOf("<",eind);
                    if((sind!=eind+1) && (eind!=0) && (sind!=-1)){
                        astr.add(testStr.substring(eind+1,sind));
                    }
                    if (sind!=-1){
                        eind = testStr.indexOf(">", sind);
                        if(eind>sind){
                        astr.add(testStr.substring(sind+1, eind));
                        }
                    }
                  }
                 
                 if((eind!=testStr.length())&&(eind!=0)){
                     astr.add(testStr.substring(eind+1));
                 }
                 
                 if(eind==0){
                     astr.add(testStr);
                 }
                 
                 for(String partStr:astr){
                     String [] a_partStr = partStr.split(",");
                     if (a_partStr[0].equalsIgnoreCase("str")){
                         String str_len = "6";
                         if(a_partStr.length==2){
                             str_len = a_partStr[1];
                         }else if(a_partStr.length>2){
                             //error message
                             
                             xel.Update_Log("Predefined string " +  partStr +" mentioned under the url : " + url + " is invalid");
                             //throw new InterruptedException();
                             continue;
                         }
                            StringBuffer sb = new StringBuffer();  
                            for (int x = 0; x < Integer.parseInt(str_len); x++)  
                                {  
                                    sb.append((char)((int)(Math.random()*26)+97));  
                                }  
                            actStr = actStr + sb.toString();
                     }else if (a_partStr[0].equalsIgnoreCase("num")){
                         //<int,no of digits, greater than, lesser than>
                         int max_val = 0;
                         int min_val = 0;
                         if(a_partStr.length==2){
                             min_val = (int) Math.pow(Double.parseDouble(Integer.toString(10)),Double.parseDouble(a_partStr[1]));
                             max_val = ((int) Math.pow(Double.parseDouble(Integer.toString(10)),Double.parseDouble(Integer.toString(Integer.parseInt(a_partStr[1]))+1))) - 1;
                         }else if (a_partStr.length==3){
                             min_val = Integer.parseInt(a_partStr[1]);
                             max_val = Integer.parseInt(a_partStr[2]);
                         }else if ((a_partStr.length>3)||(a_partStr.length==1)){
                             //error message
                             xel.Update_Log("Predefined string " +  partStr +" mentioned under the url : " + url + " is invalid");
                             continue;
                         }
                                                  
                         Random rand = new Random();
                         int gen_num = rand.nextInt(max_val) - min_val + 1;
                         actStr = actStr + Integer.toString(gen_num);
                     }else{
                         actStr = actStr + partStr;
                     }
                 }
                 return actStr;
                  
	
        }
        
	private void reset(){
		txt_ind = 0;
		rdo_ind = 0;
		chk_ind = 0;
		btn_ind = 0;
		ddown_ind = 0;
		txtboxes = new WebElement[1];
		dum_txtboxes = new WebElement[1];
		chkboxes = new WebElement[1];
		dum_chkboxes = new WebElement[1];
		rdobtn = new WebElement[1];
		dum_rdobtn = new WebElement[1];
		btn = new WebElement[1];
		dum_btn = new WebElement[1];
		ddown_list = null;
		ddown = null;
		d1.manage().deleteAllCookies();		
	}
	
	
	public void scan(WebElement WbFrm){
		List<WebElement> inp_elems = WbFrm.findElements(By.tagName("input"));
		for (WebElement ele : inp_elems){
			String txt_attr = ele.getAttribute("type"); 
			if((txt_attr.equalsIgnoreCase("text"))|| (txt_attr.equalsIgnoreCase("password")))  {
				if (txt_ind==0) {
					txtboxes[0]=ele;
					txt_ind = txt_ind+1;
				}else{
				dum_txtboxes= new WebElement[txt_ind+1];
				System.arraycopy(txtboxes, 0, dum_txtboxes, 0, txtboxes.length);
				dum_txtboxes[txt_ind]=ele;
				txtboxes = new WebElement[txt_ind+1];
				System.arraycopy(dum_txtboxes, 0, txtboxes, 0, dum_txtboxes.length);
				txt_ind = txt_ind+1;
				}	
			}
			
			
			if(txt_attr.equalsIgnoreCase("checkbox")){
				if (chk_ind==0){
					chkboxes[0] = ele;
					chk_ind = chk_ind+1;
				}else{
					dum_chkboxes = new WebElement[chk_ind +1];
					System.arraycopy(chkboxes, 0, dum_chkboxes, 0, chkboxes.length);
					dum_chkboxes[chk_ind]=ele;
					chkboxes = new WebElement[chk_ind+1];
					System.arraycopy(dum_chkboxes, 0, chkboxes, 0, dum_chkboxes.length);
					chk_ind = chk_ind+1;
				}
			}
			
			if(txt_attr.equalsIgnoreCase("radio")){
				if (rdo_ind==0){
					rdobtn[0] = ele;
					rdo_ind = rdo_ind+1;
				}else{
					dum_rdobtn = new WebElement[rdo_ind +1];
					System.arraycopy(rdobtn, 0, dum_rdobtn, 0, rdobtn.length);
					dum_rdobtn[rdo_ind]=ele;
					rdobtn = new WebElement[rdo_ind+1];
					System.arraycopy(dum_rdobtn, 0, rdobtn, 0, dum_rdobtn.length);
					rdo_ind = rdo_ind+1;
				}
			}
			
			if((txt_attr.equalsIgnoreCase("submit"))||(txt_attr.equalsIgnoreCase("button"))){
				if (btn_ind==0){
					btn[0] = ele;
					btn_ind = btn_ind+1;
				}else{
					dum_btn = new WebElement[btn_ind +1];
					System.arraycopy(btn, 0, dum_btn, 0, btn.length);
					dum_btn[btn_ind]=ele;
					btn = new WebElement[btn_ind+1];
					System.arraycopy(dum_btn, 0, btn, 0, dum_btn.length);
					btn_ind = btn_ind+1;
				}
			}
			
		}
		
		List<WebElement> Tarea = WbFrm.findElements(By.tagName("textarea"));
		
		if (Tarea.size()!=0){
			for (WebElement ta:Tarea){
				dum_txtboxes= new WebElement[txt_ind+1];
				System.arraycopy(txtboxes, 0, dum_txtboxes, 0, txtboxes.length);
				dum_txtboxes[txt_ind]= ta;
				txtboxes = new WebElement[txt_ind+1];
				System.arraycopy(dum_txtboxes, 0, txtboxes, 0, dum_txtboxes.length);
				txt_ind = txt_ind+1;
			}
		}
		
                List<WebElement> Btn_tag = WbFrm.findElements(By.tagName("button"));
                
                if (Btn_tag.size()!=0){
                    for (WebElement bt:Btn_tag){
                        dum_btn = new WebElement[btn_ind +1];
                        System.arraycopy(btn, 0, dum_btn, 0, btn.length);
                        dum_btn[btn_ind]=bt;
                        btn = new WebElement[btn_ind+1];
                        System.arraycopy(dum_btn, 0, btn, 0, dum_btn.length);
                        btn_ind = btn_ind +1;
                    }
                }
                
		ddown_list = d1.findElementsByTagName("select");
		ddown_ind = ddown_list.size();	
		
		if (ddown_ind!=0) {
		ddown = new WebElement[ddown_list.size()];
		int i=0;
		for (WebElement e1:ddown_list){
			ddown[i]=e1;
			i=i+1;
		}
	}
	}
}
