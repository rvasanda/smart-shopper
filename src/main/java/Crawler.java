import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by Rohit on 2014-07-28.
 */
public class Crawler {

    public static void main(String[] args) {
        CrawlerBase bestBuy = new BestBuyCrawler();
        //bestBuy.retrieveDataBruteForce("un55h7150afxzc");
        testBruteForce();
    }

    private static void testBruteForce() {
        Document pageDoc = null;
        try {
            pageDoc = Jsoup.connect("http://www.bestbuy.ca/en-CA/product/samsung-samsung-55-1080p-240hz-3d-led-smart-tv-un55h7150afxzc-un55h7150afxzc/10290946.aspx?").timeout(10000).get();
            Element potentialProductMatch = pageDoc.getElementsByClass("product-title").get(0);

            if (potentialProductMatch.text().contains("queryString")) {
                System.out.println("yaay");
            }
        } catch(IOException e) {
            System.err.println("Could not connect due to IOException" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Could not connect due to Exception" + e.getMessage());
        }

    }

}
