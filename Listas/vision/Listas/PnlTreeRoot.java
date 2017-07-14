package Listas;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import beansListas.Grupo;
import controllerListas.ControlaLista;
import modelTables.TreeModelGruposListas;

public class PnlTreeRoot extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JTree treeGrupos;
	private static TreeModelGruposListas mdlArvGrupos;
	private static ControlaLista contList;

	public PnlTreeRoot() {
		setLayout(new BorderLayout());
		contList = new ControlaLista();
		criaArvore();
		add(treeGrupos, BorderLayout.CENTER);

	}

	/**
	 * Captura grupo selecionado na árvore
	 * 
	 * @return -- Grupo selecionado
	 */
	public static Grupo pegaGrupodaArvore() {
		return (Grupo) treeGrupos.getLastSelectedPathComponent();
	}

	public static void criaArvore() {
		mdlArvGrupos = new TreeModelGruposListas(contList.listGrupos());
		treeGrupos = new JTree(mdlArvGrupos);
		treeGrupos.getSelectionModel()
				.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		treeGrupos.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if (!treeGrupos.isRowSelected(0)) {
					Grupo g = (Grupo) treeGrupos.getLastSelectedPathComponent();
					PnlTarefasNotas.limpaTela();
					PnlTarefasNotas.exibeTbl(g);

				} else {
					PnlTarefasNotas.exibeTbl(new Grupo());
				}
			}
		});
		treeGrupos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				// Verificando se o botão direito do mouse foi clicado
				if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
					new MnuCrudGrupos(pegaGrupodaArvore(), me.getX(),
							me.getY());
				} else {

				}
			}
		});
		treeGrupos.setShowsRootHandles(true);
		treeGrupos.setRootVisible(true);
		treeGrupos.setRowHeight(30);
		ImageIcon openCloseIcon = new ImageIcon(
				"C:\\SIMPRO\\img\\listas\\Order.png");
		ImageIcon leafIcon = new ImageIcon(
				"C:\\SIMPRO\\img\\listas\\listaDocs.png");

		if (leafIcon != null) {
			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
			renderer.setLeafIcon(leafIcon);
			renderer.setClosedIcon(openCloseIcon);
			renderer.setOpenIcon(openCloseIcon);
			treeGrupos.setCellRenderer(renderer);
		}

	}
	public static JTree getTreeGrupos() {
		return treeGrupos;
	}
	public static void setTreeGrupos(JTree treeGrupos) {
		PnlTreeRoot.treeGrupos = treeGrupos;
	}

}
