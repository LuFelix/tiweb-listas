package br.com.simprovendas.visao;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import br.com.simprovendas.beans.CondPagamento;
import br.com.simprovendas.beans.Lancamento;
import br.com.simprovendas.beans.Pedido;
import br.com.simprovendas.beans.Produto;
import br.com.simprovendas.beans.TabelaPreco;
import br.com.simprovendas.beans.Pessoa;
import br.com.simprovendas.controle.ControlaLancamento;
import br.com.simprovendas.controle.ControlaListaPedidos;
import br.com.simprovendas.controle.ControlaPedido;
import br.com.simprovendas.controle.ControlaProduto;
import br.com.simprovendas.dao.DAOCondPagamento;
import br.com.simprovendas.dao.DAOPedidoPrepSTM;
import br.com.simprovendas.dao.DAOProdutosEstoque;
import br.com.simprovendas.dao.DAOTabelaPreco;

public class PainelPedidos extends JPanel {

	JPanel painelPrincipal;
	private JLabel lblTituloTela;
	private JLabel lblLogoEmpresa;
	private JLabel lblCodigopedi;
	private JLabel lblUsuario;
	private JLabel lblCliente;
	private JLabel lblQuantItens;
	private JLabel lblData;
	private JLabel lblPermiteEditar;
	private static JLabel lblTipoPedido;
	private static JTextField txtFCodigoPedi;
	private static JTextField txtFCodiCliente;
	private static JTextField txtFNomeCliente;
	private static JTextField txtfUsuario;
	private static JTextField txtFQuantItens;
	private static JScrollPane scrProdutos;
	private static JScrollPane scrObsPedido;
	private static JScrollPane scrPagamentos;
	private static JTabbedPane pnlTabAnexos;
	private static JComboBox<String> cmbTipoPedido;
	private static JComboBox<String> cmbTabPreco;

	private static JTextField txtFPrecopedi;
	private JRadioButton jrbEditarSim;
	private JRadioButton jrbEditarNao;
	private ButtonGroup grpRadio;

	// TODO objetos de controle

	private static ControlaListaPedidos controledaLista;
	private static ControlaPedido contPedi;
	private ControlaProduto contProd;
	private static Pedido pedi;
	private static DAOPedidoPrepSTM daoPedi;
	private static DAOTabelaPreco daoTabPreco;
	private static DAOCondPagamento daoCondPagamento;

	private List<Pedido> listPedi;
	int tam;

	private JLabel lblPrecoPedido;
	private static float totalPedido;
	private static int quantProdutos;
	private static int numTab;

	// Painel de Visualiza do pedido;
	private static List<TabelaPreco> listTabPreco;
	private static JTextArea txtAreaObsPedido;
	private static JTable tabelaItensPedido;
	private static DefaultTableModel modeloTabela;
	private static JTable tblPagamentos;
	private static DefaultTableModel modeloTabelaPagamentos;
	private static Lancamento lanc;
	private static ControlaLancamento contLanc;
	private static DAOProdutosEstoque daoProdEstoque;

	public PainelPedidos(String nome) {
		UIManager.put("TextField.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Label.font", new Font("Times New Roman", Font.PLAIN, 12));
		UIManager.put("Button.font", new Font("Times New Roman", Font.BOLD, 12));
		// Controle
		contPedi = new ControlaPedido();
		controledaLista = new ControlaListaPedidos(listPedi);
		daoTabPreco = new DAOTabelaPreco();
		daoCondPagamento = new DAOCondPagamento();
		listTabPreco = new ArrayList<TabelaPreco>(daoTabPreco.pesquisaString(""));
		contLanc = new ControlaLancamento();
		daoProdEstoque = new DAOProdutosEstoque();
		// Dados
		setLayout(null);
		painelPrincipal = new JPanel();
		painelPrincipal.setBorder(BorderFactory.createEtchedBorder());
		painelPrincipal.setLayout(null);
		painelPrincipal.setSize(525, 510);
		painelPrincipal.setBackground(Color.WHITE);

		txtAreaObsPedido = new JTextArea();
		scrObsPedido = new JScrollPane();
		scrObsPedido.setSize(420, 320);
		scrObsPedido.setViewportView(txtAreaObsPedido);

		scrProdutos = new JScrollPane();
		scrProdutos.setSize(420, 320);

		scrPagamentos = new JScrollPane();
		scrPagamentos.setSize(420, 320);

		pnlTabAnexos = new JTabbedPane();
		pnlTabAnexos.setBounds(0, 240, 525, 330);
		pnlTabAnexos.addTab("Produtos", scrProdutos);
		pnlTabAnexos.addTab("Pagamentos", scrPagamentos);
		pnlTabAnexos.addTab("Observações", scrObsPedido);

		// TODO Labels e Text fields

		lblTituloTela = new JLabel("Pedido");
		lblTituloTela.setBounds(10, 0, 120, 40);
		lblTituloTela.setFont(new Font("Times New Roman", Font.BOLD, 28));
		setLblTipoPedido(new JLabel());
		getLblTipoPedido().setBounds(70, 30, 100, 30);
		getLblTipoPedido().setFont(new Font("Times New Roman", Font.ITALIC, 24));

		lblData = new JLabel(String.valueOf(Calendar.getInstance().getTime()));
		lblData.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		lblData.setBounds(160, 32, 130, 30);

		lblCodigopedi = new JLabel("Código:");
		lblCodigopedi.setBounds(5, 60, 90, 25);
		txtFCodigoPedi = new JTextField();
		txtFCodigoPedi.setBounds(75, 60, 90, 25);

		cmbTabPreco = new JComboBox<String>();
		cmbTabPreco.setBounds(170, 60, 190, 25);
		cmbTabPreco.addItem("Tabela de Preços");
		for (int i = 0; i < listTabPreco.size(); i++) {
			cmbTabPreco.addItem(listTabPreco.get(i).getNomeTabela());
		}
		cmbTabPreco.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// JOptionPane.showMessageDialog(null, "Alterando para " +
				// cmbTabPreco.getSelectedItem());
			}
		});

		lblLogoEmpresa = new JLabel(new ImageIcon("C:\\SIMPRO\\Salutar\\logoSalutar100x80.jpg"));
		lblLogoEmpresa.setBounds(370, 20, 150, 80);

		lblCliente = new JLabel("Cliente: ");
		lblCliente.setBounds(5, 90, 120, 25);
		txtFCodiCliente = new JTextField();
		txtFCodiCliente.setBounds(75, 90, 90, 25);
		txtFNomeCliente = new JTextField();
		txtFNomeCliente.setBounds(170, 90, 190, 25);
		txtFCodiCliente.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// TODO txtfCliente ao perder foco
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO txtfCliente ao receber foco
				FrameInicial.pesquisaUsuarioAdicionarAOPedido();
			}
		});

		lblQuantItens = new JLabel("Qtd. ítens: ");
		lblQuantItens.setBounds(5, 180, 120, 25);

		txtFQuantItens = new JTextField();
		txtFQuantItens.setBounds(75, 180, 80, 25);
		txtFQuantItens.setHorizontalAlignment(JTextField.RIGHT);
		txtFQuantItens.setFont(new Font("TimesNew Roman", Font.BOLD, 16));
		txtFQuantItens.setEditable(false);

		lblPrecoPedido = new JLabel("TOTAL: ");
		lblPrecoPedido.setBounds(160, 180, 80, 25);
		lblPrecoPedido.setFont(new Font("Times New Roman", Font.BOLD, 18));
		txtFPrecopedi = new JTextField();
		txtFPrecopedi.setBounds(240, 180, 200, 40);
		txtFPrecopedi.setFont(new Font("Times New Roman", Font.BOLD, 28));
		txtFPrecopedi.setForeground(Color.RED);
		txtFPrecopedi.setHorizontalAlignment(JTextField.RIGHT);
		txtFPrecopedi.setEditable(false);

		lblPermiteEditar = new JLabel("Permite Editar ");
		lblPermiteEditar.setBounds(105, 590, 100, 25);

		// TODO Posicionamento Botões

		// JRadio Buttons+++++++++++++++++
		jrbEditarSim = new JRadioButton("Sim");
		jrbEditarSim.setBounds(95, 560, 50, 35);
		jrbEditarNao = new JRadioButton("Não");
		jrbEditarNao.setBounds(155, 560, 50, 35);
		grpRadio = new ButtonGroup();
		grpRadio.add(jrbEditarSim);
		grpRadio.add(jrbEditarNao);

		// Ação Radio Buttons++++++++++++++++
		jrbEditarNao.setSelected(true);

		// TODO Configuração do Painel principal
		painelPrincipal.add(lblTituloTela);
		painelPrincipal.add(lblTipoPedido);
		painelPrincipal.add(lblLogoEmpresa);
		painelPrincipal.add(lblCodigopedi);
		painelPrincipal.add(txtFCodigoPedi);
		painelPrincipal.add(txtFNomeCliente);
		painelPrincipal.add(cmbTabPreco);
		painelPrincipal.add(lblCliente);
		painelPrincipal.add(txtFCodiCliente);
		painelPrincipal.add(lblQuantItens);
		painelPrincipal.add(txtFQuantItens);
		painelPrincipal.add(lblData);
		painelPrincipal.add(lblPrecoPedido);
		painelPrincipal.add(txtFPrecopedi);
		painelPrincipal.add(pnlTabAnexos);

		desHabilitaEdicao();
		listPedi = contPedi.listaPedidosTipo(AbaNegocios.getNomeNo());
		tam = listPedi.size();
		tam--;
		if (tam < 0) {
			FrameInicial.setPainelVisualiza(null);
			FrameInicial.setTabela(null);
			FrameInicial.atualizaTela();
		} else {
			controledaLista = new ControlaListaPedidos(listPedi);
			pedi = controledaLista.first();
			contPedi.carregarProdutosPedido(pedi);
			contPedi.carregarPagamentosPedido(pedi);
			carregarCampos(pedi);
			desbilitaTabela();
		}
		add(painelPrincipal);
	}

	public static void desbilitaTabela() {
		getTabelaItensPedido().setEnabled(false);

	}

	// TODO Vai para uma posição específica
	public static void irParaPoicao(int posicao) {
		controledaLista.setCurrentPosition(posicao);
		pedi = controledaLista.getAt(posicao);
		contPedi.carregarProdutosPedido(pedi);
		contPedi.carregarPagamentosPedido(pedi);
		carregarCampos(pedi);
		desbilitaTabela();
	}

	private boolean listavazia() {
		if (tam == -1) {
			return true;
		}
		return false;
	}

	// TODO Editar lista de ítens do pedido
	public static void adicionaItem(Produto prod) {
		// Tratar se o usuário cancelar ou digitar letras ou 0
		System.out.println("PainelPedido.adicionarItem");
		prod.setQuantMovimento(Integer.parseInt(JOptionPane.showInputDialog(null, "QUANTIDADE")));
		System.out.println("Trabalhando no pedido: " + pedi.getCodiPedi());
		for (Produto produto : pedi.getItensProduto()) {
			System.out.println("Produto na lista: " + produto.getNome_prod() + " :::: " + produto.getQuantMovimento());
			if (produto.equals(prod)) {
				System.out.println("Produto igual a " + produto.getNome_prod());
			}
			System.out.println("Produto não encontrado ::::: " + prod.getNome_prod());

		}
		if (!pedi.getItensProduto().contains(prod)) {
			pedi.getItensProduto().add(prod);
			contPedi.adicionaItemProduto(pedi, prod);
			daoProdEstoque.novoMovProdEstoque("Padrao", null, prod.getCodi_prod_1(), pedi.getQuantItens(),
					pedi.getCodiPedi(), "Sai");
		} else {
			contPedi.alterarQuantProd(pedi, prod);
		}
		atualizaTabelaProdutos(pedi);
	}

	public static void adicionaPagamento(CondPagamento condPag) {
		float valor = Float.parseFloat(JOptionPane.showInputDialog("Valor:"));
		pedi = leCampos();
		lanc = new Lancamento();
		lanc.setCodiCondPag(condPag.getCodiCondPag());
		lanc.setValor(valor);
		if (valor <= 0) {
			contPedi.removerPagamento(pedi, lanc);
		} else {
			contLanc.adicionaLancamentoPedido(pedi, lanc);
		}
		atualizaTabelaPagamentos(pedi);
	}

	// TODO Atualizar a tabela de itens do pedido
	public static JTable atualizaTabelaProdutos(final Pedido pedi) {
		System.out.println("PainelPedidos.atualizaTabelaProdutos");
		quantProdutos = 0;
		totalPedido = 0;
		modeloTabela = new DefaultTableModel();
		tabelaItensPedido = new JTable(modeloTabela);
		Object colunas[] = { "Código", "Nome", "Quantidade", "Preço Unit.", "Total ítem" };
		modeloTabela.setColumnIdentifiers(colunas);
		getTabelaItensPedido().setRowSelectionAllowed(false);
		getTabelaItensPedido().setCellSelectionEnabled(false);
		getTabelaItensPedido().setColumnSelectionAllowed(false);
		tabelaItensPedido.addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int posicao = tabelaItensPedido.getSelectedRow();
				adicionaItem(pedi.getItensProduto().get(posicao));
				carregarCampos(pedi);

			}
		});
		contPedi.carregarProdutosPedido(pedi);
		for (int i = 0; i < pedi.getItensProduto().size(); i++) {
			Object linha[] = { pedi.getItensProduto().get(i).getCodi_prod_1(),
					pedi.getItensProduto().get(i).getNome_prod(), pedi.getItensProduto().get(i).getQuantMovimento(),
					pedi.getItensProduto().get(i).getPrec_prod_1(), pedi.getItensProduto().get(i).getPrec_prod_1()
							* pedi.getItensProduto().get(i).getQuantMovimento() };
			modeloTabela.addRow(linha);
			quantProdutos = quantProdutos + pedi.getItensProduto().get(i).getQuantMovimento();
			totalPedido = totalPedido + (pedi.getItensProduto().get(i).getPrec_prod_1()
					* pedi.getItensProduto().get(i).getQuantMovimento());
		}
		tabelaItensPedido.setShowGrid(true);
		scrProdutos.setViewportView(tabelaItensPedido);
		txtFQuantItens.setText(String.valueOf(quantProdutos));
		txtFPrecopedi.setText(String.valueOf(totalPedido));
		return tabelaItensPedido;
	}

	// TODO Atualiza tabela de pagamentos
	public static JTable atualizaTabelaPagamentos(final Pedido pedi) {
		System.out.println("PainelPedidos.atualizaTabelaPagamentos");
		modeloTabelaPagamentos = new DefaultTableModel();
		setTblPagamentos(new JTable(modeloTabelaPagamentos));
		Object colunas[] = { "Cod. Pedido", "Cond. Pagamento", "Valor", "Data do Lançamento" };
		modeloTabelaPagamentos.setColumnIdentifiers(colunas);
		getTblPagamentos().setRowSelectionAllowed(false);
		getTblPagamentos().setCellSelectionEnabled(false);
		getTblPagamentos().setColumnSelectionAllowed(false);
		getTblPagamentos().addMouseListener(new MouseListener() {

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
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int posicao = tblPagamentos.getSelectedRow();
				adicionaPagamento(
						daoCondPagamento.pesquisaCondPagCodigo(pedi.getLancPedido().get(posicao).getCodiCondPag()));
				carregarCampos(pedi);
			}
		});
		contPedi.carregarPagamentosPedido(pedi);
		for (int i = 0; i < pedi.getLancPedido().size(); i++) {
			Object linha[] = { pedi.getCodiPedi(),
					daoCondPagamento.pesquisaNomeCodigo(pedi.getLancPedido().get(i).getCodiCondPag()),
					pedi.getLancPedido().get(i).getValor(), pedi.getLancPedido().get(i).getDataHoraLancamento() };
			modeloTabelaPagamentos.addRow(linha);
		}
		getTblPagamentos().setShowGrid(true);
		scrPagamentos.setViewportView(tblPagamentos);
		return getTblPagamentos();
	}

	// TODO Ler os campos e retornar um pedido
	public static Pedido leCampos() {
		System.out.println("PainelPedidos.lerCampos");
		pedi = new Pedido();
		pedi.setTipoPedido(AbaNegocios.getNomeNo());
		pedi.setCodiPedi(txtFCodigoPedi.getText());
		pedi.setCodiTabPreco(daoTabPreco.pesquisaCodigoNome(cmbTabPreco.getSelectedItem().toString()));
		if (!txtFCodiCliente.getText().equals("") & !txtFCodiCliente.equals(null)) {
			pedi.setxNome(txtFCodiCliente.getText());
		}
		if (!txtFQuantItens.getText().equals(null) & !txtFQuantItens.getText().equals("")) {
			pedi.setQuantItens(Integer.parseInt(txtFQuantItens.getText()));
		}
		if (!txtFPrecopedi.getText().equals(null) & !txtFPrecopedi.getText().equals("")) {
			pedi.setTotalPedi(Float.parseFloat(txtFPrecopedi.getText()));
		}
		pedi.setObsPedi1(txtAreaObsPedido.getText());
		return pedi;
	}

	// TODO Habilita novo
	public static void habilitaNovo() {
		pedi = new Pedido();
		limparCampos();
		atualizaTabelaProdutos(pedi);
		atualizaTabelaPagamentos(pedi);
		pedi.setCodiPedi(criaCodiPedi());
		pedi.setTipoPedido(AbaNegocios.getNomeNo());
		lblTipoPedido.setText(pedi.getTipoPedido());
		try {
			daoPedi.reservaCodigo(pedi.getCodiPedi());
		} catch (SQLException e) {
			// TODO Erro ao conectar banco.
			JOptionPane.showMessageDialog(null, "Erro ao reservar código para o pedido/n" + e.getMessage());
			e.printStackTrace();
		}
		cmbTabPreco.setEnabled(true);
		txtFCodigoPedi.setText(pedi.getCodiPedi());
		txtFCodigoPedi.setEditable(false);
		txtFCodiCliente.setFocusable(true);
		txtFCodiCliente.setEditable(false);
		txtFNomeCliente.setEditable(false);
		// txtfUsuario.setEditable(true);
		txtAreaObsPedido.setEditable(true);
		txtAreaObsPedido.setFocusable(true);
		getTabelaItensPedido().setEnabled(true);
		pnlTabAnexos.setEnabled(true);
		pnlTabAnexos.setFocusable(true);
		pnlTabAnexos.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				numTab = pnlTabAnexos.getSelectedIndex();
				if (numTab == 0) {
					FrameInicial.pesquisaProdutoAdicaoItem();
				} else if (numTab == 1) {
					FrameInicial.pesquisaCondPagamentoRealizaPedido();
				} else if (numTab == 2) {

				}
			}
		});

	}

	// TODO Habilita edição
	public static void habilitaEdicao() {
		cmbTabPreco.setEnabled(true);
		txtFCodigoPedi.setEditable(false);
		txtFCodiCliente.setFocusable(true);
		txtFCodiCliente.setEditable(true);
		txtFPrecopedi.setEditable(true);
		txtAreaObsPedido.setEditable(true);
		txtAreaObsPedido.setFocusable(true);
		getTabelaItensPedido().setEnabled(true);
		pnlTabAnexos.setFocusable(true);
		pnlTabAnexos.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				numTab = pnlTabAnexos.getSelectedIndex();
				if (numTab == 0) {
					FrameInicial.pesquisaProdutoAdicaoItem();
				} else if (numTab == 1) {
					FrameInicial.pesquisaCondPagamentoRealizaPedido();
				} else if (numTab == 2) {

				}

			}
		});
	}

	public int abaSelecionnada() {
		numTab = pnlTabAnexos.getSelectedIndex();
		return numTab;
	}

	// TODO desabilita edição
	public static void desHabilitaEdicao() {
		cmbTabPreco.setEnabled(false);
		txtFCodigoPedi.setEditable(false);
		txtFCodiCliente.setFocusable(false);
		txtFNomeCliente.setEditable(false);
		txtFQuantItens.setEditable(false);
		txtFCodiCliente.setEditable(false);
		txtFCodiCliente.setFocusable(false);
		txtFPrecopedi.setEditable(false);
		txtFPrecopedi.setText(null);
		txtAreaObsPedido.setEditable(false);
		txtAreaObsPedido.setFocusable(false);
		pnlTabAnexos.setFocusable(false);
		for (ChangeListener cl : pnlTabAnexos.getChangeListeners()) {
			pnlTabAnexos.removeChangeListener(cl);
		}
	}

	// TODO Limpa Campos
	public static void limparCampos() {
		pedi = new Pedido();
		cmbTabPreco.setSelectedIndex(0);
		txtFCodigoPedi.setText(null);
		txtFNomeCliente.setText(null);
		// txtfUsuario.setText(null);
		txtFQuantItens.setText(null);
		txtFCodiCliente.setText(null);
		txtFPrecopedi.setText(null);
		txtFQuantItens.setText(null);
		txtAreaObsPedido.setText(null);
		totalPedido = 0;
		quantProdutos = 0;
		limparTabelas();
	}

	static void limparTabelas() {
		setTabelaItensPedido(null);
		tblPagamentos = null;
	}

	// TODO Carregar a tela com um pedido
	public static void carregarCampos(Pedido pedi) {
		if (!pedi.equals(null)) {
			System.out.println("PainelPedidos.carregarCampos: ");
			txtFCodigoPedi.setText(pedi.getCodiPedi());
			txtFQuantItens.setText(String.valueOf(pedi.getQuantItens()));
			txtFPrecopedi.setText(String.valueOf(pedi.getTotalPedi()));
			txtFNomeCliente.setText(pedi.getxNome());
			lblTipoPedido.setText(pedi.getTipoPedido());
			if (!(pedi.getCodiTabPreco() == null)) {
				cmbTabPreco.setSelectedItem(daoTabPreco.pesquisaNomeCodigo(pedi.getCodiTabPreco()));
			} else {
				cmbTabPreco.setSelectedIndex(0);
			}
			txtAreaObsPedido.setText(pedi.getObsPedi1());

			System.out.println("Conteudo de pedido tabela de preço:  " + pedi.getCodiTabPreco());
			atualizaTabelaProdutos(pedi);
			atualizaTabelaPagamentos(pedi);
		}
	}

	// TODO Adicionar um cliente
	public static void adicionaUsuario(Pessoa usua) {
		txtFCodiCliente.setText(usua.getNome());
	}

	// TODO Fechamento do pedido
	public static void fechamentoPedido() {
		System.out.println("Fechando Pedido");
		// somar os Ã­tens da tabela e aplicar o desconto
		// chamar a condiÃ§Ã£o de pagamento cartÃ£o ou mudar o status do pedido
		// para
		// pendente de pagamento e passar para o caixa caso vÃ¡ emitir cupom ou
		// outro comprovante.
		// o caixa emite o comprovante, muda o status para pendente de entrega
		// e o cliente dirige-se ao estoque para aretirada do produto
	}

	// TODO Setar o codigo do pedido
	public static String criaCodiPedi() {
		daoPedi = new DAOPedidoPrepSTM();
		Calendar c = Calendar.getInstance();
		daoPedi.consultaUltimo();
		String codiPedi = String.valueOf(daoPedi.consultaUltimo()) + String.valueOf(c.get(Calendar.YEAR))
				+ String.valueOf(c.get(Calendar.MONTH)) + String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		return codiPedi;
	}

	public static JScrollPane getScrPedido() {
		return scrProdutos;
	}

	public static void setScrPedido(JScrollPane scrPedido) {
		PainelPedidos.scrProdutos = scrPedido;
	}

	public static JTable getTabelaItensPedido() {
		return tabelaItensPedido;
	}

	public static void setTabelaItensPedido(JTable tabelaItensPedido) {
		PainelPedidos.tabelaItensPedido = tabelaItensPedido;
	}

	public JLabel getLblQuantItens() {
		return lblQuantItens;
	}

	public void setLblQuantItens(JLabel lblQuantItens) {
		this.lblQuantItens = lblQuantItens;
	}

	public static JTextField getTxtfCliente() {
		return txtFCodiCliente;
	}

	public static void setTxtfCliente(JTextField txtfCliente) {
		PainelPedidos.txtFCodiCliente = txtfCliente;
	}

	public static JTabbedPane getPnlTabAnexos() {
		return pnlTabAnexos;
	}

	public static void setPnlTabAnexos(JTabbedPane pnlTabAnexos) {
		PainelPedidos.pnlTabAnexos = pnlTabAnexos;
	}

	public static JTable getTblPagamentos() {
		return tblPagamentos;
	}

	public static void setTblPagamentos(JTable tblPagamentos) {
		PainelPedidos.tblPagamentos = tblPagamentos;
	}

	public JLabel getLblTipoPedido() {
		return lblTipoPedido;
	}

	public void setLblTipoPedido(JLabel lblTipoPedido) {
		this.lblTipoPedido = lblTipoPedido;
	}
}
