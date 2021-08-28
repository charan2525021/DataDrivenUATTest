package managers.filereader;

import managers.configfile.ConfigFile;

public class Filereader {

    private static Filereader filereader = new Filereader();

    private static ConfigFile configFile;

    private Filereader(){};

    public static Filereader getFilereader() {
        return filereader;
    }

    public ConfigFile getConfigFile(){

        return (configFile==null)?new ConfigFile():configFile;
    }

}