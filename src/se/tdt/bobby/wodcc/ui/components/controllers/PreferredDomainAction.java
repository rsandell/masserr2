package se.tdt.bobby.wodcc.ui.components.controllers;

import se.tdt.bobby.wodcc.ui.components.MutableAction;
import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.db.AppPreferences;

import java.awt.event.*;

/**
 * Description
 *
 * Created: 2004-mar-08 17:43:09
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class PreferredDomainAction extends MutableAction {

    private se.tdt.bobby.wodcc.data.Domain mDomain;

    public PreferredDomainAction(se.tdt.bobby.wodcc.data.Domain pDomain) {
        super(pDomain.getName());
        mDomain = pDomain;
    }

    public void actionPerformed(ActionEvent e) {
        AppPreferences.setPreferredDomain(mDomain);
    }
}
