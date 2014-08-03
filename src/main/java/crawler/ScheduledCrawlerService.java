package crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rohit on 2014-08-02.
 */
public class ScheduledCrawlerService {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private List<Crawler> crawlers = new ArrayList<Crawler>();

    public ScheduledCrawlerService(List<Crawler> crawlers) {
        this.crawlers = crawlers;
    }

    public void runScheduledCrawler() {
        final Runnable runCrawler = new Runnable() {
            public void run() {
                for (Crawler c : crawlers) {
                    c.crawl();
                }
            }
        };
        scheduler.scheduleAtFixedRate(runCrawler, 0, 30, TimeUnit.MINUTES);
    }
}
