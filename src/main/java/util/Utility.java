package util;

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

    public static int parseCrawlInterval(String interval) {
        int intervalInMinutes = -1;
        String intervalString = "1h 30m";
        int hours = 0;
        int minutes = -1;

        if (intervalString.indexOf("h") != -1) {
            hours = Integer.parseInt(intervalString.substring(0, intervalString.indexOf("h")));
            minutes = Integer.parseInt(intervalString.substring(intervalString.indexOf("h") + 2, intervalString.indexOf("m")));
            intervalInMinutes = hours * 60 + minutes;
        } else if (intervalString.indexOf("m") != -1) {
            minutes = Integer.parseInt(intervalString.substring(0, intervalString.indexOf("m")));
            intervalInMinutes = minutes;
        }
        return intervalInMinutes;
    }
}
