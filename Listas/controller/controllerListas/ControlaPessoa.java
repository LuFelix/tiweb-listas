package controllerListas;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import commomDao.DaoPessoa;
import modelTables.TableModelPessoa;

public class ControlaPessoa {
	private TableModelPessoa modelTblFunc;
	private JTable tblPessoas;
	private DaoPessoa daoPessoa;

	/**
	 * Cria uma tabela contendo as listas aproximadas
	 * 
	 * @return JTable --
	 */
	public JTable tblFuncListaCompleta() {
		modelTblFunc = new TableModelPessoa(daoPessoa.carregaFunc());
		tblPessoas = new JTable(modelTblFunc);
		tblPessoas.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 40 || e.getKeyCode() == 38) {
					// carregaItensLista();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// carregaItensLista();

			}
		});
		tblPessoas.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// carregaItensLista();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// carregaItensLista();
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// carregaItensLista();
			}
		});

		tblPessoas.setShowHorizontalLines(false);
		return tblPessoas;
	}

}
