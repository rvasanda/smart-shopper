import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Rohit on 2014-07-28.
 */
public abstract class CrawlerBase {

    protected Document pageDoc = null;

    public CrawlerBase(String url) {
        connect(url);
    }

    private boolean connect(String url) {
        try {
            pageDoc = Jsoup.connect(url).get();
        } catch(IOException e) {
            System.err.println("Could not connect due to IOException" + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Could not connect due to Exception" + e.getMessage());
            return false;
        }
        return true;
    }

    protected abstract CrawlerData retrieveDataSmart(String query);
    protected abstract CrawlerData retrieveDataBruteForce(String query);
    protected abstract CrawlerData retrieveDataBySearchUrl(String url, String query);

    protected abstract boolean checkIfTargetPage();

}
