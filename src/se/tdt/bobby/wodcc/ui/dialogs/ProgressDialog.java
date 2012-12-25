package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.ui.components.GridBagPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Description
 *
 * Created: 2004-feb-19 15:18:22
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class ProgressDialog extends JDialog {
    private JProgressBar mProgressBar;
    private JLabel mLabel;

    /**
     * Creates a non-modal dialog without a title and without a specified
     * <code>Frame</code> owner.  A shared, hidden frame will be
     * set as the owner of the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public ProgressDialog() throws HeadlessException {
        setTitle("Progress");
        mProgressBar = new JProgressBar(0, 100);
        GridBagPanel panel = new GridBagPanel();
        panel.setComponentInsets(new Insets(4, 4, 4, 4));
        panel.setIpadx(2);
        panel.setIpady(2);
        panel.addLine(mProgressBar);
        mLabel = new JLabel("Message");
        panel.addLine(mLabel);
        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
    }

    public void setMinimum(int pMin) {
        mProgressBar.setMinimum(pMin);
    }

    public void setMaximum(int pMax) {
        mProgressBar.setMaximum(pMax);
    }

    public void setValue(int pValue) {
        mProgressBar.setValue(pValue);
    }

    public void setText(String pText) {
        mLabel.setText(pText);
    }

    public int getMinimum() {
        return mProgressBar.getMinimum();
    }

    public int getMaximum() {
        return mProgressBar.getMaximum();
    }

    public int getValue() {
        return mProgressBar.getValue();
    }

    public String getText() {
        return mLabel.getText();
    }
}
