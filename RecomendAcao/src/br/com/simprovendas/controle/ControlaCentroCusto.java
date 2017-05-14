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

import br.com.simprovendas.beans.CentroCusto;
import br.com.simprovendas.dao.DAOCentroCusto;
import br.com.simprovendas.visao.FrameInicial;
import br.com.simprovendas.visao.FrameInicial.ControlaBotoes;
import br.com.simprovendas.visao.PainelCentroCusto;

public class ControlaCentroCusto {

	private static JTable tabela;
	private static List<CentroCusto> listCentroCusto;
	private static DAOCentroCusto daoCentroCusto;
	private static CentroCusto centroCusto;
	private static String nome;

	public ControlaCentroCusto() {
		System.out.println("ControlaCentroCusto.construtor");
		daoCentroCusto = new DAOCentroCusto();
	}

	private JTable pesqNomeTabela(String str) {
		tabela = new JTable();
		List<String> colunas = new ArrayList<String>();
		DefaultTableModel modelotabela = new DefaultTableModel();
		modelotabela = (DefaultTableModel) tabela.getModel();
		listCentroCusto = new ArrayList<CentroCusto>(daoCentroCusto.pesquisarString(str));
		tabela.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40 || tecla.getExtendedKeyCode() == 38) {
					int posicao = tabela.getSelectedRow();
					centroCusto = listCentroCusto.get(posicao);
					PainelCentroCusto.carregarCampos(centroCusto);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40 || tecla.getExtendedKeyCode() == 38) {
					PainelCentroCusto.carregarCampos(centroCusto);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					PainelCentroCusto.carregarCampos(centroCusto);
					funcaoSobrescrever();
					FrameInicial.getTabela().changeSelection(--posicao, 0, false, false);
					PainelCentroCusto.getTxtFNome().grabFocus();
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
				PainelCentroCusto.irParaPoicao(posicao);
				System.out.println(tabela.getMouseListeners());
			}
		});
		colunas.add("Número");
		colunas.add("Nome");
		colunas.add("Descrição");
		modelotabela.setColumnIdentifiers(colunas.toArray());

		for (int i = 0; i < listCentroCusto.size(); i++) {
			Object linha[] = { listCentroCusto.get(i).getSeqcentroCusto(), listCentroCusto.get(i).getNomeCentroCusto(),
					listCentroCusto.get(i).getDescCentroCusto() };
			modelotabela.addRow(linha);
		}
		// tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(60);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(240);
		tabela.setShowGrid(true);
		tabela.setModel(modelotabela);
		return tabela;
	}

	public List<CentroCusto> pesqNomeArray(String str) {
		return daoCentroCusto.pesquisarString(str);
	}

	private static void ajusta_tamanho_coluna() {
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(60);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(270);
	}

	// TODO Função Salvar
	private void funcaoSalvar() {
		System.out.println("ControlaCentroCusto.funcaoSalvar");
		ControlaBotoes.limparBtnSalvar();
		ControlaBotoes.habilitaNovoBotoes();
		PainelCentroCusto.habilitaNovo();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				centroCusto = PainelCentroCusto.lerCampos();
				if (!centroCusto.equals(null) & daoCentroCusto.cadastrar(centroCusto)) {
					PainelCentroCusto.limparCampos();
					FrameInicial.setTabela(pesqNomeTabela(centroCusto.getCodiCentroCusto()));
					FrameInicial.setPainelVisualiza(new PainelCentroCusto(centroCusto.getCodiCentroCusto()));
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

	public void funcaoCancelar() {
		System.out.println("ControlaCentroCusto.cancelar");
		iniciar();
	}

	// TODO Funcao excluir
	public boolean funcaoExcluir() {
		System.out.println("ControlaGCentroCusto.excluir");
		centroCusto = PainelCentroCusto.lerCampos();
		if (daoCentroCusto.excluir(centroCusto)) {
			FrameInicial.limpaTela();
			funcaoCancelar();
			return true;
		} else {
			funcaoCancelar();
			return false;
		}
	}

	// TODO Função sobrescrever
	private void funcaoSobrescrever() {
		System.out.println("ControlaCentroCusto.funcaoSobrescrever");
		ControlaBotoes.limparBtnSalvar();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				centroCusto = PainelCentroCusto.lerCampos();
				if (!centroCusto.equals(null) & daoCentroCusto.alterar(centroCusto)) {
					FrameInicial.setTabela(pesqNomeTabela(centroCusto.getCodiCentroCusto()));
					FrameInicial.setPainelVisualiza(new PainelCentroCusto(centroCusto.getCodiCentroCusto()));
					FrameInicial.atualizaTela();
					JOptionPane.showMessageDialog(null, "Feito!");
					iniciar();
				} else {
					JOptionPane.showMessageDialog(null, "Favor verificar os campos informados. ",
							"Não foi possivel alterar!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public CentroCusto buscaNome(String nome) {
		centroCusto = daoCentroCusto.buscaNome(nome);
		return centroCusto;
	}

	public CentroCusto buscaCodigo(String codigo) {
		centroCusto = daoCentroCusto.buscaCodigo(codigo);
		return centroCusto;
	}

	// TODO Criar código
	public String criaCodigo() {
		System.out.println("ControlaCentroCusto.criarCodigo");
		Calendar c = Calendar.getInstance();
		String codigo = String.valueOf(daoCentroCusto.consultaUltimo()) + String.valueOf(c.get(Calendar.YEAR))
				+ String.valueOf(c.get(Calendar.MONTH)) + String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		return codigo;
	}

	// TODO Pesquisa Centro Custo
	public void iniciar() {
		System.out.println("ControlaCentroCusto.pesquisaGrupo");
		ControlaBotoes.limpaTodosBotoes();
		ControlaBotoes.desHabilitaEdicaoBotoes();
		FrameInicial.limparTxtfPesquisa();
		FrameInicial.getTxtfPesquisa().grabFocus();
		FrameInicial.setTabela(pesqNomeTabela(""));
		FrameInicial.setPainelVisualiza(new PainelCentroCusto(""));
		FrameInicial.atualizaTela();
		ActionListener acaoEditar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PainelCentroCusto.habilitaEdicao();
				ControlaBotoes.habilitaEdicaoBotoes();
				funcaoSobrescrever();
			}
		};
		FrameInicial.getBtnEditar().addActionListener(acaoEditar);

		FrameInicial.getBtnNovo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.habilitaNovoBotoes();
				PainelCentroCusto.habilitaNovo();
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
				if (tecla.getExtendedKeyCode() == 40) {// seta para baixo
					FrameInicial.getTabela().grabFocus();
					FrameInicial.getTabela().changeSelection(0, 0, false, false);
				} else if (tecla.getExtendedKeyCode() == 27) {
					funcaoCancelar();
				} else {
					// System.out.println(tecla.getExtendedKeyCode());
					nome = FrameInicial.getTxtfPesquisa().getText();
					FrameInicial.setTabela(pesqNomeTabela(nome));
					FrameInicial.setPainelVisualiza(new PainelCentroCusto(nome));
					FrameInicial.atualizaTela();
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				nome = FrameInicial.getTxtfPesquisa().getText();
				FrameInicial.setTabela(pesqNomeTabela(nome));
				FrameInicial.setPainelVisualiza(new PainelCentroCusto(nome));
				FrameInicial.atualizaTela();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

}
