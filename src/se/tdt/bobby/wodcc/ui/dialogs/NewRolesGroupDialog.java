package se.tdt.bobby.wodcc.ui.dialogs;

import se.tdt.bobby.wodcc.data.ImageJFileFilter;
import se.tdt.bobby.wodcc.data.RolesGroup;
import se.tdt.bobby.wodcc.data.mgm.OperationDeniedException;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.logic.IconFactory;
import se.tdt.bobby.wodcc.proxy.interfaces.GroupsDB;
import se.tdt.bobby.wodcc.ui.Utilities;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * Description
 * <p/>
 * Created: 2004-feb-26 15:11:17
 *
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class NewRolesGroupDialog extends JDialog implements ActionListener {
    private JTextField mNameField;
    private GroupsDB mGroupsDB;
    private JComboBox mTypesCombo;
    private JLabel mNewTypeLabel;
    private JLabel mNewTypeIconLabel;
    private JTextField mNewTypeNameField;
    private JTextField mTypeIconField;
    private JButton mBrowseIconBtn;
    private JFileChooser mFileChooser;
    private static final boolean DEBUG = false;
    private JTextField mDateField;
    private JTextArea mDescriptionArea;
    private Integer mParent;
    private RolesGroup mCreatedGroup = null;

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("typeChanged")) {
            if (mTypesCombo.getSelectedItem().equals("<<New>>")) {
                mNewTypeLabel.setEnabled(true);
                mNewTypeIconLabel.setEnabled(true);
                mNewTypeNameField.setEnabled(true);
                mTypeIconField.setEnabled(true);
                mBrowseIconBtn.setEnabled(true);
            }
            else {
                mNewTypeLabel.setEnabled(false);
                mNewTypeIconLabel.setEnabled(false);
                mNewTypeNameField.setEnabled(false);
                mTypeIconField.setEnabled(false);
                mBrowseIconBtn.setEnabled(false);
            }
        }
        else if (e.getActionCommand().equals("browseIcon")) {
            if (mFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = mFileChooser.getSelectedFile();
                try {
                    mTypeIconField.setText(f.getCanonicalPath());
                }
                catch (IOException e1) {
                    if (DEBUG) e1.printStackTrace();
                }
            }
        }
        else if (e.getActionCommand().equals("OK")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    boolean cont = true;
                    try {
                        if (mNewTypeNameField.isEnabled()) {
                            if (mTypeIconField.getText().length() > 0) {
                                File f = new File(mTypeIconField.getText());
                                cont = saveImage(f, mNewTypeNameField.getText());
                            }
                        }
                        if (cont) {
                            createGroup();
                            setVisible(false);
                        }
                        else {
                            JOptionPane.showMessageDialog(mNewTypeLabel, "Unable to write the image to disk", "IO ERROR!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    catch (Exception e1) {
                        if (DEBUG) e1.printStackTrace();
                        report(e1);
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else if (e.getActionCommand().equals("cancel")) {
            mCreatedGroup = null;
            setVisible(false);
        }
    }

    private void report(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    private void createGroup() throws Exception, SQLException {
        String name = mNameField.getText();
        String type = null;
        if (mNewTypeNameField.isEnabled()) {
            type = mNewTypeNameField.getText();
        }
        else {
            type = (String) mTypesCombo.getSelectedItem();
        }
        Date date = DateFormat.getDateInstance(DateFormat.SHORT).parse(mDateField.getText());
        String descr = mDescriptionArea.getText();
        try {
            if (mCreatedGroup != null) {
                mCreatedGroup.setName(name);
                mCreatedGroup.setType(type);
                mCreatedGroup.setDate(date);
                mCreatedGroup.setDescription(descr);
                mGroupsDB.updateGroup(mCreatedGroup);
            }
            else {
                mCreatedGroup = mGroupsDB.createGroup(name, type, descr, date, mParent);
            }
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            mCreatedGroup = null;
            throw e;
        }
    }

    private boolean saveImage(File pFromFile, String pTypeName) throws IOException, OperationDeniedException {
        BufferedImage image = ImageIO.read(pFromFile);
        if (DEBUG) System.out.println("[NewRolesGroupDialog][saveImage][123] image: " + image);
        if (image.getHeight() > 16) {
            int choice = JOptionPane.showConfirmDialog(this, "The selected image is higher than recommended would you like me to shrink it?", "Image Size", JOptionPane.YES_NO_CANCEL_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                Image smallImage = image.getScaledInstance(-1, 16, Image.SCALE_DEFAULT);
                BufferedImage subImg = image.getSubimage(0, 0, smallImage.getWidth(this), smallImage.getHeight(this));
                subImg.getGraphics().drawImage(smallImage, 0, 0, this);
                subImg.flush();
                image = subImg;
                if (DEBUG) System.out.println("[NewRolesGroupDialog][saveImage][132] image: " + image);
            }
            else if (choice != JOptionPane.NO_OPTION) {
                return false;
            }
        }
        String mime = Utilities.getImageMimeType(pFromFile);
        if (mime != null) {
            String fileName = pFromFile.getName();
            String newFileName = pTypeName.trim().toLowerCase() + fileName.substring(fileName.lastIndexOf('.'));
            File toFile = new File(IconFactory.getGroupTypesIconDir(), newFileName);
            /*if (DEBUG) System.out.println("[NewRolesGroupDialog][saveImage][143] writing " + image);
            if (!ImageIO.write(image, mime, toFile)) {
                newFileName += ".png";
                toFile = new File(newFileName);
                ImageIO.write(image, "image/png", toFile);
            } */
            if (writeImage(image, toFile)) {
                IconFactory.getIconFactory().setRolesGroupTypeIcon(image, pTypeName);
                if (DEBUG) System.out.println("[NewRolesGroupDialog][saveImage][157] true");
                return true;
            }
            else {
                if (DEBUG) System.out.println("[NewRolesGroupDialog][saveImage][160] false");
                return false;
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "The file does not have a recognizable format!");
            return false;
        }
    }

    private boolean writeImage(BufferedImage pImage, File pToFile) throws IOException, OperationDeniedException {
        File writenFile = IconFactory.getIconFactory().writeImage(pImage, pToFile);
        if (writenFile != null) {
            RandomAccessFile read = new RandomAccessFile(writenFile, "r");
            byte[] bytes = new byte[(int)read.length()];
            read.readFully(bytes);
            mGroupsDB.storeGroupTypeIcon(writenFile.getName(), bytes);
            return true;
        }
        else {
            return false;
        }
    }

    public NewRolesGroupDialog(Frame owner, Integer pParent) throws HeadlessException, SQLException, RemoteException {
        super(owner, "New RolesGroup", true);
        mParent = pParent;
        init();
    }

    public NewRolesGroupDialog(Dialog owner, Integer pParent) throws HeadlessException, SQLException, RemoteException {
        super(owner, "New RolesGroup", true);
        mParent = pParent;
        init();
    }

    public NewRolesGroupDialog(Frame owner, RolesGroup pEditGroup) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Edit RolesGroup", true);
        mParent = null;
        mCreatedGroup = pEditGroup;
        init();
    }

    public NewRolesGroupDialog(Dialog owner, RolesGroup pEditGroup) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Edit RolesGroup", true);
        mParent = null;
        mCreatedGroup = pEditGroup;
        init();
    }

    private void init() throws SQLException, RemoteException {
        mGroupsDB = Proxy.getGroupsDB();
        mFileChooser = new JFileChooser();
        mFileChooser.setFileFilter(new ImageJFileFilter());
        mFileChooser.setAcceptAllFileFilterUsed(false);
        mFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        mFileChooser.setMultiSelectionEnabled(false);
        GridBagPanel panel = new GridBagPanel();
        mNameField = new JTextField(35);
        panel.addLine("Name: ", mNameField);
        Vector groupTypes = mGroupsDB.getGroupTypes();
        groupTypes.add(0, "<<New>>");
        mTypesCombo = new JComboBox(groupTypes);
        mTypesCombo.setActionCommand("typeChanged");
        mTypesCombo.addActionListener(this);
        panel.addLine("Type: ", mTypesCombo);
        mNewTypeLabel = new JLabel("Type Name: ");
        mNewTypeLabel.setEnabled(false);
        mNewTypeNameField = new JTextField(35);
        mNewTypeNameField.setEnabled(false);
        panel.addLine(mNewTypeLabel, mNewTypeNameField);
        mNewTypeIconLabel = new JLabel("Type Icon: ");
        mNewTypeIconLabel.setEnabled(false);
        mTypeIconField = new JTextField(15);
        mTypeIconField.setEnabled(false);
        mTypeIconField.setToolTipText("Leave Empty For standard Icon");
        mBrowseIconBtn = new JButton("...");
        mBrowseIconBtn.setActionCommand("browseIcon");
        mBrowseIconBtn.addActionListener(this);
        mBrowseIconBtn.setMargin(new Insets(1, 1, 1, 1));
        mBrowseIconBtn.setEnabled(false);
        JPanel p = new JPanel(new BorderLayout());
        p.add(mTypeIconField, BorderLayout.CENTER);
        p.add(mBrowseIconBtn, BorderLayout.EAST);
        panel.addLine(mNewTypeIconLabel, p);
        mDateField = new JTextField(35);
        panel.addLine("Date: ", mDateField);
        mDescriptionArea = new JTextArea(5, 35);
        mDescriptionArea.setWrapStyleWord(true);
        mDescriptionArea.setLineWrap(true);
        //JScrollPane scroll = new JScrollPane(mDescriptionArea);
        //scroll.setPreferredSize(new Dimension(50, 160));
        panel.addLine("Description: ", mDescriptionArea);
        getContentPane().add(panel, BorderLayout.CENTER);

        p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btn = new JButton("OK");
        btn.setActionCommand("OK");
        btn.addActionListener(this);
        getRootPane().setDefaultButton(btn);
        p.add(btn);
        btn = new JButton("Cancel");
        btn.setActionCommand("cancel");
        btn.addActionListener(this);
        p.add(btn);
        getContentPane().add(p, BorderLayout.SOUTH);
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);

        if (mCreatedGroup != null) {
            mNameField.setText(mCreatedGroup.getName());
            mDateField.setText(format.format(mCreatedGroup.getDate()));
            mDescriptionArea.setText(mCreatedGroup.getDescription());
            String type = mCreatedGroup.getType();
            for (int i = 0; i < groupTypes.size(); i++) {
                String s = (String) groupTypes.elementAt(i);
                if (type.equals(s)) {
                    mTypesCombo.setSelectedItem(s);
                }
            }
        }
        else {
            mDateField.setText(format.format(Calendar.getInstance().getTime()));
            if (groupTypes.size() > 1) {
                mTypesCombo.setSelectedIndex(1);
            }
            else {
                mTypesCombo.setSelectedIndex(0);
            }
        }
        pack();
        Utilities.positionMe(this);
    }

    public static RolesGroup showDialog(Dialog pOwner, Integer pParent) throws SQLException, RemoteException {
        NewRolesGroupDialog dialog = new NewRolesGroupDialog(pOwner, pParent);
        return showDialog(dialog);
    }

    public static RolesGroup showDialog(Frame pOwner, Integer pParent) throws SQLException, RemoteException {
        NewRolesGroupDialog dialog = new NewRolesGroupDialog(pOwner, pParent);
        return showDialog(dialog);
    }

    public static RolesGroup showEditDialog(Dialog pOwner, RolesGroup pGroup) throws SQLException, RemoteException {
        NewRolesGroupDialog dialog = new NewRolesGroupDialog(pOwner, pGroup);
        return showDialog(dialog);
    }

    public static RolesGroup showEditDialog(Frame pOwner, RolesGroup pGroup) throws SQLException, RemoteException {
        NewRolesGroupDialog dialog = new NewRolesGroupDialog(pOwner, pGroup);
        return showDialog(dialog);
    }

    private static RolesGroup showDialog(NewRolesGroupDialog pDialog) {
        pDialog.setVisible(true);
        return pDialog.mCreatedGroup;
    }
}
