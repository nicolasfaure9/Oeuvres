/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import modeles.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Epulapp
 */
public class AdherentDAO extends Dao{
    
        public Adherent lire_Id(int id) throws Exception {
        Map mParams = new HashMap();
        Map mParam;
        Map mResults;
        try {
            String requete = "select * from adherent where id_adherent = ?";
            mParam = new HashMap();
            mParam.put(1, id);
            mParams.put(0, mParam);            
            mResults = lecture(requete, mParams);
            if (mResults.size() > 0) {
                Map mRecord = (Map)mResults.get(0);
                return(setProperties(mRecord));
            }  else {
                throw new Exception("adherent inconnue !");
            }
        } catch (Exception e) {
            throw e;
        } 
    }
        /**
     * Liste des Adherents
     * @return Collection de Adherents
     * @throws Exception 
     */
    public List<Adherent> listeAdherents() throws Exception {
        // la méthode lecture() attend ce paramètre
        Map mParams = new HashMap();
        Map mParam = new HashMap();
        mParams.put(0, mParam);
        // Dictionnaire recevant les enregistrements lus
        Map mResults;
        // La collection de User à retourner
        List<Adherent> lAdherents = new ArrayList<Adherent>();
        try {
            String requete = "select * from adherent";
            
            mResults = lecture(requete, mParams);
            // Chaque enregistrement est référencé par un indice
            // Pour chaque item du Dictionnaire
            for (Object record :mResults.keySet()) {
                // On récuprère le Dictionnaire de l'enregistrement
                Map mRecord = (Map)mResults.get(record);
                // On l'envoie pour affectation des données portées
                // à l'objet Métier Oeuvre qui ajouté à la Collection
                lAdherents.add(setProperties(mRecord));
            }
            return (lAdherents);
        } catch (Exception e) {
            throw e;
        } 
    }
    private Adherent setProperties(Map mRecord) throws Exception {

        Adherent adherent = new Adherent();
        try {

            adherent.setNom_adherent(((mRecord.get("nom_adherent"))).toString());
            adherent.setId_adherent(((Integer)(mRecord.get("id_adherent"))).intValue());
            adherent.setPrenom_adherent((((mRecord.get("prenom_adherent"))).toString()));

            return (adherent);
        } catch (Exception e) {
            throw e;
        }
    }  
}
