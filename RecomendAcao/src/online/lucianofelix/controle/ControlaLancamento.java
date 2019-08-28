package br.com.recomendacao.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import br.com.recomendacao.beans.Lancamento;
import br.com.recomendacao.beans.Pedido;
import br.com.recomendacao.dao.DAOLancamento;
import br.com.recomendacao.tableModels.commom.TableModelLancamento;
import br.com.recomendacao.visao.FrameInicial;
import br.com.recomendacao.visao.FrameInicial.ControlaBotoes;
import br.com.recomendacao.visao.PainelConta;
import br.com.recomendacao.visao.PainelLancamento;

public class ControlaLancamento {
	DAOLancamento daoLancamento;
	Lancamento lanc;
	TableModelLancamento tblMdLanc;
	JTable tabela;

	private String nome;

	public ControlaLancamento() {
		daoLancamento = new DAOLancamento();
	}

	public void adicionaLancamentoPedido(Pedido pedi, Lancamento lanc) {
		if (pedi.getTipoPedido().equals("Compra")) {
			lanc.setTipoLancamento("Sai");
		} else if (pedi.getTipoPedido().equals("Venda")) {
			lanc.setTipoLancamento("Entra");
		}
		try {
			daoLancamento.novoLancamento("162017514", lanc.getCodiCondPag(),
					pedi.getCodiPedi(), pedi.getCodiPessoaCliente(), null,
					lanc.getValor(), "Obs", null, lanc.getTipoLancamento());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void adicionaLancamento(Lancamento lanc) {
		try {
			daoLancamento.novoLancamento("10201662", lanc.getCodiCondPag(),
					"codigoDocumento", "codigoPagador", null, lanc.getValor(),
					"Obs", null, lanc.getTipoLancamento());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public JTable pesqNomeTabela(String str) {
		tabela = new JTable();
		tblMdLanc = new TableModelLancamento(
				daoLancamento.listUltLancamentos());
		tabela.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					lanc = tblMdLanc.getLancamento(
							FrameInicial.getTabela().getSelectedRow());
					FrameInicial.setPainelVisualiza(new PainelLancamento(lanc));
					FrameInicial.atualizaTela();
					FrameInicial.getTabela().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40
						|| tecla.getExtendedKeyCode() == 38) {
					lanc = tblMdLanc.getLancamento(
							FrameInicial.getTabela().getSelectedRow());
					FrameInicial.setPainelVisualiza(new PainelLancamento(lanc));
					FrameInicial.atualizaTela();
					FrameInicial.getTabela().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();

				} else if (tecla.getExtendedKeyCode() == 10) {
					FrameInicial.getTabela().changeSelection(--posicao, 0,
							false, false);
					lanc = tblMdLanc.getLancamento(
							FrameInicial.getTabela().getSelectedRow());
					FrameInicial.setPainelVisualiza(new PainelLancamento(lanc));
					FrameInicial.atualizaTela();
					funcaoSobrescrever();
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
				lanc = tblMdLanc.getLancamento(
						FrameInicial.getTabela().getSelectedRow());
				FrameInicial.setPainelVisualiza(new PainelLancamento(lanc));
				FrameInicial.atualizaTela();

			}
		});

		tabela.setShowGrid(true);
		tabela.setModel(tblMdLanc);
		return tabela;
	}
	// TODO Iniciar
	public void iniciar() {
		System.out.println("ControlaLancamento.pesquisaLancamento");
		ControlaBotoes.limpaTodosBotoes();
		ControlaBotoes.desHabilitaEdicaoBotoes();
		configuraBotoes();
		FrameInicial.limparTxtfPesquisa();
		FrameInicial.getTxtfPesquisa().grabFocus();

		FrameInicial.setTabela(pesqNomeTabela(""));
		FrameInicial.getTabela().setRowSelectionInterval(0, 0);
		lanc = tblMdLanc
				.getLancamento(FrameInicial.getTabela().getSelectedRow());
		FrameInicial.setPainelVisualiza(new PainelLancamento(lanc));
		FrameInicial.atualizaTela();

		FrameInicial.getTxtfPesquisa().addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40) {// seta para baixo
					FrameInicial.getTabela().grabFocus();
					FrameInicial.getTabela().setRowSelectionInterval(0, 0);
					lanc = tblMdLanc.getLancamento(
							FrameInicial.getTabela().getSelectedRow());
					FrameInicial.setPainelVisualiza(new PainelLancamento(lanc));
				} else if (tecla.getExtendedKeyCode() == 27) {
					funcaoCancelar();
				} else {
					// System.out.println(tecla.getExtendedKeyCode());
					nome = FrameInicial.getTxtfPesquisa().getText();
					FrameInicial.setTabela(pesqNomeTabela(nome));
					lanc = tblMdLanc.getLancamento(
							FrameInicial.getTabela().getSelectedRow());
					FrameInicial.setPainelVisualiza(new PainelLancamento(lanc));
					FrameInicial.atualizaTela();
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				nome = FrameInicial.getTxtfPesquisa().getText();
				FrameInicial.setTabela(pesqNomeTabela(nome));
				lanc = tblMdLanc.getLancamento(
						FrameInicial.getTabela().getSelectedRow());
				FrameInicial.setPainelVisualiza(new PainelLancamento(lanc));
				FrameInicial.atualizaTela();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}
	void configuraBotoes() {
		FrameInicial.getBtnEditar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PainelLancamento.habilitaEdicao();
				ControlaBotoes.habilitaEdicaoBotoes();
				funcaoSobrescrever();

			}
		});
		FrameInicial.getBtnNovo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.habilitaNovoBotoes();
				PainelLancamento.habilitaNovo();
				funcaoSalvar();
			}
		});
		FrameInicial.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciar();
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
	public void funcaoCancelar() {
		System.out.println("ControlaConta.cancelar");
		iniciar();
	}
	// TODO Funcao excluir
	public boolean funcaoExcluir() {
		System.out.println("ControlaConta.excluir");
		lanc = PainelLancamento.lerCampos();
		if (daoLancamento.excluir(lanc)) {
			FrameInicial.setTabela(null);
			FrameInicial.setPainelVisualiza(null);
			FrameInicial.atualizaTela();
			JOptionPane.showMessageDialog(null, "Feito!");
			iniciar();
			return true;
		} else {
			funcaoCancelar();
			return false;
		}
	}
	// TODO Função Salvar
	public void funcaoSalvar() {
		System.out.println("ControlaConta.funcaoSalvar");
		ControlaBotoes.limparBtnSalvar();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lanc = PainelLancamento.lerCampos();
				if (!lanc.equals(null)
						& daoLancamento.inserirLancamento(lanc)) {
					PainelConta.limparCampos();
					FrameInicial.setTabela(pesqNomeTabela(lanc.getCodiConta()));
					FrameInicial.setPainelVisualiza(new PainelLancamento(lanc));
					FrameInicial.atualizaTela();
					JOptionPane.showMessageDialog(null, "Feito");
					iniciar();
				} else {
					JOptionPane.showMessageDialog(null,
							"Problemas: Erro de acesso ao banco",
							"Erro ao Salvar", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	// TODO Função sobrescrever
	public void funcaoSobrescrever() {
		System.out.println("ControlaConta.funcaoSobrescrever");
		ControlaBotoes.limparBtnSalvar();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lanc = PainelLancamento.lerCampos();
				if (!lanc.equals(null) & daoLancamento.alterar(lanc)) {
					FrameInicial
							.setTabela(pesqNomeTabela(lanc.getCodiCondPag()));
					FrameInicial.setPainelVisualiza(new PainelLancamento(lanc));
					FrameInicial.atualizaTela();
					JOptionPane.showMessageDialog(null, "Feito!");
					iniciar();
				}
			}
		});
	}

}
