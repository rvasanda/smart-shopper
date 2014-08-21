package crawler;

import configuration.AppConfig;
import configuration.ConfigConstants;
import mail.MailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by Rohit on 2014-07-28.
 */
public abstract class Crawler {

    private static final Logger logger = LogManager.getLogger(Crawler.class);

    protected CrawlerDetails crawlerDetails = null;

    protected abstract boolean checkIfTargetPage(Document pageDoc, String query);

    public Crawler(CrawlerDetails crawlerDetails) {
        this.crawlerDetails = crawlerDetails;
        initialize();
    }

    private void initialize() {

        if (connect(crawlerDetails.get(ConfigConstants.BASE_URL)) == false) {
            logger.error("Quitting program because of unsuccessful connection...");
            System.exit(0);
        }
    }

    public void updateConfigurations() {
        AppConfig.readXMLConfigFile();
    }

    private boolean connect(String url) {
        logger.info("Testing connection to base url: " + crawlerDetails.get(ConfigConstants.BASE_URL));
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
        throw new UnsupportedOperationException();
    }

    protected void retrieveDataByProductUrls() {
        logger.info("Retrieving data from product urls");

        String productUrl = null;

        for (TrackedProduct product : AppConfig.getProducts()) {
            try {
                if (!product.details.get(ConfigConstants.PRODUCT_URL).startsWith(crawlerDetails.get(ConfigConstants.BASE_URL))) {
                    continue;
                }
                productUrl = product.details.get(ConfigConstants.PRODUCT_URL);
                Document pageDoc = Jsoup.connect(productUrl).timeout(30000).get();
                logger.info("Connected to " + productUrl);
                pageDoc.select("script, .hidden").remove();

                Element productTitleElement = pageDoc.select(crawlerDetails.get(ConfigConstants.PRODUCT_TITLE)).first();
                String productTitleText = productTitleElement.text();
                logger.info("PRODUCT NAME: " + productTitleText);
                product.details.put(ConfigConstants.PRODUCT_TITLE, productTitleText);

                Element productPriceElement  = pageDoc.select(crawlerDetails.get(ConfigConstants.PRICE_WRAPPER))
                                                      .select(crawlerDetails.get(ConfigConstants.PRODUCT_PRICE)).first();

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

    }

    public void crawl() {
        // Retrieve Data from product URLS
        retrieveDataByProductUrls();
        // Check if data is in range
        for (TrackedProduct product : AppConfig.getProducts()) {
            if (checkProductInRange(product)) {
                // Send Email
                MailSender.sendMail(product);
            }
        }
    }

    private boolean checkProductInRange(TrackedProduct product) {
        boolean isInPriceRange = false;

        Double max = Double.parseDouble(product.details.get(ConfigConstants.PRODUCT_MAXPRICE));

        if (product.price <= max) {
            logger.info("Product " + product.details.get(ConfigConstants.PRODUCT_TITLE) + " is in the price range");
            isInPriceRange = true;
        }

        return isInPriceRange;
    }
}
