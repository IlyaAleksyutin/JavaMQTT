import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SettingProg {

    private static ServerModel server;
    private static boolean  readConfigFile_OK = false;
    private final static String FILE_CONFIG_NANME = "Config.json";

    public SettingProg() {

    }

    public static Boolean openFileConfig() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = null;


        if(GetPathJAR.getPathJar() != null) {
            try {
                File initialFile = new File(GetPathJAR.getPathJar() + FILE_CONFIG_NANME);
                is = new FileInputStream(initialFile);
                server = mapper.readValue(is, ServerModel.class);
                return readConfigFile_OK = true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.err.println("Не удается найти файл с конфигурацией!");
                return readConfigFile_OK = false;
            }

        } else {
            try {
                is = Main.class.getResourceAsStream("/" + FILE_CONFIG_NANME);
                server = mapper.readValue(is, ServerModel.class);
                return readConfigFile_OK = true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.err.println("Отсутствует файл с настройками в нутри JAR архива!");
                return readConfigFile_OK = false;
            }
        }
    }

    public static boolean isReadConfigFile_OK() {
        return readConfigFile_OK;
    }

    public static ServerModel getConfig () {
        return server;
    }

}
