package ifg.urutai.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ifg.urutai.dao.PedidoDAO;
import ifg.urutai.dao.PessoaDAO;
import ifg.urutai.model.Pedido;
import ifg.urutai.model.Pessoa;



/**
 * Servlet implementation class PedidoController
 */
@WebServlet("/PedidoController")
public class PedidoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PedidoDAO pedidoDAO;
	private PessoaDAO pessoaDAO;
	private static final String MANTER_PEDIDO = "manterPedido.jsp";
	private static final String LISTAR_PEDIDOS = "listarPedidos.jsp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PedidoController() {
		super();
		pedidoDAO = new PedidoDAO();
		pessoaDAO = new PessoaDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String avancar = "";

		if (acao.equalsIgnoreCase("criar")) {
			request.setAttribute("listaPessoas", pessoaDAO.findAll());
			avancar = MANTER_PEDIDO;
		}

		else if (acao.equalsIgnoreCase("buscarPorId")) {
			int id_pedido = Integer.parseInt(request.getParameter("id_pedido"));
			Pedido pedido = pedidoDAO.findbyIdPedido(id_pedido);

			List<Pedido> listaPedidos = new ArrayList<>();
			listaPedidos.add(pedido);
			request.setAttribute("listaPedidos", listaPedidos);

			avancar = LISTAR_PEDIDOS;

		} else if (acao.equalsIgnoreCase("atualizar")) {

			String id_pedido = request.getParameter("id_pedido");
			String dataPedido = request.getParameter("dataPedido");
			String id_pessoa = request.getParameter("id_pessoa");

			Pedido pedido = new Pedido();
			pedido.setId(Integer.parseInt(id_pedido));
			pedido.setDataPedido(Date.valueOf(dataPedido));
			
			Pessoa pessoa = pessoaDAO.findbyId(Integer.parseInt(id_pessoa));
			pedido.setPessoa(pessoa);

			request.setAttribute("pedido", pedido);
			List<Pessoa> listaPessoas = new ArrayList<Pessoa>();
			listaPessoas.add(pessoa);
			request.setAttribute("listaPessoas", listaPessoas);

			avancar = MANTER_PEDIDO;

		} else if (acao.equalsIgnoreCase("remover")) {

			int id_pedido = Integer.parseInt(request.getParameter("id_pedido"));
			Pedido pedido = pedidoDAO.findbyIdPedido(id_pedido);
			pedidoDAO.deleteById(pedido);

			List<Pedido> listaPedidos = pedidoDAO.findByAll();
			request.setAttribute("listaPedidos", listaPedidos);

			avancar = LISTAR_PEDIDOS;

		} else if (acao.equalsIgnoreCase("listarTodos")) {

			List<Pedido> listaPedidos = pedidoDAO.findByAll();
			request.setAttribute("listaPedidos", listaPedidos);

			avancar = LISTAR_PEDIDOS;
			
		}

		else {
			avancar = LISTAR_PEDIDOS;
		}

		RequestDispatcher pagina = request.getRequestDispatcher(avancar);
		pagina.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id_pedido = request.getParameter("id_pedido");
		String dataPedido = request.getParameter("dataPedido");
		String id_pessoa = request.getParameter("id_pessoa");

		// Se o id_pessoa e vazio, criamos. Senao, atualizamos um pedido existente
		if (id_pedido.isEmpty()) {

			Pedido pedido = new Pedido();

			// Formatar data antes de salvar
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate localDate = LocalDate.parse(dataPedido, formato);
			pedido.setDataPedido(Date.valueOf(localDate));

			Pessoa pessoa = pessoaDAO.findbyId(Integer.parseInt(id_pessoa));
			pedido.setPessoa(pessoa);

			pedidoDAO.add(pedido);

			request.setAttribute("listaPedidos", pedidoDAO.findByAll());

			RequestDispatcher pagina = request.getRequestDispatcher(LISTAR_PEDIDOS);
			pagina.forward(request, response);

		} else {

			Pedido pedido = new Pedido();
			pedido.setId(Integer.parseInt(id_pedido));

			// Formatar data antes de salvar
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate localDate = LocalDate.parse(dataPedido, formato);
			pedido.setDataPedido(Date.valueOf(localDate));
			
			Pessoa pessoa = pessoaDAO.findbyId(Integer.parseInt(id_pessoa));
			pedido.setPessoa(pessoa);

			pedidoDAO.updateDataById(pedido);

			request.setAttribute("listaPedidos", pedidoDAO.findByAll());

			RequestDispatcher pagina = request.getRequestDispatcher(LISTAR_PEDIDOS);
			pagina.forward(request, response);
		}
	}

}
