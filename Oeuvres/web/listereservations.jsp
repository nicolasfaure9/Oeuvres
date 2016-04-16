<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/menu.jsp"/>
<c:import url="/bootstrap.jsp"/>
<div  class="col-md-11 col-md-offset-1">
      <body class="body">       
        <div class="container">
    <h1 align='center'>Liste des réservations</h1>             
    <table class="table table-bordered table-striped">
        <thead>
            <tr>
                <td>Titre</td>
                <td>Date</td>
                <td>Statut</td>
                <td>Prénom adhérent</td>
                <td>Nom adhérent</td>
                <td>Confirmer</td>
                <td>Supprimer</td>
            </tr>
        </thead>
        <tbody>        
            <c:forEach var="reservation" items="${lstReservationsR}">
                <tr>
                    <td>${reservation.oeuvre.titre}</td>
                    <td><fmt:formatDate value="${reservation.date_reservation}" type="date" pattern="dd-MM-yyyy"/></td>
                    <td>${reservation.statut}</td>
                    <td>${reservation.adherent.prenom_adherent}</td>
                    <td>${reservation.adherent.nom_adherent}</td>
                    <c:if test="${reservation.statut == 'Attente'}">  
                        <td><a class="btn btn-primary" href="confirmerReservation.res?id=${reservation.id_oeuvre}&dateres='<fmt:formatDate value="${reservation.date_reservation}" type="date" pattern="yyyy-MM-dd"/>'">Confirmer</a></td>
                    </c:if>
                    <c:if test="${reservation.statut == 'Confirmée'}"> 
                        <td><a class="btn btn-success disabled">Confirmée</a></td>
                    </c:if>
                    <td><a class="btn btn-danger" data-toggle="modal" data-target="#articleDialog${reservation.id_oeuvre}<fmt:formatDate value="${reservation.date_reservation}" type="date" pattern="yyyy-MM-dd"/>">Supprimer</a></td>
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
                </tr>
            </c:forEach> 
        </tbody>
    </table>              
</div>