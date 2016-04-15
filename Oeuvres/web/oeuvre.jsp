<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <c:import url="/bootstrap.jsp"/>
    <body>
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" onClick="history.go(0)">&times;</button>
                <h4 class="modal-title">${titre}</h4>
            </div>
            <div class="modal-body">
                <form class="form-signin form-horizontal" role="form" action="enregistrer.oe?id=${oeuvreR.id_oeuvre}" method="post">
                    <div class="form-group">
                        <label class="col-md-3 control-label">Titre : </label>
                        <div class="col-md-6">
                            <input type="text" name="txtTitre" value="${oeuvreR.titre}" class="form-control" placeholder="Titre de l'oeuvre" required autofocus>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">Prix : </label>
                        <div class="col-md-3">
                            <input type="text" step="any" name="txtPrix" id="prix" value="${oeuvreR.prix}"  class="form-control" placeholder="Prix de l'oeuvre" onKeyUp="nombre()" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">Proprietaire : </label>
                        <div class="col-md-3">
                            <select class='form-control' name='lProprietaires' required>
                                <c:forEach var="proprietaire" items="${lstProprietairesR}">
                                    <option value="${proprietaire.id_proprietaire}"<c:if test="${proprietaire.id_proprietaire == oeuvreR.id_proprietaire}"> SELECTED</c:if> >${proprietaire.prenom_proprietaire} ${proprietaire.nom_proprietaire}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>             
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-default btn-primary"><span class="glyphicon glyphicon-log-in"></span> Valider</button>
                    </div>
                </form>
            </div>
        </div>
</div>
</body>

