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
            var lstDelitos = [];


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
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
                var marker, i;
                var j;
                var cantV=parseInt(<c:out value="${cantV}"/>);
                var cantH=parseInt(<c:out value="${cantH}"/>);
                <c:forEach varStatus="j" var="lats" items="${latLng}" >
                    <c:forEach varStatus="i" var="longs" items="${lats}" >
                        
                        <c:if test="${i.index<=cantV-2 && j.index<=cantH-2}">
                             var rect= new google.maps.Rectangle({
                                map: map,
                                bounds: new google.maps.LatLngBounds(
                                new google.maps.LatLng(${latLng[j.index][i.index][0]},${latLng[j.index][i.index][1]}),        
                                new google.maps.LatLng(${latLng[j.index+1][i.index+1][0]},${latLng[j.index+1][i.index+1][1]})
                                
                                )                         
                            });
                        </c:if>
                    </c:forEach>
                </c:forEach>
                



            }
            google.maps.event.addDomListener(window, 'load', initialize);

        </script>


    </head>

    <body>
        <div class="container">

        <h1>Mapa de distribución</h1>
        
        
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
 
            
           
        </div>
    </body>
</html>

