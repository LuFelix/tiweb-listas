package br.com.recomendacao.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.recomendacao.beans.Pessoa;
import br.com.recomendacao.util.Conexao;
import br.com.recomendacao.util.ConexaoSTM;

public class DAOPessoaPG implements InterDaoPessoa {
	private Conexao c;
	private ConexaoSTM c2;
	private ResultSet result;
	private PreparedStatement prepStm;
	private Pessoa p;
	private String sql;
	List<Pessoa> listP;

	public DAOPessoaPG() {
		System.out.println("DAOUsuario.construtor");
		c = new Conexao(ConfigS.getBdPg());
		c2 = new ConexaoSTM(ConfigS.getBdPg(), ConfigS.getLocal(),
				ConfigS.getPortaPgDB(), ConfigS.getBanco1(),
				ConfigS.getUserPgDB(), ConfigS.getSenhaPgDB());
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

	public void reservaCodigo(String codiPessoa) throws SQLException {
		// TODO Reserva um código livre na tabela de pedidos para cadastrar
		sql = "insert into pessoas (codi_pessoa) values (?)";
		c.conectar();
		prepStm = c.getCon().prepareStatement(sql);
		prepStm.setString(1, codiPessoa);
		prepStm.executeUpdate();
		c.desconectar();
	}

	public boolean cadastrar(Pessoa p) {
		sql = "insert into pessoas (codi_pessoa, nome_pessoa, email_pessoa, tipo_pessoa, codi_grupo) values (?,?,?,?,?)";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, p.getCodiPessoa());
			prepStm.setString(2, p.getNome());
			prepStm.setString(3, p.getEmail());
			prepStm.setString(4, p.getTipoPessoa());
			prepStm.setString(5, p.getRelacao());
			prepStm.executeUpdate();
			c.desconectar();
			return true;
		} catch (SQLException e) {
			c.desconectar();
			e.printStackTrace();
			return false;
		}

	}
	public boolean alterar(Pessoa u) {
		System.out.println("DAOUsuario.alterar");
		sql = "update pessoas set nome_pessoa=?, cnpj_cpf_pessoa=?, email_pessoa=?, codi_grupo=? where codi_pessoa =?;";
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
		sql = "delete from pessoas where codi_pessoa=?;";
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
	public Pessoa pesquisar(String nome) {
		sql = "select * from pessoas where nome like ?;";
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
	// Pesquisa por relação (clientes, fornecedores, etc.)
	public List<Pessoa> pesquisarRelacao(String str) {
		System.out.println("DAOUsuario.pesquisarRelacao");
		sql = "select * from pessoas where codi_grupo = ?;";
		listP = new ArrayList<Pessoa>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
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
				listP.add(p);
			}

			c.desconectar();
			return listP;
		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}
	}

	public List<Pessoa> listarTodos() {
		sql = "select * from pessoas order by nome_pessoa;";
		listP = new ArrayList<Pessoa>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			result = prepStm.executeQuery();
			while (result.next()) {
				p = new Pessoa();
				p.setSeqUsuario(result.getInt("seq_pessoa"));
				p.setNome(result.getString("nome_pessoa"));
				p.setCpf(result.getString("cnpj_cpf_pessoa"));
				p.setEmail(result.getString("email_pessoa"));
				p.setRelacao(result.getString("codi_grupo"));
				p.setTipoPessoa(result.getString("tipo_pessoa"));
				listP.add(p);
			}
			c.desconectar();
			return listP;
		} catch (SQLException e) {
			c.desconectar();
			e.printStackTrace();
			return null;
		}

	}

	public List<Pessoa> pesquisarString(String nome) {
		System.out.println("DAOUsuario.pesquisarNome");
		sql = "select * from pessoas where nome_pessoa ~* ? or cnpj_cpf_pessoa ~* ? or email_pessoa ~* ? or codi_pessoa = ? order by nome_pessoa;";
		listP = new ArrayList<Pessoa>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
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
				listP.add(p);
			}

			c.desconectar();
			return listP;
		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}
	}

}
