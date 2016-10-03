<%-- 
    Document   : inicio
    Created on : 01-oct-2016, 1:45:36
    Author     : Mariella
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Carga policial</title>
        
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script>
             $('#btnNuevoUsuario').on('click', function() {
                 alert("hola");
                 $('#userNew').modal('show');
             });
            
        </script>
    </head>
    <body>
        <div class="container">
        <br>    
        <h1 align=center>Herramienta de Distribución de Carga policial</h1>
        <br>
        <br>
        <br>
         
        <div class="modal fade" id="userNew" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Nuevo usuario</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row" style="margin-top: 10px;">
                                <div class="form-group">
                                    <div class="col-md-6 col-sm-6">
                                        <label for="middle-name" class="control-label">Correo electrónico:</label>
                                    </div>
                                    
                                    <div class="col-md-6 col-sm-6">
                                        <input type="text" class="form-control"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top: 10px;">
                                <div class="form-group">
                                    <div class="col-md-6 col-sm-6">
                                        <label for="middle-name" class="control-label">Contraseña:</label>
                                    </div>
                                    
                                    <div class="col-md-6 col-sm-6">
                                        <input type="password" class="form-control"/>
                                    </div>
                                </div>
                            </div>

                            
                            <div class="row" style="margin-top: 10px;">
                                <div class="form-group">
                                    <div class="col-md-6 col-sm-6">
                                        <label for="middle-name" class="control-label">Repetir contraseña:</label>
                                    </div>
                                    
                                    <div class="col-md-6 col-sm-6">
                                        <input type="password" class="form-control"/>
                                    </div>
                                </div>
                            </div>    
                        </div>
                        <div class="modal-footer">
                            <div class="form-group">
                                     <div class="row" style="margin-top: 10px;">
                                        <div class="col-md-6 col-sm-6">
                                            
                                        </div>
                                        <div class="col-md-6 col-sm-6" >
                                            <input type="submit" value="Guardar" class="form-control">
                                        </div>
                                     </div>
                                </div>
                                
                        </div>
                    </div>

                </div>
            </div>
        <div class="row" style="margin-top: 10px;">
            <div class="form-group">
                <div class="col-md-4 col-sm-4"></div>
                <div class="col-md-4 col-sm-4">
                    <input type="text" class="form-control" placeholder="Usuario"
                        />
                </div> 
                <div class="col-md-4 col-sm-4"></div>
            </div>
        </div> 
        
        <div class="row" style="margin-top: 10px;">
            <div class="form-group">
                <div class="col-md-4 col-sm-4"></div>
                <div class="col-md-4 col-sm-4">
                    <input type="password" class="form-control"  placeholder="Contraseña"
                        />
                </div> 
                <div class="col-md-4 col-sm-4"></div>
            </div>
        </div> 
        
        <div class="row" style="margin-top: 10px;">
            <div class="form-group">
                <div class="col-md-4 col-sm-4"></div>
                <div class="col-md-4 col-sm-4">
                    <input type="button" value="Iniciar Sesión" class="form-control" 
                        />
                </div>
                
                
                <div class="col-md-4 col-sm-4"></div>
            </div>
        </div> 
        <div class="row" style="margin-top: 10px;">
            <div class="form-group">
                <div class="col-md-4 col-sm-4"></div>
                <div class="col-md-4 col-sm-4">
                    
                    <p><a data-toggle="modal" href="#userNew" >¿No tienes cuenta? Créate una!</a></p>
                        
                </div>
                
                
                <div class="col-md-4 col-sm-4"></div>
            </div>
        </div> 
        
        
        </div>
        
             
        
    </body>
</html>
