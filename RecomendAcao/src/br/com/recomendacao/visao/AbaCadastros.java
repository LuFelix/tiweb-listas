package br.com.recomendacao.visao;

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

import br.com.recomendacao.util.ModeloArvore;

public class AbaCadastros extends JPanel implements TreeSelectionListener {
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
	private DefaultMutableTreeNode produtos;

	private JScrollPane scrArvNegocios;
	private DefaultTreeModel modArvore;
	private JSplitPane sppPrincipal;
	private DefaultMutableTreeNode grupos;
	private DefaultMutableTreeNode contas;
	private DefaultMutableTreeNode centroCusto;
	private DefaultMutableTreeNode tabelasPrecos;
	private DefaultMutableTreeNode condPagamento;
	private DefaultMutableTreeNode servicos;

	public AbaCadastros() {

		// TODO Configuração dos Labels e text fields e árvore de negócios
		lblTituloTela = new JLabel("Pessoas");
		lblTituloTela.setFont(new Font("Times new roman", Font.BOLD, 18));
		produtos = new DefaultMutableTreeNode("Produtos");

		// Subgrupos para pessoas
		clientes = new DefaultMutableTreeNode("Clientes");
		fornecedores = new DefaultMutableTreeNode("Fornecedores");
		funcionarios = new DefaultMutableTreeNode("Funcionários");
		root = new DefaultMutableTreeNode("Root");

		pessoas = new DefaultMutableTreeNode("Pessoas");
		pessoas.add(clientes);
		pessoas.add(fornecedores);
		pessoas.add(funcionarios);
		produtos = new DefaultMutableTreeNode("Produtos");
		grupos = new DefaultMutableTreeNode("Grupos");
		centroCusto = new DefaultMutableTreeNode("Centros de Custo");
		condPagamento = new DefaultMutableTreeNode("Condições de Pagamento");
		contas = new DefaultMutableTreeNode("Contas");
		servicos = new DefaultMutableTreeNode("Serviços");
		tabelasPrecos = new DefaultMutableTreeNode("Tabelas de Preços");

		root.add(pessoas);
		root.add(produtos);
		root.add(servicos);
		root.add(grupos);
		root.add(centroCusto);
		root.add(condPagamento);
		root.add(contas);
		root.add(tabelasPrecos);

		modArvore = new DefaultTreeModel(root);
		modArvore.addTreeModelListener(new ModeloArvore());
		arvore = new JTree(modArvore);

		// Where the tree is initialized:
		arvore.getSelectionModel()
				.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		arvore.addTreeSelectionListener(this);
		arvore.setCellRenderer(new RenderizarTreeNegocios());
		arvore.setShowsRootHandles(true);
		arvore.setRootVisible(false);
		arvore.setRowHeight(50);

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

			if (node.toString().equals("Pessoas")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\personfolder_32x32.png"));
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
						"C:\\SIMPRO\\img\\order\\money32x32.png"));
			}
			if (node.toString().equals("Centros de Custo")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\flowblock32x32.png"));
			}
			if (node.toString().equals("Condições de Pagamento")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\credit32x32.png"));
			}
			if (node.toString().equals("Tabelas de Preços")) {
				setIcon(new ImageIcon(
						"C:\\SIMPRO\\img\\order\\billing32x32.png"));
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

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvore
				.getLastSelectedPathComponent();
		Object nodeInfo = node.getUserObject();
		nomeNo = nodeInfo.toString();
		if (node != null) {
			if (nomeNo.equals("Pessoas")) {
				FrameInicial.getContPess().iniciar(AbaCadastros.getNomeNo());
			}
			if (nomeNo.equals("Clientes")) {
				FrameInicial.getContPess().iniciar(AbaCadastros.getNomeNo());
			}
			if (nomeNo.equals("Fornecedores")) {
				FrameInicial.getContPess().iniciar(AbaCadastros.getNomeNo());
			}
			if (nomeNo.equals("Funcionários")) {
				FrameInicial.getContPess().iniciar(AbaCadastros.getNomeNo());
			}
			if (nomeNo.equals("Produtos")) {
				FrameInicial.pesquisaProduto();
			}
			if (nomeNo.equals("Serviços")) {
				FrameInicial.getContServ().iniciar();
			}
			if (nomeNo.equals("Grupos")) {
				FrameInicial.getContGrupo().iniciar();
			}
			if (nomeNo.equals("Centros de Custo")) {
				FrameInicial.getContCentroCusto().iniciar();
			}
			if (nomeNo.equals("Condições de Pagamento")) {
				FrameInicial.getContCondPag().iniciar();
			}
			if (nomeNo.equals("Contas")) {
				FrameInicial.getContConta().iniciar();
			}
			if (nomeNo.equals("Tabelas de Preços")) {
				FrameInicial.getContTabPreco().iniciar();
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
		AbaCadastros.nomeNo = nomeNo;
	}

	public static JTree getArvoreNegocios() {
		return arvore;
	}

	public static void setArvoreNegocios(JTree arvoreNegocios) {
		AbaCadastros.arvore = arvoreNegocios;
	}
}
