package se.tdt.bobby.wodcc.ui.components.view;

import se.tdt.bobby.wodcc.data.Resource;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

/**
 * Description
 *
 *
 * Created: 2004-feb-03 23:26:48
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class ResourceListCellRenderer extends DefaultListCellRenderer {
	private NumberFormat mFormat;
    private NumberFormat mCurrencyFormat;

    public ResourceListCellRenderer() {
		mFormat = NumberFormat.getPercentInstance();
        mCurrencyFormat = NumberFormat.getCurrencyInstance();
	}

	public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (value instanceof se.tdt.bobby.wodcc.data.Resource) {
			se.tdt.bobby.wodcc.data.Resource res = (se.tdt.bobby.wodcc.data.Resource) value;
			String percent = mFormat.format(res.getPercent() / 100f);
			label.setText(res.getType() + ": " + percent + " : " + res.getName());
            label.setToolTipText("Income: " + mCurrencyFormat.format(res.getIncome() * res.getPercent()));
		}

		return label;
	}

}
