package se.tdt.bobby.wodcc.ui.components.view;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

/**
 * Description
 *
 *
 * Created: 2004-feb-10 23:35:50
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class MoneyTableCellRenderer extends DefaultTableCellRenderer {
	private NumberFormat mFormat;

	public MoneyTableCellRenderer() {
		mFormat = NumberFormat.getCurrencyInstance();
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
												   boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (value instanceof Number) {
			label.setText(mFormat.format(value));
		}
		return label;
	}
}
