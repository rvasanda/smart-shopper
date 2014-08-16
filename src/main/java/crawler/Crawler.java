package crawler;

import configuration.AppConfig;
import configuration.ConfigConstants;
import mail.GoogleMail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(Crawler.class);

    private String baseUrl = null;
    private String starterUrl = null;
    private String filePath = null;
    protected Map<String, Object> configProperties= null;
    private Properties userProperties = new Properties();
    protected List<TrackedProduct> trackedProducts = new ArrayList<TrackedProduct>();

    protected abstract boolean checkIfTargetPage(Document pageDoc, String query);

    public Crawler(String filePath) {
        this.filePath = filePath;
        initialize();
    }

    private void initialize() {
        //configProperties = AppConfig.readXMLConfigFile(filePath);
        userProperties = AppConfig.readPropertiesFile(ConfigConstants.USER_PROPERTIES_FILE);
        baseUrl = configProperties.get(ConfigConstants.BASE_URL).toString();
        constructProductList();
        if (connect(baseUrl) == false) {
            logger.error("Quitting program because of unsuccessful connection...");
            System.exit(0);
        }
    }

    public void updateConfigurations() {
        //configProperties = AppConfig.readXMLConfigFile(filePath);
        constructProductList();
    }

    private boolean connect(String url) {
        logger.info("Testing connection to base url: " + baseUrl);
        try {
            Jsoup.connect(url).get();
        } catch(IOException e) {
            logger.error("Connection failed!");
            logger.error(e.getMessage(), e);
            return false;
        } catch (Exception e) {
            logger.error("Connection failed!");
            logger.error(e.getMessage(), e);
            return false;
        }
        logger.info("Connection successful");
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

    protected void retrieveDataByProductUrls() {
        logger.info("Retrieving data from product urls");

        String productUrl = null;

        for (TrackedProduct product : trackedProducts) {
            try {
                productUrl = product.details.get(ConfigConstants.PRODUCT_URL);
                Document pageDoc = Jsoup.connect(productUrl).timeout(30000).get();
                logger.info("Connected to " + productUrl);
                pageDoc.select("script, .hidden").remove();

                Element productTitleElement = pageDoc.select(configProperties.get(ConfigConstants.PRODUCT_TITLE).toString()).first();
                String productTitleText = productTitleElement.text();
                logger.info("PRODUCT NAME: " + productTitleText);
                product.details.put(ConfigConstants.PRODUCT_TITLE, productTitleText);

                Element productPriceElement  = pageDoc.select(configProperties.get(ConfigConstants.PRICE_WRAPPER).toString())
                                                      .select(configProperties.get(ConfigConstants.PRODUCT_PRICE).toString()).first();

                Double productPrice = Double.parseDouble(productPriceElement.text().replace("$","").replace(",",""));
                product.price = productPrice;
                logger.info("Product price: " + productPriceElement.text());
            } catch(IOException e) {
                logger.error("Connection to url failed: " + productUrl);
                logger.error(e.getMessage(), e);
                continue;
            } catch (Exception e) {
                logger.error("Connection to url failed: " + productUrl);
                logger.error(e.getMessage(), e);
                continue;
            }
        }
    }

    protected void retrieveDataSmart(String query) {
        throw new UnsupportedOperationException("Smart retrieval of data not implemented yet");
    }

    protected void retrieveDataBySearchUrl(String url, String query) {
        throw new UnsupportedOperationException("Search retrieval of data not implemented yet");
    }

    protected void setStarterUrl(String starterUrl) {
        this.starterUrl = starterUrl;
    }

    private void constructProductList() {
        Iterator iterator = configProperties.values().iterator();
        trackedProducts.clear();
        while (iterator.hasNext()) {
            Object value = iterator.next();

            if (value instanceof TrackedProduct) {
                trackedProducts.add((TrackedProduct) value);
            }
        }
    }

    public void crawl() {
        // Retrieve Data from product URLS
        retrieveDataByProductUrls();
        // Check if data is in range
        for (TrackedProduct product : trackedProducts) {
            if (checkProductInRange(product)) {
                // Send Email
                sendMail(product);
            }
        }
    }

    private boolean checkProductInRange(TrackedProduct product) {
        boolean isInPriceRange = false;

        Double min = Double.parseDouble(product.details.get(ConfigConstants.PRODUCT_PRICERANGE_MIN));
        Double max = Double.parseDouble(product.details.get(ConfigConstants.PRODUCT_PRICERANGE_MAX));

        if (product.price <= max && product.price >= min) {
            logger.info("Product " + product.details.get(ConfigConstants.PRODUCT_TITLE) + " is in the price range");
            isInPriceRange = true;
        }

        return isInPriceRange;
    }

    private void sendMail(TrackedProduct product) {
        //TODO: consider aggregating results

        String title = new StringBuilder().append("ON SALE! ")
                .append(product.details.get(ConfigConstants.PRODUCT_TITLE)).toString();

        String message = new StringBuilder().append(userProperties.getProperty(ConfigConstants.NAME))
                .append(", we have great news! \n\nThe ")
                .append(product.details.get(ConfigConstants.PRODUCT_TITLE))
                .append(" is on sale now for $")
                .append(product.price)
                .append(" at ")
                .append(product.details.get(ConfigConstants.PRODUCT_URL))
                .append(". Go buy it bitch!")
                .append("\n\nKind regards, \n")
                .append("Smart Shopper")
                .toString();

        try {
            GoogleMail.Send(userProperties.getProperty(ConfigConstants.USERNAME),
                    userProperties.getProperty(ConfigConstants.PASSWORD), userProperties.getProperty(ConfigConstants.EMAIL), title, message);
            logger.info("Sending mail to:  " + userProperties.getProperty((ConfigConstants.EMAIL)) + " for product " + product.details.get(ConfigConstants.PRODUCT_TITLE));
        } catch (AddressException e) {
            logger.error(e.getMessage(), e);
        } catch (MessagingException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
