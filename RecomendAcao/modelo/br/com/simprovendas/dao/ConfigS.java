package br.com.simprovendas.dao;

public class ConfigS {

	private static String bd = "PostgreSql";
	private static String bd2 = "MySql";
	private static String local = "localhost";
	private static String porta = "5432";
	private static String porta2 = "";
	private static String banco1 = "siacecf";
	private static String banco2 = "simpro";
	private static String banco3 = "contab";
	private static String user = "postgres";
	private static String senha = "Lu123!@#";

	public static String getBd() {
		return bd;
	}

	public static void setBd(String bd) {
		ConfigS.bd = bd;
	}

	public static String getLocal() {
		return local;
	}

	public static void setLocal(String local) {
		ConfigS.local = local;
	}

	public static String getPorta() {
		return porta;
	}

	public static void setPorta(String porta) {
		ConfigS.porta = porta;
	}

	public static String getBanco1() {
		return banco1;
	}

	public static void setBanco1(String banco1) {
		ConfigS.banco1 = banco1;
	}

	public static String getBanco2() {
		return banco2;
	}

	public static void setBanco2(String banco2) {
		ConfigS.banco2 = banco2;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		ConfigS.user = user;
	}

	public static String getSenha() {
		return senha;
	}

	public static void setSenha(String senha) {
		ConfigS.senha = senha;
	}

	public static String getBanco3() {
		return banco3;
	}

	public static void setBanco3(String banco3) {
		ConfigS.banco3 = banco3;
	}

}
