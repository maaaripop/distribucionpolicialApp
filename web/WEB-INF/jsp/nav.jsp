<%-- 
    Document   : nav
    Created on : 04-oct-2016, 23:06:15
    Author     : Mariella
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
  <head>
    
  </head>
  <body>
    <div class="navbar navbar-inverse navbar-static-top" th:fragment="header">
        
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="">My project</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="inicio">Inicio</a></li>
            <li><a href="delitos">Delitos</a></li>
            <li><a href="comisarias">Comisarias</a></li>
            <li><a href="mapa">Mapa de Distribución</a></li>
            <li><a href="configuracion">Configuraciones</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            
            <li>
              <a href="inicio" >Cerrar Sesión</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </body>
</html>