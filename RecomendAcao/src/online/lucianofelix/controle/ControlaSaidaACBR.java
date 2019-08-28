package br.com.recomendacao.controle;

import br.com.recomendacao.beans.Produto;

public interface ControlaSaidaACBR {
	
	String ecfAtivarResposta();
	
	String ecfDesativarResposta();
	
	boolean ecfAtivoResposta();
	
	int		ecfColunasResposta();
	
	String  ecfComandoEnviadoResposta();
	
	String  ecfRespostaComandoResposta();
	
	String  ecfModeloStrResposta();
	
	String  ecfModeloResposta();
	
	String  ecfPortaResposta();
	
	String  ecfTimeOutResposta();
	
	String  ecfAbreCupomResposta();
	
	String ecfLeituraXResposta();
	
	String ecfVendeItemResposta();
	
	String ecfCancelaItemVendidoResposta();
	
	String ecfTestaPodeAbrirCupomResposta();
	
	String ecfSubtotalizaCupomResposta();
	
	String ecfEfetuaPagamentoResposta();
	
	String ecfFecharCupomResposta();
	
	String ecfCancelarCupomResposta();
	
	String ecfReducaoZResposta(String dataHora);
	
	String ecfFormasPagamentoResposta();
	
	
}
