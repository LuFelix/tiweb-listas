package br.com.simprovendas.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.simprovendas.beans.Conta;
import br.com.simprovendas.beans.Lancamento;
import br.com.simprovendas.dao.DAOCentroCusto;
import br.com.simprovendas.dao.DAOConta;
import br.com.simprovendas.dao.DAOLancamento;
import br.com.simprovendas.visao.FrameInicial;
import br.com.simprovendas.visao.FrameInicial.ControlaBotoes;
import br.com.simprovendas.visao.PainelConta;

public class ControlaConta {
	private JTable tabela;
	private List<Conta> listConta;
	private DAOConta daoConta;
	private DAOLancamento daoContaLanc;
	private DAOCentroCusto daoCentCust;
	private Conta conta;
	private List<Lancamento> listLanc;
	private String nome;

	public ControlaConta() {
		daoConta = new DAOConta();
		daoContaLanc = new DAOLancamento();
		daoCentCust = new DAOCentroCusto();

	}

	// TODO Função Salvar
	public void funcaoSalvar() {
		System.out.println("ControlaConta.funcaoSalvar");
		ControlaBotoes.limparBtnSalvar();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conta = PainelConta.lerCampos();
				if (!conta.equals(null) & daoConta.cadastrar(conta)) {
					PainelConta.limparCampos();
					FrameInicial.setTabela(pesqNomeTabela(conta.getCodiConta()));
					FrameInicial.setPainelVisualiza(new PainelConta(conta.getCodiConta()));
					FrameInicial.atualizaTela();
					JOptionPane.showMessageDialog(null, "Feito");
					iniciar();
				} else {
					JOptionPane.showMessageDialog(null, "Problemas: Erro de acesso ao banco", "Erro ao Salvar",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	// TODO Criar código
	public String criaCodigo() {
		System.out.println("ControlaConta.criarCodigo");
		Calendar c = Calendar.getInstance();
		String codigo = String.valueOf(daoConta.consultaUltimo()) + String.valueOf(c.get(Calendar.YEAR))
				+ String.valueOf(c.get(Calendar.MONTH)) + String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		return codigo;
	}

	public JTable pesqNomeTabela(String str) {
		tabela = new JTable();
		List<String> colunas = new ArrayList<String>();
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
					PainelConta.irParaPoicao(posicao);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40 || tecla.getExtendedKeyCode() == 38) {
					PainelConta.irParaPoicao(posicao);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					PainelConta.irParaPoicao(posicao);
					funcaoSobrescrever();
					FrameInicial.getTabela().changeSelection(--posicao, 0, false, false);
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
				int posicao = tabela.getSelectedRow();
				PainelConta.irParaPoicao(posicao);
				System.out.println(tabela.getMouseListeners());
			}
		});
		colunas.add("Nome");
		colunas.add("Agência");
		colunas.add("Num. Conta");
		colunas.add("Banco");
		List<Conta> dados = new ArrayList<Conta>();
		dados = daoConta.pesquisarString(str);
		modelotabela.setColumnIdentifiers(colunas.toArray());
		for (int i = 0; i < dados.size(); i++) {
			Object linha[] = { dados.get(i).getNomeConta(), dados.get(i).getAgencia(), dados.get(i).getConta(),
					dados.get(i).getBanco() };
			modelotabela.addRow(linha);
		}
		tabela.setShowHorizontalLines(true);
		tabela.setModel(modelotabela);
		return tabela;
	}

	public JTable tblResumoContasMes() {
		tabela = new JTable();
		List<String> colunas = new ArrayList<String>();
		DefaultTableModel modelotabela = new DefaultTableModel();
		modelotabela = (DefaultTableModel) tabela.getModel();
		colunas.add("Nome");
		colunas.add("Centro de Custo");
		colunas.add("Saldo");

		List<Conta> dados = new ArrayList<Conta>();
		dados = daoConta.pesquisarString("");
		modelotabela.setColumnIdentifiers(colunas.toArray());
		for (int i = 0; i < dados.size(); i++) {
			Object linha[] = { dados.get(i).getNomeConta(),
					daoCentCust.buscaCodigo(dados.get(i).getCentroCusto()).getNomeCentroCusto(),
					consultaSaldo(dados.get(i)) };
			modelotabela.addRow(linha);
		}
		tabela.setShowGrid(true);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(90);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(145);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(80);
		tabela.setModel(modelotabela);

		return tabela;

	}

	// TODO Função sobrescrever
	public void funcaoSobrescrever() {
		System.out.println("ControlaConta.funcaoSobrescrever");
		ControlaBotoes.limparBtnSalvar();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conta = PainelConta.lerCampos();
				if (!conta.equals(null) & daoConta.alterar(conta)) {
					FrameInicial.setTabela(pesqNomeTabela(conta.getCodiConta()));
					FrameInicial.setPainelVisualiza(new PainelConta(conta.getCodiConta()));
					FrameInicial.atualizaTela();
					JOptionPane.showMessageDialog(null, "Feito!");
					iniciar();
				}
			}
		});
	}

	// TODO Funcao excluir
	public boolean funcaoExcluir() {
		System.out.println("ControlaConta.excluir");
		conta = PainelConta.lerCampos();
		if (daoConta.excluir(conta)) {
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

	private List<Conta> listaPorCentroCusto() {
		return listConta;
	}

	// TODO Iniciar
	public void iniciar() {
		System.out.println("FrameInicial.pesquisaConta");
		ControlaBotoes.limpaTodosBotoes();
		ControlaBotoes.desHabilitaEdicaoBotoes();
		FrameInicial.limparTxtfPesquisa();
		FrameInicial.getTxtfPesquisa().grabFocus();
		FrameInicial.setTabela(pesqNomeTabela(""));
		FrameInicial.setPainelVisualiza(new PainelConta(""));
		FrameInicial.atualizaTela();
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
		FrameInicial.getTxtfPesquisa().addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40) {// seta para baixo
					FrameInicial.getTabela().grabFocus();
					FrameInicial.getTabela().changeSelection(0, 0, false, false);
				} else if (tecla.getExtendedKeyCode() == 27) {
					funcaoCancelar();
				} else {
					// System.out.println(tecla.getExtendedKeyCode());
					nome = FrameInicial.getTxtfPesquisa().getText();
					FrameInicial.setTabela(pesqNomeTabela(nome));
					FrameInicial.setPainelVisualiza(new PainelConta(nome));
					FrameInicial.atualizaTela();
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				nome = FrameInicial.getTxtfPesquisa().getText();
				FrameInicial.setTabela(pesqNomeTabela(nome));
				FrameInicial.setPainelVisualiza(new PainelConta(nome));
				FrameInicial.atualizaTela();
			}

			@Override
			public void keyTyped(KeyEvent e) {
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

	public void funcaoCancelar() {
		System.out.println("ControlaConta.cancelar");
		iniciar();
	}

	// TODO Consultar Entradas
	public Conta carregarEntradas(Conta conta) {
		conta.setListEntradas(daoContaLanc.conEntrSaiConta(conta));
		return conta;
	}

	public List<Conta> pesqNomeArray(String str) {
		return daoConta.pesquisarString(str);
	}

}