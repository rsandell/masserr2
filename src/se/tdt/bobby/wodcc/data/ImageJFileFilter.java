package se.tdt.bobby.wodcc.data;

import java.io.File;

/**
 * Description
 *
 * Created: 2004-feb-26 18:34:33
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class ImageJFileFilter extends javax.swing.filechooser.FileFilter {

    public boolean accept(File pathname) {
        if (pathname.isDirectory()) {
            return true;
        }
        String fileName = pathname.getName().toLowerCase();
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".gif") || fileName.endsWith(".png")) {
            return true;
        }
        return false;
    }

    public String getDescription() {
        return "Image files (*.jpg, *.gif, *.png)";
    }
}
