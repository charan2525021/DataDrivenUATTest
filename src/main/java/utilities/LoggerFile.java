package utilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerFile {

    public static Logger logger;

    public static void main(String[] args){

        logger = Logger.getLogger("Log4j.properties");

//        PropertyConfigurator.configure("Log4j.properties");

        logger.info("inromation");
        logger.error("Error");
        logger.trace("Trace");
        logger.getName();
        logger.fatal("fatal");


    }

}
