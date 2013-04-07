package net.joinedminds.masserr.db.impl;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.FieldCriteria;
import com.github.jmkgreen.morphia.query.Query;
import com.google.inject.Inject;
import com.google.inject.Provider;
import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.db.AdminDB;
import net.joinedminds.masserr.model.Campaign;
import net.joinedminds.masserr.model.Domain;
import net.joinedminds.masserr.model.Player;
import net.joinedminds.masserr.model.mgm.Config;
import net.joinedminds.masserr.model.mgm.User;
import org.bson.types.ObjectId;

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

    @Override
    public Campaign newCampaign() {
        return new Campaign();
    }

    @Override
    public Campaign saveCampaign(Campaign campaign) {
        return save(campaign);
    }

    @Override
    public List<Campaign> getCampaigns() {
        return db.get().find(Campaign.class).order("name").asList();
    }

    @Override
    public List<Player> getPlayers(Campaign campaign) {
        return db.get().find(Player.class, "campaign", campaign).order("name").asList();
    }

    @Override
    public boolean isPlayerEmailTaken(String email, String exceptPlayerId) {
        Query<Player> query = db.get().find(Player.class, "email", email);
        ObjectId id = Functions.toObjectId(exceptPlayerId);
        if(id != null) {
            query.criteria("_id").notEqual(id);
        }
        return query.countAll() >= 1;
    }

    @Override
    public Player savePlayer(Player player) {
        return save(player);
    }

    @Override
    public Player getPlayer(String id) {
        return get(Player.class, id);
    }

    @Override
    public Player newPlayer() {
        return new Player();
    }
}
