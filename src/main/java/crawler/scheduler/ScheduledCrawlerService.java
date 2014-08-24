package crawler.scheduler;

import configuration.AppConfig;
import configuration.ConfigConstants;
import crawler.model.Crawler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rohit on 2014-08-02.
 */
public class ScheduledCrawlerService {

    private static final Logger logger = LogManager.getLogger(ScheduledCrawlerService.class);

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final int crawlInterval = Utility.parseCrawlInterval(AppConfig.getProperty(ConfigConstants.CRAWL_INTERVAL));
    private List<Crawler> crawlers = new ArrayList<Crawler>();

    public ScheduledCrawlerService(List<Crawler> crawlers) {
        logger.info("Initializing Scheduled Crawler Service");
        this.crawlers = crawlers;

    }

    public void runScheduledCrawler() {

        final Runnable runCrawler = new Runnable() {
            public void run() {
                AppConfig.readXMLConfigFile();
                logger.info("Crawler Service is running now");
                for (Crawler c : crawlers) {
                    c.crawl();
                }
            }
        };
        scheduler.scheduleAtFixedRate(runCrawler, 0, crawlInterval, TimeUnit.MINUTES);
        logger.info("Crawler Service scheduled to run every " + crawlInterval + " minutes");
    }
}
