package crawler;

import configuration.AppConfig;
import configuration.ConfigConstants;
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
        AppConfig.readXMLConfigFile();
        AppConfig.readPropertiesFile();

        List<Crawler> crawlers = new ArrayList<Crawler>();
        for (TrackedProduct product : AppConfig.getProducts()) {
            String crawlerID = product.details.get(ConfigConstants.CRAWLER_ID_ATTRIBUTE);
            Crawler newCrawler = CrawlerFactory.createCustomCrawler(crawlerID, product.details);
            crawlers.add(newCrawler);
        }

        ScheduledCrawlerService scheduledCrawlerService = new ScheduledCrawlerService(crawlers);
        scheduledCrawlerService.runScheduledCrawler();
    }
}
