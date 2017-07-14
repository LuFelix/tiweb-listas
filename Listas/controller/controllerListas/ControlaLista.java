package controllerListas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import Listas.MnuGrupos;
import Listas.PnlTarefasNotas;
import Listas.PnlTreeRoot;
import beansListas.CheckList;
import beansListas.Grupo;
import beansListas.ItemCheckList;
import commomVision.FrmPrincipal;
import daoListas.DaoLista;
import modelTables.TableModelItem;
import modelTables.TableModelLista;

public class ControlaLista {

	TableModelLista modelTblLista;
	TableModelItem modelTblItens;
	private JTable tblItens;
	private JTable tblListas;
	private JTextArea txtAObsItem;
	private JTextArea txtNota;
	List<Grupo> listGruposChkList;
	List<CheckList> listChkList;
	List<ItemCheckList> listItens;

	CheckList checkList;
	ItemCheckList item;
	DaoLista daoLista;

	/**
	 * Construtor
	 */
	public ControlaLista() {
		daoLista = new DaoLista();

		txtAObsItem = new JTextArea();
		txtAObsItem.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				atualizaObsItem(pegaItemTabela(), txtAObsItem.getText());

			}
		});

		txtNota = new JTextArea();
		txtNota.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				salvaNota(pegaListadaTabela(), txtNota.getText());
			}
		});

	}

	/**
	 * TODO Controle de Pacotes
	 */

	/**
	 * Pacotes - Cria Pacote
	 */
	public void criaPacote() {

	}

	/**
	 * TODO Controle de Grupos
	 */

	/**
	 * Grupos - Cria um grupo solicitando o nome
	 * 
	 * @author Luciano Felix
	 * 
	 * @param String
	 *            nomeGrupo - nome para a novo grupo.
	 */
	public Grupo criaGrupo() {
		String nomeGrupo = JOptionPane.showInputDialog("Nome do grupo?");
		Grupo g = new Grupo();
		g.setNome(nomeGrupo);
		if (daoLista.addGrupo(nomeGrupo)) {
			iniciaGrupo(g);
			return g;
		}
		return null;
	}

	/**
	 * Grupos - Uma lista com os grupos existentes
	 * 
	 * @return listGrupos
	 */
	public List<Grupo> listGrupos() {

		return daoLista.lerGrupos();

	}

	/**
	 * Grupos - Remove o grupo
	 * 
	 * @author Luciano Felix
	 * 
	 * @param Grupo
	 *            g - grupo a ser removido
	 */
	public void removeGrupo(Grupo g) {
		if (g != null) {
			int opt = JOptionPane.showConfirmDialog(null, "Remover o grupo? \n"
					+ g.getNome() + "\n Não poderá ser desfeito!");
			switch (opt) {
				case 0 :
					daoLista.remGrupo(g);
					iniciaGrupo(g);
					break;
				default :
					break;
			}
		} else {
			erro();

		}

	}

	/**
	 * Grupos - Altera o nome do grupo solicitando o novo nome
	 * 
	 * @author Luciano Felix
	 * 
	 * @param Grupo
	 *            g - Grupo que recebera o novo nome
	 */
	public void alteraNomeGrupo(Grupo g) {
		if (g != null) {
			String nome = JOptionPane.showInputDialog("Novo nome:");
			g.setNome(nome);
			daoLista.atualizaGrupo(g);
			iniciaGrupo(g);
		} else {
			erro();
		}
	}

	/**
	 * Grupos - Inicia Grupo
	 * 
	 * @author Luciano Felix
	 * 
	 * 
	 */
	public void iniciaGrupo(Grupo g) {
		PnlTreeRoot.criaArvore();
		PnlTarefasNotas.exibeTbl(g);
		FrmPrincipal.atualizaArvore();
	}

	/**
	 * TODO Controle de Listas
	 */

	/**
	 * Listas - Cria uma nova Lista recebendo o nome
	 * 
	 * @author Luciano Felix
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
					Grupo g = new Grupo();
					g.setSeq(0);
					checkList.setGrupo(g);
					checkList.setNomeLista(nomeLista);
					daoLista.addLista(checkList);
					PnlTarefasNotas.exibeTbl(g);
				} else if (JOptionPane.showConfirmDialog(null,
						"Criar com mesmo nome?") == 0) {
					Grupo g = new Grupo();
					g.setSeq(0);
					checkList.setGrupo(g);
					checkList.setNomeLista(nomeLista);
					daoLista.addLista(checkList);
					PnlTarefasNotas.exibeTbl(g);
				}
			} else if (!modelTblLista.contemNomeLista(nomeLista)) {
				checkList = new CheckList();
				Grupo g = new Grupo();
				g.setSeq(0);
				checkList.setGrupo(g);
				checkList.setNomeLista(nomeLista);
				daoLista.addLista(checkList);
				PnlTarefasNotas.exibeTbl(g);
			} else if (JOptionPane.showConfirmDialog(null,
					"Criar com mesmo nome?") == 0) {
				checkList = new CheckList();
				Grupo g = new Grupo();
				g.setSeq(0);
				checkList.setGrupo(g);
				checkList.setNomeLista(nomeLista);
				daoLista.addLista(checkList);
				PnlTarefasNotas.exibeTbl(g);
			} else {

			}
		}
	}

	/**
	 * Listas - Retorna uma lista selecionando da tabela
	 * 
	 * @author Luciano Felix
	 * 
	 * @return Checklist -- checkList
	 */
	public CheckList pegaListadaTabela() {
		return modelTblLista.getLista(tblListas.getSelectedRow());
	}

	/**
	 * Listas - Altera o nome da lista recebendoo novo nome
	 * 
	 * @author Luciano Felix
	 * 
	 * @param String
	 *            -- nome da lista
	 */
	public void alteraNomeLista(String nomeLista) {
		if (tblListas.getSelectedRow() != -1) {
			checkList = modelTblLista.getLista(tblListas.getSelectedRow());
			checkList.setNomeLista(nomeLista);
			daoLista.atualizaLista(checkList);
		} else {
			erro();
		}
	}

	/**
	 * Listas - Altera o Grupo da lista recebendoo a lista e o grupo de destino
	 * 
	 * @author Luciano Felix
	 * 
	 * @param CheckList
	 *            -- checkList lista a ser enviada
	 * @param Grupo
	 *            -- g para receber a lista
	 */
	public void alteraGrupoLista(CheckList checkList, Grupo g) {
		checkList.setGrupo(g);
		daoLista.alteraGrupoLista(checkList);
	}

	/**
	 * Listas - Retorna uma tabela contendo todas as listas sem os ítens.
	 * 
	 * @author Luciano Felix
	 * 
	 * @see KeyListener carregarItensLista;
	 * @see MouseListener carregarItensLista;
	 * 
	 * @return JTable --
	 */
	public JTable tblListaCompleta() {
		modelTblLista = new TableModelLista(daoLista.lerListas());
		tblListas = new JTable(modelTblLista);
		tblListas.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 40 || e.getKeyCode() == 38) {
					iniciaTarefaNotas();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				iniciaTarefaNotas();
			}
		});

		tblListas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {

				if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
					menuBtnDirTbl(me.getX(), me.getY());

				} else {

				}
			}
		});
		tblListas.setShowHorizontalLines(false);
		return tblListas;
	}

	/**
	 * Listas - Retorna uma tabela de listas sem os ítens recebendo o grupo
	 * 
	 * @author Luciano Felix
	 * 
	 * @param Grupo
	 *            -- g
	 * @return Jtable tblListas
	 */
	public JTable tblLista(Grupo grupo) {
		modelTblLista = new TableModelLista(daoLista.lerListas(grupo.getSeq()));
		tblListas = new JTable(modelTblLista);
		tblListas.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 40 || e.getKeyCode() == 38) {
					iniciaTarefaNotas();
				}
			}

			@Override

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 40 || e.getKeyCode() == 38) {
					iniciaTarefaNotas();
				}
			}
		});
		tblListas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				// Verificando se o botão direito do mouse foi clicado
				if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
					menuBtnDirTbl(me.getX(), me.getY());

				} else {
					iniciaTarefaNotas();
				}
			}

		});
		tblListas.setShowHorizontalLines(false);
		// tblListas.setShowHorizontalLines(true);
		return tblListas;
	}

	/**
	 * Listas - Remove a lista recebida por parâmetro
	 * 
	 * @param CheckList
	 *            -- Lista
	 */
	public boolean removeLista(CheckList checkList) {
		if (daoLista.remLista(checkList)) {
			iniciaTarefaNotas();
			PnlTarefasNotas.exibeTbl(checkList.getGrupo());
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Listas - Remove a lista selecionada na tabela
	 * 
	 * @param checkList
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
	 * Listas - Menu de opções do btnDireito do mouse sobre tabela de Listas
	 * 
	 * @param checkList
	 */
	void menuBtnDirTbl(int posX, int posY) {

		JPopupMenu menu = new JPopupMenu("Operações");
		if (tblListas.getSelectedRow() != -1) {
			checkList = modelTblLista.getLista(tblListas.getSelectedRow());
			JMenuItem item = new JMenuItem("Remover");
			JMenu subMenu = new JMenu("Enviar para...");
			new MnuGrupos(checkList, subMenu);
			menu.add(item);
			menu.add(subMenu);

			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					int op = JOptionPane.showConfirmDialog(null,
							"Remover a lista " + checkList.getNomeLista());
					if (op == 1) {
						removeLista(checkList);
					}

				}
			});

			menu.show(tblListas, posX, posY);
		}

	}

	/**
	 * TODO Controle de ítens
	 */

	/**
	 * Ítens - Adiciona um ítem à lista selecionada na tabela recebendo o nome
	 * 
	 * @author Luciano Felix
	 * 
	 * @param item
	 *            String - nome para o ítem a ser inserido
	 */
	public boolean adicionaItem(String nome) {
		if (tblListas.getSelectedRow() != -1) {
			checkList = modelTblLista.getLista(tblListas.getSelectedRow());
			item = new ItemCheckList();
			item.setItem(nome);
			daoLista.addItemListaSeqLista(checkList, item);
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Ítens - Adiciona um ítem à lista
	 * 
	 * @author Luciano Felix
	 * 
	 * @param item
	 *            String - nome para o ítem a ser inserido
	 */
	public boolean adicionaItem(CheckList checkList, String nome) {
		if (checkList != null) {
			item = new ItemCheckList();
			item.setItem(nome);
			daoLista.addItemListaSeqLista(checkList, item);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Ítens - Retorna uma tabela com os ítens recebendo a lista
	 * 
	 * @author Luciano Felix
	 * 
	 * @param CheckList
	 *            -- checkList Lista para montar a tabela de ítens
	 * @return JTable -- tblItens
	 */
	public JTable tblItens(CheckList checkList) {
		tblItens = new JTable();
		modelTblItens = new TableModelItem(
				daoLista.lerItensListaSeq(checkList.getNumLista()));
		tblItens.setModel(modelTblItens);
		tblItens.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 40 || e.getKeyCode() == 38) {
					PnlTarefasNotas.exibeObsItem();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				PnlTarefasNotas.exibeObsItem();
			}
		});
		tblItens.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {

				if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
					// botão direito

				} else {
					PnlTarefasNotas.exibeObsItem();
				}
			}
		});

		ajustaColunas();
		tblItens.setShowGrid(true);
		return tblItens;
	}

	/**
	 * Ítens - Retorna uma tabela com os ítens recebendo a lista e o status
	 * 
	 * @author Luciano Felix
	 * 
	 * @param CheckList
	 *            -- checkList Lista para montar a tabela de ítens
	 * @return JTable -- tblItens
	 */
	public JTable tblItens(CheckList checkList, boolean status) {
		tblItens = new JTable();
		modelTblItens = new TableModelItem(
				daoLista.lerItensListaSeq(checkList.getNumLista(), status));
		tblItens.setModel(modelTblItens);
		tblItens.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 40 || e.getKeyCode() == 38) {
					PnlTarefasNotas.exibeObsItem();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				PnlTarefasNotas.exibeObsItem();
			}
		});
		tblItens.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {

				if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
					// botão direito

				} else {
					PnlTarefasNotas.exibeObsItem();
				}
			}
		});

		ajustaColunas();
		tblItens.setShowGrid(true);
		return tblItens;
	}

	/**
	 * Ítens - Exibe obs do ítem selecionado na tabela no painel inferior
	 * 
	 * @author Luciano Felix
	 */
	public ItemCheckList pegaItemTabela() {

		if (tblItens.getSelectedRow() != -1) {
			return modelTblItens.getItem(tblItens.getSelectedRow());
		} else {
			return null;
		}

	}

	/**
	 * Ítem - Atualizar a obsservação do ítem
	 */
	public void atualizaObsItem(ItemCheckList item, String txt) {
		item.setObservacao(txt);
		daoLista.atualizaItem(item);

	}

	/*
	 * Ítens - Remove um ítem recebendo a sequencia
	 * 
	 * @author Luciano Felix
	 * 
	 * @param seqItem int - número do ítem a ser removido
	 */
	public void removeItem(int seqItem) {
		item = new ItemCheckList();
		item.setSeqItem(seqItem);
		daoLista.remItem(item);
	}

	/**
	 * Ítens - Remove o ítem selecionado na tabela
	 */
	public void removeItem() {
		if (!tblItens.equals(null) & tblItens.getSelectedRow() != -1) {
			item = modelTblItens.getItem(tblItens.getSelectedRow());
			daoLista.remItem(item);
		} else {
			erro();
		}
	}

	/**
	 * Ítens - Exibe a observação do ítem
	 * 
	 * @author Luciano Felix
	 */
	public JTextArea carregaObsItem() {
		if (tblItens.getSelectedRow() != -1) {
			System.out.println(tblItens.getSelectedRow());
			item = pegaItemTabela();
			txtAObsItem.setText(item.getObservacao());
			return txtAObsItem;
		} else {
			// erro();
			return null;

		}

	}

	/**
	 * TODO Controle de Notas
	 */

	/**
	 * Notas - inicia o painel de notas
	 */
	public void iniciaTarefaNotas() {
		PnlTarefasNotas.exibeItens(false);
		PnlTarefasNotas.exibeNota();
		PnlTarefasNotas.getScrObsItem().setViewportView(null);
	}

	/**
	 * Notas - Salva a nota recebendo a lista e a nota como parêmetros.
	 * 
	 * @author Luciano Felix
	 * 
	 * @param CheckList
	 *            checkList -- lista para a gravação da nota
	 * @param String
	 *            nota -- conteúdo da nota a ser gravada
	 * 
	 */
	public boolean salvaNota(CheckList checkList, String nota) {
		try {
			daoLista.atualizaNota(checkList.getNumLista(), nota);
			return true;
		} catch (SQLException e) {
			erro();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Notas - Exibe a nota da lista
	 * 
	 * @param CheckList
	 *            checkList -- carrega a nota da lista
	 */
	public JTextArea carregaNota() {
		if (tblListas.getSelectedRow() != -1) {
			checkList = pegaListadaTabela();
			txtNota.setText(daoLista.lerNota(checkList.getNumLista()));
			return txtNota;
		}
		return null;
	}

	/**
	 * TODO Controles adicionais
	 */
	/**
	 * Informa um erro em um JOptionPane
	 * 
	 * @author Luciano Felix
	 */
	public void erro() {
		JOptionPane.showMessageDialog(null,
				"Por favor!\n" + "Selecione algo para executar esta operação!",
				"Erro ao capturar seleção", JOptionPane.ERROR_MESSAGE);

	}

	/**
	 * ajusta colunas da tabela
	 */
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
	 * TODO Getters and Setters
	 */
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

}
