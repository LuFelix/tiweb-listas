package daoListas;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import beansListas.CheckList;
import beansListas.Grupo;
import beansListas.ItemCheckList;
import utilListas.Conexao;

public class DaoLista {
	Connection c2;
	Conexao c;
	CallableStatement callStm;
	List<Grupo> listGrupo;
	List<CheckList> listChkLists;
	List<ItemCheckList> listItens;
	CheckList checkList;
	Grupo grupo;
	ItemCheckList item;

	public DaoLista() {
		c = new Conexao();
		// FirebaseOptions options = null;
		// try {
		// options = new FirebaseOptions.Builder()
		// .setServiceAccount(new
		// FileInputStream("path/to/serviceAccountCredentials.json"))
		// .setDatabaseUrl("https://CheckList.firebaseio.com/").build();
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// FirebaseApp.initializeApp(options);

	}

	/**
	 * TODO Acesso a dados de Grupos no MDB
	 */

	/**
	 * Grupos - Cria um novo grupo
	 * 
	 * @author Luciano Felix
	 * 
	 * @param nomeGrupo
	 */
	public boolean addGrupo(String nomeGrupo) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call cria_grupo(?,?)}");
			callStm.setString(1, nomeGrupo);
			callStm.setString(2, nomeGrupo);
			callStm.executeQuery();
			c.disconect();
			return true;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Grupos - Retorna um grupo através do nome
	 * 
	 * @author Luciano Felix
	 * 
	 * @param nomeGrupo
	 * @return
	 */
	public Grupo buscaGrupo(String nomeGrupo) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call busca_grupo_nome(?)}");
			callStm.setString(1, nomeGrupo);
			ResultSet rs = callStm.executeQuery();
			if (rs.first()) {
				grupo = new Grupo();
				grupo.setSeq(rs.getInt("nota"));
				grupo.setNome(rs.getString("nota"));
				grupo.setNome(rs.getString("descr"));
			}
			c.disconect();
			return grupo;
		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Grupos - Retorna um grupo através da sequencia
	 * 
	 * @author Luciano Felix
	 * 
	 * @param seqGrupo
	 * @return
	 */
	public Grupo buscaGrupo(int seqGrupo) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call busca_grupo_seq(?)}");
			callStm.setInt(1, seqGrupo);
			ResultSet rs = callStm.executeQuery();
			if (rs.first()) {
				grupo = new Grupo();
				grupo.setSeq(rs.getInt("nota"));
				grupo.setNome(rs.getString("nota"));
				grupo.setNome(rs.getString("descr"));
			}
			c.disconect();
			return grupo;
		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Grupos - Retorna array contendo os grupos existentes sem as listas
	 * 
	 * @author Luciano Felix
	 * 
	 * @return List <CheckList>
	 */
	public List<Grupo> lerGrupos() {
		c2 = (Connection) c.conect();
		listGrupo = new ArrayList<Grupo>();
		try {
			callStm = c2.prepareCall("{call ler_grupos()}");
			ResultSet rs = callStm.executeQuery();
			while (rs.next()) {
				grupo = new Grupo();
				grupo.setSeq(rs.getInt("seq_grupo"));
				grupo.setNome(rs.getString("nome_grupo"));
				grupo.setDescr(rs.getString("desc_grupo"));
				listGrupo.add(grupo);
			}
			c.disconect();
			return listGrupo;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Grupos - Atualiza o grupo recebido por parêmetro
	 * 
	 * @param Grupo
	 *            -- g grupo a ser atualizado
	 */
	public void atualizaGrupo(Grupo g) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call atualiza_grupo_seq(?,?,?)}");
			callStm.setString(1, g.getNome());
			callStm.setString(2, g.getDescr());
			callStm.setInt(3, g.getSeq());
			callStm.execute();
			c.disconect();

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();

		}

	}

	/**
	 * Grupos - Remove o grupo recebido por parâmetro
	 * 
	 * @param nomeGrupo
	 */
	public void remGrupo(Grupo grupo) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call rem_grupo(?)}");
			callStm.setInt(1, grupo.getSeq());
			callStm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * TODO Acesso a dados de Listas no MDB
	 */

	/**
	 * Listas - Cria uma nova lista
	 * 
	 * @author Luciano Felix
	 * 
	 * @param CheckList
	 *            checkList - Lista a ser criada
	 */
	public void addLista(CheckList chkList) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call cria_chk_list(?)}");
			callStm.setString(1, chkList.getNomeLista());
			callStm.executeQuery();
			c.disconect();
		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
		}

	}

	/**
	 * Listas - Retorna um array contendo as listas sem os ítens.
	 * 
	 * @author Luciano Felix
	 * 
	 * @return List <CheckList>
	 */
	public List<CheckList> lerListas() {
		c2 = (Connection) c.conect();
		listChkLists = new ArrayList<CheckList>();
		try {
			callStm = c2.prepareCall("{call ler_listas()}");
			ResultSet rs = callStm.executeQuery();
			while (rs.next()) {
				checkList = new CheckList();
				checkList.setNumLista(rs.getInt("id_chk_list"));
				checkList.setNomeLista(rs.getString("nome_lista"));
				checkList.setNota(rs.getString("nota"));
				listChkLists.add(checkList);
			}
			c.disconect();
			return listChkLists;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Listas - Retorna um array com listas sem os ítens por grupo.
	 * 
	 * @author Luciano Felix
	 * 
	 * @param int
	 *            -- seq_grupo sequencia do Grupo
	 * @return List <CheckList>
	 */
	public List<CheckList> lerListas(int grupo) {
		c2 = (Connection) c.conect();
		listChkLists = new ArrayList<CheckList>();
		try {
			callStm = c2.prepareCall("{call ler_listas_grupo (?)}");
			callStm.setInt(1, grupo);
			ResultSet rs = callStm.executeQuery();
			while (rs.next()) {
				checkList = new CheckList();
				checkList.setNumLista(rs.getInt("id_chk_list"));
				checkList.setNomeLista(rs.getString("nome_lista"));
				checkList.setNota(rs.getString("nota"));
				listChkLists.add(checkList);
			}
			c.disconect();
			return listChkLists;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Listas - Retorna a lista recebida carregada com os ítens
	 * 
	 * @author Luciano Felix
	 * 
	 * @param CheckList
	 *            -- Lista para carregar os ítens
	 * 
	 * @return CheckList -- checkList com a lisa de ítens
	 */
	public CheckList carregarItens(CheckList checkList) {
		this.c2 = (Connection) c.conect();
		this.checkList = checkList;
		listItens = new ArrayList<ItemCheckList>();
		try {
			callStm = c2.prepareCall("{call exibir_lista_id(?)}");
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
			c.disconect();
			return checkList;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Listas - Atualiza uma lista previamente alterada
	 * 
	 * @author Luciano Felix
	 * 
	 * @param CheckList
	 *            -- Lista a ser modificada
	 */
	public void atualizaLista(CheckList checkList) {

		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call atualiza_nomelista_seq(?,?)}");
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
	 * Listas - Atualiza a lista com os dados
	 * 
	 * @param CheckList
	 *            --checkList com a ser modificada
	 */
	public void alteraGrupoLista(CheckList checkList) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call altera_grupo_lista(?,?)}");
			callStm.setInt(1, checkList.getGrupo().getSeq());
			callStm.setInt(2, checkList.getNumLista());
			callStm.execute();
			c.disconect();

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();

		}
	}

	/**
	 * Remove uma lista da tabela
	 * 
	 * @param CheckList
	 *            -- checkList a ser removida
	 */
	public boolean remLista(CheckList chkList) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call rem_lista(?)}");
			callStm.setInt(1, chkList.getNumLista());
			callStm.executeUpdate();
			c.disconect();
			return true;
		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * TODO Acesso a dados de ítens no MDB
	 */

	/**
	 * Ítens - Adiciona um ítem na lista recebendo o ítem e a lista
	 * 
	 * @author Luciano Felix
	 * 
	 * @param chkList
	 * @param item
	 */
	public void addItemListaSeqLista(CheckList chkList, ItemCheckList item) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call add_item_lista_seq_lista(?,?,?)}");
			callStm.setInt(1, chkList.getNumLista());
			callStm.setString(2, item.getItem());
			callStm.setString(3, item.getObservacao());
			callStm.executeQuery();
			c.disconect();
		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
		}
	}

	/**
	 * Ítens - Atualiza o ítem recebido por parâmetro
	 * 
	 * @author Luciano Felix
	 * 
	 * @param item
	 */
	public void atualizaItem(ItemCheckList item) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call atualiza_item_seq(?,?,?,?)}");
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

	/**
	 * Ítens - Retorna uma array com os ítens por nomeLista
	 * 
	 * @author Luciano Felix
	 * 
	 * @param String
	 *            -- nomeLista - o nome da lista para carregar os ítens
	 * 
	 * @return ArrayList<ItemCheckList>
	 */
	public List<ItemCheckList> lerItensListaNomeLista(String nomeLista) {
		c2 = (Connection) c.conect();
		listItens = new ArrayList<ItemCheckList>();
		try {
			callStm = c2.prepareCall("{call exibir_lista_nome(?)}");
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
			c.disconect();
			return listItens;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Ítens - Retorna um array com os ítens por seqLista
	 * 
	 * @author Luciano Felix
	 * 
	 * @param numLista
	 * 
	 * @return List
	 */
	public List<ItemCheckList> lerItensListaSeq(int numLista) {
		c2 = (Connection) c.conect();
		listItens = new ArrayList<ItemCheckList>();
		try {
			callStm = c2.prepareCall("{call ler_itens_lista_seq(?)}");
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
			c.disconect();
			return listItens;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Ítens - Retorna um array com os ítens por seqLista e status
	 * 
	 * @author Luciano Felix
	 * 
	 * @param numLista
	 *            -- Número de sequencia da lista a exibir
	 * @param status
	 *            -- Se está concluiído o ítem
	 * 
	 * @return List
	 */
	public List<ItemCheckList> lerItensListaSeq(int numLista, boolean status) {
		c2 = (Connection) c.conect();
		listItens = new ArrayList<ItemCheckList>();
		try {
			callStm = c2.prepareCall("{call ler_itens_lista_seq_status(?,?)}");
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
			c.disconect();
			return listItens;

		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Ítens - Remove o ítem recebido através do parâmetro
	 * 
	 * @author Luciano Felix
	 * 
	 * @param CheckList
	 *            -- chkList a ser removido
	 * @param ItemCheckList
	 *            -- item
	 */
	public void remItem(ItemCheckList item) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call rem_item(?)}");
			callStm.setInt(1, item.getSeqItem());
			callStm.executeUpdate();
			c.disconect();
		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
		}

	}

	/**
	 * TODO Acesso a a Notas no MDB
	 * 
	 */

	/**
	 * Notas - Ler nota para a lista recebendo seqLista
	 * 
	 * @param int
	 *            -- sequencia da lista para exibir a nota
	 * @return String -- texto com a nota
	 */
	public String lerNota(int numLista) {

		c2 = (Connection) c.conect();
		String nota = null;
		try {
			callStm = c2.prepareCall("{call ler_nota_seq_lista(?)}");
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
	 * Notas - Ler a nota para a lista recebendo o nome da lista
	 * 
	 * @param String
	 *            -- nome da Lista para exibir a nota
	 * @return String -- nota
	 */
	public String lerNota(String nomeLista) {
		c2 = (Connection) c.conect();
		String nota = null;
		try {
			callStm = c2.prepareCall("{call ler_nota_nome(?)}");
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

	/*
	 * Notas - Atualiza nota na lista recebendo seqLista e a nota a ser gravada
	 * 
	 * @param int -- número de sequencia da lista
	 * 
	 * @param String -- texto com a nota para gravação
	 * 
	 * @throws SQLException
	 */
	public void atualizaNota(int numLista, String nota) throws SQLException {
		c2 = (Connection) c.conect();

		callStm = c2.prepareCall("{call atualiza_nota_seq(?,?)}");
		callStm.setInt(1, numLista);
		callStm.setString(2, nota);
		callStm.executeQuery();
		c.disconect();

	}

	/**
	 * Notas - Atualiza nota na lista recebendo nomeLista e a nota a ser gravada
	 * 
	 * @param int
	 *            -- nomeLista nome da lista para a atualização da nota
	 * @param String
	 *            -- nota
	 */
	public void atualizaNota(String nomeLista, String nota) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call atualiza_nota_nome(?,?)}");
			callStm.setString(1, nomeLista);
			callStm.setString(2, nota);
			callStm.executeQuery();
			c.disconect();
		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
		}
	}

	/**
	 * TODO Acesso a dados FIREBASE
	 */

	/**
	 * Ítem - Atualiza o íten editado previamente
	 * 
	 * @param item
	 */

	public void addListaFireBase(CheckList chkList) {
		c2 = (Connection) c.conect();
		try {
			callStm = c2.prepareCall("{call nova_chk_list(?)}");
			callStm.setString(1, chkList.getNomeLista());
			callStm.executeQuery();
			c.disconect();
		} catch (SQLException e) {
			c.disconect();
			e.printStackTrace();
		}

	}

}
