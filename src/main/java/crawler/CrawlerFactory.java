package crawler;

import crawler.customcrawlers.BestBuyCrawler;
import crawler.customcrawlers.FutureShopCrawler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Created by Rohit on 2014-08-02.
 */
public final class CrawlerFactory {

    private static final Logger logger = LogManager.getLogger(CrawlerFactory.class);

    public static Crawler createCustomCrawler(String crawlerType, Map<String,String> productDetails) {
        Crawler customCrawler = null;

        if (crawlerType.toLowerCase().equals(CrawlerType.BESTBUY)) {
            customCrawler = new BestBuyCrawler(productDetails);
        } else if (crawlerType.toLowerCase().equals(CrawlerType.FUTURESHOP)){
            customCrawler = new FutureShopCrawler(productDetails);
        } else {
            logger.error(crawlerType + " Crawler not currently supported!", new UnsupportedOperationException());
            throw new UnsupportedOperationException(crawlerType + " Crawler not currently supported!");
        }

        logger.info("Initalizing " + crawlerType + " Crawler");
        return customCrawler;
    }
}
