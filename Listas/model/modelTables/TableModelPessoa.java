package modelTables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import daoListas.DaoListaM;
import daoListas.InterDAOListas;

public class TableModelPessoa extends AbstractTableModel {

	private List<String> linhas;
	private String[] colunas = new String[]{"Listas"};
	private static final int Listas = 0;
	InterDAOListas daoLista;

	public TableModelPessoa() {
		daoLista = new DaoListaM();
		linhas = new ArrayList<String>();
	}

	public TableModelPessoa(List<String> listFunc) {
		daoLista = new DaoListaM();
		linhas = new ArrayList<String>(listFunc);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String nome = linhas.get(rowIndex);
		switch (columnIndex) {
			case Listas :
				return nome;
			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}
	}

	public boolean contemNomeLista(String nomeLista) {
		for (String nome : linhas) {
			if (nome.equals(nomeLista)) {
				return true;
			}
		}
		return false;
	}

	public String getLista(int linha) {
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
		String nome = linhas.get(rowIndex);
		nome = ((String) aValue);
		// fireTableCellUpdated(rowIndex, columnIndex);
		// daoLista.editaLista(checkList);
	}

	@Override
	public String getColumnName(int columnIndex) {
		return colunas[columnIndex];
	};

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
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
