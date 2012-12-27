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

package net.joinedminds.masserr.model;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-jun-29 19:57:15
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class Player implements Serializable, Cloneable{
    @Id
    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private int xp;
    private Vector<Role> roles;
    private List<Experience> experienceList;

    public Player(String pId, String pName, String pAddress, String pPhone, String pEmail) {
        id = pId;
        name = pName;
        address = pAddress;
        phone = pPhone;
        email = pEmail;
    }

    public Player(String pId, String pName) {
        id = pId;
        name = pName;
        address = "";
        phone = "";
        email = "";
    }

    public Player() {
    }

    public Object clone() {
        return new Player(id, name, address, phone, email);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String pAddress) {
        address = pAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String pPhone) {
        phone = pPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String pEmail) {
        email = pEmail;
    }

    public String toString() {
        return name;
    }

    public int getXP() {
        return xp;
    }

    public void setXP(int pXP) {
        xp = pXP;
    }

    public Vector<Role> getRoles() {
        return roles;
    }

    public void setRoles(Vector<Role> pRoles) {
        roles = pRoles;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> pExperienceList) {
        experienceList = pExperienceList;
    }
}
