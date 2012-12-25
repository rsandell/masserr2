package se.tdt.bobby.wodcc.ui.components.models;

import se.tdt.bobby.wodcc.data.BankAccount;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Description
 *
 *
 * Created: 2004-feb-10 23:08:09
 * @author <a href="the.bobby.is@home.se">Robert Sandell</a>
 */
public class BankAccountsTableModel extends AbstractTableModel {


	private List mBankAccounts;

	public BankAccountsTableModel(List pBankAccounts) {
		mBankAccounts = pBankAccounts;
	}

	public int getRowCount() {
		return mBankAccounts.size();
	}

	public int getColumnCount() {
		return 5;
	}

	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return Integer.class;
			case 1:
				return String.class;
			case 2:
				return Double.class;
			case 3:
				return Boolean.class;
			case 4:
				return Boolean.class;
		}
		return Object.class;
	}

	private static String[] columnNames = {"Id", "Owner Name", "Ammount", "Income", "Active"};

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		se.tdt.bobby.wodcc.data.BankAccount ac = (se.tdt.bobby.wodcc.data.BankAccount) mBankAccounts.get(rowIndex);
		if (ac != null) {
			switch (columnIndex) {
				case 0:
					return new Integer(ac.getId());
				case 1:
					return ac.getOwnerName();
				case 2:
					return new Double(ac.getAmmount());
				case 3:
					return new Boolean(ac.isIncome());
				case 4:
					return new Boolean(ac.isActive());
			}
		}
		return null;
	}

	public se.tdt.bobby.wodcc.data.BankAccount setIncome(int pRowIndex) {
		se.tdt.bobby.wodcc.data.BankAccount affected = null;
		for (int i = 0; i < mBankAccounts.size(); i++) {
			se.tdt.bobby.wodcc.data.BankAccount bankAccount = (se.tdt.bobby.wodcc.data.BankAccount) mBankAccounts.get(i);
			if (i == pRowIndex) {
				bankAccount.setIncome(true);
				affected = bankAccount;
				fireTableCellUpdated(i, 3);
			}
			else if (bankAccount.isIncome()) {
				bankAccount.setIncome(false);
				fireTableCellUpdated(i, 3);
			}
		}
		return affected;
	}

	public se.tdt.bobby.wodcc.data.BankAccount getBankAccount(int pRowIndex) {
		return (se.tdt.bobby.wodcc.data.BankAccount) mBankAccounts.get(pRowIndex);
	}

	public List getBankAccounts() {
		return mBankAccounts;
	}

	public void setBankAccounts(List pBankAccounts) {
		mBankAccounts = pBankAccounts;
		fireTableDataChanged();
	}

	public boolean isOneIncome() {
		int count = 0;
		for (int i = 0; i < mBankAccounts.size(); i++) {
			se.tdt.bobby.wodcc.data.BankAccount bankAccount = (se.tdt.bobby.wodcc.data.BankAccount) mBankAccounts.get(i);
			if (bankAccount.isIncome()) {
				count++;
			}
		}
		return count == 1;
	}
}
