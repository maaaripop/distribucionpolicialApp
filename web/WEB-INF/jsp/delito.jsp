<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>

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
        var j;
        
        function initialize() {
              var mapProp = {
                center:orig,
                zoom:14,
                mapTypeId:google.maps.MapTypeId.ROADMAP
              };
              var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
              /*var rect= new google.maps.Rectangle({
                 map: map,
                 bounds: new google.maps.LatLngBounds(
                    orig,
                    new google.maps.LatLng(-12,-77)),
                 editable: true,
                 draggable:true      
                  
                  
              });
              
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
              var marker,i; 
                 for (i = 0; i < lstDelitos.length; i++) {  

                    var delito = [];
                    delito.push(<c:out value="${delito.idDelito}"/>);
                    delito.push(<c:out value="${delito.latitud}"/>);
                    delito.push(<c:out value="${delito.longitud}"/>);
                    delito.push('<c:out value="${delito.tipodelito.nombre}"/>');
                    delito.push('<c:out value="${delito.turno.horaInicio}"/>'.concat("- ", '<c:out value="${delito.turno.horaFin}"/>'));
                    delito.push('<c:out value="${delito.fecha}"/>'.substring(0, 10));
                    lstDelitos.push(delito);
                }
            var j;

            function initialize() {
                var mapProp = {
                    center: orig,
                    zoom: 14,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
                var marker, i;
                for (i = 0; i < lstDelitos.length; i++) {

                    marker = new google.maps.Marker({
                        position: new google.maps.LatLng(lstDelitos[i][1], lstDelitos[i][2]),
                        map: map
                    });
                    google.maps.event.addListener(marker, 'click', (function (marker, i) {
                        return function () {
                            $('#crimeView').modal('show');
                            $("#crimeView #crime-type").html(lstDelitos[i][3]);
                            $("#crimeView #turn").html(lstDelitos[i][4]);
                            $("#crimeView #date").html(lstDelitos[i][5]);
                        }
                    })(marker, i));

                  }
                
                google.maps.event.addListener(map, 'click', function() {
                       // $('#crimeNew').modal('show');
                       alert("V: " +  <c:out value="${cantV}"/> + " H: "  + <c:out value="${cantH}"/> );
                 });  

                }

                google.maps.event.addListener(map, 'click', function () {
                    $('#crimeNew').modal('show');
                });


            }
            google.maps.event.addDomListener(window, 'load', initialize);

        </script>


    </head>

    <body>
        <div class="container">

        <div id="googleMap" style="width:500px;height:380px;"></div>
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
                                    <div class="col-md-3 col-sm-3"></div>
                                    <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Tipo de delito:</label>
                                    <div class="col-md-3 col-sm-3 col-xs-12" id="crime-type">
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top: 10px;">
                                <div class="form-group">
                                    <div class="col-md-3 col-sm-3"></div>
                                    <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Fecha:</label>
                                    <div class="col-md-3 col-sm-3 col-xs-12" id="date">
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top: 10px;">
                                <div class="form-group">
                                    <div class="col-md-3 col-sm-3"></div>
                                    <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Turno:</label>
                                    <div class="col-md-3 col-sm-3 col-xs-12" id="turn">

                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top: 10px;">
                                <div class="form-group">
                                    <div class="col-md-3 col-sm-3"></div>
                                    <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Comentarios:</label>
                                    <div class="col-md-3 col-sm-3 col-xs-12" id="comment">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                        </div>
                    </div>

                </div>
            </div>

            <!-- Modal delito nuevo -->   
            <div class="modal fade" id="crimeNew" role="dialog">
                <div class="modal-dialog">
                    <c:url var="agregarDelito" value="/mapas/agregarDelito" ></c:url>
                    <form:form action="${agregarDelito}" commandName="delito">
                        <!-- Modal content-->
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title">Nuevo delito</h4>
                            </div>
                            <div class="modal-body">
                                <div class="row" style="margin-top: 10px;">
                                    <div class="form-group">
                                        <div class="col-md-3 col-sm-3"></div>
                                        <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Tipo de delito:</label>
                                        <div class="col-md-3 col-sm-3">
                                            <form:select path="tipodelito.idTipoDelito">
                                                <form:option value="NONE" label="--- Seleccione ---"/>                                                
                                                <c:forEach var="tipodelito" items="${tipodelitoLst}">
                                                    <form:option value="${tipodelito.getIdTipoDelito()}" label="${tipodelito.getNombre()}"/>
                                                </c:forEach>                           
                                            </form:select>                               
                                        </div>    
                                    </div>
                                </div>

                                <div class="row" style="margin-top: 10px;">
                                    <div class="form-group">
                                        <div class="col-md-3 col-sm-3"></div>
                                        <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Fecha:</label>
                                        <div class="col-md-3 col-sm-3 col-xs-12" id="date">
                                            <form:input type="date" path="fecha"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="row" style="margin-top: 10px;">
                                    <div class="form-group">
                                        <div class="col-md-3 col-sm-3"></div>
                                        <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Turno:</label>
                                        <div class="col-md-3 col-sm-3">
                                            <form:select path="turno.idTurno">
 x                                               <form:option value="NONE" label="--- Seleccione ---"/>                           
                                                <c:forEach var="turno" items="${turnoLst}">
                                                    <form:option value="${turno.getIdTurno()}" label="${turno.getHoraInicio()}"/>
                                                </c:forEach>
                                            </form:select>
                                        </div>    

                                    </div>
                                </div>      


                                <div class="row" style="margin-top: 10px;">
                                    <div class="form-group">
                                        <div class="col-md-3 col-sm-3"></div>
                                        <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Descripción:</label>
                                        <div class="col-md-3 col-sm-3 col-xs-12" >
                                            <form:input path="descripcion" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                                <input type="submit" value="OK">
                            </div>
                        </div>
                    </form:form>

                </div>
            </div>  
        </div>
    </body>
</html>

