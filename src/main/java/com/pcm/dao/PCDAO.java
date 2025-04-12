package com.pcm.dao;

import com.pcm.model.PC;
import com.pcm.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PCDAO {
    
    public boolean addPC(PC pc) {
        String sql = "INSERT INTO PC (brand, hdd, ram, os, registration_year, status, location, technician_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pc.getBrand());
            pstmt.setString(2, pc.getHdd());
            pstmt.setString(3, pc.getRam());
            pstmt.setString(4, pc.getOs());
            pstmt.setInt(5, pc.getRegistrationYear());
            pstmt.setString(6, pc.getStatus());
            pstmt.setString(7, pc.getLocation());
            pstmt.setInt(8, pc.getTechnicianId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<PC> getAllPCs() {
        List<PC> pcs = new ArrayList<>();
        String sql = "SELECT * FROM PC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                PC pc = new PC();
                pc.setId(rs.getInt("id"));
                pc.setBrand(rs.getString("brand"));
                pc.setHdd(rs.getString("hdd"));
                pc.setRam(rs.getString("ram"));
                pc.setOs(rs.getString("os"));
                pc.setRegistrationYear(rs.getInt("registration_year"));
                pc.setStatus(rs.getString("status"));
                pc.setLocation(rs.getString("location"));
                pc.setTechnicianId(rs.getInt("technician_id"));
                pcs.add(pc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pcs;
    }
    
    public List<PC> getPCsByTechnician(int technicianId) {
        List<PC> pcs = new ArrayList<>();
        String sql = "SELECT * FROM PC WHERE technician_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, technicianId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PC pc = new PC();
                    pc.setId(rs.getInt("id"));
                    pc.setBrand(rs.getString("brand"));
                    pc.setHdd(rs.getString("hdd"));
                    pc.setRam(rs.getString("ram"));
                    pc.setOs(rs.getString("os"));
                    pc.setRegistrationYear(rs.getInt("registration_year"));
                    pc.setStatus(rs.getString("status"));
                    pc.setLocation(rs.getString("location"));
                    pc.setTechnicianId(rs.getInt("technician_id"));
                    pcs.add(pc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pcs;
    }
    
    public boolean updatePC(PC pc) {
        String sql = "UPDATE PC SET brand = ?, hdd = ?, ram = ?, os = ?, registration_year = ?, " +
                    "status = ?, location = ?, technician_id = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pc.getBrand());
            pstmt.setString(2, pc.getHdd());
            pstmt.setString(3, pc.getRam());
            pstmt.setString(4, pc.getOs());
            pstmt.setInt(5, pc.getRegistrationYear());
            pstmt.setString(6, pc.getStatus());
            pstmt.setString(7, pc.getLocation());
            pstmt.setInt(8, pc.getTechnicianId());
            pstmt.setInt(9, pc.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deletePC(int pcId) {
        String sql = "DELETE FROM PC WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, pcId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<PC> getPCsByStatus(String status) {
        List<PC> pcs = new ArrayList<>();
        String sql = "SELECT * FROM PC WHERE status = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PC pc = new PC();
                    pc.setId(rs.getInt("id"));
                    pc.setBrand(rs.getString("brand"));
                    pc.setHdd(rs.getString("hdd"));
                    pc.setRam(rs.getString("ram"));
                    pc.setOs(rs.getString("os"));
                    pc.setRegistrationYear(rs.getInt("registration_year"));
                    pc.setStatus(rs.getString("status"));
                    pc.setLocation(rs.getString("location"));
                    pc.setTechnicianId(rs.getInt("technician_id"));
                    pcs.add(pc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pcs;
    }
} 