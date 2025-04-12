<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pcm.model.User" %>
<%@ page import="com.pcm.dao.UserDAO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - PC Maintenance System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <%
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("../index.jsp");
            return;
        }
    %>
    
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">PC Maintenance System</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active" href="dashboard.jsp">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="technicians.jsp">Technicians</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="equipment.jsp">Equipment</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="requests.jsp">Requests</a>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="../logout">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row">
            <div class="col-md-4">
                <div class="card text-white bg-primary mb-3">
                    <div class="card-body">
                        <h5 class="card-title">Total Equipment</h5>
                        <p class="card-text display-4">0</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card text-white bg-success mb-3">
                    <div class="card-body">
                        <h5 class="card-title">Working Equipment</h5>
                        <p class="card-text display-4">0</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card text-white bg-warning mb-3">
                    <div class="card-body">
                        <h5 class="card-title">Pending Requests</h5>
                        <p class="card-text display-4">0</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="row mt-4">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h5>Equipment Status</h5>
                    </div>
                    <div class="card-body">
                        <canvas id="equipmentChart"></canvas>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h5>Technician Status</h5>
                    </div>
                    <div class="card-body">
                        <canvas id="technicianChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        // Equipment Status Chart
        const equipmentCtx = document.getElementById('equipmentChart').getContext('2d');
        new Chart(equipmentCtx, {
            type: 'pie',
            data: {
                labels: ['Working', 'Not Working', 'Damaged', 'Old'],
                datasets: [{
                    data: [0, 0, 0, 0],
                    backgroundColor: ['#28a745', '#dc3545', '#ffc107', '#6c757d']
                }]
            }
        });

        // Technician Status Chart
        const technicianCtx = document.getElementById('technicianChart').getContext('2d');
        new Chart(technicianCtx, {
            type: 'pie',
            data: {
                labels: ['Available', 'Not Available', 'Busy'],
                datasets: [{
                    data: [0, 0, 0],
                    backgroundColor: ['#28a745', '#dc3545', '#ffc107']
                }]
            }
        });
    </script>
</body>
</html> 