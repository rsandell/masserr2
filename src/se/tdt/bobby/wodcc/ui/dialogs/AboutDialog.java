package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.ui.Utilities;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-30 15:54:14
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class AboutDialog extends JDialog {

    public AboutDialog(Frame owner) throws HeadlessException {
        super(owner, "About", true);
        setAlwaysOnTop(true);
        setUndecorated(true);
        JLabel aboutLabel = new JLabel(new ImageIcon("img/about.jpg"));
        //JLayeredPane lPane = new JLayeredPane();
        aboutLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white, Color.black));
        //aboutLabel.setBounds(0, 0, 320, 200);
        getContentPane().add(aboutLabel, BorderLayout.CENTER);

        JLabel label = new JLabel("x");
        label.setForeground(Color.white);
        label.setBounds(310, 1, 20, 20);
        aboutLabel.add(label);
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }
        });

        //getContentPane().add(lPane, BorderLayout.CENTER);
        //setSize(320, 200);
        pack();
        setResizable(false);
        Utilities.positionMe(this);
    }
}
