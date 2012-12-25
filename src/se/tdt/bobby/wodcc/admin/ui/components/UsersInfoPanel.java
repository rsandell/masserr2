package se.tdt.bobby.wodcc.admin.ui.components;

import se.tdt.bobby.wodcc.data.mgm.User;
import se.tdt.bobby.wodcc.data.Domain;

import javax.swing.*;
import java.awt.*;
import java.io.StringWriter;
import java.text.DateFormat;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;

/**
 * Description.
 * <p/>
 * Created: 2004-apr-15 21:27:46
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class UsersInfoPanel extends JPanel {
    private JEditorPane mEditorPane;

    public UsersInfoPanel() throws Exception {
        super(new BorderLayout());
        mEditorPane = new JEditorPane("text/html", "<html></html>");
        mEditorPane.setEditable(false);
        add(new JScrollPane(mEditorPane), BorderLayout.CENTER);
        Velocity.init();
    }

    public void showUser(User pUser) throws Exception {
        VelocityContext context = new VelocityContext();
		context.put("user", pUser);
        //context.put("format", DateFormat.getDateTimeInstance());
        Template template = Velocity.getTemplate("config/user.vm");
        StringWriter fw = new StringWriter();
		template.merge(context, fw);
        mEditorPane.setText(fw.toString());
    }

    public void showBlankPage() {
        mEditorPane.setText("<html><head></head><body bgcolor=\"#FFFFFF\"></body></html>");
    }

    public void showDomain(Domain pDomain) {
        mEditorPane.setText(pDomain.getHistory());
    }
}
