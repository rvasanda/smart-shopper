package crawler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohit on 2014-07-28.
 */
public class CrawlerMain {

    public static void main(String[] args) {
        List<Crawler> crawlers = new ArrayList<Crawler>();

        //crawlers.add(CrawlerFactory.createCustomCrawler(CrawlerFactory.Crawlers.BESTBUY));
        crawlers.add(CrawlerFactory.createCustomCrawler(CrawlerFactory.Crawlers.FUTURESHOP));

        ScheduledCrawlerService scheduledCrawlerService = new ScheduledCrawlerService(crawlers);
        scheduledCrawlerService.runCrawlerEveryHour();
    }
}
