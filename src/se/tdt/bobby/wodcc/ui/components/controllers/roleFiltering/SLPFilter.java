package se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering;

import se.tdt.bobby.wodcc.data.Role;

/**
 * Description.
 * <p/>
 * Created: 2004-maj-06 01:41:04
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class SLPFilter implements Filter{

    public boolean check(Role pRole, Rules pFilterRules) {
        if (!pFilterRules.isViewSLP()) {
            if(pRole.isSLP()) {
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
