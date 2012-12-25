package se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering;

import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.db.AppPreferences;

/**
 * Description
 *
 * Created: 2004-mar-08 18:28:18
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class DomainFilter implements Filter {

    public boolean check(se.tdt.bobby.wodcc.data.Role pRole, Rules pFilterRules) {
        if (pFilterRules.getVisibleDomain() >= 0) {
            if (pRole.getDomain() != null) {
                if(pRole.getDomain().getId() == pFilterRules.getVisibleDomain()) {
                    return true;
                } else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return true;
        }
    }
}
