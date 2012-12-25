package se.tdt.bobby.help;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-31 16:29:13
 * 
 * @author <a href="mailto:the.bobby.is@home.se>Robert Sandell</a>"
 */
public class IndexGenerator extends JFrame implements ActionListener {
    private JFileChooser mFileChooser;
    private JTextArea mTextArea;
    private JEditorPane mEditorPane;
    private static final boolean DEBUG = false;

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("open")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    if (mFileChooser.showOpenDialog(mTextArea) == JFileChooser.APPROVE_OPTION) {
                        final File f = mFileChooser.getSelectedFile();
                        if (f.exists() && f.isFile()) {
                            Runnable runnable = new Runnable() {
                                public void run() {
                                    try {
                                        read(f);
                                    }
                                    catch (IOException e1) {
                                        if (DEBUG) e1.printStackTrace();
                                        JOptionPane.showMessageDialog(mTextArea, e1.getMessage(), e1.getClass().getName(), JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            };
                            new Thread(runnable).start();
                        }
                        else {
                            JOptionPane.showMessageDialog(mTextArea, f + "\nIs not a file.", "No File!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
    }

    protected void read(File pFile) throws IOException {
        FileReader in = new FileReader(pFile);
        BufferedReader br = new BufferedReader(in);
        String file = "";
        String row = br.readLine();
        while (row != null) {
            file += row + "\n";
            row = br.readLine();
        }
        mEditorPane.setText(file);
        int lastIndex = file.indexOf("<a name=\"", 0);
        String output = "";
        while (lastIndex > 0) {
            int endIndex = file.indexOf("\"", lastIndex + 9);
            String name = file.substring(lastIndex + 9, endIndex);
            if (DEBUG) System.out.println("IndexGenerator.read(39) name= " + name);
            output += "\n<p class=MsoToc3><span class=MsoHyperlink><a href=\"#" + name + "\">" + name + "</a></span></p>";
            lastIndex = file.indexOf("<a name=\"", endIndex);
        }
        mTextArea.setText(output);
    }

    public IndexGenerator() throws HeadlessException {
        super("Html IndexGenerator");
        mFileChooser = new JFileChooser();
        mTextArea = new JTextArea();
        mEditorPane = new JEditorPane("text/html", "<html></html>");
        mEditorPane.setEditable(false);
        JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        pane.add(new JScrollPane(mTextArea));
        pane.add(new JScrollPane(mEditorPane));
        getContentPane().add(pane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.setMnemonic('F');
        JMenuItem item = new JMenuItem("Open");
        item.setMnemonic('O');
        item.setActionCommand("open");
        item.addActionListener(this);
        menu.add(item);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new IndexGenerator();
    }
}
