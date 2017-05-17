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

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.recomendacao.beans.GrupoSubgrupo;
import br.com.recomendacao.dao.DAOGrupoSubgrupo;
import br.com.recomendacao.visao.FrameInicial;
import br.com.recomendacao.visao.PainelConta;
import br.com.recomendacao.visao.PainelGrupoSubgrupo;
import br.com.recomendacao.visao.FrameInicial.ControlaBotoes;

public class ControlaGrupoSubgrupo {
	private JTable tabela;
	private List<GrupoSubgrupo> listGrupo;
	private DAOGrupoSubgrupo daoGrupo;
	private GrupoSubgrupo grupo;
	private String nome;

	public ControlaGrupoSubgrupo() {
		System.out.println("ControlaGrupoSubgrupo.construtor");
		daoGrupo = new DAOGrupoSubgrupo();
	}

	// TODO Função Salvar
	public void funcaoSalvar() {
		System.out.println("ControlaGrupo.funcaoSalvar");
		ControlaBotoes.limparBtnSalvar();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				grupo = PainelGrupoSubgrupo.lerCampos();

				if (!grupo.equals(null) & daoGrupo.cadastrar(grupo)) {
					PainelGrupoSubgrupo.limparCampos();
					FrameInicial.setTabela(pesqNomeTabela(grupo.getCodiGrupo()));
					FrameInicial.setPainelVisualiza(new PainelGrupoSubgrupo(grupo.getCodiGrupo()));
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
		System.out.println("ControlaGrupoSubgrupo.criarCodigo");
		Calendar c = Calendar.getInstance();
		String codigo = String.valueOf(daoGrupo.consultaUltimo()) + String.valueOf(c.get(Calendar.YEAR))
				+ String.valueOf(c.get(Calendar.MONTH)) + String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		return codigo;
	}

	public GrupoSubgrupo nomeAproximado() {
		int tam = daoGrupo.consultaUltimo();
		if (tam == 0) {
			FrameInicial.setPainelVisualiza(new PainelGrupoSubgrupo(null));
			FrameInicial.getScrVisualiza().setViewportView(FrameInicial.getPainelVisualiza());
			return null;
		} else {
			return daoGrupo.pesquisarString("").get(0);
		}
	}

	public void funcaoCancelar() {
		System.out.println("ControlaGrupo.cancelar");
		iniciar();
	}

	public List<GrupoSubgrupo> pesqNomeArray(String str) {
		return daoGrupo.pesquisarString(str);
	}

	public JTable pesqNomeTabela(String str) {
		tabela = new JTable();
		List<String> colunas = new ArrayList<String>();
		DefaultTableModel modelotabela = new DefaultTableModel();
		modelotabela = (DefaultTableModel) tabela.getModel();
		listGrupo = new ArrayList<GrupoSubgrupo>(daoGrupo.pesquisarString(str));
		tabela.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				if (tecla.getExtendedKeyCode() == 40 || tecla.getExtendedKeyCode() == 38) {
					int posicao = tabela.getSelectedRow();
					grupo = listGrupo.get(posicao);
					PainelGrupoSubgrupo.carregarCampos(grupo);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent tecla) {
				int posicao = tabela.getSelectedRow();
				if (tecla.getExtendedKeyCode() == 40 || tecla.getExtendedKeyCode() == 38) {
					PainelGrupoSubgrupo.carregarCampos(grupo);
				} else if (tecla.getExtendedKeyCode() == 27) {// esc
					FrameInicial.getTxtfPesquisa().grabFocus();
				} else if (tecla.getExtendedKeyCode() == 10) {
					PainelGrupoSubgrupo.carregarCampos(grupo);
					funcaoSobrescrever();
					FrameInicial.getTabela().changeSelection(--posicao, 0, false, false);
					PainelGrupoSubgrupo.getTxtFNomeGrupo().grabFocus();
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
				PainelGrupoSubgrupo.irParaPoicao(posicao);
				System.out.println(tabela.getMouseListeners());
			}
		});
		colunas.add("Número");
		colunas.add("Grupo");
		colunas.add("Subgrupo");
		modelotabela.setColumnIdentifiers(colunas.toArray());
		for (int i = 0; i < listGrupo.size(); i++) {
			Object linha[] = { listGrupo.get(i).getSeqGrupo(), listGrupo.get(i).getNomeGrupo(),
					listGrupo.get(i).getNoAncora() };
			modelotabela.addRow(linha);
		}
		tabela.setShowGrid(true);
		tabela.setModel(modelotabela);
		return tabela;
	}

	// TODO Função sobrescrever
	public void funcaoSobrescrever() {
		System.out.println("ControlaGrupoSubgrupo.funcaoSobrescrever");
		ControlaBotoes.limparBtnSalvar();
		FrameInicial.getBtnSalvar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				grupo = PainelGrupoSubgrupo.lerCampos();
				if (!grupo.equals(null) & daoGrupo.alterar(grupo)) {
					FrameInicial.setTabela(pesqNomeTabela(grupo.getCodiGrupo()));
					FrameInicial.setPainelVisualiza(new PainelConta(grupo.getCodiGrupo()));
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

	// TODO Funcao excluir
	public boolean funcaoExcluir() {
		System.out.println("ControlaGrupoSubgrupo.excluir");
		grupo = PainelGrupoSubgrupo.lerCampos();
		if (daoGrupo.excluir(grupo)) {
			FrameInicial.limpaTela();
			funcaoCancelar();
			return true;
		} else {
			funcaoCancelar();
			return false;
		}
	}

	public JTable getTabela() {
		return tabela;
	}

	public void setTabela(JTable tabela) {
		this.tabela = tabela;
	}

	// TODO Pesquisa Grupo.
	public void iniciar() {
		System.out.println("FrameInicial.pesquisaGrupo");
		ControlaBotoes.limpaTodosBotoes();
		FrameInicial.limparTxtfPesquisa();
		ControlaBotoes.desHabilitaEdicaoBotoes();
		FrameInicial.getTxtfPesquisa().grabFocus();
		FrameInicial.setTabela(pesqNomeTabela(""));
		FrameInicial.setPainelVisualiza(new PainelGrupoSubgrupo(""));
		FrameInicial.atualizaTela();
		ActionListener acaoEditar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PainelGrupoSubgrupo.habilitaEdicao();
				ControlaBotoes.habilitaEdicaoBotoes();
				funcaoSobrescrever();
			}
		};
		FrameInicial.getBtnEditar().addActionListener(acaoEditar);

		FrameInicial.getBtnNovo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlaBotoes.habilitaNovoBotoes();
				PainelGrupoSubgrupo.habilitaNovo();
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
					getTabela().grabFocus();
					getTabela().changeSelection(0, 0, false, false);
				} else if (tecla.getExtendedKeyCode() == 27) {
					funcaoCancelar();
				} else {
					// System.out.println(tecla.getExtendedKeyCode());
					nome = FrameInicial.getTxtfPesquisa().getText();
					setTabela(pesqNomeTabela(nome));
					FrameInicial.setPainelVisualiza(new PainelGrupoSubgrupo(nome));
					FrameInicial.atualizaTela();
				}
			}

			@Override
			public void keyReleased(KeyEvent tecla) {
				nome = FrameInicial.getTxtfPesquisa().getText();
				setTabela(pesqNomeTabela(nome));
				FrameInicial.setPainelVisualiza(new PainelGrupoSubgrupo(nome));
				FrameInicial.atualizaTela();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
	}

	public List<GrupoSubgrupo> getListGrupo() {
		return listGrupo;
	}

	public void setListGrupo(List<GrupoSubgrupo> listGrupo) {
		this.listGrupo = listGrupo;
	}

	public DAOGrupoSubgrupo getDaoGrupo() {
		return daoGrupo;
	}

	public void setDaoGrupo(DAOGrupoSubgrupo daoGrupo) {
		this.daoGrupo = daoGrupo;
	}

	public GrupoSubgrupo getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoSubgrupo grupo) {
		this.grupo = grupo;
	}

}
