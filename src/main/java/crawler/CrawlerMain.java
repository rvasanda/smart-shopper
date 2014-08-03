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
        List<Crawler> crawlers = new ArrayList<Crawler>();

        crawlers.add(CrawlerFactory.createCustomCrawler(CrawlerFactory.Crawlers.BESTBUY));
        crawlers.add(CrawlerFactory.createCustomCrawler(CrawlerFactory.Crawlers.FUTURESHOP));
        printAvailbleCrawlers(crawlers);
        ScheduledCrawlerService scheduledCrawlerService = new ScheduledCrawlerService(crawlers);
        scheduledCrawlerService.runCrawlerEveryHour();
    }

    private static void printAvailbleCrawlers(List<Crawler> availableCrawlers) {
        logger.debug("sad");
        for (Crawler c : availableCrawlers) {
            logger.debug("sad");
        }
    }
}
