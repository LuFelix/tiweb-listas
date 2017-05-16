package br.com.recomendacao.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.recomendacao.beans.GrupoSubgrupo;
import br.com.recomendacao.beans.Pessoa;
import br.com.recomendacao.dao.DAOGrupoSubgrupo;
import br.com.recomendacao.dao.DAOPessoaPG;
import br.com.recomendacao.tableModels.TableModelPessoa;
import br.com.simprovendas.visao.AbaPessoas;
import br.com.simprovendas.visao.FrameInicial;
import br.com.simprovendas.visao.FrameInicial.ControlaBotoes;
import br.com.simprovendas.visao.PainelPedidos;
import br.com.simprovendas.visao.PainelPessoa;

public class ControlaUsuario {

	private List<Pessoa> arrayPessoa;
	private List<GrupoSubgrupo> listGrupo;
	static DAOPessoaPG daoP;
	static DAOGrupoSubgrupo daoG;
	static JTable tabela;
	static TableModelPessoa tblMdPessoa;
	private JComboBox<String> cmbGrupos;
	static Pessoa p;

	public ControlaUsuario() {
		daoP = new DAOPessoaPG();
		daoG = new DAOGrupoSubgrupo();
		listGrupo = daoG.pesquisarString("");
	}

	public JTable tblPessoas(String str) {
		tblMdPessoa = new TableModelPessoa(daoP.pesquisarString(str));
		tabela = new JTable(tblMdPessoa);
		tabela.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getExtendedKeyCode() == 40
						|| e.getExtendedKeyCode() == 38) {
					p = tblMdPessoa.getPessoa(tabela.getSelectedRow());
					carregaDetalhes(p);

				} else if (e.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 40 || e.getKeyCode() == 38) {
					p = tblMdPessoa.getPessoa(tabela.getSelectedRow());
					carregaDetalhes(p);
				}

			}
		});
		tabela.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				p = tblMdPessoa.getPessoa(tabela.getSelectedRow());
				carregaDetalhes(p);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				p = tblMdPessoa.getPessoa(tabela.getSelectedRow());
				carregaDetalhes(p);
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				p = tblMdPessoa.getPessoa(tabela.getSelectedRow());
				carregaDetalhes(p);
			}
		});

		tabela.setShowGrid(true);
		return tabela;
	}

	public List<Pessoa> pesquisar() {
		arrayPessoa = new ArrayList<Pessoa>();
		arrayPessoa = daoP.listarTodos();
		for (int i = 0; i < arrayPessoa.size(); i++) {
			System.out.println(arrayPessoa.get(i).getCpf() + "Nome"
					+ arrayPessoa.get(i).getNome());
		}
		return arrayPessoa;

	}

	public Pessoa pesquisar(String nome) {
		p = new Pessoa();
		p = daoP.pesquisar(nome);
		return p;

	}

	public void posicionarTabela(int linha) {
		System.out.println("Ir para a linha: " + linha);
		FrameInicial.getTabela().changeSelection(linha, 0, false, false);

	}

	// TODO Tabela Ligada ao painel de Contatos/ Pessoas
	public static JTable pesqNomeTabela(String nome) {
		tabela = new JTable();
		ArrayList<String> colunas = new ArrayList<>();
		DefaultTableModel modelotabela = new DefaultTableModel();
		modelotabela = (DefaultTableModel) tabela.getModel();
		tabela.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					int posicao = tabela.getSelectedRow();
					// PainelPessoa.irParaPoicao(posicao);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					// PainelPessoa.irParaPoicao(posicao);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					// PainelPessoa.irParaPoicao(posicao);
					funcaoSobrescrever();
					FrameInicial.getTabela().changeSelection(--posicao, 0,
							false, false);
					PainelPessoa.getTxtfNome().grabFocus();
				}
			}
		});
		tabela.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int posicao = tabela.getSelectedRow();
				// PainelPessoa.irParaPoicao(posicao);
				System.out.println(tabela.getMouseListeners());
			}
		});
		colunas.add("Nome");
		colunas.add("CPF");
		colunas.add("Email");
		List<Pessoa> dados = new ArrayList<>();
		dados = daoP.pesquisarString(nome);
		modelotabela.setColumnIdentifiers(colunas.toArray());
		for (int i = 0; i < dados.size(); i++) {
			Object linha[] = {dados.get(i).getNome(),
					String.valueOf(dados.get(i).getCpf()),
					dados.get(i).getEmail()};
			modelotabela.addRow(linha);
		}
		tabela.setShowGrid(true);
		tabela.setModel(modelotabela);
		return tabela;
	}

	// TODO Tabela que adiciona usu·rios ao pedido
	public JTable pesqNomeTabelaAdicionaUsuarioAopedido(String str) {
		ArrayList<String> colunas = new ArrayList<>();
		tabela = new JTable();
		DefaultTableModel modelotabela = new DefaultTableModel();
		modelotabela = (DefaultTableModel) tabela.getModel();
		tabela.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Ao escrever
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				// TODO Ao Soltar a tecla
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					int posicao = tabela.getSelectedRow();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				// TODO Ao Pressionar Tecla
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					PainelPedidos.adicionaUsuario(arrayPessoa.get(posicao));
				} else if (tecla.getKeyCode() == 9) {
					// PainelPedidos.getTxtfCondPag().grabFocus();
				}
			}
		});
		tabela.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Ao soltar o bot√£o do mouse
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Ao Pressionar o bot√£o do mouse
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Ao sair o mouse
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Ao entrar o mouse
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Ao Clicar
				int posicao = tabela.getSelectedRow();
				PainelPedidos.adicionaUsuario(arrayPessoa.get(posicao));
			}
		});
		colunas.add("Nome");
		colunas.add("CPF");
		colunas.add("Email");
		arrayPessoa = new ArrayList<>();
		arrayPessoa = daoP.pesquisarString(str);
		modelotabela.setColumnIdentifiers(colunas.toArray());
		for (int i = 0; i < arrayPessoa.size(); i++) {
			Object linha[] = {arrayPessoa.get(i).getNome(),
					arrayPessoa.get(i).getCpf(), arrayPessoa.get(i).getEmail()};
			modelotabela.addRow(linha);
		}
		tabela.setShowGrid(true);
		tabela.setModel(modelotabela);
		return tabela;
	}
	public String carregarNomeGrupoCodigo(String codiGrupo) {
		return daoG.pesquisarNomeCodigo(codiGrupo);

	}
	public String carregarCodigoGrupoNome(String nomeGrupo) {
		return daoG.pesquisarCodigoNome(nomeGrupo);

	}
	public JComboBox<String> carregarGrupos() {
		cmbGrupos = new JComboBox<String>();
		cmbGrupos.addItem("Grupos");
		for (int i = 0; i < listGrupo.size(); i++) {
			cmbGrupos.addItem(listGrupo.get(i).getNomeGrupo());
		}
		return cmbGrupos;

	}

	public boolean logar(Pessoa u2) {
		// TODO Auto-generated method stub
		return false;
	}

	public void cadastrar(Pessoa u2, String confirmaSenha,
			String confirmaEmail) {
		// TODO Auto-generated method stub
	}

	public String criaCodiUsuario() {
		Calendar c = Calendar.getInstance();
		String codi = String.valueOf(daoP.consultaUltimo())
				+ String.valueOf(c.get(Calendar.YEAR))
				+ String.valueOf(c.get(Calendar.MONTH))
				+ String.valueOf(c.get(Calendar.DAY_OF_MONTH));

		return codi;
	}

	// TODO FunÁ„o sobrescrever
	public static void funcaoSobrescrever() {
		System.out.println("ControlaUsuario.funcaoSobrescrever");
		ControlaBotoes.limparBtnSalvar();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				p = PainelPessoa.lerCampos();
				if (!p.equals(null) & daoP.alterar(p)) {
					FrameInicial.setTabela(pesqNomeTabela(p.getCodiPessoa()));
					FrameInicial.setPainelVisualiza(new PainelPessoa(p));
					FrameInicial.atualizaTela();
					JOptionPane.showMessageDialog(null, "Feito!");
					FrameInicial.getContUsua().iniciar(AbaPessoas.getNomeNo());
				} else {
					JOptionPane.showMessageDialog(null,
							"Favor verificar os campos informados. ",
							"N„o foi possivel alterar!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	// TODO FunÁ„o Salvar
	public static void funcaoSalvar() {
		System.out.println("ControlaUsuario.funcaoSalvar");
		ControlaBotoes.limparBtnSalvar();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				p = PainelPessoa.lerCampos();
				if (!p.equals(null) & daoP.cadastrar(p)) {
					PainelPessoa.limparCampos();
					FrameInicial.setTabela(pesqNomeTabela(p.getCodiPessoa()));
					FrameInicial.setPainelVisualiza(new PainelPessoa(p));
					FrameInicial.atualizaTela();
					JOptionPane.showMessageDialog(null, "Feito");
					FrameInicial.getContUsua().iniciar(AbaPessoas.getNomeNo());
				} else {
					JOptionPane.showMessageDialog(null,
							"Problemas: Erro de acesso ao banco",
							"Erro ao Salvar", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public static void funcaoCancelar() {
		System.out.println("ControlaUsuario.cancelar");
		FrameInicial.getContUsua().iniciar("");
	}

	// TODO Funcao excluir
	public static boolean funcaoExcluir() {
		System.out.println("ControlaProduto.excluir");
		p = PainelPessoa.lerCampos();
		if (daoP.excluir(p)) {
			FrameInicial.limpaTela();
			funcaoCancelar();
			return true;
		} else {
			funcaoCancelar();
			return false;
		}
	}

	public void iniciar(String tipo) {
		System.out.println("FrameInicial.controlePessoasIniciar");
		ControlaBotoes.limpaTodosBotoes();
		FrameInicial.limparTxtfPesquisa();
		ControlaBotoes.desHabilitaEdicaoBotoes();
		FrameInicial.setTabela(tblPessoas(""));
		tabela.setRowSelectionInterval(0, 0);
		p = tblMdPessoa.getPessoa(tabela.getSelectedRow());
		FrameInicial.setPainelVisualiza(new PainelPessoa(p));
		carregaDetalhes(p);

		FrameInicial.getBtnEditar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PainelPessoa.habilitaEdicao();
				ControlaBotoes.habilitaEdicaoBotoes();
				funcaoSobrescrever();
			}
		});
		FrameInicial.getBtnNovo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.habilitaNovoBotoes();
				PainelPessoa.habilitaNovo();
				funcaoSalvar();
			}
		});
		FrameInicial.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.desHabilitaEdicaoBotoes();
				funcaoCancelar();
			}
		});
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.desHabilitaEdicaoBotoes();
				funcaoSalvar();
			}
		});
		FrameInicial.getBtnExcluir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.desHabilitaEdicaoBotoes();
				funcaoExcluir();
			}
		});

		FrameInicial.getTxtfPesquisa().grabFocus();
		FrameInicial.getTxtfPesquisa().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40) {
					tabela.setColumnSelectionInterval(0, 0);
					tabela.grabFocus();
				} else if (tecla.getExtendedKeyCode() == 27) {
					funcaoCancelar();
				} else {
					String str = FrameInicial.getTxtfPesquisa().getText();
					FrameInicial.setTabela(tblPessoas(str));
					tabela.setRowSelectionInterval(0, 0);
					p = tblMdPessoa.getPessoa(tabela.getSelectedRow());
					carregaDetalhes(p);
					FrameInicial.atualizaTela();
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40) {
					tabela.setColumnSelectionInterval(0, 0);
					tabela.grabFocus();
				} else if (tecla.getExtendedKeyCode() == 27) {
					funcaoCancelar();
				} else {
					String str = FrameInicial.getTxtfPesquisa().getText();
					FrameInicial.setTabela(tblPessoas(str));
					tabela.setRowSelectionInterval(0, 0);
					p = tblMdPessoa.getPessoa(tabela.getSelectedRow());
					carregaDetalhes(p);
					FrameInicial.atualizaTela();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	public void carregaDetalhes(Pessoa p) {
		PainelPessoa.carregarCampos(p);
		FrameInicial.atualizaTela();
		tabela.grabFocus();

	}
}