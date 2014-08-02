package crawler;

import config.ConfigConstants;
import config.ConfigurationReader;
import mail.GoogleMail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.*;

/**
 * Created by Rohit on 2014-07-28.
 */
public abstract class Crawler {

    protected Document pageDoc = null;
    private String baseUrl = null;
    private String starterUrl = null;
    protected Map<String, Object> configProperties= null;
    protected List<TrackedProduct> trackedProducts = new ArrayList<TrackedProduct>();

    protected abstract CrawlerData retrieveDataSmart(String query);
    protected abstract CrawlerData retrieveDataBySearchUrl(String url, String query);

    public Crawler(String filePath) {
        configProperties = ConfigurationReader.readXMLConfigFile(filePath);
        baseUrl = configProperties.get(ConfigConstants.BASE_URL).toString();
        constructProductList();
        if (connect(baseUrl) == false) {
            System.exit(0);
        }
    }

    private boolean connect(String url) {
        try {
            pageDoc = Jsoup.connect(url).get();
        } catch(IOException e) {
            System.err.println("Could not connect due to IOException" + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Could not connect due to Exception" + e.getMessage());
            return false;
        }
        return true;
    }

    protected CrawlerData retrieveDataBruteForce(String query) {
        Queue<String> linksQueue = new LinkedList<String>();

        if (starterUrl != null) {
            linksQueue.add(starterUrl);
        } else {
            linksQueue.add(baseUrl);
        }
        while (!linksQueue.isEmpty()) {
            try {
                Document pageDoc = Jsoup.connect(linksQueue.remove()).timeout(30000).get();

                Elements allLinks = pageDoc.select("a[href]");
                for (Element link : allLinks) {
                    StringBuilder linkBuilder = new StringBuilder(baseUrl);
                    linkBuilder.append(link.attr("href"));
                    String linkString = linkBuilder.toString();
                    if (!linksQueue.contains(linkString) && linkString.contains(baseUrl)) {
                        linksQueue.add(linkString);
                        if (checkIfTargetPage(pageDoc,query)) {
                            CrawlerData cd = new CrawlerData();
                            cd.somedata = "Found";
                            return cd;
                        }
                    }
                }
                System.out.println("Queue Size: " + linksQueue.size());
            } catch(IOException e) {
                System.err.println("Could not retrieve data due to IOException: " + e.getMessage());
                continue;
            } catch (Exception e) {
                System.err.println("Could not retrieve data due to Exception: " + e.getMessage());
                continue;
            }
        }
        return null;
    }


    protected CrawlerData retrieveDataByProductUrls() {
        for (TrackedProduct product : trackedProducts) {
            try {
                String productUrl = product.details.get(ConfigConstants.PRODUCT_URL);
                Document pageDoc = Jsoup.connect(productUrl).timeout(30000).get();

                Element productTitleElement = pageDoc.select(configProperties.get(ConfigConstants.PRODUCT_TITLE).toString()).first();
                String productTitleText = productTitleElement.text();
                product.details.put(ConfigConstants.PRODUCT_TITLE, productTitleText);

                Element productPriceElement  = pageDoc.select(configProperties.get(ConfigConstants.PRICE_WRAPPER).toString())
                                                      .select(configProperties.get(ConfigConstants.PRODUCT_PRICE).toString()).first();

                Double productPrice = Double.parseDouble(productPriceElement.text().replace("$","").replace(",",""));
                product.price = productPrice;

                System.out.println(productPrice);

            } catch(IOException e) {
                System.err.println("Could not retrieve data due to IOException: " + e.getMessage());
                continue;
            } catch (Exception e) {
                System.err.println("Could not retrieve data due to Exception: " + e.getMessage());
                continue;
            }
        }

        return null;
    }

    protected abstract boolean checkIfTargetPage(Document pageDoc, String query);

    protected void setStarterUrl(String starterUrl) {
        this.starterUrl = starterUrl;
    }

    private void constructProductList() {
        Iterator iterator = configProperties.values().iterator();

        while (iterator.hasNext()) {
            Object value = iterator.next();

            if (value instanceof TrackedProduct) {
                trackedProducts.add((TrackedProduct) value);
            }
        }
    }

    public CrawlerData crawl() {
        // Retrieve Data from product URLS
        CrawlerData data = retrieveDataByProductUrls();
        // Check if data is in range
        for (TrackedProduct product : trackedProducts) {
            if (checkProductInRange(product)) {
                // Send Email

            }
        }
        return data;

    }

    private boolean checkProductInRange(TrackedProduct product) {
        boolean isInPriceRange = false;

        Double min = Double.parseDouble(product.details.get(ConfigConstants.PRODUCT_PRICERANGE_MIN));
        Double max = Double.parseDouble(product.details.get(ConfigConstants.PRODUCT_PRICERANGE_MAX));

        if (product.price <= max && product.price >= min) {
            isInPriceRange = true;
        }

        return isInPriceRange;
    }

    private void sendMail() {
        try {
            GoogleMail.Send("rvasanda12", "Escaflowne1", "rvasanda12@gmail.com", "sometitle", "somemessage");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
