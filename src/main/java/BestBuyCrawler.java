import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Rohit on 2014-07-28.
 */
public class BestBuyCrawler extends CrawlerBase {

    private static final String BESTBUY_URL = "http://www.bestbuy.ca";

    public BestBuyCrawler() {
        super(BESTBUY_URL);
    }

    @Override
    public CrawlerProperties retrieveData(String query) {

        Elements searchResults = pageDoc.select("#locFound").select("a[href]");
        Element mainContent = pageDoc.getElementById("mainContent");
        Elements newsHeadlines = pageDoc.select("#mp-itn b a");

        return null;

    }

}
