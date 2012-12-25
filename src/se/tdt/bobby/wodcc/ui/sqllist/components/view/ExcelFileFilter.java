package se.tdt.bobby.wodcc.ui.sqllist.components.view;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created: 2006-jul-25 01:48:38
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class ExcelFileFilter extends FileFilter {
    public boolean accept(File f) {
        if (f.exists()) {
            if (f.isFile()) {
                return f.getName().toLowerCase().endsWith(".xls");
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public String getDescription() {
        return "Excel Files (*.xls)";
    }
}
