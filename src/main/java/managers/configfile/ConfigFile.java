package managers.configfile;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigFile {

    Properties prop;


    public Properties getWebloctorFromPropertyFile(){

        try {
            if (prop==null){
                FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "//src//test//resources//ObjectRepository//webelement.properties");
                prop.load(file);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return prop;
    }

    public String getwebsite(){

        String url = prop.getProperty("url");
        if (url!=null) return url;
        else throw new RuntimeException("Url is empty");
    }

}
