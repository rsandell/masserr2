package se.tdt.bobby.wodcc.ui.components.view;

import javax.swing.*;
import java.awt.*;

/**
 * Description
 *
 * 
 * Created: 2004-feb-12 12:20:57
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class LineComponent extends JPanel {

	public LineComponent() {
		setPreferredSize(new Dimension(-1, 1));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
	}
}
