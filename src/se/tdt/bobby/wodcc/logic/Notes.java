package se.tdt.bobby.wodcc.logic;

import java.util.List;
import java.util.ArrayList;

/**
 * Description
 *
 * 
 * Created: 2004-jan-19 00:56:40
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class Notes {
	private List mNotes;

	public Notes() {
		mNotes = new ArrayList();
		mNotes.add("");
	}

	public int addNote(String pNote) {
		mNotes.add(pNote);
		return mNotes.size() - 1;
	}

	public String getNote(int pNoteNr) {
		return (String) mNotes.get(pNoteNr);
	}

	public int size() {
		return mNotes.size();
	}

	public void clear() {
		mNotes = new ArrayList();
		mNotes.add("");
	}
}
