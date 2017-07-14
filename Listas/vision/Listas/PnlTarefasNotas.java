package Listas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import beansListas.CheckList;
import beansListas.Grupo;
import beansListas.ItemCheckList;
import controllerListas.ControlaLista;

public class PnlTarefasNotas extends JSplitPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lblResult;
	JButton btnAdd;
	JButton btnRem;
	static JRadioButton rdBtnConcluidos;
	static JRadioButton rdBtnConcluir;
	JRadioButton rdBtnTodos;
	static ButtonGroup grpBtnRadio;
	JTabbedPane tabPTarNot;

	JPanel pnlFunction;
	JPanel pnlRight;
	JPanel pnlLeft;

	static JScrollPane scrLista;
	JScrollPane scrVisualiza;

	JPanel pnlTarefas;
	static JScrollPane scrNota;
	static JScrollPane scrTblTarefas;
	static JScrollPane scrObsItem;
	private static final String rdBtns[] = {"Concluir", "Concluídos", "Todos"};

	Grupo g;
	static ControlaLista contList;
	CheckList checkList;
	static ItemCheckList item;

	public PnlTarefasNotas(ControlaLista contList, Grupo g) {
		super();
		this.g = g;
		PnlTarefasNotas.contList = contList;

		criaPainel();
		exibeTbl(g);

	}

	public static void limpaTela() {
		getScrLista().setViewportView(null);
		getScrTblTarefas().setViewportView(null);
		getScrObsItem().setViewportView(null);
	}

	void criaPainel() {

		lblResult = new JLabel("");
		lblResult.setFont(new Font("Arial", Font.BOLD, 14));
		lblResult.setHorizontalAlignment(JLabel.CENTER);
		lblResult.setForeground(Color.BLACK);
		lblResult.setText("Tarefa adicionada...");
		lblResult.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		lblResult.setVisible(false);

		lblResult.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblResult.setVisible(false);
			}

		});

		scrTblTarefas = new JScrollPane();
		scrObsItem = new JScrollPane();
		scrNota = new JScrollPane();

		pnlTarefas = new JPanel(new GridLayout(2, 0));
		pnlTarefas.add(scrTblTarefas);
		pnlTarefas.add(scrObsItem);

		tabPTarNot = new JTabbedPane();
		tabPTarNot.add("Tarefas / Itens", pnlTarefas);
		tabPTarNot.addTab("Notas", scrNota);
		tabPTarNot.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabPTarNot.getSelectedIndex() == 0) {
					if (contList.getTblListas().getSelectedRow() != -1) {
						exibeItens();
					}

				} else if (tabPTarNot.getSelectedIndex() == 1) {
					if (contList.getTblListas().getSelectedRow() != -1) {
						exibeNota();
					}

				}
			}
		});

		// criaBotoes();
		pnlRight = new JPanel(new BorderLayout());
		pnlRight.add(new PnlButtonsItens(contList), BorderLayout.NORTH);
		pnlRight.add(tabPTarNot, BorderLayout.CENTER);

		scrLista = new JScrollPane();
		setLeftComponent(scrLista);
		setRightComponent(pnlRight);

	}

	public static void exibeTbl(Grupo g) {
		if (g != null) {
			scrLista.setViewportView(contList.tblLista(g));
		} else {
			g = new Grupo();
			g.setSeq(0);
			scrLista.setViewportView(contList.tblLista(g));
		}
	}
	/**
	 * Exibição de ítens
	 */
	public static void exibeItens(ButtonModel md) {
		System.out.println("PnlTarefasNotas.exibeItens");
		if (md == rdBtnConcluir.getModel()) {
			if (contList.getTblListas().getSelectedRow() != -1) {
				System.out.println("Itens à concluir.");
				scrTblTarefas.setViewportView(
						contList.tblItens(contList.pegaListadaTabela(), false));
			}
		} else if (md == rdBtnConcluidos.getModel()) {
			if (contList.getTblListas().getSelectedRow() != -1) {
				System.out.println("Ítens concluidos.");
				scrTblTarefas.setViewportView(
						contList.tblItens(contList.pegaListadaTabela(), true));
			}
		} else {
			if (contList.getTblListas().getSelectedRow() != -1) {
				System.out.println("Todos os ítens");
				scrTblTarefas.setViewportView(
						contList.tblItens(contList.pegaListadaTabela()));
			}

		}
	}

	public static void exibeItens(Boolean status) {
		if (contList.getTblListas().getSelectedRow() != -1) {
			scrTblTarefas.setViewportView(
					contList.tblItens(contList.pegaListadaTabela(), status));
		}
	}
	public static void exibeItens() {
		System.out.println("Executou exibeItens");
		if (contList.getTblListas().getSelectedRow() != -1) {
			scrTblTarefas.setViewportView(
					contList.tblItens(contList.pegaListadaTabela()));
		}
	}
	/**
	 * Exibição de observação do ítem
	 */
	public static void exibeObsItem() {
		scrObsItem.setViewportView(contList.carregaObsItem());
	}
	/**
	 * Exibição da nota da lista
	 */
	public static void exibeNota() {
		scrNota.setViewportView(contList.carregaNota());
	}

	public static JScrollPane getScrNota() {
		return scrNota;
	}

	public static void setScrNota(JScrollPane scrNota) {
		PnlTarefasNotas.scrNota = scrNota;
	}

	public static JScrollPane getScrObsItem() {
		return scrObsItem;
	}

	public static void setScrObsItem(JScrollPane scrObsItem) {
		PnlTarefasNotas.scrObsItem = scrObsItem;
	}

	public static JScrollPane getScrLista() {
		return scrLista;
	}

	public static void setScrLista(JScrollPane scrLista) {
		PnlTarefasNotas.scrLista = scrLista;
	}

	public static JScrollPane getScrTblTarefas() {
		return scrTblTarefas;
	}

	public static void setScrTblTarefas(JScrollPane scrTblTarefas) {
		PnlTarefasNotas.scrTblTarefas = scrTblTarefas;
	}

}
