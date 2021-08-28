package useractions;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class msn {



    @Test
    public static  void invoke(){

    try{

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");

    }catch (Exception e){

        e.printStackTrace();
        System.out.println(e.getMessage());
    }
    }

}
