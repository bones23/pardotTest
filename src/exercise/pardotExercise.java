package exercise;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class pardotExercise {
	static String name = "";
	static String pName = "";
	public static void main(String args[]) throws InterruptedException{
		WebDriver driver = new FirefoxDriver();
		login(driver);
		makeName();
		createList(driver);
		uniqueNameTest(driver);
		rename(driver);
		createList(driver);
		createProspect(driver);
		verifyProspect(driver);
		sendEmail(driver);
		logout(driver);
		System.out.println("Done");
	}
	
    public static void login(WebDriver driver) {
		driver.get("http://pi.pardot.com");
        WebElement id = driver.findElement(By.name("email_address"));
        WebElement pass = driver.findElement(By.name("password"));
        WebElement button = driver.findElement(By.name("commit"));         

        id.sendKeys("pardot.applicant@pardot.com");
        pass.sendKeys("Applicant2012");
        button.submit();
        System.out.println("Login: Passed");
    }
    public static void makeName(){
    	Random rand = new Random();
    	name = "RandomName"+rand.nextInt();
    }
    public static void createList(WebDriver driver) throws InterruptedException{
    	driver.get("http://pi.pardot.com/list/create");
    	//WebElement button = driver.findElement(By.id("listxistx_link_create"));
    	//button.click();
    	Thread.sleep(5000);
    	WebElement id = driver.findElement(By.name("name"));
    	
    	id.sendKeys(name);
    	Thread.sleep(5000);
    	WebElement submit = driver.findElement(By.id("save_information"));
    	submit.submit();
    	System.out.println("Create List: Passed");
    	System.out.println(name);
    }
    public static void uniqueNameTest(WebDriver driver) throws InterruptedException{
    	driver.get("http://pi.pardot.com/list/create");
    	Thread.sleep(5000);
    	WebElement id = driver.findElement(By.name("name"));
    	id.sendKeys(name);
    	Thread.sleep(5000);
    	WebElement submit = driver.findElement(By.id("save_information"));
    	submit.submit();
    	Thread.sleep(5000);
    	if(driver.findElement(By.cssSelector("#error_for_name")).getText().equals("Please input a unique value for this field")){
    		System.out.println("Unique Name: Passed");
    	}else{
    		System.out.println("Unique Name: Failed");
    	}
    }
    public static void rename(WebDriver driver) throws InterruptedException{
    	driver.get("http://pi.pardot.com/list");
    	Thread.sleep(5000);
    	WebElement search = driver.findElement(By.cssSelector("#listx_table_filter"));
    	search.sendKeys(name);
    	Thread.sleep(5000);
    	WebElement firstOption = driver.findElement(By.linkText(name));
    	driver.get(firstOption.getAttribute("href").replace("read", "edit"));
    	
    	WebElement id = driver.findElement(By.name("name"));
    	id.clear();
    	id.sendKeys(name + "-b");
    	
    	WebElement submit = driver.findElement(By.id("save_information"));
    	submit.submit();
    	
    	System.out.println("Rename: Passed");
    }
    public static void createProspect(WebDriver driver) throws InterruptedException{
    	driver.get("http://pi.pardot.com/prospect/create");
    	driver.manage().window().maximize();
    	Thread.sleep(5000);
    	WebElement email = driver.findElement(By.cssSelector("#email"));
    	pName = name + "@name.com";
    	email.sendKeys(pName);
    	//WebElement campaign = driver.findElement(By.cssSelector("#campaign_id"));
    	new Select(driver.findElement(By.cssSelector("#campaign_id"))).selectByVisibleText("Adil Yellow Jackets");
    	new Select(driver.findElement(By.cssSelector("#profile_id"))).selectByVisibleText("Adil Yellow Jackets 1");
    	WebElement score = driver.findElement(By.cssSelector("#campaign_id"));
        score.sendKeys("1");
        WebElement plus = driver.findElement(By.xpath("//*[@id='pr_form_update']/form/div[20]/h4"));
        plus.click();
        //WebElement dropdown = driver.findElement(By.id("selUGU_chzn"));
        WebElement span = driver.findElement(By.xpath("//*[@id='pr_form_update']/form/div[20]/div/div/div/div/div"));
        span.click();
        WebElement listA = driver.findElement(By.xpath("//*[contains(@id, '_chzn')]/div/div/input"));
        listA.sendKeys(name + "-b");
        Thread.sleep(5000);
        listA.sendKeys(Keys.RETURN);
        WebElement commit = driver.findElement(By.name("commit"));
        commit.submit();
        Thread.sleep(5000);
        System.out.println("Create Prospect: Passed");
    }
    public static void verifyProspect(WebDriver driver) throws InterruptedException{
    	driver.get("http://pi.pardot.com/list");
    	Thread.sleep(5000);
    	WebElement search = driver.findElement(By.cssSelector("#listx_table_filter"));
    	search.sendKeys(name);
    	Thread.sleep(5000);
    	WebElement firstOption = driver.findElement(By.linkText(name + "-b"));
    	driver.get(firstOption.getAttribute("href"));
    	Thread.sleep(3000);
    	search = driver.findElement(By.cssSelector("#listxProspect_table_filter"));
    	search.sendKeys(pName);
    	Thread.sleep(5000);
    	try{
    		firstOption = driver.findElement(By.linkText(pName));
    		System.out.println("Verify Prospect: Passed");
    	}catch(Exception e){
    		System.out.println("Verify Prospect: Failed");
    	}
   
    	
    }
    public static void sendEmail(WebDriver driver) throws InterruptedException{
    	driver.get("https://pi.pardot.com/email/draft/edit");
    	WebElement id = driver.findElement(By.cssSelector("#name"));
    	id.sendKeys(name);
    	//WebElement campaign = driver.findElement(By.xpath("//*[@id='information_form']/div[4]/div/div/span[2]"));
    	WebElement chooseCampaign = driver.findElement(By.className("campaign_errors")).findElement(By.className("choose-asset"));
    	
    	chooseCampaign.click();
    	Thread.sleep(5000);
    	WebElement searchResults = driver.findElement(By.id("folder-contents"));
    	WebElement firstOption = searchResults.findElement(By.className("folder-list-item"));
    	firstOption.click();
    	
    	WebElement chooseAssetButton = driver.findElement(By.id("select-asset"));
    	chooseAssetButton.click();
    	WebElement radioButton = driver.findElement(By.id("email_type_text_only"));
    	radioButton.click();
    	
    	WebElement checkbox = driver.findElement(By.id("from_template"));
    	checkbox.click();
    	Thread.sleep(1000);
    	WebElement save = driver.findElement(By.id("save_information"));
    	save.click();
    	Thread.sleep(5000);
    	//System.out.println("************************");
    	WebElement send = driver.findElement(By.id("save_footer"));
    	send.click();
    	Thread.sleep(5000);
    	
    	//change url to sending
    	String url = driver.getCurrentUrl();
    	//System.out.println(url);
    	driver.get(url.replace("edit","send"));
    	
    	WebElement span = driver.findElement(By.xpath("//*[contains(@id, '_chzn')]/a/span"));
    	span.click();
    	
    	WebElement spanSearch = driver.findElement(By.xpath("//*[contains(@id, '_chzn')]/div/div/input"));
    	spanSearch.sendKeys(name + "-b");
    	Thread.sleep(5000);
    	spanSearch.sendKeys(Keys.RETURN);
    	
    	new Select(driver.findElement(By.name("a_sender[]"))).selectByVisibleText("Account Owner");
    	
    	WebElement subject = driver.findElement(By.id("subject_a"));
    	subject.sendKeys("Test");
    	
    	WebElement sendButton = driver.findElement(By.xpath("//*[@id='schedule_sidebar']/a[2]"));
    	sendButton.click();
    	System.out.println("Send Email: Passed");
    }
    public static void logout(WebDriver driver){
    	driver.get("https://pi.pardot.com/user/logout");
    	System.out.println("Logout: Passed");
    }
    
}
