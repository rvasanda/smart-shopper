/**
 * Created by Rohit on 2014-07-28.
 */
public class Crawler {

    public static void main(String[] args) {
        CrawlerBase bestBuy = new BestBuyCrawler();
        bestBuy.retrieveDataBruteForce("un55h7150afxzc");
    }

}
