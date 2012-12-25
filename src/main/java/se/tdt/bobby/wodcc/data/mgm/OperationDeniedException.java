package se.tdt.bobby.wodcc.data.mgm;

import java.sql.SQLException;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-14 02:36:33
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class OperationDeniedException extends SQLException {
    public OperationDeniedException(String reason) {
        super(reason);
    }

    public OperationDeniedException() {
    }
}
