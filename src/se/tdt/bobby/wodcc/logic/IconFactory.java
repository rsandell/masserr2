package se.tdt.bobby.wodcc.logic;

import se.tdt.bobby.wodcc.data.ImageFileFilter;
import se.tdt.bobby.wodcc.server.ServerPreferences;

import javax.swing.*;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Description
 *
 * Created: 2004-feb-18 19:49:52
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class IconFactory {
    private static IconFactory sInstance = null;
    private HashMap mClanIcons;
    private HashMap mClanIcons16;
    private se.tdt.bobby.wodcc.data.ImageFileFilter mImageFileFilter;
    private HashMap mGroupTypes;
    private static final boolean DEBUG = false;
    private static final String sClansIconDir = "img/clans/";
    private static final String sIconsDir = "img/icons/";
    private static final String sGroupTypesIconDir = "img/groupTypes";
    private HashMap mIcons;

    public static File getClansIconDir() {
        return new File(sClansIconDir);
    }

    public static File getGroupTypesIconDir() {
        return new File(sGroupTypesIconDir);
    }

    private IconFactory() {
        mImageFileFilter = new se.tdt.bobby.wodcc.data.ImageFileFilter();
        File iconDir = getClansIconDir();
        mIcons = new HashMap();
        mClanIcons = new HashMap();
        mClanIcons16 = new HashMap();
        File[] files = iconDir.listFiles(mImageFileFilter);
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isFile()) {
                String fileName = file.getName();
                if (fileName.lastIndexOf('.') > 0) {
                    String number = fileName.substring(0, fileName.lastIndexOf('.'));
                    try {
                        int result = Integer.parseInt(number);
                        ImageIcon img = new ImageIcon(file.getAbsolutePath());
                        Image image = img.getImage();
                        image = image.getScaledInstance(-1, 16, Image.SCALE_DEFAULT);
                        mClanIcons.put(result + "", img);
                        ImageIcon img16 = new ImageIcon(image);
                        mClanIcons16.put(result + "", img16);
                    }
                    catch (NumberFormatException e) {

                    }
                }
            }
        }
        iconDir = getGroupTypesIconDir();
        files = iconDir.listFiles(mImageFileFilter);
        mGroupTypes = new HashMap();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isFile()) {
                if(DEBUG) System.out.println("[IconFactory][IconFactory][57] examining " + file);
                String fileName = file.getName();
                String name = fileName.substring(0, fileName.lastIndexOf('.')).toLowerCase().trim();
                ImageIcon img = new ImageIcon(file.getAbsolutePath());
                if(DEBUG) System.out.println("[IconFactory][IconFactory][59] found an icon: " + name);
                mGroupTypes.put(name, img);
            }
        }
    }

    public ImageIcon getClanIcon(int pId) {
        ImageIcon icon = (ImageIcon) mClanIcons.get(pId + "");
        if (icon != null) {
            return icon;
        }
        else {
            icon = (ImageIcon) mClanIcons.get("0");
            if (icon != null) {
                return icon;
            }
        }
        return null;
    }

    public ImageIcon getClanIcon16(int pId) {
        ImageIcon icon = (ImageIcon) mClanIcons16.get(pId + "");
        if (icon != null) {
            return icon;
        }
        else {
            icon = (ImageIcon) mClanIcons16.get("0");
            if (icon != null) {
                return icon;
            }
        }
        return null;
    }

    public ImageIcon getRolesGroupTypeIcon(String pType) {
        return (ImageIcon) mGroupTypes.get(pType.toLowerCase().trim());
    }

    public void setRolesGroupTypeIcon(Image pImage, String pType) {
        mGroupTypes.put(pType.toLowerCase(), new ImageIcon(pImage));
    }

    public ImageIcon getIcon(String pFileName) {
        ImageIcon icon = (ImageIcon) mIcons.get(pFileName);
        if(icon == null) {
            File f = new File(sIconsDir, pFileName);
            if(f.exists()) {
                icon = new ImageIcon(f.getPath());
                mIcons.put(pFileName, icon);
            }
        }
        return icon;
    }

    public File writeImage(BufferedImage pImage, File pToFile) throws IOException {
        Iterator writers = ImageIO.getImageWritersBySuffix(pToFile.getName().substring(pToFile.getName().lastIndexOf('.') + 1));
        if (writers.hasNext()) {
            ImageWriter writer = (ImageWriter) writers.next();
            ImageOutputStream out = ImageIO.createImageOutputStream(pToFile);
            writer.setOutput(out);
            writer.write(pImage);

            return pToFile;
        }
        else {
            if(DEBUG) System.out.println("IconFactory.writeImage(146) no writer 1");
            writers = ImageIO.getImageWritersByMIMEType("image/png");
            if (writers.hasNext()) {
                ImageWriter writer = (ImageWriter) writers.next();
                String fileName = pToFile.getName();
                fileName = fileName.substring(0, fileName.lastIndexOf('.'));
                fileName += ".png";
                pToFile = new File(pToFile.getParentFile(), fileName);
                ImageOutputStream out = ImageIO.createImageOutputStream(pToFile);
                writer.setOutput(out);
                writer.write(pImage);
                return pToFile;
            }
            else {
                if(DEBUG) System.out.println("IconFactory.writeImage(160) no writer 2");
                return null;
            }
        }
    }

    public static IconFactory getIconFactory() {
        if (sInstance == null) {
            sInstance = new IconFactory();
        }
        return sInstance;
    }

    public static ArrayList<String> getGroupTypeIconFileNames() {
        File dir;
        if(ServerPreferences.getBaseDir() != null) {
            dir = new File(ServerPreferences.getBaseDir(), "img/groupTypes");
        } else {
            dir = new File("img/groupTypes");
        }
        File[] files = dir.listFiles();
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if(file.isFile()) {
                list.add(file.getName());
            }
        }
        return list;
    }
}
