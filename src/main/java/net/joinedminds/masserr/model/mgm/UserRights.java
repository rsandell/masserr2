/*
 * The MIT License
 *
 * Copyright (c) 2004-2012-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.joinedminds.masserr.model.mgm;

import java.io.Serializable;
import java.util.BitSet;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-26 01:35:34
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class UserRights implements Serializable, Cloneable {

    private BitSet bitSet;
    public static final int GIVE_USER_ACCESS_TO_OTHER_DOMAINS = 0;
    public static final int ADD_ROLE_DOMAIN = 1;
    public static final int ADD_ROLE_OTHER_DOMAIN = 2;
    public static final int UPDATE_ROLE_DOMAIN = 3;
    public static final int UPDATE_ROLE_OTHER_DOMAIN = 4;
    public static final int ADD_USERS = 5;
    public static final int UPDATE_USERS = 6;
    public static final int ADD_USERS_OTHER_DOMAIN = 7;
    public static final int UPDATE_USERS_OTHER_DOMAIN = 8;
    public static final int ADD_RESOURCE = 9;
    public static final int LIST_USERS = 10;
    public static final int ADD_RESOURCE_OTHER_DOMAIN = 11;
    public static final int UPDATE_RESOURCE = 12;
    public static final int UPDATE_RESOURCE_OTHER_DOMAIN = 13;
    public static final int ADD_RITUAL = 14;
    public static final int UPDATE_RITUAL = 15;
    public static final int INSERT_EXPERIENCE_DOMAIN = 16;
    public static final int INSERT_EXPERIENCE_OTHER_DOMAIN = 17;
    public static final int INSERT_GROUP_EXPERIENCE = 18;
    public static final int UPDATE_BACKGROUND_AND_WILL_DOMAIN = 19;
    public static final int UPDATE_BACKGROUND_AND_WILL_OTHER_DOMAIN = 20;
    public static final int ADD_REMOVE_CLAN_DISCIPLINES = 21;
    public static final int ADD_UPDATE_DISCIPLINE = 22;
    public static final int ADD_UPDATE_MERIT_OR_FLAW = 23;
    public static final int ADD_UPDATE_OTHER_TRAITS = 24;
    public static final int CREATE_BANKACCOUNT = 25;
    public static final int CREATE_BANKACCOUNT_OTHER_DOMAIN = 26;
    public static final int UPDATE_BANKACCOUNT = 27;
    public static final int UPDATE_BANKACCOUNT_OTHER_DOMAIN = 28;
    public static final int SET_EXTRA_INCOME = 29;
    public static final int SET_EXTRA_INCOME_OTHER_DOMAIN = 30;
    public static final int BUY_OR_SELL_RESOURCE = 31;
    public static final int BUY_OR_SELL_RESOURCE_OTHER_DOMAIN = 32;
    public static final int BANKACCOUNT_WITHDRAWAL = 33;
    public static final int BANKACCOUNT_WITHDRAWAL_OTHER_DOMAIN = 34;
    public static final int ADD_ROLE_TO_GROUP = 35;
    public static final int ADD_ROLE_TO_GROUP_OTHER_DOMAIN = 36;
    public static final int CREATE_GROUP = 37;
    public static final int REMOVE_ROLE_FROM_GROUP = 38;
    public static final int REMOVE_ROLE_FROM_GROUP_OTHER_DOMAIN = 39;
    public static final int DELETE_GROUP = 40;
    public static final int DELETE_GROUP_OTHER_DOMAIN = 41;
    public static final int UPDATE_GROUP = 42;
    public static final int UPDATE_ROLE_PROFESSION = 43;
    public static final int UPDATE_ROLE_PROFESSION_OTHER_DOMAIN = 44;
    public static final int REMOVE_RESOURCE_FROM_ROLE = 45;
    public static final int REMOVE_RESOURCE_FROM_ROLE_OTHER_DOMAIN = 46;
    public static final int ADD_INFLUENCE_TO_ROLE = 47;
    public static final int ADD_INFLUENCE_TO_ROLE_OTHER_DOMAIN = 48;
    public static final int REMOVE_INFLUENCE_FROM_ROLE = 49;
    public static final int REMOVE_INFLUENCE_FROM_ROLE_OTHER_DOMAIN = 50;
    public static final int UPDATE_DOMAIN = 51;
    public static final int UPDATE_OTHER_DOMAIN = 52;
    public static final int ADD_DOMAIN = 53;
    public static final int ADD_PLOT = 54;
    public static final int ADD_PLOT_OTHER_DOMAIN = 55;
    public static final int UPDATE_PLOT = 56;
    public static final int UPDATE_PLOT_OTHER_DOMAIN = 57;
    public static final int ASSIGN_UNASSIGN_PLOT = 58;
    public static final int ASSIGN_UNASSIGN_PLOT_OTHER_DOMAIN = 59;
    public static final int CHANGE_PASSWORD = 60;
    public static final int UPDATE_TEMPLATE = 61;
    public static final int ADD_TEMPLATE = 62;
    public static final int ADD_UPDATE_PLAYERS = 63;
    public static final int[] INFLICTS_ON_OTHER_DOMAINS = {GIVE_USER_ACCESS_TO_OTHER_DOMAINS,
                                                           ADD_ROLE_OTHER_DOMAIN,UPDATE_ROLE_OTHER_DOMAIN,
                                                           ADD_USERS_OTHER_DOMAIN, UPDATE_USERS_OTHER_DOMAIN,
                                                           ADD_RESOURCE_OTHER_DOMAIN, UPDATE_RESOURCE_OTHER_DOMAIN,
                                                           INSERT_EXPERIENCE_OTHER_DOMAIN, INSERT_GROUP_EXPERIENCE,
                                                           UPDATE_BACKGROUND_AND_WILL_OTHER_DOMAIN,
                                                           CREATE_BANKACCOUNT_OTHER_DOMAIN,
                                                           UPDATE_BANKACCOUNT_OTHER_DOMAIN, SET_EXTRA_INCOME_OTHER_DOMAIN,
                                                           BUY_OR_SELL_RESOURCE_OTHER_DOMAIN, BANKACCOUNT_WITHDRAWAL_OTHER_DOMAIN,
                                                           ADD_ROLE_TO_GROUP_OTHER_DOMAIN, REMOVE_ROLE_FROM_GROUP_OTHER_DOMAIN,
                                                           DELETE_GROUP_OTHER_DOMAIN, UPDATE_ROLE_PROFESSION_OTHER_DOMAIN,
                                                           REMOVE_RESOURCE_FROM_ROLE_OTHER_DOMAIN, ADD_INFLUENCE_TO_ROLE_OTHER_DOMAIN,
                                                           REMOVE_INFLUENCE_FROM_ROLE_OTHER_DOMAIN,
                                                           UPDATE_OTHER_DOMAIN, ADD_DOMAIN,
                                                           ADD_PLOT_OTHER_DOMAIN,
                                                           UPDATE_PLOT_OTHER_DOMAIN,
                                                           ASSIGN_UNASSIGN_PLOT_OTHER_DOMAIN};
    public static final String[] sDescriptions = {"Role: Add", "Role: Add to other Domain",
                                                  "Role: Update", "Role: Update in other Domain",
                                                  "Users: Add", "Users: Update",
                                                  "Users: Add to other Domain", "Users: Update in other Domain",
                                                  "Resource: Add", "Users: List",
                                                  "Resource: Add to other Domain", "Resource: Update",
                                                  "Resource: Update in other Domain", "Ritual: Add",
                                                  "Ritual: Update", "Insert Experience on Roles",
                                                  "Insert Experience on Roles in other Domain", "Insert Experience on Group",
                                                  "Role: Update Background and Will", "Role: Update Background and Will in other Domain",
                                                  "Discipline: Add/Remove Clan-Disciplines", "Discipline: Add/Update",
                                                  "Merits & Flaws: Add/Update", "Other Traits: Add/Update",
                                                  "Bank-Account: Create", "Bank-Account: Create in other Domain",
                                                  "Bank-Account: Update", "Bank-Account: Update in other Domain",
                                                  "Extra Income: Set", "Extra Income: Set in other Domain",
                                                  "Resource: Buy for Role", "Resource: Buy for Role in other Domain",
                                                  "Bank-Account: Withdrawal", "Bank-Account: Withdrawal from other Domain",
                                                  "Groups: Add Role", "Groups: Add Role from other Domain",
                                                  "Groups: Create",
                                                  "Groups: Remove Role", "Groups: Remove Role from other Domain",
                                                  "Groups: Delete", "Groups: Delete with Roles from other Domain",
                                                  "Groups: Update",
                                                  "Role: Update Profession", "Role: Update Profession in other Domain",
                                                  "Resource: Remove From Role", "Resource: Remove From Role in other Domain",
                                                  "Influence: Add to Role", "Influence: Add to Role in other Domain",
                                                  "Influence: Remove from Role", "Influence: Remove from Role in other Domain",
                                                  "Domain: Update", "Domain: Update Other", "Domain: Add",
                                                  "Plot: Add", "Plot: Add to Other Domain",
                                                  "Plot: Update", "Plot: Update in Other Domain",
                                                  "Plot: Assign/UnAssign to Role", "Plot: Assign/UnAssign to Role in Other Domain",
                                                  "Allow Password-Change",
                                                  "Templates: Add", "Templates: Update", "Players: Add/Update"};


    public static String getDescription(int pSection) {
        return sDescriptions[pSection-1];
    }

    /**
     * For serialization
     */
    public UserRights() {
    }

    public UserRights(String pRightsBitString) {
        bitSet = new BitSet();
        for (int i = 0; i < pRightsBitString.length(); i++) {
            char c = pRightsBitString.charAt(i);
            bitSet.set(i, c == '1');
        }
    }

    public Object clone() {
        return new UserRights(this.toString());
    }

    public boolean get(int pRight) {
        return bitSet.get(pRight);
    }

    public void set(int pRight, boolean pOK) {
        bitSet.set(pRight, pOK);
    }

    public boolean canInflictOnOtherDomains() {
        for (int i = 0; i < INFLICTS_ON_OTHER_DOMAINS.length; i++) {
            int right = INFLICTS_ON_OTHER_DOMAINS[i];
            if(get(right)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String rights = "";
        for (int i = 0; i <= 63; i++) {
            if (get(i)) {
                rights += "1";
            }
            else {
                rights += "0";
            }
        }
        return rights;
    }
}
