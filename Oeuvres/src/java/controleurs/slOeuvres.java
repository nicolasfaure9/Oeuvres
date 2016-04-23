/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleurs;

import dao.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            HttpSession session = request.getSession(true);
            Object sessionScope = session.getAttribute("userS");
            ProprietaireDAO user = (ProprietaireDAO) sessionScope;

            Object sessionScope2 = session.getAttribute("adminS");
            String admin = (String) sessionScope2;

            if (user != null) {
                request.setAttribute("sessionScope.userId", 1);
            }

            if ((user == null) || (demande.equalsIgnoreCase("login.oe"))) {

                if (demande.equalsIgnoreCase("connecter.oe")) {
                    vueReponse = connecter(request);
                }
                vueReponse = login(request);
            } else if (demande.equalsIgnoreCase("deconnecter.oe")) {
                vueReponse = deconnecter(request);

            } else if (admin != null) {
                if (demande.equalsIgnoreCase("ajouter.oe")) {
                    vueReponse = creerOeuvre(request);
                } else if (demande.equalsIgnoreCase("enregistrer.oe")) {
                    vueReponse = enregistrerOeuvre(request);
                } else if (demande.equalsIgnoreCase("modifier.oe")) {
                    vueReponse = modifierOeuvre(request);
                } else if (demande.equalsIgnoreCase("supprimer.oe")) {
                    vueReponse = supprimerOeuvre(request);
                } else if (demande.equalsIgnoreCase("catalogue.oe")) {
                    vueReponse = listerOeuvres(request);
                }
            }

        } catch (Exception e) {
            erreur = e.getMessage();
            vueReponse = "erreur.jsp";
        } finally {
            request.setAttribute("erreurR", erreur);
            request.setAttribute("pageR", vueReponse);

            RequestDispatcher dsp = request.getRequestDispatcher(vueReponse);
            if (vueReponse.contains(".oe")) {
                dsp = request.getRequestDispatcher(vueReponse);
            }
            dsp.forward(request, response);
        }
    }

    /**
     * Enregistre une oeuvre qui a été soit créée (id_oeuvre = 0) soit modifiée
     * (id_oeuvre > 0)
     *
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String enregistrerOeuvre(HttpServletRequest request) throws Exception {

        String vueReponse, message;
        message = "";
        int id_oeuvre;
        try {
            OeuvreDAO oeuvreDAO = new OeuvreDAO();
            Oeuvre oeuvre = new Oeuvre();
            id_oeuvre = Integer.parseInt(request.getParameter("id"));
            oeuvre.setId_oeuvre(id_oeuvre);
            oeuvre.setTitre(request.getParameter("txtTitre"));
            oeuvre.setPrix(Double.parseDouble(request.getParameter("txtPrix")));

            String id = request.getParameter("lProprietaires");
            int id_proprietaire = Integer.parseInt(id);
            oeuvre.setId_proprietaire(id_proprietaire);
            // Si on a un id c'est qu'il s'agit d'une modification
            if (id_oeuvre > 0) {
                oeuvreDAO.modifier(oeuvre);
            } else {
                oeuvreDAO.ajouter(oeuvre);
            }
            vueReponse = "/home.jsp";
            HttpSession session = request.getSession(true);
            // String userId = session.getAttribute("userS").toString();
            // Après une modification l'administrateur accède
            // à la liste des utilisateur alors qu'un utilisateur
            // lambda retourne à la page home
            message = "Oeuvre enregistrée avec succès !";
            vueReponse = "catalogue.oe";
            request.setAttribute("succes", message);
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }

    private String creerOeuvre(HttpServletRequest request) throws Exception {
        Oeuvre oeuvre;
        ProprietaireDAO proprietaireDAO;
        List<Proprietaire> lProprietaire;
        String vueReponse;
        try {
            oeuvre = new Oeuvre();
            request.setAttribute("oeuvreR", oeuvre);
            HttpSession session = request.getSession(true);
            session.setAttribute("id", 0);
            proprietaireDAO = new ProprietaireDAO();
            lProprietaire = proprietaireDAO.liste();
            request.setAttribute("lstProprietairesR", lProprietaire);
            request.setAttribute("titre", "Ajouter Oeuvre");
            vueReponse = "/oeuvre.jsp";
            return (vueReponse);
        } catch (Exception e) {
            throw e;

        }
    }

    /**
     * Lit et affiche une oeuvre pour pouvoir la modifier
     *
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String modifierOeuvre(HttpServletRequest request) throws Exception {

        OeuvreDAO oeuvreDAO;
        List<Proprietaire> lProprietaires;
        ProprietaireDAO proprietaireDAO;
        String vueReponse, message;
        message = "";
        int id_oeuvre;
        try {
            vueReponse = "/login.jsp";

            id_oeuvre = Integer.parseInt(request.getParameter("id"));
            if (id_oeuvre > 0) {
                proprietaireDAO = new ProprietaireDAO();
                lProprietaires = proprietaireDAO.liste();
                oeuvreDAO = new OeuvreDAO();
                Oeuvre oeuvre = oeuvreDAO.lire_Id(id_oeuvre);
                request.setAttribute("oeuvreR", oeuvre);
                request.setAttribute("lstProprietairesR", lProprietaires);
                request.setAttribute("titre", "Modifier une oeuvre");
                vueReponse = "/oeuvre.jsp";

            } else {
                erreur = "Oeuvre inconnue !";
            }
            return (vueReponse);
        } catch (Exception e) {
            throw e;

        }
    }

    /**
     * Supprimer une oeuvre
     *
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String supprimerOeuvre(HttpServletRequest request) throws Exception {
        String vueReponse, titre,message;
        OeuvreDAO oeuvreDAO;
        Oeuvre oeuvre;
        vueReponse = "catalogue.oe";
        message="";
        int id_oeuvre;
        titre = "";

        try {
            id_oeuvre = Integer.parseInt(request.getParameter("id"));
            oeuvreDAO = new OeuvreDAO();
            oeuvre = oeuvreDAO.lire_Id(id_oeuvre);
            titre = oeuvre.getTitre();
            oeuvreDAO.Supprimer(oeuvre);
            message ="Oeuvre supprimée avec succès !";
            request.setAttribute("succes", message);
        } catch (Exception e) {
            erreur = e.getMessage();
            if (erreur.contains("FK_RESERVATION_OEUVRE")) {
                erreur = "Il n'est pas possible de supprimer l'oeuvre : " + titre + " car elle a été réservée !";
                request.setAttribute("erreur", erreur);
            }
            throw new Exception(erreur);
        } finally {
            return (vueReponse);
        }
    }

    /**
     * Affiche le formulaire vide d'une oeuvre Initialise la liste des
     * propriétaires Initialise le titre de la page
     *
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    /**
     * Vérifie que l'utilisateur a saisi le bon login et mot de passe
     *
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String connecter(HttpServletRequest request) throws Exception {
        ProprietaireDAO user;
        Proprietaire proprietaire;
        String login, pwd;
        String pageReponse = "/login.jsp";
        HttpSession session = request.getSession(true);
        request.setAttribute("erreur", null);
        try {

            login = request.getParameter("txtLogin");
            pwd = request.getParameter("txtPwd");
            user = new ProprietaireDAO();

            if (user.connecter(login, pwd)) {

                proprietaire = user.getProprietaire();
                pageReponse = "/home.jsp";

                session.setAttribute("userS", user);
                request.setAttribute("userR", user);
                Proprietaire pro = user.getProprietaire();
                if (pro.getNom_proprietaire().equals("Administrateur")) {
                    session.setAttribute("adminS", pro.getNom_proprietaire());
                }

            } else {
                erreur = "Login ou mot de passe inconnus !";
                request.setAttribute("erreur", erreur);
            }
        } catch (Exception e) {
            erreur = e.getMessage();
            request.setAttribute("erreur", erreur);
        } finally {
            return (pageReponse);
        }
    }

    private String deconnecter(HttpServletRequest request) throws Exception {
        String vueReponse;
        try {
            HttpSession session = request.getSession(true);
            session.setAttribute("userS", null);
            session.setAttribute("adminS", null);

            vueReponse = "/home.jsp";
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Afficher la page de login
     *
     * @param request
     * @return
     * @throws Exception
     */
    private String login(HttpServletRequest request) throws Exception {
        String pageReponse = "/login.jsp";
        try {
            HttpSession session = request.getSession(true);
            if (session.getAttribute("userS") != null) {
                pageReponse = ("/home.jsp");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            return pageReponse;
        }
    }

    /**
     * liste des oeuvres pour le catalogue
     *
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String listerOeuvres(HttpServletRequest request) throws Exception {

        List<Oeuvre> lOeuvres = new ArrayList<>();
        OeuvreDAO oeuvre = new OeuvreDAO();

        String pageReponse = "/catalogue.jsp";
        try {

            lOeuvres = oeuvre.listeOeuvres();
            pageReponse = "/catalogue.jsp";
            HttpSession session = request.getSession(true);
            request.setAttribute("lstOeuvresR", lOeuvres);
        } catch (Exception e) {
            erreur = e.getMessage();
        } finally {
            return (pageReponse);
        }
    }

    /**
     * Extrait le texte de la demande de l'URL
     *
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
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
