package useractions;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.github.bonigarcia.wdm.WebDriverManager;
import managers.filereader.Filereader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import utilities.DateUtils;
import utilities.ExtentReportManger;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseUI {

    public static WebDriver driver;
    private Properties prop;
    public static ExtentReports extentreport = ExtentReportManger.getReportInstance();
    public static ExtentTest logger ;

    /*****************Invoke Browser****************************/
    public void invokeBrowser(String browserName) {


        if (browserName.equalsIgnoreCase("Chrome") || browserName.equalsIgnoreCase("Chromebrowser")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        } else if (browserName.equalsIgnoreCase("mozilla") || browserName.equalsIgnoreCase("mozillabrowser")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("IE") || browserName.equalsIgnoreCase("IEbrowser")) {
            WebDriverManager.operadriver().setup();
            driver = new OperaDriver();
        } else {
            driver = new SafariDriver();
        }
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

        try {

            prop = Filereader.getFilereader().getConfigFile().getWebloctorFromPropertyFile();
        } catch (Exception e) {

            ReportFail(e.getMessage());
        }

//        if (prop == null) {
//
//            prop = new Properties();
//            try {
//                FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "//src//test//resources//ObjectRepository//webelement.properties");
//                prop.load(file);
//            } catch (Exception e) {
//                ReportFail(e.getMessage());
//            }
//
//        }


    }

    /*****************Open Website****************************/

    public void openURL(String url) {
        try {
            driver.get(prop.getProperty(url));
            ReportPass(prop.getProperty(url) + " is passed");
        } catch (Exception e) {
            logger.log(Status.FAIL, "Please Verify " + url + "keyword" + "in \"ProjectConfig.properties\" File ");
            ReportFail(e.getMessage());

        }


    }

    public void openWebSite() {

        driver.get(Filereader.getFilereader().getConfigFile().getwebsite());
        logger.log(Status.INFO,"Open Website");
//        ReportPass(Filereader.getFilereader().getConfigFile().getwebsite()+" Opened");
    }


    public void getBrokenLink(String weburl) {

        try {
            int invalidLink = 0;
            URL url = new URL(weburl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setConnectTimeout(50000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {

            } else {
                invalidLink++;
                logger.log(Status.INFO, invalidLink + ": " + weburl);
                System.out.println(weburl);
            }

        } catch (Exception e) {
            e.printStackTrace();
            ReportFail(e.getMessage());
        }


    }


    /*************************Verify Broken Link********************/
    public void verifyBrokenLinks() {

        List<WebElement> elementList = driver.findElements(By.tagName("a"));
        for (WebElement liste : elementList) {
            String URL = liste.getAttribute("href");
            getBrokenLink(URL);
        }

    }


    /*****************Close Browser****************************/
    public void closeBrowser() {
        driver.close();
    }

    /*****************Quite Browser****************************/
    public void tearDown() {
        driver.quit();
    }


    /*****************Click Element ****************************/
    public void clickElemet(String elementKey) {

        try {
            getElement(elementKey).click();
            ReportPass("Locator identified sucessfully" + elementKey);
        } catch (Exception e) {
            ReportFail(e.getMessage());

        }
    }

    /*****************Enter Text****************************/
    public void enterText(String elementKey, String inputText) {
        try {
            getElement(elementKey).sendKeys(inputText);
            ReportPass(inputText + " has been entered in " + elementKey);
        } catch (Exception e) {
            ReportFail(e.getMessage());

        }
    }

    /*****************Mouse Over****************************/
    public void mouseOver(String elementKey) {
        try {
            Actions act = new Actions(driver);
            WebElement element = getElement(elementKey);
            act.moveToElement(element).build().perform();
            ReportPass("Locator identified sucessfully " + elementKey);
        } catch (Exception e) {
            ReportFail(e.getMessage());

        }
    }

    /*****************Select Value in Drop Down****************************/
    public void selectElementInList(String elementKey, String Value) {
        try {
            List<WebElement> listElement = driver.findElements(By.xpath(prop.getProperty(elementKey)));

            for (WebElement listItem : listElement) {

                String prefix = listItem.getText();
                if (prefix.equals(Value)) {
                    waitForPageLoad();
                    listItem.click();
                    ReportPass(Value + " selected in " + elementKey);
                }
            }
        } catch (Exception e) {
            ReportFail(e.getMessage());
        }

    }

    /*****************Drop Down by Index****************************/

    public void dropDownByIndex(String elementKey, int indexValue) {
        try {
            Select dd = new Select(getElement(elementKey));
            dd.selectByIndex(indexValue);
            ReportPass("Locator identified sucessfully" + elementKey);
        } catch (Exception e) {
            ReportFail(e.getMessage());

        }
    }

    /*****************Drop Down By Value****************************/
    public void dropDownByValue(String elementKey, String Value) {
        try {


            Select dd = new Select(getElement(elementKey));
            dd.selectByValue(Value);
            ReportPass("Locator identified sucessfully" + elementKey);
        } catch (Exception e) {
            ReportFail(e.getMessage());

        }
    }

    /*****************Drop Down By Visible Text****************************/
    public void dropDownByVisibleText(String elementKey, String VisibleText) {
        try {
            Select dd = new Select(getElement(elementKey));
            dd.selectByVisibleText(VisibleText);
            ReportPass("Locator identified sucessfully" + elementKey);
        } catch (Exception e) {
            ReportFail(e.getMessage());

        }
    }

    /*****************Handiling Frames****************************/
    public void switchToFrameByNumber(int frameNumber) {

        try {
            driver.switchTo().frame(frameNumber);
            logger.log(Status.INFO, "Switched to " + frameNumber + " frame");
        } catch (Exception e) {
            ReportFail(e.getMessage());
        }
    }

    public void switchFrameBylocator(String elementKey) {

        try {

            driver.switchTo().frame(elementKey);
            logger.log(Status.INFO, "Switched to frame");
        } catch (Exception e) {

            ReportFail(e.getMessage());
        }

    }

    public void SwitchToDefaultWindow() {

        try {
            driver.switchTo().defaultContent();
            logger.log(Status.INFO, "Switched to Main Window");
        } catch (Exception e) {
            ReportFail(e.getMessage());
        }
    }

    /*****************Handiling Frames****************************/
    public boolean isElementPresent(String elementKey) {

        try {
            if (getElement(elementKey).isDisplayed()) {

                ReportPass(elementKey + ": element is Displayed");
                return true;

            }


        } catch (Exception e) {
            ReportFail(e.getMessage());
        }

        return false;
    }

    public boolean isEnabled(String elementKey) {

        try {
            if (getElement(elementKey).isEnabled()) {
                ReportPass(elementKey + ": element is Enabled");
                return true;
            }
        } catch (Exception e) {
            ReportFail(e.getMessage());
        }

        return false;
    }

    public boolean isSelected(String elementKey) {

        try {
            if (getElement(elementKey).isSelected()) {
                ReportPass(elementKey + ": element is Seleted");
                return true;
            }
        } catch (Exception e) {
            ReportFail(e.getMessage());
        }

        return false;
    }

    public void verifyPageTitle(String pageTitle) {

        try {
            String actualTitle = driver.getTitle();
            logger.log(Status.INFO, "Actual Title is : " + actualTitle);
            logger.log(Status.INFO, "Expected Title is : " + pageTitle);
            Assert.assertEquals(actualTitle, pageTitle);

        } catch (Exception e) {
            ReportFail(e.getMessage());

        }

    }

    /*****************Get Text ****************************/
    public String getText(String elementKey) {
        WebElement ele = null;
        try {
            ele = getElement(elementKey);
            ReportPass("Locator identified sucessfully" + elementKey);

        } catch (Exception e) {
            ReportFail(e.getMessage());

        }
        return ele.getText();
    }

    /*****************Get Element****************************/
    public WebElement getElement(String elementKey) {

        WebElement element = null;
        try {
            if (elementKey.endsWith("_ID")) {
                element = driver.findElement(By.id(prop.getProperty(elementKey)));
                logger.log(Status.INFO, "Following Locator Identified: " + prop.getProperty(elementKey));
            } else if (elementKey.endsWith("_CLASS")) {
                element = driver.findElement(By.className(prop.getProperty(elementKey)));
                logger.log(Status.INFO, "Following Locator Identified: " + prop.getProperty(elementKey));
            } else if (elementKey.endsWith("_linkText")) {
                element = driver.findElement(By.linkText(prop.getProperty(elementKey)));
                logger.log(Status.INFO, "Following Locator Identified: " + prop.getProperty(elementKey));
            } else if (elementKey.endsWith("_XPATH")) {
                element = driver.findElement(By.xpath(prop.getProperty(elementKey)));
                logger.log(Status.INFO, "Following Locator Identified: " + prop.getProperty(elementKey));
            } else {
                Assert.fail("Invalid LocatorName " + elementKey);
            }
        } catch (Exception e) {
            ReportFail(e.getMessage());
            e.printStackTrace();
            Assert.fail("Failing the test case" + e.getMessage());

        }
        return element;
    }

    /*****************Reporting Function****************************/
    public static void ReportFail(String reportMessage) {
        logger.log(Status.FAIL, reportMessage);
        takeScreenshotsonFail();
        Assert.fail(reportMessage);

    }

    public static void ReportPass(String reportMessage) {
        logger.log(Status.PASS, reportMessage);
    }

    public static void takeScreenshotsonFail() {

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourcefile = takesScreenshot.getScreenshotAs(OutputType.FILE);

//        File destinationFile = new File(System.getProperty("user.dir") + "//Screenshots//" + DateUtils.getTimeStamp() + ".png");

//        try {
//            FileUtils.copyFile(sourcefile, destinationFile);
//            logger.addScreenCaptureFromPath
//                    (System.getProperty("user.dir") + "//Screenshots//" + DateUtils.getTimeStamp() + ".png");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        final  String screenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + DateUtils.getTimeStamp() + ".png";
        File destinationFile = new File(screenshotPath);
        try {
            FileUtils.copyFile(sourcefile, destinationFile);
            logger.addScreenCaptureFromPath
                    (screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*****************Custamized wait function****************************/

    public void waitLoad(int waitTime) {
        try {
            Thread.sleep(waitTime * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void waitForPageLoad() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int i = 0;
        while (i != 180) {

            String pageState = (String) js.executeScript("return document.readyState");

            if (pageState.equalsIgnoreCase("complete"))
                break;
            else
                waitLoad(1);

        }

        waitLoad(2);

        i = 0;
        while (i != 180) {

            Boolean jsState = (Boolean) js.executeScript("return window.jQuery != undefined && JQuery.active == 0");

            if (jsState)
                break;
            else
                waitLoad(1);
        }

    }
}

