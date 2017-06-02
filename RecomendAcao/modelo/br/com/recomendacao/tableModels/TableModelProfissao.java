package br.com.recomendacao.tableModels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.recomendacao.beans.PessoaProfissional;
import br.com.recomendacao.dao.DAOPessoaProfissional;

public class TableModelProfissao extends AbstractTableModel {
	private List<PessoaProfissional> linhas;
	private String[] colunas = new String[]{"Nome", "Documento", "PIS",
			"Optante"};
	private static final int Nome = 0;
	private static final int Documento = 1;
	private static final int PIS = 2;
	private static final int Optante = 3;
	DAOPessoaProfissional daoPess;

	public TableModelProfissao() {
		daoPess = new DAOPessoaProfissional();
		linhas = new ArrayList<PessoaProfissional>();
	}

	public TableModelProfissao(List<PessoaProfissional> list) {
		daoPess = new DAOPessoaProfissional();
		linhas = new ArrayList<PessoaProfissional>(list);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		PessoaProfissional p = linhas.get(rowIndex);
		switch (columnIndex) {
			case Nome :
				return p.getNomeProf();
			case Documento :
				return p.getDocFunc();
			case PIS :
				return p.getPis();
			case Optante :
				return p.isOptante();
			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}
	}

	public boolean contemNomeLista(PessoaProfissional pp) {
		for (PessoaProfissional p : linhas) {
			if (p.equals(pp)) {
				return true;
			}
		}
		return false;
	}

	public PessoaProfissional getPessProf(int linha) {
		return linhas.get(linha);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case Nome :
				return String.class;
			case Documento :
				return String.class;
			case PIS :
				return Integer.class;
			case Optante :
				return Boolean.class;
			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		PessoaProfissional pp = linhas.get(rowIndex);
		switch (columnIndex) {

			case Nome :
				pp.setNomeProf((String) aValue);
				break;
			case Documento :
				pp.setDocFunc((String) aValue);
				break;
			case PIS :
				pp.setPis((Integer) aValue);
				break;
			case Optante :
				pp.setOptante((Boolean) aValue);
				break;
			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}

		fireTableCellUpdated(rowIndex, columnIndex);
		daoPess.atualizar(pp);

	}

	@Override
	public String getColumnName(int columnIndex) {
		return colunas[columnIndex];
	};

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case Nome :
				return columnIndex == Nome;
			case Documento :
				return columnIndex == Documento;
			case PIS :
				return columnIndex == PIS;
			case Optante :
				return columnIndex == Optante;
			default :
				throw new IndexOutOfBoundsException(
						"columnIndex out of bounds");
		}

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
