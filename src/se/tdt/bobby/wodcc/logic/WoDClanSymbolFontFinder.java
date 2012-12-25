package se.tdt.bobby.wodcc.logic;

import se.tdt.bobby.wodcc.data.Clan;
import se.tdt.bobby.wodcc.data.ClanFontSymbol;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


/**
 * Description.
 * <p/>
 * Created: 2004-mar-26 18:38:38
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class WoDClanSymbolFontFinder {
    private HashMap<Integer, ClanFontSymbol> mClansMap;

    public WoDClanSymbolFontFinder() throws SQLException, RemoteException {
        mClansMap = new HashMap<Integer, ClanFontSymbol>();
        RetrievalDB db = Proxy.getRetrievalDB();
        List<ClanFontSymbol> list = db.getClanFontSymbols();
        for (int i = 0; i < list.size(); i++) {
            ClanFontSymbol clanFontSymbol = list.get(i);
            mClansMap.put(new Integer(clanFontSymbol.getClanId()), clanFontSymbol);
        }
    }

    public ClanFontSymbol get(Clan pClan) {
        return mClansMap.get(new Integer(pClan.getId()));
    }

    public ClanFontSymbol get(int pClanId) {
        return mClansMap.get(new Integer(pClanId));
    }
}
