package se.tdt.bobby.wodcc.ui.components.view;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Description.
 * <p/>
 * Created: 2004-jul-12 00:29:13
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class MDBFileFilter extends FileFilter {
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        else if (f.exists()) {
            if (f.getName().toLowerCase().endsWith(".mdb")) {
                return true;
            }
            else {
                return false;
            }
        }

        return false;
    }

    public String getDescription() {
        return "MS Access Database Files (.mdb)";
    }
}

