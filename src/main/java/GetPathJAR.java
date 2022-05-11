import java.net.URI;
import java.nio.file.Paths;

public class GetPathJAR {

    public static String getPathJar() {
        try {
            final URI jarUriPath =
                    Main.class.getResource(Main.class.getSimpleName() + ".class").toURI();
            String jarStringPath = jarUriPath.toString().replace("jar:", "");
            String jarCleanPath  = Paths.get(new URI(jarStringPath)).toString();

            if (jarCleanPath.toLowerCase().contains(".jar")) {
                String testPath = jarCleanPath.substring(0, jarCleanPath.lastIndexOf(".jar") + 4);

                int CountChar = 0;
                int cat = 0;
                for(char ch : testPath.toCharArray()){
                    CountChar++;
                    if((ch == '/') || (ch == '\u005c\')) cat = CountChar;
                }
                return testPath.substring(0, cat);
            }  else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }
}
