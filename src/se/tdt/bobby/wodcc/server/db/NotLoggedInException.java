package se.tdt.bobby.wodcc.server.db;

import java.sql.SQLException;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-23 16:09:05
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class NotLoggedInException extends SQLException {

    public NotLoggedInException() {
        super("You are not logged in.");
    }
}
