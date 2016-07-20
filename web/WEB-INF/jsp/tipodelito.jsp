<%-- 
    Document   : tipodelito
    Created on : 19/07/2016, 12:06:17 AM
    Author     : USER
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Agregar tipo delitos</h1>        

        <c:url var="addAction" value="/tipodelito/add" ></c:url>

        <form:form action="${addAction}" commandName="tipodelito">
        <table>
            <c:if test="${!empty tipodelito.nombre}">
                <tr>
                    <td>
                <form:label path="idTipoDelito">
                    <spring:message text="ID"/>
                </form:label>
                </td>
                <td>
                <form:input path="idTipoDelito" readonly="true" size="8"  disabled="true" />
                <form:hidden path="idTipoDelito" />
                </td> 
                </tr>
            </c:if>
            <tr>
                <td>
            <form:label path="nombre">
                <spring:message text="Nombre"/>
            </form:label>
            </td>
            <td>
            <form:input path="nombre" />
            </td> 
            </tr>            
            <tr>
                <td colspan="2">
                    <c:if test="${!empty tipodelito.nombre}">
                        <input type="submit"
                               value="<spring:message text="Editar Tipo Delito" />"
                    </c:if>
                    <c:if test="${empty tipodelito.nombre}">
                        <input type="submit"
                               value="<spring:message text="Agregar Tipo Delito" />"
                    </c:if>
                </td>
            </tr>
        </table>	
    </form:form>
    <br>
    <h3>Lista de Tipo delito</h3>
    <c:if test="${!empty tipodelitoLst}">
        <table class="tg">
            <tr>
                <th width="80">ID</th>
                <th width="120">Tipo Delito</th>                
            </tr>
            <c:forEach items="${tipodelitoLst}" var="tipodelito">
                <tr>
                    <td>${tipodelito.idTipoDelito}</td>
                    <td>${tipodelito.nombre}</td>                    
                    <td><a href="<c:url value='tipodelitos/edit/${tipodelito.idTipoDelito}' />" >Editar</a></td>
                    <td><a href="<c:url value='tipodelitos/remove/${tipodelito.idTipoDelito}' />" >Eliminar</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>    

</body>
</html>
