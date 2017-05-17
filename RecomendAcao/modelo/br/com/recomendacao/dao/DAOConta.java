package br.com.recomendacao.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.recomendacao.beans.Conta;
import br.com.recomendacao.beans.Lancamento;
import br.com.recomendacao.util.Conexao;
import br.com.recomendacao.util.ConexaoSTM;

public class DAOConta {
	private Conexao c;
	private ConexaoSTM c2;
	private ResultSet result;
	private PreparedStatement prepStm;
	private Conta conta;
	private boolean first;
	private List<Conta> listConta;
	private List<Lancamento> listMov;
	private Lancamento mov;

	public DAOConta() {
		System.out.println("DAOConta.construtor");
		c = new Conexao(ConfigS.getBdPg(), "siacecf");
		c2 = new ConexaoSTM(ConfigS.getBdPg(), ConfigS.getLocal(),
				ConfigS.getPortaPgDB(), ConfigS.getBanco1(),
				ConfigS.getUserPgDB(), ConfigS.getSenhaPgDB());
	}

	public void reservaCodigo(String codigo) throws SQLException {
		// TODO Reserva um código livre na tabela de pedidos para cadastrar
		String sql = "insert into conta (codi_conta) values (?)";
		c.conectar();
		prepStm = c.getCon().prepareStatement(sql);
		prepStm.setString(1, codigo);
		prepStm.executeUpdate();
		c.desconectar();
	}

	public boolean cadastrar(Conta conta) {
		String sql = "insert into contas (codi_conta, agencia, conta, banco, nome_conta, "
				+ "titular, num_cartao, senha, desc_conta, obs_conta, tipo_conta, codi_centro_custo) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, conta.getCodiConta());
			prepStm.setString(2, conta.getAgencia());
			prepStm.setString(3, conta.getConta());
			prepStm.setString(4, conta.getBanco());
			prepStm.setString(5, conta.getNomeConta());
			prepStm.setString(6, conta.getTiular());
			prepStm.setString(7, conta.getNumCartao());
			prepStm.setString(8, conta.getSenha());
			prepStm.setString(9, conta.getDescConta());
			prepStm.setString(10, conta.getObsConta());
			prepStm.setString(11, conta.getTipoConta());
			prepStm.setString(12, conta.getCentroCusto());
			prepStm.executeUpdate();
			c.desconectar();
			return true;
		} catch (SQLException e) {
			c.desconectar();
			e.printStackTrace();
			return false;
		}
	}

	public int consultaUltimo() {
		System.out.println("DAOConta.consultarUltimo");
		c2.conectStm();
		result = c2.query("SELECT max(seq_conta) FROM contas;");
		c2.disconect();
		try {
			if (result.next()) {
				System.out.println("Está retornando: " + result.getInt(1));
				return result.getInt(1);
			} else {
				return 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<Conta> pesquisarString(String str) {
		System.out.println("DAOConta.pesquisarString");
		String sql = "select * from contas where codi_conta ~* ? or agencia ~* ? or conta ~* ? or banco = ? or nome_conta ~* ? order by nome_conta;";
		listConta = new ArrayList<Conta>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prepStm.setString(1, str);
			prepStm.setString(2, str);
			prepStm.setString(3, str);
			prepStm.setString(4, str);
			prepStm.setString(5, str);
			ResultSet res = prepStm.executeQuery();
			while (res.next()) {
				conta = new Conta();
				conta.setSeqConta(res.getInt("seq_conta"));
				conta.setCodiConta(res.getString("codi_conta"));
				conta.setAgencia(res.getString("agencia"));
				conta.setConta(res.getString("conta"));
				conta.setBanco(res.getString("banco"));
				conta.setNomeConta(res.getString("nome_conta"));
				conta.setTiular(res.getString("titular"));
				conta.setNumCartao(res.getString("num_cartao"));
				conta.setSenha(res.getString("senha"));
				conta.setDescConta(res.getString("desc_conta"));
				conta.setObsConta(res.getString("obs_conta"));
				conta.setTipoConta(res.getString("tipo_conta"));
				conta.setCentroCusto(res.getString("codi_centro_custo"));
				listConta.add(conta);
			}
			c.desconectar();
			return listConta;
		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}
	}

	public boolean alterar(Conta conta) {
		System.out.println("DAOConta.alterar");
		String sql = "update contas set  agencia=?, conta=?, banco=?, nome_conta=?, titular=?, "
				+ "num_cartao=?, senha=?, desc_conta=?, obs_conta=?,tipo_conta=?,codi_centro_custo=?"
				+ "  where codi_conta =?;";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, conta.getAgencia());
			prepStm.setString(2, conta.getConta());
			prepStm.setString(3, conta.getBanco());
			prepStm.setString(4, conta.getNomeConta());
			prepStm.setString(5, conta.getTiular());
			prepStm.setString(6, conta.getNumCartao());
			prepStm.setString(7, conta.getSenha());
			prepStm.setString(8, conta.getDescConta());
			prepStm.setString(9, conta.getObsConta());
			prepStm.setString(10, conta.getTipoConta());
			prepStm.setString(11, conta.getCentroCusto());
			prepStm.setString(12, conta.getCodiConta());
			prepStm.executeUpdate();
			c.desconectar();
			return true;
		} catch (Exception e) {
			c.desconectar();
			e.printStackTrace();
			return false;
		}
	}

	public boolean excluir(Conta conta) {
		System.out.println("DAOConta.excluir");
		String sql = "delete from contas where codi_conta=?;";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, conta.getCodiConta());
			prepStm.executeUpdate();
			c.desconectar();
			return true;
		} catch (Exception e) {
			c.desconectar();
			e.printStackTrace();
			return false;
		}
	}

}
