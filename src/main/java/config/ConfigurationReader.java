package config; /**
 * Created by Rohit on 2014-08-01.
 */

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigurationReader {

    public static Map<String,String> readXMLConfigFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }

        Map<String, String> xmlProperties = new HashMap<String, String>();
        InputStream stream = null;



        try {
            stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(stream);
            XPath xPath =  XPathFactory.newInstance().newXPath();


            // Read Base URL

            String expression = "/Configuration/BaseURL";
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);


            // Read HTML Div Classes

            expression = "Configuration/HTMLDivClass";
            nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

            // Read Tracked Products

            expression = "Configuration/TrackedProducts/Product";
            nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return xmlProperties;
    }

    public static Properties readPropertiesFile(String filePath) {
        InputStream stream = null;
        Properties properties = null;
        try {
            stream = ConfigurationReader.class.getClass().getResourceAsStream(filePath);
            properties = new Properties();
            properties.load(stream);
        } catch (IOException e) {
            System.err.println("Could not read properties file successfully");
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }
}
