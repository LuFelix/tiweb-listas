package Listas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import beansListas.Grupo;
import controllerListas.ControlaLista;

public class MnuCrudGrupos extends JMenu {
	ControlaLista contList;
	Grupo g;
	public MnuCrudGrupos(Grupo g, int posX, int posY) {

		super();
		this.g = g;
		contList = new ControlaLista();
		criaMenuCrudGrupo(posX, posY);
	}

	void criaMenuCrudGrupo(int posX, int posY) {

		JPopupMenu menu = new JPopupMenu();
		JMenuItem item = new JMenuItem("Criar grupo");
		JMenuItem item2 = new JMenuItem("Renomear grupo");
		JMenuItem item3 = new JMenuItem("Remover grupo");
		menu.add(item);
		menu.add(item2);
		menu.add(item3);

		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				contList.criaGrupo();
			}

		});
		item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				g = PnlTreeRoot.pegaGrupodaArvore();
				contList.alteraNomeGrupo(g);

			}

		});
		item3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				g = PnlTreeRoot.pegaGrupodaArvore();
				contList.removeGrupo(g);
			}

		});

		menu.show(PnlTreeRoot.getTreeGrupos(), posX, posY);

	}

}
