import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

    /**
     * Key here is to find the search box input and the search button/img/url/input. If we can identify a parent container in the dom which contains these 2
     * things then we can be 99% sure that this is the search field we want. Next is to submit a query through this input field. this will make search 10x faster
     *
     * @param query
     * @return
     */
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
                pageDoc = Jsoup.connect(linksQueue.remove()).timeout(10000).get();

                Elements allLinks = pageDoc.select("a[href]");
                for (Element link : allLinks) {
                    StringBuilder linkBuilder = new StringBuilder(BESTBUY_URL);
                    linkBuilder.append(link.attr("href"));
                    String linkString = linkBuilder.toString();
                    if (!linksQueue.contains(linkString)) {
                        linksQueue.add(linkString);
                    }
                }
                System.out.println("done");
            } catch(IOException e) {
                System.err.println("Could not connect due to IOException: " + e.getMessage());
                continue;
            } catch (Exception e) {
                System.err.println("Could not connect due to Exception: " + e.getMessage());
                continue;
            }
        }

        return null;
    }

    @Override
    protected CrawlerData retrieveDataBySearchUrl(String url, String query) {
        return null;
    }

    @Override
    protected boolean checkIfTargetPage(Document pageDoc, String query) {
        boolean isTargetPage = false;

        if (pageDoc == null) {
            return isTargetPage;
        }

        Element potentialProductMatch = pageDoc.getElementsByClass("product-title").get(0);
        if (potentialProductMatch.text().contains(query)) {
            //TODO: check if price is in desired range
            //TODO: send mail if price in desired range
        }

        return isTargetPage;
    }

}
