/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Tung
 */
public class Connection1 {

    Connection con;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Connection1() {
     
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = (Connection) DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=test4","sa","123");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Lỗi 100:: Không tìm thấy lớp");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
        } 
       
    }

}
