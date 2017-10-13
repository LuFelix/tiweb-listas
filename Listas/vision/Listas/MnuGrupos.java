package Listas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import beansListas.CheckList;
import beansListas.Grupo;
import controllerListas.ControlaLista;

public class MnuGrupos extends JMenu {
	JMenu menu;
	JMenuItem itemMenu;
	List<Grupo> listGrupos;
	CheckList checkList;
	ControlaLista contList;

	public MnuGrupos(CheckList checkList, JMenu menu, int baseDados) {
		super();
		this.menu = menu;
		this.checkList = checkList;
		contList = new ControlaLista(baseDados);
		criaMenuGrupos();

	}
	JMenu criaMenuGrupos() {

		listGrupos = contList.listGrupos();

		for (int i = 0; i < listGrupos.size(); i++) {
			itemMenu = new JMenuItem(listGrupos.get(i).getNome());
			Grupo g = listGrupos.get(i);
			itemMenu.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					contList.alteraGrupoLista(checkList, g);
				}
			});

			menu.add(itemMenu);
		}
		return menu;
	}
}
