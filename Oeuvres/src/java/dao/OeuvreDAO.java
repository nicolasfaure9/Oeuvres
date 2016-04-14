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
    
     private Oeuvre oeuvre;
    public Oeuvre getOeuvre(){
        return this.oeuvre;
    }
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
    public void modifier(Oeuvre oeuvre) throws Exception {
        // Dictionnaire des paramètres qui seront
        // affectés à la requête
      // Dictionnaire des paramètres qui seront
        // affectés à la requête
        Map mParams = new HashMap();
        Map mParam;
        try {
            String requete = "update oeuvre set titre = ?, prix = ?, id_proprietaire = ? where id_oeuvre = ?";
            // On ajoute chaque paramètre au Dictionnaire
            // en spécifiant sa place dans la requête
            mParam = new HashMap();            
            mParam.put(1, oeuvre.getTitre());
            mParam.put(2, oeuvre.getPrix());
            mParam.put(3, oeuvre.getId_proprietaire());
            mParam.put(4, oeuvre.getId_oeuvre());
            
            mParams.put(0, mParam);            
            // Mise à jour dans la BdD
            ecriture(requete, mParams);
        } catch (Exception e) {
            throw e;
        } 
    }
    
     public void Supprimer(Oeuvre oeuvre) throws Exception {
        // Dictionnaire des paramètres qui seront
        // affectés à la requête
      // Dictionnaire des paramètres qui seront
        // affectés à la requête
        Map mParams = new HashMap();
        Map mParam;
        try {
            String requete = "delete from oeuvre where id_oeuvre = ?";
            // On ajoute chaque paramètre au Dictionnaire
            // en spécifiant sa place dans la requête
            mParam = new HashMap();            
            
            mParam.put(1, oeuvre.getId_oeuvre());
            
            mParams.put(0, mParam);            
            // Mise à jour dans la BdD
            ecriture(requete, mParams);
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
        Map mParam;
        Map mResults;
        try {
            String requete = "select * from oeuvre where id_oeuvre = ?";
            mParam = new HashMap();
            mParam.put(1, id);
            mParams.put(0, mParam);            
            mResults = lecture(requete, mParams);
            if (mResults.size() > 0) {
                Map mRecord = (Map)mResults.get(0);
                return(setProperties(mRecord));
            }  else {
                throw new Exception("oeuvre inconnue !");
            }
        } catch (Exception e) {
            throw e;
        } 
    }

     public void ajouter(Oeuvre oeuvre) throws Exception {
        // Dictionnaire des paramètres qui seront
        // affectés à la requête        
        Map mParams = new HashMap();
        Map mParam;
        try {
            String requete = "insert into oeuvre (id_proprietaire, titre,prix,id_oeuvre)";
            requete += " values (?, ?, ?, :id)";
            // On ajoute chaque paramètre au Dictionnaire
            // en spécifiant sa place dans la requête 
            mParam = new HashMap();
            
            mParam.put(1, oeuvre.getId_proprietaire());
            mParam.put(2, oeuvre.getTitre());
            mParam.put(3, oeuvre.getPrix());
            
            mParams.put(0, mParam);
            List<String> lRequetes = new ArrayList<String>();
            // On utilise une collection de requêtes car c'est
            // ce qu'attend la méthode transaction() en paramètre
            lRequetes.add(requete);
            // Le troisième paramètre servira à générer
            // l'Id du nouvel Utilisateur
            transaction(lRequetes, mParams, "OEUVRE");
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
            oeuvre.setPrix(((BigDecimal)(mRecord.get("prix"))).doubleValue());
            oeuvre.setTitre(((mRecord.get("titre"))).toString());
            
            ProprietaireDAO proprietaireDAO = new ProprietaireDAO();
            oeuvre.setProprietaire(proprietaireDAO.lire_Id(oeuvre.getId_proprietaire()));
            return (oeuvre);
        } catch (Exception e) {
            throw e;
        }
    }    
}


