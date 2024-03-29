/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * */
public class Revenue extends javax.swing.JFrame {

    /**
     * Creates new form Revenue
     */
    ResultSet rs;
    Connection con;
    PreparedStatement ps;
    server.Connection1 db = new server.Connection1();
    DefaultTableModel tblModel;
    Vector row;
    SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#,###");

    public Revenue() {
        initComponents();
        
        tblModel = new DefaultTableModel();
        tblModel.addColumn("Mã");
        tblModel.addColumn("Ngày/tháng/năm");
        tblModel.addColumn("Tiền thu (VNĐ)");
        tblRevenue.setModel(tblModel);
        loadTable();
      //  btnPrint.setEnabled(false);
    }

    public void loadTable() {
        try {
            con = db.getCon();
            ps = con.prepareStatement("select * from Revenue order by IDRevenue DESC");
            rs = ps.executeQuery();
            while (rs.next()) {
                row = new Vector();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(formatter.format(rs.getInt(3)));
                tblModel.addRow(row);
            }
            tblRevenue.setModel(tblModel);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
        }
        int line = tblRevenue.getRowCount();
        int tong = 0;
        for (int i = 0; i < line; i++) {
            String price = (String) tblRevenue.getValueAt(i, 2);
            tong += Integer.parseInt(price.replaceAll(",", ""));
        }
        //lbTong.setText(formatter.format(tong) + " VNĐ");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon("image//white1.png");
            public void paintComponent(Graphics g){
                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRevenue = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon("src//image//back_glossy.png");
            public void paintComponent(Graphics g){
                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        lbLoi = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();
        txtDate = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon("image//dog.jpg" + "");
            public void paintComponent(Graphics g){
                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width,d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản lý doanh thu");
        setResizable(false);

        tblRevenue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblRevenue.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblRevenue);

        lbLoi.setBackground(new java.awt.Color(255, 255, 255));
        lbLoi.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        lbLoi.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(350, 350, 350)
                .addComponent(lbLoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(58, 58, 58))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbLoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Tìm kiếm theo ngày:");

        btnPrint.setBackground(new java.awt.Color(255, 51, 51));
        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setText("Thống kê");
        btnPrint.setPreferredSize(new java.awt.Dimension(50, 50));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        txtDate.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtDateCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 131, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 127, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDateCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtDateCaretUpdate
        while (true) {
            if (!txtDate.getText().trim().matches("([0-9]{0,2}/)?([0-9]{0,2}/)?[0-9]{4}")) {
                lbLoi.setText("Nhập đúng định dạng để tìm kiếm: dd/MM/yyyy, MM/yyyy hoặc yyyy.");
                btnPrint.setEnabled(false);
                return;
            } else {
                lbLoi.setText("");
                break;
            }
        }
        tblModel.getDataVector().removeAllElements();
        try {
            ps = con.prepareStatement("select * from Revenue where Date like ?");
            ps.setString(1, "%" + (String) txtDate.getText().trim());
            rs = ps.executeQuery();
            if (rs.next()) {
                ps = con.prepareStatement("select * from Revenue where Date like ?");
                ps.setString(1, "%" + (String) txtDate.getText().trim());
                rs = ps.executeQuery();
                while (rs.next()) {
                    row = new Vector();
                    row.add(rs.getString(1));
                    row.add(rs.getString(2));
                    row.add(formatter.format(rs.getInt(3)));
                    tblModel.addRow(row);
                }
                tblRevenue.setModel(tblModel);
                btnPrint.setEnabled(true);
            } else {
                //lbLoi.setText("Không tìm thấy dữ liệu!");
                tblRevenue.removeAll();
                btnPrint.setEnabled(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, " Không thể kết nối đến máy chủ");
        }
    }//GEN-LAST:event_txtDateCaretUpdate

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        int line = tblRevenue.getRowCount();
        int tong = 0;
        for (int i = 0; i < line; i++) {
            String price = (String) tblRevenue.getValueAt(i, 2);
            tong += Integer.parseInt(price.replaceAll(",", ""));
        }
        JOptionPane.showMessageDialog(this, "Tổng doanh thu: " + formatter.format(tong) + " VND ");
    }//GEN-LAST:event_btnPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbLoi;
    private javax.swing.JTable tblRevenue;
    private javax.swing.JTextField txtDate;
    // End of variables declaration//GEN-END:variables
}
