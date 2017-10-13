package utilListas;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexao {

	static Connection con;
	String localDB;
	String path;
	String nomeDb;
	public Conexao() {
		path = new File("").getAbsolutePath() + "/db/";
		path = path.replace("\\", "/");
		localDB = "\\C:\\SIMPRO\\db\\";
		nomeDb = "check_list.db";
	}

	public Connection conectMaria() {

		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/check_list", "root",
					"tec2005");
			// JOptionPane.showMessageDialog(null, "Mysql Conectado");
			return con;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao estabelecer conexão MySql", "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}

	}

	public boolean disconectMaria() {
		try {
			con.close();
			// JOptionPane.showMessageDialog(null, "Mysql Desconectado",
			// "Advertência", JOptionPane.WARNING_MESSAGE);
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao comunicar MySql",
					"Erro ao desconectar", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}

	}

	public Connection conectSQLite() {
		try {
			con = DriverManager
					.getConnection("jdbc:sqlite:" + localDB + nomeDb);

			return con;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao estabelecer conexão SQLite\n" + e.getMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
	}

	public boolean disconectSQLite() {
		try {
			con.close();
			// JOptionPane.showMessageDialog(null, "Mysql Desconectado",
			// "Advertência", JOptionPane.WARNING_MESSAGE);
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao comunicar SQLite",
					"Erro ao desconectar", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}

	}

}
