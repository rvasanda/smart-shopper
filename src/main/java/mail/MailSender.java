package mail;

import configuration.AppConfig;
import configuration.ConfigConstants;
import crawler.model.TrackedProduct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * Created by Rohit on 2014-08-20.
 */
public class MailSender {

    private static final Logger logger = LogManager.getLogger(MailSender.class);

    public static void sendMail(TrackedProduct product) {
        //TODO: consider aggregating results

        if (product.mailSent == true) {
            return;
        }

        String title = new StringBuilder().append("ON SALE! ")
                .append(product.details.get(ConfigConstants.PRODUCT_TITLE)).toString();

        String message = new StringBuilder().append(AppConfig.getProperty(ConfigConstants.NAME))
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
            GoogleMail.Send(AppConfig.getProperty(ConfigConstants.USERNAME),
                    AppConfig.getProperty(ConfigConstants.PASSWORD), AppConfig.getProperty(ConfigConstants.EMAIL), title, message);
            product.mailSent = true;
            logger.debug("Sending mail to:  " + AppConfig.getProperty((ConfigConstants.EMAIL)) + " for product " + product.details.get(ConfigConstants.PRODUCT_TITLE));
        } catch (AddressException e) {
            logger.error(e.getMessage(), e);
        } catch (MessagingException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
