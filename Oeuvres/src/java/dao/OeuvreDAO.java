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
public class OeuvreDAO extends Dao {
    
    public OeuvreDAO() {
    }

    /**
     * Liste des Categories
     * @return Collection de Categorie
     * @throws Exception 
     */
    public List<Oeuvre> listeOeuvres() throws Exception {
        // la méthode lecture() attend ce paramètre
        Map mParams = new HashMap();
        Map mParam = new HashMap();
        mParams.put(0, mParam);
        // Dictionnaire recevant les enregistrements lus
        Map mResults;
        // La collection de User à retourner
        List<Oeuvre> lOeuvres = new ArrayList<Oeuvre>();
        try {
            String requete = "select * from oeuvre";
            
            mResults = lecture(requete, mParams);
            // Chaque enregistrement est référencé par un indice
            // Pour chaque item du Dictionnaire
            for (Object record :mResults.keySet()) {
                // On récuprère le Dictionnaire de l'enregistrement
                Map mRecord = (Map)mResults.get(record);
                // On l'envoie pour affectation des données portées
                // à l'objet Métier Oeuvre qui ajouté à la Collection
                lOeuvres.add(setProperties(mRecord));
            }
            return (lOeuvres);
        } catch (Exception e) {
            throw e;
        } 
    }

    /**
     * Mise à jour d'un Utilisateur dans la BdD
     * @param user Objet Métier portant les données
     * @throws Exception 
     */
    public void modifier(Proprietaire proprietaire) throws Exception {
        // Dictionnaire des paramètres qui seront
        // affectés à la requête
        
        try {
            
            // On ajoute chaque paramètre au Dictionnaire
            // en spécifiant sa place dans la requête
                        
            // Mise à jour dans la BdD
            
        } catch (Exception e) {
            throw e;
        } 
    }

    /**
     * Lecture d'une Catégorie sur son Id
     * @param id Identifiant de la Catégorie à lire
     * @throws Exception 
     */
    public Oeuvre lire_Id(int id) throws Exception {
Map mParams = new HashMap();
        Map mParam = new HashMap();
        mParams.put(0,mParam);
        Map mResults;
        Oeuvre oeuvre = new Oeuvre();

        try {
            
            String requete="select * from oeuvre where id_oeuvre=?";
            mResults = lecture(requete,mParams);
            
            for(Object record: mResults.keySet()){
                Map mRecord = (Map)mResults.get(record);
                oeuvre = (setProperties(mRecord));
             
            }
            return (oeuvre);
        } catch (Exception e) {
            throw e;
        } 
    }
    
    /**
     * Affecte les valeurs lues dans la base de données
     * aux propriétés de la classe Categorie
     * @param mRecord
     * @return
     * @throws Exception 
     */
    private Oeuvre setProperties(Map mRecord) throws Exception {
        
        Oeuvre oeuvre = new Oeuvre();
        try {
            
            oeuvre.setId_oeuvre(((Integer)(mRecord.get("id_oeuvre"))).intValue());
            oeuvre.setId_proprietaire(((Integer)(mRecord.get("id_proprietaire"))).intValue());
            oeuvre.setPrix(((BigDecimal)(mRecord.get("prix"))).floatValue());
            oeuvre.setTitre(((mRecord.get("titre"))).toString());
            
            ProprietaireDAO proprietaireDAO = new ProprietaireDAO();
            oeuvre.setProprietaire(proprietaireDAO.lire_Id(oeuvre.getId_proprietaire()));
            return (oeuvre);
        } catch (Exception e) {
            throw e;
        }
    }    
}


