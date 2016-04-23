<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/menu.jsp"/>
<c:import url="/bootstrap.jsp"/>
<div  class="col-md-11 col-md-offset-1">
    <body class="body">       
        <div class="container">
            <h1 align='center'>Liste des réservations</h1> 
            <c:if test="${requestScope.erreur != null}">
                <div class="alert alert-danger">
                    <strong>Erreur  ! ${requestScope.erreur} </strong> 
                </div>
            </c:if>
            
            <c:if test="${requestScope.succes != null}">
                <div class="alert alert-success">
                    <strong>${requestScope.succes} </strong> 
                </div>
            </c:if>
            <div class="table-responsive">
                <table class="table table-hover table-condensed">
                    <thead>
                        <tr>
                            <th>Titre</th>
                            <th>Date</th>
                            <th>Statut</th>
                            <th>Adhérent</th>

                            <th></th>

                        </tr>
                    </thead>
                    <tbody>        
                        <c:forEach var="reservation" items="${lstReservationsR}">
                            <tr>
                                <td>${reservation.oeuvre.titre}</td>
                                <td><fmt:formatDate value="${reservation.date_reservation}" type="date" pattern="dd-MM-yyyy"/></td>
                                <td>${reservation.statut}</td>
                                <td>${reservation.adherent.prenom_adherent}   ${reservation.adherent.nom_adherent}</td>

                                <c:if test="${sessionScope.adminS != null}"> 
                                    <c:if test="${reservation.statut == 'Attente'}">  
                                        <td><button type="button" class="btn btn-primary btn-xs" title="Confirmer" data-toggle="modal" data-target="#resDialog${reservation.id_oeuvre}"><span class="glyphicon glyphicon-ok"></span>
                                            </button>
                                            <div class="modal fade" id="resDialog${reservation.id_oeuvre}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onClick="history.go(0)">&times;</button>
                                                            <h4 class="modal-title" id="myModalLabel">Confirmation requise</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            Voulez vous vraiment confirmer cette réservation ?
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                                                            <a href="confirmerReservation.res?id=${reservation.id_oeuvre}&dateres='<fmt:formatDate value="${reservation.date_reservation}" type="date" pattern="yyyy-MM-dd"/>'" class="btn btn-primary">Confirmer</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${reservation.statut == 'Confirmée'}"> 
                                        <td><a class="btn btn-success btn-xs disabled" title="Confirmée"><span class="glyphicon glyphicon-ok"></span></a>
                                        </c:if>
                                        <button class="btn btn-danger btn-xs" data-toggle="modal" title="Supprimer" data-target="#articleDialog${reservation.id_oeuvre}<fmt:formatDate value="${reservation.date_reservation}" type="date" pattern="yyyy-MM-dd"/>"><span class="glyphicon glyphicon-remove"></span></button></td>
                            <div class="modal fade" id="articleDialog${reservation.id_oeuvre}<fmt:formatDate value="${reservation.date_reservation}" type="date" pattern="yyyy-MM-dd"/>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onClick="history.go(0)">&times;</button>
                                            <h4 class="modal-title" id="myModalLabel">Confirmation requise</h4>
                                        </div>
                                        <div class="modal-body">
                                            Voulez vous vraiment supprimer cette reservation ?
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                                            <a href="supprimerReservation.res?id=${reservation.id_oeuvre}&dateres='<fmt:formatDate value="${reservation.date_reservation}" type="date" pattern="yyyy-MM-dd"/>'" class="btn btn-danger">Confirmer</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        </tr>
                    </c:forEach> 
                    </tbody>
                </table>              
            </div>