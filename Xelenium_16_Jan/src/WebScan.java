
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

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
                
		//d1= new FirefoxDriver();
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
	
	public void run(){
            
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
				scan(frms.get(frm_i));			
				if ((txt_ind!=0) && (btn_ind!=0)){
					for (btn_i=0; btn_i<btn_ind;btn_i++){
						btn_cnt = btn_cnt+1; 
						for (txt_i=0;txt_i<txt_ind;txt_i++){
							txt_cnt = txt_cnt+1;
							set_values();
							for (txt_j=0;txt_j<txt_ind;txt_j++){
								if (txt_i != txt_j){
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

							if ((btn_key == null)|| (btn_key.equalsIgnoreCase(""))){
								btn_key = "Form-" + (frm_i+1) + "," + "Button-" + (btn_cnt);
							}
										                
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
