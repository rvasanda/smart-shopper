package crawler.customcrawlers;

import crawler.Crawler;
import org.jsoup.nodes.Document;

/**
 * Created by Rohit on 2014-08-02.
 */
public class FutureShopCrawler extends Crawler {

    private static final String CONFIG_FILE = "FutureShopConfig.xml";

    public FutureShopCrawler() {
        super(CONFIG_FILE);
    }

    @Override
    protected boolean checkIfTargetPage(Document pageDoc, String query) {
        return false;
    }
}
