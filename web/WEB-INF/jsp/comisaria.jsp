<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comisaria</title>

        <script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAITDKVsmqWFfcBXcEszV0ZMkCj9tJTbns"></script>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script>
            var latOrig = -12.117222;
            var lngOrig = -77.020556;
            var orig = new google.maps.LatLng(latOrig, lngOrig);
            var lstComisarias = [];
            function initialize() {
                var mapProp = {
                    center: orig,
                    zoom: 14,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
                var marker, i;
                var j;
                var cantV=parseInt(<c:out value="${cantV}"/>);
                var cantH=parseInt(<c:out value="${cantH}"/>);
                var rect;
                <c:forEach  items="${comisariaLst}" var="comisaria" >
                    var comisaria=[];
                    comisaria.push(<c:out value="${comisaria.idComisaria}"/>);
                    comisaria.push(<c:out value="${comisaria.latitud}"/>);
                    comisaria.push(<c:out value="${comisaria.longitud}"/>);
                    comisaria.push('<c:out value="${comisaria.nombre}"/>');
                    comisaria.push('<c:out value="${comisaria.cantPatrulla}"/>');
                    lstComisarias.push(comisaria);
                </c:forEach>
		 var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';	 
                 for (i = 0; i < lstComisarias.length; i++) {
                    marker = new google.maps.Marker({
                        position: new google.maps.LatLng(lstComisarias[i][1], lstComisarias[i][2]),
                        map: map,
                        title:  lstComisarias[i][3],
                        icon: 'https://maps.google.com/mapfiles/kml/shapes/ranger_station.png'
                    });
                    google.maps.event.addListener(marker, 'click', (function (marker, i) {
                        return function () {
                            $('#policeView').modal('show');
                            $("#policeView #police-name").html(lstComisarias[i][3]);
                            $("#policeView #vehiculePolice").html(lstComisarias[i][4]);
                            $("#policeView #vehicule").html(0);
                            
                        }
                    })(marker, i));

                  }  
                  


            }
            google.maps.event.addDomListener(window, 'load', initialize);

        </script>


    </head>

    <body>
        <div class="container">

        <h1>Comisarias</h1>
        
 
        <div class="row"> 
            <div class="col-md-12">
                <div id="googleMap" style="width:1100px;height:380px;"></div>
            </div>
        </div>
        
        <!-- Modal comisaria ver --> 
            <div class="modal fade" id="policeView" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Comisaria</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row" style="margin-top: 10px;">
                                <div class="form-group">
                                    <div class="col-md-3 col-sm-3"></div>
                                    <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Nombre:</label>
                                    <div class="col-md-3 col-sm-3 col-xs-12" id="police-name">
                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top: 10px;">
                                <div class="form-group">
                                    <div class="col-md-3 col-sm-3"></div>
                                    <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Cantidad patrulleros:</label>
                                    <div class="col-md-3 col-sm-3 col-xs-12" id="vehiculePolice">

                                    </div>
                                </div>
                            </div>

                            <div class="row" style="margin-top: 10px;">
                                <div class="form-group">
                                    <div class="col-md-3 col-sm-3"></div>
                                    <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Cantidad serenazgos</label>
                                    <div class="col-md-3 col-sm-3 col-xs-12" id="vehicule">

                                    </div>
                                </div>
                            </div>
                            
                            
                            <div class="row" style="margin-top: 10px;">
                                <div class="form-group">
                                    <div class="col-md-12 col-sm-12">
                                        <table class="table table-bordered">
                                            <thead>
                                              <tr>
                                                <th>Patrullas</th>
                                                <th>Serenazgos</th>
                                              </tr>
                                            </thead>
                                        </table> 
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
        </div>
    </body>
</html>

