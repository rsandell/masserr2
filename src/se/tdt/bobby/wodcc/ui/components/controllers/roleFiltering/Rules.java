package se.tdt.bobby.wodcc.ui.components.controllers.roleFiltering;

import se.tdt.bobby.wodcc.data.Domain;
import se.tdt.bobby.wodcc.data.Vitals;
import se.tdt.bobby.wodcc.db.AppPreferences;

import javax.swing.*;

/**
 * Description
 *
 * Created: 2004-mar-08 18:54:15
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class Rules {
    private int mVisibleClan;
    private boolean mViewGhouls;
    private boolean mViewNotPlayedRoles;
    private int mVisibleDomain;
    private se.tdt.bobby.wodcc.data.Vitals mVisibleVitals;
    private boolean mViewKindred = true;
    private boolean mViewSLP;

    public Rules(int pVisibleClan, boolean pViewGhouls, boolean pViewNotPlayedRoles, int pVisibleDomain, se.tdt.bobby.wodcc.data.Vitals pVisibleVitals, boolean pViewKindred, boolean pViewSLP) {
        mVisibleClan = pVisibleClan;
        mViewGhouls = pViewGhouls;
        mViewNotPlayedRoles = pViewNotPlayedRoles;
        mVisibleDomain = pVisibleDomain;
        mVisibleVitals = pVisibleVitals;
        mViewKindred = pViewKindred;
        mViewSLP = pViewSLP;
    }

    public Rules() {
        mVisibleClan = AppPreferences.getVisibleClan();
        mViewGhouls = AppPreferences.getViewGhouls();
        mViewNotPlayedRoles = AppPreferences.getViewNotPlayedRoles();
        mVisibleDomain = AppPreferences.getVisibleDomain();
        mVisibleVitals = AppPreferences.getVisibleVitals();
        mViewKindred = AppPreferences.getViewKindred();
        mViewSLP = AppPreferences.getViewSLP(); 
    }

    public int getVisibleClan() {
        return mVisibleClan;
    }

    public void setVisibleClan(int pVisibleClan) {
        mVisibleClan = pVisibleClan;
    }

    public boolean isViewGhouls() {
        return mViewGhouls;
    }

    public void setViewGhouls(boolean pViewGhouls) {
        mViewGhouls = pViewGhouls;
    }

    public boolean isViewNotPlayedRoles() {
        return mViewNotPlayedRoles;
    }

    public void setViewNotPlayedRoles(boolean pViewNotPlayedRoles) {
        mViewNotPlayedRoles = pViewNotPlayedRoles;
    }

    public int getVisibleDomain() {
        return mVisibleDomain;
    }

    public void setVisibleDomain(int pVisibleDomain) {
        mVisibleDomain = pVisibleDomain;
    }

    public se.tdt.bobby.wodcc.data.Vitals getVisibleVitals() {
        return mVisibleVitals;
    }

    public void setVisibleVitals(se.tdt.bobby.wodcc.data.Vitals pVisibleVitals) {
        mVisibleVitals = pVisibleVitals;
    }

    public boolean isViewKindred() {
        return mViewKindred;
    }

    public void setViewKindred(boolean pViewKindred) {
        mViewKindred = pViewKindred;
    }

    public boolean isViewSLP() {
        return mViewSLP;
    }

    public void setViewSLP(boolean pViewSLP) {
        mViewSLP = pViewSLP;
    }
}
