package br.com.simprovendas.visao;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import br.com.simprovendas.beans.CentroCusto;
import br.com.simprovendas.beans.Produto;
import br.com.simprovendas.controle.ControlaConta;
import br.com.simprovendas.controle.ControlaListaProdutos;
import br.com.simprovendas.controle.ControlaTabelaPreco;
import br.com.simprovendas.dao.DAOCentroCusto;

public class PainelStatus extends JPanel {

	// JFrame telaProduto;
	private JTabbedPane principal;
	JPanel painelGeral;
	JPanel painelDespesas;
	JPanel painelReceitas;
	private JLabel lblTituloTela;
	// Labels e text fields
	private JLabel contas;
	private JLabel lblTotalGeral;
	private JLabel lblRealizado;
	private JLabel lblRealizar;
	private JLabel lblRealizadoPorc;
	private JLabel lblRealizarPorc;

	private static JCheckBox chkBListaPrecos;

	private static JTextField txtFTotalGeral;
	private static JTextField txtFRealizar;
	private static JTextField txtFRealizarPorc;
	private static JTextField txtFRealizado;
	private static JTextField txtFRealizadoPorc;

	private static JComboBox<String> cmbCentroCustoDesp;
	private static JComboBox<String> cmbCentroCustoRec;
	private static JComboBox<String> cmbAnoDesp;
	private static JComboBox<String> cmbAnoRec;

	private static JButton btnEditarPreco;
	private JRadioButton jrbEditarSim;
	private JRadioButton jrbEditarNao;
	private ButtonGroup grpRadio;
	private ActionListener acaoEditar;

	private JTabbedPane tabPnlDupPagar;
	private JTabbedPane tabPnlDupReceber;
	private static JTable tblContas;
	private static JTable tblCentroCusto;
	private static DefaultTableModel modeloTabela;
	private static JScrollPane scrContas;
	private static JScrollPane scrEstoque;

	// objetos de controle

	private static ControlaListaProdutos controledaLista;
	private static ControlaConta contConta;
	private ControlaTabelaPreco contTabPreco;
	private static Produto prod;
	private DAOCentroCusto daoCentCusto;

	private static JComboBox<String> cmbData;

	// TODO Construtor
	public PainelStatus(String nome) {

		UIManager.put("TextField.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Label.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Button.font", new Font("Times New Roman", Font.BOLD, 12));
		// Controle

		contConta = new ControlaConta();
		contTabPreco = new ControlaTabelaPreco();
		// Dados
		daoCentCusto = new DAOCentroCusto();
		// telaProduto.setContentPane(painelPrincipal);

		// TODO Configuração dos Labels e text fields

		lblTituloTela = new JLabel("Status");
		lblTituloTela.setBounds(10, 0, 250, 40);
		lblTituloTela.setFont(new Font("Times New Roman", Font.BOLD, 28));

		cmbData = new JComboBox<String>();
		cmbData.setBounds(10, 60, 90, 25);
		cmbData.addItem("..: Mês :..");
		cmbData.addItem("Janeiro");
		cmbData.addItem("Fevereiro");
		cmbData.addItem("Março");
		cmbData.addItem("Abril");
		cmbData.addItem("Maio");
		cmbData.addItem("Junho");
		cmbData.addItem("Julho");
		cmbData.addItem("Agosto");
		cmbData.addItem("Setembro");
		cmbData.addItem("Outubro");
		cmbData.addItem("Novembro");
		cmbData.addItem("Dezembro");

		contas = new JLabel("Contas:");
		contas.setBounds(10, 90, 100, 25);

		lblTotalGeral = new JLabel("Total Geral: ");
		lblTotalGeral.setBounds(120, 60, 100, 25);
		txtFTotalGeral = new JTextField();
		txtFTotalGeral.setBounds(200, 60, 130, 45);

		tblContas = new JTable();
		scrContas = new JScrollPane();
		scrContas.setBounds(10, 120, 320, 187);
		scrContas.setBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.CYAN, Color.BLUE, Color.BLUE, Color.CYAN));
		// scrContas.setBorder(BorderFactory.createEtchedBorder(Color.CYAN,
		// Color.BLUE));
		tblContas = contConta.tblResumoContasMes();
		scrContas.setViewportView(tblContas);

		lblRealizado = new JLabel("Realizado:");
		lblRealizado.setBounds(10, 320, 80, 25);
		txtFRealizado = new JTextField();
		txtFRealizado.setBounds(110, 320, 100, 25);

		txtFRealizadoPorc = new JTextField();
		txtFRealizadoPorc.setBounds(220, 320, 60, 25);
		lblRealizadoPorc = new JLabel("%");
		lblRealizadoPorc.setBounds(290, 320, 30, 25);
		lblRealizadoPorc.setFont(new Font("Times new Roman", Font.BOLD, 16));

		lblRealizar = new JLabel("A Realizar:");
		lblRealizar.setBounds(10, 350, 80, 25);
		txtFRealizar = new JTextField();
		txtFRealizar.setBounds(110, 350, 100, 25);

		txtFRealizarPorc = new JTextField();
		txtFRealizarPorc.setBounds(220, 350, 60, 25);
		lblRealizarPorc = new JLabel("%");
		lblRealizarPorc.setBounds(290, 350, 30, 25);
		lblRealizarPorc.setFont(new Font("Times new Roman", Font.BOLD, 16));

		tblCentroCusto = new JTable();
		cmbCentroCustoDesp = new JComboBox<String>();
		cmbCentroCustoDesp.setBounds(10, 115, 170, 25);
		cmbCentroCustoDesp.addItem("Centro de Custo");
		List<CentroCusto> listCentCustoDesp = daoCentCusto.pesquisarString("");
		for (int i = 0; i < listCentCustoDesp.size(); i++) {
			cmbCentroCustoDesp.addItem(listCentCustoDesp.get(i).getNomeCentroCusto());
		}
		cmbAnoDesp = new JComboBox<String>();
		cmbAnoDesp.setBounds(220, 115, 170, 25);
		cmbAnoDesp.addItem("Ano");

		cmbCentroCustoRec = new JComboBox<String>();
		cmbCentroCustoRec.setBounds(10, 115, 170, 25);
		cmbCentroCustoRec.addItem("Centro de Custo");
		List<CentroCusto> listCentCustoRec = daoCentCusto.pesquisarString("");
		for (int i = 0; i < listCentCustoRec.size(); i++) {
			cmbCentroCustoRec.addItem(listCentCustoRec.get(i).getNomeCentroCusto());
		}
		chkBListaPrecos = new JCheckBox("Exibir");
		chkBListaPrecos.setBounds(10, 115, 80, 25);
		chkBListaPrecos.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (chkBListaPrecos.isSelected()) {
					habilitaTabelaContas();
				} else {
					desabilitaTabela();
				}

			}
		});

		// Posicionamento e ações dos Botões

		// JRadio Buttons
		jrbEditarSim = new JRadioButton("Sim");
		jrbEditarSim.setBounds(95, 560, 50, 35);
		jrbEditarNao = new JRadioButton("Não");
		jrbEditarNao.setBounds(155, 560, 50, 35);
		grpRadio = new ButtonGroup();
		grpRadio.add(jrbEditarSim);
		grpRadio.add(jrbEditarNao);

		// Ação Radio Buttons
		jrbEditarNao.setSelected(true);

		// Tabela de detalhes

		tabPnlDupPagar = new JTabbedPane();
		tabPnlDupPagar.addTab("Vencidas", null);
		tabPnlDupPagar.addTab("Hoje", null);
		tabPnlDupPagar.addTab("Prox. Semana", null);
		tabPnlDupPagar.addTab("Prox. 30 dias", null);
		tabPnlDupPagar.addTab("Prox. 60 dias", null);
		tabPnlDupPagar.addTab("Prox. 90 dias", null);
		tabPnlDupPagar.setBounds(0, 140, 525, 380);

		tabPnlDupReceber = new JTabbedPane();
		tabPnlDupReceber.addTab("Vencidas", null);
		tabPnlDupReceber.addTab("Hoje", null);
		tabPnlDupReceber.addTab("Prox. Semana", null);
		tabPnlDupReceber.addTab("Prox. 30 dias", null);
		tabPnlDupReceber.addTab("Prox. 60 dias", null);
		tabPnlDupReceber.addTab("Prox. 90 dias", null);
		tabPnlDupReceber.setBounds(0, 140, 525, 380);
		// TODO Painel principal
		setLayout(null);
		painelGeral = new JPanel();
		painelGeral.setBorder(BorderFactory.createEtchedBorder());
		painelGeral.setLayout(null);
		painelGeral.setSize(525, 510);
		painelGeral.add(lblTituloTela);
		painelGeral.add(cmbData);
		painelGeral.add(contas);
		painelGeral.add(lblTotalGeral);
		painelGeral.add(txtFTotalGeral);
		painelGeral.add(scrContas);
		painelGeral.add(lblRealizado);
		painelGeral.add(txtFRealizado);
		painelGeral.add(txtFRealizadoPorc);
		painelGeral.add(lblRealizadoPorc);
		painelGeral.add(lblRealizar);
		painelGeral.add(txtFRealizar);
		painelGeral.add(txtFRealizarPorc);
		painelGeral.add(lblRealizarPorc);
		painelGeral.setBackground(Color.WHITE);

		painelDespesas = new JPanel();
		painelDespesas.setBorder(BorderFactory.createEtchedBorder());
		painelDespesas.setLayout(null);
		painelDespesas.setSize(525, 510);
		painelDespesas.setBackground(Color.WHITE);
		painelDespesas.add(cmbCentroCustoDesp);
		painelDespesas.add(tabPnlDupPagar);

		painelReceitas = new JPanel();
		painelReceitas.setBorder(BorderFactory.createEtchedBorder());
		painelReceitas.setLayout(null);
		painelReceitas.setSize(525, 510);
		painelReceitas.setBackground(Color.WHITE);
		painelReceitas.add(cmbCentroCustoRec);
		painelReceitas.add(tabPnlDupReceber);

		principal = new JTabbedPane(JTabbedPane.TOP);
		principal.setBackground(Color.WHITE);
		principal.setSize(525, 510);

		principal.addTab("Resumo", painelGeral);
		principal.addTab("Despesas .:: C. Pagar", painelDespesas);
		principal.addTab("Receitas .:: C. Receber", painelReceitas);
		atualizaPaineis();
		setLayout(null);
		setBackground(Color.WHITE);
		add(principal);
	}

	private void atualizaPaineis() {
		scrContas.setViewportView(tblContas);
	}

	// TODO Fim contrutor inicio Habilita/Desab./Carrega/Le/Limpa Campos

	public static void irParaMes(int posicao) {

	}

	private void desabilitaTabela() {
		setTabelaPrecos(null);
		getScrPrecos().setViewportView(tblContas);
	}

	// TODO Habilitar tabela de contas
	public static JTable habilitaTabelaContas() {
		return tblCentroCusto;

	}

	// TODO Ler Campos.
	public static Produto lerCampos() {
		prod = new Produto();
		if (txtFTotalGeral.equals("") || txtFTotalGeral.equals(null)) {
			prod.setSeq_produto(Integer.parseInt(txtFTotalGeral.getText()));
		}
		return prod;
	}

	// TODO Carregar campos
	public static void carregarCampos(Produto prod) {
		if (!prod.equals(null)) {
			chkBListaPrecos.setSelected(false);
			txtFTotalGeral.setText(String.valueOf(prod.getSeq_produto()));
		}
	}

	// TODO Habilitar Edição
	public static void habilitaEdicao() {
		cmbCentroCustoDesp.setEnabled(true);
	}

	// TODO Habilita novo
	public static void habilitaNovo() {
		limparCampos();
		cmbCentroCustoDesp.setEnabled(true);
	}

	// TODO Desabilita edição
	public static void desHabilitaEdicao() {
		txtFTotalGeral.setEditable(false);

		cmbCentroCustoDesp.setEnabled(false);
		chkBListaPrecos.setSelected(false);
	}

	// TODO Limpar campos
	public static void limparCampos() {
		txtFTotalGeral.setText(null);
		chkBListaPrecos.setSelected(false);
	}

	public JTable getTabelaPrecos() {
		return tblContas;
	}

	public void setTabelaPrecos(JTable tabelaPrecos) {
		this.tblContas = tabelaPrecos;
	}

	public JScrollPane getScrPrecos() {
		return scrContas;
	}

	public void setScrPrecos(JScrollPane scrPrecos) {
		this.scrContas = scrPrecos;
	}

}
