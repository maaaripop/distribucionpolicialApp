<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Distribución</title>

        <script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAITDKVsmqWFfcBXcEszV0ZMkCj9tJTbns"></script>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script>
            var latOrig = -12.117222;
            var lngOrig = -77.020556;
            var orig = new google.maps.LatLng(latOrig, lngOrig);
            var lstComisarias = [];
            var idDistrito=parseInt(<c:out value="${idDistrito}"/>);
            var flag=true;
            var lstrect= [];
            var mapProp = {
                    center: orig,
                    zoom: 14,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
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
            function refrescarMapa(){
                var e=document.getElementById("distrito");
                idDistrito= e.options[e.selectedIndex].value;
                initialize();
            }  
            function initialize() {
                
                var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
                var marker, i;
                var j;
                var cantV=parseInt(<c:out value="${cantV}"/>);
                var cantH=parseInt(<c:out value="${cantH}"/>);
                var rect;
                
                <c:forEach varStatus="j" var="lats" items="${latLng}" >
                        <c:forEach varStatus="i" var="longs" items="${lats}" >
                            <c:if test="${i.index<=cantV-2 && j.index<=cantH-2 }">
                                if(${distritos[j.index][i.index]}==idDistrito){ 
                                    var rect= new google.maps.Rectangle({
                                        map: map,
                                        bounds: new google.maps.LatLngBounds(
                                        new google.maps.LatLng(${latLng[j.index][i.index][0]},${latLng[j.index][i.index][1]}),        
                                        new google.maps.LatLng(${latLng[j.index+1][i.index+1][0]},${latLng[j.index+1][i.index+1][1]})
                                        )                         
                                    });
                                    lstrect.push(rect);
                                }
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                $('#sol1').on('click', function() {
                    
                    var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
                    <c:set var="distritos" value="${distritos}" />
                    <c:set var="vehiculosSolucion1" value="${vehiculosSolucion1}" />  
                    <c:forEach varStatus="j" var="lats" items="${latLng}" >
                        <c:forEach varStatus="i" var="longs" items="${lats}" >
                            <c:if test="${i.index<=cantV-2 && j.index<=cantH-2 }">
                                if(${distritos[j.index][i.index]}==idDistrito){ 
                                    var rect= new google.maps.Rectangle({
                                        map: map,
                                        bounds: new google.maps.LatLngBounds(
                                        new google.maps.LatLng(${latLng[j.index][i.index][0]},${latLng[j.index][i.index][1]}),        
                                        new google.maps.LatLng(${latLng[j.index+1][i.index+1][0]},${latLng[j.index+1][i.index+1][1]})
                                        )                         
                                    });
                                    rect.addListener('click', function() {
                                        infoWindow = new google.maps.InfoWindow();
                                        var ne = this.getBounds().getCenter();
                                        var placa = '${vehiculosSolucion1[j.index][i.index]}';
                                        var nombre= "Vehiculo Asigado : " + placa;
                                        if(placa==" ") nombre= "No está asignado"
                                        infoWindow.setContent(nombre);
                                        infoWindow.setPosition(ne);
                                        infoWindow.open(map);
                                    });
                                }
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                
                });
                
                $('#sol2').on('click', function() {
                    var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
                    <c:set var="distritos" value="${distritos}" />
                    <c:set var="vehiculosSolucion2" value="${vehiculosSolucion2}" />  
                    <c:forEach varStatus="j" var="lats" items="${latLng}" >
                        <c:forEach varStatus="i" var="longs" items="${lats}" >
                            <c:if test="${i.index<=cantV-2 && j.index<=cantH-2 }">
                                if(${distritos[j.index][i.index]}==idDistrito){ 
                                    var rect= new google.maps.Rectangle({
                                        map: map,
                                        bounds: new google.maps.LatLngBounds(
                                        new google.maps.LatLng(${latLng[j.index][i.index][0]},${latLng[j.index][i.index][1]}),        
                                        new google.maps.LatLng(${latLng[j.index+1][i.index+1][0]},${latLng[j.index+1][i.index+1][1]})
                                        )                         
                                    });
                                    rect.addListener('click', function() {
                                        infoWindow = new google.maps.InfoWindow();
                                        var ne = this.getBounds().getCenter();
                                        var placa = '${vehiculosSolucion2[j.index][i.index]}';
                                        var nombre= "Vehiculo Asigado : " + placa;
                                        if(placa==" ") nombre= "No está asignado"
                                        infoWindow.setContent(nombre);
                                        infoWindow.setPosition(ne);
                                        infoWindow.open(map);
                                    });
                                }
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                
                });
                
                $('#sol3').on('click', function() {
                    var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
                    <c:set var="distritos" value="${distritos}" />
                    <c:set var="vehiculosSolucion3" value="${vehiculosSolucion3}" />  
                    <c:forEach varStatus="j" var="lats" items="${latLng}" >
                        <c:forEach varStatus="i" var="longs" items="${lats}" >
                            <c:if test="${i.index<=cantV-2 && j.index<=cantH-2 }">
                                if(${distritos[j.index][i.index]}==idDistrito){ 
                                    var rect= new google.maps.Rectangle({
                                        map: map,
                                        bounds: new google.maps.LatLngBounds(
                                        new google.maps.LatLng(${latLng[j.index][i.index][0]},${latLng[j.index][i.index][1]}),        
                                        new google.maps.LatLng(${latLng[j.index+1][i.index+1][0]},${latLng[j.index+1][i.index+1][1]})
                                        )                         
                                    });
                                    rect.addListener('click', function() {
                                        infoWindow = new google.maps.InfoWindow();
                                        var ne = this.getBounds().getCenter();
                                        var placa = '${vehiculosSolucion3[j.index][i.index]}';
                                        var nombre= "Vehiculo Asigado : " + placa;
                                        if(placa==" ") nombre= "No está asignado"
                                        infoWindow.setContent(nombre);
                                        infoWindow.setPosition(ne);
                                        infoWindow.open(map);
                                    });
                                }
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                
                });
                
                  


            }
            google.maps.event.addDomListener(window, 'load', initialize);

        </script>


    </head>

    <body>
       
        <jsp:include page="nav.jsp"/>
        <div class="container">
        <h1>Mapa de distribución</h1>
        <div class="row" style="margin-top: 10px;">
            <div class="form-group">
                <div class="col-md-3 col-sm-3"></div>
                <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Distrito:</label>
                <div class="col-md-3 col-sm-3">
                    <select id="distrito"  onchange="refrescarMapa()">
                        <option value="0" label="--- Seleccione ---"/>                                                
                        <c:forEach var="d" items="${distritolst}">
                            <option value="${d.getIdDistrito()}" label="${d.getNombre()}"/>
                        </c:forEach>                           
                    </select>                               
                </div>    
            </div>
        </div>   
        
        <div class="row" style="margin-top: 10px;">
            <div class="form-group">
                <div class="col-md-3 col-sm-3">
                </div>    
                <div class="col-md-6 col-sm-6">
                    <table class="table table-bordered" id="opciones">
                        <thead>
                          <tr>
                            <th>Solución</th>
                            <th></th>    
                          </tr>
                        </thead>
                        <tbody>
                          <tr>
                            <td>Solución 1</td>
                            <td><input type="radio" id="sol1"></td>
                          </tr>
                          <tr>
                            <td>Solución 2</td>
                            <td><input type="radio" id="sol2"></td>
                          </tr>
                          <tr>
                            <td>Solución 3</td>
                            <td><input type="radio" id="sol3"></td>
                          </tr>
                        </tbody>
                    </table>                              
                </div>    
                <div class="col-md-3 col-sm-3">
                </div>   
            </div>
        </div>  
 
        <div class="row"> 
            <div class="col-md-12">
                <div id="googleMap" style="width:1100px;height:380px;"></div>
            </div>
        </div>
        </div>
    </body>
</html>

