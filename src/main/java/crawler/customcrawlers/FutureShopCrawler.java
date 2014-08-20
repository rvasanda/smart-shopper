package crawler.customcrawlers;

import configuration.ConfigConstants;
import crawler.Crawler;
import org.jsoup.nodes.Document;

import java.util.Map;

/**
 * Created by Rohit on 2014-08-02.
 */
public class FutureShopCrawler extends Crawler {

    public FutureShopCrawler(Map<String, String> productDetails) {
        super(productDetails);
    }

    @Override
    protected boolean checkIfTargetPage(Document pageDoc, String query) {
        return false;
    }
}
