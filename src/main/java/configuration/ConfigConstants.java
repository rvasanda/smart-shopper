package configuration;

/**
 * Created by Rohit on 2014-08-02.
 */
public class ConfigConstants {

    public static final String BASE_URL = "BaseURL";
    public static final String PRODUCT_TITLE = "ProductTitle";
    public static final String PRICE_WRAPPER = "PriceWrapper";
    public static final String PRODUCT_PRICE = "ProductPrice";
    public static final String PRODUCT_URL = "ProductURL";
    public static final String PRODUCT_PRICERANGE_MIN = "RangeMin";
    public static final String PRODUCT_PRICERANGE_MAX = "RangeMax";
    public static final String CRAWLER_ID_ATTRIBUTE = "id";

    public static final String USER_PROPERTIES_FILE = "App.properties";
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final String EMAIL = "Email";
    public static final String NAME = "Name";

    public static final String CONFIG_FOLDER = System.getProperty("os.name").contains("OS X") ? "configuration/" : "configuration\\";

    private ConfigConstants() { }

}
