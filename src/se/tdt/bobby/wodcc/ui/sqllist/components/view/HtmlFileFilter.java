package se.tdt.bobby.wodcc.ui.sqllist.components.view;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created: 2006-jul-25 01:52:02
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class HtmlFileFilter extends FileFilter {
    public boolean accept(File f) {
        if(f.exists()) {
            if(f.isFile()) {
                String name = f.getName().toLowerCase();
                return name.endsWith(".html") || name.endsWith(".htm");
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
        return "Html Files (*.html | *.htm)";
    }
}
