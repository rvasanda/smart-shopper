package crawler;

import configuration.AppConfig;
import configuration.ConfigConstants;
import crawler.crawlerfactory.CrawlerFactory;
import crawler.model.Crawler;
import crawler.model.CrawlerDetails;
import crawler.scheduler.ScheduledCrawlerService;
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
        logger.info("Available Crawlers: ");
        for (CrawlerDetails crawlerDetails : AppConfig.getCrawlerDetails()) {
            String crawlerID = crawlerDetails.get(ConfigConstants.CRAWLER_ID_ATTRIBUTE);
            Crawler newCrawler = CrawlerFactory.createCustomCrawler(crawlerID, crawlerDetails);
            crawlers.add(newCrawler);
            logger.info("----" + crawlerID);
        }

        ScheduledCrawlerService scheduledCrawlerService = new ScheduledCrawlerService(crawlers);
        scheduledCrawlerService.runScheduledCrawler();
    }
}
