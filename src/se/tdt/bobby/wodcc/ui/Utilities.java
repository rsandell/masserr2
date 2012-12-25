package se.tdt.bobby.wodcc.ui;

import se.tdt.bobby.wodcc.db.AppPreferences;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Description
 *
 *
 * Created: 2004-jan-10 02:04:37
 * @author <a href="the.bobby.is@home.se"> Robert Sandell</a>
 */
public class Utilities {
	private static final boolean DEBUG = false;

	public static JSpinner createIntegerJSpinner(int pMinimum, int pMaximum, int pStepSize, int pInitialValue) {
		SpinnerNumberModel model = new SpinnerNumberModel(pInitialValue, pMinimum, pMaximum, pStepSize);
		return new JSpinner(model);
	}

	public static void changeSpinnerAttributes(JSpinner pSpinner, int pMinimum, int pMaximum, int pStepSize) {
		SpinnerNumberModel model = (SpinnerNumberModel) pSpinner.getModel();
		SpinnerNumberModel newModel = new SpinnerNumberModel(model.getNumber().intValue(), pMinimum, pMaximum, pStepSize);
		pSpinner.setModel(newModel);
	}

	public static int getSpinnerInteger(JSpinner pSpinner) {
		Number n = (Number) pSpinner.getValue();
		return n.intValue();
	}

	public static Process startExplorer(String pParams) {
		try {
			Runtime run = Runtime.getRuntime();
			//return run.exec("C:/Program/Internet Explorer/iexplore.exe " + pParams);
            return run.exec(AppPreferences.getExplorer() + " " + pParams);
		}
		catch (IOException e) {
			if (DEBUG) e.printStackTrace();
		}
		return null;
	}

	public static void positionMe(JDialog pDialog) {
		Window w = pDialog.getOwner();
		Point ownerLocation = w.getLocation();
		Dimension ownerSize = w.getSize();
		Point dialogLocation = pDialog.getLocation();
		Dimension dialogSize = pDialog.getSize();
		int centerX = (int) (ownerSize.getWidth() / 2f + ownerLocation.x);
		dialogLocation.x = Math.max(0, (int) (centerX - (dialogSize.getWidth() / 2)));

		int centerY = (int) (ownerSize.getHeight() / 2 + ownerLocation.y);
		dialogLocation.y = Math.max(0, (int) (centerY - (dialogSize.getHeight() / 2)));
		pDialog.setLocation(dialogLocation);
	}

    public static void positionMe(JFrame pMe, JFrame pTo) {
		Window w = pTo;
		Point ownerLocation = w.getLocation();
		Dimension ownerSize = w.getSize();
		Point dialogLocation = pMe.getLocation();
		Dimension dialogSize = pMe.getSize();
		int centerX = (int) (ownerSize.getWidth() / 2f + ownerLocation.x);
		dialogLocation.x = Math.max(0, (int) (centerX - (dialogSize.getWidth() / 2)));

		int centerY = (int) (ownerSize.getHeight() / 2 + ownerLocation.y);
		dialogLocation.y = Math.max(0, (int) (centerY - (dialogSize.getHeight() / 2)));
		pMe.setLocation(dialogLocation);
	}

    public static String getImageMimeType(File pFromFile) {
        String fileName = pFromFile.getName();
        if(fileName.endsWith(".jpg") || fileName.endsWith("jpeg")) {
            return "image/jpeg";
        }
        else if(fileName.endsWith(".gif")) {
            return "image/gif";
        }
        else if(fileName.endsWith(".png")) {
            return "image/png";
        }
        return null;
    }

    public static void centerMeOnScreen(Dialog pDialog) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension d = pDialog.getSize();
        int x = screenSize.width / 2 - d.width / 2;
        int y = screenSize.height / 2 - d.height / 2;
        pDialog.setLocation(x, y);
    }

    public static java.util.List deepClone(java.util.List pList) throws IllegalAccessException, InstantiationException {
        java.util.List newList = (java.util.List) pList.getClass().newInstance();
        for (int i = 0; i < pList.size(); i++) {
            Cloneable cloneable = (Cloneable) pList.get(i);
            newList.add(cloneable);
        }
        return newList;
    }
}
