package commomBeans;

import java.util.Date;

import javax.swing.JLabel;

public class Pessoa {
	private int seqUsuario;
	private String codiPessoa;
	private String cpf;
	private String email;
	private String senha;
	private String nome;
	private String relacao;
	private String tipoPessoa;
	private int sexo;
	private Date dataNasc;
	private JLabel foto;

	public Pessoa() {
		super();

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getSexo() {
		return sexo;
	}

	public void setSexo(int sexo) {
		this.sexo = sexo;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public int getSeqUsuario() {
		return seqUsuario;
	}

	public String getCpf() {
		return cpf;
	}

	public void setSeqUsuario(int seqUsuario) {
		this.seqUsuario = seqUsuario;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setCodiPessoa(String codiPessoa) {
		// TODO Setar o c?digo da pessoa
		this.codiPessoa = codiPessoa;

	}

	public String getCodiPessoa() {
		return codiPessoa;
	}

	public String getRelacao() {
		return relacao;
	}

	public void setRelacao(String relacao) {
		this.relacao = relacao;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public JLabel getFoto() {
		return foto;
	}

	public void setFoto(JLabel foto) {
		this.foto = foto;
	}

}
