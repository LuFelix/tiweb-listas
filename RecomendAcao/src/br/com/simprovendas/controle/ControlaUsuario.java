package br.com.simprovendas.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.simprovendas.beans.Pessoa;
import br.com.simprovendas.dao.DAOUsuario;
import br.com.simprovendas.visao.FrameInicial;
import br.com.simprovendas.visao.FrameInicial.ControlaBotoes;
import br.com.simprovendas.visao.PainelPedidos;
import br.com.simprovendas.visao.PainelPessoa;

public class ControlaUsuario {
	static JTable tabela;
	private ArrayList<Pessoa> arrayUsua;
	static DAOUsuario daoU;
	static Pessoa u;

	public ControlaUsuario() {
		daoU = new DAOUsuario();
	}

	public ArrayList<Pessoa> pesquisar() {
		daoU = new DAOUsuario();
		arrayUsua = new ArrayList<Pessoa>();
		arrayUsua = daoU.listarTodos();
		for (int i = 0; i < arrayUsua.size(); i++) {
			System.out.println(arrayUsua.get(i).getCpf() + "Nome" + arrayUsua.get(i).getNome());
		}
		return arrayUsua;

	}

	public Pessoa pesquisar(String nome) {
		u = new Pessoa();
		daoU = new DAOUsuario();
		u = daoU.pesquisar(nome);
		return u;

	}

	public void posicionarTabela(int linha) {
		System.out.println("Ir para a linha: " + linha);
		FrameInicial.getTabela().changeSelection(linha, 0, false, false);

	}

	// TODO Tabela Ligada ao painel de Contatos/ Pessoas
	public static JTable pesqNomeTabela(String nome) {
		daoU = new DAOUsuario();
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
				if (tecla.getExtendedKeyCode() == 40 || tecla.getExtendedKeyCode() == 38) {
					int posicao = tabela.getSelectedRow();
					PainelPessoa.irParaPoicao(posicao);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40 || tecla.getExtendedKeyCode() == 38) {
					PainelPessoa.irParaPoicao(posicao);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					PainelPessoa.irParaPoicao(posicao);
					funcaoSobrescrever();
					FrameInicial.getTabela().changeSelection(--posicao, 0, false, false);
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
				PainelPessoa.irParaPoicao(posicao);
				System.out.println(tabela.getMouseListeners());
			}
		});
		colunas.add("Nome");
		colunas.add("CPF");
		colunas.add("Email");
		ArrayList<Pessoa> dados = new ArrayList<>();
		dados = daoU.pesquisarNome(nome);
		modelotabela.setColumnIdentifiers(colunas.toArray());
		for (int i = 0; i < dados.size(); i++) {
			Object linha[] = { dados.get(i).getNome(), String.valueOf(dados.get(i).getCpf()), dados.get(i).getEmail() };
			modelotabela.addRow(linha);
		}
		tabela.setShowGrid(true);
		tabela.setModel(modelotabela);
		return tabela;
	}

	// Pesquisa por nome filtra relaÁ„o
	public static JTable pesqNomeRelacao(String nome, String relacao) {
		daoU = new DAOUsuario();
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
				if (tecla.getExtendedKeyCode() == 40 || tecla.getExtendedKeyCode() == 38) {
					int posicao = tabela.getSelectedRow();
					PainelPessoa.irParaPoicao(posicao);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40 || tecla.getExtendedKeyCode() == 38) {
					PainelPessoa.irParaPoicao(posicao);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					PainelPessoa.irParaPoicao(posicao);
					funcaoSobrescrever();
					FrameInicial.getTabela().changeSelection(--posicao, 0, false, false);
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
				PainelPessoa.irParaPoicao(posicao);
				System.out.println(tabela.getMouseListeners());
			}
		});
		colunas.add("Nome");
		colunas.add("CPF");
		colunas.add("Email");
		ArrayList<Pessoa> dados = new ArrayList<>();
		dados = daoU.pesquisarNome(nome);
		modelotabela.setColumnIdentifiers(colunas.toArray());
		for (int i = 0; i < dados.size(); i++) {
			Object linha[] = { dados.get(i).getNome(), String.valueOf(dados.get(i).getCpf()), dados.get(i).getEmail() };
			modelotabela.addRow(linha);
		}
		tabela.setShowGrid(true);
		tabela.setModel(modelotabela);
		return tabela;
	}

	public JTable tabela() {
		tabela = new JTable();
		daoU = new DAOUsuario();
		ArrayList<String> colunas = new ArrayList<>();
		DefaultTableModel modelotabela = new DefaultTableModel();
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
				JOptionPane.showMessageDialog(null, "Posi√ß√£o da linha da tabela: " + posicao);
			}
		});
		modelotabela = (DefaultTableModel) tabela.getModel();
		colunas.add("Nome");
		colunas.add("CPF");
		colunas.add("Email");
		ArrayList<Pessoa> dados = new ArrayList<>();
		dados = daoU.listarTodos();
		modelotabela.setColumnIdentifiers(colunas.toArray());
		for (int i = 0; i < dados.size(); i++) {
			Object linha[] = { dados.get(i).getNome(), String.valueOf(dados.get(i).getCpf()), dados.get(i).getEmail() };
			modelotabela.addRow(linha);
		}
		tabela.setShowGrid(true);
		tabela.setModel(modelotabela);
		return tabela;
	}

	// TODO Tabela que adiciona usu·rios ao pedido
	public JTable pesqNomeTabelaAdicionaUsuarioAopedido(String nome) {
		daoU = new DAOUsuario();
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
				if (tecla.getExtendedKeyCode() == 40 || tecla.getExtendedKeyCode() == 38) {
					int posicao = tabela.getSelectedRow();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				// TODO Ao Pressionar Tecla
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40 || tecla.getExtendedKeyCode() == 38) {
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					PainelPedidos.adicionaUsuario(arrayUsua.get(posicao));
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
				PainelPedidos.adicionaUsuario(arrayUsua.get(posicao));
			}
		});
		colunas.add("Nome");
		colunas.add("CPF");
		colunas.add("Email");
		arrayUsua = new ArrayList<>();
		arrayUsua = daoU.pesquisarNome(nome);
		modelotabela.setColumnIdentifiers(colunas.toArray());
		for (int i = 0; i < arrayUsua.size(); i++) {
			Object linha[] = { arrayUsua.get(i).getNome(), arrayUsua.get(i).getCpf(), arrayUsua.get(i).getEmail() };
			modelotabela.addRow(linha);
		}
		tabela.setShowGrid(true);
		tabela.setModel(modelotabela);
		return tabela;
	}

	public boolean logar(Pessoa u2) {
		// TODO Auto-generated method stub
		return false;
	}

	public void cadastrar(Pessoa u2, String confirmaSenha, String confirmaEmail) {
		// TODO Auto-generated method stub
	}

	public String criaCodiUsuario() {
		Calendar c = Calendar.getInstance();
		String codi = String.valueOf(daoU.consultaUltimo()) + String.valueOf(c.get(Calendar.YEAR))
				+ String.valueOf(c.get(Calendar.MONTH)) + String.valueOf(c.get(Calendar.DAY_OF_MONTH));

		return codi;
	}

	// TODO FunÁ„o sobrescrever
	public static void funcaoSobrescrever() {
		System.out.println("ControlaUsuario.funcaoSobrescrever");
		ControlaBotoes.limparBtnSalvar();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				u = PainelPessoa.lerCampos();
				if (!u.equals(null) & daoU.alterar(u)) {
					FrameInicial.setTabela(pesqNomeTabela(u.getCodiPessoa()));
					FrameInicial.setPainelVisualiza(new PainelPessoa(u.getCodiPessoa()));
					System.out.println(u.getCodiPessoa());
					System.out.println(u.getNome());
					FrameInicial.atualizaTela();
					JOptionPane.showMessageDialog(null, "Feito!");
					FrameInicial.pesquisaUsuario();
				} else {
					JOptionPane.showMessageDialog(null, "Favor verificar os campos informados. ",
							"N„o foi possivel alterar o produto!", JOptionPane.ERROR_MESSAGE);
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
				u = PainelPessoa.lerCampos();
				if (!u.equals(null) & daoU.cadastrar(u)) {
					PainelPessoa.limparCampos();
					FrameInicial.setTabela(pesqNomeTabela(u.getCodiPessoa()));
					FrameInicial.setPainelVisualiza(new PainelPessoa(u.getCodiPessoa()));
					FrameInicial.atualizaTela();
					JOptionPane.showMessageDialog(null, "Feito");
					FrameInicial.pesquisaUsuario();
				} else {
					JOptionPane.showMessageDialog(null, "Problemas: Erro de acesso ao banco", "Erro ao Salvar",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public static void funcaoCancelar() {
		System.out.println("ControlaUsuario.cancelar");
		FrameInicial.pesquisaUsuario();
	}

	// TODO Funcao excluir
	public static boolean funcaoExcluir() {
		System.out.println("ControlaProduto.excluir");
		u = PainelPessoa.lerCampos();
		if (daoU.excluir(u)) {
			FrameInicial.limpaTela();
			funcaoCancelar();
			return true;
		} else {
			funcaoCancelar();
			return false;
		}
	}

	public void iniciar() {
		System.out.println("FrameInicial.pesquisaProduto");
		ControlaBotoes.limpaTodosBotoes();
		FrameInicial.limparTxtfPesquisa();
		ControlaBotoes.desHabilitaEdicaoBotoes();
		FrameInicial.getTxtfPesquisa().grabFocus();
		FrameInicial.setTabela(pesqNomeTabela(""));
		FrameInicial.setPainelVisualiza(new PainelPessoa(""));
		FrameInicial.atualizaTela();
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
		FrameInicial.getTxtfPesquisa().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40) {
					FrameInicial.getTabela().grabFocus();
					FrameInicial.getTabela().changeSelection(0, 0, false, false);
				} else if (tecla.getExtendedKeyCode() == 27) {
					funcaoCancelar();
				} else {
					String nome = FrameInicial.getTxtfPesquisa().getText();
					FrameInicial.setTabela(pesqNomeTabela(nome));
					FrameInicial.setPainelVisualiza(new PainelPessoa(nome));
					FrameInicial.atualizaTela();
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				String nome = FrameInicial.getTxtfPesquisa().getText();
				FrameInicial.setTabela(pesqNomeTabela(nome));
				FrameInicial.setPainelVisualiza(new PainelPessoa(nome));
				FrameInicial.atualizaTela();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	public void iniciar(String nomeNo) {
		System.out.println("FrameInicial.pesquisaProduto");
		ControlaBotoes.limpaTodosBotoes();
		FrameInicial.limparTxtfPesquisa();
		ControlaBotoes.desHabilitaEdicaoBotoes();
		FrameInicial.getTxtfPesquisa().grabFocus();
		FrameInicial.setTabela(pesqNomeTabela(""));
		FrameInicial.setPainelVisualiza(new PainelPessoa(""));
		FrameInicial.atualizaTela();
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
		FrameInicial.getTxtfPesquisa().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40) {
					FrameInicial.getTabela().grabFocus();
					FrameInicial.getTabela().changeSelection(0, 0, false, false);
				} else if (tecla.getExtendedKeyCode() == 27) {
					funcaoCancelar();
				} else {
					String nome = FrameInicial.getTxtfPesquisa().getText();
					FrameInicial.setTabela(pesqNomeTabela(nome));
					FrameInicial.setPainelVisualiza(new PainelPessoa(nome));
					FrameInicial.atualizaTela();
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				String nome = FrameInicial.getTxtfPesquisa().getText();
				FrameInicial.setTabela(pesqNomeTabela(nome));
				FrameInicial.setPainelVisualiza(new PainelPessoa(nome));
				FrameInicial.atualizaTela();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

}