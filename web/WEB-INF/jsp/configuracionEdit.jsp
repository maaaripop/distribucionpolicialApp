<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Configuración</title>
        
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script>
            
        </script>

        
    </head>

    <body>
        
        <jsp:include page="nav.jsp"/>
        <div class="container">
        
        <h2>Configuración</h2>
        
        <br>
        
            <div class="row" style="margin-top: 10px;">
                <div class="form-group">
                    <div class="col-md-2 col-sm-2">

                    </div>
                    <div class="col-md-4 col-sm-4">
                        <label for="middle-name" class="control-label">Coeficiente de Relajación:</label>
                    </div>

                    <div class="col-md-2 col-sm-2">
                        <input type="text" class="form-control"/>
                    </div>
                </div>
            </div>

            <div class="row" style="margin-top: 10px;">
                <div class="form-group">
                    <div class="col-md-2 col-sm-2">

                    </div>
                    <div class="col-md-4 col-sm-4">
                        <label for="middle-name" class="control-label">Factor Smoothing:</label>
                    </div>

                    <div class="col-md-2 col-sm-2">
                        <input type="text" class="form-control"/>
                    </div>
                </div>
            </div>

            <div class="row" style="margin-top: 10px;">
                <div class="form-group">
                    
                    <div class="col-md-2 col-sm-2">

                    </div>
                    <div class="col-md-4 col-sm-4">

                        <label for="middle-name" class="control-label">Cantidad de Meses</label>
                    </div>

                    <div class="col-md-2 col-sm-2">
                        <input type="text" class="form-control"/>

                    </div>
                </div>
            </div>

            <div class="row" style="margin-top: 10px;">
                <div class="form-group">
                    <div class="col-md-2 col-sm-2">

                    </div>
                    <div class="col-md-4 col-sm-4">
                        <label for="middle-name" class="control-label">Turno</label>
                    </div>

                    <div class="col-md-2 col-sm-2">
                        <input type="text" class="form-control"/>
                    </div>
                </div>
            </div>
        
        <br>
        <br>
            <div class="row" style="margin-top: 10px;">
                <div class="col-md-6 col-sm-6">

                </div>
                <div class="col-md-2 col-sm-2">
                     <input type="submit" value="Guardar" class="form-control">
                </div>
                <div class="col-md-2 col-sm-2">
                    
                </div>
                <div class="col-md-2 col-sm-2">
                   
                </div>
             </div>
            
        </div>
                        
        </div>
    </body>
</html>

