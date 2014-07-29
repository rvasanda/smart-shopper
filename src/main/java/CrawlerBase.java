import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Rohit on 2014-07-28.
 */
public abstract class CrawlerBase {

    protected boolean connect(String url) {
        boolean isConnected = false;
        try {
            Document doc = Jsoup.connect("http://www.virtualtourist.com/search/location/?keyword=brazil").get();
            Elements searchResults = doc.select("#locFound").select("a[href]");
            //Element mainContent = doc.getElementById("mainContent");
            //Elements newsHeadlines = doc.select("#mp-itn b a");
        } catch(IOException e) {
            System.err.println("Some sort of IOException" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Some sort of Exception" + e.getMessage());
        }


        return isConnected;
    }

    protected abstract CrawlerProperties retrieveProperties();

}
