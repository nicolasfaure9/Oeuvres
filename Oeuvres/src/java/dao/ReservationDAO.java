/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.math.BigDecimal;
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
        private Reservation setProperties(Map mRecord) throws Exception {
        
        Reservation reservation = new Reservation();
        try {

            reservation.setStatut(((mRecord.get("statut"))).toString());
            reservation.setId_oeuvre(((Integer)(mRecord.get("id_oeuvre"))).intValue());
            reservation.setId_adherent(((Integer)(mRecord.get("id_adherent"))).intValue());
            reservation.setDate_reservation((java.util.Date)(mRecord.get("date_reservation")));
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
