import com.sun.corba.se.impl.orb.ORBConfiguratorImpl;
import mail.GoogleMail;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * Created by Rohit on 2014-07-28.
 */
public class BestBuyCrawler extends Crawler {

    private static final String BESTBUY_URL = "http://www.bestbuy.ca";
    private static final String PROPERTIES_FILE = "BestBuy.properties";

    public BestBuyCrawler() {
        super(BESTBUY_URL, PROPERTIES_FILE);

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
    protected CrawlerData retrieveDataBySearchUrl(String url, String query) {
        return null;
    }

    @Override
    protected boolean checkIfTargetPage(Document pageDoc, String query) {
        boolean isTargetPage = false;

        if (pageDoc == null) {
            return isTargetPage;
        }

        Element potentialProductMatch = pageDoc.getElementsByClass("product-title").size() > 0 ? pageDoc.getElementsByClass("product-title").get(0) : null;
        if (potentialProductMatch!= null && potentialProductMatch.text().contains(query)) {
            //TODO: check if price is in desired range
            //TODO: send mail if price in desired range
            try {
                GoogleMail.Send("pieman0112", "tennispro", "rvasanda12@gmail.com", "sometitle", "somemessage");
            } catch (AddressException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        return isTargetPage;
    }
}
