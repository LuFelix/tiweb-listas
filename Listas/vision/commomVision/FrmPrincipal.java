package commomVision;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.FontUIResource;

import controllerListas.ControlaLista;

public class FrmPrincipal extends JFrame {

	// Paineis
	JPanel pnlPrincipal;
	JPanel pnlButtons;
	JPanel pnlCol1;
	JPanel pnlCol2;
	JPanel pnlCol3;
	JPanel pnlBarraMenu1;
	JPanel pnlBarraMenu2;
	JPanel pnlBarraMenu3;
	JScrollPane scrImages;
	static JScrollPane scrLista;
	static JScrollPane scrEventos;
	static JScrollPane scrTarefas;
	static JScrollPane scrNotas;
	JTabbedPane tabPDetalhes;

	// Menus
	JMenuBar mnuBarSuperior;
	JMenuBar mnuBarInferior;
	JMenuBar mnuBarPrincipal;
	JMenu mnuPrincipal;
	JMenu mnuHelp;
	JMenu mnuExportar;
	JMenuItem mnuISobre;
	JMenuItem mnuITarefas;
	JMenuItem mnuINota;
	JMenuItem mnuIAmbos;

	// Buttons
	JButton btnAddItem;
	JButton btnRemItem;
	JButton btnAddLista;
	JButton btnRemLista;
	JButton btnEditarLista;
	JButton btnSalvaNota;

	// Objetos
	JLabel lblImage;
	JLabel lblPesquisa;
	JLabel lblStatus;
	JTextField txtFPesquisa;

	// Controle
	static ControlaLista contChkList;

	public FrmPrincipal() {
		Font font = new Font("Arial", Font.BOLD,16);
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
		

		contChkList = new ControlaLista();

		// TODO Menus
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
		mnuHelp.add(mnuISobre);
		mnuISobre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null,
						"My Reminders \nTechnology Projects - ME\n" + "\n12/2016" + "Versão 1.3");
			}
		});

		btnSalvaNota = new JButton("Salva Nota");
		btnSalvaNota.setVisible(false);
		btnSalvaNota.setEnabled(false);
		btnSalvaNota.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (contChkList.salvaNota()) {
					lblStatus.setText("     Nota armazenada...");
					contChkList.carregaItensLista();
					atualizaTelas();
				} else {
					contChkList.erroLista();
				}
			}
		});

		lblStatus = new JLabel("");

		lblPesquisa = new JLabel("Pesquisa: ");
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
		// TODO Barras
		mnuBarSuperior = new JMenuBar();
		mnuBarSuperior.setLayout(new GridLayout(0, 3));

		mnuBarPrincipal = new JMenuBar();
		mnuBarPrincipal.add(mnuPrincipal);
		mnuBarPrincipal.add(mnuHelp);

		mnuBarInferior = new JMenuBar();
		mnuBarInferior.setLayout(new GridLayout(0, 2));

		pnlBarraMenu1 = new JPanel(new GridLayout(1, 0));
		pnlBarraMenu1.add(txtFPesquisa);

		pnlBarraMenu2 = new JPanel(new GridLayout(0, 3));
		pnlBarraMenu2.add(btnSalvaNota);
		pnlBarraMenu2.add(lblStatus);

		mnuBarSuperior.add(mnuBarPrincipal);
		mnuBarSuperior.add(pnlBarraMenu1);
		mnuBarSuperior.add(pnlBarraMenu2);

		// TODO Buttons
		btnAddLista = new JButton("Criar Lista");
		btnAddLista.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				contChkList.criaLista(JOptionPane.showInputDialog("Nome lista"));
				btnAddItem.grabFocus();
				atualizaTelas();
			}
		});

		btnRemLista = new JButton("Remover Lista");
		btnRemLista.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null,
						"Remover a lista selecionada?\nNão poderá ser desfeito!") == 0) {
					if (contChkList.removeLista()) {
						atualizaTelas();
					} else {
						contChkList.erroLista();
					}
				}

			}
		});

		btnAddItem = new JButton("Adicionar Ítem");
		btnAddItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String item = JOptionPane.showInputDialog("Nome do ítem");
				if (contChkList.adicionaItem(item)) {
					contChkList.carregaItensLista();
					btnAddItem.grabFocus();
				} else {
					contChkList.erroItem();
				}
			}
		});

		btnRemItem = new JButton("Remover Ítem");
		btnRemItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int linha = contChkList.getTblItens().getSelectedRow();
				if (linha != -1) {
					contChkList.removeItem();
					contChkList.carregaItensLista();
					contChkList.getTblItens().changeSelection(linha - 1, 0, false, false);
				} else {
					contChkList.erroItem();
				}

			}
		});

		btnEditarLista = new JButton("Editar Lista");
		btnEditarLista.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String nomeLista = JOptionPane.showInputDialog("Digite o novo nome para a lista:");
				contChkList.editaLista(nomeLista);
				atualizaTelas();
			}
		});

		pnlButtons = new JPanel(new GridLayout(4, 0));
		pnlButtons.add(btnAddLista);
		pnlButtons.add(btnRemLista);
		pnlButtons.add(btnAddItem);
		pnlButtons.add(btnRemItem);

		scrEventos = new JScrollPane();
		scrLista = new JScrollPane();
		scrTarefas = new JScrollPane();
		scrNotas = new JScrollPane();

		String localImagem = "C:\\SIMPRO\\img\\listas\\lista1.jpg";
		lblImage = new JLabel(new ImageIcon(localImagem));

		scrImages = new JScrollPane(lblImage);
		scrImages.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrImages.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		pnlPrincipal = new JPanel();
		pnlPrincipal.setLayout(new GridLayout(0, 3, 0, 100));
		pnlPrincipal.setBounds(0, 100, 960, 500);

		tabPDetalhes = new JTabbedPane();
		tabPDetalhes.add("Tarefas", scrTarefas);
		tabPDetalhes.addTab("Notas", scrNotas);
		tabPDetalhes.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabPDetalhes.getSelectedIndex() == 0) {
					btnSalvaNota.setVisible(false);
					btnSalvaNota.setEnabled(false);
					lblStatus.setText("");
				} else if (tabPDetalhes.getSelectedIndex() == 1) {
					btnSalvaNota.setVisible(true);
					btnSalvaNota.setEnabled(true);
				}
			}
		});
		pnlCol1 = new JPanel(new GridLayout(2, 0));
		pnlCol1.add(scrImages);
		pnlCol1.add(pnlButtons);

		pnlCol2 = new JPanel(new GridLayout(2, 0));
		pnlCol2.add(scrLista);
		pnlCol2.add(scrEventos);

		pnlCol3 = new JPanel(new GridLayout(1, 1));
		pnlCol3.add(tabPDetalhes);

		pnlPrincipal.add(pnlCol1);
		pnlPrincipal.add(pnlCol2);
		pnlPrincipal.add(pnlCol3);

		setTitle(". . : : -- Minhas Listas -- : : . .");
		setJMenuBar(mnuBarSuperior);
		setContentPane(pnlPrincipal);

		setSize(1180, 600);
		setLocation(50, 75);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		atualizaTelas();
		setVisible(true);

	}

	public static void atualizaTelas() {
		scrLista.setViewportView(contChkList.tblListaCompleta());
		scrNotas.setViewportView(contChkList.getTextArea());
		contChkList.getTblListas().grabFocus();
	}

	public void setDefaultFont(Font defaultFont) {

		FontUIResource font = new FontUIResource(defaultFont);
		Enumeration uiManagerKeys = UIManager.getDefaults().keys();
		while (uiManagerKeys.hasMoreElements()) {
			Object key = uiManagerKeys.nextElement(), value = UIManager.get(key);

			if (null != value && value instanceof FontUIResource)
				UIManager.put(key, font);
		}
	}

	public JPanel getPnlPrincipal() {
		return pnlPrincipal;
	}

	public void setPnlPrincipal(JPanel pnlPrincipal) {
		this.pnlPrincipal = pnlPrincipal;
	}

	public JPanel getPnlFunctions() {
		return pnlButtons;
	}

	public void setPnlFunctions(JPanel pnlFunctions) {
		this.pnlButtons = pnlFunctions;
	}

	public JButton getBtnAddItem() {
		return btnAddItem;
	}

	public void setBtnAddItem(JButton btnAddItem) {
		this.btnAddItem = btnAddItem;
	}

	public JButton getBtnRemItem() {
		return btnRemItem;
	}

	public void setBtnRemItem(JButton btnRemItem) {
		this.btnRemItem = btnRemItem;
	}

	public JButton getBtnAddLista() {
		return btnAddLista;
	}

	public void setBtnAddLista(JButton btnAddLista) {
		this.btnAddLista = btnAddLista;
	}

	public JButton getBtnRemLista() {
		return btnRemLista;
	}

	public void setBtnRemLista(JButton btnRemLista) {
		this.btnRemLista = btnRemLista;
	}

	public ControlaLista getContChkList() {
		return contChkList;
	}

	public void setContChkList(ControlaLista contChkList) {
		this.contChkList = contChkList;
	}

	public JScrollPane getScrLista() {
		return scrLista;
	}

	public void setScrLista(JScrollPane scrLista) {
		this.scrLista = scrLista;
	}

	public static JScrollPane getScrTarefas() {
		return scrTarefas;
	}

	public void setScrTarefas(JScrollPane scrTarefas) {
		this.scrTarefas = scrTarefas;
	}

	public static JScrollPane getScrNotas() {
		return scrNotas;
	}

	public static void setScrNotas(JScrollPane scrNotas) {
		FrmPrincipal.scrNotas = scrNotas;
	}

	public JScrollPane getScrImages() {
		return scrImages;
	}

	public void setScrImages(JScrollPane scrImages) {
		this.scrImages = scrImages;
	}

}
