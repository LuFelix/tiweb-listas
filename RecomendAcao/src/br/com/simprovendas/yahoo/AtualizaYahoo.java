package br.com.simprovendas.yahoo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;

import br.com.simprovendas.beans.Ativo;
import br.com.simprovendas.beans.AtivoYahoo;
import br.com.simprovendas.beans.CotacaoYahoo;
import br.com.simprovendas.dao.DAOAtivo;
import br.com.simprovendas.dao.DAOAtvYahoo;
import br.com.simprovendas.dao.DAOCotacaoYahoo;
import br.com.simprovendas.util.BaixarArquivos;
import br.com.simprovendas.util.ManipulaData;

public class AtualizaYahoo {

	BaixarArquivos baixar;
	List<String> listLines;
	String dataAux;
	String horaAux;
	String idYahoo;
	Date dataCotacao;
	Time hora;
	Timestamp dataHora;
	float preAbe, preUlt, preMax, preMin, volumeNeg, variacao;
	DAOCotacaoYahoo daoCotYahoo;
	DAOAtvYahoo daoAtvYahoo;
	DAOAtivo daoAtv;
	BufferedReader novaEntrada;
	BufferedReader ultimaEntrada;
	private String url;
	private String pathLocalAtual;
	private String pathLocalUlt;
	CotacaoYahoo cotYahoo;

	public AtualizaYahoo() {
		setPathLocalAtual("C:\\SIMPRO\\yahoo\\atual\\");
		setPathLocalUlt("C:\\SIMPRO\\yahoo\\ult\\");
		baixar = new BaixarArquivos();
		daoCotYahoo = new DAOCotacaoYahoo();
		daoAtvYahoo = new DAOAtvYahoo();

	}

	// http://download.finance.yahoo.com/d/quotes.csv?s= VALE5.SA
	// &f=sl1d1t1c1ohgv&e=.csv

	public String getPathLocalAtual() {
		return pathLocalAtual;
	}

	public void setPathLocalAtual(String pathLocalAtual) {
		this.pathLocalAtual = pathLocalAtual;
	}

	public String getPathLocalUlt() {
		return pathLocalUlt;
	}

	public void setPathLocalUlt(String pathLocalUlt) {
		this.pathLocalUlt = pathLocalUlt;
	}

	public boolean atualiza(String idYahoo) throws Exception {
		url = "http://download.finance.yahoo.com/d/quotes.csv?s=" + idYahoo + "&f=sl1d1t1c1ohgv&e=.csv";
		baixar.gravaArquivoDeURL(url, pathLocalAtual, idYahoo);
		// System.out.println("Atualizado para: ===>> " + idYahoo);
		return true;
	}

	// passa a referencia do nome do arquivo na pasta para interpretacão
	// retornando uma nova cotação
	public CotacaoYahoo interpretaCotacaoYahoo(String idYahoo) throws Exception {

		novaEntrada = new BufferedReader(new FileReader(pathLocalAtual + idYahoo));
		String linha;
		// Lendo as linhas do arquivo uma a uma
		while ((linha = novaEntrada.readLine()) != null) {
			// converte cada linha em um array separados pelo marcadorn
			// nesse
			// caso CSV ","
			String conteudo[] = linha.split(",");

			// Tratando conteudo[0] idYahoo
			idYahoo = conteudo[0].replace("\"", "").trim();
			// System.out.println("ID Yahoo => " + idYahoo);

			// Tratando conteudo[1] Último Preço
			if (!conteudo[1].equals("N/A")) {
				preUlt = Float.parseFloat(conteudo[1].trim());
				// System.out.println("variável " + preUlt);
				DecimalFormat df = new DecimalFormat("###.##");
				df.setRoundingMode(RoundingMode.UP);
				// System.out.println(df.format(preUlt)); //

			} else
				preUlt = 0;
			// System.out.println("Ultimo => " + preUlt);

			// Tratando conteudo[2] e conteudo[3] DataHora
			if (!conteudo[2].equals("N/A")) {
				dataAux = conteudo[2].replaceAll("\"", "").trim();
				horaAux = conteudo[3].replace("\"", "").trim();
				// System.out.println("Data => " + dataAux);
				ManipulaData manData = new ManipulaData();
				dataAux = manData.inverteData4(dataAux);
				horaAux = manData.converteAMPM(horaAux);
				// System.out.println("Data correta => " + dataAux);
				// System.out.println("Hora correta => " + horaAux);
				// System.out.println("DATA/HORA =>" + dataAux + " " +
				// horaAux);
				dataHora = Timestamp.valueOf(dataAux + " " + horaAux);
				// System.out.println(dataHora);
				// dataCotacao = java.sql.Date.valueOf(dataAux);
				// System.out.println("TIMESTAMP => "
				// + Timestamp.valueOf(dataAux + " " + horaAux));

			}
			// Tratando conteudo[4] variaÃ§Ã£o
			if (!conteudo[4].isEmpty() && !conteudo[4].equals("N/A")) {
				variacao = Float.parseFloat(conteudo[4].trim());

			}
			// System.out.println("Variacao => " + conteudo[4]);

			// Tratando conteudo[5] abertura
			if (!conteudo[5].equals("N/A")) {
				preAbe = Float.parseFloat(conteudo[1].trim());
			} else
				preAbe = 0;
			// System.out.println("Abertura => " + conteudo[5]);

			// Tratando conteudo[6] mÃ¡ximo
			if (!conteudo[6].equals("N/A")) {
				preMax = Float.parseFloat(conteudo[6].trim());
			} else
				preAbe = 0;
			// System.out.println("MÃ¡ximo => " + conteudo[6]);

			// Tratando conteudo[7] mÃ­nimo
			if (!conteudo[7].equals("N/A")) {
				preMax = Float.parseFloat(conteudo[7].trim());
			} else
				preMin = 0;
			// System.out.println("MÃ­nimo => " + conteudo[7]);

			// Tratando conteudo[8] Volume
			if (!conteudo[8].equals("N/A")) {
				volumeNeg = Float.parseFloat(conteudo[8].trim());
			} else
				volumeNeg = 0;
			// System.out.println("Volume => " + conteudo[8]);
			// System.out.println();
			// System.out.println("Interpretado para: ===>> " + idYahoo);
			// System.out.println();
			// System.out
			// .println("++++++++++++++++++++++++++++++++++++++++++++++");
			// System.out.println();

		}
		cotYahoo = new CotacaoYahoo();
		cotYahoo.setIdYahoo(idYahoo);
		cotYahoo.setDataHoraCotacao(dataHora);
		cotYahoo.setPreAbe(preAbe);
		cotYahoo.setPreFec(preUlt);
		cotYahoo.setPreMax(preMax);
		cotYahoo.setPreMin(preMin);
		cotYahoo.setVolumeNeg(volumeNeg);
		cotYahoo.setVariacao(variacao);
		// IO.delete(pathLocalAtual + idYahoo);
		return cotYahoo;
	}

	public boolean verificaUltCotacao(AtivoYahoo atvYahoo) {

		return false;

	}

	public void atualizarListaAtivos() {
		List<Ativo> listAtv = daoAtv.procurarTodosOrdIdNeg();
		AtivoYahoo atvYahoo;
		for (int i = 0; i < listAtv.size(); i++) {
			atvYahoo = new AtivoYahoo();
			atvYahoo.setIdYahoo(listAtv.get(i).getIdNeg() + ".SA");
			daoAtvYahoo.inserir(atvYahoo);
		}

	}

}
