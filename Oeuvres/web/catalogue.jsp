<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div  class="col-md-11 col-md-offset-1">
    <h1 align='center'>Catalogue des oeuvres</h1>
    <table class="table table-bordered table-striped">
        <thead>
            <tr>
                <td>Titre</td>
                <td>Prix</td>
                <td>Prénom propriétaire</td>
                <td>Nom propriétaire</td>
                <td>Réserver</td>
                <td>modif</td>
                <td>Modifier</td>
                <td>Supprimer</td>                
            </tr>  
        </thead>
        <tbody>
            <c:forEach var="oeuvre" items="${lstOeuvresR}">
                <tr>
                    <td>${oeuvre.titre}</td>
                    <td>${oeuvre.prix}</td>
                    <td>${oeuvre.proprietaire.prenom_proprietaire}</td>
                    <td>${oeuvre.proprietaire.nom_proprietaire}</td>
                    <td><a class="btn btn-primary" href="reserver.res?id=${oeuvre.id_oeuvre}">Réserver</a></td>
                    <td><a href="modifier.oe?id=${oeuvre.id_oeuvre}" id="${oeuvre.id_oeuvre}" data-toggle="modal"data-target="#oeuvre">${oeuvre.id_oeuvre}</a></td>
                    
                    
                    <td><a class="btn btn-primary" href="modifier.oe?id=${oeuvre.id_oeuvre}">Modifier</a> 
                        <button class="btnModifier btn btn-info btn-xs glyphicon glyphicon-edit" onClick='editForm(this)' value='{{practitioner.id}}' title="Edit"></button></td> 
                   
                    <td>
                        <button type="button" class="btn btn-danger btn-xs" title="Delete" data-toggle="modal" data-target="#articleDialog${oeuvre.id_oeuvre}"><span class="glyphicon glyphicon-remove"></span>
                            </button>
                         <div class="modal fade" id="articleDialog${oeuvre.id_oeuvre}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                        <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
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
                </tr>
            </c:forEach>
        </tbody>
    </table>          
</div>
<!-- Modal -->
    <div class="modal fade" id="oeuvre" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content"  id="modal-content">
                <div class="modal-header">
                </div>
                <div class="modal-body">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
                    <button type="button" class="btn btn-primary" id="modalBtnModifier">Modifier</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
