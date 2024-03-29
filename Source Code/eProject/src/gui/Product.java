package gui;

import gui.ComboListener;
import gui.SetImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ContainerListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

public class Product extends javax.swing.JFrame {

    DefaultTableModel tblModelSP, tblModelLoaiSP,model,tblModelPP;
    DefaultComboBoxModel cbModelSP, cbModelLoaiSP;
    Connection conSP, conLoaiSP,conPP;
    ResultSet rsSP, rsLoaiSP, rsIDPr, rsTen, rsTK, rsAdd1,rspp;
    server.Connection1 db = new server.Connection1();
    PreparedStatement psSP, psLoaiSP, psSP1, psTen, psTK, psAdd1, psAdd2,pspp;
    Vector rowSP, rowLoaiSP, vecTK, vecTen, rowTen, vec1;   
  
     SetImage img = new SetImage();
    public Product() {
        initComponents();
        conSP = db.getCon();
        conLoaiSP = db.getCon();
        conPP = db.getCon();
        cbChonTimKiem.setVisible(false);
        btnDeleteSP.setEnabled(false);
        btnUpdateSP.setEnabled(false);
        btnXoaLoaiSP.setEnabled(true);
        btnSuaLoaiSP.setEnabled(true);

        tblModelSP = new DefaultTableModel();
        tblModelSP.addColumn("ID");
        tblModelSP.addColumn("Tên");
        tblModelSP.addColumn("Loại");
        tblModelSP.addColumn("Giá");
        tblModelSP.addColumn("Đơn vị");
        tblModelSP.addColumn("Mã nhà phân phối");
        tblModelSP.addColumn("Số lượng");
        tblSP.setModel(tblModelSP);
        loadDataSP();

        tblModelLoaiSP = new DefaultTableModel();
        tblModelLoaiSP.addColumn("ID");
        tblModelLoaiSP.addColumn("Loại");
        tblModelLoaiSP.addColumn("Kích cỡ");
        tblLoaiSP.setModel(tblModelLoaiSP);
        loadDataLoaiSP();
        btnTimKiem.setVisible(false);
        cbTen.setVisible(false);
        PanelTimKiem.setVisible(false);
        
        
        tblModelPP = new DefaultTableModel();
        tblModelPP.addColumn("Mã số");
        tblModelPP.addColumn("Tên nhà phân phối");
        tblModelPP.addColumn("Số điện thoại");
        tblModelPP.addColumn("Email");
        tblModelPP.addColumn("Địa chỉ");
        
        tblPP.setModel(tblModelPP);
        loadDataPP();
        
      lbdgo.setSize(200,200);
      new SetImage().setImageLabel(lbdgo, "image//dog.jpg");
     
    }

    private void filter(String query)
    {
        model = (DefaultTableModel) tblLoaiSP.getModel(); 
        TableRowSorter<DefaultTableModel> model1 = new TableRowSorter<DefaultTableModel>(model);
        tblLoaiSP.setRowSorter(model1);
        model1.setRowFilter(RowFilter.regexFilter(query));
    }
    
    
     private void filter1(String query)
    {
        model = (DefaultTableModel) tblSP.getModel(); 
        TableRowSorter<DefaultTableModel> model1 = new TableRowSorter<DefaultTableModel>(model);
        tblSP.setRowSorter(model1);
        model1.setRowFilter(RowFilter.regexFilter(query));
    }
    
    
    
    private void loadDataSP() {
        try {
            String sql = "select * from Product inner join ProductType on Product.IDType=ProductType.IDType";
            psSP = conSP.prepareStatement(sql);
            rsSP = psSP.executeQuery();
            while (rsSP.next()) {
                rowSP = new Vector();
                rowSP.add(rsSP.getString("IDProduct"));
                rowSP.add(rsSP.getString("ProductName"));
                rowSP.add(rsSP.getString("IDType"));
                rowSP.add(rsSP.getString("Price"));
                rowSP.add(rsSP.getString("Size"));
                rowSP.add(rsSP.getString("code"));
                rowSP.add(rsSP.getString("acc"));
                tblModelSP.addRow(rowSP);
            }
            tblSP.setModel(tblModelSP);
        } catch (Exception e) {
        }
        cbNameType.removeAllItems();
        try {
            String sql = "select DISTINCT TypeName from  ProductType";
            psSP = conSP.prepareStatement(sql);
            rsSP = psSP.executeQuery();
            while (rsSP.next()) {
                cbNameType.addItem(rsSP.getString(1));
            }
        } catch (Exception e) {
        }
        
        cbid.removeAllItems();
        try{
         String sql = "select DISTINCT code from store";
            pspp = conPP.prepareStatement(sql);
            rspp = pspp.executeQuery();
            while (rspp.next()) {
                cbid.addItem(rspp.getString(1));
        }}
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
        

    
    }
    private void loadDataLoaiSP() {
        try {
            conLoaiSP = db.getCon();
            String sql = "select * from ProductType";
            psLoaiSP = conLoaiSP.prepareStatement(sql);
            rsLoaiSP = psLoaiSP.executeQuery();
            while (rsLoaiSP.next()) {
                rowLoaiSP = new Vector();
                rowLoaiSP.add(rsLoaiSP.getString(1));
                rowLoaiSP.add(rsLoaiSP.getString(2));
                rowLoaiSP.add(rsLoaiSP.getString(3));
                tblModelLoaiSP.addRow(rowLoaiSP);
            }
            tblLoaiSP.setModel(tblModelLoaiSP);
        } catch (Exception e) {
        }
    }
    
      private void loadDataPP() {
        try {
            conPP = db.getCon();
            String sql = "select * from Store";
            pspp = conPP.prepareStatement(sql);
            rspp = pspp.executeQuery();
            while (rspp.next()) {
                vec1 = new Vector();
                vec1.add(rspp.getString(1));
                vec1.add(rspp.getString(2));
                vec1.add(rspp.getString(3));
                vec1.add(rspp.getString(4));
                vec1.add(rspp.getString(5));
                tblModelPP.addRow(vec1);
            }
            tblPP.setModel(tblModelPP);
        } catch (Exception e) {
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon("image//white1.png");
            public void paintComponent(Graphics g){
                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        jPanel2 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon("src//image//trongsuot.png");
            public void paintComponent(Graphics g){
                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        jTabbedPane1 = new javax.swing.JTabbedPane(){
            ImageIcon icon = new ImageIcon("src//image//trongsuot.png");
            public void paintComponent(Graphics g){
                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        jpanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtIDProduct = new javax.swing.JTextField();
        txtNameproduct = new javax.swing.JTextField();
        cbNameType = new javax.swing.JComboBox();
        txtPrice = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable(){
            ImageIcon icon = new ImageIcon("src//image//trongsuot.jpg");
            public void paintComponent(Graphics g){
                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        jLabel9 = new javax.swing.JLabel();
        cbKichCo = new javax.swing.JComboBox();
        PanelTimKiem = new javax.swing.JPanel();
        cbTen = new javax.swing.JComboBox();
        lbGiaTu = new javax.swing.JLabel();
        lbDen = new javax.swing.JLabel();
        txtGiaTu = new javax.swing.JTextField();
        txtDen = new javax.swing.JTextField();
        lbNhom = new javax.swing.JLabel();
        lbLoai = new javax.swing.JLabel();
        cbLoaiTK = new javax.swing.JComboBox();
        cbKichThuoc = new javax.swing.JComboBox();
        lbVND = new javax.swing.JLabel();
        lbVND1 = new javax.swing.JLabel();
        cbChonTimKiem = new javax.swing.JComboBox();
        btnTimKiem = new javax.swing.JButton();
        btnAddSP = new javax.swing.JButton();
        btnUpdateSP = new javax.swing.JButton();
        btnResetSP = new javax.swing.JButton();
        btnDeleteSP = new javax.swing.JButton();
        txt2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cbid = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        txtacc = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon("image//trongsuot.jpg");
            public void paintComponent(Graphics g){
                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        btnThemLoaiSP = new javax.swing.JButton();
        btnResetLoaiSP = new javax.swing.JButton();
        btnSuaLoaiSP = new javax.swing.JButton();
        btnXoaLoaiSP = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cbSizeType = new javax.swing.JComboBox();
        txtNameType = new javax.swing.JTextField();
        txtIDProductType = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLoaiSP = new javax.swing.JTable(){
            ImageIcon icon = new ImageIcon("src//image//trongsuot.jpg");
            public void paintComponent(Graphics g){
                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPP = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtname1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtphone1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtmail = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtadd = new javax.swing.JTextField();
        btnaddpp = new javax.swing.JButton();
        btndeletepp = new javax.swing.JButton();
        btnuppp = new javax.swing.JButton();
        btnrepp = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtcode = new javax.swing.JTextField();
        lbcode = new javax.swing.JLabel();
        lbname = new javax.swing.JLabel();
        lbphone = new javax.swing.JLabel();
        lbmail1 = new javax.swing.JLabel();
        lbadd = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbdgo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản lý sản phẩm");

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jpanel.setBackground(new java.awt.Color(255, 255, 255));
        jpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("ID:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Tên Sản Phẩm :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Loại Sản Phẩm :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Giá Sản Phẩm :");

        cbNameType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblSP.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblSP.setRowHeight(30);
        tblSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSP);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Đơn vị");

        cbKichCo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bịch", "Thùng", "Gói", "Chai ", "Lon", "Cái" }));

        PanelTimKiem.setBackground(new java.awt.Color(255, 255, 255));

        cbTen.setEditable(true);
        cbTen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbGiaTu.setText("Giá từ:");

        lbDen.setText("Đến :");

        txtGiaTu.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtDen.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        lbNhom.setText("Nhóm:");

        lbLoai.setText("Kích Thước:");

        cbLoaiTK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbKichThuoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nhỏ", "Vừa", "Lớn" }));

        lbVND.setText("VNĐ");

        lbVND1.setText("VNĐ");

        javax.swing.GroupLayout PanelTimKiemLayout = new javax.swing.GroupLayout(PanelTimKiem);
        PanelTimKiem.setLayout(PanelTimKiemLayout);
        PanelTimKiemLayout.setHorizontalGroup(
            PanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTimKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbTen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PanelTimKiemLayout.createSequentialGroup()
                        .addGroup(PanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbGiaTu)
                            .addComponent(lbDen))
                        .addGap(18, 18, 18)
                        .addGroup(PanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtGiaTu, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(txtDen))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbVND1)
                            .addComponent(lbVND))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PanelTimKiemLayout.createSequentialGroup()
                        .addComponent(lbLoai)
                        .addGap(28, 28, 28)
                        .addComponent(cbKichThuoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelTimKiemLayout.createSequentialGroup()
                        .addComponent(lbNhom)
                        .addGap(18, 18, 18)
                        .addComponent(cbLoaiTK, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PanelTimKiemLayout.setVerticalGroup(
            PanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTimKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaTu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbVND)
                    .addComponent(lbGiaTu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbDen)
                    .addComponent(txtDen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbVND1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNhom)
                    .addComponent(cbLoaiTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbLoai))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        cbChonTimKiem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---Tìm kiếm theo---", "Tên", "Giá", "Nhóm" }));
        cbChonTimKiem.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbChonTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbChonTimKiemActionPerformed(evt);
            }
        });

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnAddSP.setBackground(new java.awt.Color(255, 51, 51));
        btnAddSP.setForeground(new java.awt.Color(255, 255, 255));
        btnAddSP.setText("Thêm Mới");
        btnAddSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSPActionPerformed(evt);
            }
        });

        btnUpdateSP.setBackground(new java.awt.Color(255, 0, 51));
        btnUpdateSP.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateSP.setText("Sửa");
        btnUpdateSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSPActionPerformed(evt);
            }
        });

        btnResetSP.setBackground(new java.awt.Color(255, 0, 51));
        btnResetSP.setForeground(new java.awt.Color(255, 255, 255));
        btnResetSP.setText("Làm Mới");
        btnResetSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetSPActionPerformed(evt);
            }
        });

        btnDeleteSP.setBackground(new java.awt.Color(255, 0, 51));
        btnDeleteSP.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteSP.setText("Xóa");
        btnDeleteSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSPActionPerformed(evt);
            }
        });

        txt2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt2KeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Tìm kiếm");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Mã nhà phân phối:");

        cbid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Số lượng:");

        javax.swing.GroupLayout jpanelLayout = new javax.swing.GroupLayout(jpanel);
        jpanel.setLayout(jpanelLayout);
        jpanelLayout.setHorizontalGroup(
            jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelLayout.createSequentialGroup()
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(PanelTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbChonTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jpanelLayout.createSequentialGroup()
                                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpanelLayout.createSequentialGroup()
                                        .addGap(182, 182, 182)
                                        .addComponent(btnTimKiem))
                                    .addGroup(jpanelLayout.createSequentialGroup()
                                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jpanelLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel7)
                                                        .addComponent(jLabel9))
                                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelLayout.createSequentialGroup()
                                                .addGap(25, 25, 25)
                                                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNameproduct)
                                            .addComponent(cbNameType, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtPrice, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtIDProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                            .addComponent(cbKichCo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbid, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtacc))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                                .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelLayout.createSequentialGroup()
                                .addComponent(btnAddSP)
                                .addGap(41, 41, 41)
                                .addComponent(btnUpdateSP, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnResetSP, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeleteSP, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80))))))
        );
        jpanelLayout.setVerticalGroup(
            jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelLayout.createSequentialGroup()
                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtIDProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNameproduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cbNameType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(31, 31, 31)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cbKichCo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtacc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDeleteSP)
                            .addComponent(btnResetSP)
                            .addComponent(btnUpdateSP)
                            .addComponent(btnAddSP))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbChonTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PanelTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(btnTimKiem))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sản Phẩm", jpanel);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btnThemLoaiSP.setBackground(new java.awt.Color(255, 51, 51));
        btnThemLoaiSP.setForeground(new java.awt.Color(255, 255, 255));
        btnThemLoaiSP.setText("Thêm Mới");
        btnThemLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLoaiSPActionPerformed(evt);
            }
        });

        btnResetLoaiSP.setBackground(new java.awt.Color(255, 51, 51));
        btnResetLoaiSP.setForeground(new java.awt.Color(255, 255, 255));
        btnResetLoaiSP.setText("Làm Mới");
        btnResetLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetLoaiSPActionPerformed(evt);
            }
        });

        btnSuaLoaiSP.setBackground(new java.awt.Color(255, 51, 51));
        btnSuaLoaiSP.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaLoaiSP.setText("Sửa");
        btnSuaLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaLoaiSPActionPerformed(evt);
            }
        });

        btnXoaLoaiSP.setBackground(new java.awt.Color(255, 51, 51));
        btnXoaLoaiSP.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaLoaiSP.setText("Xóa");
        btnXoaLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaLoaiSPActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Đơn vị");

        cbSizeType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bịch", "Thùng", "Gói", "Chai ", "Lon", "Cái" }));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("ID Loại Sản Phẩm :");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Tên Loại Sản Phẩm :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Tìm kiếm");

        txt1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtIDProductType)
                                .addComponent(txtNameType)
                                .addComponent(cbSizeType, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGap(51, 51, 51)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnThemLoaiSP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuaLoaiSP)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaLoaiSP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnResetLoaiSP)))
                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtIDProductType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNameType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(28, 28, 28)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbSizeType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(44, 44, 44)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSuaLoaiSP)
                        .addComponent(btnThemLoaiSP))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnXoaLoaiSP)
                        .addComponent(btnResetLoaiSP)))
                .addContainerGap())
        );

        tblLoaiSP.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tblLoaiSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblLoaiSP.setRowHeight(30);
        tblLoaiSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiSPMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblLoaiSP);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 88, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Loại Sản Phẩm", jPanel4);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblPP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblPP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPPMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblPP);

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Tên nhà phân phối:");

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Số điện thoại");

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Email");

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Địa chỉ");

        btnaddpp.setBackground(new java.awt.Color(255, 51, 51));
        btnaddpp.setForeground(new java.awt.Color(255, 255, 255));
        btnaddpp.setText("Thêm");
        btnaddpp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddppActionPerformed(evt);
            }
        });

        btndeletepp.setBackground(new java.awt.Color(255, 51, 51));
        btndeletepp.setForeground(new java.awt.Color(255, 255, 255));
        btndeletepp.setText("Xóa");
        btndeletepp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteppActionPerformed(evt);
            }
        });

        btnuppp.setBackground(new java.awt.Color(255, 51, 51));
        btnuppp.setForeground(new java.awt.Color(255, 255, 255));
        btnuppp.setText("Sửa");
        btnuppp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupppActionPerformed(evt);
            }
        });

        btnrepp.setBackground(new java.awt.Color(255, 51, 51));
        btnrepp.setForeground(new java.awt.Color(255, 255, 255));
        btnrepp.setText("Làm mới");
        btnrepp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnreppActionPerformed(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Mã nhà phân phối:");

        lbcode.setForeground(new java.awt.Color(255, 0, 0));

        lbname.setForeground(new java.awt.Color(255, 0, 0));

        lbphone.setForeground(new java.awt.Color(255, 0, 0));

        lbmail1.setForeground(new java.awt.Color(255, 0, 0));

        lbadd.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(btnaddpp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btndeletepp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnrepp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnuppp, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 99, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(txtcode))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtname1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                                    .addComponent(lbname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtphone1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                                    .addComponent(lbphone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtmail)
                                    .addComponent(lbmail1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbadd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtadd))))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 90, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcode, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbcode, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtname1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(lbname, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtphone1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbphone, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtmail, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbmail1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtadd, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbadd, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnaddpp)
                    .addComponent(btndeletepp)
                    .addComponent(btnuppp)
                    .addComponent(btnrepp))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Nhà phân phối", jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel16.setText("Quản lý sản phẩm");
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbdgo, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(245, 245, 245)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbdgo, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
        loadDataSP();
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void tblLoaiSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiSPMouseClicked
        // TODO add your handling code here:
        btnXoaLoaiSP.setEnabled(true);
        btnSuaLoaiSP.setEnabled(true);
        btnThemLoaiSP.setEnabled(false);
        int line1 = tblLoaiSP.getSelectedRow();
        String ID1 = (String) tblModelLoaiSP.getValueAt(line1, 0);
        String Name1 = (String) tblModelLoaiSP.getValueAt(line1, 1);
        String Size = (String) tblModelLoaiSP.getValueAt(line1, 2);
      
        txtIDProductType.setText(ID1);
        txtNameType.setText(Name1);
        cbSizeType.setSelectedItem(Size);
        txtIDProductType.setEnabled(false);
       
    }//GEN-LAST:event_tblLoaiSPMouseClicked

    private void txt1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt1KeyReleased
        String query =txt1.getText();
        filter(query);
    }//GEN-LAST:event_txt1KeyReleased

    private void btnXoaLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLoaiSPActionPerformed
        // TODO add your handling code here:
        String xoa = "select * from Product where IDType=?";

        try {
            psLoaiSP = conLoaiSP.prepareStatement(xoa);
            psLoaiSP.setString(1, txtIDProductType.getText());
            rsLoaiSP = psLoaiSP.executeQuery();
            if (!rsLoaiSP.next()) {
                int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa loại sản phẩm không?");
                if (click == 0) {
                    try {
                        String sql = "Delete ProductType where IDType=?";
                        psLoaiSP = conLoaiSP.prepareStatement(sql);
                        psLoaiSP.setString(1, txtIDProductType.getText());
                        psLoaiSP.executeUpdate();
                        tblModelLoaiSP.getDataVector().removeAllElements();
                        loadDataLoaiSP();
                        btnResetActionPerformed(null);
                        JOptionPane.showMessageDialog(null, "Xóa loại sản phẩm thành công");
                    } catch (Exception e) {
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa loại sản phẩm không thành công");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Sản phẩm còn tồn tại, không thể xóa");
            }
        } catch (SQLException ex) {
        }
        tblModelLoaiSP.getDataVector().removeAllElements();
        loadDataLoaiSP();
    }//GEN-LAST:event_btnXoaLoaiSPActionPerformed

    private void btnSuaLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaLoaiSPActionPerformed
        // TODO add your handling code here:

        JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa loại sản phẩm không?");
        if (JOptionPane.YES_OPTION == 0) {
            try {
                String sql = "update ProductType set TypeName=?, Size=? where IDType=?";
                psLoaiSP = conLoaiSP.prepareStatement(sql);
                psLoaiSP.setString(1, txtNameType.getText());
                psLoaiSP.setString(2, (String) cbSizeType.getSelectedItem());
                psLoaiSP.setString(3, txtIDProductType.getText());
                psLoaiSP.executeUpdate();
                tblModelLoaiSP.getDataVector().removeAllElements();
                loadDataLoaiSP();
                btnResetActionPerformed(null);
                JOptionPane.showMessageDialog(null, "Sửa loại sản phẩm thành công");
                tblModelLoaiSP.getDataVector().removeAllElements();
                loadDataLoaiSP();
            } catch (Exception e) {
            }
        } else {
            JOptionPane.showMessageDialog(null, "Sửa loại sản phẩm không thành công");
        }
    }//GEN-LAST:event_btnSuaLoaiSPActionPerformed

    private void btnResetLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetLoaiSPActionPerformed
        txtIDProductType.setText("");
        txtNameType.setText("");
        cbSizeType.setSelectedIndex(0);//re set combobox
        txtIDProductType.setEnabled(true);
        btnXoaLoaiSP.setEnabled(false);
        btnSuaLoaiSP.setEnabled(false);
        btnThemLoaiSP.setEnabled(true);
    }//GEN-LAST:event_btnResetLoaiSPActionPerformed

    private void btnThemLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLoaiSPActionPerformed
        // TODO add your handling code here:
        int line = tblModelLoaiSP.getRowCount();
        int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm loại sản phẩm không?");
        if (click == 0) {
            try {
                while (true) {
                    for (int i = 0; i < line; i++) {
                        if (txtIDProductType.getText().trim().equals(tblModelLoaiSP.getValueAt(i, 0))) {
                            JOptionPane.showMessageDialog(this, "ID trùng");
                            txtIDProductType.grabFocus();
                            return;
                        }
                    }
                    if (txtIDProductType.getText().trim().equals("")) {
                        JOptionPane.showMessageDialog(this, "ID không được để trống");
                        txtIDProductType.grabFocus();
                        return;
                    } else if (!txtIDProductType.getText().trim().matches("[A-Za-z]{2}[0-9]{2}")) {
                        JOptionPane.showMessageDialog(this, "ID phải bắt đầu bằng chữ T và 2 số ");
                        txtIDProductType.grabFocus();
                        return;
                    } else {
                        break;
                    }
                }

                while (true) {
                    String sql2 = "select * from ProductType";
                    psLoaiSP = conSP.prepareStatement(sql2);
                    //                psLoaiSP.setString(1, (String) cbNameType.getSelectedItem());
                    //                psLoaiSP.setString(2, (String) cbKichCo.getSelectedItem());
                    rsLoaiSP = psLoaiSP.executeQuery();
                    if (rsLoaiSP.next()) {
                        String idprotype = rsLoaiSP.getString(1);

                    }
                    for (int i = 0; i < line; i++) {
                        if (txtNameType.getText().equals(tblModelLoaiSP.getValueAt(i, 1))
                            && (cbSizeType.getSelectedItem().equals(tblModelLoaiSP.getValueAt(i, 2)))) {
                            JOptionPane.showMessageDialog(this, "Loại sản phẩm đã tồn tại");
                            txtNameType.grabFocus();
                            return;
                        }
                    }

                    if (txtNameType.getText().trim().equals("")) {
                        JOptionPane.showMessageDialog(this, "Tên không được để trống");
                        txtNameType.grabFocus();
                        return;
                    } else {
                        break;
                    }
                }
                String sql = "insert into ProductType values(?,?,?)";
                psLoaiSP = conLoaiSP.prepareStatement(sql);
                psLoaiSP.setString(1, txtIDProductType.getText());
                psLoaiSP.setString(2, txtNameType.getText());
                psLoaiSP.setString(3, (String) cbSizeType.getSelectedItem());//cbBox
                psLoaiSP.executeUpdate();
                tblModelLoaiSP.getDataVector().removeAllElements();
                loadDataLoaiSP();
                btnResetActionPerformed(null);
                JOptionPane.showMessageDialog(null, "Thêm loại sản phẩm thành công");
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_btnThemLoaiSPActionPerformed

    private void txt2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt2KeyReleased
        String query = txt2.getText();
        filter1(query);
    }//GEN-LAST:event_txt2KeyReleased

    private void btnDeleteSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSPActionPerformed
        // TODO add your handling code here:
        int de = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa sản phẩm không?");
        if (de == 0) {
            try {
                String sql1 = "select * from OrderDetails where IDProduct=?";
                psSP = conSP.prepareStatement(sql1);
                psSP.setString(1, txtIDProduct.getText());
                rsIDPr = psSP.executeQuery();
                if (rsIDPr.next()) {
                    JOptionPane.showMessageDialog(null, "Sản phẩm đã có đơn hàng, không thể xóa sản phẩm.");
                } else {
                    try {
                        String sql = "Delete Product where IDProduct=?";
                        psSP = conSP.prepareStatement(sql);
                        psSP.setString(1, txtIDProduct.getText());
                        psSP.executeUpdate();
                        tblModelSP.getDataVector().removeAllElements();
                        loadDataSP();
                        btnResetActionPerformed(null);
                        JOptionPane.showMessageDialog(null, "Xóa sản phẩm thành công.");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            } catch (SQLException e) {
            }

        }
    }//GEN-LAST:event_btnDeleteSPActionPerformed

    private void btnResetSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSPActionPerformed
        // TODO add your handling code here:
        txtIDProduct.setText("");
        txtNameproduct.setText("");
        txtPrice.setText("");
        txtacc.setText("");
        cbNameType.setSelectedIndex(0);//re set combobox
        cbKichCo.setSelectedIndex(0);//re set combobox
        txtIDProduct.setEnabled(true);
        btnDeleteSP.setEnabled(false);
        btnUpdateSP.setEnabled(false);
        btnAddSP.setEnabled(true);
        cbChonTimKiem.setSelectedIndex(0);
        cbid.setEnabled(true);
        
    }//GEN-LAST:event_btnResetSPActionPerformed

    private void btnUpdateSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSPActionPerformed
        // TODO add your handling code here:
        int a = JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa sản phẩm không?");
        if (a == 0) {

            try {
//                
//              psSP =  conSP.prepareStatement("update Product set ProductName=?, IDType=?, Price=?,acc=? where IDProduct=?");
//              psSP.setString(1, txtNameproduct.getText());
//              psSP.setString(2, cbNameType.getSelectedItem().toString());
//              psSP.setString(1, txtNameproduct.getText());
//                
//                
//                    
                String sql2 = "select * from ProductType where TypeName=? and Size=?";
                psLoaiSP = conSP.prepareStatement(sql2);
                psLoaiSP.setString(1, (String) cbNameType.getSelectedItem());
                psLoaiSP.setString(2, (String) cbKichCo.getSelectedItem());
                rsLoaiSP = psLoaiSP.executeQuery();
                
                
                if (rsLoaiSP.next()) {
                    String sql = "update Product set ProductName=?, IDType=?, Price=?,acc=? where IDProduct=?";
                    psSP = conSP.prepareStatement(sql);
                    psSP.setString(1, txtNameproduct.getText());
                    psSP.setString(2, rsLoaiSP.getString(1));//cbBox
                    psSP.setInt(3, Integer.parseInt(txtPrice.getText()));//kieu int
                    psSP.setString(4, txtacc.getText());
                    
                    psSP.setString(5, txtIDProduct.getText());
                 
                    psSP.setString(PROPERTIES, sql);
                    psSP.executeUpdate();
                    tblModelSP.getDataVector().removeAllElements();
                    loadDataSP();
                    btnResetActionPerformed(null);
                    JOptionPane.showMessageDialog(null, "Sửa sản phẩm thành công");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy nhóm sản phẩm.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_btnUpdateSPActionPerformed

    private void btnAddSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSPActionPerformed

        // TODO add your handling code here:
        int line = tblModelSP.getRowCount();//diem quet so dong kiem tra trung nhau
        int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm sản phẩm không?");
        if (click == 0) {
            try {
                psAdd1 = conLoaiSP.prepareStatement("select * from ProductType where TypeName=? and Size=?");
                psAdd1.setString(1, (String) cbNameType.getSelectedItem());
                psAdd1.setString(2, (String) cbKichCo.getSelectedItem());
                rsAdd1 = psAdd1.executeQuery();
                if (rsAdd1.next()) {
                    while (true) {
                        for (int i = 0; i < line; i++) {
                            if (txtIDProduct.getText().trim().equals(tblModelSP.getValueAt(i, 0))) {
                                JOptionPane.showMessageDialog(this, "ID sản phẩm trùng");
                                txtIDProduct.grabFocus();
                                return;
                            }
                        }

                        if (txtIDProduct.getText().trim().equals("")) {
                            JOptionPane.showMessageDialog(this, "ID không được để trống");
                            txtIDProduct.grabFocus();
                            return;
                        } else if (!txtIDProduct.getText().trim().matches("[A-Za-z]{2}\\d{2,2}")) {
                            JOptionPane.showMessageDialog(this, "ID phải bắt đầu bằng 2 chữ và 2 số ");
                            txtIDProduct.grabFocus();
                            return;
                        } else {
                            break;
                        }
                    }

                    while (true) {
                        if (txtNameproduct.getText().trim().equals("")) {
                            JOptionPane.showMessageDialog(this, "Tên không được để trống");
                            txtNameproduct.grabFocus();
                            return;
                        } else {
                            break;
                        }
                    }
                    while (true) {
                        if (txtPrice.getText().trim().equals("")) {
                            JOptionPane.showMessageDialog(this, "Giá không được để trống");
                            txtPrice.grabFocus();
                            return;
                        } else if (!txtPrice.getText().trim().matches("[0-9]+")) {
                            JOptionPane.showMessageDialog(this, "Giá phải là số và lớn hơn 0");
                            txtPrice.grabFocus();
                            return;
                        } else if ((Integer.parseInt(txtPrice.getText())) <= 0 || Integer.parseInt(txtPrice.getText()) > 200000) {
                            JOptionPane.showMessageDialog(this, "Giá phải lớn hơn 0 và nhỏ hơn 200.000");
                            txtPrice.grabFocus();
                            return;
                        } else {
                            break;
                        }
                    }
                    int line1 = tblSP.getRowCount();
                    while (true) {
                        String sql3 = "select * from ProductType where TypeName=? and Size=?";
                        String Loaisp, Size, IDType;
                        psSP = conSP.prepareStatement(sql3);
                        psSP.setString(1, (String) cbNameType.getSelectedItem());
                        psSP.setString(2, (String) cbKichCo.getSelectedItem());
                        rsSP = psSP.executeQuery();
                        if (rsSP.next()) {
                            IDType = rsSP.getString(1);
                            for (int i = 0; i < line1; i++) {
                                if (txtNameproduct.getText().equals(tblSP.getValueAt(i, 1)) && tblSP.getValueAt(i, 2).equals(IDType) && cbKichCo.getSelectedItem().equals(tblSP.getValueAt(i, 4))) {
                                    JOptionPane.showMessageDialog(null, "Sản phẩm Đã tồn tại");
                                    return;
                                }
                            }
                            break;
                        }
                    }

                    String sql = "insert into Product values(?,?,?,?,?,?)";
                    psAdd2 = conSP.prepareStatement(sql);
                    psAdd2.setString(1, txtIDProduct.getText());
                    psAdd2.setString(2, txtNameproduct.getText());
                    psAdd2.setString(3, rsAdd1.getString("IDType"));//cbBox
                    psAdd2.setInt(4, Integer.parseInt(txtPrice.getText()));//kieu int
                    psAdd2.setString(5, cbid.getSelectedItem().toString());
                    psAdd2.setString(6, txtacc.getText());
                    psAdd2.executeUpdate();
                    tblModelSP.getDataVector().removeAllElements();
                    loadDataSP();
                    btnResetActionPerformed(null);
                    JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy nhóm sản phẩm.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_btnAddSPActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        if (cbChonTimKiem.getSelectedItem().equals("Tên")) {
            tblModelSP.getDataVector().removeAllElements();
            try {
                psTK = conSP.prepareStatement("select * from Product inner join ProductType on Product.IDType=ProductType.IDType where ProductName=?");
                psTK.setString(1, (String) cbTen.getSelectedItem());
                rsTK = psTK.executeQuery();
                if (rsTK.next()) {
                    psTK = conSP.prepareStatement("select * from Product inner join ProductType on Product.IDType=ProductType.IDType where ProductName=?");
                    psTK.setString(1, (String) cbTen.getSelectedItem());
                    rsTK = psTK.executeQuery();
                    while (rsTK.next()) {
                        rowTen = new Vector();
                        rowTen.add(rsTK.getString("IDProduct"));
                        rowTen.add(rsTK.getString("ProductName"));
                        rowTen.add(rsTK.getString("IDType"));
                        rowTen.add(rsTK.getString("Price"));
                        rowTen.add(rsTK.getString("Size"));
                        tblModelSP.addRow(rowTen);
                    }
                    tblSP.setModel(tblModelSP);
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm được sản phẩm");
                    loadDataSP();
                }
            } catch (Exception e) {
            }
        } else if (cbChonTimKiem.getSelectedItem().equals("Giá")) {
            while (true) {
                if (txtGiaTu.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(this, "Giá không được để trống");
                    txtGiaTu.grabFocus();
                    return;
                } else if (!txtGiaTu.getText().trim().matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(this, "Giá phải là số");
                    txtGiaTu.grabFocus();
                    return;
                } else if ((Integer.parseInt(txtGiaTu.getText())) <= 0 || Integer.parseInt(txtGiaTu.getText()) > 200000) {
                    JOptionPane.showMessageDialog(this, "Giá phải lớn hơn 0 và nhỏ hơn 200.000");
                    txtGiaTu.grabFocus();
                    return;
                } else {
                    break;
                }
            }
            while (true) {
                if (txtDen.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(this, "Giá không được để trống");
                    txtDen.grabFocus();
                    return;
                } else if (!txtDen.getText().trim().matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(this, "Giá phải là số");
                    txtDen.grabFocus();
                    return;
                } else if ((Integer.parseInt(txtDen.getText())) <= 0 || Integer.parseInt(txtDen.getText()) > 200000) {
                    JOptionPane.showMessageDialog(this, "Giá phải lớn hơn 0 và nhỏ hơn 200.000");
                    txtPrice.grabFocus();
                    return;
                } else if ((Integer.parseInt(txtGiaTu.getText())) >= Integer.parseInt(txtDen.getText())) {
                    JOptionPane.showMessageDialog(this, "Giá phải từ nhỏ đến lớn");
                    txtDen.grabFocus();
                    return;
                } else {
                    break;
                }
            }
            tblModelSP.getDataVector().removeAllElements();
            try {
                psTK = conSP.prepareStatement("select * from Product inner join ProductType on Product.IDType=ProductType.IDType where Price > ? and Price < ?");
                psTK.setInt(1, Integer.parseInt(txtGiaTu.getText().trim()));
                psTK.setInt(2, Integer.parseInt(txtDen.getText().trim()));
                rsTK = psTK.executeQuery();
                if (rsTK.next()) {
                    psTK = conSP.prepareStatement("select * from Product inner join ProductType on Product.IDType=ProductType.IDType where Price > ? and Price < ?");
                    psTK.setInt(1, Integer.parseInt(txtGiaTu.getText().trim()));
                    psTK.setInt(2, Integer.parseInt(txtDen.getText().trim()));
                    rsTK = psTK.executeQuery();
                    while (rsTK.next()) {
                        rowTen = new Vector();
                        rowTen.add(rsTK.getString("IDProduct"));
                        rowTen.add(rsTK.getString("ProductName"));
                        rowTen.add(rsTK.getString("IDType"));
                        rowTen.add(rsTK.getString("Price"));
                        rowTen.add(rsTK.getString("Size"));
                        tblModelSP.addRow(rowTen);
                    }
                    tblSP.setModel(tblModelSP);
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm được sản phẩm");
                    loadDataSP();
                }
            } catch (Exception e) {
            }
        } else if (cbChonTimKiem.getSelectedItem().equals("Nhóm")) {
            tblModelSP.getDataVector().removeAllElements();
            try {
                psTK = conSP.prepareStatement("select * from Product inner join ProductType on Product.IDType=ProductType.IDType where TypeName= ? and Size= ?");
                psTK.setString(1, (String) cbLoaiTK.getSelectedItem());
                psTK.setString(2, (String) cbKichThuoc.getSelectedItem());
                rsTK = psTK.executeQuery();
                if (rsTK.next()) {
                    psTK = conSP.prepareStatement("select * from Product inner join ProductType on Product.IDType=ProductType.IDType where TypeName= ? and Size= ?");
                    psTK.setString(1, (String) cbLoaiTK.getSelectedItem());
                    psTK.setString(2, (String) cbKichThuoc.getSelectedItem());
                    rsTK = psTK.executeQuery();
                    while (rsTK.next()) {
                        rowTen = new Vector();
                        rowTen.add(rsTK.getString("IDProduct"));
                        rowTen.add(rsTK.getString("ProductName"));
                        rowTen.add(rsTK.getString("IDType"));
                        rowTen.add(rsTK.getString("Price"));
                        rowTen.add(rsTK.getString("Size"));
                        tblModelSP.addRow(rowTen);
                    }
                    tblSP.setModel(tblModelSP);
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm được sản phẩm");
                    loadDataSP();
                }
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tblSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPMouseClicked
        // TODO add your handling code here:
        String DepName = null;
        btnDeleteSP.setEnabled(true);
        btnUpdateSP.setEnabled(true);
        btnAddSP.setEnabled(false);
        int line = tblSP.getSelectedRow();
        String ID = (String) tblModelSP.getValueAt(line, 0);
        String Name = (String) tblModelSP.getValueAt(line, 1);
        String Type = (String) tblModelSP.getValueAt(line, 2);
        String Price = (String) tblModelSP.getValueAt(line, 3);
        String Size = (String) tblModelSP.getValueAt(line, 4);
        String idc = (String) tblModelSP.getValueAt(line, 5);
        String acc = (String) tblModelSP.getValueAt(line, 6);
        txtIDProduct.setText(ID);
        txtNameproduct.setText(Name);
        try {
            String sql = "select * from ProductType where IDType=?";
            psSP = conSP.prepareStatement(sql);
            psSP.setString(1, Type);
            rsSP = psSP.executeQuery();
            if (rsSP.next()) {
                DepName = rsSP.getString(2);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        cbNameType.setSelectedItem(DepName);
        //        cbNameType.setSelectedItem(Type);
        txtPrice.setText(Price);
        cbKichCo.setSelectedItem(Size);
        txtIDProduct.setEnabled(false);
        txtacc.setText(acc);
        cbid.setSelectedItem(idc);
    }//GEN-LAST:event_tblSPMouseClicked

    private void btnaddppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddppActionPerformed
    try{
         int line = tblPP.getRowCount();
         while (true) {
                    for (int i = 0; i < line; i++) {
                        if (txtname1.getText().trim().equals(tblPP.getValueAt(i, 0))) {
                           lbname.setText("Tên trùng.");
                            txtname1.grabFocus();
                            return;
                        }
                    }
                    if (txtname1.getText().trim().equals("")) {
                          lbname.setText("ID không được bỏ trống.");
                        txtname1.grabFocus();
                        return;
                    } else {
                        break;
                    }
                }
        
        
      
        String Name = txtname1.getText().replaceAll("\\s+", " ");
                while (true) {
                    if (txtname1.getText().trim().equals("")) {
                        lbname.setText("Tên nhà phân phối không được bỏ trống.");
                        txtname1.grabFocus();
                        return;
                    } else {
                        lbname.setText("");
                        break;
                    }
                }
         while (true) {
                    if (txtphone1.getText().trim().equals("")) {
                        lbphone.setText("SĐT không được bỏ trống.");
                        txtphone1.grabFocus();
                        return;
                    } else if (txtphone1.getText().trim().length() > 11 || txtphone1.getText().trim().length() < 10) {
                        lbphone.setText("SĐT chứa từ 10-11 số.");
                        txtphone1.grabFocus();
                        return;
                    } else if (!txtphone1.getText().trim().matches("0[1-9]{1}\\d{8,9}")) {
                        lbphone.setText("SĐT chưa đúng định dạng.");
                        txtphone1.grabFocus();
                        return;
                    } else {
                        lbphone.setText("");
                        break;
                    }
                }
          while (true) {
                    if (txtmail.getText().trim().equals("")) {
                        lbmail1.setText("Email không được bỏ trống.");
                        txtmail.grabFocus();
                        return;
                    } else if (!txtmail.getText().trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                        lbmail1.setText("Email chưa đúng định dạng.");
                        txtmail.grabFocus();
                        return;
                    } else {
                        lbmail1.setText("");
                        break;
                    }
                }
          

                pspp = conPP.prepareStatement("Insert into Store values(?,?,?,?,?)");
                pspp.setString(1, txtcode.getText().trim());
                pspp.setString(2, txtname1.getText().trim());
                pspp.setString(3, txtphone1.getText().trim());
                pspp.setString(4, txtmail.getText().trim());
                pspp.setString(5, txtadd.getText().trim());
                
                if (pspp.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công.");
                    loadDataPP();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại.");
                } 
                  
                
                
    }catch(Exception ex)
    {
        System.out.println(ex.toString());
    }
   
    }//GEN-LAST:event_btnaddppActionPerformed

    private void tblPPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPPMouseClicked
        DefaultTableModel model = (DefaultTableModel) tblPP.getModel();
        String code = model.getValueAt(tblPP.getSelectedRow(),0).toString();
        String name = model.getValueAt(tblPP.getSelectedRow(), 1).toString();
        String phone = model.getValueAt(tblPP.getSelectedRow(),2).toString();
        String email = model.getValueAt(tblPP.getSelectedRow(), 3).toString();
        String address = model.getValueAt(tblPP.getSelectedRow(), 4).toString();
        
        txtcode.setText(code);
        txtname1.setText(name);
        txtphone1.setText(phone);
        txtmail.setText(email);
        txtadd.setText(address);
                
    }//GEN-LAST:event_tblPPMouseClicked

    private void btndeleteppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteppActionPerformed
  // int click = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn xóa thành viên này?");
            try {
                pspp = conSP.prepareStatement("Delete from Store where code=?");
                pspp.setString(1, tblPP.getValueAt(tblPP.getSelectedRow(),0).toString());
               if(JOptionPane.showConfirmDialog(this, "Xóa nhà phân phối ?","Xác nhận",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                if (pspp.executeUpdate() > 0) {
                    pspp.executeUpdate();
                    loadDataPP();
                    JOptionPane.showMessageDialog(null, "Xóa thành viên thành công.");
                  
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thành viên thất bại.");
                }
               }
               
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
            }
               
    }//GEN-LAST:event_btndeleteppActionPerformed

    private void btnreppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnreppActionPerformed
       txtcode.setText("");
       txtname1.setText("");
       txtphone1.setText("");
       txtadd.setText("");
       txtmail.setText("");
    }//GEN-LAST:event_btnreppActionPerformed

    private void btnupppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupppActionPerformed
      try{
            int line = tblPP.getRowCount();
         while (true) {
                    for (int i = 0; i < line; i++) {
                        if (txtname1.getText().trim().equals(tblPP.getValueAt(i, 0))) {
                           lbname.setText("ID trùng.");
                            txtname1.grabFocus();
                            return;
                        }
                    }
                    if (txtname1.getText().trim().equals("")) {
                          lbname.setText("ID không được bỏ trống.");
                        txtname1.grabFocus();
                        return;
                    } else {
                        break;
                    }
                }
        
        
      
        String Name = txtname1.getText().replaceAll("\\s+", " ");
                while (true) {
                    if (txtname1.getText().trim().equals("")) {
                        lbname.setText("Tên nhà phân phối không được bỏ trống.");
                        txtname1.grabFocus();
                        return;
                    } else {
                        lbname.setText("");
                        break;
                    }
                }
         while (true) {
                    if (txtphone1.getText().trim().equals("")) {
                        lbphone.setText("SĐT không được bỏ trống.");
                        txtphone1.grabFocus();
                        return;
                    } else if (txtphone1.getText().trim().length() > 11 || txtphone1.getText().trim().length() < 10) {
                        lbphone.setText("SĐT chứa từ 10-11 số.");
                        txtphone1.grabFocus();
                        return;
                    } else if (!txtphone1.getText().trim().matches("0[1-9]{1}\\d{8,9}")) {
                        lbphone.setText("SĐT chưa đúng định dạng.");
                        txtphone1.grabFocus();
                        return;
                    } else {
                        lbphone.setText("");
                        break;
                    }
                }
          while (true) {
                    if (txtmail.getText().trim().equals("")) {
                        lbmail1.setText("Email không được bỏ trống.");
                        txtmail.grabFocus();
                        return;
                    } else if (!txtmail.getText().trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                        lbmail1.setText("Email chưa đúng định dạng.");
                        txtmail.grabFocus();
                        return;
                    } else {
                        lbmail1.setText("");
                        break;
                    }
                }
          

          pspp = conPP.prepareStatement("Update Store set code=?,phone=?,name=?,email=?");
          pspp.setString(1, txtcode.getText());
          pspp.setString(2, txtname1.getText());
          pspp.setString(3, txtphone1.getText());
          pspp.setString(4, txtmail.getText());
          pspp.setString(5, txtadd.getText());
          int check = pspp.executeUpdate();
          if(check > 0)
          {
              JOptionPane.showMessageDialog(this, "Chỉnh sửa thành công");
             loadDataPP();
          }
          else{
           JOptionPane.showMessageDialog(this, "Chỉnh sửa thất bại");
          }
      
      }
      catch(Exception ex)
      {
          System.out.println(ex.toString());
      }
        
        
        
    }//GEN-LAST:event_btnupppActionPerformed

    private void cbChonTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbChonTimKiemActionPerformed
        PanelTimKiem.setVisible(true);
        if (cbChonTimKiem.getSelectedIndex() == 0) {
            cbTen.setVisible(false);
            txtGiaTu.setVisible(false);
            txtDen.setVisible(false);
            cbLoaiTK.setVisible(false);
            cbKichThuoc.setVisible(false);
            btnTimKiem.setVisible(false);
            lbDen.setVisible(false);
            lbGiaTu.setVisible(false);
            lbLoai.setVisible(false);
            lbNhom.setVisible(false);
            lbVND1.setVisible(false);
            lbVND.setVisible(false);

        } else if (cbChonTimKiem.getSelectedItem().equals("Tên")) {
            cbTen.setVisible(true);
            txtGiaTu.setVisible(false);
            txtDen.setVisible(false);
            cbLoaiTK.setVisible(false);
            cbKichThuoc.setVisible(false);
            btnTimKiem.setVisible(true);
            lbDen.setVisible(false);
            lbGiaTu.setVisible(false);
            lbLoai.setVisible(false);
            lbNhom.setVisible(false);
            lbVND1.setVisible(false);
            lbVND.setVisible(false);

            cbTen.removeAllItems();
            try {
                String url = "Select DISTINCT ProductName from Product";
                psTen = conSP.prepareStatement(url);
                rsTen = psTen.executeQuery();
                vecTen = new Vector();
                while (rsTen.next()) {
                    vecTen.add(rsTen.getString("ProductName"));
                }
                JTextField text = (JTextField) cbTen.getEditor().getEditorComponent();
                text.setText("");
                text.addKeyListener(new ComboListener(cbTen, vecTen));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
            }
        } else if (cbChonTimKiem.getSelectedItem().equals("Giá")) {
            txtGiaTu.setVisible(true);
            txtDen.setVisible(true);
            cbTen.setVisible(false);
            cbLoaiTK.setVisible(false);
            cbKichThuoc.setVisible(false);
            cbTen.setVisible(false);
            btnTimKiem.setVisible(true);

            lbDen.setVisible(true);
            lbGiaTu.setVisible(true);
            lbLoai.setVisible(false);
            lbNhom.setVisible(false);
            lbVND1.setVisible(true);
            lbVND.setVisible(true);
        } else if (cbChonTimKiem.getSelectedItem().equals("Nhóm")) {
            txtGiaTu.setVisible(false);
            txtDen.setVisible(false);
            cbTen.setVisible(false);
            cbLoaiTK.setVisible(true);
            cbKichThuoc.setVisible(true);
            btnTimKiem.setVisible(true);
            lbDen.setVisible(false);
            lbGiaTu.setVisible(false);
            lbLoai.setVisible(true);
            lbNhom.setVisible(true);
            lbVND1.setVisible(false);
            lbVND.setVisible(false);

            cbLoaiTK.removeAllItems();
            try {
                String sql = "select Distinct TypeName from ProductType";
                psSP = conSP.prepareStatement(sql);
                rsSP = psSP.executeQuery();
                while (rsSP.next()) {
                    cbLoaiTK.addItem(rsSP.getString(1));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_cbChonTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelTimKiem;
    private javax.swing.JButton btnAddSP;
    private javax.swing.JButton btnDeleteSP;
    private javax.swing.JButton btnResetLoaiSP;
    private javax.swing.JButton btnResetSP;
    private javax.swing.JButton btnSuaLoaiSP;
    private javax.swing.JButton btnThemLoaiSP;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnUpdateSP;
    private javax.swing.JButton btnXoaLoaiSP;
    private javax.swing.JButton btnaddpp;
    private javax.swing.JButton btndeletepp;
    private javax.swing.JButton btnrepp;
    private javax.swing.JButton btnuppp;
    private javax.swing.JComboBox cbChonTimKiem;
    private javax.swing.JComboBox cbKichCo;
    private javax.swing.JComboBox cbKichThuoc;
    private javax.swing.JComboBox cbLoaiTK;
    private javax.swing.JComboBox cbNameType;
    private javax.swing.JComboBox cbSizeType;
    private javax.swing.JComboBox cbTen;
    private javax.swing.JComboBox<String> cbid;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel jpanel;
    private javax.swing.JLabel lbDen;
    private javax.swing.JLabel lbGiaTu;
    private javax.swing.JLabel lbLoai;
    private javax.swing.JLabel lbNhom;
    private javax.swing.JLabel lbVND;
    private javax.swing.JLabel lbVND1;
    private javax.swing.JLabel lbadd;
    private javax.swing.JLabel lbcode;
    private javax.swing.JLabel lbdgo;
    private javax.swing.JLabel lbmail1;
    private javax.swing.JLabel lbname;
    private javax.swing.JLabel lbphone;
    private javax.swing.JTable tblLoaiSP;
    private javax.swing.JTable tblPP;
    private javax.swing.JTable tblSP;
    private javax.swing.JTextField txt1;
    private javax.swing.JTextField txt2;
    private javax.swing.JTextField txtDen;
    private javax.swing.JTextField txtGiaTu;
    private javax.swing.JTextField txtIDProduct;
    private javax.swing.JTextField txtIDProductType;
    private javax.swing.JTextField txtNameType;
    private javax.swing.JTextField txtNameproduct;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtacc;
    private javax.swing.JTextField txtadd;
    private javax.swing.JTextField txtcode;
    private javax.swing.JTextField txtmail;
    private javax.swing.JTextField txtname1;
    private javax.swing.JTextField txtphone1;
    // End of variables declaration//GEN-END:variables

    private void btnResetActionPerformed(Object object) {
        txtIDProduct.setText("");
        txtNameproduct.setText("");
        txtPrice.setText("");
        cbNameType.setSelectedIndex(0);//re set combobox
        txtIDProduct.setEnabled(true);
        btnDeleteSP.setEnabled(false);
        btnUpdateSP.setEnabled(false);
        btnAddSP.setEnabled(true);
    }
}
