package se.tdt.bobby.wodcc.ui.components.verifiers;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

/**
 * Description
 *
 *
 * Created: 2004-feb-03 00:59:17
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class IntegerVerifier extends InputVerifier {

	public boolean verify(JComponent input) {
		if (input instanceof JTextComponent) {
			JTextComponent c = (JTextComponent) input;
			try {
				Integer.parseInt(c.getText());
				return true;
			}
			catch (NumberFormatException e) {
				return false;
			}
		}

		return false;
	}
}
