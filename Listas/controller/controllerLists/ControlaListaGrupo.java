package controllerLists;

import java.util.List;

import commomBeans.GrupoSubgrupo;

public class ControlaListaGrupo {

	private List<GrupoSubgrupo> iterableList;
	private int currentPosition;
	private int collectionSize;

	public ControlaListaGrupo(List<GrupoSubgrupo> iterableList) {
		super();
		this.iterableList = iterableList;
		this.currentPosition = currentPosition;
		this.collectionSize = collectionSize;
	}

	// Anda um item para a frente.
	public int getNextPosition() {
		setCurrentPosition(getCurrentPosition() + 1);
		return getCurrentPosition();
	}

	// Anda um item para trás.
	public int getPreviousPosition() {
		setCurrentPosition(getCurrentPosition() - 1);
		return getCurrentPosition();
	}

	// Pega um item em uma determinada posi??o.
	protected GrupoSubgrupo getCollectionItemAt(int position) {
		return getIterableList().get(position);
	}

	// Recupera o primeiro item da cole??o.
	public GrupoSubgrupo first() {
		return getCollectionItemAt(0);
	}

	// Recupera o ?ltimo item da cole??o.
	public GrupoSubgrupo last() {
		return getCollectionItemAt(this.collectionSize);
	}

	// Recupera o pr?ximo item a partir da posi??o atual.
	public GrupoSubgrupo next() {
		return getCollectionItemAt(getNextPosition());
	}

	// Recupera o item anterior a partir da posi??o atual.
	public GrupoSubgrupo previous() {
		return getCollectionItemAt(getPreviousPosition());
	}

	// Recupera um item em qualquer posi??o.
	public GrupoSubgrupo getAt(int position) {
		return getCollectionItemAt(position);

	}

	public List<GrupoSubgrupo> getIterableList() {
		return iterableList;
	}

	public void setIterableList(List<GrupoSubgrupo> iterableList) {
		this.iterableList = iterableList;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

}
