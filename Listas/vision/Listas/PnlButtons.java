package Listas;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import beansListas.CheckList;
import controllerListas.ControlaLista;

public class PnlButtons extends JPanel {

	JButton btnAddItem;
	JButton btnRemItem;
	JButton btnAddLista;
	JButton btnRemLista;

	public PnlButtons(ControlaLista contList, int linhas, int colunas) {

		btnAddLista = new JButton("Criar Lista");
		btnAddLista.setBackground(new Color(0, 0, 0, 0));

		btnAddLista.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				btnAddLista.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void focusGained(FocusEvent e) {
				btnAddLista.setBackground(null);

			}
		});

		btnAddLista.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contList.criaLista(JOptionPane.showInputDialog("Nome lista"));

			}
		});
		btnAddLista.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAddLista.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAddLista.setBackground(null);

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		btnRemLista = new JButton("Remover Lista");
		btnRemLista.setBackground(new Color(0, 0, 0, 0));
		btnRemLista.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				btnRemLista.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void focusGained(FocusEvent e) {
				btnRemLista.setBackground(null);

			}
		});
		btnRemLista.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (contList.getTblListas() != null
						& contList.getTblListas().getSelectedRow() != -1) {
					CheckList chk = contList.pegaListadaTabela();
					if (JOptionPane.showConfirmDialog(null,
							"Remover " + chk.getNomeLista()
									+ "  ?\nNão poderá ser desfeito!") == 0) {
						if (contList.removeLista(chk)) {
							contList.iniciaTarefaNotas();
						} else {
							contList.erro();
						}
					}

				}
			}
		});

		btnRemLista.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnRemLista.setBackground(new Color(0, 0, 0, 0));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnRemLista.setBackground(null);

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		setLayout(new GridLayout(linhas, colunas));
		add(btnAddLista);
		add(btnRemLista);

	}
}
