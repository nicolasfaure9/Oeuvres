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
