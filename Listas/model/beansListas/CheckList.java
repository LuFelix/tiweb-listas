package beansListas;

import java.util.List;

public class CheckList {

	String nomeLista;
	String nota;
	Grupo grupo;
	int numLista;
	List<ItemCheckList> listItens;

	public CheckList() {

	}
	public String getNomeLista() {
		return nomeLista;
	}

	public void setNomeLista(String nomeLista) {
		this.nomeLista = nomeLista;
	}

	public int getNumLista() {
		return numLista;
	}

	public void setNumLista(int numLista) {
		this.numLista = numLista;
	}

	public List<ItemCheckList> getListItens() {
		return listItens;
	}

	public void setListItens(List<ItemCheckList> listItens) {
		this.listItens = listItens;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

}
