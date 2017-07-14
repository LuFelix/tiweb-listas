package beansListas;

import java.util.ArrayList;
import java.util.List;

public class Pack {

	private int seq;
	private String nome;
	private String descr;
	private List<Grupo> listGrupo;

	public Pack() {

		super();
		setListGrupo(new ArrayList<Grupo>());
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public List<Grupo> getListGrupo() {
		return listGrupo;
	}

	public void setListGrupo(List<Grupo> listGrupo) {
		this.listGrupo = listGrupo;
	}

}
