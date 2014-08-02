import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Rohit on 2014-07-30.
 */
public class Utility {

    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public static Properties readPropertiesFile(String filePath) {
        InputStream stream = null;
        Properties properties = null;
        try {
            stream = Utility.class.getClass().getResourceAsStream(filePath);
            properties = new Properties();
            properties.load(stream);
        } catch (IOException e) {
            System.err.println("Could not read properties file successfully");
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    public static Map readXMLPropertiesFile(String filePath) {
        InputStream stream = null;
        //Map properties = new HashMap<String,Object>();
        Properties properties = null;
        try {
            stream = Utility.class.getClass().getResourceAsStream(filePath);
            properties = new Properties();
            properties.load(stream);
        } catch (IOException e) {
            System.err.println("Could not read properties file successfully");
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }
}
