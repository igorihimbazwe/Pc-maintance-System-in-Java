package com.pcm.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pcm.dao.RequestDAO;
import com.pcm.model.Request;
import com.pcm.model.User;

@WebServlet("/request")
public class RequestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        Request maintenanceRequest = new Request();
        maintenanceRequest.setUserId(user.getId());
        maintenanceRequest.setFirstName(request.getParameter("firstName"));
        maintenanceRequest.setLastName(request.getParameter("lastName"));
        maintenanceRequest.setEmail(request.getParameter("email"));
        maintenanceRequest.setTelephone(request.getParameter("telephone"));
        maintenanceRequest.setUnit(request.getParameter("unit"));
        maintenanceRequest.setRequestType(request.getParameter("requestType"));
        maintenanceRequest.setStatus("PENDING");
        
        RequestDAO requestDAO = new RequestDAO();
        requestDAO.addRequest(maintenanceRequest);
        
        response.sendRedirect("user/dashboard.jsp");
    }
} 