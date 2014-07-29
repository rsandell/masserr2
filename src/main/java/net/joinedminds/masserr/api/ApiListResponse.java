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
