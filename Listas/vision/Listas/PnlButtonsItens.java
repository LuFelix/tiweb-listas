package Listas;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import beansListas.CheckList;
import beansListas.ItemCheckList;
import controllerListas.ControlaLista;

public class PnlButtonsItens extends JPanel {
	JButton btnAdd;
	JButton btnRem;
	JRadioButton rdBtnConcluidos;
	static JRadioButton rdBtnConcluir;
	JRadioButton rdBtnTodos;
	ButtonGroup grpBtnRadio;
	ControlaLista contList;
	CheckList checkList;
	ItemCheckList item;
	JPanel pnlFunction;

	public PnlButtonsItens(ControlaLista contList) {
		super();
		this.contList = contList;
		criaBotoes();
		setLayout(new GridLayout(1, 1));
		add(pnlFunction);
	}
	void criaBotoes() {
		/**
		 * Botão de adição
		 * 
		 * @author Luciano Felix
		 */
		btnAdd = new JButton("Adicionar Ítem");
		btnAdd.setBackground(new Color(0, 0, 0, 0));
		btnAdd.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				btnAdd.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void focusGained(FocusEvent e) {
				btnAdd.setBackground(null);

			}
		});
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (contList.getTblListas() != null
						& contList.getTblListas().getSelectedRow() != -1) {
					String nome = JOptionPane
							.showInputDialog("Informe um ítem");

					checkList = contList.pegaListadaTabela();
					if (checkList != null
							& (contList.adicionaItem(checkList, nome))) {
						PnlTarefasNotas.getScrTblTarefas().setViewportView(
								contList.tblItens(checkList, false));
						// TODO FAzer a tabela rolar acompanhando a adição de
						// ítens
						btnAdd.grabFocus();
					} else {
						contList.erro();
					}

				}
			}
		});
		btnAdd.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAdd.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAdd.setBackground(null);

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		btnRem = new JButton("Remover Ítem");
		btnRem.setBackground(new Color(0, 0, 0, 0));
		btnRem.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				btnRem.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void focusGained(FocusEvent e) {
				btnRem.setBackground(null);

			}
		});
		btnRem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (contList.getTblItens() != null) {
					int linha = contList.getTblItens().getSelectedRow();
					if (linha != -1) {
						item = contList.pegaItemTabela();
						contList.removeItem(item.getSeqItem());
						PnlTarefasNotas.exibeItens();
						contList.getTblItens().changeSelection(linha - 1, 0,
								false, false);
						PnlTarefasNotas.exibeObsItem();
					} else {
						contList.erro();
					}
				}
			}
		});
		btnRem.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnRem.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnRem.setBackground(null);

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		grpBtnRadio = new ButtonGroup();

		rdBtnConcluir = new JRadioButton("Concluir", true);
		rdBtnConcluir.setName("Concluir");
		rdBtnConcluir.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				JRadioButton rdBtn = (JRadioButton) e.getSource();;
				if (rdBtn.getModel().isSelected()) {
					if (rdBtn.getName().equals("Concluir")) {
						PnlTarefasNotas.exibeItens(false);
					}
				}
			}
		});
		rdBtnConcluidos = new JRadioButton("Concluidos");
		rdBtnConcluidos.setName("Concluidos");
		rdBtnConcluidos.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JRadioButton rdBtn = (JRadioButton) e.getSource();;
				if (rdBtn.getModel().isSelected()) {
					if (rdBtn.getName().equals("Concluidos")) {
						PnlTarefasNotas.exibeItens(true);
					}
				}
			}
		});
		rdBtnTodos = new JRadioButton("Todos");
		rdBtnTodos.setName("Todos");
		rdBtnTodos.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				JRadioButton rdBtn = (JRadioButton) e.getSource();;
				if (rdBtn.getModel().isSelected()) {
					if (rdBtn.getName().equals("Todos")) {
						PnlTarefasNotas.exibeItens();
					}
				}
			}
		});

		grpBtnRadio.add(rdBtnConcluir);
		grpBtnRadio.add(rdBtnConcluidos);
		grpBtnRadio.add(rdBtnTodos);

		pnlFunction = new JPanel(new GridLayout(0, 5));
		pnlFunction.add(btnAdd);
		pnlFunction.add(btnRem);
		pnlFunction.add(rdBtnConcluir);

		pnlFunction.add(rdBtnConcluidos);
		pnlFunction.add(rdBtnTodos);

	}

}
