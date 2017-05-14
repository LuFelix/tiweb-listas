package utilListas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexao {

	static Connection con;
	static java.sql.CallableStatement callStm;

	public Conexao() {

	}

	public static void executeStoredProcedure(String sql) {

		try {
			callStm = con.prepareCall(sql);
			callStm.execute("{call listar_chk_list}");
			System.out.println("MANAGER ID: ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection conect() {

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/check_list", "root", "tec2005");
			// JOptionPane.showMessageDialog(null, "Mysql Conectado");
			return con;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao estabelecer conexão MySql", "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}

	}

	public boolean disconect() {
		try {
			con.close();
			// JOptionPane.showMessageDialog(null, "Mysql Desconectado",
			// "Advertência", JOptionPane.WARNING_MESSAGE);
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao comunicar MySql", "Erro ao desconectar",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}

	}

}
