package net.joinedminds.masserr.modules;

import com.google.inject.Inject;
import net.joinedminds.masserr.api.admin.AdminApi;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class ApiModule {
    @Inject
    private AdminApi admin;

    public AdminApi getAdmin() {
        return admin;
    }
}
