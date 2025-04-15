package com.pcm.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pcm.dao.RequestDAO;
import com.pcm.model.User;

@WebServlet("/updateRequestStatus")
public class UpdateRequestStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null || !"TECHNICIAN".equals(user.getRole())) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        String status = request.getParameter("status");
        
        RequestDAO requestDAO = new RequestDAO();
        requestDAO.updateRequestStatus(requestId, status);
        
        response.sendRedirect("technician/requests.jsp");
    }
} 