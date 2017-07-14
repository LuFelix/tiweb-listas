package br.com.recomendacao.tableModels.commom;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.recomendacao.beans.Conta;
import br.com.recomendacao.dao.DAOConta;

public class TableModelContas extends AbstractTableModel {

	private DAOConta daoCont;
	private ArrayList<Conta> linhas;
	private String[] colunas = new String[]{"Nome", "Descrição"};
	private static final int Nome = 0;
	private static final int Descricao = 1;
	public TableModelContas() {
		daoCont = new DAOConta();
		linhas = new ArrayList<Conta>();
	}

	public TableModelContas(List<Conta> list) {
		daoCont = new DAOConta();
		linhas = new ArrayList<Conta>(list);
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return linhas.size();
	}
	public Conta getConta(int linha) {
		return linhas.get(linha);
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return colunas.length;
	}
	@Override
	public String getColumnName(int columnIndex) {
		return colunas[columnIndex];
	};

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Conta c = linhas.get(rowIndex);
		switch (columnIndex) {
			case Nome :
				return c.getNomeConta();
			case Descricao :
				return c.getDescConta();
			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}

	}

}
