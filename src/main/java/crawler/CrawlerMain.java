package crawler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohit on 2014-07-28.
 */
public class CrawlerMain {

    public static void main(String[] args) {
        Crawler bestBuy = new BestBuyCrawler();
        List<Crawler> crawlers = new ArrayList<Crawler>();
        crawlers.add(bestBuy);
        ScheduledCrawler scheduledCrawler = new ScheduledCrawler(crawlers);
        scheduledCrawler.runCrawlerEveryHour();
    }
}
