package br.com.recomendacao.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.recomendacao.beans.Pessoa;

public interface InterDaoPessoa {

	public int consultaUltimo();
	public void reservaCodigo(String codigo) throws SQLException;
	public boolean cadastrar(Pessoa o);
	public boolean alterar(Pessoa o);
	public boolean excluir(Pessoa o);
	public List<Pessoa> listarTodos();
	public List<Pessoa> pesquisarString(String str);
	public List<Pessoa> pesquisarRelacao(String str);

}
