package modelTables;

import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import beansListas.Grupo;

public class TreeModelGruposListas implements TreeModel {

	private String raiz = "Grupos";
	List<Grupo> listGrupos;
	public TreeModelGruposListas(List<Grupo> listGrupos) {
		this.listGrupos = listGrupos;
	}

	@Override
	public Object getRoot() {
		return raiz;
	}

	@Override
	public Object getChild(Object parent, int index) {

		if (parent == raiz) // É o nó principal?

			return listGrupos.get(index); // Pegamos da
		// lista de
		// livro
		if (parent instanceof Grupo) // O pai é um livro?
		{

			return ((Grupo) parent).getListChkList().get(index);

		}
		// Se o pai não é nenhum desses. Melhor dar erro.
		throw new IllegalArgumentException(
				"Invalid parent class" + parent.getClass().getSimpleName());
	}

	@Override
	public int getChildCount(Object parent) {

		if (parent == raiz) {

			return listGrupos.size();
		}
		if (parent instanceof Grupo) { // O pai é um Grupo?

			return ((Grupo) parent).getListChkList().size();
		}

		throw new IllegalArgumentException(
				"Invalid parent class " + parent.getClass().getSimpleName());

	}

	@Override
	public boolean isLeaf(Object node) {
		return node instanceof Grupo;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent == raiz)
			return listGrupos.indexOf(child);
		if (parent instanceof Grupo)
			return ((Grupo) parent).getListChkList().indexOf(child);
		return 0;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub

	}

}
