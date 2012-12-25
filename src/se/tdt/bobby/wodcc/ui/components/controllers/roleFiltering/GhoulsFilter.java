package se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering;

import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.db.AppPreferences;

/**
 * Description
 *
 * Created: 2004-mar-08 18:31:59
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class GhoulsFilter implements Filter {

    public boolean check(se.tdt.bobby.wodcc.data.Role pRole, Rules pFilterRules) {
        if (!pFilterRules.isViewGhouls()) {
            if(pRole.isGhoul()) {
                return false;
            } else {
                return true;
            }
        }
        else {
            return true;
        }
    }
}
