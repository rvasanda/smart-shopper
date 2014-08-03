package crawler;

import crawler.customcrawlers.BestBuyCrawler;
import crawler.customcrawlers.FutureShopCrawler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Rohit on 2014-08-02.
 */
public final class CrawlerFactory {

    private static final Logger logger = LogManager.getLogger(CrawlerFactory.class);

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
                logger.error(type + " Crawler not currently supported!", new UnsupportedOperationException());
                throw new UnsupportedOperationException(type + " Crawler not currently supported!");
        }
        logger.info("Initalizing " + type + " Crawler");
        return customCrawler;
    }
}
