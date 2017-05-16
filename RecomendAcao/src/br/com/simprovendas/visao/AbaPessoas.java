package br.com.simprovendas.visao;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import br.com.simprovendas.util.ModeloArvore;

public class AbaPessoas extends JPanel implements TreeSelectionListener {
	JPanel painelPrincipal;
	private JLabel lblTituloTela;
	// Labels e text fields

	// Arvore do Sistema
	private static JTree arvore;
	private static JTree arvoreStatus;
	private static String nomeNo;
	private DefaultMutableTreeNode root;
	private DefaultMutableTreeNode clientes;
	private DefaultMutableTreeNode fornecedores;
	private DefaultMutableTreeNode funcionarios;
	private DefaultMutableTreeNode pessoas;
	private DefaultMutableTreeNode grupo;

	private JScrollPane scrArvNegocios;
	private DefaultTreeModel modArvore;
	private JSplitPane sppPrincipal;

	public AbaPessoas() {

		// TODO Configuração dos Labels e text fields e árvore de negócios
		lblTituloTela = new JLabel("Pessoas");
		lblTituloTela.setFont(new Font("Times new roman", Font.BOLD, 18));
		clientes = new DefaultMutableTreeNode("Clientes");
		fornecedores = new DefaultMutableTreeNode("Fornecedores");
		funcionarios = new DefaultMutableTreeNode("Funcionários");
		root = new DefaultMutableTreeNode("Root");
		pessoas = new DefaultMutableTreeNode("Pessoas");
		grupo = new DefaultMutableTreeNode("Grupos");
		grupo.add(clientes);
		grupo.add(fornecedores);
		grupo.add(funcionarios);
		
		root.add(pessoas);
		root.add(grupo);
		modArvore = new DefaultTreeModel(root);
		modArvore.addTreeModelListener(new ModeloArvore());
		arvore = new JTree(modArvore);

		// Where the tree is initialized:
		arvore.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		arvore.addTreeSelectionListener(this);
		arvore.setCellRenderer(new RenderizarTreeNegocios());
		arvore.setShowsRootHandles(true);
		arvore.setRootVisible(false);
		arvore.setRowHeight(35);

		// TODO Posicionamento e ações botões
		scrArvNegocios = new JScrollPane(arvore);
		sppPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppPrincipal.setDividerSize(2);
		sppPrincipal.setDividerLocation(310);

		sppPrincipal.add(scrArvNegocios);
		setLayout(new GridLayout());
		add(sppPrincipal);

	}

	// TODO Eventos de Mouse
	// arvoreSistema.addMouseListener(new MouseListener() {
	//
	// @Override
	// public void mouseReleased(MouseEvent arg0) {
	// // TODO Ao soltar o botão
	// System.out.println("Mouse Released");
	//
	// }
	//
	// @Override
	// public void mousePressed(MouseEvent arg0) {
	// //TODO Ao pressionar o botão
	// System.out.println("Mouse Pressed");
	// }
	//
	// @Override
	// public void mouseExited(MouseEvent arg0) {
	// // TODO Ao sair de sobre o objeto
	// System.out.println("Mouse Exited");
	// }
	//
	// @Override
	// public void mouseEntered(MouseEvent arg0) {
	// // TODO Ao entrar sobre o objeto
	// System.out.println("Mouse Entered");
	//
	// }
	//
	// @Override
	// public void mouseClicked(MouseEvent arg0) {
	// // TODO Ao clicar
	// System.out.println("Mouse Clicked");
	//
	// }
	// });

	// TODO Renderizar a árvore negócios
	private class RenderizarTreeNegocios extends DefaultTreeCellRenderer implements TreeCellRenderer {
		private Font plainFont, italicFont;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			
			if (node.toString().equals("Pessoas")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\personfolder_32x32.png"));
			}
			if (node.toString().equals("Grupos")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\workarea.png"));
			}
			
			return this;
		}
	}

	// TODO Capturar seleção de folha
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// Returns the last path element of the selection.
		// This method is useful only when the selection model allows a single
		// selection.

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvore.getLastSelectedPathComponent();
		Object nodeInfo = node.getUserObject();
		nomeNo = nodeInfo.toString();
		if (node != null) {
			
			if (node.isLeaf() & nomeNo.equals("Pessoas")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContUsua().iniciar(AbaPessoas.getNomeNo());
				}
			}
			if (!node.isLeaf() & nomeNo.equals("Grupos")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContGrupo().iniciar();
				}
			}
			
			if (node.isLeaf() & nomeNo.equals("Clientes")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContUsua().iniciar(AbaPessoas.getNomeNo());

				}
			}
			
			if (node.isLeaf() & nomeNo.equals("Fornecedores")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContUsua().iniciar(AbaPessoas.getNomeNo());

				}
			}
			if (node.isLeaf() & nomeNo.equals("Funcionários")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContUsua().iniciar(AbaPessoas.getNomeNo());
				}
			}
			
			
		}
	}
	

	public static void expandirrArvore(JTree tree) {
		try {
			for (int row = 0; row <= tree.getRowCount(); row++) {
				tree.expandRow(row);
			}
		} catch (Exception e) {
			// tratar erro
		}
	}

	public static String getNomeNo() {
		return nomeNo;
	}

	public static void setNomeNo(String nomeNo) {
		AbaPessoas.nomeNo = nomeNo;
	}

	public static JTree getArvoreNegocios() {
		return arvore;
	}

	public static void setArvoreNegocios(JTree arvoreNegocios) {
		AbaPessoas.arvore = arvoreNegocios;
	}
}
