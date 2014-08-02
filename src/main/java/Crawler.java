import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

/**
 * Created by Rohit on 2014-07-28.
 */
public abstract class Crawler {

    protected Document pageDoc = null;
    private String baseUrl = null;
    private String starterUrl = null;
    protected Properties properties = null;

    public Crawler(String url, String filePath) {
//        try {
//            this.baseUrl = Utility.getDomainName(url);
//        } catch(URISyntaxException e) {
//            e.printStackTrace();
//        }
        this.baseUrl = url;
        connect(baseUrl);
        readConfigFile(filePath);

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
                System.err.println("Could not retrieve due to Exception: " + e.getMessage());
                continue;
            }
        }
        return null;
    }
    protected abstract CrawlerData retrieveDataSmart(String query);
    protected abstract CrawlerData retrieveDataBySearchUrl(String url, String query);
    protected abstract boolean checkIfTargetPage(Document pageDoc, String query);

    protected void setStarterUrl(String starterUrl) {
        this.starterUrl = starterUrl;
    }

    protected void readConfigFile(String filePath) {
        try {
            System.out.println(System.getProperty("java.class.path"));
            Properties prop = new Properties();
            //ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = this.getClass().getResourceAsStream(filePath);
            prop.load(stream);
        } catch (IOException e) {
            System.err.println("Could not read properties file successfully");
            e.printStackTrace();
        }
    }

}
