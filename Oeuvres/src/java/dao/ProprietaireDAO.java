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
public class ProprietaireDAO extends Dao{
    
 

    private Proprietaire proprietaire;
    public Proprietaire getProprietaire(){
        return this.proprietaire;
    }
    
    /**
     * Liste des Utilisateurs
     * @return Collection de User
     * @throws Exception 
     */
    public List<Proprietaire> liste() throws Exception {
        Map mParams = new HashMap();
        Map mParam = new HashMap();
        mParams.put(0,mParam);
        Map mResults;
        List<Proprietaire> lProprietaires = new ArrayList<Proprietaire>();

        try {
            
            String requete="select * from proprietaire";
            mResults = lecture(requete,mParams);
            
            for(Object record: mResults.keySet()){
                Map mRecord = (Map)mResults.get(record);
                lProprietaires.add(setProperties(mRecord));
             
            }
            return (lProprietaires);
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
     * Ajoute un Utilisateur dans la BdD
     * Note : On utilise un Dictionnaire de Dictionnaire car
     * la méthode transaction() exécutera une collection de 
     * requêtes qui auront chacune leur Dictionnaire de paramètres
     * @param user Objet Métier portant les données
     * @throws Exception 
     */
    public void ajouter(Proprietaire proprietaire) throws Exception {
        // Dictionnaire des paramètres qui seront
        // affectés à la requête        
        
        try {
            
            // On ajoute chaque paramètre au Dictionnaire
            // en spécifiant sa place dans la requête 
            
            // On utilise une collection de requêtes car c'est
            // ce qu'attend la méthode transaction() en paramètre
            
            // Le troisième paramètre servira à générer
            // l'Id du nouvel Utilisateur
            
        } catch (Exception e) {
            throw e;
        }       
    }

    /**
     * Lecture d'un Utilisateur sur son Id
     * Note : fonctionnement identique aux autres méthodes
     * @param id Identifiant de l'Utilisateur à lire
     * @return un objet Métier User
     * @throws Exception 
     */
    public Proprietaire lire_Id(int id) throws Exception {
        Map mParams = new HashMap();
        Map mParam = new HashMap();
        mParams.put(0,mParam);
        Map mResults;
        Proprietaire Proprietaire = new Proprietaire();

        try {
            
            String requete="select * from proprietaire where id_proprietaire=?";
            mResults = lecture(requete,mParams);
            
            for(Object record: mResults.keySet()){
                Map mRecord = (Map)mResults.get(record);
                Proprietaire = (setProperties(mRecord));
             
            }
            return (Proprietaire);
        } catch (Exception e) {
            throw e;
        } 
    }

    /**
     * Lecture d'un Utilisateur sur son login
     * Notes : le login est unique
     *      : fonctionnement identique aux autres méthodes
     * @param login Login de l'utilisateur à lire
     * @return objet Métier User
     * @throws Exception 
     */
    public Proprietaire lire_Login(String login) throws Exception {

        Map mParams = new HashMap();
        Map mParam = new HashMap();
        mParams.put(0,mParam);
        Map mResults;
        Proprietaire Proprietaire = new Proprietaire();

        try {
            
            String requete="select * from proprietaire where login=?";
            mResults = lecture(requete,mParams);
            
            for(Object record: mResults.keySet()){
                Map mRecord = (Map)mResults.get(record);
                Proprietaire = (setProperties(mRecord));
             
            }
            return (Proprietaire);
        } catch (Exception e) {
            throw e;
        } 
    }
     

    /**
     * Vérifie qu'un Utilisateur voulant s'authentifier
     * a bien fournit le bon login et le bon mot de passe
     * @param login Login de l'Utilisateur
     * @param pwd Mot de passe de l'Utilisateur
     * @return True : si tout OK, False sinon
     * @throws Exception 
     */
    public boolean connecter(String login, String pwd) throws Exception {
        
        boolean retour = false;
        
        try {
            Proprietaire pro = lire_Login(login);
            proprietaire = pro;
            if (pwd.equals(proprietaire.getPwd())) {
                retour = true;
            }
            return retour;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Affecte les valeur prises dans le dictionnaire au propriétés
     * de l'objet Métier User
     * @param mRecord Dictionnaire contenant les valeurs lues dans la BdD
     * @return objet Métier User
     * @throws Exception 
     */
    private Proprietaire setProperties(Map mRecord) throws Exception {
        Proprietaire proprietaire = new Proprietaire();
        try {
            proprietaire.setId_proprietaire(((Integer)(mRecord.get("id_proprietaire"))).intValue());
            proprietaire.setNom_proprietaire(((String)(mRecord.get("nom_proprietaire"))).toString());
            proprietaire.setPrenom_proprietaire(((String)(mRecord.get("prenom_proprietaire"))).toString());
            proprietaire.setLogin(((String)(mRecord.get("login"))).toString());
            proprietaire.setPwd(((String)(mRecord.get("pwd"))).toString());
            
            return proprietaire;
        } catch (Exception e) {
            throw e;
        }
    }
}

    

