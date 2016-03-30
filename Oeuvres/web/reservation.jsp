<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div  class="col-md-8 col-md-offset-1">
    <h1 align='center'>R�servation d'une oeuvre</h1>
    <form class="form-signin form-horizontal" role="form" action="enregistrerReservation.res?id=${oeuvreR.id_oeuvre}" method="post">
        <div class="form-group">
            <label class="col-md-3 control-label">Titre : </label>
            <label class="col-md-6 form-control-static">${oeuvreR.titre}</label>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">Prix : </label>
            <label class="col-md-6 form-control-static">${oeuvreR.prix}</label>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">Date r�servation : </label>
            <div class="col-md-3">
                    <input type="text" name="txtDate" id="txtDate" value="" class="form-control" placeholder="AAAA-MM-JJ" required/>
            </div>
        </div>            
        <div class="form-group">
            <label class="col-md-3 control-label">Adh�rent : </label>
            <div class="col-sm-6 col-md-3">
                <select class='form-control' name='lstAdherents' required>
                    <c:forEach var="adherent" items="${lstAdherentsR}">
                        <option value="${adherent.id_adherent}">${adherent.prenom_adherent} ${adherent.nom_adherent}</option>
                    </c:forEach>
                </select>
            </div>
        </div>             
        <div class="form-group">
            <div class="col-md-4 col-md-offset-4">
                <button type="submit" class="btn btn-default btn-primary"><span class="glyphicon glyphicon-log-in"></span> Valider</button>
            </div>
        </div>
    </form>
  <script>
  $(function() {
    $( "#txtDate" ).datepicker({dateFormat: "yy-mm-dd"});
  });
  </script>        
</div>