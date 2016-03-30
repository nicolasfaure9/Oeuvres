/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

/**
 *
 * @author Epulapp
 */
public class Db_outils {

    public int getIdentifiant(String id) throws Exception {
        CallableStatement cs = null;
        Connection connection;
        try {
            Connexion cnx = new Connexion();
            connection = cnx.connecter();
            cs = connection.prepareCall("{? = call inc_parametre(?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, id);
            cs.execute();
            return (cs.getInt(1));
        } catch (Exception e) {
            throw e;
        }
        finally
        {
            try {
                if (cs != null) {
                    cs.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
