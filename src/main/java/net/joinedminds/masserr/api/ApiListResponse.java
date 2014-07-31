/*
 * The MIT License
 *
 * Copyright (c) 2014-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
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

package net.joinedminds.masserr.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.joinedminds.masserr.api.serialization.NoObjectIdExclusionStrategy;
import net.joinedminds.masserr.api.serialization.ObjectIdTypeAdapter;
import net.joinedminds.masserr.model.Identifiable;
import net.joinedminds.masserr.model.NamedIdentifiable;
import org.bson.types.ObjectId;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.export.ExportedBean;
import org.kohsuke.stapler.export.Flavor;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@ExportedBean
public abstract class ApiListResponse<E extends Identifiable> extends ApiResponse<List<E>> implements Collection<E> {

    protected abstract List<E> createList();

    protected void ensureList() {
        if (data == null) {
            data = createList();
        }
    }

    protected abstract E get(String id);

    @Override
    public int size() {
        ensureList();
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        ensureList();
        return data == null || data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return false;  //Not Implemented
    }

    @Override
    public Iterator<E> iterator() {
        ensureList();
        return data.iterator();
    }

    @Override
    public Object[] toArray() {
        ensureList();
        return data.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        ensureList();
        return data.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return false; //Not implemented
    }

    @Override
    public boolean remove(Object o) {
        return false;  //Not Implemented yet
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;  //Not Implemented
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;  //Not Implemented
    }


    @Override
    public boolean removeAll(Collection<?> c) {
        return false;  //Not implemented yet
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;  //Not implemented yet
    }

    @Override
    public void clear() {
        //Not implemented yet
    }

    @Override
    protected void create(StaplerRequest req, StaplerResponse rsp) throws ServletException, IOException {
        super.create(req, rsp);    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void read(StaplerRequest req, StaplerResponse rsp) throws ServletException, IOException  {
        Gson gson = getGsonBuilder().create();
        rsp.setContentType(Flavor.JSON.contentType);
        gson.toJson(this, rsp.getWriter());
    }



    public ApiResponse<E> getDynamic(String id, StaplerRequest req, StaplerResponse rsp) {
        return new ApiResponse<>(get(id));
    }
}
