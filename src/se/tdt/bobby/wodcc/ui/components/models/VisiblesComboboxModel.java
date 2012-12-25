package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.IntWithString;

import javax.swing.*;
import java.util.Vector;

/**
 * Description
 *
 * Created: 2004-feb-15 20:10:43
 * @author <a href="mailto:the.bobby.is@home.se">Robert "Bobby" Sandell</a>
 */
public class VisiblesComboboxModel extends DefaultComboBoxModel {
    private Vector mAllElements;
    //private Vector mVisible;
    private static final boolean DEBUG = false;

    public VisiblesComboboxModel() {
        mAllElements = new Vector();
    }

    public VisiblesComboboxModel(Vector pAllElements) {
        super(copyVector(pAllElements));
        mAllElements = copyVector(pAllElements);

    }

    public void showAll() {
        super.removeAllElements();
        for (int i = 0; i < mAllElements.size(); i++) {
            Object o = mAllElements.elementAt(i);
            super.addElement(o);
        }
    }

    private static Vector copyVector(Vector pVector) {
        Vector v = new Vector(pVector.size());
        for (int i = 0; i < pVector.size(); i++) {
            Object o = pVector.elementAt(i);
            v.addElement(o);
        }
        return v;
    }

    public void show(Object pObject) {
        if (!mAllElements.contains(pObject)) {
            mAllElements.addElement(pObject);
        }
        super.addElement(pObject);
    }

    public void show(int pIntWithStringNumber) {
        for (int i = 0; i < mAllElements.size(); i++) {
            se.tdt.bobby.wodcc.data.IntWithString intWithString = (se.tdt.bobby.wodcc.data.IntWithString) mAllElements.elementAt(i);
            if (intWithString.getNumber() == pIntWithStringNumber) {
                super.addElement(intWithString);
            }
        }
    }

    public void hide(int pIntWithStringNumber) {
        for (int i = 0; i < mAllElements.size(); i++) {
            se.tdt.bobby.wodcc.data.IntWithString intWithString = (se.tdt.bobby.wodcc.data.IntWithString) mAllElements.elementAt(i);
            if (intWithString.getNumber() == pIntWithStringNumber) {
                super.removeElement(intWithString);
            }
        }
    }

    public void hide(Object pObject) {
        if (!mAllElements.contains(pObject)) {
            mAllElements.addElement(pObject);
        }
        super.removeElement(pObject);
    }

    // implements javax.swing.MutableComboBoxModel
    public void addElement(Object anObject) {
        super.addElement(anObject);
        mAllElements.addElement(anObject);
    }

    // implements javax.swing.MutableComboBoxModel
    public void insertElementAt(Object anObject, int index) {
        super.insertElementAt(anObject, index);

        mAllElements.addElement(anObject);
    }

    // implements javax.swing.MutableComboBoxModel
    public void removeElementAt(int index) {
        super.removeElementAt(index);
    }

    // implements javax.swing.MutableComboBoxModel
    public void removeElement(Object anObject) {
        super.removeElement(anObject);
        mAllElements.removeElement(anObject);
    }

    public void removeAllElements() {
        super.removeAllElements();
        mAllElements.removeAllElements();
    }
}
