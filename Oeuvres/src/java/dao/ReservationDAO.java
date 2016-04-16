/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import modeles.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Epulapp
 */
public class ReservationDAO extends Dao{
        
     private Reservation reservation;
    public Reservation getReservation(){
        return this.reservation;
    }
    public ReservationDAO() {
    }

    public Reservation lire_reservation(int id_oeuvre, java.sql.Date date) throws Exception {
        Map mParams = new HashMap();
        Map mParam;
        Map mResults;
        try {
            String requete = "select * from reservation where id_oeuvre = ? and date_reservation = ?";
            mParam = new HashMap();
            mParam.put(1, id_oeuvre);
            
            mParam.put(2, date.toString());
            mParams.put(0, mParam);            
            mResults = lecture(requete, mParams);
            if (mResults.size() > 0) {
                Map mRecord = (Map)mResults.get(0);
                return(setProperties(mRecord));
            }  else {
                throw new Exception("reservation inconnue !");
            }
        } catch (Exception e) {
            throw e;
        } 
    }
        
    /**
     * Liste des Reservations
     * @return Collection de Reservations
     * @throws Exception 
     */
    public List<Reservation> listeReservations() throws Exception {
        // la méthode lecture() attend ce paramètre
        Map mParams = new HashMap();
        Map mParam = new HashMap();
        mParams.put(0, mParam);
        // Dictionnaire recevant les enregistrements lus
        Map mResults;
        // La collection de User à retourner
        List<Reservation> lReservations = new ArrayList<Reservation>();
        try {
            String requete = "select * from reservation";
            
            mResults = lecture(requete, mParams);
            // Chaque enregistrement est référencé par un indice
            // Pour chaque item du Dictionnaire
            for (Object record :mResults.keySet()) {
                // On récuprère le Dictionnaire de l'enregistrement
                Map mRecord = (Map)mResults.get(record);
                // On l'envoie pour affectation des données portées
                // à l'objet Métier Oeuvre qui ajouté à la Collection
                lReservations.add(setProperties(mRecord));
            }
            return (lReservations);
        } catch (Exception e) {
            throw e;
        } 
    }
         public void ajouter(Reservation reservation) throws Exception {
        // Dictionnaire des paramètres qui seront
        // affectés à la requête        
        Map mParams = new HashMap();
        Map mParam;
        try {
            String requete = "insert into reservation (id_adherent, id_oeuvre, date_reservation, statut)";
            requete += " values (?, ?, ?, ?)";
            // On ajoute chaque paramètre au Dictionnaire
            // en spécifiant sa place dans la requête 
            mParam = new HashMap();
            
            mParam.put(1, reservation.getId_adherent());
            mParam.put(2, reservation.getId_oeuvre());
            java.sql.Date date_reservation = new java.sql.Date(reservation.getDate_reservation().getTime());
            mParam.put(3, date_reservation);
            mParam.put(4, "Attente");
            
            mParams.put(0, mParam);
            List<String> lRequetes = new ArrayList<String>();
            // On utilise une collection de requêtes car c'est
            // ce qu'attend la méthode transaction() en paramètre
            lRequetes.add(requete);
            // Le troisième paramètre servira à générer
            // l'Id du nouvel Utilisateur
            transaction(lRequetes, mParams, "RESERVATION");
        } catch (Exception e) {
            throw e;
        }       
    }
         /**
     * Mise à jour d'un Utilisateur dans la BdD
     * @param user Objet Métier portant les données
     * @throws Exception 
     */
    public void modifier_statut(Reservation reservation) throws Exception {
        // Dictionnaire des paramètres qui seront
        // affectés à la requête
      // Dictionnaire des paramètres qui seront
        // affectés à la requête
        Map mParams = new HashMap();
        Map mParam;
        try {
            String requete = "update reservation set statut = ? where id_oeuvre = ? and date_reservation = ?";
            // On ajoute chaque paramètre au Dictionnaire
            // en spécifiant sa place dans la requête
            mParam = new HashMap();  
            mParam.put(1, reservation.getStatut());
            mParam.put(2, reservation.getId_oeuvre());
            java.sql.Date date_reservation = new java.sql.Date(reservation.getDate_reservation().getTime());
            mParam.put(3, date_reservation);
            
            mParams.put(0, mParam);            
            // Mise à jour dans la BdD
            ecriture(requete, mParams);
        } catch (Exception e) {
            throw e;
        } 
    }
         public void Supprimer(Reservation reservation) throws Exception {
        // Dictionnaire des paramètres qui seront
        // affectés à la requête
      // Dictionnaire des paramètres qui seront
        // affectés à la requête
        Map mParams = new HashMap();
        Map mParam;
        try {
            String requete = "delete from reservation where id_oeuvre = ? and date_reservation = ?";
            // On ajoute chaque paramètre au Dictionnaire
            // en spécifiant sa place dans la requête
            mParam = new HashMap();            
            
            mParam.put(1, reservation.getId_oeuvre());
            java.sql.Date date_reservation = new java.sql.Date(reservation.getDate_reservation().getTime());
            mParam.put(2, date_reservation);
            mParams.put(0, mParam);            
            // Mise à jour dans la BdD
            ecriture(requete, mParams);
        } catch (Exception e) {
            throw e;
        } 
    }
        private Reservation setProperties(Map mRecord) throws Exception {
        
        Reservation reservation = new Reservation();
        try {

            reservation.setStatut(((mRecord.get("statut"))).toString());
            reservation.setId_oeuvre(((Integer)(mRecord.get("id_oeuvre"))).intValue());
            reservation.setId_adherent(((Integer)(mRecord.get("id_adherent"))).intValue());
            reservation.setDate_reservation((Timestamp)(mRecord.get("date_reservation")));
            OeuvreDAO oeuvreDAO = new OeuvreDAO();
            reservation.setOeuvre(oeuvreDAO.lire_Id(reservation.getId_oeuvre()));
            
             AdherentDAO adherentDAO = new AdherentDAO();
            reservation.setAdherent(adherentDAO.lire_Id(reservation.getId_adherent()));
            return (reservation);
        } catch (Exception e) {
            throw e;
        }
    }  
    
}
