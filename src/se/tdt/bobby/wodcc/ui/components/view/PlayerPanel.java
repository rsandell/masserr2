package se.tdt.bobby.wodcc.ui.components.view;

import se.tdt.bobby.wodcc.data.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Description.
 * <p/>
 * Created: 2004-jun-30 19:03:44
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class PlayerPanel extends JPanel {
    private Player mEditPlayer;
    private JTextField mNameField;
    private JTextArea mAddressArea;
    private JTextField mPhoneField;
    private JTextField mEmailField;

    public PlayerPanel(Player pEditPlayer, ActionListener pListener) {
        mEditPlayer = pEditPlayer;
        initPanel(pListener);
    }

    public PlayerPanel(ActionListener pListener) {
        mEditPlayer = null;
        initPanel(pListener);
    }

    public Player getNewPlayer() {
        int id = -1;
        if (mEditPlayer != null) {
            id = mEditPlayer.getId();
        }
        Player player = new Player(id, mNameField.getText(), mAddressArea.getText(), mPhoneField.getText(), mEmailField.getText());
        return player;
    }

    private void initPanel(ActionListener pListener) {
        mNameField = new JTextField(25);
        mAddressArea = new JTextArea(4, 25);
        mPhoneField = new JTextField(25);
        mEmailField = new JTextField(25);
        JPanel panel = new JPanel(new SpringLayout());
        JLabel label = new JLabel("Name: ");
        label.setLabelFor(mNameField);
        panel.add(label);
        panel.add(mNameField);
        label = new JLabel("Address: ");
        JScrollPane scrollPane = new JScrollPane(mAddressArea);
        //scrollPane.setPreferredSize(mAddressArea.getPreferredSize());
        label.setLabelFor(mAddressArea);
        panel.add(label);
        panel.add(scrollPane);
        label = new JLabel("Phone: ");
        label.setLabelFor(mPhoneField);
        panel.add(label);
        panel.add(mPhoneField);
        label = new JLabel("Email: ");
        label.setLabelFor(mEmailField);
        panel.add(label);
        panel.add(mEmailField);
        SpringUtilities.makeCompactGrid(panel,
                                        4, 2, //rows, cols
                                        4, 4, //initX, initY
                                        4, 4);       //xPad, yPad
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btn = new JButton("OK");
        btn.setActionCommand("ok");
        btn.addActionListener(pListener);
        btn.setDefaultCapable(true);
        btnPanel.add(btn);
        btn = new JButton("Cancel");
        btn.setActionCommand("cancel");
        btn.addActionListener(pListener);
        btnPanel.add(btn);
        add(btnPanel, BorderLayout.SOUTH);
        if (mEditPlayer != null) {
            mNameField.setText(mEditPlayer.getName());
            mAddressArea.setText(mEditPlayer.getAddress());
            mPhoneField.setText(mEditPlayer.getPhone());
            mEmailField.setText(mEditPlayer.getEmail());
        }
    }

    public void setPlayer(Player pSelectedPlayer) {
        mEditPlayer = pSelectedPlayer;
        mNameField.setText(mEditPlayer.getName());
        mAddressArea.setText(mEditPlayer.getAddress());
        mPhoneField.setText(mEditPlayer.getPhone());
        mEmailField.setText(mEditPlayer.getEmail());
    }
}
