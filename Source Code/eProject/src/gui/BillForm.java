/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 *
 */
public class BillForm extends javax.swing.JFrame {

    /**
     * Creates new form BillForm
     */
    ResultSet rs, rsEmp, rsIDOrder;
    Vector vec, rowHis;
    PreparedStatement ps;
    DefaultComboBoxModel cbModel;
    DefaultTableModel tblModel, tblModelHis;
    server.Connection1 db = new server.Connection1();
    Connection con = db.getCon();
    SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    SimpleDateFormat ftnow = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat ftNgay = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#,###");

    public BillForm(String EmpName) {
        initComponents();
       
        txtIDBill.setEnabled(false);
        btnPrint.setEnabled(true);
        btnLogout.setSize(20, 20);
        new SetImage().setImageButton(btnLogout, "image//log.png");
        btnchanfe.setSize(20, 20);
        new SetImage().setImageButton(btnchanfe, "image//change1.png");
        txtName1.setText(EmpName);
        spQuantity.setValue(1);
        setText(false); 
        btnPrint.setSize(40, 40);
        tblModel = new DefaultTableModel();
        tblModel.addColumn("Mã");
        tblModel.addColumn("Tên sản phẩm");
        tblModel.addColumn("Nhóm");
        tblModel.addColumn("Đơn vị");
        tblModel.addColumn("Đơn giá (VNĐ)");
        tblModel.addColumn("Số lượng");
        tblModel.addColumn("Thành tiền (VNĐ)");
        tblBill.setModel(tblModel);
        try {
            String url = "Select DISTINCT ProductName from Product Join ProductType on Product.IDType=ProductType.IDType";
            ps = con.prepareStatement(url);
            rs = ps.executeQuery();
            vec = new Vector();
            while (rs.next()) {
                vec.add(rs.getString("ProductName"));
            }
            JTextField text = (JTextField) cbProduct.getEditor().getEditorComponent();
            text.setText("");
            text.addKeyListener(new ComboListener(cbProduct, vec));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến máy chủ");
        }
        ReloadCombobox();
        autoId();
        clock();
        PanelOnOff(false);
    }
    
    public void autoId()
    {
        try{
            Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select IDOrder from [Order] order by IDOrder desc");
        if(rs.next())
        {
            String test = rs.getString("IDOrder");
            int co = test.length();
            String txt = test.substring(0,2);
            String num = test.substring(2,co);
            int n = Integer.parseInt(num);
            n++;
            String snum = Integer.toString(n);
            String ftxt = txt + snum;
            txtIDBill.setText(ftxt);
        }
        else{
            txtIDBill.setText("HD1000");
            
        }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
    
    }

    private BillForm() {
    }

    public void intomoney() {
        int price, totalprice = 0;
        int count = tblBill.getRowCount();
        for (int i = 0; i < count; i++) {
            price = Integer.parseInt((String) tblBill.getValueAt(i, 6));
            totalprice += price;
        }
        txtTotal.setText(formatter.format(totalprice)); //set giá trị cho txtOrder bằng totalprice
    }

    public void LoadTblFromDB(){
        try {
            String url = "Select * from Product Join ProductType on Product.IDType=ProductType.IDType where Product.ProductName=? and ProductType.Size=? ";
            ps = con.prepareStatement(url);
            ps.setString(1, (String) cbProduct.getSelectedItem());
            ps.setString(2, (String) cbSize.getSelectedItem());
            rs = ps.executeQuery();
            int price, quantity, into,test,left;
            if (rs.next()) {
                vec = new Vector();
                price = Integer.parseInt(rs.getString("Price"));
                quantity = Integer.parseInt(spQuantity.getValue().toString());
                into = price * quantity;
                test = Integer.parseInt(rs.getString("acc"));
                left = test - quantity;
                if(left == 0)
                {
                    JOptionPane.showConfirmDialog(this, "Sản phẩm đã hết");
                }
                else{
                txttes.setText(String.valueOf(left) );
                vec.add(rs.getString("IDProduct"));
                vec.add(rs.getString("ProductName"));
                vec.add(rs.getString("TypeName"));
                vec.add(rs.getString("Size"));
                vec.add(rs.getString("Price"));           
                vec.add(spQuantity.getValue());
                vec.add(String.valueOf(into));
                vec.add(String.valueOf(left));
                tblModel.addRow(vec);
               try{
               String url1 = "update Product set acc =? where ProductName=?";
               ps = con.prepareStatement(url1);
               ps.setString(1, txttes.getText());
               ps.setString(2, cbProduct.getSelectedItem().toString());
              
               rs = ps.executeQuery();
                }
               catch(Exception ex)
               {
                   
               }
                }
                
                
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi:: Không tìm thấy sản phẩm");
                cbProduct.grabFocus();
            }
            tblBill.setModel(tblModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Không thể kết nối đến máy chủ");
        }
    }
    
    public void LoadTblFromDB1(){
        try {
            String url = "Select * from Product Join ProductType on Product.IDType=ProductType.IDType where Product.ProductName=? and ProductType.Size=?";
            ps = con.prepareStatement(url);
            ps.setString(1, (String) cbProduct.getSelectedItem());
            ps.setString(2, (String) cbSize.getSelectedItem());
            rs = ps.executeQuery();
            int amount,quanlity;
            if(rs.next())
            {
            txttes.setText(rs.getString("acc"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Không thể kết nối đến máy chủ");
        }
    }
    
    
    
    
    
    
    
    

    public void setText(boolean b) {
        txtGuest.setEnabled(b);
    }
    public void clock() {
        Thread clock = new Thread() {
            public void run() {
                try {
                    while (true) {
                        Date t = new Date();
                        lbTime.setText(ft.format(t));
                        sleep(1000);
                    }
                } catch (Exception e) {
                }
            }
        };
        clock.start();    
    }


    public void UpdatetxtDis1() {
        int Dis;
        NumberFormat formatter = new DecimalFormat("#,###");
        //tính Discount
        String Order = txtTotal.getText().replaceAll(",", "");
        Dis = (Integer.parseInt(txtDis1.getText()) * Integer.parseInt(Order)) / 100;
        txtDis2.setText(formatter.format(Dis));
        //tính total
        int total = Integer.parseInt(Order) - Dis;
        txtPay.setText(formatter.format(total));
    }

    public void ReloadCombobox() {
        cbCTKM.removeAllItems();
        cbCTKM.addItem("Không có");
        cbCTKM.addItem("Khách hàng VIP");
        try {
            Date now = new Date();
            ps = con.prepareStatement("select * from Promotions where StartPromo <= ? and EndPromo >= ?");
            ps.setString(1, ftnow.format(now));
            ps.setString(2, ftnow.format(now));
            rs = ps.executeQuery();
            while (rs.next()) {
                cbCTKM.addItem(rs.getString(2));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến máy chủ");
        }
    }

    public void PanelOnOff(boolean b) {
        lbNhap.setVisible(b);
        txtID.setVisible(b);
        lbIDError.setVisible(b);
        pnInformation.setVisible(b);
    }

    public void PressPrintandSave(String Name) {
        int line = tblBill.getRowCount();
        //Thêm từng value vào trong database
        try {
            ps = con.prepareStatement("Insert into [Order] values(?,convert(varchar(20),getdate(),103),convert(varchar(20),getdate(),108),?)");
            ps.setString(1, txtIDBill.getText());
            ps.setString(2, Name);
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến máy chủ");
        }
        for (int i = 0; i < line; i++) {
            String IDProduct = (String) tblModel.getValueAt(i, 0);
            String quantity = String.valueOf(tblModel.getValueAt(i, 5));
            try {
                String in = "Insert into OrderDetails values(?,?,?,?,?)";
                ps = con.prepareStatement(in);
                ps.setString(1, txtIDBill.getText());
                ps.setString(2, IDProduct);
                if (cbCTKM.getSelectedItem().equals("Khách hàng VIP")) {
                    ps.setString(3, lbIDCus.getText());
                } else {
                    ps.setString(3, "Khách vãng lai");
                }
                ps.setInt(4, Integer.parseInt(quantity));
                ps.setString(5, (String) cbCTKM.getSelectedItem());
                ps.executeUpdate();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
            }
        }
        //kiểm tra số tiền ngày hôm nay và set lại giá trị
        String pay = txtPay.getText().replaceAll(",", "");
        try {
            ps = con.prepareStatement("SELECT * from Revenue where Date=convert(varchar(20),getdate(),103)");
            rs = ps.executeQuery();
            if (rs.next()) {
                int money1 = Integer.parseInt(rs.getString("Money"));
                int money2 = money1 + Integer.parseInt(pay);
                ps = con.prepareStatement("update Revenue set Money=" + money2 + " where Date=convert(varchar(20),getdate(),103)");
                ps.executeUpdate();
            } else {
                ps = con.prepareStatement("insert into Revenue values(convert(varchar(20),getdate(),103)," + pay + ")");
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi 101:: Không thể kết nối đến máy chủ");
        }
        //Viết vào file txt
        int guest = Integer.parseInt(txtGuest.getText());
        try {
            Date now = new Date();
            Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("History//" + txtIDBill.getText().trim() + ".txt"), "UTF8"));
            bw.write("\t\tCONG TI TRACH NHIEM HUU HAN \r\n\r\n");
            bw.write("\t\t\tHÓA ĐƠN BÁN HÀNG\r\n\r\n");
            bw.write("Mã hóa đơn: " + txtIDBill.getText() + "\r\n");
            bw.write("Thời gian: " + ft.format(now) + "\r\n");
            bw.write("NHÂN VIÊN: " + txtName1.getText() + "\r\n");
            bw.write("------------------------------------------------------------\r\n");
            bw.write("Mã\tĐơn vị\tSố lượng\tĐơn giá\tThành tiền\r\n");
            bw.write("-----------------------------------------------------------\r\n");
            //Ghi sản phẩm
            int quantotal = 0;
            for (int i = 0; i < line; i++) {
                String id = (String) tblModel.getValueAt(i, 0);
               // String name = (String) tblModel.getValueAt(i, 1);
                String size = (String) tblModel.getValueAt(i, 3);
                String price = String.valueOf(tblModel.getValueAt(i, 4));
                String quantity = String.valueOf(tblModel.getValueAt(i, 5));
                String intomoney = String.valueOf(tblModel.getValueAt(i, 6));
             //   bw.write((i + 1) + ". " + name + "\r\n");
                bw.write(id + "\t" + size + "\t\t" + quantity + "\t\t" + price + "\t" + intomoney + "\r\n\r\n");
                quantotal += Integer.parseInt(quantity);
            }
            bw.write("------------------------------------------------------------\r\n");
            bw.write("Tổng cộng:\t" + "\t\t" + txtTotal.getText() + " VNĐ\r\n");
            bw.write("Chiết khấu:\t" + txtDis1.getText() + "%\t\t-" + txtDis2.getText() + " VNĐ\r\n");
            bw.write("--------------------------------------------\r\n");
            bw.write("Thành tiền:\t\t\t" + txtPay.getText() + " VNĐ\r\n");
            bw.write("--------------------------------------------\r\n");
            bw.write("Tiền khách đưa:\t\t\t" + formatter.format(guest) + " VNĐ\r\n");
            bw.write("Tiền trả lại:\t\t\t" + txtRepay.getText() + " VNĐ\r\n");
            bw.write("------------------------------------------------------------\r\n");
            bw.write("Chương trình khuyến mãi: ");
            if (cbCTKM.getSelectedItem().equals("Không có")) {
                bw.write("Không có.\r\n");
            } else if (cbCTKM.getSelectedItem().equals("Khách hàng VIP")) {
                bw.write("Thành viên quán.\r\n");
                bw.write("-----Thông tin thành viên-----\r\n");
                bw.write("Mã thẻ: " + lbIDCus.getText() + "\r\n");
                bw.write("Tên thành viên: " + lbNameCus.getText() + "\r\n");
                bw.write("Ngày đăng ký: " + lbDateCus.getText() + "\r\n");
                bw.write("Số lượng: " + lbQuantityCus.getText() + " ly.\r\n");
                bw.write("Số điểm: " + quantotal );
                bw.write("Chiết khấu: " + lbDisCus.getText() + "\r\n");
            } else {
                bw.write((String) cbCTKM.getSelectedItem() + "\r\n");
            }
            bw.write("------------------------------------------------------------\r\n");
            bw.write("---------------------CÁM ƠN QUÝ KHÁCH!----------------------");
            bw.close();
            //update số ly và chiết khấu vào bảng customer
            int quannew = Integer.parseInt(lbQuantityCus.getText()) + quantotal;
            if (quannew < 10) {
                try {
                    ps = con.prepareStatement("Update Customer set Quantity=?,Discount=? where IDCus=?");
                    ps.setInt(1, quannew);
                    ps.setInt(2, 0);
                    ps.setString(3, lbIDCus.getText());
                    ps.executeUpdate();
                } catch (Exception e) {
                }
            } else if (quannew >= 10 && quannew < 20) {
                try {
                    ps = con.prepareStatement("Update Customer set Quantity=?,Discount=? where IDCus=?");
                    ps.setInt(1, quannew);
                    ps.setInt(2, 5);
                    ps.setString(3, lbIDCus.getText());
                    ps.executeUpdate();
                } catch (Exception e) {
                }
            } else if (quannew >= 20 && quannew < 30) {
                try {
                    ps = con.prepareStatement("Update Customer set Quantity=?,Discount=? where IDCus=?");
                    ps.setInt(1, quannew);
                    ps.setInt(2, 10);
                    ps.setString(3, lbIDCus.getText());
                    ps.executeUpdate();
                } catch (Exception e) {
                }
            } else if (quannew >= 30 && quannew < 40) {
                try {
                    ps = con.prepareStatement("Update Customer set Quantity=?,Discount=? where IDCus=?");
                    ps.setInt(1, quannew);
                    ps.setInt(2, 15);
                    ps.setString(3, lbIDCus.getText());
                    ps.executeUpdate();
                } catch (Exception e) {
                }
            } else if (quannew >= 40 && quannew < 50) {
                try {
                    ps = con.prepareStatement("Update Customer set Quantity=?,Discount=? where IDCus=?");
                    ps.setInt(1, quannew);
                    ps.setInt(2, 20);
                    ps.setString(3, lbIDCus.getText());
                    ps.executeUpdate();
                } catch (Exception e) {
                }
            } else if (quannew >= 50) {
                try {
                    ps = con.prepareStatement("Update Customer set Quantity=?,Discount=? where IDCus=?");
                    ps.setInt(1, quannew);
                    ps.setInt(2, 25);
                    ps.setString(3, lbIDCus.getText());
                    ps.executeUpdate();
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
        //Mở file txt
        Runtime run = Runtime.getRuntime();
        try {
            run.exec("notepad History//" + txtIDBill.getText().trim() + ".txt");
        } catch (IOException e) {
        }

        // set lại bảng, combobox và textbox
        tblModel.getDataVector().removeAllElements();
        tblBill.revalidate();
        setText(false);
        txtIDBill.setEnabled(false);
        cbCTKM.setSelectedIndex(0);
        txtPay.setText("0");
        txtTotal.setText("0");
        txtGuest.setText("0");
        txtRepay.setText("0");
        ResetPnInfor();
        txtID.setText("");
        lbIDError.setText("");
        btnPrint.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon("image//white1.png");
            public void paintComponent(Graphics g){
                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        jPanel3 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon("image//white1.png");
            public void paintComponent(Graphics g){
                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        lbNhap = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        lbIDError = new javax.swing.JLabel();
        lbNgayKM = new javax.swing.JLabel();
        pnInformation = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbIDCus = new javax.swing.JLabel();
        lbNameCus = new javax.swing.JLabel();
        lbQuantityCus = new javax.swing.JLabel();
        lbDisCus = new javax.swing.JLabel();
        lbDateCus = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtName1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBill = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        lbTime = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        cbProduct = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        spQuantity = new javax.swing.JSpinner();
        cbSize = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbCTKM = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        txtDis1 = new javax.swing.JTextField();
        txtGuest = new javax.swing.JTextField();
        txtPay = new javax.swing.JTextField();
        txtDis2 = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtIDBill = new javax.swing.JTextField();
        txtRepay = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();
        lberror = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        btnchanfe = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txttes = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();

        jMenuItem4.setText("jMenuItem4");

        jMenuItem5.setText("jMenuItem5");

        jMenuItem1.setText("jMenuItem1");

        jMenu4.setText("jMenu4");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý bán hàng");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lbNhap.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbNhap.setText("Nhập mã thẻ:");

        txtID.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtIDCaretUpdate(evt);
            }
        });

        lbIDError.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbIDError.setForeground(new java.awt.Color(255, 0, 0));
        lbIDError.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbIDError.setToolTipText("");

        lbNgayKM.setForeground(new java.awt.Color(255, 0, 0));
        lbNgayKM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 51));
        jLabel13.setText("Thông tin khách hàng:");

        jLabel14.setText("Mã thẻ:");

        jLabel15.setText("Họ và tên:");

        jLabel16.setText("Số điểm");

        jLabel17.setText("Được giảm:");

        lbIDCus.setForeground(new java.awt.Color(204, 0, 51));
        lbIDCus.setText("...");

        lbNameCus.setForeground(new java.awt.Color(204, 0, 51));
        lbNameCus.setText("...");

        lbQuantityCus.setForeground(new java.awt.Color(204, 0, 51));
        lbQuantityCus.setText("...");

        lbDisCus.setForeground(new java.awt.Color(204, 0, 51));
        lbDisCus.setText("...");

        lbDateCus.setForeground(new java.awt.Color(204, 0, 51));
        lbDateCus.setText("...");

        jLabel19.setText("Ngày đăng ký:");

        javax.swing.GroupLayout pnInformationLayout = new javax.swing.GroupLayout(pnInformation);
        pnInformation.setLayout(pnInformationLayout);
        pnInformationLayout.setHorizontalGroup(
            pnInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(pnInformationLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbIDCus))
                    .addGroup(pnInformationLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbNameCus))
                    .addGroup(pnInformationLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbQuantityCus))
                    .addGroup(pnInformationLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbDisCus))
                    .addGroup(pnInformationLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbDateCus)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnInformationLayout.setVerticalGroup(
            pnInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lbIDCus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lbNameCus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbDateCus)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(pnInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbQuantityCus)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lbDisCus))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lbNhap)
                .addGap(0, 95, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lbIDError, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbNgayKM, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                    .addComponent(txtID)
                    .addComponent(pnInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbNhap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lbNgayKM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbIDError, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtName1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtName1.setForeground(new java.awt.Color(255, 0, 0));
        txtName1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtName1.setBorder(null);

        tblBill.setBackground(new java.awt.Color(0, 255, 0));
        tblBill.setSelectionBackground(new java.awt.Color(255, 0, 0));
        tblBill.getTableHeader().setReorderingAllowed(false);
        tblBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBillMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBill);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Bảng thanh toán");

        lbTime.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbTime.setForeground(new java.awt.Color(255, 0, 0));
        lbTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnAdd.setBackground(new java.awt.Color(255, 51, 51));
        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDel.setBackground(new java.awt.Color(255, 51, 51));
        btnDel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDel.setForeground(new java.awt.Color(255, 255, 255));
        btnDel.setText("Xóa");
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        cbProduct.setEditable(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Sản phẩm:");

        spQuantity.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        spQuantity.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cbSize.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bịch", "Thùng", "Gói", "Chai ", "Lon", "Cái" }));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Đơn vị");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Số lượng:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Khuyến mãi:");

        cbCTKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCTKMActionPerformed(evt);
            }
        });
        cbCTKM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbCTKMKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel10.setText("%");

        txtDis1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDis1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDis1.setText("0");
        txtDis1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDis1.setEnabled(false);

        txtGuest.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtGuest.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtGuest.setText("0");
        txtGuest.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtGuestCaretUpdate(evt);
            }
        });

        txtPay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPay.setText("0");
        txtPay.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPay.setEnabled(false);

        txtDis2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDis2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDis2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDis2.setEnabled(false);

        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("0");
        txtTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotal.setEnabled(false);
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Tổng cộng:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Chiết khấu:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Thành tiền:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Tiền khách đưa:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Mã hóa đơn:");

        txtIDBill.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtRepay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRepay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtRepay.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtRepay.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Tiền trả lại:");

        btnPrint.setBackground(new java.awt.Color(255, 0, 51));
        btnPrint.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setText("Xuất hóa đơn");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnLogout.setBackground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Đăng xuất");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnchanfe.setBackground(new java.awt.Color(255, 255, 255));
        btnchanfe.setText("Đổi mật khẩu");
        btnchanfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnchanfeActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Thông tin cá nhân");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txttes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txttes.setForeground(new java.awt.Color(255, 0, 0));
        txttes.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txttes.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txttesCaretUpdate(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Số lượng trong kho");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jLabel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txttes))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(34, 34, 34)
                                                .addComponent(spQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(67, 67, 67)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbCTKM, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(34, 34, 34)
                                        .addComponent(cbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(238, 238, 238)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(lberror, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(16, 16, 16))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnPrint)
                                        .addGap(49, 49, 49))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDis1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10))
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel6))
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtGuest)
                                    .addComponent(txtPay)
                                    .addComponent(txtDis2)
                                    .addComponent(txtTotal)
                                    .addComponent(txtIDBill, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtRepay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(lbTime, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(txtName1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jButton1))
                            .addGap(210, 210, 210)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnchanfe, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(38, 38, 38)
                            .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnchanfe, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbTime, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbCTKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txttes, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10)
                            .addComponent(txtDis1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDis2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGuest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtRepay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtIDBill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lberror, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        int line = tblBill.getRowCount();
        for (int i = 0; i < line; i++) {
            if (tblBill.getValueAt(i, 1).equals(cbProduct.getSelectedItem()) && tblBill.getValueAt(i, 3).equals(cbSize.getSelectedItem())) {
                int quanCu = (int) tblBill.getValueAt(i, 5);
                int quanMoi = (int) spQuantity.getValue();
                int quanTotal = quanCu + quanMoi;
                spQuantity.setValue(quanTotal);
                tblModel.removeRow(i);
                break;
            }
        }
        LoadTblFromDB();
        cbProduct.setSelectedIndex(-1);
        spQuantity.setValue(1);
        cbSize.setSelectedIndex(0);
        intomoney();
        UpdatetxtDis1();
        if (tblBill.getRowCount() > 0) {
            setText(true);
        } else {
            setText(false);
        }
        btnPrint.setEnabled(false);
        txtIDBill.setEnabled(false);
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtGuestCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtGuestCaretUpdate
        int Repay;
        
        
        while (true) {
            if (txtGuest.getText().trim().equals("")) {
                lberror.setText("Khách hàng chưa đưa tiền.");
                txtRepay.setText("0");
                btnPrint.setEnabled(false);
                txtIDBill.setEnabled(false);
                return;
            } else if (!txtGuest.getText().trim().matches("\\d+")) {
                lberror.setText("Tiền có dạng số.");
                txtRepay.setText("0");
                btnPrint.setEnabled(false);
                txtIDBill.setEnabled(false);
                return;
            } else {
                lberror.setText("");
                btnPrint.setEnabled(false);
                txtIDBill.setEnabled(false);
                break;
            }
        }
        String total = txtPay.getText().replaceAll(",", "");
        Repay = Integer.parseInt(txtGuest.getText()) - Integer.parseInt(total);
        txtRepay.setText(formatter.format(Repay));
        if (Repay < 0) {
            lberror.setText("Khách hàng chưa đưa đủ tiền.");
            btnPrint.setEnabled(false);
            txtIDBill.setEnabled(false);
            txtRepay.setText("0");
        } else if (Integer.parseInt(txtGuest.getText()) == 0) {
            btnPrint.setEnabled(false);
            txtIDBill.setEnabled(false);
            txtRepay.setText("0");
        } else {
            lberror.setText("");
            btnPrint.setEnabled(true);
            txtIDBill.setEnabled(false);
        }
    }//GEN-LAST:event_txtGuestCaretUpdate

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        int line = tblBill.getSelectedRow();
        tblModel.removeRow(line);
        intomoney();
        UpdatetxtDis1();
        if (tblBill.getRowCount() > 0) {
            setText(true);
        } else {
            setText(false);
        }
        txtPay.setText("0");
        txtTotal.setText("0");
        txtGuest.setText("0");
        txtRepay.setText("0");
        btnPrint.setEnabled(false);
        txtIDBill.setEnabled(false);
    }//GEN-LAST:event_btnDelActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        txttes.setText("");
        while (true) {
            if (txtGuest.getText().trim().equals("")) {
                lberror.setText("Khách hàng chưa đưa tiền.");
                txtRepay.setText("0");
                btnPrint.setEnabled(false);
                txtIDBill.setEnabled(false);
                return;
            } else if (!txtGuest.getText().trim().matches("\\d+")) {
                lberror.setText("Tiền có dạng số.");
                txtRepay.setText("0");
                btnPrint.setEnabled(false);
                txtIDBill.setEnabled(false);
                return;
            } else {
                lberror.setText("");
                btnPrint.setEnabled(false);
                txtIDBill.setEnabled(false);
                break;
            }
        }  
        
        if (cbCTKM.getSelectedItem().equals("Khách hàng VIP")) {
            while (true) {
                if (txtID.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Mã thẻ VIP không được để trống!");
                    txtID.grabFocus();
                    return;
                } else if(!txtID.getText().trim().equals("") && !lbIDError.getText().equals("Thành công.")) {
                    JOptionPane.showMessageDialog(null, "Mã thẻ VIP chưa đúng, vui lòng nhập lại!");
                    txtID.grabFocus();
                    return;
                } else{
                    break;
                }
            }
        }
        try {
            ps = con.prepareStatement("select IDOrder from [Order] where IDOrder=?");
            ps.setString(1, txtIDBill.getText().trim());
            rsIDOrder = ps.executeQuery();
            if (!rsIDOrder.next()) {
                ps = con.prepareStatement("Select * from Employee where NameEmp=?");
                ps.setString(1, txtName1.getText());
                rsEmp = ps.executeQuery();
                 
                if (rsEmp.next()) {
                   PressPrintandSave(rsEmp.getString(1));
                } else{
                    JOptionPane.showMessageDialog(null, "Tên nhân viên không tồn tại.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Mã hóa đơn đã tồn tại, vui lòng chọn mã mới.");
                txtIDBill.grabFocus();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến máy chủ");
        }
         try{
            Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select IDOrder from [Order] order by IDOrder desc");
        if(rs.next())
        {
            String test = rs.getString("IDOrder");
            int co = test.length();
            String txt = test.substring(0,2);
            String num = test.substring(2,co);
            int n = Integer.parseInt(num);
            n++;
            String snum = Integer.toString(n);
            String ftxt = txt + snum;
            txtIDBill.setText(ftxt);
        }
        else{
            txtIDBill.setText("HD1000");
            
        }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
    
    }//GEN-LAST:event_btnPrintActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void txtIDCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtIDCaretUpdate
        btnPrint.setEnabled(false);
        txtIDBill.setEnabled(false);
        if (txtID.getText().trim().equals("")) {
            txtDis1.setText("0");
            lbIDError.setText("Vui lòng nhập mã thẻ.");
            lbIDError.setForeground(Color.red);
            ResetPnInfor();
        } else {
            while (true) {
                if (!txtID.getText().trim().matches("\\d+")) {
                    lbIDError.setText("Mã thẻ dạng số.");
                    lbIDError.setForeground(Color.red);
                    txtDis1.setText("0");
                    ResetPnInfor();
                    return;
                } else {
                    break;
                }
            }
            try {
                ps = con.prepareStatement("Select * from Customer where IDCus=?");
                ps.setString(1, txtID.getText());
                rs = ps.executeQuery();
                if (!rs.next()) {
                    lbIDError.setText("Mã thẻ không tồn tại!");
                    lbIDError.setForeground(Color.red);
                    txtDis1.setText("0");
                    ResetPnInfor();
                } else {
                    lbIDError.setText("Thành công.");
                    lbIDError.setForeground(Color.BLUE);
                    lbIDCus.setText(rs.getString(1));
                    lbNameCus.setText(rs.getString(3));
                    lbDateCus.setText(rs.getString(4));
                    lbQuantityCus.setText(rs.getString(7));
                    lbDisCus.setText(rs.getString(8) + "%");
                    txtDis1.setText(rs.getString(8));
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến máy chủ");
            }
        }
        UpdatetxtDis1();
    }//GEN-LAST:event_txtIDCaretUpdate

    private void cbCTKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCTKMActionPerformed
        btnPrint.setEnabled(false);
        txtIDBill.setEnabled(false);
        if (cbCTKM.getSelectedItem().equals("Không có")) {
            txtDis1.setText("0");
            lbNgayKM.setText("");
            PanelOnOff(false);
            UpdatetxtDis1();
            ResetPnInfor();
            txtID.setText("");
            lbIDError.setText("");
        } else if (cbCTKM.getSelectedItem().equals("Khách hàng VIP")) {
            txtDis1.setText("0");
            lbNgayKM.setText("");
            UpdatetxtDis1();
            PanelOnOff(true);
        } else {
            PanelOnOff(false);
            ResetPnInfor();
            txtID.setText("");
            lbIDError.setText("");
            try {
                ps = con.prepareStatement("Select * from Promotions where NamePromo=?");
                ps.setString(1, (String) cbCTKM.getSelectedItem());
                rs = ps.executeQuery();
                if (rs.next()) {
                    Date start = ftnow.parse(rs.getString(4));
                    Date end = ftnow.parse(rs.getString(5));
                    txtDis1.setText(rs.getString(3));
                    lbNgayKM.setText(ftNgay.format(start) + " - " + ftNgay.format(end));
                    UpdatetxtDis1();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, " Không thể kết nối đến máy chủ");
            } catch (ParseException ex) {
                Logger.getLogger(BillForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cbCTKMActionPerformed

    private void tblBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillMouseClicked

    }//GEN-LAST:event_tblBillMouseClicked

    
    
    
    
    
    
    
    
    private void cbCTKMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbCTKMKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_F5){
            ReloadCombobox();
        }
    }//GEN-LAST:event_cbCTKMKeyPressed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int click = JOptionPane.showConfirmDialog(null, "Đăng xuất ngay bây giờ?");
        if (click == 0) {
            this.setVisible(false);
            new LoginForm().setVisible(true);
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnchanfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnchanfeActionPerformed
       new PasswordChange(txtName1.getText()).setVisible(true);
    }//GEN-LAST:event_btnchanfeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Information(txtName1.getText()).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txttesCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txttesCaretUpdate
   
    }//GEN-LAST:event_txttesCaretUpdate

    private void jLabel2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel2KeyPressed
                LoadTblFromDB1();        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2KeyPressed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
      LoadTblFromDB1(); 
    }//GEN-LAST:event_jLabel2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnchanfe;
    private javax.swing.JComboBox cbCTKM;
    private javax.swing.JComboBox cbProduct;
    private javax.swing.JComboBox cbSize;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbDateCus;
    private javax.swing.JLabel lbDisCus;
    private javax.swing.JLabel lbIDCus;
    private javax.swing.JLabel lbIDError;
    private javax.swing.JLabel lbNameCus;
    private javax.swing.JLabel lbNgayKM;
    private javax.swing.JLabel lbNhap;
    private javax.swing.JLabel lbQuantityCus;
    private javax.swing.JLabel lbTime;
    private javax.swing.JLabel lberror;
    private javax.swing.JPanel pnInformation;
    private javax.swing.JSpinner spQuantity;
    private javax.swing.JTable tblBill;
    private javax.swing.JTextField txtDis1;
    private javax.swing.JTextField txtDis2;
    private javax.swing.JTextField txtGuest;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIDBill;
    private javax.swing.JTextField txtName1;
    private javax.swing.JTextField txtPay;
    private javax.swing.JTextField txtRepay;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txttes;
    // End of variables declaration//GEN-END:variables

    private void ResetPnInfor() {
        lbIDCus.setText("...");
        lbNameCus.setText("...");
        lbDateCus.setText("...");
        lbQuantityCus.setText("...");
        lbDisCus.setText("...");
    }
    
}
