<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pedidos - Lista</title>
</head>
<body>
	<br>
	<br>
	<h1>LISTA DE PEDIDOS</h1>
	<br>
	<br>
	<form name="buscarPedido" action="PedidoController" method="GET">
		<table>
			<tr>
				<td>ID</td>
				<td><input type="hidden" name="acao" value="buscarPorId" /></td>
				<td><input type="text" name="id_pedido" size="10"
					maxlength="10" /></td>
				<td><input type="submit" value="Buscar" /></td>
			</tr>
		</table>
	</form>
	<h1>Manter Pedidos</h1>
	<form name="listarPedidos" action="PedidoController" method="GET">
		<table style="width: 90%" border="1">
			<thead>
				<tr>
					<td>ID</td>
					<td>Data Pedido</td>
					<td>Nome Pessoa</td>
					<td colspan="3">Ação</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listaPedidos}" var="pedido">
					<tr>
						<td><c:out value="${pedido.id}" /></td>
						<td><fmt:formatDate value="${pedido.dataPedido}"
								pattern="dd/MM/yyyy" /></td>
						<td><c:out value="${pedido.pessoa.nome}" /></td>
						<td><a
							href="ItemPedidoController?acao=detalhes&id_pedido=<c:out value="$
{pedido.id}"/>">
								Detalhes </a></td>
						<td><a
							href="PedidoController?acao=atualizar&id_pedido=<c:out value="${pedido.id}"/>
&dataPedido=<c:out value="${pedido.dataPedido}"/>&id_pessoa=<c:out
value="${pedido.pessoa.id}"/> ">
								Alterar </a></td>
						<td><a
							href="PedidoController?acao=remover&id_pedido=<c:out value="${pedido.id}"/>"
							onclick="return confirm('Confirma a exclusão?')">Excluir </a></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan='6'><a href="PedidoController?acao=criar">Incluir
							Novo Pedido </a></td>
				</tr>
			</tfoot>
		</table>
	</form>
</body>
<p>
	<a href="../LojaWeb/index.html"> VOLTAR MENU</a>
</p>
</body>
</html>