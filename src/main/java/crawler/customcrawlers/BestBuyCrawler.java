package crawler.customcrawlers;

import crawler.model.Crawler;
import crawler.model.CrawlerDetails;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by Rohit on 2014-07-28.
 */
public class BestBuyCrawler extends Crawler {

    public BestBuyCrawler(CrawlerDetails crawlerDetails) {
        super(crawlerDetails);
    }

    @Override
    protected boolean checkIfTargetPage(Document pageDoc, String query) {
        boolean isTargetPage = false;

        if (pageDoc == null) {
            return isTargetPage;
        }

        Element potentialProductMatch = pageDoc.getElementsByClass("product-title").size() > 0 ? pageDoc.getElementsByClass("product-title").get(0) : null;
        if (potentialProductMatch != null && potentialProductMatch.text().contains(query)) {
            isTargetPage = true;
        }

        return isTargetPage;
    }
}