import mail.GoogleMail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rohit on 2014-07-28.
 */
public class Crawler {

    public static void main(String[] args) {
        CrawlerBase bestBuy = new BestBuyCrawler();
        long startTime = System.currentTimeMillis();
        CrawlerData data = bestBuy.retrieveDataBruteForce("un55h7150afxzc");
        long endTime = System.currentTimeMillis();

        String totalExecutionTime = TimeUnit.MILLISECONDS.toMinutes(endTime-startTime) + " minutes";
        System.out.println(totalExecutionTime);
        System.out.println(data.somedata);
        //testBruteForce();
        //testMail();
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

    private static void testMail() {
        try {
            GoogleMail.Send("pieman0112", "tennispro", "rvasanda12@gmail.com", "sometitle", "somemessage");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
