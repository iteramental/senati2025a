<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Formulario</title>
</head>
<body>
    <h1>Tiendas</h1>
    <form action="ModuloVentas" method="post">
	    <input type="hidden" id="formulario" name="formulario" value="datostienda"><br>
	    <label for="nombre">Nombre:</label>
	    <input type="text" id="nombre" name="nombre" value="${nombre}"><br>
	    <label for="direccion">Dirección:</label>
	    <input type="text" id="direccion" name="direccion" value="${direccion}"><br>
	    <label for="ubigeo">Ubigeo:</label>
	    <input type="text" id="ubigeo" name="ubigeo" value="${ubigeo}"><br>
	    <input type="submit" value="Enviar">
    </form>
</body>
</html>
