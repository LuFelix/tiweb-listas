package br.com.recomendacao.controle;

import java.sql.SQLException;

import br.com.recomendacao.beans.Lancamento;
import br.com.recomendacao.beans.Pedido;
import br.com.recomendacao.dao.DAOLancamento;

public class ControlaLancamento {
	DAOLancamento daoLancamento;

	public ControlaLancamento() {
		daoLancamento = new DAOLancamento();
	}

	public void adicionaLancamentoPedido(Pedido pedi, Lancamento lanc) {
		if (pedi.getTipoPedido().equals("Compra")) {
			lanc.setTipoLancamento("Sai");
		} else if (pedi.getTipoPedido().equals("Venda")) {
			lanc.setTipoLancamento("Entra");
		}
		try {
			daoLancamento.novoLancamento("10201662", lanc.getCodiCondPag(), pedi.getCodiPedi(),
					pedi.getCodiPessoaCliente(), null, lanc.getValor(), "Obs", null, lanc.getTipoLancamento());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
