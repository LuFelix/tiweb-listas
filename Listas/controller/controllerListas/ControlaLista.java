package controllerListas;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import beansListas.CheckList;
import beansListas.ItemCheckList;
import commomVision.FrmPrincipal;
import daoListas.DaoLista;
import modelTables.TableModelItem;
import modelTables.TableModelLista;

public class ControlaLista {

	TableModelLista modelTblLista;
	TableModelItem modelTblItens;
	List<CheckList> listChkList;
	List<ItemCheckList> listItens;
	DaoLista daoLista;
	private JTable tblItens;
	private JTable tblListas;
	private JTextArea textArea;
	CheckList checkList;
	ItemCheckList itm;

	public ControlaLista() {
		daoLista = new DaoLista();

	}

	private void ajustaColunas() {
		DefaultTableCellRenderer rendererCentro = new DefaultTableCellRenderer();
		rendererCentro.setHorizontalAlignment(SwingConstants.CENTER);
		DefaultTableCellRenderer rendererDireita = new DefaultTableCellRenderer();
		rendererDireita.setHorizontalAlignment(SwingConstants.RIGHT);

		DefaultTableCellRenderer rendererEsquerda = new DefaultTableCellRenderer();
		rendererEsquerda.setHorizontalAlignment(SwingConstants.LEFT);

		JTableHeader header = tblItens.getTableHeader();
		header.setPreferredSize(new Dimension(0, 20));
		TableColumnModel modeloDaColuna = tblItens.getColumnModel();
		// modeloDaColuna.getColumn(0).setCellRenderer(rendererCentro);
		modeloDaColuna.getColumn(1).setCellRenderer(rendererEsquerda);
		modeloDaColuna.getColumn(0).setMaxWidth(70);

	}

	/**
	 * Cria uma tabela contendo as listas aproximadas
	 * 
	 * @return JTable --
	 */
	public JTable tblListaCompleta() {
		modelTblLista = new TableModelLista(daoLista.carregaListas());
		tblListas = new JTable(modelTblLista);
		tblListas.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 40 || e.getKeyCode() == 38) {
					carregaItensLista();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				carregaItensLista();

			}
		});
		tblListas.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				carregaItensLista();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				carregaItensLista();
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				carregaItensLista();
			}
		});

		tblListas.setShowHorizontalLines(false);
		return tblListas;
	}

	/**
	 * Retorna uma tabela com os ítens recebendo a lista
	 * 
	 * @param CheckList
	 *            -- checkList
	 * @return JTable -- tblItens
	 */
	public JTable tblItens(CheckList checkList) {
		tblItens = new JTable();
		modelTblItens = new TableModelItem(daoLista.carregarItens(checkList));
		tblItens.setModel(modelTblItens);
		tblItens.setShowHorizontalLines(false);
		ajustaColunas();

		return tblItens;
	}

	public JTable tblItens(int numLista) {
		tblItens = new JTable();
		modelTblItens = new TableModelItem(daoLista.carregarItens(numLista));
		tblItens.setModel(modelTblItens);
		tblItens.setShowHorizontalLines(false);
		ajustaColunas();
		return tblItens;
	}

	/**
	 * Retorna uma tabela com os ítens recebendo o nome da lista como parâmetro
	 * 
	 * @param String
	 *            -- nomeLista - Nome da lista para exibição de ítens
	 * @return JTable
	 */
	public JTable tblItens(String nomeLista) {
		tblItens = new JTable();
		modelTblItens = new TableModelItem(daoLista.carregarItens(nomeLista));
		tblItens.setShowHorizontalLines(false);
		tblItens.setModel(modelTblItens);
		ajustaColunas();
		return tblItens;
	}

	/**
	 * Grava uma nota recebendo a nota como parêmetro, captura a seleção da
	 * tabela para identificar a lista de referência.
	 * 
	 * @param checkList
	 */
	public boolean atualizaNota(CheckList checkList) {
		if (tblListas.getSelectedRow() != -1) {
			daoLista.gravaNota(checkList.getNumLista(), checkList.getNota());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * retorna uma lista da tabela caregada
	 * 
	 * @return Checklist -- checkList
	 */
	public CheckList carregaListadaTabela(int linha) {
		return modelTblLista.getLista(linha);
	}

	public boolean salvaNota() {
		if (tblListas.getSelectedRow() != -1) {
			checkList = modelTblLista.getLista(tblListas.getSelectedRow());
			daoLista.gravaNota(checkList.getNumLista(), textArea.getText());
			return true;
		} else {
			return false;
		}
	}

	public JTextArea exibeNota() {
		checkList = modelTblLista.getLista(tblListas.getSelectedRow());
		textArea = new JTextArea(daoLista.carregaNota(checkList.getNumLista()));
		return textArea;
	}

	/**
	 * Cria uma nova Lista
	 * 
	 * @param String
	 *            nomeLista - nome para a nova lista criada.
	 */
	public void criaLista(String nomeLista) {
		if (nomeLista != null) {
			if (nomeLista.isEmpty()) {
				nomeLista = "Lista sem nome ";
				if (!modelTblLista.contemNomeLista(nomeLista)) {
					checkList = new CheckList();
					checkList.setNomeLista(nomeLista);
					daoLista.addLista(checkList);
				} else if (JOptionPane.showConfirmDialog(null, "Criar com mesmo nome?") == 0) {
					checkList = new CheckList();
					checkList.setNomeLista(nomeLista);
					daoLista.addLista(checkList);
				}
			} else if (!modelTblLista.contemNomeLista(nomeLista)) {
				checkList = new CheckList();
				checkList.setNomeLista(nomeLista);
				daoLista.addLista(checkList);
			} else if (JOptionPane.showConfirmDialog(null, "Criar com mesmo nome?") == 0) {
				checkList = new CheckList();
				checkList.setNomeLista(nomeLista);
				daoLista.addLista(checkList);
			} else {

			}
		}
	}

	/**
	 * Remove a lista informando o código para a remoção
	 * 
	 * @param numLista
	 */
	public boolean removeLista() {
		int linha = tblListas.getSelectedRow();
		if (linha != -1) {
			checkList = modelTblLista.getLista(tblListas.getSelectedRow());
			daoLista.remLista(checkList);
			tblListas.changeSelection(linha - 1, 0, false, false);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Adiciona um ítem a lista passada por parâmentro
	 * 
	 * @param numLista
	 *            int - número da lista para inserir o ítem
	 * @param item
	 *            String - nome para o ítem a ser inserido
	 */
	public boolean adicionaItem(String item) {
		if (tblListas.getSelectedRow() != -1) {
			checkList = modelTblLista.getLista(tblListas.getSelectedRow());
			itm = new ItemCheckList();
			itm.setItem(item);
			daoLista.addItem(checkList, itm);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Remove o ítem da lista, passados por paraâmetro
	 * 
	 * @param numLista
	 *            int - número da lista que contém o ítem
	 * @param seqItem
	 *            int - número do ítem a ser removido
	 */
	public void removeItem(int seqItem) {
		checkList = modelTblLista.getLista(tblListas.getSelectedRow());
		itm = new ItemCheckList();
		itm.setSeqItem(seqItem);
		daoLista.removeItem(itm);
	}

	/**
	 * Remove o ítem da lista lendo direto a tabela
	 * 
	 * @param numLista
	 *            int - número da lista que contém o ítem
	 * @param seqItem
	 *            int - número do ítem a ser removido
	 */
	public void removeItem() {
		if (!tblItens.equals(null) & tblItens.getSelectedRow() != -1) {
			itm = modelTblItens.getItem(tblItens.getSelectedRow());
			daoLista.removeItem(itm);
		} else {
			erroLista();
		}
	}

	public void carregaItensLista() {
		if (tblListas.getSelectedRow() != -1) {
			checkList = modelTblLista.getLista(tblListas.getSelectedRow());
			FrmPrincipal.getScrTarefas().setViewportView(tblItens(checkList));
			FrmPrincipal.getScrNotas().setViewportView(exibeNota());
		}
	}

	public void editaLista(String nomeLista) {
		if (tblListas.getSelectedRow() != -1) {
			checkList = modelTblLista.getLista(tblListas.getSelectedRow());
			daoLista.editaLista(nomeLista, checkList);
		} else {
			erroLista();
		}
	}

	public void erroLista() {
		JOptionPane.showMessageDialog(null, "Por favor!\n" + "Selecione uma lista para executar esta operação!",
				"Erro ao capturar seleção de lista", JOptionPane.ERROR_MESSAGE);

	}

	public void erroItem() {
		JOptionPane.showMessageDialog(null, "Por favor!\n" + "Selecione um ítem para executar esta operação!",
				"Erro ao capturar seleção de ítem", JOptionPane.ERROR_MESSAGE);

	}

	public JTable getTblItens() {
		return tblItens;
	}

	public void setTblItens(JTable tblItens) {
		this.tblItens = tblItens;
	}

	public JTable getTblListas() {
		return tblListas;
	}

	public void setTblListas(JTable tblListas) {
		this.tblListas = tblListas;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

}
