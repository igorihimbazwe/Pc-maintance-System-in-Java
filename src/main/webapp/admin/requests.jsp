<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pcm.model.User" %>
<%@ page import="com.pcm.model.Request" %>
<%@ page import="com.pcm.dao.RequestDAO" %>
<%@ page import="com.pcm.dao.UserDAO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Requests Management - PC Maintenance System</title>
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
        
        RequestDAO requestDAO = new RequestDAO();
        UserDAO userDAO = new UserDAO();
        List<Request> requests = requestDAO.getAllRequests();
        List<User> technicians = userDAO.getAllTechnicians();
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
                        <a class="nav-link" href="dashboard.jsp">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="technicians.jsp">Technicians</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="equipment.jsp">Equipment</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="requests.jsp">Requests</a>
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
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Requests Management</h2>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addRequestModal">
                <i class="bi bi-plus-circle"></i> Add New Request
            </button>
        </div>

        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Requester</th>
                        <th>Contact</th>
                        <th>Unit</th>
                        <th>Request Type</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Technician</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Request request : requests) { %>
                    <tr>
                        <td><%= request.getId() %></td>
                        <td>
                            <%= request.getFirstName() %> <%= request.getLastName() %>
                        </td>
                        <td>
                            <%= request.getEmail() %><br>
                            <%= request.getTelephone() %>
                        </td>
                        <td><%= request.getUnit() %></td>
                        <td><%= request.getRequestType() %></td>
                        <td><%= request.getRequestDate() %></td>
                        <td>
                            <span class="badge bg-<%= 
                                "PENDING".equals(request.getStatus()) ? "warning" : 
                                "TECHNICIAN_ASSIGNED".equals(request.getStatus()) ? "info" : 
                                "FIXED".equals(request.getStatus()) ? "success" : "danger" 
                            %>">
                                <%= request.getStatus() %>
                            </span>
                        </td>
                        <td>
                            <% if (request.getTechnicianId() > 0) { 
                                User technician = userDAO.getUserById(request.getTechnicianId());
                                if (technician != null) {
                            %>
                                <%= technician.getUsername() %>
                            <% } } else { %>
                                Not assigned
                            <% } %>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-info" onclick="assignTechnician(<%= request.getId() %>)">
                                <i class="bi bi-person-plus"></i>
                            </button>
                            <button class="btn btn-sm btn-success" onclick="updateStatus(<%= request.getId() %>)">
                                <i class="bi bi-check-circle"></i>
                            </button>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Assign Technician Modal -->
    <div class="modal fade" id="assignTechnicianModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Assign Technician</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form action="assignTechnician" method="post">
                    <input type="hidden" id="requestId" name="requestId">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="technician" class="form-label">Select Technician</label>
                            <select class="form-select" id="technician" name="technicianId" required>
                                <option value="">Select a technician</option>
                                <% for (User technician : technicians) { %>
                                <option value="<%= technician.getId() %>"><%= technician.getUsername() %></option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Assign</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function assignTechnician(requestId) {
            document.getElementById('requestId').value = requestId;
            new bootstrap.Modal(document.getElementById('assignTechnicianModal')).show();
        }
        
        function updateStatus(requestId) {
            if (confirm('Are you sure you want to mark this request as fixed?')) {
                window.location.href = 'updateRequestStatus?id=' + requestId + '&status=FIXED';
            }
        }
    </script>
</body>
</html> 