package com.pcm.controller;

import com.pcm.model.User;
import com.pcm.dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        
        if (path == null || path.equals("/")) {
            response.sendRedirect("dashboard.jsp");
            return;
        }
        
        switch (path) {
            case "/addTechnician":
                addTechnician(request, response);
                break;
            case "/updateTechnician":
                updateTechnician(request, response);
                break;
            case "/deleteTechnician":
                deleteTechnician(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void addTechnician(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setRole("TECHNICIAN");
        user.setStatus(request.getParameter("status"));
        
        UserDAO userDAO = new UserDAO();
        if (userDAO.addUser(user)) {
            response.sendRedirect("technicians.jsp?success=Technician added successfully");
        } else {
            response.sendRedirect("technicians.jsp?error=Failed to add technician");
        }
    }
    
    private void updateTechnician(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = new User();
        user.setId(Integer.parseInt(request.getParameter("id")));
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setStatus(request.getParameter("status"));
        
        UserDAO userDAO = new UserDAO();
        if (userDAO.updateUser(user)) {
            response.sendRedirect("technicians.jsp?success=Technician updated successfully");
        } else {
            response.sendRedirect("technicians.jsp?error=Failed to update technician");
        }
    }
    
    private void deleteTechnician(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        UserDAO userDAO = new UserDAO();
        
        if (userDAO.deleteUser(userId)) {
            response.sendRedirect("technicians.jsp?success=Technician deleted successfully");
        } else {
            response.sendRedirect("technicians.jsp?error=Failed to delete technician");
        }
    }
} 