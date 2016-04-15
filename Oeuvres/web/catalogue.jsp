<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/bootstrap.jsp"/>
<c:import url="/menu.jsp"/>
<div  class="col-md-11 col-md-offset-1">
    <body class="body">       
        <div class="container">
            <h1 align='center'>Catalogue des oeuvres</h1>
            <div class="table-responsive">
                <table class="table table-hover table-condensed">
                    <thead>
                        <tr>
                            <th>Titre</th>
                            <th>Prix</th>
                            <th>Propriétaire</th>
                            <th></th>
                        </tr>  
                    </thead>
                    <tbody>
                        <c:forEach var="oeuvre" items="${lstOeuvresR}">
                            <tr>
                                <td>${oeuvre.titre}</td>
                                <td>${oeuvre.prix}</td>
                                <td>${oeuvre.proprietaire.prenom_proprietaire}    ${oeuvre.proprietaire.nom_proprietaire}</td>
                                <c:if test="${sessionScope.adminS != null}">
                                    <td>
                                        <a class="btn btn-success btn-xs glyphicon glyphicon-shopping-cart" href="reserver.res?id=${oeuvre.id_oeuvre}" title="Réserver"></a>

                                        <a class="btnModifier btn btn-info btn-xs glyphicon glyphicon-edit" href="modifier.oe?id=${oeuvre.id_oeuvre}" data-toggle="modal"data-target="#oeuvre" title="Modifier"></a>

                                        <button type="button" class="btn btn-danger btn-xs" title="Supprimer" data-toggle="modal" data-target="#articleDialog${oeuvre.id_oeuvre}"><span class="glyphicon glyphicon-remove"></span>
                                        </button>
                                        <div class="modal fade" id="articleDialog${oeuvre.id_oeuvre}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onClick="history.go(0)">&times;</button>
                                                        <h4 class="modal-title" id="myModalLabel">Confirmation requise</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        Voulez vous vraiment supprimer cette oeuvre ?
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                                                        <a href="supprimer.oe?id=${oeuvre.id_oeuvre}" class="btn btn-danger">Confirmer</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        </a></td>  
                                    </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>          
            </div>
        </div>
    </body>
    <!-- Modal -->
    <div class="modal fade" id="oeuvre" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">

        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->