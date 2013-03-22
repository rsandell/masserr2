package net.joinedminds.masserr.db.impl;

import com.github.jmkgreen.morphia.Datastore;
import com.google.inject.Inject;
import com.google.inject.Provider;
import net.joinedminds.masserr.db.AdminDB;
import net.joinedminds.masserr.model.Domain;
import net.joinedminds.masserr.model.mgm.Config;
import net.joinedminds.masserr.model.mgm.User;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class AdminDbImpl extends BasicDbImpl implements AdminDB {


    @Inject
    public AdminDbImpl(Provider<Datastore> db) {
        super(db);
    }

    @Override
    public Config getConfig() {
        Config config = db.get().find(Config.class).get();
        if (config == null) {
            config = new Config();
            return save(config);
        } else {
            return config;
        }
    }

    @Override
    public Config saveConfig(Config config) {
        return save(config);
    }

    @Override
    public void updateUser(User pUser) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User addUser(User pUser) throws SQLException, RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User getUser(int pUserId) throws SQLException, RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<User> getFullUsersListByDomain() throws SQLException, RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addDomain(Domain pDomain) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateDomain(Domain pDomain) throws SQLException, RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean changePassword(String pCurrentPassword, String pNewPassword) throws SQLException, RemoteException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
