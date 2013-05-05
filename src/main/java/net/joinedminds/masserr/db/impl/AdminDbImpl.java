package net.joinedminds.masserr.db.impl;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.db.AdminDB;
import net.joinedminds.masserr.model.Campaign;
import net.joinedminds.masserr.model.Domain;
import net.joinedminds.masserr.model.Player;
import net.joinedminds.masserr.model.Config;
import net.joinedminds.masserr.model.auth.User;
import org.bson.types.ObjectId;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static net.joinedminds.masserr.Functions.isEmpty;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Singleton
public class AdminDbImpl extends BasicDbImpl implements AdminDB {
    private static Logger logger = Logger.getLogger(AdminDbImpl.class.getName());

    private LoadingCache<String, Config> configCache;

    @Inject
    public AdminDbImpl(Provider<Datastore> db) {
        super(db);
        configCache = CacheBuilder.newBuilder().
                refreshAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<String, Config>() {
            @Override
            public Config load(String s) throws Exception {
                return _getConfig();
            }
        });
    }

    public Config _getConfig() {
        Config config = db.get().find(Config.class).get();
        if (config == null) {
            config = new Config();
            StaplerRequest request = Stapler.getCurrentRequest();
            if (request != null) {
                config.setApplicationUrl(request.getRootPath());
            }
            return save(config);
        } else {
            return config;
        }
    }

    @Override
    public Config getConfig() {
        try {
            Config config = configCache.get("");
            if (isEmpty(config.getApplicationUrl()) && Stapler.getCurrentRequest() != null) {
                config.setApplicationUrl(Stapler.getCurrentRequest().getRootPath());
            }
            return config;
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to get Config from cache! ", e);
        }
    }

    @Override
    public Config saveConfig(Config config) {
        Config save = save(config);
        configCache.put("", save);
        return save;
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
