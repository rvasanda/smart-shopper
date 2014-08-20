package crawler.customcrawlers;

import crawler.Crawler;
import org.jsoup.nodes.Document;

import java.util.Map;

/**
 * Created by Rohit on 2014-08-02.
 */
public class FutureShopCrawler extends Crawler {

    public FutureShopCrawler(Map<String, String> crawlerDetails) {
        super(crawlerDetails);
    }

    @Override
    protected boolean checkIfTargetPage(Document pageDoc, String query) {
        return false;
    }
}
