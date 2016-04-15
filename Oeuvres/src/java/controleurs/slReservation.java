/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controleurs;

import dao.AdherentDAO;
import dao.OeuvreDAO;
import dao.ReservationDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import outils.*;
import modeles.*;

/**
 *
 * @author alain
 */

public class slReservation extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    private String erreur;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String demande;
        String vueReponse = "/home.jsp";
        erreur = "";
        try {
            demande = getDemande(request);
            if (demande.equalsIgnoreCase("reserver.res")) {
                vueReponse = reserverOeuvre(request);
            } else if (demande.equalsIgnoreCase("enregistrerReservation.res")) {
                vueReponse = enregistrerReservation(request);
            } else if (demande.equalsIgnoreCase("listeReservations.res")) {
                vueReponse = listeReservations(request);
            } else if (demande.equalsIgnoreCase("confirmerReservation.res")) {
                vueReponse = confirmerReservation(request);
            } else if (demande.equalsIgnoreCase("supprimerReservation.res")) {
                vueReponse = supprimerReservation(request);
            }
        } catch (Exception e) {
            erreur = e.getMessage();
        } finally {
            request.setAttribute("erreurR", erreur);
            request.setAttribute("pageR", vueReponse); 
            RequestDispatcher dsp = request.getRequestDispatcher("/index.jsp");
            if (vueReponse.contains(".res"))
                dsp = request.getRequestDispatcher(vueReponse);
            dsp.forward(request, response);
        }
    }

    /**
     * Transforme dans la base de données une réservation en Attente
     * en une réservation Confirmée
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String confirmerReservation(HttpServletRequest request) throws Exception {

        try {
            ReservationDAO reservationDAO = new ReservationDAO();
            Reservation reservation = new Reservation();
            
            int id_oeuvre = Integer.parseInt(request.getParameter("id"));
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(request.getParameter("dateres").replaceAll("'", ""));
            java.sql.Date date_reservation = new java.sql.Date(date.getTime()); 
            
            reservation = reservationDAO.lire_reservation(id_oeuvre, date_reservation);
            reservation.setStatut("Confirmée");
            
            reservationDAO.modifier_statut(reservation);
            return ("listeReservations.res");
        } catch (Exception e) {
            throw e;
        }
    }

    private String supprimerReservation(HttpServletRequest request) throws Exception {
        
        try {

            return ("listeReservations.res");
        } catch (Exception e) {         
            throw e;
        }
    }     
    
    /**
     * liste des réservations en Attente
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String listeReservations(HttpServletRequest request) throws Exception {
        
         List<Reservation> lReservations = new ArrayList<>();
         ReservationDAO reservations = new ReservationDAO();
        
        String pageReponse="/listereservations.jsp" ;
        try {

            lReservations = reservations.listeReservations();
            pageReponse = "/listereservations.jsp";
            HttpSession session = request.getSession(true);
            if(lReservations.isEmpty())
                erreur="Aucune réservation actuellement";
            else{
                request.setAttribute("lstReservationsR", lReservations);
            }
        }
        catch (Exception e) {
                erreur = e.getMessage();
        }

        finally {
            return (pageReponse);
        }
    }

    /**
     * Enregistre une réservation et la met en Attente
     * @param request
     * @return
     * @throws Exception
     */
    private String enregistrerReservation(HttpServletRequest request) throws Exception {
         
        try {
            OeuvreDAO oeuvreDAO = new OeuvreDAO();
            int id_oeuvre = Integer.parseInt(request.getParameter("id"));
            Oeuvre oeuvre = oeuvreDAO.lire_Id(id_oeuvre);

            AdherentDAO adherentDAO = new AdherentDAO();
            int id_adherent = Integer.parseInt(request.getParameter("lstAdherents"));
            Adherent adherent = adherentDAO.lire_Id(id_adherent);
            
            String txtDate = request.getParameter("txtDate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Timestamp date = new Timestamp(sdf.parse(txtDate).getTime());
            
            
            ReservationDAO reservationDAO = new ReservationDAO();
            Reservation reservation = new Reservation(oeuvre.getId_oeuvre(),adherent.getId_adherent(), date);
            reservationDAO.ajouter(reservation);
            
            return ("listeReservations.res");
        } catch (Exception e) {
            erreur = e.getMessage();
            if(erreur.contains("PRIMARY")){
                OeuvreDAO oeuvreDAO = new OeuvreDAO();
                int id_oeuvre = Integer.parseInt(request.getParameter("id"));
                Oeuvre oeuvre = oeuvreDAO.lire_Id(id_oeuvre);
                    erreur = "L'oeuvre " + oeuvre.getTitre() + " a déjà été réservée pour le : " + request.getParameter("txtDate") + " !";      
            }

            throw new Exception(erreur);
        }
    }

    /**
     * Lit une oeuvre, l'affiche et initialise la liste des adhérents
     * pour pouvoir saisir une réservation :
     * Saisie date et sélection de l'adhérent
     * @param request
     * @return
     * @throws Exception
     */
    private String reserverOeuvre(HttpServletRequest request) throws Exception {

        try {
            OeuvreDAO oeuvre = new OeuvreDAO();
            AdherentDAO adherent = new AdherentDAO();
            
            int id_oeuvre = Integer.parseInt(request.getParameter("id"));
            Oeuvre oeuvreR = oeuvre.lire_Id(id_oeuvre);
            List<Adherent> lAdherents = adherent.listeAdherents();
            
            request.setAttribute("oeuvreR", oeuvreR); 
            request.setAttribute("lstAdherentsR", lAdherents); 
            
            return ("/reservation.jsp");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Extrait le texte de la demande de l'URL
     * @param request
     * @return String texte de la demande
     */
    private String getDemande(HttpServletRequest request) {
        String demande = "";
        demande = request.getRequestURI();
        demande = demande.substring(demande.lastIndexOf("/") + 1);
        return demande;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
