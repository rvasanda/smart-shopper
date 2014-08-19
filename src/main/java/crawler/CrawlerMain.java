package crawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohit on 2014-07-28.
 */
public class CrawlerMain {

    private static final Logger logger = LogManager.getLogger(CrawlerMain.class);

    public static void main(String[] args) {
        logger.info("Smart-Shopper starting...");

        List<Crawler> crawlers = new ArrayList<Crawler>();

        crawlers.add(CrawlerFactory.createCustomCrawler(CrawlerType.BESTBUY));
        crawlers.add(CrawlerFactory.createCustomCrawler(CrawlerType.FUTURESHOP));

        ScheduledCrawlerService scheduledCrawlerService = new ScheduledCrawlerService(crawlers);
        scheduledCrawlerService.runScheduledCrawler();
    }
}
