package commonUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
	private String local;
	private String user;
	private String senha;
	private Connection con;
	private Statement statment;
	private String str_conexao;
	private String driverjdbc;

	public Conexao(String bd, String local, String porta, String banco, String user, String senha) {
		if (bd.equals("PostgreSql")) {
			setStr_conexao("jdbc:postgresql://" + local + ":" + porta + "/" + banco);
			setLocal(local);
			setSenha(senha);
			setUser(user);
			setDriverjdbc("org.postgresql.Driver");
		} else {
			if (bd.equals("MySql")) {
				setStr_conexao("jdbc:mysql://" + local + ":" + porta + "/" + banco);
				setLocal(local);
				setSenha(senha);
				setUser(user);
				setDriverjdbc("com.mysql.jdbc.Driver");
			}
		}
	}

	public void configUser(String user, String senha) {
		// TODO Aqui vai o proxy
		setUser(user);
		setSenha(senha);
	}

	public void configLocal(String banco) {
		setLocal(banco);
	}

	public boolean conectar() {
		System.setProperty("jdbc.Drivers", getDriverjdbc());
		try {
			con = DriverManager.getConnection(getStr_conexao(), getUser(), getSenha());
			// System.out.println("Conectado Conexao Util");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean desconectar() {
		try {
			getCon().close();
			// System.out.println("Desconectado Conexao Util");
			return true;
		} catch (SQLException ex) {
			System.err.println(ex);
			ex.printStackTrace();
			return false;
		}

	}

	public boolean estaAtiva() {
		try {

			if (con.isClosed() & con.isValid(0)) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao testar a conexão!!!");
			e.printStackTrace();
			return false;
		}

	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public Statement getStatment() {
		return statment;
	}

	public void setStatment(Statement statment) {
		this.statment = statment;
	}

	public String getStr_conexao() {
		return str_conexao;
	}

	public void setStr_conexao(String str_conexao) {
		this.str_conexao = str_conexao;
	}

	public String getDriverjdbc() {
		return driverjdbc;
	}

	public void setDriverjdbc(String driverjdbc) {
		this.driverjdbc = driverjdbc;
	}

}
