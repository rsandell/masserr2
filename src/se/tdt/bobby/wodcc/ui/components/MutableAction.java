package se.tdt.bobby.wodcc.ui.components;

import javax.swing.*;

/**
 * Description
 *
 *
 * Created: 2003-jul-16 13:28:27
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public abstract class MutableAction extends AbstractAction {

	public MutableAction(String name) {
		super(name);
	}

	public MutableAction(String name, int pMnemonic) {
		super(name);
		putValue(Action.MNEMONIC_KEY, new Integer(pMnemonic));
	}

	public MutableAction(String name, KeyStroke pAccelerator) {
		super(name);
        this.putValue(Action.ACCELERATOR_KEY, pAccelerator);
	}

    public MutableAction(String name, Icon pIcon) {
		super(name, pIcon);
	}

	public MutableAction(String name, int pMnemonic, Icon pIcon) {
		super(name, pIcon);
		putValue(Action.MNEMONIC_KEY, new Integer(pMnemonic));
	}

	public MutableAction(String name, KeyStroke pAccelerator, Icon pIcon) {
		super(name, pIcon);
        this.putValue(Action.ACCELERATOR_KEY, pAccelerator);
	}

    public void setToolTipText(String pToolTip) {
        putValue(Action.SHORT_DESCRIPTION, pToolTip);
    }

    public String getToolTipText() {
        return (String) getValue(Action.SHORT_DESCRIPTION);
    }
}