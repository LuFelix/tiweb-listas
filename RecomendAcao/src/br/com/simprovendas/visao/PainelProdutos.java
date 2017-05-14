package br.com.simprovendas.visao;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import br.com.simprovendas.beans.Produto;
import br.com.simprovendas.controle.ControlaListaProdutos;
import br.com.simprovendas.controle.ControlaProduto;
import br.com.simprovendas.controle.ControlaTabelaPreco;
import br.com.simprovendas.dao.DAOProdutoPrepSTM;
import br.com.simprovendas.dao.DAOTabelaPreco;

public class PainelProdutos extends JPanel {

	// JFrame telaProduto;

	private JSplitPane jspPrincipal;
	private JSplitPane sppSuperior;
	private JSplitPane sppImagem;
	private JPanel painelGrid;
	private JPanel painelMovimento;
	private JLabel lblTituloTela;
	// Labels e text fields
	private JLabel lblSeq;
	private JLabel lblCodigoInterno;
	private JLabel lblCodigoFabric;
	private JLabel lblNomeProd;
	private JLabel lblDescricaoProd;
	private JLabel lblAliquotaICMSProd;
	private JLabel lblQuantProdEstoque;

	private JLabel lblPrecoAtual;
	private static JLabel lblImagem;
	private static JCheckBox chkBListaPrecos;

	private static JTextField txtFCodigoInterno;
	private static JTextField txtFCodigoFabric;
	private static JTextField txtFNomeProd;
	private static JTextField txtFDescricaoProd;
	private static JTextField txtFQuantProdEstoque;
	private static JTextField txtFAliquotaICMSProd;
	private static JTextField txtFPrecoAtual;
	private static JTextField txtFSeqProduto;
	private static JComboBox<String> cmbTabPreco;

	private JButton btnProximo;
	private JButton btnAnterior;
	private static JButton btnEditarPreco;
	private static JButton btnNovo;
	private static JButton btnEditar;
	private static JButton btnCancelar;

	// Tabela de preços do produto

	private JTabbedPane tabVisualiza;
	private static JTable tabelaPrecos;
	private static JTable tabelaMovEstoque;
	private static DefaultTableModel modeloTabela;
	private static JScrollPane scrPrecos;
	private static JScrollPane scrEstoque;
	private static JScrollPane scrImagem;

	// objetos de controle

	private static ControlaListaProdutos controledaLista;
	private static ControlaProduto contProd;
	private ControlaTabelaPreco contTabPreco;
	private static Produto prod;
	private DAOProdutoPrepSTM daoP;
	private List<Produto> listProd;
	int tam;
	private DAOTabelaPreco daoTabPreco;

	// TODO Construtor
	public PainelProdutos(String nome) {

		UIManager.put("TextField.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Label.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Button.font", new Font("Times New Roman", Font.BOLD, 12));
		// Controle

		contProd = new ControlaProduto();
		contTabPreco = new ControlaTabelaPreco();
		// Dados
		daoP = new DAOProdutoPrepSTM();
		daoTabPreco = new DAOTabelaPreco();
		// telaProduto.setContentPane(painelPrincipal);

		// TODO Configuração dos Labels e text fields

		lblTituloTela = new JLabel("   Produto");
		lblTituloTela.setBounds(10, 0, 150, 40);
		lblTituloTela.setFont(new Font("Times New Roman", Font.BOLD, 32));

		lblSeq = new JLabel("   Sequencia:");
		txtFSeqProduto = new JTextField();

		lblCodigoInterno = new JLabel("   Código Interno:");
		txtFCodigoInterno = new JTextField(0);

		lblCodigoFabric = new JLabel("   Código Fábrica:");
		txtFCodigoFabric = new JTextField(0);

		lblNomeProd = new JLabel("   Produto: ");
		txtFNomeProd = new JTextField();

		lblAliquotaICMSProd = new JLabel("   Alíquota ICMS: ");
		txtFAliquotaICMSProd = new JTextField();

		lblDescricaoProd = new JLabel("   Descrição: ");
		txtFDescricaoProd = new JTextField();

		lblQuantProdEstoque = new JLabel("   Estoque: ");
		txtFQuantProdEstoque = new JTextField();

		lblPrecoAtual = new JLabel("   Preço Atual:");
		txtFPrecoAtual = new JTextField();

		btnEditarPreco = new JButton("Alterar Preço");

		cmbTabPreco = new JComboBox<String>();
		cmbTabPreco.addItem("Tabela de Preços");
		for (int i = 0; i < daoTabPreco.pesquisaString("").size(); i++) {
			cmbTabPreco.addItem(daoTabPreco.pesquisaString("").get(i).getDescTabela());
		}
		chkBListaPrecos = new JCheckBox("Exibir");
		chkBListaPrecos.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (chkBListaPrecos.isSelected()) {
					habilitaTabelaPrecos();
				} else {
					desabilitaTabela();
				}
			}
		});

		// Tabela de Visualiza

		tabelaPrecos = new JTable();
		tabelaMovEstoque = new JTable();

		scrPrecos = new JScrollPane();
		scrPrecos.setViewportView(tabelaPrecos);
		tabelaPrecos.getParent().setBackground(Color.WHITE);

		lblImagem = new JLabel("Image not Found");

		scrImagem = new JScrollPane(lblImagem);
		scrImagem.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrImagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tabVisualiza = new JTabbedPane();
		tabVisualiza.addTab("Histórico de Preços", scrPrecos);
		tabVisualiza.add("Estoque", scrEstoque);

		// Ações
		btnEditarPreco.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alteraPreco();
			}
		});

		// TODO Painel principal
		painelGrid = new JPanel();
		painelGrid.setBorder(BorderFactory.createEtchedBorder());
		painelGrid.setLayout(new GridLayout(9, 2));
		painelGrid.setBackground(Color.WHITE);
		painelGrid.add(lblSeq);
		painelGrid.add(txtFSeqProduto);
		painelGrid.add(lblCodigoInterno);
		painelGrid.add(txtFCodigoInterno);
		painelGrid.add(lblCodigoFabric);
		painelGrid.add(txtFCodigoFabric);
		painelGrid.add(lblNomeProd);
		painelGrid.add(txtFNomeProd);
		painelGrid.add(lblDescricaoProd);
		painelGrid.add(txtFDescricaoProd);
		painelGrid.add(lblAliquotaICMSProd);
		painelGrid.add(txtFAliquotaICMSProd);
		painelGrid.add(lblQuantProdEstoque);
		painelGrid.add(txtFQuantProdEstoque);
		painelGrid.add(lblPrecoAtual);
		painelGrid.add(txtFPrecoAtual);
		painelGrid.add(cmbTabPreco);
		painelGrid.add(btnEditarPreco);

		sppImagem = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sppImagem.add(lblTituloTela);
		sppImagem.add(scrImagem);
		sppImagem.setDividerLocation(50);
		sppImagem.setEnabled(false);
		sppImagem.setBackground(Color.WHITE);
		sppImagem.setForeground(Color.WHITE);
		sppImagem.setDividerSize(3);

		sppSuperior = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppSuperior.setDividerLocation(200);
		sppSuperior.setDividerSize(3);
		sppSuperior.setEnabled(false);
		sppSuperior.add(sppImagem);
		sppSuperior.add(painelGrid);

		painelMovimento = new JPanel();
		painelMovimento.setBorder(BorderFactory.createEtchedBorder());
		painelMovimento.setLayout(new GridLayout());
		painelMovimento.setBackground(Color.WHITE);
		painelMovimento.add(tabVisualiza);

		desHabilitaEdicao();
		listProd = contProd.pesqNomeArray(nome);
		tam = listProd.size();
		tam--;
		if (tam < 0) {
			FrameInicial.setPainelVisualiza(null);
			FrameInicial.getScrVisualiza().setViewportView(FrameInicial.getPainelVisualiza());
		} else {
			controledaLista = new ControlaListaProdutos(listProd);
			prod = controledaLista.first();
			prod.setEstoqueAtual(contProd.consultaEstoque(prod));
			carregarCampos(prod);
		}
		jspPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		jspPrincipal.setDividerSize(3);
		jspPrincipal.setDividerLocation(250);
		jspPrincipal.setEnabled(false);
		jspPrincipal.setBackground(Color.WHITE);
		jspPrincipal.add(sppSuperior);
		jspPrincipal.add(painelMovimento);
		setLayout(new GridLayout());
		setBackground(Color.WHITE);
		add(jspPrincipal);
	}

	// TODO Fim contrutor inicio Habilita/Desab./Carrega/Le/Limpa Campos

	public static void irParaPoicao(int posicao) {
		controledaLista.setCurrentPosition(posicao);
		prod = controledaLista.getAt(posicao);
		prod.setEstoqueAtual(contProd.consultaEstoque(prod));
		carregarCampos(prod);
	}

	private void alteraPreco() {
		prod = lerCampos();
		String data = String.valueOf(new Timestamp(System.currentTimeMillis())).substring(0, 10);
		float valor = Float.parseFloat(JOptionPane.showInputDialog("Informe o novo valor:"));
		try {
			contProd.novoPreco(cmbTabPreco.getItemAt(0), Date.valueOf(data), prod.getCodi_prod_1(), valor);
			txtFPrecoAtual.setText(String.valueOf(valor));
			habilitaTabelaPrecos();
			contProd.funcaoSobrescrever();
			FrameInicial.getBtnSalvar().doClick();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Problemas: ", " Erro ao Cadastrar: " + e1.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void desabilitaTabela() {
		setTabelaPrecos(null);
		getScrPrecos().setViewportView(tabelaPrecos);

	}

	// TODO Habilitar histórico de preços
	public static JTable habilitaTabelaPrecos() {
		tabelaPrecos = new JTable();
		modeloTabela = new DefaultTableModel();
		modeloTabela = (DefaultTableModel) tabelaPrecos.getModel();
		contProd.carregarCotacoes(prod);
		Object colunas[] = { "Nome", "Preço Unitário", "Data" };
		modeloTabela.setColumnIdentifiers(colunas);
		tabelaPrecos.setShowGrid(true);

		tabelaPrecos.setModel(modeloTabela);
		for (int i = 0; i < prod.getListCotacaoProduto().size(); i++) {
			Object linha[] = { prod.getNome_prod(), prod.getListCotacaoProduto().get(i).getValor(),
					prod.getListCotacaoProduto().get(i).getDataHoraMarcacao() };
			modeloTabela.addRow(linha);
		}

		scrPrecos.setViewportView(tabelaPrecos);
		return tabelaPrecos;
	}

	// TODO Ler Campos.

	public static Produto lerCampos() {
		prod = new Produto();
		if (txtFCodigoInterno.getText().equals("") || txtFCodigoInterno.getText().equals(null)) {
			prod.setCodi_prod_1(contProd.criaCodiProd());
		} else {
			prod.setCodi_prod_1(txtFCodigoInterno.getText());
		}
		if (!txtFNomeProd.getText().equals(null) & !txtFNomeProd.getText().equals("")) {
			prod.setNome_prod(txtFNomeProd.getText());
		} else {
			JOptionPane.showMessageDialog(null, "Problemas: Verifique as informações preenchidas",
					"Erro ao Salvar. Campos com * são necessários", JOptionPane.ERROR_MESSAGE);
		}
		prod.setDesc_prod(txtFDescricaoProd.getText());
		if (!txtFAliquotaICMSProd.getText().equals(null) & !txtFAliquotaICMSProd.getText().equals("")) {
			prod.setAliq_prod(txtFAliquotaICMSProd.getText());
		} else {
			JOptionPane.showMessageDialog(null, "Problemas: Verifique as informações preenchidas",
					"Erro ao Salvar. Campos com * são necessários", JOptionPane.ERROR_MESSAGE);
		}
		prod.setAliq_prod(txtFAliquotaICMSProd.getText());
		if (txtFPrecoAtual.getText().equals("")) {
			prod.setPrec_prod_1(0);
		} else {
			prod.setPrec_prod_1(Float.parseFloat(txtFPrecoAtual.getText()));
		}
		return prod;
	}

	public static void carregarImagem(String codiProd) {
		lblImagem = new JLabel(new ImageIcon("C:\\SIMPRO\\Salutar\\prods\\" + codiProd + ".jpg "));
		scrImagem.setViewportView(lblImagem);
	}

	// TODO Carregar campos
	public static void carregarCampos(Produto prod) {
		if (!prod.equals(null)) {
			chkBListaPrecos.setSelected(false);
			txtFSeqProduto.setText(String.valueOf(prod.getSeq_produto()));
			txtFCodigoInterno.setText(String.valueOf(prod.getCodi_prod_1()));
			txtFNomeProd.setText(prod.getNome_prod());
			txtFDescricaoProd.setText(prod.getDesc_prod());
			txtFAliquotaICMSProd.setText(prod.getAliq_prod());
			txtFPrecoAtual.setText(String.valueOf(prod.getPrec_prod_1()));
			txtFQuantProdEstoque.setText(String.valueOf(prod.getEstoqueAtual()));
			habilitaTabelaPrecos();
			carregarImagem(prod.getCodi_prod_1());

		}

	}

	// TODO Habilitar Edição
	public static void habilitaEdicao() {
		txtFCodigoInterno.setEditable(false);
		txtFNomeProd.setEditable(true);
		txtFDescricaoProd.setEditable(true);
		txtFQuantProdEstoque.setEditable(true);
		txtFAliquotaICMSProd.setEditable(true);
		txtFPrecoAtual.setEditable(true);

		btnEditarPreco.setEnabled(true);
		cmbTabPreco.setEnabled(true);
	}

	// TODO Habilita novo
	public static void habilitaNovo() {
		limparCampos();
		txtFSeqProduto.setEditable(false);
		txtFCodigoInterno.setEditable(false);
		txtFCodigoInterno.setText(contProd.criaCodiProd());
		txtFCodigoFabric.setEditable(true);
		txtFCodigoFabric.grabFocus();
		txtFNomeProd.setEditable(true);
		txtFDescricaoProd.setEditable(true);
		txtFQuantProdEstoque.setEditable(true);
		txtFAliquotaICMSProd.setEditable(true);
		// txtFPrecoAtual.setEditable(true);

		btnEditarPreco.setEnabled(true);
		cmbTabPreco.setEnabled(true);
	}

	// TODO Desabilita edição
	public static void desHabilitaEdicao() {
		txtFSeqProduto.setEditable(false);
		txtFCodigoInterno.setEditable(false);
		txtFNomeProd.setEditable(false);
		txtFDescricaoProd.setEditable(false);
		txtFQuantProdEstoque.setEditable(false);
		txtFAliquotaICMSProd.setEditable(false);

		btnEditarPreco.setEnabled(false);
		cmbTabPreco.setEnabled(false);
		chkBListaPrecos.setSelected(false);

	}

	// TODO Limpar campos
	public static void limparCampos() {
		txtFSeqProduto.setText(null);
		txtFCodigoInterno.setText(null);
		txtFNomeProd.setText(null);
		txtFDescricaoProd.setText(null);
		txtFQuantProdEstoque.setText(null);
		txtFAliquotaICMSProd.setText(null);
		txtFPrecoAtual.setText(null);
		chkBListaPrecos.setSelected(false);

	}

	public static JButton getBtnNovo() {
		return btnNovo;
	}

	public static void setBtnNovo(JButton btnNovo) {
		PainelProdutos.btnNovo = btnNovo;
	}

	public static JButton getBtnEditar() {
		return btnEditar;
	}

	public static void setBtnEditar(JButton btnEditar) {
		PainelProdutos.btnEditar = btnEditar;
	}

	public static JTextField getTxtFNomeProd() {
		return txtFNomeProd;
	}

	public static void setTxtFNomeProd(JTextField txtFNomeProd) {
		PainelProdutos.txtFNomeProd = txtFNomeProd;
	}

	public static JButton getBtnCancelar() {
		return btnCancelar;
	}

	public static void setBtnCancelar(JButton btnCancelar) {
		PainelProdutos.btnCancelar = btnCancelar;
	}

	public static JComboBox<String> getCmbTabPreco() {
		return cmbTabPreco;
	}

	public static void setCmbTabPreco(JComboBox<String> cmbTabPreco) {
		PainelProdutos.cmbTabPreco = cmbTabPreco;
	}

	public JTable getTabelaPrecos() {
		return tabelaPrecos;
	}

	public void setTabelaPrecos(JTable tabelaPrecos) {
		this.tabelaPrecos = tabelaPrecos;
	}

	public JScrollPane getScrPrecos() {
		return scrPrecos;
	}

	public void setScrPrecos(JScrollPane scrPrecos) {
		this.scrPrecos = scrPrecos;
	}

}
