package modelTables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import beansListas.ItemCheckList;
import daoListas.DaoLista;

public class TableModelItem extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ItemCheckList> linhas;
	private String[] colunas = new String[]{"Concluído", "Ítem", "Observação"};
	private static final int Concluido = 0;
	private static final int Item = 1;
	private static final int Observacao = 2;
	private DaoLista daoLista;

	public TableModelItem() {
		linhas = new ArrayList<ItemCheckList>();
	}

	public TableModelItem(List<ItemCheckList> listDados) {
		daoLista = new DaoLista();
		linhas = new ArrayList<ItemCheckList>(listDados);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		ItemCheckList item = linhas.get(rowIndex);

		switch (columnIndex) {

			case Concluido :
				item.setConcluido((boolean) aValue);
				break;
			case Item :
				item.setItem((String) aValue);
				break;
			case Observacao :
				item.setObservacao((String) aValue);
				break;
			default :

				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}

		fireTableCellUpdated(rowIndex, columnIndex);
		daoLista.atualizaItem(item);

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ItemCheckList itemCheckList = linhas.get(rowIndex);
		switch (columnIndex) {
			case Concluido :
				return itemCheckList.isConcluido();
			case Item :
				return itemCheckList.getItem();
			case Observacao :
				return itemCheckList.getObservacao();

			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {

			case Concluido :
				return columnIndex == Concluido;
			case Item :
				return columnIndex == Item;
			case Observacao :
				return columnIndex == Observacao;
			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}

	}

	public ItemCheckList getItem(int linha) {
		return linhas.get(linha);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		switch (columnIndex) {

			case Concluido :
				return Boolean.class;
			case Item :
				return String.class;
			case Observacao :
				return String.class;
			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}
	}

	@Override
	public String getColumnName(int columnIndex) {
		return colunas[columnIndex];
	};

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return linhas.size();
	}

}
