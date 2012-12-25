package se.tdt.bobby.wodcc.server.catalina;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import se.tdt.bobby.wodcc.server.RemoteFactoryImpl;
import se.tdt.bobby.wodcc.server.db.BasicDBImpl;

/**
 * Description.
 * <p/>
 * Created: 2004-jul-09 16:40:41
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class LifecycleListener implements org.apache.catalina.LifecycleListener {
    private static final boolean DEBUG = false;

    public void lifecycleEvent(LifecycleEvent event) {
        if (DEBUG) System.out.println("LifecycleListener.lifecycleEvent(15) ");
        if (DEBUG) System.out.println("LifecycleListener.lifecycleEvent(17) type: " + event.getType());
        if (DEBUG) System.out.println("LifecycleListener.lifecycleEvent(18) data: " + event.getData());
        if (DEBUG) System.out.println("LifecycleListener.lifecycleEvent(19) work-dir: " + System.getProperty("user.key"));

        if (DEBUG) System.out.println("LifecycleListener.lifecycleEvent(22) ");
        String type = event.getType();
        if (type.equals(Lifecycle.START_EVENT)) {
            if (DEBUG) System.out.println("LifecycleListener.lifecycleEvent(25) starting masserr");
            try {
                BasicDBImpl.setRemoteActor(true);
                RemoteFactoryImpl.main(new String[]{"-server"});
            }
            catch (Exception e) {
                if (DEBUG) e.printStackTrace();
                System.err.println("Cannot start Masserr Server due to " + e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

    public LifecycleListener() {        
    }
}
