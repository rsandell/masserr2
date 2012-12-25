package se.tdt.bobby.wodcc.ui.components.verifiers;

import javax.swing.*;
import javax.swing.text.JTextComponent;

/**
 * Description
 *
 *
 * Created: 2004-feb-11 20:17:48
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class LongVerifier extends InputVerifier {

	public boolean verify(JComponent input) {
		if (input instanceof JTextComponent) {
			JTextComponent c = (JTextComponent) input;
			try {
				Long.parseLong(c.getText());
				return true;
			}
			catch (NumberFormatException e) {
				return false;
			}
		}

		return false;
	}
}
