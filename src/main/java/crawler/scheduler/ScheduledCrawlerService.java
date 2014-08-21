package crawler.scheduler;

import crawler.Crawler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private List<Crawler> crawlers = new ArrayList<Crawler>();

    public ScheduledCrawlerService(List<Crawler> crawlers) {
        logger.info("Initializing Scheduled Crawler Service");
        this.crawlers = crawlers;
    }

    public void runScheduledCrawler() {

        final Runnable runCrawler = new Runnable() {
            public void run() {
                for (Crawler c : crawlers) {
                    c.crawl();
                    c.updateConfigurations();
                }
            }
        };
        scheduler.scheduleAtFixedRate(runCrawler, 0, 2, TimeUnit.MINUTES);
        logger.info("Crawler Service scheduled to run every 2 minutes");
    }
}
