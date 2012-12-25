package se.tdt.bobby.wodcc;

import se.tdt.bobby.wodcc.ui.MainFrame;
import se.tdt.bobby.wodcc.server.RemoteFactoryImpl;
import se.tdt.bobby.wodcc.server.ui.ServerSetupFrame;
import se.tdt.bobby.wodcc.server.db.BasicDBImpl;
import se.tdt.bobby.wodcc.admin.ui.AdminFrame;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-16 13:58:30
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class Main {
    private static final boolean DEBUG = false;

    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    String arg = args[i];
                    if (arg.equals("-server")) {
                        BasicDBImpl.setRemoteActor(true);
                        RemoteFactoryImpl.main(args);
                        break;
                    }
                    else if (arg.equals("-client")) {
                        BasicDBImpl.setRemoteActor(false);
                        MainFrame.main(args);
                        break;
                    }
                    else if (arg.equals("-admin")) {
                        BasicDBImpl.setRemoteActor(false);
                        AdminFrame.main(args);
                        break;
                    }
                    else if (arg.equals("-serversetup")) {
                        new ServerSetupFrame();
                        break;
                    }
                }
            }
            else {
                MainFrame.main(args);
            }
        }
        catch (Throwable e) {
            if (DEBUG) e.printStackTrace();
            System.exit(1);
        }
    }
}
