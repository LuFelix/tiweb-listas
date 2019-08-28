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

import br.com.recomendacao.beans.CentroCusto;
import br.com.recomendacao.beans.Conta;
import br.com.recomendacao.beans.Lancamento;
import br.com.recomendacao.dao.DAOCentroCusto;
import br.com.recomendacao.dao.DAOConta;
import br.com.recomendacao.dao.DAOLancamento;
import br.com.recomendacao.tableModels.commom.TableModelContas;
import br.com.recomendacao.visao.AbaCadastros;
import br.com.recomendacao.visao.FrameInicial;
import br.com.recomendacao.visao.FrameInicial.ControlaBotoes;
import br.com.recomendacao.visao.PainelConta;

public class ControlaConta {
	private JTable tabela;
	private TableModelContas mdlTabela;
	private DAOConta daoConta;
	private DAOLancamento daoContaLanc;
	private DAOCentroCusto daoCentCust;
	private List<Lancamento> listLanc;
	private List<Conta> listContas;
	private String nome;
	private JComboBox<String> cmbContasCCusto;
	private CentroCusto cCust;
	private Conta c;

	public ControlaConta() {
		daoConta = new DAOConta();
		daoContaLanc = new DAOLancamento();
		daoCentCust = new DAOCentroCusto();
		cCust = new CentroCusto();

	}

	// TODO Criar código
	public String criaCodigo() {
		System.out.println("ControlaConta.criarCodigo");
		Calendar c = Calendar.getInstance();
		String codigo = String.valueOf(daoConta.consultaUltimo())
				+ String.valueOf(c.get(Calendar.YEAR))
				+ String.valueOf(c.get(Calendar.MONTH))
				+ String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		return codigo;
	}
	public JTable tblctSaldoCentCust() {
		tabela = new JTable();
		mdlTabela = new TableModelContas(daoConta.listContaGroupByCCusto());
		tabela.setModel(mdlTabela);
		tabela.setShowHorizontalLines(true);
		return tabela;
	}

	public JTable pesqNomeTabela(String str) {
		tabela = new JTable();
		mdlTabela = new TableModelContas(daoConta.listContaGroupByCCusto());
		tabela.setModel(mdlTabela);
		tabela.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					c = mdlTabela.getConta(tabela.getSelectedRow());
					PainelConta.carregarCampos(c);
					FrameInicial.atualizaTela();
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					c = mdlTabela.getConta(tabela.getSelectedRow());
					PainelConta.carregarCampos(c);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					c = mdlTabela.getConta(tabela.getSelectedRow());
					PainelConta.carregarCampos(c);
					funcaoSobrescrever();
					FrameInicial.getTabela().changeSelection(--posicao, 0,
							false, false);
					PainelConta.getTxtFNomeConta().grabFocus();
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
				c = mdlTabela.getConta(tabela.getSelectedRow());
				PainelConta.carregarCampos(c);
				FrameInicial.atualizaTela();
			}
		});

		tabela.setShowHorizontalLines(true);
		return tabela;
	}

	public JTable tblPrincipal() {
		tabela = new JTable();
		mdlTabela = new TableModelContas(daoConta.listContaGroupByCCusto());
		tabela.setModel(mdlTabela);
		tabela.setShowGrid(true);
		tabela.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					c = mdlTabela.getConta(tabela.getSelectedRow());
					PainelConta.carregarCampos(c);
					FrameInicial.atualizaTela();
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					c = mdlTabela.getConta(tabela.getSelectedRow());
					PainelConta.carregarCampos(c);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					c = mdlTabela.getConta(tabela.getSelectedRow());
					PainelConta.carregarCampos(c);
					funcaoSobrescrever();
					FrameInicial.getTabela().changeSelection(--posicao, 0,
							false, false);
					PainelConta.getTxtFNomeConta().grabFocus();
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
				c = mdlTabela.getConta(tabela.getSelectedRow());
				PainelConta.carregarCampos(c);
				FrameInicial.atualizaTela();
			}
		});
		return tabela;

	}
	public JTable tblContasCentroCusto(String codiCentro) {
		tabela = new JTable();
		mdlTabela = new TableModelContas(daoConta.listContCCusto(codiCentro));
		tabela.setModel(mdlTabela);
		tabela.setShowGrid(true);
		tabela.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					c = mdlTabela.getConta(tabela.getSelectedRow());
					PainelConta.carregarCampos(c);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					PainelConta.carregarCampos(c);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					PainelConta.carregarCampos(c);
					funcaoSobrescrever();
					FrameInicial.getTabela().changeSelection(--posicao, 0,
							false, false);
					PainelConta.getTxtFNomeConta().grabFocus();
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
				c = mdlTabela.getConta(tabela.getSelectedRow());
				PainelConta.carregarCampos(c);

			}
		});
		return tabela;

	}
	public JTable tblContasCentroCusto(String str, String codiCentro) {
		tabela = new JTable();
		mdlTabela = new TableModelContas(
				daoConta.pesquisarString(str, codiCentro));
		tabela.setModel(mdlTabela);
		tabela.setShowGrid(true);
		tabela.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					c = mdlTabela.getConta(tabela.getSelectedRow());
					PainelConta.carregarCampos(c);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					iniciar(AbaCadastros.selecionado().getCodiCentroCusto());
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					PainelConta.carregarCampos(c);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					iniciar(AbaCadastros.selecionado().getCodiCentroCusto());
				} else if (tecla.getExtendedKeyCode() == 10) {
					PainelConta.carregarCampos(c);
					funcaoSobrescrever();
					FrameInicial.getTabela().changeSelection(--posicao, 0,
							false, false);
					PainelConta.getTxtFNomeConta().grabFocus();
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
				c = mdlTabela.getConta(tabela.getSelectedRow());
				PainelConta.carregarCampos(c);

			}
		});
		return tabela;

	}
	public List<Conta> listContCCusto(CentroCusto c) {
		listContas = new ArrayList<Conta>(
				daoConta.listContCCusto(c.getCodiCentroCusto()));
		return listContas;

	}
	public String nomeCentCustCodi(String codiCentCust) {
		cCust = daoCentCust.buscaCodigo(codiCentCust);
		if (cCust != (null)) {
			return cCust.getNomeCentroCusto();
		} else {
			return "";
		}

	}
	public String nomeCentCustCodiConta(String codiConta) {
		String codiCCusto = daoConta.cCustoCodiConta(codiConta);
		cCust = daoCentCust.buscaCodigo(codiCCusto);
		if (cCust != (null)) {
			return cCust.getNomeCentroCusto();
		} else {
			return "";
		}

	}
	// TODO Função Salvar
	public void funcaoSalvar() {
		System.out.println("ControlaConta.funcaoSalvar");
		ControlaBotoes.limparBtnSalvar();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c = PainelConta.lerCampos();
				if (!c.equals(null) & daoConta.cadastrar(c)) {
					PainelConta.limparCampos();
					FrameInicial.setTabela(pesqNomeTabela(c.getCodiConta()));
					PainelConta.carregarCampos(c);
					FrameInicial.atualizaTela();
					JOptionPane.showMessageDialog(null, "Feito");
					iniciar(AbaCadastros.selecionado().getCodiCentroCusto());
				} else {
					JOptionPane.showMessageDialog(null,
							"Problemas: Erro de acesso ao banco",
							"Erro ao Salvar", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public JComboBox cmbContasCCusto(String codiCCusto) {

		cmbContasCCusto = new JComboBox<String>();
		cmbContasCCusto.addItem("Conta");
		cmbContasCCusto.setToolTipText("Selecione a conta para o lançamento.");
		listContas = daoConta.pesquisarString("");
		if (listContas.size() > 0) {
			for (Conta conta : listContas) {
				cmbContasCCusto.addItem(conta.getNomeConta());
			}
		}

		return cmbContasCCusto;

	}
	// TODO Função sobrescrever
	public void funcaoSobrescrever() {
		System.out.println("ControlaConta.funcaoSobrescrever");
		ControlaBotoes.limparBtnSalvar();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c = PainelConta.lerCampos();
				if (!c.equals(null) & daoConta.alterar(c)) {
					FrameInicial.setTabela(pesqNomeTabela(c.getCodiConta()));
					PainelConta.carregarCampos(c);
					FrameInicial.atualizaTela();
					JOptionPane.showMessageDialog(null, "Feito!");
					iniciar(AbaCadastros.selecionado().getCodiCentroCusto());
				}
			}
		});
	}

	// TODO Funcao excluir
	public boolean funcaoExcluir() {
		System.out.println("ControlaConta.excluir");
		c = PainelConta.lerCampos();
		if (daoConta.excluir(c)) {
			FrameInicial.setTabela(null);
			FrameInicial.setPainelVisualiza(null);
			FrameInicial.atualizaTela();
			JOptionPane.showMessageDialog(null, "Feito!");
			iniciar(AbaCadastros.selecionado().getCodiCentroCusto());
			return true;
		} else {
			funcaoCancelar();
			return false;
		}
	}

	// TODO Iniciar
	// public void iniciar() {
	// System.out.println("ControlaConta.iniciar();");
	// ControlaBotoes.limpaTodosBotoes();
	// ControlaBotoes.desHabilitaEdicaoBotoes();
	// FrameInicial.getTxtfPesquisa().grabFocus();
	// FrameInicial.setTabela(tblPrincipal());
	// FrameInicial.setPainelVisualiza(new PainelConta());
	// FrameInicial.atualizaTela();
	// configuraBotes();
	// configuraTxtPesquisa();
	//
	// }

	public void iniciar(String codiCentro) {
		System.out.println("ControlaConta.iniciar(String codiCentro)");
		ControlaBotoes.limpaTodosBotoes();
		ControlaBotoes.desHabilitaEdicaoBotoes();
		FrameInicial.getTxtfPesquisa().grabFocus();
		FrameInicial.setTabela(tblContasCentroCusto(codiCentro));
		FrameInicial.setPainelVisualiza(new PainelConta());
		configuraBotes();
		configuraTxtPesquisa();
		FrameInicial.atualizaTela();

	}
	void configuraTxtPesquisa() {
		FrameInicial.limparTxtfPesquisa();
		FrameInicial.getTxtfPesquisa().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40) {// seta para baixo
					FrameInicial.getTabela().grabFocus();
					FrameInicial.getTabela().changeSelection(0, 0, false,
							false);

				} else if (tecla.getExtendedKeyCode() == 27) {
					funcaoCancelar();
				} else {
					nome = FrameInicial.getTxtfPesquisa().getText();
					FrameInicial.setTabela(tblContasCentroCusto(nome,
							AbaCadastros.selecionado().getCodiCentroCusto()));
					FrameInicial.atualizaTela();

				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40) {// seta para baixo
					FrameInicial.getTabela().grabFocus();
					FrameInicial.getTabela().changeSelection(0, 0, false,
							false);

				} else if (tecla.getExtendedKeyCode() == 27) {
					funcaoCancelar();
				} else {
					nome = FrameInicial.getTxtfPesquisa().getText();
					FrameInicial.setTabela(tblContasCentroCusto(nome,
							AbaCadastros.selecionado().getCodiCentroCusto()));
					FrameInicial.atualizaTela();

				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}
	void configuraBotes() {
		FrameInicial.getBtnEditar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PainelConta.habilitaEdicao();
				ControlaBotoes.habilitaEdicaoBotoes();
				funcaoSobrescrever();

			}
		});
		FrameInicial.getBtnNovo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.habilitaNovoBotoes();
				PainelConta.habilitaNovo();
				funcaoSalvar();
			}
		});
		FrameInicial.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciar(AbaCadastros.selecionado().getCodiCentroCusto());
			}
		});
		FrameInicial.getBtnExcluir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.desHabilitaEdicaoBotoes();
				funcaoExcluir();
			}
		});
	}
	// TODO Consultar Saldo
	public float consultaSaldo(Conta conta) {
		System.out.println("ControlaConta.consultaSaldo");
		float total = 0;
		listLanc = daoContaLanc.conEntrSaiConta(conta);
		for (Lancamento contaLancamento : listLanc) {
			total = total + contaLancamento.getValor();
		}
		return total;
	}
	public List<Conta> listContTipo(String tipoConta) {
		listContas = new ArrayList<Conta>(daoConta.listContTipo(tipoConta));
		return listContas;
	}
	public List<Conta> lisContCCusto(String codiCentroCusto) {
		listContas = new ArrayList<Conta>(
				daoConta.listContCCusto(codiCentroCusto));
		return listContas;
	}

	public void funcaoCancelar() {
		System.out.println("ControlaConta.cancelar");
		iniciar(AbaCadastros.selecionado().getCodiCentroCusto());
	}

	// TODO Consultar Entradas
	public Conta carregarEntradas(Conta conta) {
		conta.setListEntradas(daoContaLanc.conEntrSaiConta(conta));
		return conta;
	}

	public List<Conta> pesqNomeArray(String str) {
		return daoConta.pesquisarString(str);
	}

	public Object nomeContaCodigo() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Conta> getListCont() {
		return listContas;
	}

	public void setListCont(List<Conta> listCont) {
		this.listContas = listCont;
	}

}