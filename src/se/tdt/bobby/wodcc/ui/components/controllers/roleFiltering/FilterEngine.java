package se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering;

import se.tdt.bobby.wodcc.data.Role;

/**
 * Description
 *
 * Created: 2004-mar-08 18:37:13
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class FilterEngine {
    private static Filter[] sFilters = {new VitalsFilter(), new DomainFilter(), new ClanFilter(), new GhoulsFilter(), new NotPlayedRolesFilter(), new KindredFilter(), new SLPFilter()};

    public static boolean check(se.tdt.bobby.wodcc.data.Role pRole, Rules pFilterRules) {
        for (int i = 0; i < sFilters.length; i++) {
            Filter filter = sFilters[i];
            if(!filter.check(pRole, pFilterRules)) {
                return false;
            }
        }
        return true;
    }
}
