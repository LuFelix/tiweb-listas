package br.com.simprovendas.visao;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

import br.com.simprovendas.beans.Pessoa;
import br.com.simprovendas.controle.ControlaListaUsuarios;
import br.com.simprovendas.controle.ControlaUsuario;
import br.com.simprovendas.dao.DAOUsuario;

public class PainelPessoa extends JPanel {
	// Objetos de Controle
	private static Pessoa p;
	private static ControlaListaUsuarios controledaLista;
	private static ControlaUsuario contU;
	private DAOUsuario daoU;
	// Labels
	private JLabel lblTituloTela;
	private JLabel lblCodiPessoa;
	private JLabel lblNome;
	private JLabel lblEmail;
	private JLabel lblCpf;
	private JLabel lblSeqPessoa;
	private JLabel lblperfil;
	private JLabel lblDescricaoProd;
	private JLabel lblAliquotaICMSProd;
	private JLabel lblQuantProdEstoque;
	private JLabel lblPrecoProd;

	// Text Fields
	private static JTextField txtfNome;
	private static JTextField txtFSeqPessoa;
	private static JTextField txtFCodiPessoa;
	private static JTextField txtfCpf;
	private static JTextField txtfEmail;
	private static JComboBox<String> cmbTipoPessoa;
	private static JComboBox<String> cmbRelPessoa;
	private static JPanel pnlRelPessoa;
	private static JCheckBox chkCliente;
	private static JCheckBox chkFornecedor;

	// Buttons
	static JTable tabela;

	// Paineis
	JPanel painelPrincipal;
	// Variaveis
	int result[];
	int result1[];
	private List<Pessoa> listU;
	int tam;
	private JSplitPane jspPrincipal;
	private JSplitPane sppImagem;
	private JSplitPane sppSuperior;
	private JPanel painelGrid;
	private JPanel painelMovimento;
	private static JLabel lblImagem;
	private JTabbedPane tabVisualiza;
	private static JScrollPane scrImagem;
	private static JScrollPane scrDetalhes;
	private static JScrollPane scrUltPedidos;

	public PainelPessoa(String nome) {
		UIManager.put("TextField.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Label.font", new Font("Times New Roman", Font.BOLD, 12));
		UIManager.put("Button.font", new Font("Times New Roman", Font.BOLD, 12));

		contU = new ControlaUsuario();
		daoU = new DAOUsuario();

		result = new int[9];
		result1 = new int[10];
		setLayout(null);
		painelPrincipal = new JPanel();
		painelPrincipal.setBackground(Color.WHITE);
		painelPrincipal.setBorder(BorderFactory.createEtchedBorder());
		painelPrincipal.setLayout(null);
		painelPrincipal.setSize(525, 510);
		// JLabels e JTextFields
		lblTituloTela = new JLabel("Contato");
		lblTituloTela.setBounds(10, 0, 150, 40);
		lblTituloTela.setFont(new Font("Times New Roman", Font.BOLD, 28));

		lblSeqPessoa = new JLabel("Número:");
		lblSeqPessoa.setBounds(10, 60, 90, 25);
		txtFSeqPessoa = new JTextField();
		txtFSeqPessoa.setBounds(100, 60, 80, 25);

		lblCodiPessoa = new JLabel("Código:");
		lblCodiPessoa.setBounds(190, 60, 80, 25);
		txtFCodiPessoa = new JTextField();
		txtFCodiPessoa.setBounds(240, 60, 100, 25);

		pnlRelPessoa = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlRelPessoa.setBounds(360, 60, 140, 80);
		pnlRelPessoa.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true), "Relação"));
		pnlRelPessoa.setBackground(Color.WHITE);

		chkCliente = new JCheckBox("Cliente");
		chkFornecedor = new JCheckBox("Fornecedor");

		pnlRelPessoa.add(chkCliente);
		pnlRelPessoa.add(chkFornecedor);

		cmbRelPessoa = new JComboBox<String>();
		cmbRelPessoa.setBounds(360, 60, 130, 25);
		cmbRelPessoa.addItem("Relação");
		cmbRelPessoa.addItem("Clientes");
		cmbRelPessoa.addItem("Fornecedores");
		cmbRelPessoa.addItem("Ambos");

		lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 90, 90, 25);
		txtfNome = new JTextField();
		txtfNome.setBounds(100, 90, 240, 25);
		txtfNome.setCaretPosition(0);
		txtfNome.setHorizontalAlignment(JTextField.LEFT);

		lblCpf = new JLabel("CPF/CNPJ:");
		lblCpf.setBounds(10, 120, 70, 25);
		txtfCpf = new JTextField();
		txtfCpf.setBounds(100, 120, 100, 25);
		txtfCpf.setColumns(15);

		cmbTipoPessoa = new JComboBox<String>();
		cmbTipoPessoa.setBounds(210, 120, 130, 25);
		cmbTipoPessoa.addItem("Tipo de Pessoa");
		cmbTipoPessoa.addItem("Jurídica");
		cmbTipoPessoa.addItem("Física");

		lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(10, 150, 90, 25);
		txtfEmail = new JTextField();
		txtfEmail.setBounds(100, 150, 220, 25);
		txtfEmail.setHorizontalAlignment(JTextField.LEFT);
		txtfEmail.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				FrameInicial.getBtnSalvar().grabFocus();
			}

			@Override
			public void focusGained(FocusEvent arg0) {

			}
		});

		tabVisualiza = new JTabbedPane();
		tabVisualiza.addTab("Detalhes", scrDetalhes);
		tabVisualiza.add("Últimos Pedidos", scrUltPedidos);

		painelMovimento = new JPanel();
		painelMovimento.setBorder(BorderFactory.createEtchedBorder());
		painelMovimento.setLayout(new GridLayout());
		painelMovimento.setBackground(Color.WHITE);
		painelMovimento.add(tabVisualiza);

		painelGrid = new JPanel();
		painelGrid.setBorder(BorderFactory.createEtchedBorder());
		painelGrid.setLayout(new GridLayout(9, 2));
		painelGrid.setBackground(Color.WHITE);

		painelGrid.add(lblSeqPessoa);
		painelGrid.add(txtFSeqPessoa);
		painelGrid.add(lblCodiPessoa);
		painelGrid.add(txtFCodiPessoa);

		painelGrid.add(lblNome);
		painelGrid.add(txtfNome);
		painelGrid.add(lblCpf);
		painelGrid.add(txtfCpf);
		painelGrid.add(lblEmail);
		painelGrid.add(txtfEmail);
		painelGrid.add(cmbTipoPessoa);
		painelGrid.add(cmbRelPessoa);

		lblImagem = new JLabel("Image not Found");
		scrImagem = new JScrollPane(lblImagem);
		scrImagem.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrImagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

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

		listU = daoU.pesquisarNome(nome);
		tam = listU.size();
		tam--;
		if (tam >= 0) {
			controledaLista = new ControlaListaUsuarios(listU);
			p = controledaLista.first();
			carregarCampos(p);
		} else {
			FrameInicial.setPainelVisualiza(null);
			FrameInicial.getScrVisualiza().setViewportView(FrameInicial.getPainelVisualiza());

		}
		desHabilitaEdicao();
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

	}// TODO Fim do construtor Controle de Campos

	public void desHabilitaEdicao() {
		cmbRelPessoa.setEnabled(false);
		cmbTipoPessoa.setEnabled(false);
		txtFSeqPessoa.setEditable(false);
		txtFCodiPessoa.setEditable(false);
		txtfNome.setEditable(false);
		txtfCpf.setEditable(false);
		txtfEmail.setEditable(false);
	}

	// TODO Habilita Edição
	public static void habilitaEdicao() {
		cmbRelPessoa.setEnabled(true);
		cmbTipoPessoa.setEnabled(true);
		txtfNome.setEditable(true);
		txtfCpf.setEditable(true);
		txtfEmail.setEditable(true);
	}

	// TODO habilitar novo
	public static void habilitaNovo() {
		limparCampos();
		if (txtFCodiPessoa.getText().equals("") || txtFCodiPessoa.getText().equals(null)) {
			txtFCodiPessoa.setText(contU.criaCodiUsuario());
		}
		cmbRelPessoa.setEnabled(true);
		cmbRelPessoa.grabFocus();
		cmbTipoPessoa.setEnabled(true);
		txtfNome.setEditable(true);
		txtfCpf.setEditable(true);
		txtfEmail.setEditable(true);
	}

	// TODO le campos
	public static Pessoa lerCampos() {
		p = new Pessoa();
		if (txtFCodiPessoa.getText().equals("")) {
			p.setSeqUsuario(0);
		} else {
			p.setSeqUsuario(Integer.parseInt(txtFCodiPessoa.getText()));
		}
		p.setRelacao(cmbRelPessoa.getSelectedItem().toString());
		p.setTipoPessoa(cmbTipoPessoa.getSelectedItem().toString());
		p.setCodiPessoa(txtFCodiPessoa.getText());
		p.setNome(txtfNome.getText());
		p.setCpf(txtfCpf.getText());
		p.setEmail(txtfEmail.getText());
		return p;
	}

	public static void limparCampos() {
		cmbRelPessoa.setSelectedIndex(0);
		cmbTipoPessoa.setSelectedIndex(0);
		txtFCodiPessoa.setText(null);
		txtfNome.setText(null);
		txtfCpf.setText(null);
		txtfEmail.setText(null);

	}

	static void carregarCampos(Pessoa p) {
		cmbRelPessoa.setSelectedItem(p.getRelacao());
		cmbTipoPessoa.setSelectedItem(p.getTipoPessoa());
		txtFSeqPessoa.setText(String.valueOf(p.getSeqUsuario()));
		txtFCodiPessoa.setText(p.getCodiPessoa());
		txtfNome.setText(p.getNome());
		txtfCpf.setText(String.valueOf(p.getCpf()));
		txtfEmail.setText(p.getEmail());
		carregarImagem(p.getCodiPessoa());
	}

	public static void carregarImagem(String codiPessoa) {
		lblImagem = new JLabel(new ImageIcon("C:\\SIMPRO\\img\\common\\" + "javinha4" + ".jpg "));
		scrImagem.setViewportView(lblImagem);
	}

	public static JTextField getTxtfNome() {
		return txtfNome;
	}

	public static void irParaPoicao(int posicao) {
		controledaLista.setCurrentPosition(posicao);
		p = controledaLista.getAt(posicao);
		carregarCampos(p);
	}

	public static void setTxtfNome(JTextField txtfNome) {
		PainelPessoa.txtfNome = txtfNome;
	}

	// TODO Validar CPF
	private boolean validarcpf(String cpf) {
		// int soma = 0;
		// int soma1 = 0;
		// int resto = 0;
		// int resto1 = 0;
		// int dv = 0;
		// int dv1 = 0;
		// int mult[] = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
		// for (int i = 0; i < 9; i++) {
		// result[i] = mult[i] * Integer.parseInt(cpf.substring(i, i + 1));
		// soma = soma + result[i];
		// System.out.println(result[i] + "soma:" + soma);
		//
		// }
		// // Primeiro digito verificador efetuado.
		// resto = soma % 11;
		//
		// if (resto < 2) {
		// dv = 0;
		// System.out.println(dv);
		// } else {
		// dv = 11 - resto;
		// System.out.println(dv);
		// }
		// // Segundo digito verificador
		// int mult1[] = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
		// for (int i = 0; i < 10; i++) {
		// result1[i] = mult1[i] * Integer.parseInt(cpf.substring(i, i + 1));
		// soma1 = soma1 + result1[i];
		// System.out.println(result1[i] + "soma:" + soma1);
		//
		// }
		// resto1 = soma1 % 11;
		// if (resto1 < 2) {
		// dv1 = 0;
		// System.out.println(dv1);
		//
		// } else {
		// dv1 = 11 - resto1;
		// System.out.println(dv1);
		// }
		//
		// if (dv == Integer.parseInt(cpf.substring(9, 10)) & dv1 ==
		// Integer.parseInt(cpf.substring(10, 11))) {
		// return true;
		// } else {
		// JOptionPane.showMessageDialog(null, "Favor verificar CPF ", "CPF
		// Inválido!", JOptionPane.ERROR_MESSAGE);
		// return false;
		// }
		//
		return true;
	}

}
