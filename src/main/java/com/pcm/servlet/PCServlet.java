package com.pcm.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pcm.dao.PCDAO;
import com.pcm.model.PC;
import com.pcm.model.User;

@WebServlet("/pc")
public class PCServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !"TECHNICIAN".equals(user.getRole())) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        PC pc = new PC();
        pc.setTechnicianId(user.getId());
        pc.setBrand(request.getParameter("brand"));
        pc.setSpecifications(request.getParameter("specifications"));
        pc.setStatus("OPERATIONAL");
        pc.setLocation(request.getParameter("location"));
        
        PCDAO pcDAO = new PCDAO();
        pcDAO.addPC(pc);
        
        response.sendRedirect("technician/equipment.jsp");
    }
} 