package se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering;

import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.db.AppPreferences;

/**
 * Description
 *
 * Created: 2004-mar-08 18:33:41
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class NotPlayedRolesFilter implements Filter {

    public boolean check(se.tdt.bobby.wodcc.data.Role pRole, Rules pFilterRules) {
        if (!pFilterRules.isViewNotPlayedRoles()) {
            if (pRole.getPlayerName().length() <= 0) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return true;
        }
    }
}
