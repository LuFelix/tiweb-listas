package br.com.simprovendas.dao;

import java.util.List;

import br.com.simprovendas.beans.AtivoYahoo;
import br.com.simprovendas.beans.CotacaoYahoo;
import br.com.simprovendas.yahoo.AtualizaYahoo;

public class ConsultaTodosAtivosComListaDiario implements Runnable{
	private List<AtivoYahoo> listAtvYahoo;
	private List<CotacaoYahoo> listCotacoes;

	private DAOAtvYahoo daoAtvYahoo;
	private DAOCotacaoYahoo daoCotYahoo;

	private AtualizaYahoo atuYahoo;

	private AtivoYahoo atvYahoo;
	private int index;
	private int tamLista;

	public ConsultaTodosAtivosComListaDiario() {
		index = 0;
		daoAtvYahoo = new DAOAtvYahoo();
		daoCotYahoo = new DAOCotacaoYahoo();
		atuYahoo = new AtualizaYahoo();
		listAtvYahoo = daoAtvYahoo.listarOrdIdNeg();
		
		System.out.println("Tamanho lista de Ativos: " + listAtvYahoo.size());
		tamLista = listAtvYahoo.size() - 1;
			
	}

	@Override
	public void run() {

		while (true) {
				listAtvYahoo.get(index);
				listCotacoes = daoCotYahoo.conCotAtvOrdDtAscend(listAtvYahoo.get(index).getIdYahoo());
				listAtvYahoo.get(index).setListCot(listCotacoes);
				System.out.println("Cotações carregadas para: "
							+ listAtvYahoo.get(index).getIdYahoo());
				if (index < tamLista) {
					index++;
				}else{
					return;
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

	}

}
