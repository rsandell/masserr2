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

import org.kohsuke.stapler.DataBoundConstructor;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Description
 *
 * 
 * Created: 2004-jan-11 19:31:04
 * @author <a href="sandell.robert@gmail.com"> Robert Sandell</a>
 */
public class Path implements NamedIdentifiable {

	@Id
    private String id;
	private String name;
    private Type type;
    private String docUrl;

	public Path() {
	}

	public Path(String pName) {
		name = pName;
	}

    public Path(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @DataBoundConstructor
    public Path(String id, String name, Type type, String docUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.docUrl = docUrl;
    }

    public Path(Path path) {
        this.id = path.getId();
        this.name = path.getName();
        this.type = path.getType();
        this.docUrl = path.getDocUrl();
    }

    @Override
	public String getId() {
		return id;
	}

    @Override
	public String getName() {
		return name;
	}

	public void setName(String pName) {
		name = pName;
	}

	public String toString() {
		return name;
	}

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public static enum Type {
        Necromancy, Thaumaturgy
    }
}
