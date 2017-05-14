package beansListas;

public class ItemCheckList {

	int seqItem;
	int idLista;
	String item;
	String Observacao;
	float valor;
	
	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public boolean isConcluido() {
		return concluido;
	}

	public void setConcluido(boolean concluido) {
		this.concluido = concluido;
	}

	boolean concluido;
	
	public ItemCheckList() {

	}
	
	public int getSeqItem() {
		return seqItem;
	}

	public void setSeqItem(int seqItem) {
		this.seqItem = seqItem;
	}

	public int getIdLista() {
		return idLista;
	}

	public void setIdLista(int idLista) {
		this.idLista = idLista;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getObservacao() {
		return Observacao;
	}

	public void setObservacao(String observacao) {
		Observacao = observacao;
	}

	

}
