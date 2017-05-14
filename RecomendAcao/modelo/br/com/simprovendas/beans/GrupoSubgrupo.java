package br.com.simprovendas.beans;

public class GrupoSubgrupo {

	private int seqGrupo;// Código sequencial
	private String codiGrupo; // Código composto para administração da tabela.
	private String nomeGrupo;// Nome para o grupo.
	private String noAncora;// Grupo pai para esse elemento, se existir.

	public int getSeqGrupo() {
		return seqGrupo;
	}

	public void setSeqGrupo(int seqGrupo) {
		this.seqGrupo = seqGrupo;
	}

	public String getCodiGrupo() {
		return codiGrupo;
	}

	public void setCodiGrupo(String codiGrupo) {
		this.codiGrupo = codiGrupo;
	}

	public String getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

	public String getNoAncora() {
		return noAncora;
	}

	public void setNoAncora(String noAncora) {
		this.noAncora = noAncora;
	}

}
