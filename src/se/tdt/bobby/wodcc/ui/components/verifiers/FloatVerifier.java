package se.tdt.bobby.wodcc.ui.components.verifiers;

import javax.swing.*;
import javax.swing.text.JTextComponent;

/**
 * Description
 *
 * 
 * Created: 2004-feb-05 19:31:16
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class FloatVerifier extends InputVerifier {

	public boolean verify(JComponent input) {
		if (input instanceof JTextComponent) {
			JTextComponent c = (JTextComponent) input;
			try {
				Float.parseFloat(c.getText());
				return true;
			}
			catch (NumberFormatException e) {
				return false;
			}
		}

		return false;
	}
}
