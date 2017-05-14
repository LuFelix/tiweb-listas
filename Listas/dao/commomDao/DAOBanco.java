package commomDao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import commonUtils.Conexao;
import commonUtils.ConfigS;

public class DAOBanco {
	List<String> listTabelas;
	Conexao c;

	public DAOBanco() {
		c = new Conexao(ConfigS.getBd(), ConfigS.getLocal(), ConfigS.getPorta(), ConfigS.getBanco2(), ConfigS.getUser(),
				ConfigS.getSenha());
	}

	public List<String> listaTabelas() {
		c.conectar();
		listTabelas = new ArrayList<String>();
		String sql = "{call lista_tabelas()}";
		try {

			CallableStatement cStm = c.getCon().prepareCall(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = cStm.executeQuery();

			while (rs.next()) {
				listTabelas.add(rs.getString(1));
			}
			c.desconectar();
			return listTabelas;
		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}

	}

	public boolean limpaTabela(String nomeTabela) {
		c.conectar();
		String sql = "{call limpa_tabela(?)}";

		boolean executou = false;
		try {
			CallableStatement cStm = c.getCon().prepareCall(sql);
			cStm.setString(1, nomeTabela);
			executou = cStm.execute();
			c.desconectar();
			return executou;
		} catch (SQLException e) {

			e.printStackTrace();
			c.desconectar();
			return executou;
		}

	}

}
