import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Rohit on 2014-07-30.
 */
public class Utility {

    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}
