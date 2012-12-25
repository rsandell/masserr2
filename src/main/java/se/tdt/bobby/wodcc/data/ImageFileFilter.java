package se.tdt.bobby.wodcc.data;

//import java.io.FileFilter;
import java.io.File;

/**
 * Description
 *
 * Created: 2004-feb-26 16:05:36
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class ImageFileFilter implements java.io.FileFilter {

    public boolean accept(File pathname) {
        String fileName = pathname.getName().toLowerCase();
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".gif") || fileName.endsWith(".png")) {
            return true;
        }
        return false;
    }
}
