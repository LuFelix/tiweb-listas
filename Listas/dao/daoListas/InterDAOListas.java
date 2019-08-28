package daoListas;

import java.sql.SQLException;
import java.util.List;

import beansListas.CheckList;
import beansListas.Grupo;
import beansListas.ItemCheckList;

public abstract class InterDAOListas {

	/**
	 * TODO Acesso a dados de Grupos
	 */

	/**
	 * Grupos - Cria um novo grupo
	 * 
	 * @author Luciano Felix
	 * 
	 * @param nomeGrupo
	 */
	public abstract boolean addGrupo(String nomeGrupo);

	/**
	 * Grupos - Retorna um grupo através do nome
	 * 
	 * @author Luciano Felix
	 * 
	 * @param nomeGrupo
	 * @return
	 */
	public abstract Grupo buscaGrupo(String nomeGrupo);

	/**
	 * Grupos - Retorna um grupo através da sequencia
	 * 
	 * @author Luciano Felix
	 * 
	 * @param seqGrupo
	 * @return
	 */
	public abstract Grupo buscaGrupo(int seqGrupo);

	/**
	 * Grupos - Retorna array contendo os grupos existentes sem as listas
	 * 
	 * @author Luciano Felix
	 * 
	 * @return List <CheckList>
	 */
	public abstract List<Grupo> lerGrupos();

	/**
	 * Grupos - Atualiza o grupo recebido por parêmetro
	 * 
	 * @param Grupo
	 *            -- g grupo a ser atualizado
	 */
	public abstract void atualizaGrupo(Grupo g);

	/**
	 * Grupos - Remove o grupo recebido por parâmetro
	 * 
	 * @param nomeGrupo
	 */
	public abstract void remGrupo(Grupo grupo);

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
	public abstract void addLista(CheckList chkList);

	/**
	 * Listas - Retorna um array contendo as listas sem os ítens.
	 * 
	 * @author Luciano Felix
	 * 
	 * @return List <CheckList>
	 */
	public abstract List<CheckList> lerListas();

	/**
	 * Listas - Retorna um array com listas sem os ítens por grupo.
	 * 
	 * @author Luciano Felix
	 * 
	 * @param int
	 *            -- seq_grupo sequencia do Grupo
	 * @return List <CheckList>
	 */
	public abstract List<CheckList> lerListas(int grupo);

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
	public abstract CheckList carregarItens(CheckList checkList);

	/**
	 * Listas - Atualiza uma lista previamente alterada
	 * 
	 * @author Luciano Felix
	 * 
	 * @param CheckList
	 *            -- Lista a ser modificada
	 */
	public abstract void atualizaLista(CheckList checkList);

	/**
	 * Listas - Atualiza a lista com os dados
	 * 
	 * @param CheckList
	 *            --checkList com a ser modificada
	 */
	public abstract void alteraGrupoLista(CheckList checkList);

	/**
	 * Remove uma lista da tabela
	 * 
	 * @param CheckList
	 *            -- checkList a ser removida
	 */
	public abstract boolean remLista(CheckList chkList);

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
	public abstract void addItemListaSeqLista(CheckList chkList,
			ItemCheckList item);

	/**
	 * Ítens - Atualiza o ítem recebido por parâmetro
	 * 
	 * @author Luciano Felix
	 * 
	 * @param item
	 */
	public abstract void atualizaItem(ItemCheckList item);

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
	public abstract List<ItemCheckList> lerItensListaNomeLista(
			String nomeLista);

	/**
	 * Ítens - Retorna um array com os ítens por seqLista
	 * 
	 * @author Luciano Felix
	 * 
	 * @param numLista
	 * 
	 * @return List
	 */
	public abstract List<ItemCheckList> lerItensListaSeq(int numLista);

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
	public abstract List<ItemCheckList> lerItensListaSeq(int numLista,
			boolean status);

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
	public abstract void remItem(ItemCheckList item);

	/**
	 * TODO Acesso a a Notas
	 * 
	 */

	/**
	 * Notas - Ler nota para a lista recebendo seqLista
	 * 
	 * @param int
	 *            -- sequencia da lista para exibir a nota
	 * @return String -- texto com a nota
	 */
	public abstract String lerNota(int numLista);

	/**
	 * Notas - Ler a nota para a lista recebendo o nome da lista
	 * 
	 * @param String
	 *            -- nome da Lista para exibir a nota
	 * @return String -- nota
	 */
	public abstract String lerNota(String nomeLista);

	/*
	 * Notas - Atualiza nota na lista recebendo seqLista e a nota a ser gravada
	 * 
	 * @param int -- número de sequencia da lista
	 * 
	 * @param String -- texto com a nota para gravação
	 * 
	 * @throws SQLException
	 */
	public abstract void atualizaNota(int numLista, String nota)
			throws SQLException;

	/**
	 * Notas - Atualiza nota na lista recebendo nomeLista e a nota a ser gravada
	 * 
	 * @param int
	 *            -- nomeLista nome da lista para a atualização da nota
	 * @param String
	 *            -- nota
	 */
	public abstract void atualizaNota(String nomeLista, String nota);

	/**
	 * TODO Acesso a dados FIREBASE
	 */

	/**
	 * Ítem - Atualiza o íten editado previamente
	 * 
	 * @param item
	 */

	public abstract void addListaFireBase(CheckList chkList);

}
