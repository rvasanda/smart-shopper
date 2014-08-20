package crawler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rohit on 2014-08-16.
 */
public class CrawlerDetails {

    private Map<String, String> details = new HashMap<String, String>();

    public String get(String key) {
        return details.get(key);
    }

    public void put(String key, String val) {
        details.put(key, val);
    }
}
