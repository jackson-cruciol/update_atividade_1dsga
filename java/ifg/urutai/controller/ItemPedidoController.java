package ifg.urutai.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ifg.urutai.dao.ItemPedidoDAO;
import ifg.urutai.dao.PedidoDAO;
import ifg.urutai.dao.ProdutoDAO;
import ifg.urutai.model.ItemPedido;
import ifg.urutai.model.Pedido;
import ifg.urutai.model.Produto;

/**
 * Servlet implementation class ItemPedidoController
 */
@WebServlet("/ItemPedidoController")
public class ItemPedidoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ItemPedidoDAO itemPedidoDAO;
	private PedidoDAO pedidoDAO;
	private ProdutoDAO produtoDAO;
	private static final String MANTER_ITEM_PEDIDO = "manterItemPedido.jsp";
	private static final String LISTAR_ITEM_PEDIDOS = "listarItemPedidos.jsp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ItemPedidoController() {
		super();
		itemPedidoDAO = new ItemPedidoDAO();
		pedidoDAO = new PedidoDAO();
		produtoDAO = new ProdutoDAO();
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
			request.setAttribute("listaProdutos", produtoDAO.findAllProduto());
			String id_pedido = request.getParameter("id_pedido");
			request.setAttribute("pedido", pedidoDAO.findbyIdPedido(Integer.parseInt(id_pedido)));
			avancar = MANTER_ITEM_PEDIDO;

		} else if (acao.equalsIgnoreCase("atualizar")) {

			String id_item_pedido = request.getParameter("id_item_pedido");

			ItemPedido itemPedido = itemPedidoDAO.findbyId(Integer.parseInt(id_item_pedido));

			String id_pedido = request.getParameter("id_pedido");

			Pedido pedido = pedidoDAO.findbyIdPedido(Integer.parseInt(id_pedido));

			request.setAttribute("itemPedido", itemPedido);
			request.setAttribute("pedido", pedido);
			List<Produto> listaProdutos = new ArrayList<Produto>();
			listaProdutos.add(itemPedido.getProduto());
			request.setAttribute("listaProdutos", listaProdutos);

			avancar = MANTER_ITEM_PEDIDO;

		} else if (acao.equalsIgnoreCase("remover")) {

			int id_item_pedido = Integer.parseInt(request.getParameter("id_item_pedido"));
			ItemPedido itemPedido = itemPedidoDAO.findbyId(id_item_pedido);
			itemPedidoDAO.deleteById(itemPedido);

			List<ItemPedido> listaItemPedidos = itemPedidoDAO.findAllByIdPedido(id_item_pedido);
			request.setAttribute("listaItemPedidos", listaItemPedidos);

			avancar = LISTAR_ITEM_PEDIDOS;
		} 
		
		else if (acao.equalsIgnoreCase("detalhes")) {

			String id_pedido = request.getParameter("id_pedido");
			request.setAttribute("listaItemPedidos", itemPedidoDAO.findAllByIdPedido(Integer.parseInt(id_pedido)));
			request.setAttribute("pedido", pedidoDAO.findbyIdPedido(Integer.parseInt(id_pedido)));

			avancar = LISTAR_ITEM_PEDIDOS;
		}

		else {
			avancar = LISTAR_ITEM_PEDIDOS;
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
		String id_item_pedido = request.getParameter("id_item_pedido");
		String quantidade = request.getParameter("quantidade");
		String id_pedido = request.getParameter("id_pedido");
		String id_produto = request.getParameter("id_produto");

		// Se o id_item_pedido e vazio, criamos. Senao, atualizamos um item existente
		if (id_item_pedido.isEmpty()) {

			ItemPedido itemPedido = new ItemPedido();

			itemPedido.setQuantidade(Integer.parseInt(quantidade));

			Pedido pedido = pedidoDAO.findbyIdPedido(Integer.parseInt(id_pedido));
			itemPedido.setPedido(pedido);

			Produto produto = produtoDAO.findbyIdProduto(Integer.parseInt(id_produto));
			itemPedido.setProduto(produto);

			itemPedidoDAO.add(itemPedido);

			request.setAttribute("listaItemPedidos", itemPedidoDAO.findAllByIdPedido(Integer.parseInt(id_pedido)));

			RequestDispatcher pagina = request.getRequestDispatcher(LISTAR_ITEM_PEDIDOS);
			pagina.forward(request, response);

		} else {

			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setIdItemPedido(Integer.parseInt(id_item_pedido));
			itemPedido.setQuantidade(Integer.parseInt(quantidade));

			Pedido pedido = pedidoDAO.findbyIdPedido(Integer.parseInt(id_pedido));
			itemPedido.setPedido(pedido);

			Produto produto = produtoDAO.findbyIdProduto(Integer.parseInt(id_produto));
			itemPedido.setProduto(produto);

			itemPedidoDAO.updateQuantidadeById(itemPedido);

			request.setAttribute("listaItemPedidos", itemPedidoDAO.findAllByIdPedido(Integer.parseInt(id_pedido)));

			RequestDispatcher pagina = request.getRequestDispatcher(LISTAR_ITEM_PEDIDOS);
			pagina.forward(request, response);
		}
	}

}
