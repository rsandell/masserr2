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
import java.sql.Date;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-25 17:34:51
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class Plot implements Serializable, Cloneable{

    @Id
    private String id;
    private String title;
    private String description;
    private Date created;
    private String positive;
    private String negative;
    private boolean done;
    private Domain domain;
    private String storyTellerDescription;


    public Plot(String pId, String pTitle, String pDescription, Date pCreated, String pPositive, String pNegative, boolean pDone, Domain pDomain, String storyTellerDescription) {
        id = pId;
        title = pTitle;
        description = pDescription;
        created = pCreated;
        positive = pPositive;
        negative = pNegative;
        done = pDone;
        domain = pDomain;
        this.storyTellerDescription = storyTellerDescription;
    }

    public Plot(String pTitle, String pDescription, Date pCreated, String pPositive, String pNegative, boolean pDone, Domain pDomain, String storyTellerDescription) {
        title = pTitle;
        description = pDescription;
        created = pCreated;
        positive = pPositive;
        negative = pNegative;
        done = pDone;
        domain = pDomain;
        this.storyTellerDescription = storyTellerDescription;
    }

    public Plot(String pTitle, String pDescription, Date pCreated, String pPositive, String pNegative, Domain pDomain, String storyTellerDescription) {
        title = pTitle;
        description = pDescription;
        created = pCreated;
        positive = pPositive;
        negative = pNegative;
        domain = pDomain;
        this.storyTellerDescription = storyTellerDescription;
    }

    public Plot(String pId, String pTitle, String pDescription, Date pCreated, Domain pDomain, String pSLdescription) {
        id = pId;
        title = pTitle;
        description = pDescription;
        created = pCreated;
        domain = pDomain;
        storyTellerDescription = pSLdescription;
    }

    public Plot(String pId, String pTitle, Domain pDomain) {
        id = pId;
        title = pTitle;
        domain = pDomain;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String pTitle) {
        title = pTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date pCreated) {
        created = pCreated;
    }

    public String getPositive() {
        return positive;
    }

    public void setPositive(String pPositive) {
        positive = pPositive;
    }

    public String getNegative() {
        return negative;
    }

    public void setNegative(String pNegative) {
        negative = pNegative;
    }

    public boolean isDone() {
        return done;
    }

    public Object clone() {
        return new Plot(id, title, description, (Date) created.clone(), positive, negative, done, (Domain) domain.clone(), storyTellerDescription);
    }

    public void setDone(boolean pDone) {
        done = pDone;
    }

    public String toString() {
        return id + ": " + title;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain pDomain) {
        domain = pDomain;
    }

    public String getStoryTellerDescription() {
        return storyTellerDescription;
    }

    public void setStoryTellerDescription(String storyTellerDescription) {
        this.storyTellerDescription = storyTellerDescription;
    }
}
