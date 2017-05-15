package br.com.simprovendas.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.simprovendas.beans.Pessoa;
import br.com.simprovendas.util.Conexao;
import br.com.simprovendas.util.ConexaoSTM;

public class DAOPessoa {
	private Conexao c;
	private ConexaoSTM c2;
	private ResultSet result;
	private PreparedStatement prepStm;
	private Pessoa p;
	private boolean first;
	private ArrayList<Pessoa> pesqU;

	public DAOPessoa() {
		System.out.println("DAOUsuario.construtor");
		c = new Conexao(ConfigS.getBdPg(), ConfigS.getLocal(), ConfigS.getPortaPgDB(), ConfigS.getBanco1(), ConfigS.getUserPgDB(),
				ConfigS.getSenhaPgDB());
		c2 = new ConexaoSTM(ConfigS.getBdPg(), ConfigS.getLocal(), ConfigS.getPortaPgDB(), ConfigS.getBanco1(),
				ConfigS.getUserPgDB(), ConfigS.getSenhaPgDB());
	}

	public void reservaCodigo(String codiPessoa) throws SQLException {
		// TODO Reserva um código livre na tabela de pedidos para cadastrar
		String sql = "insert into pessoas (codi_pessoa) values (?)";
		c.conectar();
		prepStm = c.getCon().prepareStatement(sql);
		prepStm.setString(1, codiPessoa);
		prepStm.executeUpdate();
		c.desconectar();
	}

	public boolean cadastrar(Pessoa u) {
		String sql = "insert into pessoas (codi_pessoa, nome_pessoa, email_pessoa, tipo_pessoa, codi_grupo) values (?,?,?,?,?)";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, u.getCodiPessoa());
			prepStm.setString(2, u.getNome());
			prepStm.setString(3, u.getEmail());
			prepStm.setString(4, u.getTipoPessoa());
			prepStm.setString(5, u.getRelacao());
			prepStm.executeUpdate();
			c.desconectar();
			return true;
		} catch (SQLException e) {
			c.desconectar();
			e.printStackTrace();
			return false;
		}

	}

	public Pessoa pesquisar(String nome) {
		String sql = "select * from pessoas where nome like ?;";
		p = new Pessoa();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, nome);
			result = prepStm.executeQuery();
			p.setSeqUsuario((result.getInt("seq_pessoa")));
			p.setNome(result.getString("nome_pessoa"));
			p.setCpf(result.getString("cnpj_cpf_pessoa"));
			p.setEmail(result.getString("email_pessoa"));
			p.setTipoPessoa(result.getString("tipo_pessoa"));
			p.setTipoPessoa(result.getString("codi_grupo"));
			c.desconectar();
			return p;
		} catch (SQLException e) {
			c.desconectar();
			e.printStackTrace();
			return null;
		}
	}
	//Pesquisa por relação (clientes, fornecedores, etc.)
	public ArrayList<Pessoa> pesquisarRelacao(String str) {
		System.out.println("DAOUsuario.pesquisarRelacao");
		String sql = "select * from pessoas where codi_grupo = ?;";
		pesqU = new ArrayList<Pessoa>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			prepStm.setString(1, str);
			result = prepStm.executeQuery();
			while (result.next()) {
				p = new Pessoa();
				p.setSeqUsuario((result.getInt("seq_pessoa")));
				p.setCodiPessoa(result.getString("codi_pessoa"));
				p.setNome(result.getString("nome_pessoa"));
				p.setCpf(result.getString("cnpj_cpf_pessoa"));
				p.setEmail(result.getString("email_pessoa"));
				p.setRelacao(result.getString("codi_grupo"));
				p.setTipoPessoa(result.getString("tipo_pessoa"));
				pesqU.add(p);
			}

			c.desconectar();
			return pesqU;
		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}
	}
	
	public int consultaUltimo() {
		c2.conectStm();
		result = c2.query("SELECT max(seq_pessoa) FROM pessoas;");
		c2.disconect();
		try {
			result.next();
			return result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	public ArrayList<Pessoa> listarTodos() {
		String sql = "select * from pessoas order by nome_pessoa; ";
		pesqU = new ArrayList<Pessoa>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			result = prepStm.executeQuery();
			while (result.next()) {
				p = new Pessoa();
				p.setSeqUsuario(result.getInt("seq_pessoa"));
				p.setNome(result.getString("nome_pessoa"));
				p.setCpf(result.getString("cnpj_cpf_pessoa"));
				p.setEmail(result.getString("email_pessoa"));
				p.setRelacao(result.getString("codi_grupo"));
				p.setTipoPessoa(result.getString("tipo_pessoa"));
				pesqU.add(p);
			}
			c.desconectar();
			return pesqU;
		} catch (SQLException e) {
			c.desconectar();
			e.printStackTrace();
			return null;
		}

	}

	public ArrayList<Pessoa> pesquisarNome(String nome) {
		System.out.println("DAOUsuario.pesquisarNome");
		String sql = "select * from pessoas where nome_pessoa ~* ? or cnpj_cpf_pessoa ~* ? or email_pessoa ~* ? or codi_pessoa = ? order by nome_pessoa;";
		pesqU = new ArrayList<Pessoa>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			prepStm.setString(1, nome);
			prepStm.setString(2, nome);
			prepStm.setString(3, nome);
			prepStm.setString(4, nome);
			result = prepStm.executeQuery();
			while (result.next()) {
				p = new Pessoa();
				p.setSeqUsuario((result.getInt("seq_pessoa")));
				p.setCodiPessoa(result.getString("codi_pessoa"));
				p.setNome(result.getString("nome_pessoa"));
				p.setCpf(result.getString("cnpj_cpf_pessoa"));
				p.setEmail(result.getString("email_pessoa"));
				p.setRelacao(result.getString("codi_grupo"));
				p.setTipoPessoa(result.getString("tipo_pessoa"));
				pesqU.add(p);
			}

			c.desconectar();
			return pesqU;
		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}
	}

	public boolean alterar(Pessoa u) {
		System.out.println("DAOUsuario.alterar");
		String sql = "update pessoas set nome_pessoa=?, cnpj_cpf_pessoa=?, email_pessoa=?, codi_grupo=? where codi_pessoa =?;";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, u.getNome());
			prepStm.setString(2, u.getCpf());
			prepStm.setString(3, u.getEmail());
			prepStm.setString(4, u.getRelacao());
			prepStm.setString(5, u.getCodiPessoa());
			prepStm.execute();
			c.desconectar();
			return true;
		} catch (Exception e) {
			c.desconectar();
			e.printStackTrace();
			return false;
		}
	}

	public boolean excluir(Pessoa u) {
		System.out.println("DAOUsuario.excluir");
		String sql = "delete from pessoas where codi_pessoa=?;";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, u.getCodiPessoa());
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
