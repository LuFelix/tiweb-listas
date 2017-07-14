package beansListas;

import java.util.ArrayList;
import java.util.List;

public class Grupo {

	private int seq;
	private int codiPack;
	private String nome;
	private String descr;

	List<CheckList> listChkList;

	public Grupo() {
		super();
		listChkList = new ArrayList<CheckList>();
	}

	@Override
	public String toString() {
		return getNome();
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
	public List<CheckList> getListChkList() {
		return listChkList;
	}
	public void setListChkList(List<CheckList> listChkList) {
		this.listChkList = listChkList;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getCodiPack() {
		return codiPack;
	}

	public void setCodiPack(int codiPack) {
		this.codiPack = codiPack;
	}

}
