package se.tdt.bobby.wodcc.data;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Description
 *
 *
 * Created: 2004-jan-27 14:27:50
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class TreeNode implements Serializable {
	private Role mData;
	private List mChildren;

    public TreeNode() {
    }

	public TreeNode(Role pData) {
		mData = pData;
		mChildren = new ArrayList();
	}

	public Role getData() {
		return mData;
	}

	public void setData(Role pData) {
		mData = pData;
	}

	public List getChildren() {
		return mChildren;
	}

	public void setChildren(List pChildren) {
		mChildren = pChildren;
	}

	public void addChild(TreeNode pChild) {
		mChildren.add(pChild);
	}

	public String toString() {
		if (mData != null) {
			String toReturn = mData.getName();
            if(mData.getVitals().isFinalDeath()) {
                String style = "\"vertical-align: super; font-size: 8pt; font-style: italic;\"";
                toReturn += " <span style="+style+">(R.I.P)</span>";
            }
            if(mData.isGhoul()) {
                toReturn = "<i>" + toReturn + "</i>";
            }
            return toReturn;
		}
		else {
			return null;
		}
	}
}
