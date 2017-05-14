package daoListas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import beansListas.CheckList;
import beansListas.ItemCheckList;
import utilListas.Conexao;

public class DaoLista {
	Connection c2;
	Conexao c;
	java.sql.CallableStatement callStm;
	List<CheckList> listChkLists;
	List<String> listFunc;
	List<ItemCheckList> listItens;
	CheckList chkList;
	ItemCheckList item;

	public DaoLista() {
		c = new Conexao();

	}

	/**
	 * Método retorna um Array contendo as listas sem os ítens.
	 * 
	 * @return List <CheckList>
	 */
	public List<CheckList> carregaListas() {
		c2 = (Connection) c.conect();
		listChkLists = new ArrayList<CheckList>();
		try {
			callStm = c2.prepareCall("{call listar_chk_list()}");
			ResultSet rs = callStm.executeQuery();
			while (rs.next()) {
				chkList = new CheckList();
				chkList.setNumLista(rs.getInt("id_chk_list"));
				chkList.setNomeLista(rs.getString("nome_lista"));
				chkList.setNota(rs.getString("nota"));
				listChkLists.add(chkList);
			}
			c.disconect();
			return listChkLists;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}

	}

	public List<String> carregaFunc() {
		c2 = (Connection) c.conect();
		listFunc = new ArrayList<String>();
		String nome = null;
		try {
			callStm = c2.prepareCall("{call listar_todos_funcionarios()}");
			ResultSet rs = callStm.executeQuery();
			int i = 0;
			while (rs.next()) {
				nome = rs.getString("nome_funcionario");
				listFunc.add(nome);
				i++;
			}
			c.disconect();
			return listFunc;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Método recebe um inteiro com o número da lista para carregar os ítens a
	 * ela pertinentes.
	 * 
	 * @param numLista
	 * 
	 * @return List
	 */
	public List<ItemCheckList> carregarItens(int numLista) {
		c2 = (Connection) c.conect();
		listItens = new ArrayList<ItemCheckList>();
		try {
			callStm = c2.prepareCall("{call exibir_lista_id(?)}");
			callStm.setInt(1, numLista);
			ResultSet rs = callStm.executeQuery();

			int i = 0;
			while (rs.next()) {
				item = new ItemCheckList();
				item.setIdLista(rs.getInt("id_chk_list"));
				item.setSeqItem(rs.getInt("seq_itm_chk_list"));
				item.setItem(rs.getString("itm_chk_list"));
				item.setConcluido(rs.getBoolean("concluido"));
				item.setObservacao(rs.getString("observacao"));
				listItens.add(item);
				// System.out.println(item.getSeqItem() + " ==> " +
				// item.getItem());
				i++;

			}
			c.disconect();
			return listItens;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}

	}

	public List<ItemCheckList> carregarItens(CheckList checkList) {
		c2 = (Connection) c.conect();
		listItens = new ArrayList<ItemCheckList>();
		try {
			callStm = c2.prepareCall("{call exibir_lista_id(?)}");
			callStm.setInt(1, checkList.getNumLista());
			ResultSet rs = callStm.executeQuery();

			int i = 0;
			while (rs.next()) {
				item = new ItemCheckList();
				item.setIdLista(rs.getInt("id_chk_list"));
				item.setSeqItem(rs.getInt("seq_itm_chk_list"));
				item.setItem(rs.getString("itm_chk_list"));
				item.setConcluido(rs.getBoolean("concluido"));
				item.setObservacao(rs.getString("observacao"));
				listItens.add(item);
				// System.out.println(item.getSeqItem() + " ==> " +
				// item.getItem());
				i++;

			}
			c.disconect();
			return listItens;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Recebe uma String com o nome da lista e retorna um ArrayList com os ítens
	 * 
	 * @param String
	 *            -- nomeLista
	 * @return ArrayList<ItemCheckList>
	 */
	public List<ItemCheckList> carregarItens(String nomeLista) {
		c2 = (Connection) c.conect();
		listItens = new ArrayList<ItemCheckList>();
		try {
			callStm = c2.prepareCall("{call exibir_lista_nome(?)}");
			callStm.setString(1, nomeLista);
			ResultSet rs = callStm.executeQuery();

			int i = 0;
			while (rs.next()) {
				item = new ItemCheckList();
				item.setIdLista(rs.getInt("id_chk_list"));
				item.setSeqItem(rs.getInt("seq_itm_chk_list"));
				item.setItem(rs.getString("itm_chk_list"));
				item.setConcluido(rs.getBoolean("concluido"));
				item.setObservacao(rs.getString("observacao"));
				listItens.add(item);
				// System.out.println(item.getSeqItem() + " ==> " +
				// item.getItem());
				i++;

			}
			c.disconect();
			return listItens;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Adiciona uma nova lista na tabela
	 * 
	 * @param CheckList
	 *            checkList
	 */

	public void addLista(CheckList chkList) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call nova_chk_list(?)}");
			callStm.setString(1, chkList.getNomeLista());
			callStm.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Remove uma lista da tabela
	 * 
	 * @param chkList
	 */
	public void remLista(CheckList chkList) {
		// System.out.println("DaoLista.remLista");
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call rem_lista(?)}");
			callStm.setInt(1, chkList.getNumLista());
			callStm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Adiciona um ítem na lista
	 * 
	 * @param chkList
	 * @param item
	 */
	public void addItem(CheckList chkList, ItemCheckList item) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call add_item(?,?,?)}");
			callStm.setInt(1, chkList.getNumLista());
			callStm.setString(2, item.getItem());
			callStm.setString(3, item.getObservacao());
			callStm.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Remove um ítem da lista recebendo a lista e o ítem a ser removido
	 * 
	 * @param CheckList
	 *            -- chkList
	 * @param ItemCheckList
	 *            -- item
	 */
	public void removeItem(ItemCheckList item) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call rem_item(?)}");
			callStm.setInt(1, item.getSeqItem());
			callStm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Grava um nota na lista recebendo o id da lista e a nota a ser gravada
	 * 
	 * @param int
	 *            -- numList
	 * @param String
	 *            -- nota
	 */
	public void gravaNota(int numLista, String nota) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call atualiza_nota_id(?,?)}");
			callStm.setInt(1, numLista);
			callStm.setString(2, nota);
			callStm.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Grava um nota na lista recebendo o id da lista e a nota a ser gravada
	 * 
	 * @param int
	 *            -- numList
	 * @param String
	 *            -- nota
	 */
	public void gravaNota(String nomeLista, String nota) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call atualiza_nota_nome(?,?)}");
			callStm.setString(1, nomeLista);
			callStm.setString(2, nota);
			callStm.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Carrega uma nota para a lista recebendo o id da lista
	 * 
	 * @param String
	 *            -- nomeLista
	 * @return String -- nota
	 */
	public String carregaNota(int numLista) {

		c2 = (Connection) c.conect();
		String nota = null;
		try {
			callStm = c2.prepareCall("{call carrega_nota_id(?)}");
			callStm.setInt(1, numLista);
			ResultSet rs = callStm.executeQuery();
			if (rs.first()) {
				nota = rs.getString("nota");
			}
			c.disconect();
			return nota;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Carrega uma nota para a lista recebendo o nome da lista
	 * 
	 * @param int
	 *            -- numLista
	 * @return String -- nota
	 */
	public String carregaNota(String nomeLista) {

		c2 = (Connection) c.conect();
		String nota = null;
		try {
			callStm = c2.prepareCall("{call carrega_nota_nome(?)}");
			callStm.setString(1, nomeLista);
			ResultSet rs = callStm.executeQuery();
			if (rs.first()) {
				nota = rs.getString("nota");
			}
			c.disconect();
			return nota;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Edita a lista recebendo a lista
	 * 
	 * @param int
	 *            -- numLista
	 * @return String -- nota
	 */
	public void editaLista(CheckList checkList) {

		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call altera_nome_lista(?,?)}");
			callStm.setString(1, checkList.getNomeLista());
			callStm.setInt(2, checkList.getNumLista());
			callStm.execute();
			c.disconect();

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();

		}
	}

	/**
	 * Edita a lista recebendo o novo nome da lista e a lista
	 * 
	 * @param int
	 *            -- numLista
	 * @return String -- nota
	 */
	public void editaLista(String nomeLista, CheckList checkList) {

		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call altera_nome_lista(?,?)}");
			callStm.setString(1, nomeLista);
			callStm.setInt(2, checkList.getNumLista());
			callStm.execute();
			c.disconect();

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();

		}
	}

	public void editaItem(ItemCheckList item) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call edita_item(?,?,?,?)}");
			callStm.setBoolean(1, item.isConcluido());
			callStm.setString(2, item.getItem());
			callStm.setString(3, item.getObservacao());
			callStm.setInt(4, item.getSeqItem());
			callStm.execute();
			c.disconect();

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();

		}

	}
}
