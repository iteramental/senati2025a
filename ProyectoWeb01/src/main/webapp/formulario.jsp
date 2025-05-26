<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Formulario</title>
</head>
<body>
    <h1>Datos recibidos del Servidor</h1>
    <form action="RegistroVendedores" method="post">
	    <label for="nombre">Nombre:</label>
	    <input type="text" id="nombre" name="nombre" value="${nombre}"><br>
	    <label for="direccion">Dirección:</label>
	    <input type="text" id="direccion" name="direccion" value="${direccion}"><br>
	    <label for="tipodocumento">Tipo de Documento:</label>
	    <input type="text" id="tipodocumento" name="tipodocumento" value="${tipodocumento}"><br>
	    <label for="numerodocumento">Número de Documento:</label>
	    <input type="text" id="numerodocumento" name="numerodocumento" value="${numerodocumento}"><br>
	    <input type="submit" value="Enviar">
    </form>
</body>
</html>
