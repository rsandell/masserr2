package se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering;

import se.tdt.bobby.wodcc.data.Role;

/**
 * Description
 *
 * Created: 2004-mar-08 18:24:29
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public interface Filter {

    public boolean check(se.tdt.bobby.wodcc.data.Role pRole, Rules pFilterRules);
}
