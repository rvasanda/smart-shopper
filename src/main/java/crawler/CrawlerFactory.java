package crawler;

import crawler.customcrawlers.BestBuyCrawler;
import crawler.customcrawlers.FutureShopCrawler;

/**
 * Created by Rohit on 2014-08-02.
 */
public final class CrawlerFactory {

    public static enum Crawlers {
        BESTBUY,
        FUTURESHOP,
        TIGERDIRECT,
        NEWEGG,
        NCIX
    }

    public static Crawler createCustomCrawler(Crawlers type) {
        Crawler customCrawler = null;

        switch(type) {
            case BESTBUY:
                customCrawler = new BestBuyCrawler();
                break;
            case FUTURESHOP:
                customCrawler = new FutureShopCrawler();
                break;
            default:
                throw new UnsupportedOperationException("Custom crawler type not currently supported!");
        }
        return customCrawler;
    }

}
