package se.tdt.bobby.wodcc.logic;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import se.tdt.bobby.help.ContentParser;
import se.tdt.bobby.wodcc.data.*;
import se.tdt.bobby.wodcc.db.AppPreferences;
import se.tdt.bobby.wodcc.db.Proxy;
import se.tdt.bobby.wodcc.proxy.interfaces.RetrievalDB;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description
 * <p/>
 * <p/>
 * Created: 2004-jan-18 20:44:31
 *
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class OutputCreator {
    private static final boolean DEBUG = false;
    private WoDClanSymbolFontFinder mClanSymbolFinder;
    private ContentParser mMeritsInfo;
    private ExperienceListFetcher mExperienceListFetcher;
    private static OutputCreator sInstance;
    //private IndexGenerator mIndexGenerator;
    //private String mTempDir = "C:/temp";

    private OutputCreator() throws Exception {
        mClanSymbolFinder = new WoDClanSymbolFontFinder();
        mMeritsInfo = new ContentParser();
        mExperienceListFetcher = new ExperienceListFetcher();
        Velocity.init();
    }

    public static OutputCreator getInstance() throws Exception {
        if (sInstance == null) {
            sInstance = new OutputCreator();
        }
        return sInstance;
    }

    public File makeSingleRole(Role pRole) throws Exception, IOException, MethodInvocationException, ParseErrorException {
        VelocityContext context = new VelocityContext();
        context.put("notes", new Notes());
        context.put("roles", new Role[]{pRole});
        context.put("heading", AppPreferences.getHeadingName());
        context.put("ClanSymbolFinder", mClanSymbolFinder);
        context.put("MeritsInfo", mMeritsInfo);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH.mm.ss");
        Date time = Calendar.getInstance().getTime();

        Template template = Velocity.getTemplate("config/role.vm");
        File f = new File(AppPreferences.getTempDir(), "Roles_" + format.format(time) + ".html");
        StringWriter fw = new StringWriter();

        template.merge(context, fw);
        //if (DEBUG) System.out.println("[OutputCreator][makeSingleRole][45] \n" + fw.getBuffer());
        FileWriter fileOut = new FileWriter(f);
        fileOut.write(fw.getBuffer().toString());
        fileOut.close();
        return f;
    }

    public File makeEmptyRole(Clan pClan, boolean pGhoul) throws Exception, IOException, MethodInvocationException, ParseErrorException, SQLException {
        RetrievalDB db = Proxy.getRetrievalDB();
        List<Discipline> disciplines = db.getClanDisciplines(pClan);
        EmptyRole role = new EmptyRole(pClan, db.getAbilities(RetrievalDB.ABILITY_TYPE_PHYSICAL), db.getAbilities(RetrievalDB.ABILITY_TYPE_SOCIAL), db.getAbilities(RetrievalDB.ABILITY_TYPE_MENTAL), disciplines, pGhoul);
        VelocityContext context = new VelocityContext();
        context.put("notes", new Notes());
        context.put("roles", new EmptyRole[]{role});
        context.put("heading", AppPreferences.getHeadingName());
        context.put("ClanSymbolFinder", mClanSymbolFinder);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH.mm.ss");
        Date time = Calendar.getInstance().getTime();

        Template template = Velocity.getTemplate("config/role.vm");
        File f = new File(AppPreferences.getTempDir(), "EmptyRole_" + format.format(time) + ".html");
        StringWriter fw = new StringWriter();

        template.merge(context, fw);
        //if (DEBUG) System.out.println("[OutputCreator][makeSingleRole][45] \n" + fw.getBuffer());
        FileWriter fileOut = new FileWriter(f);
        fileOut.write(fw.getBuffer().toString());
        fileOut.close();
        return f;
    }

    /**
     * Creates a HTML file with the data from the Roles array and the Velocity template file config/role.vm in the temporary directory.
     *
     * @param pRoles       the data to fill the template with
     * @param pShowPageTwo trie if the "second" page should be rendered.
     * @return the File of the file created.
     * @throws Exception
     * @throws IOException
     * @throws MethodInvocationException
     * @throws ParseErrorException
     */
    public File makeRoles(Role[] pRoles, boolean pShowPageTwo) throws Exception, IOException, MethodInvocationException, ParseErrorException {
        VelocityContext context = new VelocityContext();
        context.put("notes", new Notes());
        context.put("roles", pRoles);
        context.put("heading", AppPreferences.getHeadingName());
        context.put("showPageTwo", new Boolean(pShowPageTwo));
        context.put("fMoney", NumberFormat.getCurrencyInstance());
        context.put("ClanSymbolFinder", mClanSymbolFinder);
        context.put("MeritsInfo", mMeritsInfo);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH.mm.ss");
        Date time = Calendar.getInstance().getTime();

        Template template = Velocity.getTemplate("config/role.vm");
        File f = new File(AppPreferences.getTempDir(), "Roles_" + format.format(time) + ".html");
        StringWriter fw = new StringWriter();

        template.merge(context, fw);
        //if (DEBUG) System.out.println("[OutputCreator][makeSingleRole][45] \n" + fw.getBuffer());
        FileWriter fileOut = new FileWriter(f);
        fileOut.write(fw.getBuffer().toString());
        fileOut.close();
        return f;
    }


    private File makeFamelyTrees(List pNodes) throws Exception, ParseErrorException {
        VelocityContext context = new VelocityContext();
        context.put("ClanSymbolFinder", mClanSymbolFinder);
        context.put("nodes", pNodes);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH.mm.ss");
        Date time = Calendar.getInstance().getTime();
        File f = new File(AppPreferences.getTempDir(), "FamelyTrees_" + format.format(time) + ".html");

        Template template = Velocity.getTemplate("config/famelyTrees.vm");
        StringWriter fw = new StringWriter();
        template.merge(context, fw);
        FileWriter fileOut = new FileWriter(f);
        fileOut.write(fw.getBuffer().toString());
        fileOut.close();
        return f;
    }

    /*private File makeFamelyTrees(List pNodes) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH.mm.ss");
		Date time = Calendar.getInstance().getTime();
		File f = new File(AppPreferences.getTempDir(), "FamelyTrees_" + format.format(time) + ".html");
		//StringWriter fw = new StringWriter();
		PrintWriter br = new PrintWriter(new FileWriter(f));
		br.println("<html><head><title>Famely Trees</title></head>");
		br.println("<body>");
		for (int i = 0; i < pNodes.size(); i++) {
			TreeNode treeNode = (TreeNode) pNodes.get(i);
			br.println("<table cellpadding=\"4\" cellspacing=\"0\" border=\"0\" style=\"border-collapse: collapse;\">");
			br.println("<tr>");
			br.write("<td align=\"center\" valign=\"top\" style=\"border: 1px solid black;\"><span style=\"font-size: 20pt;\">" + mClanSymbolFinder.get(treeNode.getData().getClan()) +"&nbsp;&nbsp;</span>"+ treeNode + "</td>");
			br.println("</tr>");
			br.println("<tr>");
			List children = treeNode.getChildren();
			br.println("<td align=\"center\" valign=\"top\" style=\"border: 1px solid black;\">");
			printNodes(children, br);
			br.println("</td>");
			br.println("</tr>");
			br.println("</table>");
            br.println("<p></p>");
		}
		//printNodes(pNodes, br);
		br.println("</body></html>");
		br.close();
		return f;
    }
    */
    public File makeFamelyTrees() throws Exception, IOException, ParseErrorException {
        RetrievalDB db = Proxy.getRetrievalDB();
        List nodes = db.getFamelyTrees();
        return makeFamelyTrees(nodes);
    }

    public File makeFamelyTrees(Clan pClan) throws Exception, IOException, ParseErrorException {
        RetrievalDB db = Proxy.getRetrievalDB();
        List nodes = db.getFamelyTrees(pClan);
        if (DEBUG) System.out.println("OutputCreator.makeFamelyTrees(155) nodes: " + nodes.size());
        return makeFamelyTrees(nodes);
    }

    /*private void printNodes(List pNodes, PrintWriter pOut) throws IOException {
        pOut.println("<table cellpadding=\"4\" cellspacing=\"0\" border=\"0\" style=\"border-collapse: collapse;\">");
        pOut.println("<tr>");
        for (int i = 0; i < pNodes.size(); i++) {
            TreeNode treeNode = (TreeNode) pNodes.get(i);
            pOut.write("<td align=\"center\" valign=\"top\" style=\"border: 1px dotted black;\">" + treeNode + "</td>");
        }
        pOut.println("</tr>");
        pOut.println("<tr>");
        for (int i = 0; i < pNodes.size(); i++) {
            TreeNode treeNode = (TreeNode) pNodes.get(i);
            pOut.println("<td align=\"center\" valign=\"top\">");
            printNodes(treeNode.getChildren(), pOut);
            pOut.println("</td>");
        }
        pOut.println("</tr>");
        pOut.println("</table>");
    }*/

    public File makeBackgrounds(String pRoleName, String pBackground, String pWill) throws Exception {
        VelocityContext context = new VelocityContext();
        context.put("roleName", pRoleName);
        context.put("background", toHTMLformat(pBackground));
        context.put("will", toHTMLformat(pWill));
        context.put("heading", AppPreferences.getHeadingName());

        Template template = Velocity.getTemplate("config/backgrounds.vm");
        File f = new File(AppPreferences.getTempDir(), "Backgrounds_for_" + pRoleName + ".html");
        StringWriter fw = new StringWriter();

        template.merge(context, fw);
        //if (DEBUG) System.out.println("[OutputCreator][makeSingleRole][45] \n" + fw.getBuffer());
        FileWriter fileOut = new FileWriter(f);
        fileOut.write(fw.getBuffer().toString());
        fileOut.close();
        return f;
    }

    public static String toHTMLformat(String pPreFormatedText) {
        /*String str = pPreFormatedText.replace(">", "&gt;");
        str = str.replace("<", "&lt;");
        str = str.replace("\n", "<br/>");
        str = str.replace("[b]", "<b>");
        str = str.replace("[/b]", "</b>");
        str = str.replace("[i]", "<i>");
        str = str.replace("[/i]", "</i>");
        str = str.replace("[u]", "<u>");
        str = str.replace("[/u]", "</u>");
        return str;*/
        return HTMLUtil.toHTMLformat(pPreFormatedText);
    }

    private HTMLUtil mHTMLUtil = new HTMLUtil();

    public File makeRoles(List<Role> pRoles, boolean pShowPageTwo, boolean pListPlots, boolean pListExperience) throws Exception, IOException {
        VelocityContext context = new VelocityContext();
        if (DEBUG) System.out.println("OutputCreator.makeRoles(252) ");
        context.put("notes", new Notes());
        if (DEBUG) System.out.println("OutputCreator.makeRoles(254) roles");
        context.put("roles", new MinimumToFullRolesIterator(pRoles));
        context.put("heading", AppPreferences.getHeadingName());
        context.put("showPageTwo", new Boolean(pShowPageTwo));
        context.put("listPlots", new Boolean(pListPlots));
        if (DEBUG) System.out.println("OutputCreator.makeRoles(259) pListExperience");
        if (pListExperience) {
            context.put("listExperience", new Boolean(true));
            if (AppPreferences.getReadXPFrom() == AppPreferences.XP_ROLE) {
                if (DEBUG) System.out.println("OutputCreator.makeRoles(263) adding fetcher");
                context.put("experience", mExperienceListFetcher);
            }
            else {
                if(DEBUG) System.out.println("OutputCreator.makeRoles(267) adding readXPFromPlayer");
                context.put("readXPFromPLayer", new Boolean(true));
            }
        }
        else {
            if(DEBUG) System.out.println("OutputCreator.makeRoles(272) setting listExperience false");
            context.put("listExperience", new Boolean(false));
        }
        context.put("fMoney", NumberFormat.getCurrencyInstance());
        context.put("ClanSymbolFinder", mClanSymbolFinder);
        context.put("MeritsInfo", mMeritsInfo);
        context.put("util", mHTMLUtil);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH.mm.ss");
        Date time = Calendar.getInstance().getTime();

        Template template = Velocity.getTemplate("config/role.vm");
        File f = new File(AppPreferences.getTempDir(), "Roles_" + format.format(time) + ".html");
        StringWriter fw = new StringWriter();
        if(DEBUG) System.out.println("OutputCreator.makeRoles(285) merging");
        template.merge(context, fw);
        //if (DEBUG) System.out.println("[OutputCreator][makeSingleRole][45] \n" + fw.getBuffer());
        if(DEBUG) System.out.println("OutputCreator.makeRoles(288) writing");
        FileWriter fileOut = new FileWriter(f);
        fileOut.write(fw.getBuffer().toString());
        fileOut.close();
        if(DEBUG) System.out.println("OutputCreator.makeRoles(292) returning");
        return f;
    }

    public File makeTemplates(List<Role> pTemplates) throws Exception, IOException {
        VelocityContext context = new VelocityContext();
        context.put("notes", new Notes());
        context.put("roles", new MinimumToFullTemplatesIterator(pTemplates));
        context.put("heading", AppPreferences.getHeadingName());
        context.put("showPageTwo", new Boolean(false));
        context.put("listPlots", new Boolean(false));
        context.put("fMoney", NumberFormat.getCurrencyInstance());
        context.put("ClanSymbolFinder", mClanSymbolFinder);
        context.put("MeritsInfo", mMeritsInfo);
        context.put("util", mHTMLUtil);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH.mm.ss");
        Date time = Calendar.getInstance().getTime();

        Template template = Velocity.getTemplate("config/role.vm");
        File f = new File(AppPreferences.getTempDir(), "Templates_" + format.format(time) + ".html");
        StringWriter fw = new StringWriter();

        template.merge(context, fw);
        //if (DEBUG) System.out.println("[OutputCreator][makeSingleRole][45] \n" + fw.getBuffer());
        FileWriter fileOut = new FileWriter(f);
        fileOut.write(fw.getBuffer().toString());
        fileOut.close();
        return f;
    }

    public File makeGenerator(Vector pGenerations, List pCreateRules) throws Exception {
        VelocityContext context = new VelocityContext();
        context.put("generations", pGenerations);
        context.put("createRules", pCreateRules);
        context.put("GhoulGeneration", new Integer(Role.GHOUL_GENERATION));
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH.mm.ss");
        //Date time = Calendar.getInstance().getTime();

        Template template = Velocity.getTemplate("config/generator.vm");
        File f = new File(AppPreferences.getTempDir(), "Roles_Generator.html");
        StringWriter fw = new StringWriter();

        template.merge(context, fw);
        //if (DEBUG) System.out.println("[OutputCreator][makeSingleRole][45] \n" + fw.getBuffer());
        FileWriter fileOut = new FileWriter(f);
        fileOut.write(fw.getBuffer().toString());
        fileOut.close();
        return f;
    }

    public File makePlotsList(List<Plot> pPlots, HashMap<Integer, List<IntWithString>> pAssignmentMap) throws Exception {
        VelocityContext context = new VelocityContext();
        context.put("plots", pPlots);
        context.put("assignmentMap", pAssignmentMap);
        context.put("util", mHTMLUtil);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH.mm.ss");
        Date time = Calendar.getInstance().getTime();

        Template template = Velocity.getTemplate("config/plots.vm");
        File f = new File(AppPreferences.getTempDir(), "Plots_" + format.format(time) + ".html");
        StringWriter fw = new StringWriter();

        template.merge(context, fw);
        //if (DEBUG) System.out.println("[OutputCreator][makeSingleRole][45] \n" + fw.getBuffer());
        FileWriter fileOut = new FileWriter(f);
        fileOut.write(fw.getBuffer().toString());
        fileOut.close();
        return f;
    }

    public File membersList() throws Exception {
        RetrievalDB db = Proxy.getRetrievalDB();
        Domain domain = AppPreferences.getPreferredDomain();
        Vector<Player> members = db.getPlayingMembersList(domain);
        VelocityContext context = new VelocityContext();
        context.put("members", members);
        context.put("date", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
        //context.put("domain", domain);

        //context.put("util", mHTMLUtil);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd HH.mm.ss");
        Date time = Calendar.getInstance().getTime();

        Template template = Velocity.getTemplate("config/members.vm");
        File f = new File(AppPreferences.getTempDir(), "Members_" + format.format(time) + ".html");
        StringWriter fw = new StringWriter();

        template.merge(context, fw);
        //if (DEBUG) System.out.println("[OutputCreator][makeSingleRole][45] \n" + fw.getBuffer());
        FileWriter fileOut = new FileWriter(f);
        fileOut.write(fw.getBuffer().toString());
        fileOut.close();
        return f;
    }
}
