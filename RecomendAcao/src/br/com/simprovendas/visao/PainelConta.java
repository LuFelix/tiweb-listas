package br.com.simprovendas.visao;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
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

import br.com.simprovendas.beans.CentroCusto;
import br.com.simprovendas.beans.Conta;
import br.com.simprovendas.controle.ControlaCentroCusto;
import br.com.simprovendas.controle.ControlaConta;
import br.com.simprovendas.controle.ControlaListaConta;
import br.com.simprovendas.controle.ControlaTabelaPreco;
import br.com.simprovendas.dao.DAOTabelaPreco;

public class PainelConta extends JPanel {

	// Labels e text fields
	private JLabel lblImagem;
	private JLabel lbl01;
	private JLabel lbl02;
	private JLabel lbl03;
	private JLabel lbl04;
	private JLabel lbl05;
	private JLabel lbl06;
	private JLabel lbl07;
	private JLabel lbl08;
	private JLabel lbl09;
	private JLabel lbl10;

	private static JTextField txtF02;
	private static JTextField txtF03;
	private static JTextField txtF04;
	private static JTextField txtF05;
	private static JTextField txtF06;
	private static JTextField txtF07;
	private static JTextField txtF08;
	private static JTextField txtF09;
	private static JTextField txtF10;

	private JScrollPane scrImagem;
	private static JScrollPane scrP01;
	private static JScrollPane scrP02;
	private JSplitPane sppImagem;
	private JSplitPane sppPrincipal;
	private JSplitPane sppSuperior;
	private JPanel pnlInferior;
	private JPanel pnlGrid;
	private JTabbedPane tabVisualiza;

	private static JComboBox<String> cmbTipoConta;
	private static JComboBox<String> cmbCentroCusto;

	private static JTable tbl01;
	private static JTable tbl02;
	private static DefaultTableModel modeloTabelaEntra;
	private static DefaultTableModel modeloTabelaSai;

	// objetos de controle

	private static ControlaListaConta controledaLista;
	private static ControlaConta contConta;
	private ControlaTabelaPreco contTabPreco;
	private static ControlaCentroCusto contCentroCusto;
	private static Conta conta;
	private List<Conta> listConta;
	private static List<CentroCusto> listCentroCusto;
	int tam;
	private DAOTabelaPreco daoTabPreco;
	private static CentroCusto centroCusto;
	// TODO Construtor

	public PainelConta(String str) {
		UIManager.put("TextField.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Label.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Button.font", new Font("Times New Roman", Font.BOLD, 12));
		// Controle
		contConta = new ControlaConta();
		contTabPreco = new ControlaTabelaPreco();
		contCentroCusto = new ControlaCentroCusto();
		// Dados
		daoTabPreco = new DAOTabelaPreco();
		setLayout(null);

		// TODO Configuração dos Labels e text fields

		lbl01 = new JLabel("Conta");
		lbl01.setFont(new Font("Times New Roman", Font.BOLD, 28));
		lbl02 = new JLabel("Seq.:");
		txtF02 = new JTextField();
		lbl03 = new JLabel("Código :");
		txtF03 = new JTextField(0);
		lbl04 = new JLabel("Nome da Conta: ");
		txtF04 = new JTextField();
		lbl05 = new JLabel("Descrição: ");
		txtF05 = new JTextField();
		lbl06 = new JLabel("Titular: ");
		txtF06 = new JTextField();
		lbl07 = new JLabel("Agência: ");
		txtF07 = new JTextField();
		lbl08 = new JLabel("Núm.Conta:");
		txtF08 = new JTextField();
		lbl09 = new JLabel("Banco:");
		txtF09 = new JTextField();
		lbl10 = new JLabel("Saldo:");
		lbl10.setFont(new Font("Times New Roman", Font.BOLD, 22));
		txtF10 = new JTextField();
		txtF10.setHorizontalAlignment(JTextField.RIGHT);
		txtF10.setFont(new Font("Times New Roman", Font.BOLD, 18));

		cmbTipoConta = new JComboBox<String>();
		cmbTipoConta.addItem("Plano de Contas");
		cmbTipoConta.addItem("Despesa");
		cmbTipoConta.addItem("Receita");
		cmbTipoConta.setToolTipText("Selecione o plano de contas para a conta");

		cmbCentroCusto = new JComboBox<String>();
		cmbCentroCusto.addItem("Centro de Custo");
		cmbCentroCusto.setToolTipText("Selecione o centro de custo para a conta.");

		tbl01 = new JTable();
		scrP01 = new JScrollPane();
		scrP01.setViewportView(tbl01);

		tbl02 = new JTable();
		scrP02 = new JScrollPane();
		scrP02.setViewportView(tbl02);

		tabVisualiza = new JTabbedPane();
		tabVisualiza.addTab("Entradas", scrP01);
		tabVisualiza.addTab("Saídas", scrP02);
		tabVisualiza.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				habilitaTabelaMovimentos();
			}
		});
		scrImagem = new JScrollPane(lblImagem);
		scrImagem.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrImagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sppImagem = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sppImagem.add(lbl01);
		sppImagem.add(scrImagem);
		sppImagem.setDividerLocation(50);
		sppImagem.setEnabled(false);
		sppImagem.setBackground(Color.WHITE);
		sppImagem.setForeground(Color.WHITE);
		sppImagem.setDividerSize(3);

		pnlGrid = new JPanel();
		pnlGrid.setBorder(BorderFactory.createEtchedBorder());
		pnlGrid.setLayout(new GridLayout(10, 2));
		pnlGrid.setBackground(Color.WHITE);

		pnlGrid.add(lbl02);
		pnlGrid.add(txtF02);
		pnlGrid.add(lbl03);
		pnlGrid.add(txtF03);
		pnlGrid.add(lbl04);
		pnlGrid.add(txtF04);
		pnlGrid.add(lbl05);
		pnlGrid.add(txtF05);
		pnlGrid.add(lbl06);
		pnlGrid.add(txtF06);
		pnlGrid.add(lbl07);
		pnlGrid.add(txtF07);
		pnlGrid.add(lbl08);
		pnlGrid.add(txtF08);
		pnlGrid.add(lbl09);
		pnlGrid.add(txtF09);
		pnlGrid.add(lbl10);
		pnlGrid.add(txtF10);

		pnlGrid.add(cmbCentroCusto);
		pnlGrid.add(cmbTipoConta);

		sppSuperior = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppSuperior.setDividerLocation(200);
		sppSuperior.setDividerSize(3);
		sppSuperior.setEnabled(false);
		sppSuperior.add(sppImagem);
		sppSuperior.add(pnlGrid);

		pnlInferior = new JPanel();
		pnlInferior.setBorder(BorderFactory.createEtchedBorder());
		pnlInferior.setLayout(new GridLayout());
		pnlInferior.setBackground(Color.WHITE);
		pnlInferior.add(tabVisualiza);

		desHabilitaEdicao();
		listConta = contConta.pesqNomeArray(str);
		tam = listConta.size();
		tam--;
		if (tam < 0) {
			FrameInicial.setPainelVisualiza(null);
			FrameInicial.getScrVisualiza().setViewportView(FrameInicial.getPainelVisualiza());
		} else {
			controledaLista = new ControlaListaConta(listConta);
			conta = controledaLista.first();
			contConta.consultaSaldo(conta);
			carregarCampos(conta);
		}
		sppPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sppPrincipal.setDividerSize(3);
		sppPrincipal.setDividerLocation(250);
		sppPrincipal.setEnabled(false);
		sppPrincipal.setBackground(Color.WHITE);
		sppPrincipal.add(sppSuperior);
		sppPrincipal.add(pnlInferior);
		setLayout(new GridLayout());
		setBackground(Color.WHITE);
		add(sppPrincipal);

	}

	public static void irParaPoicao(int posicao) {
		controledaLista.setCurrentPosition(posicao);
		conta = controledaLista.getAt(posicao);
		carregarCampos(conta);
	}

	// TODO Ler Campos.

	static void carregarCentrosCusto() {
		listCentroCusto = contCentroCusto.pesqNomeArray("");
		for (CentroCusto centroCusto : listCentroCusto) {
			cmbCentroCusto.addItem(centroCusto.getNomeCentroCusto());
		}
	}

	public static Conta lerCampos() {
		conta = new Conta();
		if (!txtF02.equals("") & !txtF02.equals(null)) {
			conta.setSeqConta(Integer.parseInt(txtF02.getText()));
		}
		if (!txtF03.getText().equals("") & !txtF03.getText().equals(null)) {
			conta.setCodiConta(txtF03.getText());
		} else {
			conta.setCodiConta(contConta.criaCodigo());
		}
		if (!txtF03.getText().equals(null) & !txtF03.getText().equals("")) {
			conta.setNomeConta(txtF03.getText());
		} else {
			erroLeitura();
			return null;
		}
		conta.setDescConta(txtF05.getText());
		if (!txtF06.getText().equals(null) & !txtF06.getText().equals("")) {
			conta.setTiular(txtF06.getText());
		} else {
			System.out.println("Erro titular");
			erroLeitura();
			return null;
		}
		conta.setAgencia(txtF06.getText());
		conta.setConta(txtF07.getText());
		conta.setBanco(txtF08.getText());
		if (!cmbTipoConta.getSelectedItem().toString().equals("Plano de Contas")) {
			conta.setTipoConta(cmbTipoConta.getSelectedItem().toString());
		} else {
			System.out.println("Erro tipo");
			erroLeitura();
			return null;
		}
		if (!cmbCentroCusto.getSelectedItem().toString().equals("Centro de Custo")) {
			centroCusto = contCentroCusto.buscaNome(cmbCentroCusto.getSelectedItem().toString());
			conta.setCentroCusto(centroCusto.getCodiCentroCusto());
		} else {
			erroLeitura();
			return null;
		}
		return conta;
	}

	// TODO Carregar campos
	public static void carregarCampos(Conta conta) {
		if (!conta.equals(null)) {
			txtF02.setText(String.valueOf(conta.getSeqConta()));
			txtF03.setText(conta.getCodiConta());
			txtF04.setText(conta.getNomeConta());
			txtF05.setText(conta.getDescConta());
			txtF05.setText(String.valueOf(conta.getAgencia()));
			txtF06.setText(conta.getTiular());
			txtF07.setText(String.valueOf(conta.getAgencia()));
			txtF08.setText(conta.getNumCartao());
			txtF09.setText(conta.getBanco());
			txtF10.setText(String.valueOf(contConta.consultaSaldo(conta)));
			cmbTipoConta.setSelectedItem(conta.getTipoConta());
			carregarCentrosCusto();
			centroCusto = contCentroCusto.buscaCodigo(conta.getCentroCusto());
			cmbCentroCusto.setSelectedItem(centroCusto.getNomeCentroCusto());
			habilitaTabelaMovimentos();
		}

	}

	private void desabilitaTabela() {
		setTabelaEntradas(null);
		setTabelaSaidas(null);
		scrP01.setViewportView(tbl01);
		scrP02.setViewportView(tbl02);

	}

	private static void erroLeitura() {
		JOptionPane.showMessageDialog(null, "Problemas: Verifique as informações preenchidas",
				"Erro ao Salvar. Campos com * são necessários", JOptionPane.ERROR_MESSAGE);
	}

	// TODO Habilitar tabela de movimentos
	public static void habilitaTabelaMovimentos() {
		tbl01 = new JTable();
		tbl02 = new JTable();
		modeloTabelaEntra = new DefaultTableModel();
		modeloTabelaEntra = (DefaultTableModel) tbl01.getModel();
		modeloTabelaSai = new DefaultTableModel();
		modeloTabelaSai = (DefaultTableModel) tbl02.getModel();
		contConta.carregarEntradas(conta);
		Object colunas[] = { "Conta", "Pedido", "Valor" };
		modeloTabelaEntra.setColumnIdentifiers(colunas);
		modeloTabelaSai.setColumnIdentifiers(colunas);
		tbl01.setModel(modeloTabelaEntra);
		tbl01.setShowHorizontalLines(true);
		tbl02.setModel(modeloTabelaSai);
		tbl02.setShowHorizontalLines(true);
		for (int i = 0; i < conta.getListEntradas().size(); i++) {
			Object linha[] = { conta.getNomeConta(), conta.getListEntradas().get(i).getCodiPedido(),
					conta.getListEntradas().get(i).getValor() };
			modeloTabelaEntra.addRow(linha);
		}
		getScrEntradas().setViewportView(tbl01);
		getScrSaidas().setViewportView(tbl02);
	}

	// TODO Desabilita Edição
	public static void desHabilitaEdicao() {

		txtF02.setEditable(false);
		txtF03.setEditable(false);
		txtF03.setEditable(false);
		txtF05.setEditable(false);
		txtF06.setEditable(false);
		txtF06.setEditable(false);
		txtF07.setEditable(false);
		txtF08.setEditable(false);

		cmbTipoConta.setEnabled(false);
		cmbCentroCusto.setEnabled(false);

	}

	// TODO Habilitar Edição
	public static void habilitaEdicao() {

		txtF03.setEditable(false);
		txtF03.setEditable(true);
		txtF05.setEditable(true);
		txtF06.setEditable(true);
		txtF06.setEditable(true);
		txtF07.setEditable(true);
		txtF08.setEditable(true);

		cmbTipoConta.setEnabled(true);
		cmbCentroCusto.setEnabled(true);

	}

	// TODO Habilita novo
	public static void habilitaNovo() {
		limparCampos();

		txtF03.setEditable(false);
		txtF03.setFocusable(false);
		txtF03.setText(contConta.criaCodigo());
		txtF02.setText("0");
		txtF03.setEditable(true);
		txtF03.grabFocus();
		txtF05.setEditable(true);
		txtF06.setEditable(true);
		txtF06.setEditable(true);
		txtF07.setEditable(true);
		txtF08.setEditable(true);

		cmbTipoConta.setEnabled(true);
		cmbCentroCusto.setEnabled(true);

	}

	// TODO Limpar campos
	public static void limparCampos() {

		txtF02.setText(null);
		txtF03.setText(null);
		txtF03.setText(null);
		txtF05.setText(null);
		txtF06.setText(null);
		txtF06.setText(null);
		txtF07.setText(null);
		txtF08.setText(null);

		cmbTipoConta.setSelectedIndex(0);
		cmbCentroCusto.setSelectedIndex(0);
	}

	public static JTable getTabelaEntradas() {
		return tbl01;
	}

	public static void setTabelaEntradas(JTable tabelaEntradas) {
		PainelConta.tbl01 = tabelaEntradas;
	}

	public static JScrollPane getScrEntradas() {
		return scrP01;
	}

	public static void setScrEntradas(JScrollPane scrEntradas) {
		PainelConta.scrP01 = scrEntradas;
	}

	public static JScrollPane getScrSaidas() {
		return scrP02;
	}

	public static void setScrSaidas(JScrollPane scrSaidas) {
		PainelConta.scrP02 = scrSaidas;
	}

	public static JTable getTabelaSaidas() {
		return tbl02;
	}

	public static void setTabelaSaidas(JTable tabelaSaidas) {
		PainelConta.tbl02 = tabelaSaidas;
	}

	public static JTextField getTxtFNomeConta() {
		return txtF03;
	}

	public static void setTxtFNomeConta(JTextField txtFNomeConta) {
		PainelConta.txtF03 = txtFNomeConta;
	}

}
