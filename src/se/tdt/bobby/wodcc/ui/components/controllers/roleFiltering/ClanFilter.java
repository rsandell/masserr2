package se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering;

import se.tdt.bobby.wodcc.data.Role;
import se.tdt.bobby.wodcc.db.AppPreferences;

/**
 * Description
 *
 * Created: 2004-mar-08 18:25:46
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class ClanFilter implements Filter {

    public boolean check(se.tdt.bobby.wodcc.data.Role pRole, Rules pFilterRules) {
        if (pFilterRules.getVisibleClan() >= 0) {
            if (pRole.getClan() != null) {
                if (pRole.getClan().getId() == pFilterRules.getVisibleClan()) {
                    return true;
                }
                else {
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
