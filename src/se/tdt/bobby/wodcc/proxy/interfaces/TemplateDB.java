package se.tdt.bobby.wodcc.proxy.interfaces;

import se.tdt.bobby.wodcc.data.Role;

import java.sql.SQLException;
import java.text.ParseException;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 * Description.
 * <p/>
 * Created: 2004-maj-03 16:40:02
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public interface TemplateDB extends BasicDB {
    char ABILITY_TYPE_MENTAL = 'M';
    char ABILITY_TYPE_PHYSICAL = 'P';
    char ABILITY_TYPE_SOCIAL = 'S';

    Role getTemplate(int pId) throws SQLException, ParseException, RemoteException;

    Vector<Role> getMinTemplateInfo() throws SQLException, RemoteException;

    void updateTemplate(Role pRole) throws SQLException, RemoteException;

    int addTemplate(Role pRole) throws SQLException, RemoteException;
}
