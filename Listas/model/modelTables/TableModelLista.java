package modelTables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import beansListas.CheckList;
import daoListas.DaoListaS;

public class TableModelLista extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CheckList> linhas;
	private String[] colunas = new String[]{"Listas"};
	private static final int Listas = 0;
	DaoListaS daoLista;

	public TableModelLista() {
		daoLista = new DaoListaS();
		linhas = new ArrayList<CheckList>();
	}

	public TableModelLista(List<CheckList> listCheckList) {
		daoLista = new DaoListaS();
		linhas = new ArrayList<CheckList>(listCheckList);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		CheckList chkList = linhas.get(rowIndex);
		switch (columnIndex) {
			case Listas :
				return chkList.getNomeLista();
			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}
	}

	public boolean contemNomeLista(String nomeLista) {
		for (CheckList checkLista : linhas) {
			if (checkLista.getNomeLista().equals(nomeLista)) {
				return true;
			}
		}
		return false;
	}

	public CheckList getLista(int linha) {
		return linhas.get(linha);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case Listas :
				return String.class;
			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		CheckList checkList = linhas.get(rowIndex);
		checkList.setNomeLista((String) aValue);
		fireTableCellUpdated(rowIndex, columnIndex);
		daoLista.atualizaLista(checkList);
	}

	@Override
	public String getColumnName(int columnIndex) {
		return colunas[columnIndex];
	};

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == Listas;
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return linhas.size();
	}

}
