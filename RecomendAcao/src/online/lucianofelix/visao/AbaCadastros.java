package br.com.recomendacao.visao;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

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

import br.com.recomendacao.beans.CentroCusto;
import br.com.recomendacao.controle.ControlaCentroCusto;
import br.com.recomendacao.dao.DAOCentroCusto;
import br.com.recomendacao.util.ModeloListenerArvore;
import treeModels.TreeModelCentroCusto;

public class AbaCadastros extends JPanel implements TreeSelectionListener {

	private JLabel lblTituloTela;
	// Labels e text fields

	// Arvore do Sistema
	private static JTree arvore;
	private static JTree arvoreContas;
	private static String nomeNo;
	static ControlaCentroCusto controlaCCusto;
	private static DefaultMutableTreeNode root;
	private static DefaultMutableTreeNode clientes;
	private static DefaultMutableTreeNode fornecedores;
	private static DefaultMutableTreeNode funcionarios;
	private static DefaultMutableTreeNode pessoas;
	private static DefaultMutableTreeNode produtos;

	private JScrollPane scrArvNegocios;
	private static DefaultTreeModel modArvore;
	private static TreeModelCentroCusto modArvCCusto;
	private static JSplitPane sppPrincipal;
	private static DefaultMutableTreeNode grupos;
	private static DefaultMutableTreeNode contas;
	private static DefaultMutableTreeNode centroCusto;
	private static DefaultMutableTreeNode tabelasPrecos;
	private static DefaultMutableTreeNode condPagamento;
	private static DefaultMutableTreeNode servicos;

	public AbaCadastros() {

		// TODO Configuração dos Labels e text fields e árvore de negócios
		lblTituloTela = new JLabel("Pessoas");
		lblTituloTela.setFont(new Font("Times new roman", Font.BOLD, 18));

		criaArvContasCentCusto();
		// criaArvore();
		// TODO Posicionamento e ações botões
		scrArvNegocios = new JScrollPane(arvoreContas);

		sppPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppPrincipal.setDividerSize(2);
		sppPrincipal.setDividerLocation(310);

		sppPrincipal.add(scrArvNegocios);
		setLayout(new GridLayout());
		add(sppPrincipal);

	}

	// TODO Renderizar a árvore negócios
	private class RenderizarTreeNegocios extends DefaultTreeCellRenderer
			implements
				TreeCellRenderer {
		private Font plainFont, italicFont;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean selected, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, selected, expanded,
					leaf, row, hasFocus);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

			if (node.getClass().equals(CentroCusto.class)) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\personfolder_32x32.png"));
			}
			if (node.toString().equals("Pessoas")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\personfolder_32x32.png"));
			}
			if (node.toString().equals("Clientes")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\People32x32.png"));
			}
			if (node.toString().equals("Fornecedores")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\Industry32x32.png"));
			}
			if (node.toString().equals("Funcionários")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\Func32x32.png"));
			}
			if (node.toString().equals("Produtos")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\store.png"));
			}
			if (node.toString().equals("Serviços")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\Equipment.png"));
			}
			if (node.toString().equals("Grupos")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\workarea.png"));
			}
			if (node.toString().equals("Contas")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\contas32x32.png"));
			}
			if (node.toString().equals("Centros de Custo")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\flowblock32x32.png"));
			}
			if (node.toString().equals("Condições de Pagamento")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\business32x32.png"));
			}
			if (node.toString().equals("Tabelas de Preços")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\billing32x32.png"));
			}

			return this;
		}
	}

	// TODO Ao mudar o no;
	@Override
	public void valueChanged(TreeSelectionEvent e) {

		if (!arvoreContas.isRowSelected(0)) {
			CentroCusto c = (CentroCusto) arvoreContas
					.getLastSelectedPathComponent();
			FrameInicial.getContConta().iniciar(c.getCodiCentroCusto());
		} else {
			FrameInicial.getContCentroCusto().iniciar(new CentroCusto());
		}

	}
	public static CentroCusto selecionado() {
		if (!arvoreContas.isRowSelected(0)) {
			return (CentroCusto) arvoreContas.getLastSelectedPathComponent();
		}
		return null;
	}
	public static void carregarNoContas() {
		contas = new DefaultMutableTreeNode("Contas");
		List<CentroCusto> listCCusto = new ArrayList<CentroCusto>(
				controlaCCusto.pesqNomeArray(""));;

		for (int i = 0; i < listCCusto.size(); i++) {
			contas.add(new DefaultMutableTreeNode(
					listCCusto.get(i).getNomeCentroCusto()));
		}
	}

	void criaNos() {
		root = new DefaultMutableTreeNode("Root");
		controlaCCusto = new ControlaCentroCusto();
		produtos = new DefaultMutableTreeNode("Produtos");
		clientes = new DefaultMutableTreeNode("Clientes");
		fornecedores = new DefaultMutableTreeNode("Fornecedores");
		funcionarios = new DefaultMutableTreeNode("Funcionários");
		controlaCCusto = new ControlaCentroCusto();
		pessoas = new DefaultMutableTreeNode("Pessoas");
		pessoas.add(clientes);
		pessoas.add(fornecedores);
		pessoas.add(funcionarios);
		produtos = new DefaultMutableTreeNode("Produtos");
		grupos = new DefaultMutableTreeNode("Grupos");
		centroCusto = new DefaultMutableTreeNode("Centros de Custo");
		// carregarNoContas();
		condPagamento = new DefaultMutableTreeNode("Condições de Pagamento");
		servicos = new DefaultMutableTreeNode("Serviços");
		tabelasPrecos = new DefaultMutableTreeNode("Tabelas de Preços");

		root.add(pessoas);
		root.add(produtos);
		root.add(servicos);
		root.add(grupos);
		root.add(centroCusto);
		// root.add(contas);
		root.add(tabelasPrecos);
		root.add(condPagamento);

	}
	void criaArvore() {
		criaNos();
		modArvore = new DefaultTreeModel(root);
		modArvore.addTreeModelListener(new ModeloListenerArvore());
		arvore = new JTree(modArvore);

		// Where the tree is initialized:
		arvore.getSelectionModel()
				.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		arvore.addTreeSelectionListener(this);
		arvore.setCellRenderer(new RenderizarTreeNegocios());
		arvore.setShowsRootHandles(true);
		arvore.setRootVisible(true);
		arvore.setRowHeight(50);
	}
	public void criaArvContasCentCusto() {
		DAOCentroCusto daocc = new DAOCentroCusto();
		modArvCCusto = new TreeModelCentroCusto(daocc.pesquisarString(""));

		arvoreContas = new JTree(modArvCCusto);

		// Where the tree is initialized:
		arvoreContas.getSelectionModel()
				.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		arvoreContas.addTreeSelectionListener(this);
		arvoreContas.setShowsRootHandles(true);
		arvoreContas.setRootVisible(true);
		arvoreContas.setRowHeight(40);

		ImageIcon openCloseIcon = new ImageIcon(
				"C:\\SIMPRO\\img\\order\\flowblock32x32.png");
		ImageIcon leafIcon = new ImageIcon(
				"C:\\SIMPRO\\img\\order\\billing16x16.png");
		if (leafIcon != null) {

			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

			renderer.setLeafIcon(leafIcon);
			renderer.setClosedIcon(openCloseIcon);
			renderer.setOpenIcon(openCloseIcon);

			arvoreContas.setCellRenderer(renderer);
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

	// .addObject("New Node " + newNodeSuffix++);
	//
	// public DefaultMutableTreeNode addObject(Object child) {
	// DefaultMutableTreeNode parentNode = null;
	// TreePath parentPath = tree.getSelectionPath();
	//
	// if (parentPath == null) {
	// //There is no selection. Default to the root node.
	// parentNode = rootNode;
	// } else {
	// parentNode = (DefaultMutableTreeNode)
	// (parentPath.getLastPathComponent());
	// }
	//
	// return addObject(parentNode, child, true);
	// }
	//
	// public DefaultMutableTreeNode
	// addObject(DefaultMutableTreeNode parent,
	// Object child,
	// boolean shouldBeVisible) {
	// DefaultMutableTreeNode childNode =
	// new DefaultMutableTreeNode(child);
	// ...
	// treeModel.insertNodeInto(childNode, parent,
	// parent.getChildCount());
	//
	// //Make sure the user can see the lovely new node.
	// if (shouldBeVisible) {
	// tree.scrollPathToVisible(new TreePath(childNode.getPath()));
	// }
	// return childNode;
	// }

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

	public static String getNomeNo() {
		return nomeNo;
	}

	public static void setNomeNo(String nomeNo) {
		AbaCadastros.nomeNo = nomeNo;
	}

	public static JTree getArvoreNegocios() {
		return arvore;
	}

	public static void setArvoreNegocios(JTree arvoreNegocios) {
		AbaCadastros.arvore = arvoreNegocios;
	}
}
