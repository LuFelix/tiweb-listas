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

import br.com.recomendacao.util.ModeloArvore;

public class AbaNegocios extends JPanel implements TreeSelectionListener {
	JPanel painelPrincipal;
	private JLabel lblTituloTela;
	// Labels e text fields

	// Arvore do Sistema
	private static JTree arvoreNegocios;
	private static JTree arvoreStatus;
	private static String nomeNo;
	private DefaultMutableTreeNode negocios;
	private DefaultMutableTreeNode financas;
	private DefaultMutableTreeNode contas;
	private DefaultMutableTreeNode centroCusto;
	private DefaultMutableTreeNode lancamentos;
	private DefaultMutableTreeNode anotacoes;
	private DefaultMutableTreeNode posicaoFinanceira;
	private DefaultMutableTreeNode pedidos;
	private DefaultMutableTreeNode venda;
	private DefaultMutableTreeNode clientes;
	private DefaultMutableTreeNode fornecedores;
	private DefaultMutableTreeNode funcionarios;
	private DefaultMutableTreeNode compra;
	private DefaultMutableTreeNode ordServicos;
	private DefaultMutableTreeNode pessoas;
	private DefaultMutableTreeNode produtos;
	private DefaultMutableTreeNode cadastros;
	private DefaultMutableTreeNode tabelasPrecos;
	private DefaultMutableTreeNode condPagamento;
	private DefaultMutableTreeNode servicos;
	private DefaultMutableTreeNode grupo;

	private JScrollPane scrArvNegocios;
	private DefaultTreeModel modArvoreNegocios;
	private JSplitPane sppPrincipal;

	public AbaNegocios() {

		// TODO Configuração dos Labels e text fields e árvore de negócios
		lblTituloTela = new JLabel("Negócios");
		lblTituloTela.setFont(new Font("Times new roman", Font.BOLD, 18));
		anotacoes = new DefaultMutableTreeNode("Anotações");
		pedidos = new DefaultMutableTreeNode("Pedidos");
		venda = new DefaultMutableTreeNode("Venda");
		compra = new DefaultMutableTreeNode("Compra");
		clientes = new DefaultMutableTreeNode("Clientes");
		fornecedores = new DefaultMutableTreeNode("Fornecedores");
		funcionarios = new DefaultMutableTreeNode("Funcionários");
		pedidos.add(compra);
		pedidos.add(venda);
		ordServicos = new DefaultMutableTreeNode("Ordem dos Serviços");
		pessoas = new DefaultMutableTreeNode("Pessoas");
		pessoas.add(clientes);
		pessoas.add(fornecedores);
		pessoas.add(funcionarios);

		produtos = new DefaultMutableTreeNode("Produtos");
		posicaoFinanceira = new DefaultMutableTreeNode("Status Financeiro");

		cadastros = new DefaultMutableTreeNode("Cadastros");
		centroCusto = new DefaultMutableTreeNode("Centros de Custo");
		condPagamento = new DefaultMutableTreeNode("Cond. Pagamento");
		contas = new DefaultMutableTreeNode("Contas");
		grupo = new DefaultMutableTreeNode("Grupos");
		servicos = new DefaultMutableTreeNode("Serviços");
		tabelasPrecos = new DefaultMutableTreeNode("Tabelas de Preços");

		cadastros.add(centroCusto);
		cadastros.add(condPagamento);
		cadastros.add(contas);
		cadastros.add(grupo);
		cadastros.add(servicos);
		cadastros.add(tabelasPrecos);

		negocios = new DefaultMutableTreeNode("Simpro");
		negocios.add(pedidos);
		negocios.add(ordServicos);
		//negocios.add(pessoas);
		negocios.add(produtos);
		negocios.add(posicaoFinanceira);
		negocios.add(cadastros);

		modArvoreNegocios = new DefaultTreeModel(negocios);
		modArvoreNegocios.addTreeModelListener(new ModeloArvore());
		arvoreNegocios = new JTree(modArvoreNegocios);

		// Where the tree is initialized:
		arvoreNegocios.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		arvoreNegocios.addTreeSelectionListener(this);
		arvoreNegocios.setCellRenderer(new RenderizarTreeNegocios());
		arvoreNegocios.setShowsRootHandles(true);
		arvoreNegocios.setRootVisible(false);
		arvoreNegocios.setRowHeight(35);

		// TODO Posicionamento e ações botões
		scrArvNegocios = new JScrollPane(arvoreNegocios);
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
			if (node.toString().equals("Pedidos")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\order.png"));
			}
			if (node.toString().equals("Produtos")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\store.png"));
			}
			if (node.toString().equals("Pessoas")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\personfolder_32x32.png"));
			}
			if (node.toString().equals("Status Financeiro")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\money_32x32.png"));
			}
			if (node.toString().equals("Cadastros")) {
				setIcon(new ImageIcon("C:\\SIMPRO\\img\\order\\cadastros.png"));
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

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreNegocios.getLastSelectedPathComponent();
		Object nodeInfo = node.getUserObject();
		nomeNo = nodeInfo.toString();
		if (node != null) {
			if (node.isLeaf() & node.isNodeAncestor(pedidos)) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContPedi().iniciar(nomeNo);
				}
			}
			if (node.isLeaf() & nomeNo.equals("Ordem dos Serviços")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContServ().iniciar();
				}
			}
			if (node.isLeaf() & nomeNo.equals("Produtos")) {
				if (node.getAllowsChildren()) {
					FrameInicial.pesquisaProduto();
				}
			}
			if (node.isLeaf() & nomeNo.equals("Cond. Pagamento")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContCondPag().iniciar();
				}
			}
			if (node.isLeaf() & nomeNo.equals("Serviços")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContServ().iniciar();
				}
			}
			if (node.isLeaf() & nomeNo.equals("Contas")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContConta().iniciar();
				}
			}
			if (node.isLeaf() & nomeNo.equals("Status Financeiro")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContPosiFin().iniciar();
				}
			}
			if (node.isLeaf() & nomeNo.equals("Tabelas de Preços")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContTabPreco().iniciar();
				}
			}
			if (node.isLeaf() & nomeNo.equals("Grupos")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContGrupo().iniciar();
				}
			}
			if (node.isLeaf() & nomeNo.equals("Centros de Custo")) {
				if (node.getAllowsChildren()) {
					FrameInicial.getContCentroCusto().iniciar();
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
		AbaNegocios.nomeNo = nomeNo;
	}

	public static JTree getArvoreNegocios() {
		return arvoreNegocios;
	}

	public static void setArvoreNegocios(JTree arvoreNegocios) {
		AbaNegocios.arvoreNegocios = arvoreNegocios;
	}
}
