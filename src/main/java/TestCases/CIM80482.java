package TestCases;

import org.testng.annotations.Test;
import useractions.BaseUI;

public class CIM80482 extends BaseUI {


    @Test
    public  void CIM80482 (){

        invokeBrowser("chrome");
        openWebSite();
    }

}
