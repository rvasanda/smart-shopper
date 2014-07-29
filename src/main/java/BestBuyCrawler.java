import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Rohit on 2014-07-28.
 */
public class BestBuyCrawler extends CrawlerBase {

    private static final String BESTBUY_URL = "http://www.bestbuy.ca";

    public BestBuyCrawler() {
        super(BESTBUY_URL);
    }

    @Override
    protected CrawlerData retrieveDataSmart(String query) {
        Elements searchElements = pageDoc.getElementsMatchingText("Search");
        Elements searchInputElements = searchElements.tagName("input");

        System.out.println("done");
        //Elements searchResults = pageDoc.select("#locFound").select("a[href]");
        //Element mainContent = pageDoc.getElementById("mainContent");
        //Elements newsHeadlines = pageDoc.select("#mp-itn b a");

        return null;
    }

    @Override
    protected CrawlerData retrieveDataBruteForce(String query) {
        Queue<String> linksQueue = new LinkedList();
        linksQueue.add(BESTBUY_URL);
        while (!linksQueue.isEmpty()) {
            try {
                pageDoc = Jsoup.connect(linksQueue.remove()).get();

                Elements allLinks = pageDoc.select("a[href]");
                for (Element link : allLinks) {
                    linksQueue.add(link.baseUri() + link.attr("href"));
                }
                System.out.println("done");
            } catch(IOException e) {
                System.err.println("Could not connect due to IOException" + e.getMessage());
                continue;
            } catch (Exception e) {
                System.err.println("Could not connect due to Exception" + e.getMessage());
                continue;
            }
        }

        return null;
    }

    @Override
    protected CrawlerData retrieveDataBySearchUrl(String url, String query) {
        return null;
    }
}
