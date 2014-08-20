package crawler.customcrawlers;

import crawler.Crawler;
import crawler.CrawlerDetails;
import org.jsoup.nodes.Document;

/**
 * Created by Rohit on 2014-08-02.
 */
public class FutureShopCrawler extends Crawler {

    public FutureShopCrawler(CrawlerDetails crawlerDetails) {
        super(crawlerDetails);
    }

    @Override
    protected boolean checkIfTargetPage(Document pageDoc, String query) {
        return false;
    }
}
