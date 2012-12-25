package se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering;

import se.tdt.bobby.wodcc.data.Role;

/**
 * Description
 *
 * Created: 2004-mar-08 19:10:15
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class VitalsFilter implements Filter {

    public boolean check(se.tdt.bobby.wodcc.data.Role pRole, Rules pFilterRules) {
        if (pFilterRules.getVisibleVitals() == null) {
            return true;
        }
        else {
            if (pRole.getVitals() == pFilterRules.getVisibleVitals()) {
                return true;
            }
            else {
                return false;
            }
        }
    }
}
