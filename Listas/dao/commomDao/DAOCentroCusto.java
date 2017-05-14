package commomDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import commomBeans.CentroCusto;
import commonUtils.Conexao;
import commonUtils.ConexaoSTM;
import commonUtils.ConfigS;

public class DAOCentroCusto {
	private Conexao c;
	private ConexaoSTM c2;
	private ResultSet result;
	private PreparedStatement prepStm;
	private List<CentroCusto> listCentroCusto;
	private CentroCusto centroCusto;

	public DAOCentroCusto() {
		super();
		System.out.println("DAOCentroCusto.construtor");
		c = new Conexao(ConfigS.getBd(), ConfigS.getLocal(), ConfigS.getPorta(), ConfigS.getBanco1(), ConfigS.getUser(),
				ConfigS.getSenha());
		c2 = new ConexaoSTM(ConfigS.getBd(), ConfigS.getLocal(), ConfigS.getPorta(), ConfigS.getBanco1(),
				ConfigS.getUser(), ConfigS.getSenha());
	}

	public void reservaCodigo(String codigo) throws SQLException {
		// TODO Reserva um código livre na tabela de grupos para cadastrar
		String sql = "insert into contas_centro_custo (codi_centro_custo) values (?)";
		c.conectar();
		prepStm = c.getCon().prepareStatement(sql);
		prepStm.setString(1, codigo);
		prepStm.executeUpdate();
		c.desconectar();
	}

	public CentroCusto buscaNome(String nome) {
		String sql = "select * from contas_centro_custo where nome_centro_custo =?;";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, nome);
			result = prepStm.executeQuery();
			centroCusto = new CentroCusto();
			if (result.next()) {
				centroCusto.setSeqcentroCusto(result.getInt("seq_centro_custo"));
				centroCusto.setCodiCentroCusto(result.getString("codi_centro_custo"));
				centroCusto.setNomeCentroCusto(result.getString("nome_centro_custo"));
				centroCusto.setDescCentroCusto(result.getString("desc_centro_custo"));
				c.desconectar();
				return centroCusto;
			} else {
				c.desconectar();
				return null;
			}
		} catch (SQLException e) {
			c.desconectar();
			e.printStackTrace();
			return null;
		}
	}

	public CentroCusto buscaCodigo(String codigo) {
		String sql = "select * from contas_centro_custo where codi_centro_custo =?;";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, codigo);
			result = prepStm.executeQuery();
			if (result.next()) {
				centroCusto = new CentroCusto();
				centroCusto.setSeqcentroCusto(result.getInt("seq_centro_custo"));
				centroCusto.setCodiCentroCusto(result.getString("codi_centro_custo"));
				centroCusto.setNomeCentroCusto(result.getString("nome_centro_custo"));
				centroCusto.setDescCentroCusto(result.getString("desc_centro_custo"));
				c.desconectar();
				return centroCusto;
			} else {
				return null;
			}
		} catch (SQLException e) {
			c.desconectar();
			e.printStackTrace();
			return null;
		}

	}

	public boolean cadastrar(CentroCusto centroCusto) {
		System.out.println("DAOCentroCusto.cadastrar");
		String sql = "insert into contas_centro_custo (codi_centro_custo, nome_centro_custo, desc_centro_custo) values (?,?,?);";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, centroCusto.getCodiCentroCusto());
			prepStm.setString(2, centroCusto.getNomeCentroCusto());
			prepStm.setString(3, centroCusto.getDescCentroCusto());
			prepStm.execute();
			c.desconectar();
			System.out.println("Inseriu");
			return true;
		} catch (SQLException e) {
			c.desconectar();
			e.printStackTrace();
			return false;
		}
	}

	public int consultaUltimo() {
		System.out.println("DAOCentroCusto.consultarUltimo");
		c2.conectStm();
		result = c2.query("SELECT max(seq_centro_custo) FROM contas_centro_custo;");
		c2.disconect();
		try {
			if (result.next()) {
				return result.getInt(1);
			} else {
				return 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<CentroCusto> pesquisarString(String str) {
		System.out.println("DAOCentroCusto.pesquisarString");
		String sql = "select * from contas_centro_custo where codi_centro_custo ~* ? or nome_centro_custo ~* ? or desc_centro_custo ~* ? order by nome_centro_custo;";
		listCentroCusto = new ArrayList<CentroCusto>();
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			prepStm.setString(1, str);
			prepStm.setString(2, str);
			prepStm.setString(3, str);
			ResultSet res = prepStm.executeQuery();
			while (res.next()) {
				centroCusto = new CentroCusto();
				centroCusto.setSeqcentroCusto(res.getInt("seq_centro_custo"));
				centroCusto.setCodiCentroCusto(res.getString("codi_centro_custo"));
				centroCusto.setNomeCentroCusto(res.getString("nome_centro_custo"));
				centroCusto.setDescCentroCusto(res.getString("desc_centro_custo"));
				listCentroCusto.add(centroCusto);
			}
			c.desconectar();
			return listCentroCusto;
		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}

	}

	public boolean alterar(CentroCusto centroCusto) {
		System.out.println("DAOGrupoSubgrupo.alterar");
		String sql = "update contas_centro_custo set  nome_centro_custo=?, desc_centro_custo=?"
				+ " where codi_centro_custo=?;";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, centroCusto.getNomeCentroCusto());
			prepStm.setString(2, centroCusto.getDescCentroCusto());
			prepStm.setString(3, centroCusto.getCodiCentroCusto());
			prepStm.executeUpdate();
			c.desconectar();
			return true;
		} catch (Exception e) {
			c.desconectar();
			e.printStackTrace();
			return false;
		}
	}

	public boolean excluir(CentroCusto centroCusto) {
		System.out.println("DAOGrupoSubgrupo.excluir");
		String sql = "delete from contas_centro_custo where codi_centro_custo=?;";
		c.conectar();
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, centroCusto.getCodiCentroCusto());
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
