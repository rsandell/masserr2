package se.tdt.bobby.wodcc.ui.components.view;

import javax.swing.*;
import java.awt.*;

/**
 * Description
 *
 * Created: 2004-feb-18 20:15:15
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class WhiteJPanel extends JPanel {

    /**
     * Creates a new JPanel with the specified layout manager and buffering
     * strategy.
     *
     * @param layout  the LayoutManager to use
     * @param isDoubleBuffered  a boolean, true for double-buffering, which
     *        uses additional memory space to achieve fast, flicker-free
     *        updates
     */
    public WhiteJPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        init();
    }

    /**
     * Create a new buffered JPanel with the specified layout manager
     *
     * @param layout  the LayoutManager to use
     */
    public WhiteJPanel(LayoutManager layout) {
        super(layout);
        init();
    }

    /**
     * Creates a new <code>JPanel</code> with <code>FlowLayout</code>
     * and the specified buffering strategy.
     * If <code>isDoubleBuffered</code> is true, the <code>JPanel</code>
     * will use a double buffer.
     *
     * @param isDoubleBuffered  a boolean, true for double-buffering, which
     *        uses additional memory space to achieve fast, flicker-free
     *        updates
     */
    public WhiteJPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        init();
    }

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public WhiteJPanel() {
        init();
    }

    private void init() {
        //setForeground(Color.white);
        setBackground(Color.white);
    }
}
