package crawler;

import java.util.concurrent.TimeUnit;

/**
 * Created by Rohit on 2014-07-28.
 */
public class CrawlerMain {

    public static void main(String[] args) {
        Crawler bestBuy = new BestBuyCrawler();
        long startTime = System.currentTimeMillis();
        bestBuy.crawl();

        long endTime = System.currentTimeMillis();

        String totalExecutionTime = TimeUnit.MILLISECONDS.toMinutes(endTime-startTime) + " minutes";
        System.out.println(totalExecutionTime);
    }
}
