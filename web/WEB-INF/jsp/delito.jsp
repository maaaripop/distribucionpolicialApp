<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mapa de delitos</title>
        
        <script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAITDKVsmqWFfcBXcEszV0ZMkCj9tJTbns"></script>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script>
            var latOrig = -12.117222;
            var lngOrig = -77.020556;
            var orig = new google.maps.LatLng(latOrig, lngOrig);
            var lstDelitos = [];
            <c:forEach  items="${delitoLst}" var="delito" >

                var delito=[];
                delito.push(<c:out value="${delito.idDelito}"/>);
                delito.push(<c:out value="${delito.latitud}"/>);
                delito.push(<c:out value="${delito.longitud}"/>);
                delito.push('<c:out value="${delito.tipodelito.nombre}"/>');
                delito.push('<c:out value="${delito.turno.horaInicio}"/>'.concat("- ",'<c:out value="${delito.turno.horaFin}"/>'));
                delito.push('<c:out value="${delito.fecha}"/>'.substring(0,10));
                lstDelitos.push(delito);
             </c:forEach>

            /*  
              google.maps.event.addListener(rect,"bounds_changed",function(){
                  
                    var bounds =rect.getBounds();
                    var NE = bounds.getNorthEast();
                    var SW = bounds.getSouthWest();
                    // North West
                    var NW = new google.maps.LatLng(NE.lat(),SW.lng());
                    // South East
                    var SE = new google.maps.LatLng(SW.lat(),NE.lng());

                    document.getElementById("NE").value=NE;
                    document.getElementById("SW").value=SW;
                    document.getElementById("NW").value=NW;
                    document.getElementById("SE").value=SE;
                  
              })
              */
              
            
            function initialize() {
                var mapProp = {
                    center: orig,
                    zoom: 14,
                    mapTypeId: google.maps.MapTypeId.ROADMAP,
                    scrollwheel:  false
                };
                var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
                var marker, i;
                var j;
                var cantV=parseInt(<c:out value="${cantV}"/>);
                var cantH=parseInt(<c:out value="${cantH}"/>);
                for (i = 0; i < lstDelitos.length; i++) {
                    marker = new google.maps.Marker({
                        position: new google.maps.LatLng(lstDelitos[i][1], lstDelitos[i][2]),
                        map: map,
                        title:  lstDelitos[i][3]
                    });
                    google.maps.event.addListener(marker, 'click', (function (marker, i) {
                        return function () {
                            $('#crimeView').modal('show');
                            $("#crimeView #crime-type").html(lstDelitos[i][3]);
                            $("#crimeView #turn").html(lstDelitos[i][4]);
                            $("#crimeView #date").html(lstDelitos[i][5]);
                            var geocoder = new google.maps.Geocoder();
                            var yourLocation = new google.maps.LatLng(lstDelitos[i][1], lstDelitos[i][2]);
                            geocoder.geocode({ 'latLng': yourLocation },(function(results, status){
                                if (results[0]) {
                                   // $("#crimeView #comment").html(results[0].formatted_address);
                                   // $("#crimeView #comment").html(results[0].address_components[2].short_name);
                                }
                                
                            }));
                        }
                    })(marker, i));

                  }
                
                google.maps.event.addListener(map, 'click', function(event) {
                            
                        $("#crimeNew #latitud").val(event.latLng.lat());
                        $("#crimeNew #longitud").val(event.latLng.lng());
                        $('#crimeNew').modal('show');
                        
                        
                       
                 });  


            }
            google.maps.event.addDomListener(window, 'load', initialize);

        </script>

        
    </head>

    <body>
        
        <jsp:include page="nav.jsp"/>
        <div class="container">
        
        <h2>Mapa de delitos</h2>
        
        
        <div class="row"> 
            <div class="col-md-12">
                <div id="googleMap" style="width:1100px;height:380px;"></div>
            </div>
        
        <!-- 
        <input type="text" id="NE" width="500px" />
        <input type="text" id="SW" width="500px" />
        <input type="text" id="NW" width="500px" />
        <input type="text" id="SE" width="500px" />
         --> 
 
            <!-- Modal delito ver --> 
            <div class="modal fade" id="crimeView" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Delito</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row" style="margin-top: 10px;">
                                <div class="form-group">
                                    <div class="col-md-6 col-sm-6">
                                        <label for="middle-name" class="control-label">Tipo de delito:</label>
                                    </div>
                                    
                                    <div class="col-md-6 col-sm-6"id="crime-type">
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top: 10px;">
                                <div class="form-group">
                                    <div class="col-md-6 col-sm-6">
                                        <label for="middle-name" class="control-label">Fecha:</label>
                                    </div>
                                    
                                    <div class="col-md-6 col-sm-6" id="date">
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top: 10px;">
                                <div class="form-group">
                                    <div class="col-md-6 col-sm-6">
                                        
                                        <label for="middle-name" class="control-label">Turno:</label>
                                    </div>
                                    
                                    <div class="col-md-6 col-sm-6" id="turn">

                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top: 10px;">
                                <div class="form-group">
                                    <div class="col-md-6 col-sm-6">
                                        <label for="middle-name" class="control-label">Comentarios:</label>
                                    </div>
                                    
                                    <div class="col-md-6 col-sm-6" id="comment">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            
                        </div>
                    </div>

                </div>
            </div>

            <!-- Modal delito nuevo -->   
            <div class="modal fade" id="crimeNew" role="dialog">
                <div class="modal-dialog">
                    <c:url var="agregarDelito" value="/delitos/nuevo" ></c:url>
                    <form:form action="${agregarDelito}" commandName="delito">
                    
                    
                        <!-- Modal content-->
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title">Nuevo delito</h4>
                            </div>
                            <form:hidden path="idDelito" />
                            <form:hidden path="latitud" id="latitud" />
                            <form:hidden path="longitud" id="longitud" />
                            <div class="modal-body">
                                <div class="row" style="margin-top: 10px;">
                                    <div class="form-group">
                                        <div class="col-md-6 col-sm-6">
                                            <label for="middle-name" class="control-label">Tipo de delito:</label>
                                            
                                        </div>
                                        
                                        <div class="col-md-6 col-sm-6">
                                            <form:select class="form-control" path="tipodelito.idTipoDelito">
                                                <form:option value="0" label="--- Seleccione ---"/>                                                
                                                <c:forEach var="tipodelito" items="${tipodelitoLst}">
                                                    <form:option value="${tipodelito.getIdTipoDelito()}" label="${tipodelito.getNombre()}"/>
                                                </c:forEach>                           
                                            </form:select>                               
                                        </div>    
                                    </div>
                                </div>

                                <div class="row" style="margin-top: 10px;">
                                    <div class="form-group">
                                        <div class="col-md-6 col-sm-6">
                                            <label for="middle-name" class="control-label">Fecha:</label>
                                        </div>
                                        
                                        <div class="col-md-6 col-sm-6"  id="date">
                                            <form:input class="form-control"type="date" path="fecha"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="row" style="margin-top: 10px;">
                                    <div class="form-group">
                                        <div class="col-md-6 col-sm-6">
                                            <label for="middle-name" class="control-label">Turno:</label>
                                        </div>
                                        <div class="col-md-6 col-sm-6">
                                            <form:select class="form-control" path="turno.idTurno">
                                                <form:option value="0" label="--- Seleccione ---"/>                           
                                                <c:forEach var="turno" items="${turnoLst}">
                                                    <form:option value="${turno.getIdTurno()}" label="${turno.getHoraInicio()}"/>
                                                </c:forEach>
                                            </form:select>
                                        </div>    

                                    </div>
                                </div>      


                                <div class="row" style="margin-top: 10px;">
                                    <div class="form-group">
                                        <div class="col-md-6 col-sm-6">
                                            <label for="middle-name" class="control-label">Descripción:</label>
                                        </div>
                                        
                                        <div class="col-md-6 col-sm-6" >
                                            <form:input path="descripcion" class="form-control"/>
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
                    </form:form>

                </div>
            </div>  
        </div>
    </body>
</html>

