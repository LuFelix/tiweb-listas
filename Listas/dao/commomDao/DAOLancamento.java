package commomDao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import beansOrder.Pedido;
import commomBeans.Conta;
import commomBeans.Lancamento;
import commonUtils.Conexao;
import commonUtils.ConfigS;

public class DAOLancamento {
	private Conexao c;
	private PreparedStatement prepStm;
	private ResultSet res;
	private List<Lancamento> listMov;
	private Lancamento lanc;

	public DAOLancamento() {
		System.out.println("DAOContaLancamento.construtor");
		c = new Conexao(ConfigS.getBd(), ConfigS.getLocal(), ConfigS.getPorta(), ConfigS.getBanco1(), ConfigS.getUser(),
				ConfigS.getSenha());
	}

	public void novoLancamento(String codiConta, String codiCondPag, String codiPedido, String codiPessoa,
			Date dataHoraMovimento, float valor, String obsLanc, Date dataHoraReceb, String tipoLanc)
			throws SQLException {
		dataHoraMovimento = new Date(Calendar.getInstance().getTimeInMillis());
		String sql = "insert into contas_lancamentos ( codi_conta, codi_cond_pag, codi_pedido, codi_pessoa, "
				+ "data_hora_lancamento,valor, obs_lancamento, data_hora_recebimento, tipo_lanc) values (?,?,?,?,?,?,?,?,?);";
		c.conectar();
		prepStm = c.getCon().prepareStatement(sql);
		prepStm.setString(1, codiConta);
		prepStm.setString(2, codiCondPag);
		prepStm.setString(3, codiPedido);
		prepStm.setString(4, codiPessoa);
		prepStm.setDate(5, dataHoraMovimento);
		prepStm.setFloat(6, valor);
		prepStm.setString(7, obsLanc);
		prepStm.setDate(8, dataHoraReceb);
		prepStm.setString(9, tipoLanc);
		prepStm.executeUpdate();
		c.desconectar();
	}

	public List<Lancamento> conMovContaOrdSeqAscend(String codigo) {
		System.out.println("DaoContaLancamento.consulataMovimentoContaOrdAscendente");
		String sql = "select * from contas_lancamentos where codi_conta = '" + codigo
				+ "' order by seq_conta_lancamento asc;";
		listMov = new ArrayList<Lancamento>();
		try {
			c.conectar();
			prepStm = c.getCon().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			res = prepStm.executeQuery();
			if (res.first()) {
				do {
					lanc = new Lancamento();
					lanc.setSequencia(res.getInt("seq_conta_lancamento"));
					lanc.setCodiConta(res.getString("codi_conta"));
					lanc.setCodiCondPag(res.getString("codi_cond_pag"));
					lanc.setCodiPedido(res.getString("codi_pedido"));
					lanc.setCodiPessoa(res.getString("codi_pessoa"));
					lanc.setDataHoraLancamento(res.getDate("data_hora_lancamento"));
					lanc.setValor(res.getFloat("valor"));
					lanc.setTipoLancamento(res.getString("codi_tipo_lancamento"));
					listMov.add(lanc);
				} while (res.next());
			} else {
				lanc = new Lancamento();
				lanc.setCodiConta("Nulo");
				listMov.add(lanc);
			}
			c.desconectar();
			return listMov;
		} catch (SQLException e) {
			c.desconectar();
			e.printStackTrace();
			return null;
		}
	}

	// Consulta somente entradas ou somente saídas
	public List<Lancamento> conEntrSaiConta(Conta conta) {
		System.out.println("DAOContaMovimento.ConsultaEntradasouSaidas");
		String sql = "select * from contas_lancamentos where codi_conta = '" + conta.getCodiConta()
				+ "' order by seq_conta_lancamento asc;";
		listMov = new ArrayList<Lancamento>();
		try {
			c.conectar();
			prepStm = c.getCon().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet res = prepStm.executeQuery();
			if (res.first()) {
				do {
					lanc = new Lancamento();
					lanc.setSequencia(res.getInt("seq_conta_lancamento"));
					lanc.setCodiConta(res.getString("codi_conta"));
					lanc.setCodiCondPag(res.getString("codi_cond_pag"));
					lanc.setCodiPedido(res.getString("codi_pedido"));
					lanc.setCodiPessoa(res.getString("codi_pessoa"));
					lanc.setDataHoraLancamento(res.getDate("data_hora_lancamento"));
					lanc.setValor(res.getFloat("valor"));
					listMov.add(lanc);
				} while (res.next());
			}
			c.desconectar();
			return listMov;
		} catch (SQLException e) {
			c.desconectar();
			e.printStackTrace();
			return null;
		}
	}

	// Consulta lancamentos do Pedido
	public List<Lancamento> consultLancPedido(Pedido pedi) {
		System.out.println("DAOContaMovimento.ConsultaEntradasouSaidas");
		String sql = "select * from contas_lancamentos where codi_pedido = '" + pedi.getCodiPedi()
				+ "' order by seq_conta_lancamento asc;";
		listMov = new ArrayList<Lancamento>();
		try {
			c.conectar();
			prepStm = c.getCon().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet res = prepStm.executeQuery();
			if (res.first()) {
				do {
					lanc = new Lancamento();
					lanc.setSequencia(res.getInt("seq_conta_lancamento"));
					lanc.setCodiConta(res.getString("codi_conta"));
					lanc.setCodiCondPag(res.getString("codi_cond_pag"));
					lanc.setCodiPedido(res.getString("codi_pedido"));
					lanc.setCodiPessoa(res.getString("codi_pessoa"));
					lanc.setDataHoraLancamento(res.getDate("data_hora_lancamento"));
					lanc.setValor(res.getFloat("valor"));
					listMov.add(lanc);
				} while (res.next());
			}
			c.desconectar();
			return listMov;
		} catch (SQLException e) {
			c.desconectar();
			e.printStackTrace();

			return null;
		}
	}

	public boolean removerItem(Pedido pedi, Lancamento lanc) {
		c.conectar();
		String sql = "delete from contas_lancamentos where codi_pedido=? and codi_cond_pag=?;";
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setString(1, pedi.getCodiPedi());
			prepStm.setString(2, lanc.getCodiCondPag());
			prepStm.executeUpdate();
			c.desconectar();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			c.desconectar();
			return false;
		}

	}

	public void alterarQuantItem(Pedido pedi, Lancamento lanc) {
		c.conectar();
		String sql = "update contas_lancamentos  set valor =? where codi_pedido=? and codi_cond_pag=?;";
		try {
			prepStm = c.getCon().prepareStatement(sql);
			prepStm.setFloat(1, lanc.getValor());
			prepStm.setString(2, pedi.getCodiPedi());
			prepStm.setString(3, lanc.getCodiCondPag());
			prepStm.executeUpdate();
			c.desconectar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			c.desconectar();
		}

	}
}