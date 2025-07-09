<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Formulario</title>
</head>
<body>
    <h1>Vendedores</h1>
    <form action="ModuloVentas" method="post">
	    <input type="hidden" id="formulario" name="formulario" value="datosvendedor"><br>
	    <label for="nombre">Nombre:</label>
	    <input type="text" id="nombre" name="nombre" value="${nombre}"><br>
	    <label for="direccion">Dirección:</label>
	    <input type="text" id="direccion" name="direccion" value="${direccion}"><br>
	    <label for="tipodocumento">Tipo de Documento:</label>
	    <input type="text" id="tipodocumento" name="tipodocumento" value="${tipodocumento}"><br>
	    <label for="numerodocumento">Número de Documento:</label>
	    <input type="text" id="numerodocumento" name="numerodocumento" value="${numerodocumento}"><br>
		<label for="id_tienda">Tienda:</label>
        <select id="id_tienda" name="id_tienda" required>
            <option value="">Seleccione una tienda</option>
            <c:forEach var="tienda" items="${tiendas}">
                <option value="${tienda.id}">${tienda.nombre}</option>
            </c:forEach>
        </select><br><br>

	    <input type="submit" value="Enviar">
    </form>
</body>
</html>
