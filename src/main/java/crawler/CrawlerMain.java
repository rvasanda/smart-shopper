package crawler;

import java.util.concurrent.TimeUnit;

/**
 * Created by Rohit on 2014-07-28.
 */
public class CrawlerMain {

    public static void main(String[] args) {
        Crawler bestBuy = new BestBuyCrawler();
        bestBuy.setStarterUrl("http://www.bestbuy.ca/en-CA/category/led-tvs/29549.aspx?type=product&filter=category%253aTV%2B%2526%2BHome%2BTheatre%253bcategory%253aTelevisions%253bcategory%253aLED%2BTVs%253bbrandName%253aSAMSUNG");
        //bestBuy.setStarterUrl("http://www.bestbuy.ca/en-CA/product/samsung-samsung-55-1080p-240hz-3d-led-smart-tv-un55h7150afxzc-un55h7150afxzc/10290946.aspx?");
        long startTime = System.currentTimeMillis();
        //CrawlerData data = bestBuy.retrieveDataBruteForce("un55h7150afxzc");
        CrawlerData data = bestBuy.retrieveDataByProductUrls();
        long endTime = System.currentTimeMillis();

        String totalExecutionTime = TimeUnit.MILLISECONDS.toMinutes(endTime-startTime) + " minutes";
        System.out.println(totalExecutionTime);
        if (data != null) {
            System.out.println(data.somedata);
        } else {
            System.out.println("FAILED");
        }
        //testBruteForce();
        //testMail();
    }


}
