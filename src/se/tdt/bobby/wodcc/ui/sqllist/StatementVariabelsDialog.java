package se.tdt.bobby.wodcc.ui.sqllist;

import se.tdt.bobby.wodcc.data.sqllists.Parameter;
import se.tdt.bobby.wodcc.proxy.interfaces.StatementsDB;
import se.tdt.bobby.wodcc.ui.components.GridBagPanel;
import se.tdt.bobby.wodcc.ui.sqllist.components.models.RowsComboBoxData;
import se.tdt.bobby.wodcc.ui.sqllist.components.models.RowsComboBoxModel;
import se.tdt.bobby.wodcc.ui.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created: 2006-jul-25 14:15:56
 *
 * @author <a href="bobby@ambrosias.se">Robert Sandell</a>
 */
public class StatementVariabelsDialog extends JDialog implements ActionListener {
    private List<Parameter> mVariabels;
    private List<Parameter> mOrigParams;
    private ArrayList<JComponent> mInputs;
    private ArrayList<Parameter> mSaveParameters;
    private StatementsDB mStatementsDB;
    private boolean mOKPerformed = false;

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("OK")) {

            for (int i = 0; i < mVariabels.size(); i++) {
                Parameter var = mVariabels.get(i);
                JComponent input = mInputs.get(i);
                Parameter par = new Parameter(var.getId(), null, var.getType(), var.isVariable(), var.getIndex());
                if (input instanceof JTextField) {
                    JTextField f = (JTextField) input;
                    par.setValue(f.getText());
                }
                else if (input instanceof JComboBox) {
                    JComboBox c = (JComboBox) input;
                    RowsComboBoxData data = (RowsComboBoxData) c.getSelectedItem();
                    if (data != null) {
                        par.setValue(String.valueOf(data.getValue()));
                    }
                }
                mSaveParameters.add(par);
            }
            mOKPerformed = true;
            setVisible(false);
        }
        else if (e.getActionCommand().equalsIgnoreCase("cancel")) {
            mOKPerformed = false;
            setVisible(false);
        }
    }

    public boolean showDialog() {
        setVisible(true);
        return mOKPerformed;
    }

    public List<Parameter> getParameters() {
        return mSaveParameters;
    }

    public StatementVariabelsDialog(Frame owner, List<Parameter> pParameters, StatementsDB pStatementsDB) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Set Variabels", true);
        mStatementsDB = pStatementsDB;
        init(pParameters);
    }

    public StatementVariabelsDialog(Dialog owner, List<Parameter> pParameters, StatementsDB pStatementsDB) throws HeadlessException, SQLException, RemoteException {
        super(owner, "Set Variabels", true);
        mStatementsDB = pStatementsDB;
        init(pParameters);
    }

    private void init(List<Parameter> pParameters) throws SQLException, RemoteException {
        mVariabels = new java.util.ArrayList<Parameter>();
        mOrigParams = pParameters;
        mSaveParameters = new ArrayList<Parameter>();
        mInputs = new ArrayList<JComponent>();
        GridBagPanel panel = new GridBagPanel();
        for (Parameter parameter : mOrigParams) {
            if (parameter.isVariable()) {
                Parameter variable = new Parameter(parameter.getId(), parameter.getValue(), parameter.getType(), parameter.isVariable(), parameter.getIndex(), parameter.getVariableInput(), parameter.getVariableLookup(), parameter.getVariableLookupValuecolumn(), parameter.getVariableLookupLabelcolumn());
                mVariabels.add(variable);
                JComponent input;
                if (variable.getVariableInput() == null || variable.getVariableInput().equalsIgnoreCase("TextBox")) {
                    input = new JTextField(variable.getValue());
                }
                else if (variable.getVariableInput().equalsIgnoreCase("ComboBox")) {
                    input = new JComboBox(new RowsComboBoxModel(mStatementsDB.getRows(variable.getVariableLookup()), variable.getVariableLookupValuecolumn(), variable.getVariableLookupLabelcolumn()));
                }
                else {
                    input = new JTextField(variable.getValue());
                }
                panel.addLine(variable.getValue(), input);
                mInputs.add(input);
            }
            else {
                Parameter p = new Parameter(parameter.getId(), parameter.getValue(), parameter.getType(), parameter.isVariable(), parameter.getIndex());
                mSaveParameters.add(p);
            }
        }
        getContentPane().add(panel, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btn = new JButton("OK");
        btn.setActionCommand("OK");
        btn.addActionListener(this);
        btnPanel.add(btn);
        getRootPane().setDefaultButton(btn);
        btn = new JButton("Cancel");
        btn.setActionCommand("cancel");
        btn.addActionListener(this);
        btnPanel.add(btn);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        pack();
        Utilities.positionMe(this);
    }
}
