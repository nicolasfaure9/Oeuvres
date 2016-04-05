/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleurs;

import dao.ProprietaireDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modeles.*;

/**
 *
 * @author arsane
 */
public class slOeuvres extends HttpServlet {

    private String erreur;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String demande;
        String vueReponse = "/home.jsp";
        erreur = "";
        try {
            demande = getDemande(request);
            if (demande.equalsIgnoreCase("login.oe")) {
                vueReponse = login(request);
            } else  if (demande.equalsIgnoreCase("connecter.oe")) {
                vueReponse = connecter(request);
            } else  if (demande.equalsIgnoreCase("deconnecter.oe")) {
                vueReponse = deconnecter(request);
            } else if (demande.equalsIgnoreCase("ajouter.oe")) {
                vueReponse = creerOeuvre(request);
            } else if (demande.equalsIgnoreCase("enregistrer.oe")) {
                vueReponse = enregistrerOeuvre(request);
            } else if (demande.equalsIgnoreCase("modifier.oe")) {
                vueReponse = modifierOeuvre(request);
            } else if (demande.equalsIgnoreCase("catalogue.oe")) {
                vueReponse = listerOeuvres(request);
            } else if (demande.equalsIgnoreCase("supprimer.oe")) {
                vueReponse = supprimerOeuvre(request);
            }  
            
   
        } catch (Exception e) {
            erreur = e.getMessage();
        } finally {
            request.setAttribute("erreurR", erreur);
            request.setAttribute("pageR", vueReponse);
            HttpSession session = request.getSession(true);
            Object sessionScope = session.getAttribute("userS");
            Proprietaire user = (Proprietaire) sessionScope;
            if(user != null)
              request.setAttribute("sessionScope.userId", 1);  
            RequestDispatcher dsp = request.getRequestDispatcher("/index.jsp");
            if (vueReponse.contains(".oe"))
                dsp = request.getRequestDispatcher(vueReponse);
            dsp.forward(request, response);
        }
    }

    /**
     * Enregistre une oeuvre qui a été soit créée (id_oeuvre = 0)
     * soit modifiée (id_oeuvre > 0)
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String enregistrerOeuvre(HttpServletRequest request) throws Exception {

        String vueReponse;

        try {

            vueReponse = "catalogue.oe";
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Lit et affiche une oeuvre pour pouvoir la modifier
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String modifierOeuvre(HttpServletRequest request) throws Exception {

        String vueReponse;
        try {

            vueReponse = "/oeuvre.jsp";
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Supprimer une oeuvre
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String supprimerOeuvre(HttpServletRequest request) throws Exception {
        String vueReponse, titre;
        
        try {
            titre = request.getParameter("txttitre");
            
            vueReponse = "catalogue.oe";           
            return (vueReponse);  
        } catch (Exception e) {
            erreur = e.getMessage();
       //     if(erreur.contains("FK_RESERVATION_OEUVRE"))
        //        erreur = "Il n'est pas possible de supprimer l'oeuvre : " + titre + " car elle a été réservée !";            
            throw new Exception(erreur);
        }
    }    
    /**
     * Affiche le formulaire vide d'une oeuvre
     * Initialise la liste des propriétaires
     * Initialise le titre de la page
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String creerOeuvre(HttpServletRequest request) throws Exception {

        String vueReponse;
        try {

            vueReponse = "/oeuvre.jsp";
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Vérifie que l'utilisateur a saisi le bon login et mot de passe
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String connecter(HttpServletRequest request) throws Exception {
        ProprietaireDAO user;
        Proprietaire proprietaire;
        String login, pwd;
        String pageReponse="/login.jsp";
        
        try {
            
            login = request.getParameter("txtLogin");
            pwd = request.getParameter("txtPwd");
            user = new ProprietaireDAO();
            if (user.connecter(login, pwd)) {
                
                proprietaire = user.getProprietaire();
                pageReponse = "/home.jsp";
                HttpSession session = request.getSession(true);
                session.setAttribute("userS", user);
                request.setAttribute("userR", user);
            } else {
                erreur = "Login ou mot de passe inconnus !";
            }
        } catch (Exception e) {
            erreur = e.getMessage();
        } finally {
            return (pageReponse);
        }
    }

    private String deconnecter(HttpServletRequest request) throws Exception {
        try {
            
            return ("/home.jsp");
        } catch (Exception e) {
            throw e;
        }
    } 
    
    /**
     * Afficher la page de login
     * @param request
     * @return
     * @throws Exception 
     */
    private String login(HttpServletRequest request) throws Exception {
        String pageReponse = "/login.jsp";
        try {
            HttpSession session = request.getSession(true);
            if(session.getAttribute("userS")!= null)
               pageReponse = ("/home.jsp");
        } catch (Exception e) {
            throw e;
        }finally{
            return pageReponse;
        }
    }    
    /**
     * liste des oeuvres pour le catalogue
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String listerOeuvres(HttpServletRequest request) throws Exception {
        
        try {

            return ("/catalogue.jsp");
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
