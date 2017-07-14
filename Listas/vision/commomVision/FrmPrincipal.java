package commomVision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.FontUIResource;

import Listas.PnlButtons;
import Listas.PnlTarefasNotas;
import Listas.PnlTreeRoot;
import beansListas.Grupo;
import controllerListas.ControlaLista;

public class FrmPrincipal extends JFrame {

	// Paineis
	JSplitPane sppPrincipal;// HorizontalSplit
	JSplitPane sppSuperior;
	JSplitPane sppInferior;
	static JSplitPane sppVisualiza;

	JPanel pnlPrincipal;
	JPanel pnlNavegador;
	JPanel pnlBarraMenu1;
	JPanel pnlBarraMenu2;
	JPanel pnlBarraMenu3;
	JScrollPane scrImages;
	static JScrollPane scrNavegador;
	static JScrollPane scrEventos;

	JTabbedPane tabPDetalhes;

	// Menus
	JMenuBar mnuBarSuperior;
	JMenuBar mnuBarInferior;
	JMenuBar mnuBarPrincipal;
	JMenu mnuPrincipal;
	JMenu mnuHelp;
	JMenu mnuExportar;
	JMenuItem mnuISobre;
	JMenuItem mnuIManual;
	JMenuItem mnuITarefas;
	JMenuItem mnuINota;
	JMenuItem mnuIAmbos;

	// Buttons

	// Objetos
	JLabel lblImage;

	JTextField txtFPesquisa;
	// Controle
	public static ControlaLista contList;

	public FrmPrincipal() {
		Font font = new Font("Arial", Font.BOLD, 16);
		// setDefaultFont(font);
		UIManager.put("Button.font", font);
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}

		contList = new ControlaLista();

		// TODO Menus
		txtFPesquisa = new JTextField();
		txtFPesquisa.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				txtFPesquisa.setText("Pesquisa");

			}

			@Override
			public void focusGained(FocusEvent e) {
				txtFPesquisa.setText("");

			}
		});

		scrNavegador = new JScrollPane(new PnlTreeRoot());
		scrEventos = new JScrollPane();

		String localImagem = "C:\\SIMPRO\\img\\listas\\Order64x64.png";
		lblImage = new JLabel(new ImageIcon(localImagem));
		lblImage.setBackground(Color.WHITE);

		scrImages = new JScrollPane(lblImage);
		scrImages.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrImages.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		mnuPrincipal = new JMenu("Principal");
		mnuExportar = new JMenu("Exportar");
		mnuITarefas = new JMenuItem("Tarefas");
		mnuINota = new JMenuItem("Nota");
		mnuIAmbos = new JMenuItem("Ambos");
		mnuExportar.add(mnuITarefas);
		mnuExportar.add(mnuINota);
		mnuExportar.add(mnuIAmbos);
		mnuPrincipal.add(mnuExportar);

		// Menu Help
		mnuHelp = new JMenu("Help");
		mnuISobre = new JMenuItem("Sobre");
		mnuIManual = new JMenuItem("Manual");
		mnuHelp.add(mnuISobre);
		mnuHelp.add(mnuIManual);
		mnuISobre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null,
						"My Reminders \nTechnology Projects - ME\n"
								+ "\n12/2016 --- " + "Versão 1.3" + "\n06/2017"
								+ " --- Versão 2.1");
			}
		});
		// TODO Barras
		mnuBarSuperior = new JMenuBar();
		mnuBarSuperior.setLayout(new GridLayout(2, 2));

		mnuBarPrincipal = new JMenuBar();
		mnuBarPrincipal.add(mnuPrincipal);
		mnuBarPrincipal.add(mnuHelp);

		mnuBarInferior = new JMenuBar();
		mnuBarInferior.setLayout(new GridLayout(0, 2));

		pnlBarraMenu1 = new PnlButtons(contList, 0, 4);

		pnlBarraMenu2 = new JPanel(new BorderLayout());
		pnlBarraMenu2.add(txtFPesquisa);

		mnuBarSuperior.add(mnuBarPrincipal);
		mnuBarSuperior.add(pnlBarraMenu2);
		mnuBarSuperior.add(pnlBarraMenu1);

		pnlNavegador = new JPanel(new BorderLayout());
		pnlNavegador.setBackground(Color.WHITE);

		pnlNavegador.add(scrImages, BorderLayout.PAGE_START);
		pnlNavegador.add(scrNavegador, BorderLayout.CENTER);

		sppVisualiza = new PnlTarefasNotas(contList, new Grupo());
		sppSuperior = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppSuperior.add(pnlNavegador);
		sppSuperior.add(sppVisualiza);

		sppInferior = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sppInferior.add(new PnlButtons(contList, 2, 2));
		sppInferior.add(new PnlButtons(contList, 0, 4));

		sppPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sppPrincipal.add(sppSuperior);
		// sppPrincipal.add(sppInferior);
		// sppPrincipal.setDividerLocation(500);

		setTitle(". . : : -- Minhas Listas -- : : . .");
		setJMenuBar(mnuBarSuperior);
		setContentPane(sppPrincipal);

		setSize(1280, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	public static void atualizaArvore() {
		scrNavegador.setViewportView(new PnlTreeRoot());
	}

	public static void limpaTelas() {
		sppVisualiza = null;
	}

	public void setDefaultFont(Font defaultFont) {

		FontUIResource font = new FontUIResource(defaultFont);
		Enumeration uiManagerKeys = UIManager.getDefaults().keys();
		while (uiManagerKeys.hasMoreElements()) {
			Object key = uiManagerKeys.nextElement(),
					value = UIManager.get(key);

			if (null != value && value instanceof FontUIResource)
				UIManager.put(key, font);
		}
	}

	public JScrollPane getScrImages() {
		return scrImages;
	}

	public void setScrImages(JScrollPane scrImages) {
		this.scrImages = scrImages;
	}

	public static JScrollPane getScrEventos() {
		return scrEventos;
	}

	public static void setScrEventos(JScrollPane scrEventos) {
		FrmPrincipal.scrEventos = scrEventos;
	}

	public JScrollPane getScrNavegador() {
		return scrNavegador;
	}

	public void setScrNavegador(JScrollPane scrNavegador) {
		this.scrNavegador = scrNavegador;
	}

}
