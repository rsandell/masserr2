package se.tdt.bobby.wodcc.ui.components.view;

import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.db.AppPreferences;

import javax.swing.*;

/**
 * Description.
 * <p/>
 * Created: 2004-jun-30 23:07:01
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class ShowPage2Panel extends GridBagPanel {    
    private JCheckBox mViewPage2;
        private JCheckBox mListPlots;
        private JCheckBox mListExperience;

        public ShowPage2Panel() {
            super();
            mViewPage2 = new JCheckBox("View Page 2", AppPreferences.isViewPage2());
            mListPlots = new JCheckBox("List Plots", AppPreferences.isListPlots());
            mListExperience = new JCheckBox("List Experience", AppPreferences.isListExperience());
            addLine(mViewPage2);
            addLine(mListPlots);
            addLine(mListExperience);
        }

        public boolean isViewPage2() {
            return mViewPage2.isSelected();
        }

        public boolean isListPlots() {
            return mListPlots.isSelected();
        }
        public boolean isListExperience() {
            return mListExperience.isSelected();
        }
}
