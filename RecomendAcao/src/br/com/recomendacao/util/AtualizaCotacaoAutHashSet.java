package br.com.recomendacao.util;

import java.util.HashSet;
import java.util.List;

import br.com.recomendacao.beans.AtivoYahoo;
import br.com.recomendacao.beans.CotacaoYahoo;
import br.com.recomendacao.controle.ControlaAtivo;
import br.com.recomendacao.dao.DAOAtvYahoo;
import br.com.recomendacao.dao.DAOCotacaoYahoo;
import br.com.recomendacao.visao.FrameInicial;
import br.com.recomendacao.yahoo.AtualizaYahoo;

public class AtualizaCotacaoAutHashSet implements Runnable {

	private HashSet<CotacaoYahoo> hashCotYahoo;
	private List<AtivoYahoo> listAtvYahoo;

	private DAOCotacaoYahoo daoCotYahoo;
	private DAOAtvYahoo daoAtvYahoo;
	private ControlaAtivo contAtv;
	private int index;
	private int tamLista;
	private AtualizaYahoo atuYahoo;

	private CotacaoYahoo cotYahoo;
	private AtivoYahoo atvYahoo;

	public AtualizaCotacaoAutHashSet() {
		index = 0;
		daoAtvYahoo = new DAOAtvYahoo();
		daoCotYahoo = new DAOCotacaoYahoo();
		atuYahoo = new AtualizaYahoo();
		listAtvYahoo = daoAtvYahoo.listarOrdIdNeg();
		hashCotYahoo = daoCotYahoo.consUltCotHashSet();
		contAtv = new ControlaAtivo();
		// System.out.println("Tamanho lista de Ativos: " +
		// listAtvYahoo.size());
		tamLista = listAtvYahoo.size() - 1;
		// System.out.println("Hash está vazio? " + hashCotYahoo.isEmpty());
		// System.out.println("Tamanho do hash: " + hashCotYahoo.size());
		// FrameInicial.setTabela(contAtv.pesqAtivoTabelaUltimas());
		// FrameInicial.getScrVisualiza().setViewportView(FrameInicial.getTabela());
		// FrameInicial.getScrLista().setViewportView(null);
	}

	@Override
	public void run() {

		while (true) {
			// System.out.println("Tamanho do HashSet de Últimas: " +
			// hashCotYahoo.size());
			cotYahoo = new CotacaoYahoo();
			atvYahoo = listAtvYahoo.get(index);
			try {
				atuYahoo.atualiza(atvYahoo.getIdYahoo());
				cotYahoo = atuYahoo.interpretaCotacaoYahoo(atvYahoo.getIdYahoo());
				// System.out.println(" CotYahoo: => " + cotYahoo.getIdYahoo());
				// System.out.println(" Timestamp => " +
				// cotYahoo.getDataHoraCotacao());
				// System.out.println(" Pre_abert => " + cotYahoo.getPreAbe());
				// System.out.println(" Pre Fech => " + cotYahoo.getPreFec());
				// System.out.println(" Pre maxi => " + cotYahoo.getPreMax());
				// System.out.println(" Pre Mini => " + cotYahoo.getPreMin());
				// System.out.println(" Volume => " + cotYahoo.getVolumeNeg());
				// System.out.println(" Variação => " + cotYahoo.getVariacao());
				// System.out.println("Data Hora => " +
				// cotYahoo.getDataHoraCotacao());
				// System.out.println();

				// System.out.println("HashSet contem essa cotação baixada? " +
				// hashCotYahoo.contains(cotYahoo));
				// System.out.println("No hash " +
				// hashCotYahoo.iterator().next().getDataHoraCotacao() + " ID "
				// + hashCotYahoo.iterator().next().getIdYahoo());
				if (!hashCotYahoo.contains(cotYahoo)) {

					daoCotYahoo.inserir(cotYahoo);
					FrameInicial.setTabela(contAtv.pesqAtivoTabelaUltimas());

					FrameInicial.getScrVisualiza().setViewportView(FrameInicial.getTabela());
					FrameInicial.getScrVisualiza().getVerticalScrollBar().setValue(1000);
					System.out.println("Eixo Y " + contAtv.getEixoY());
					FrameInicial.getScrLista().setViewportView(null);

					// FrameInicial.atualizaTela();
					// System.out.println("Inserido...");
					// System.out.println();
					hashCotYahoo = daoCotYahoo.consUltCotHashSet();
					// System.out.println("Tamanho do Hash de Últimas: " +
					// hashCotYahoo.size());
				} else {
					FrameInicial.setTabela(contAtv.pesqAtivoTabelaUltimas());
					FrameInicial.getScrVisualiza().setViewportView(FrameInicial.getTabela());
					FrameInicial.getScrVisualiza().getVerticalScrollBar().setValue(1800);
					System.out.println("Eixo Y " + contAtv.getEixoY());
					FrameInicial.getScrLista().setViewportView(null);
					// System.out.println("Cotação mais atual: " +
					// cotYahoo.getDataHoraCotacao());
				}
				Thread.sleep(1500);
				if (index < tamLista) {
					index++;
				} else {
					index = 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
