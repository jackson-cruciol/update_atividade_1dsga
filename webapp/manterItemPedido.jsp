<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manter Item Pedido</title>
</head>
<body>
	<h1>Manter Item Pedido</h1>
	<form name="manterItemPedido" action="ItemPedidoController"
		method="POST">
		<table>
			<tr>
				<td><input type="hidden" readonly="readonly"
					name="id_item_pedido" size="10" maxlength="10"
					value="<c:out value="${itemPedido.id}" />" /></td>
			</tr>
			<tr>
				<td>ID</td>
				<td><input type="text" readonly="readonly" name="id_pedido"
					size="10" maxlength="10" value="<c:out value="${pedido.id}" />" /></td>
			</tr>
			<tr>
				<td>Quantidade</td>
				<td><input type="text" name="quantidade" size="50"
					maxlength="250" value="<c:out value="${itemPedido.quantidade}"/>" /></td>
			</tr>
			<tr>
				<td>Nome Pessoa</td>
				<td><input type="text" readonly="readonly" name="nomePessoa"
					size="50" maxlength="250"
					value="<c:out value="${pedido.pessoa.nome}"/>" /></td>
			</tr>
			<tr>
				<td>Produto</td>
				<td><select name="id_produto">
						<c:forEach items="${listaProdutos}" var="produto">
							<option value="${produto.id}">${produto.nome}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><input type="submit" value="Salvar" /></td>
				<td><input type="button" value="Voltar"
					onclick="history.go(-1)" /></td>
			</tr>
		</table>
	</form>
</body>
</html>