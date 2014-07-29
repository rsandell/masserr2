package net.joinedminds.masserr.api;

import com.google.inject.AbstractModule;
import net.joinedminds.masserr.api.admin.AdminApi;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class ApiGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AdminApi.class);
    }
}
