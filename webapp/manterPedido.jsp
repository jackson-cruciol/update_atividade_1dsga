<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manter Pedido</title>
</head>
<body>
	<h1>Manter Pedido</h1>
	<form name="manterPedido" action="PedidoController" method="POST">
		<table>
			<tr>
				<td>ID</td>
				<td><input type="text" readonly="readonly" name="id_pedido"
					size="10" maxlength="10" value="<c:out value="${pedido.id}" />" /></td>
			</tr>
			<tr>
				<td>Data Pedido</td>
				<td><input type="text" name="dataPedido" size="50"
					maxlength="250"
					value="<fmt:formatDate value="${pedido.dataPedido}" pattern="dd/MM/yyyy"/>" /></td>
			</tr>
			<tr>
				<td>Pessoa</td>
				<td><select name="id_pessoa">
						<c:forEach items="${listaPessoas}" var="pessoa">
							<option value="${pessoa.id}">${pessoa.nome}</option>
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