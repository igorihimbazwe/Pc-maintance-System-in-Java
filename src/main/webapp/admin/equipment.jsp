<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pcm.model.User" %>
<%@ page import="com.pcm.model.PC" %>
<%@ page import="com.pcm.dao.PCDAO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Equipment Management - PC Maintenance System</title>
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
        
        PCDAO pcDAO = new PCDAO();
        List<PC> pcs = pcDAO.getAllPCs();
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
                        <a class="nav-link active" href="equipment.jsp">Equipment</a>
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
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Equipment Management</h2>
            <div>
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addPCModal">
                    <i class="bi bi-plus-circle"></i> Add PC
                </button>
                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addAccessoryModal">
                    <i class="bi bi-plus-circle"></i> Add Accessory
                </button>
                <button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#addNetworkDeviceModal">
                    <i class="bi bi-plus-circle"></i> Add Network Device
                </button>
            </div>
        </div>

        <ul class="nav nav-tabs" id="equipmentTabs" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="pcs-tab" data-bs-toggle="tab" data-bs-target="#pcs" type="button">
                    PCs
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="accessories-tab" data-bs-toggle="tab" data-bs-target="#accessories" type="button">
                    Accessories
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="network-devices-tab" data-bs-toggle="tab" data-bs-target="#network-devices" type="button">
                    Network Devices
                </button>
            </li>
        </ul>

        <div class="tab-content mt-3" id="equipmentTabsContent">
            <div class="tab-pane fade show active" id="pcs" role="tabpanel">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Brand</th>
                                <th>Specifications</th>
                                <th>Status</th>
                                <th>Location</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (PC pc : pcs) { %>
                            <tr>
                                <td><%= pc.getId() %></td>
                                <td><%= pc.getBrand() %></td>
                                <td>
                                    HDD: <%= pc.getHdd() %><br>
                                    RAM: <%= pc.getRam() %><br>
                                    OS: <%= pc.getOs() %><br>
                                    Year: <%= pc.getRegistrationYear() %>
                                </td>
                                <td>
                                    <span class="badge bg-<%= 
                                        "WORKING".equals(pc.getStatus()) ? "success" : 
                                        "NOT_WORKING".equals(pc.getStatus()) ? "danger" : 
                                        "DAMAGED".equals(pc.getStatus()) ? "warning" : "secondary" 
                                    %>">
                                        <%= pc.getStatus() %>
                                    </span>
                                </td>
                                <td><%= pc.getLocation() %></td>
                                <td>
                                    <button class="btn btn-sm btn-info" onclick="editPC(<%= pc.getId() %>)">
                                        <i class="bi bi-pencil"></i>
                                    </button>
                                    <button class="btn btn-sm btn-danger" onclick="deletePC(<%= pc.getId() %>)">
                                        <i class="bi bi-trash"></i>
                                    </button>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
            
            <div class="tab-pane fade" id="accessories" role="tabpanel">
                <!-- Accessories table will be added here -->
            </div>
            
            <div class="tab-pane fade" id="network-devices" role="tabpanel">
                <!-- Network devices table will be added here -->
            </div>
        </div>
    </div>

    <!-- Add PC Modal -->
    <div class="modal fade" id="addPCModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Add New PC</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form action="addPC" method="post">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="brand" class="form-label">Brand</label>
                            <input type="text" class="form-control" id="brand" name="brand" required>
                        </div>
                        <div class="mb-3">
                            <label for="hdd" class="form-label">HDD</label>
                            <input type="text" class="form-control" id="hdd" name="hdd" required>
                        </div>
                        <div class="mb-3">
                            <label for="ram" class="form-label">RAM</label>
                            <input type="text" class="form-control" id="ram" name="ram" required>
                        </div>
                        <div class="mb-3">
                            <label for="os" class="form-label">Operating System</label>
                            <input type="text" class="form-control" id="os" name="os" required>
                        </div>
                        <div class="mb-3">
                            <label for="registrationYear" class="form-label">Registration Year</label>
                            <input type="number" class="form-control" id="registrationYear" name="registrationYear" required>
                        </div>
                        <div class="mb-3">
                            <label for="status" class="form-label">Status</label>
                            <select class="form-select" id="status" name="status" required>
                                <option value="WORKING">Working</option>
                                <option value="NOT_WORKING">Not Working</option>
                                <option value="DAMAGED">Damaged</option>
                                <option value="OLD">Old</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="location" class="form-label">Location</label>
                            <select class="form-select" id="location" name="location" required>
                                <option value="LAB">Lab</option>
                                <option value="OFFICE">Office</option>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Add PC</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function editPC(id) {
            // Implement edit functionality
        }
        
        function deletePC(id) {
            if (confirm('Are you sure you want to delete this PC?')) {
                window.location.href = 'deletePC?id=' + id;
            }
        }
    </script>
</body>
</html> 