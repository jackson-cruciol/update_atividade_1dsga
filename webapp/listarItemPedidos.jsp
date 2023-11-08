<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Item Pedidos - Lista</title>
</head>
<body>
<br>
<br>
<h1>LISTA DE ITEM PEDIDOS</h1>
<br>
<br>
<table>
<tr>
<td>ID Pedido</td>
<td><input type="text" readonly="readonly" name="id_pedido"
size="10" maxlength="10" value="<c:out value="${pedido.id}" />" /></td>
</tr>
</table>
<h1>Manter Item Pedidos</h1>
<form name="listarItemPedidos" action="ItemPedidoController"
method="GET">
<table style="width: 90%" border="1">
<thead>
<tr>
<td>ID Item</td>
<td>Quantidade</td>
<td>Nome Pessoa</td>
<td>Nome Produto</td>
<td colspan="5">Ação</td>
</tr>
</thead>
<tbody>
<c:forEach items="${listaItemPedidos}" var="itemPedido">
<tr>
<td><c:out value="${itemPedido.id}" /></td>
<td><c:out value="${itemPedido.quantidade}" /></td>
<td><c:out value="${itemPedido.pedido.pessoa.nome}" /></td>
<td><c:out value="${itemPedido.produto.nome}" /></td>
<td><a
href="ItemPedidoController?acao=atualizar&id_item_pedido=<c:out value="$
{itemPedido.id}"/>
&quantidade=<c:out value="${itemPedido.quantidade}"/>&id_pedido=<c:out
value="${itemPedido.pedido.id}"/>&id_produto=<c:out
value="${itemPedido.produto.id}"/> ">
Alterar </a></td>
<td><a
href="ItemPedidoController?acao=remover&id_item_pedido=<c:out value="$
{itemPedido.id}"/>"
onclick="return confirm('Confirma a exclusão?')">Excluir </a></td>
</tr>
</c:forEach>
</tbody>
<tfoot>
<tr>
<td colspan='7'><a
href="ItemPedidoController?acao=criar
&id_pedido=<c:out value="${pedido.id}"/> ">Incluir
Novo Item Pedido </a></td></tr>
</tfoot>
</table>
</form>
</body>
<p>
<a href="../LojaWeb/PedidoController?acao=listarTodos"> VOLTAR</a>
</p>
</body>
</html>