package com.pcm.dao;

import com.pcm.model.Request;
import com.pcm.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {
    
    public boolean addRequest(Request request) {
        String sql = "INSERT INTO Requests (first_name, last_name, email, telephone, request_date, " +
                    "unit, status, request_type, technician_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, request.getFirstName());
            pstmt.setString(2, request.getLastName());
            pstmt.setString(3, request.getEmail());
            pstmt.setString(4, request.getTelephone());
            pstmt.setString(5, request.getRequestDate());
            pstmt.setString(6, request.getUnit());
            pstmt.setString(7, request.getStatus());
            pstmt.setString(8, request.getRequestType());
            pstmt.setInt(9, request.getTechnicianId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Request> getAllRequests() {
        List<Request> requests = new ArrayList<>();
        String sql = "SELECT * FROM Requests ORDER BY request_date DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Request request = new Request();
                request.setId(rs.getInt("id"));
                request.setFirstName(rs.getString("first_name"));
                request.setLastName(rs.getString("last_name"));
                request.setEmail(rs.getString("email"));
                request.setTelephone(rs.getString("telephone"));
                request.setRequestDate(rs.getString("request_date"));
                request.setUnit(rs.getString("unit"));
                request.setStatus(rs.getString("status"));
                request.setRequestType(rs.getString("request_type"));
                request.setTechnicianId(rs.getInt("technician_id"));
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
    
    public List<Request> getRequestsByTechnician(int technicianId) {
        List<Request> requests = new ArrayList<>();
        String sql = "SELECT * FROM Requests WHERE technician_id = ? ORDER BY request_date DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, technicianId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Request request = new Request();
                    request.setId(rs.getInt("id"));
                    request.setFirstName(rs.getString("first_name"));
                    request.setLastName(rs.getString("last_name"));
                    request.setEmail(rs.getString("email"));
                    request.setTelephone(rs.getString("telephone"));
                    request.setRequestDate(rs.getString("request_date"));
                    request.setUnit(rs.getString("unit"));
                    request.setStatus(rs.getString("status"));
                    request.setRequestType(rs.getString("request_type"));
                    request.setTechnicianId(rs.getInt("technician_id"));
                    requests.add(request);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
    
    public boolean updateRequest(Request request) {
        String sql = "UPDATE Requests SET first_name = ?, last_name = ?, email = ?, telephone = ?, " +
                    "request_date = ?, unit = ?, status = ?, request_type = ?, technician_id = ? " +
                    "WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, request.getFirstName());
            pstmt.setString(2, request.getLastName());
            pstmt.setString(3, request.getEmail());
            pstmt.setString(4, request.getTelephone());
            pstmt.setString(5, request.getRequestDate());
            pstmt.setString(6, request.getUnit());
            pstmt.setString(7, request.getStatus());
            pstmt.setString(8, request.getRequestType());
            pstmt.setInt(9, request.getTechnicianId());
            pstmt.setInt(10, request.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteRequest(int requestId) {
        String sql = "DELETE FROM Requests WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, requestId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Request> getRequestsByStatus(String status) {
        List<Request> requests = new ArrayList<>();
        String sql = "SELECT * FROM Requests WHERE status = ? ORDER BY request_date DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Request request = new Request();
                    request.setId(rs.getInt("id"));
                    request.setFirstName(rs.getString("first_name"));
                    request.setLastName(rs.getString("last_name"));
                    request.setEmail(rs.getString("email"));
                    request.setTelephone(rs.getString("telephone"));
                    request.setRequestDate(rs.getString("request_date"));
                    request.setUnit(rs.getString("unit"));
                    request.setStatus(rs.getString("status"));
                    request.setRequestType(rs.getString("request_type"));
                    request.setTechnicianId(rs.getInt("technician_id"));
                    requests.add(request);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
} 