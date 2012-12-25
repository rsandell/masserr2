package se.tdt.bobby.wodcc.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Description
 *
 * 
 * Created: 2004-jan-10 02:16:14
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class GridBagPanel extends JPanel {
	private GridBagLayout gridbag = new GridBagLayout();
    private GridBagConstraints c = new GridBagConstraints();

    public GridBagPanel() {
        setLayout(gridbag);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 1;
    }

    /**
     * @see java.awt.GridBagConstraints#fill
     */
    public void setFill(int fill) {
        c.fill = fill;
    }

    /**
     * @see java.awt.GridBagConstraints#fill
     */
    public int getFill() {
        return c.fill;
    }

    /**
     * @see java.awt.GridBagConstraints#anchor
     */
    public void setAnchor(int anchor) {
        c.anchor = anchor;
    }

    /**
     * @see java.awt.GridBagConstraints#anchor
     */
    public int getAnchor() {
        return c.anchor;
    }

    /**
     * @see java.awt.GridBagConstraints
     */
    public void setComponentInsets(Insets insets) {
        c.insets = insets;
    }

    /**
     * @see java.awt.GridBagConstraints
     */
    public void setIpadx(int ipadx) {
        c.ipadx = ipadx;
    }

    /**
     * @see java.awt.GridBagConstraints
     */
    public void setIpady(int ipady) {
        c.ipady = ipady;
    }

    /**
     * @see java.awt.GridBagConstraints
     */
    public void setGridHeight(int gridheight) {
        c.gridheight = gridheight;
    }

    /**
     * @see java.awt.GridBagConstraints
     */
    public void setGridWidth(int gridwidth) {
        c.gridwidth = gridwidth;
    }

    /**
     * Adds a component to the JPanel with the current GridBagConstraits.
     * Works like System.print("component") but with components in this JPanel. By setting the
     * gridwidth by hand to REMAINDER makes it be like System.print("component\n").
     */
    public Component add(Component component) {
        gridbag.setConstraints(component, c);
        return super.add(component);
    }

	public Component add(String pLabel) {
		JLabel component = new JLabel(pLabel);
        gridbag.setConstraints(component, c);
        return super.add(component);
    }

    /**
     * Adds a component to the JPanel width the current GridBagContraints and adds a newLine.
     * This is done by setting the GridBagConstraints gridwidth to REMAINDER.
     * Works like System.println("component") but with components in this JPanel.
     */
    public Component addLine(Component component) {
        int oldgridwidth = c.gridwidth;
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridbag.setConstraints(component, c);
        Component retr = super.add(component);
        c.gridwidth = oldgridwidth;
        return retr;
    }

	public Component addLine(String pLabel) {
		JLabel component = new JLabel(pLabel);
        int oldgridwidth = c.gridwidth;
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridbag.setConstraints(component, c);
        Component retr = super.add(component);
        c.gridwidth = oldgridwidth;
        return retr;
    }

	public void addLine(Component component, Component pEndlComponent) {
        int oldgridwidth = c.gridwidth;
        gridbag.setConstraints(component, c);
		super.add(component);
		c.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridbag.setConstraints(pEndlComponent, c);
        super.add(pEndlComponent);
		c.gridwidth = oldgridwidth;
    }

	public void addLine(String pLabel, Component pEndlComponent) {
        int oldgridwidth = c.gridwidth;
		JLabel label = new JLabel(pLabel);
        gridbag.setConstraints(label, c);
		super.add(label);
		c.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridbag.setConstraints(pEndlComponent, c);
        super.add(pEndlComponent);
		c.gridwidth = oldgridwidth;
    }
}
