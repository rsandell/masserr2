package se.tdt.bobby.wodcc.logic;

import javax.swing.*;
import java.io.File;
import java.awt.*;

/**
 * Description
 *
 *
 * Created: 2004-jan-20 20:51:09
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class FileQuestionaire extends Thread {

	private Process mProcess;
	private File mFile;
	private Component mDialogOwner;
	private static final boolean DEBUG = false;

	public FileQuestionaire(Process pProcess, File pFile, Component pDialogOwner) {
		mProcess = pProcess;
		mFile = pFile;
		mDialogOwner = pDialogOwner;
		this.start();
	}

	public void run() {
		try {
			mProcess.waitFor();
			/*int result = JOptionPane.showConfirmDialog(mDialogOwner, "Do you want to save this file?", "Save?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				JFileChooser chooser = new JFileChooser();
				boolean continueToAsk = true;

				while (continueToAsk) {
					int cRes = chooser.showSaveDialog(mDialogOwner);
					if (cRes == JFileChooser.APPROVE_OPTION) {
						File toFile = chooser.getSelectedFile();
						if (toFile.exists()) {
							if (toFile.isDirectory()) {
								JOptionPane.showMessageDialog(mDialogOwner, "That is a directory!", "Save error", JOptionPane.ERROR_MESSAGE);
								continueToAsk = true;
								continue;
							}
							else {
								result = JOptionPane.showConfirmDialog(mDialogOwner, "The file " + toFile.getName() + " exsists.\nDo you want to overwrite it?", "Overwrite?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
								if (result == JOptionPane.YES_OPTION) {

								}
								else if (result == JOptionPane.NO_OPTION) {
									continueToAsk = true;
									continue;
								}
								else {
									continueToAsk = false;
									break;
								}
							}
						}
						mFile.renameTo(toFile);
						continueToAsk = false;
					}
					else {
						continueToAsk = false;
					}
				}
			}
			else {
				mFile.delete();
			}*/
            mFile.delete();            
		}
		catch (InterruptedException e) {
			if (DEBUG) e.printStackTrace();
		}
	}
}
