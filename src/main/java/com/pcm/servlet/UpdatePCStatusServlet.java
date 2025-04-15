package com.pcm.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pcm.dao.PCDAO;
import com.pcm.model.User;

@WebServlet("/updatePCStatus")
public class UpdatePCStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !"TECHNICIAN".equals(user.getRole())) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        int pcId = Integer.parseInt(request.getParameter("pcId"));
        String status = request.getParameter("status");
        
        PCDAO pcDAO = new PCDAO();
        pcDAO.updatePCStatus(pcId, status);
        
        response.sendRedirect("technician/equipment.jsp");
    }
} 