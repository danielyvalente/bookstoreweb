/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.dmvs.bookstoreweb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import local.dmvs.bookstoreweb.model.bean.User;
import local.dmvs.bookstoreweb.model.dao.UserDAO;

/**
 *
 * @author devsys-a
 */
public class UserServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getPathInfo();
        Logger.getLogger(UserServlet.class.getName()).log(Level.INFO, "Path solicitado {0}", action);

        if (action == null) {
            action = "/initial";
        }

        try {
            switch (action) {
                case "/new":
                    showNewRegister(request, response);

                case "/register":
                    registerUser(request, response);
                    break;

                case "/initial":

                default:
                    Logger.getLogger(UserServlet.class.getName()).log(Level.INFO, "Carregando a ShowInicial...");
                    showInicial(request, response);
            }

        } catch (Exception e) {

        }

    }

    private void showInicial(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        
        UserDAO userDAO = new UserDAO();
        List <User> listaUser = userDAO.getResults();
        request.setAttribute("listaUser", listaUser);
        
        Logger.getLogger(UserServlet.class.getName()).log(Level.INFO, "ShowInicial acionada. oioioioi");
        
        RequestDispatcher dispacher = request.getRequestDispatcher("/UserList.jsp");
        dispacher.forward(request, response);
    }

    private void showNewRegister(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        RequestDispatcher dispacher = request.getRequestDispatcher("/UserForm.jsp");
        dispacher.forward(request, response);
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        UserDAO userDAO = new UserDAO();
        User novoCadastro = new User();

        novoCadastro.setEmail(request.getParameter("formEmail"));
        novoCadastro.setPassword(request.getParameter("formPassword"));
        novoCadastro.setFullname(request.getParameter("formFullname"));

        userDAO.create(novoCadastro);
        response.sendRedirect("/bookstoreweb/login");
//        RequestDispatcher dispacher = request.getRequestDispatcher("/login");
//        dispacher.forward(request, response);
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
