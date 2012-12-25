package se.tdt.bobby.wodcc.ui;

import se.tdt.bobby.wodcc.ui.components.MutableAction;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-30 14:59:49
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class HelpFrame extends JFrame {
    private JEditorPane mEditorPane;
    private static final boolean DEBUG = false;
    public static HelpFrame sHelpFrame = null;

    public static final MutableAction sShowLotnAbilitiesHelpAction;
    public static final MutableAction sShowLotnArchetypesHelpAction;
    public static final MutableAction sShowLotnPathsHelpAction;

    public static MutableAction sShowLotnMeritsNflawsHelpAction;

    static {
        sShowLotnAbilitiesHelpAction = new MutableAction("Abilities") {
            public void actionPerformed(ActionEvent e) {
                if (sHelpFrame == null) {
                    sHelpFrame = new HelpFrame();
                }
                sHelpFrame.setPage("Abilities.htm");
                sHelpFrame.setVisible(true);
                sHelpFrame.requestFocus();
            }
        };
        sShowLotnArchetypesHelpAction = new MutableAction("Archetypes") {
            public void actionPerformed(ActionEvent e) {
                if (sHelpFrame == null) {
                    sHelpFrame = new HelpFrame();
                }
                sHelpFrame.setPage("Archetypes.htm");
                sHelpFrame.setVisible(true);
                sHelpFrame.requestFocus();
            }
        };
        sShowLotnPathsHelpAction = new MutableAction("Paths") {
            public void actionPerformed(ActionEvent e) {
                if (sHelpFrame == null) {
                    sHelpFrame = new HelpFrame();
                }
                sHelpFrame.setPage("Paths.htm");
                sHelpFrame.setVisible(true);
                sHelpFrame.requestFocus();
            }
        };
        sShowLotnMeritsNflawsHelpAction = new MutableAction("Merits & Flaws") {
            public void actionPerformed(ActionEvent e) {
                if (sHelpFrame == null) {
                    sHelpFrame = new HelpFrame();
                }
                sHelpFrame.setPage("Merits&Flaws.htm");
                sHelpFrame.setVisible(true);
                sHelpFrame.requestFocus();
            }
        };
    }

    public HelpFrame() throws HeadlessException {
        super("Help");
        mEditorPane = new JEditorPane("text/html", "<html></html>");
        mEditorPane.setEditable(false);
        mEditorPane.addHyperlinkListener(new Hyperactive());

        getContentPane().add(new JScrollPane(mEditorPane), BorderLayout.CENTER);
        setSize(400, 600);
    }

    public void setPage(String pHelpFileName, String pSection) {
        try {
            File file = new File("help/", pHelpFileName);
            URL url = file.toURL();
            if (pSection != null) {
                url = new URL(url.toExternalForm() + "#" + pSection);
            }
            mEditorPane.setPage(url);//read(url.openStream(), "text/html");

            mEditorPane.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) {
                    if(DEBUG) System.out.println("HelpFrame.insertUpdate(76) " + e);
                    String title = (String) e.getDocument().getProperty(HTMLDocument.TitleProperty);
                    if(title != null) {
                        setTitle("Help: " + title);
                    }
                }

                public void removeUpdate(DocumentEvent e) {
                    if(DEBUG) System.out.println("HelpFrame.removeUpdate(80) " + e);
                    String title = (String) e.getDocument().getProperty(HTMLDocument.TitleProperty);
                    if(title != null) {
                        setTitle("Help: " + title);
                    }
                }

                public void changedUpdate(DocumentEvent e) {
                    if(DEBUG) System.out.println("HelpFrame.changedUpdate(84) " + e);
                    String title = (String) e.getDocument().getProperty(HTMLDocument.TitleProperty);
                    if(title != null) {
                        setTitle("Help: " + title);
                    }
                }
            });
        }
        catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            report(e);
        }
    }

    public void setPage(String pHelpFileName) {
        setPage(pHelpFileName, null);
    }

    private void report(Exception pE) {
        JOptionPane.showMessageDialog(this, pE.getMessage(), pE.getClass().getName(), JOptionPane.ERROR_MESSAGE);
    }

    class Hyperactive implements HyperlinkListener {

        public void hyperlinkUpdate(HyperlinkEvent e) {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                JEditorPane pane = (JEditorPane) e.getSource();
                if (e instanceof HTMLFrameHyperlinkEvent) {
                    HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
                    HTMLDocument doc = (HTMLDocument) pane.getDocument();
                    doc.processHTMLFrameHyperlinkEvent(evt);
                }
                else {
                    try {
                        if(DEBUG) System.out.println("HelpFrame$Hyperactive.hyperlinkUpdate(86) " + e.getURL());
                        pane.setPage(e.getURL());
                    }
                    catch (Throwable t) {
                       if (DEBUG) t.printStackTrace();
                    }
                }
            }
        }
    }

}
