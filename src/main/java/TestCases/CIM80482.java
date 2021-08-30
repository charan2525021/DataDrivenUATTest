package TestCases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import useractions.BaseUI;
import utilities.ExtentReportManger;

public class CIM80482 extends BaseUI {


    @Test
    public  void CIM80482 (){

        logger = extentreport.createTest("first Testone");
        invokeBrowser("chrome");
        openWebSite();
        enterText("text_XPATH","charan kumar Ravula  ");
        clickElemet("button_XPATH");
        closeBrowser();
    }

    @AfterTest
    public void flash(){
       extentreport.flush();
    }

}
