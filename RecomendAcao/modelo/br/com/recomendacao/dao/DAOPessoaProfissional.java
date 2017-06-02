package br.com.recomendacao.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.recomendacao.beans.PessoaProfissional;
import br.com.recomendacao.util.Conexao;

public class DAOPessoaProfissional {
	PessoaProfissional pPro;
	Conexao c;
	List<PessoaProfissional> listPro;

	public DAOPessoaProfissional() {
		pPro = new PessoaProfissional();
		c = new Conexao("MariaDB", "contab");

	}

	public List<PessoaProfissional> leFuncaoCodigo(String codiPessoa) {
		c.conectar();
		String sql = "{call lista_funcoes_codi_pess(?)}";
		listPro = new ArrayList<PessoaProfissional>();
		try {
			CallableStatement cStm = c.getCon().prepareCall(sql);
			cStm.setString(1, codiPessoa);
			ResultSet rs = cStm.executeQuery();
			while (rs.next()) {
				pPro = new PessoaProfissional();
				pPro.setCodiPess(rs.getString("codi_pess"));
				pPro.setCodiProf(rs.getString("codi_prof"));
				pPro.setNomeProf(rs.getString("nome_prof"));
				pPro.setDocFunc(rs.getString("doc_func"));
				pPro.setPis(rs.getInt("pis"));
				pPro.setOptante(rs.getBoolean("optante"));
				listPro.add(pPro);
			}
			c.desconectar();
			return listPro;
		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return null;
		}

	}
	public void criarFuncao(PessoaProfissional pp) {
		c.conectar();
		String sql = "{call cadastra_funcao(?,?,?,?,?,?)}";

		try {
			CallableStatement cStm = c.getCon().prepareCall(sql);
			cStm.setString(1, pp.getCodiPess());
			cStm.setString(2, pp.getCodiProf());
			cStm.setString(3, pp.getNomeProf());
			cStm.setString(4, pp.getDocFunc());
			cStm.setInt(5, pp.getPis());
			cStm.setBoolean(6, pp.isOptante());
			cStm.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public void atualizar(PessoaProfissional pp) {
		c.conectar();
		String sql = "{call atualiza_ocupacao(?,?,?,?,?)}";
		try {
			CallableStatement cStm = c.getCon().prepareCall(sql);
			cStm.setString(1, pp.getNomeProf());
			cStm.setString(2, pp.getDocFunc());
			cStm.setInt(3, pp.getPis());
			cStm.setBoolean(4, pp.isOptante());
			cStm.setString(5, pp.getCodiPess());
			cStm.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public void apagar() {
	}
}
