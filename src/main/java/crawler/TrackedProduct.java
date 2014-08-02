package crawler;

import com.sun.tools.javac.util.Pair;

/**
 * Created by Rohit on 2014-08-02.
 */
public class TrackedProduct {

    public String name;
    public String productURL;
    public Pair<Double,Double> priceRange;

    public TrackedProduct(String name) {
        this.name = name;
    }

}
