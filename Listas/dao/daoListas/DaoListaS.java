package daoListas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beansListas.CheckList;
import beansListas.Grupo;
import beansListas.ItemCheckList;
import utilListas.Conexao;

public class DaoListaS extends InterDAOListas {
	Connection c2;
	Conexao c;
	PreparedStatement callStm;
	List<Grupo> listGrupo;
	List<CheckList> listChkLists;
	List<ItemCheckList> listItens;
	CheckList checkList;
	Grupo grupo;
	ItemCheckList item;

	public DaoListaS() {
		c = new Conexao();
	}
	@Override
	public boolean addGrupo(String nomeGrupo) {
		c2 = (Connection) c.conectSQLite();
		try {
			callStm = c2.prepareStatement(
					"insert into tbl_grupos_chk_list (nome_grupo, desc_grupo)values(?,?);");
			callStm.setString(1, nomeGrupo);
			callStm.setString(2, nomeGrupo);
			callStm.execute();
			c.disconectSQLite();
			return true;

		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public Grupo buscaGrupo(String nomeGrupo) {
		c2 = (Connection) c.conectSQLite();
		try {
			callStm = c2.prepareStatement(
					"select * from tbl_grupos_chk_list where nome_grupo=?");
			callStm.setString(1, nomeGrupo);
			ResultSet rs = callStm.executeQuery();
			if (rs.first()) {
				grupo = new Grupo();
				grupo.setSeq(rs.getInt("nota"));
				grupo.setNome(rs.getString("nota"));
				grupo.setNome(rs.getString("descr"));
			}
			c.disconectSQLite();
			return grupo;
		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Grupo buscaGrupo(int seqGrupo) {
		c2 = (Connection) c.conectSQLite();
		try {
			callStm = c2.prepareStatement(
					"select * from tbl_grupos_chk_list where seq_grupo=?;");
			callStm.setInt(1, seqGrupo);
			ResultSet rs = callStm.executeQuery();
			if (rs.first()) {
				grupo = new Grupo();
				grupo.setSeq(rs.getInt("nota"));
				grupo.setNome(rs.getString("nota"));
				grupo.setNome(rs.getString("descr"));
			}
			c.disconectSQLite();
			return grupo;
		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Grupo> lerGrupos() {
		c2 = (Connection) c.conectSQLite();
		listGrupo = new ArrayList<Grupo>();
		try {
			callStm = c2.prepareStatement("select * from tbl_grupos_chk_list;");
			ResultSet rs = callStm.executeQuery();
			while (rs.next()) {
				grupo = new Grupo();
				grupo.setSeq(rs.getInt("seq_grupo"));
				grupo.setNome(rs.getString("nome_grupo"));
				grupo.setDescr(rs.getString("desc_grupo"));
				listGrupo.add(grupo);
			}
			c.disconectSQLite();
			return listGrupo;

		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void atualizaGrupo(Grupo g) {
		c2 = (Connection) c.conectSQLite();
		try {
			callStm = c2.prepareStatement(
					"update tbl_grupos_chk_list set nome_grupo=?, desc_grupo=? where seq_grupo=?;");
			callStm.setString(1, g.getNome());
			callStm.setString(2, g.getDescr());
			callStm.setInt(3, g.getSeq());
			callStm.execute();
			c.disconectSQLite();

		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();

		}

	}

	@Override
	public void remGrupo(Grupo grupo) {
		c2 = (Connection) c.conectSQLite();
		try {
			callStm = c2.prepareStatement(
					"delete from tbl_grupos_chk_list where seq_grupo=?;");
			callStm.setInt(1, grupo.getSeq());
			callStm.executeUpdate();
			c.disconectSQLite();
		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
		}

	}

	@Override
	public void addLista(CheckList chkList) {
		c2 = (Connection) c.conectSQLite();
		try {
			callStm = c2.prepareStatement(
					"insert into tbl_check_list (data_hora, nome_lista ) values (null,?);");
			callStm.setString(1, chkList.getNomeLista());
			callStm.execute();
			c.disconectSQLite();
		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
		}

	}

	@Override
	public List<CheckList> lerListas() {
		c2 = (Connection) c.conectSQLite();
		listChkLists = new ArrayList<CheckList>();
		try {
			callStm = c2.prepareCall("select* from tbl_check_list;");
			ResultSet rs = callStm.executeQuery();
			while (rs.next()) {
				checkList = new CheckList();
				checkList.setNumLista(rs.getInt("id_chk_list"));
				checkList.setNomeLista(rs.getString("nome_lista"));
				checkList.setNota(rs.getString("nota"));
				listChkLists.add(checkList);
			}
			c.disconectSQLite();
			return listChkLists;

		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CheckList> lerListas(int grupo) {
		c2 = (Connection) c.conectSQLite();
		listChkLists = new ArrayList<CheckList>();
		try {
			callStm = c2.prepareStatement(
					"select * from tbl_check_list where grupo=?;");
			callStm.setInt(1, grupo);
			ResultSet rs = callStm.executeQuery();
			while (rs.next()) {
				checkList = new CheckList();
				checkList.setNumLista(rs.getInt("id_chk_list"));
				checkList.setNomeLista(rs.getString("nome_lista"));
				checkList.setNota(rs.getString("nota"));
				listChkLists.add(checkList);
			}
			c.disconectSQLite();
			return listChkLists;

		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public CheckList carregarItens(CheckList checkList) {
		this.c2 = (Connection) c.conectSQLite();
		this.checkList = checkList;
		listItens = new ArrayList<ItemCheckList>();
		try {
			callStm = c2.prepareStatement(
					"select * from tbl_itens_chk_list where id_chk_list=?;");
			callStm.setInt(1, checkList.getNumLista());
			ResultSet rs = callStm.executeQuery();
			while (rs.next()) {
				item = new ItemCheckList();
				item.setIdLista(rs.getInt("id_chk_list"));
				item.setSeqItem(rs.getInt("seq_itm_chk_list"));
				item.setItem(rs.getString("itm_chk_list"));
				item.setConcluido(rs.getBoolean("concluido"));
				item.setObservacao(rs.getString("observacao"));
				listItens.add(item);

			}
			checkList.setListItens(listItens);
			c.disconectSQLite();
			return checkList;

		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void atualizaLista(CheckList checkList) {
		c2 = (Connection) c.conectSQLite();
		try {
			callStm = c2.prepareStatement(
					"update tbl_check_list  set nome_lista=? where id_chk_list=?; ");
			callStm.setString(1, checkList.getNomeLista());
			callStm.setInt(2, checkList.getNumLista());
			callStm.execute();
			c.disconectSQLite();

		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();

		}

	}

	@Override
	public void alteraGrupoLista(CheckList checkList) {
		c2 = (Connection) c.conectSQLite();
		try {
			callStm = c2.prepareStatement(
					"update tbl_check_list set grupo=? where id_chk_list=?;");
			callStm.setInt(1, checkList.getGrupo().getSeq());
			callStm.setInt(2, checkList.getNumLista());
			callStm.execute();
			c.disconectSQLite();

		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();

		}

	}

	@Override
	public boolean remLista(CheckList chkList) {
		c2 = (Connection) c.conectSQLite();
		try {
			callStm = c2.prepareStatement(
					"delete from tbl_check_list where id_chk_list=?;");
			callStm.setInt(1, chkList.getNumLista());
			callStm.executeUpdate();
			c.disconectSQLite();
			return true;
		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void addItemListaSeqLista(CheckList chkList, ItemCheckList item) {
		c2 = (Connection) c.conectSQLite();
		try {
			callStm = c2.prepareStatement(
					"insert into tbl_itens_chk_list (id_chk_list,itm_chk_list,observacao) values(?, ?, ?);");
			callStm.setInt(1, chkList.getNumLista());
			callStm.setString(2, item.getItem());
			callStm.setString(3, item.getObservacao());
			callStm.execute();
			c.disconectSQLite();
		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
		}

	}

	@Override
	public void atualizaItem(ItemCheckList item) {
		c2 = (Connection) c.conectSQLite();
		try {
			callStm = c2.prepareStatement(
					"update tbl_itens_chk_list set concluido=?,  itm_chk_list=?, observacao=? where seq_itm_chk_list=?;");
			callStm.setBoolean(1, item.isConcluido());
			callStm.setString(2, item.getItem());
			callStm.setString(3, item.getObservacao());
			callStm.setInt(4, item.getSeqItem());
			callStm.execute();
			c.disconectSQLite();

		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();

		}

	}

	@Override
	// Corrigir para SQLITE
	public List<ItemCheckList> lerItensListaNomeLista(String nomeLista) {
		c2 = (Connection) c.conectSQLite();
		listItens = new ArrayList<ItemCheckList>();
		try {
			callStm = c2
					.prepareStatement("{call ler_itens_lista_nome_lista(?)}");
			callStm.setString(1, nomeLista);
			ResultSet rs = callStm.executeQuery();
			while (rs.next()) {
				item = new ItemCheckList();
				item.setIdLista(rs.getInt("id_chk_list"));
				item.setSeqItem(rs.getInt("seq_itm_chk_list"));
				item.setItem(rs.getString("itm_chk_list"));
				item.setConcluido(rs.getBoolean("concluido"));
				item.setObservacao(rs.getString("observacao"));
				listItens.add(item);
			}
			c.disconectSQLite();
			return listItens;

		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ItemCheckList> lerItensListaSeq(int numLista) {
		c2 = (Connection) c.conectSQLite();
		listItens = new ArrayList<ItemCheckList>();
		try {
			callStm = c2.prepareStatement(
					"select * from tbl_itens_chk_list where id_chk_list=?;");
			callStm.setInt(1, numLista);
			ResultSet rs = callStm.executeQuery();
			while (rs.next()) {
				item = new ItemCheckList();
				item.setIdLista(rs.getInt("id_chk_list"));
				item.setSeqItem(rs.getInt("seq_itm_chk_list"));
				item.setItem(rs.getString("itm_chk_list"));
				item.setConcluido(rs.getBoolean("concluido"));
				item.setObservacao(rs.getString("observacao"));
				listItens.add(item);

			}
			c.disconectSQLite();
			return listItens;

		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ItemCheckList> lerItensListaSeq(int numLista, boolean status) {
		c2 = (Connection) c.conectSQLite();
		listItens = new ArrayList<ItemCheckList>();
		try {
			callStm = c2.prepareStatement(
					"select * from tbl_itens_chk_list where id_chk_list=? and concluido=?;");
			callStm.setInt(1, numLista);
			callStm.setBoolean(2, status);
			ResultSet rs = callStm.executeQuery();
			while (rs.next()) {
				item = new ItemCheckList();
				item.setIdLista(rs.getInt("id_chk_list"));
				item.setSeqItem(rs.getInt("seq_itm_chk_list"));
				item.setItem(rs.getString("itm_chk_list"));
				item.setConcluido(rs.getBoolean("concluido"));
				item.setObservacao(rs.getString("observacao"));
				listItens.add(item);

			}
			c.disconectSQLite();
			return listItens;

		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void remItem(ItemCheckList item) {
		c2 = (Connection) c.conectSQLite();
		try {
			callStm = c2.prepareStatement(
					"delete from tbl_itens_chk_list where seq_itm_chk_list=?;");
			callStm.setInt(1, item.getSeqItem());
			callStm.executeUpdate();
			c.disconectSQLite();
		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
		}

	}

	@Override
	public String lerNota(int numLista) {
		c2 = (Connection) c.conectSQLite();
		String nota = null;
		try {
			callStm = c2.prepareStatement(
					"select nota from tbl_check_list where id_chk_list=?;");
			callStm.setInt(1, numLista);
			ResultSet rs = callStm.executeQuery();
			if (rs.next()) {
				nota = rs.getString("nota");
			}
			c.disconectSQLite();
			return nota;

		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String lerNota(String nomeLista) {
		c2 = (Connection) c.conectSQLite();
		String nota = null;
		try {
			callStm = c2.prepareStatement(
					"select nota from tbl_check_list where nome_lista=?;");
			callStm.setString(1, nomeLista);
			ResultSet rs = callStm.executeQuery();
			if (rs.first()) {
				nota = rs.getString("nota");
			}
			c.disconectSQLite();
			return nota;

		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void atualizaNota(int numLista, String nota) throws SQLException {
		c2 = (Connection) c.conectSQLite();
		callStm = c2.prepareStatement(
				"update tbl_check_list set nota=? where id_chk_list=?;");
		callStm.setString(1, nota);
		callStm.setInt(2, numLista);
		callStm.execute();
		c.disconectSQLite();

	}

	@Override
	public void atualizaNota(String nomeLista, String nota) {
		c2 = (Connection) c.conectSQLite();
		try {
			callStm = c2.prepareStatement(
					"update tbl_check_list set nota=? where nome_lista=?;");
			callStm.setString(1, nomeLista);
			callStm.setString(2, nota);
			callStm.executeQuery();
			c.disconectSQLite();
		} catch (SQLException e) {
			c.disconectSQLite();
			e.printStackTrace();
		}

	}

	@Override
	public void addListaFireBase(CheckList chkList) {
		// TODO Auto-generated method stub

	}

}
