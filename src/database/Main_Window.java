/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Rene
 */
public class Main_Window extends javax.swing.JFrame {

    /**
     * Creates new form Main_Window
     * @param args
     */
    public Main_Window() {
        initComponents();
        getConnection();
        Show_Computers_In_JTable();
        Show_Equipment_In_JTable();
        Show_Software_In_JTable();
        Show_User_In_JTable();
    }
    int pos = 0;
    
    public Connection getConnection(){
        
        Connection con = null;
            
        try {
            con = 
            DriverManager.getConnection("jdbc:mysql://mysql.cc.puv.fi:3306/e1500867_Harjoitustyö", "e1500867", "MXWkFUbQZ2Pc");
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Not Connected");
            return null;
        }
    }
    
    // Check Input Fields
    
    public boolean checkInputs1() {
        if (txt_compName.getText() == null || 
            txt_compModel.getText() == null ||
            txt_compProcessor.getText() == null ||
            txt_compRAM.getText() == null) 
        {
        return false;
        }
            else {
                    try {
                         String.valueOf(txt_compName.getText());
                         return true;
                    } catch(Exception ex) {
                        return false;
                    }
        } 
    }
 
    
    
    public boolean checkInputs2() {
        if (txt_softName.getText() == null || 
            txt_softVersion.getText() == null ||
            txt_softLicense.getText() == null ||
            txt_softAquired == null) 
        {
        return false;
        }
            else {
                    try {
                         String.valueOf(txt_softName.getText());
                         return true;
                    } catch(Exception ex) {
                        return false;
                    }
        } 
    }
    
        public boolean checkInputs3() {
        if (txt_equipName.getText() == null ||
            txt_equipComp.getText() == null)
        {
        return false;
        }
            else {
                    try {
                         String.valueOf(txt_equipName.getText());
                         return true;
                    } catch(Exception ex) {
                        return false;
                    }
        } 
    }
        
        public boolean checkInputs4() {
        if (txt_userName.getText() == null ||
            txt_userComp.getText() == null)
        {
        return false;
        }
            else {
                    try {
                         String.valueOf(txt_userName.getText());
                         return true;
                    } catch(Exception ex) {
                        return false;
                    }
        } 
    }
    
    
    // Display data in JTable
    //   1 - Fill ComputerArrayList With the Data
        
        public ArrayList<Computer> getComputerList()
        {
            ArrayList<Computer> computerList = new ArrayList<Computer>();
            Connection con = getConnection();
            String query = "SELECT * FROM computer";
            
            Statement st;
            ResultSet rs;
            
            try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Computer computer;
            
            while (rs.next())
            {
                computer = new Computer(rs.getInt("computerID"),rs.getString("computerName"),rs.getString("computerModel"), rs.getString("processor"),rs.getString("ram"));
                computerList.add(computer);
            }
            } catch (SQLException ex) {
                Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
        }
            return computerList;
            
        }
        
        public void Show_Computers_In_JTable()
        {
            ArrayList<Computer> list = getComputerList();
            DefaultTableModel model = (DefaultTableModel)jTable_Computer.getModel();
            
            // Clear jTable content
            model.setRowCount(0);
            Object[] row = new Object[5];
            for(int i = 0; i<list.size(); i++)
            {
                row[0] = list.get(i).getComputerID();
                row[1] = list.get(i).getComputerName();
                row[2] = list.get(i).getComputerModel();
                row[3] = list.get(i).getProcessor();
                row[4] = list.get(i).getRam();
                
                model.addRow(row);
            }
        }
        
        public void showItem1(int index)
        {
            txt_compID.setText(Integer.toString(getComputerList().get(index).getComputerID()));
            txt_compName.setText(getComputerList().get(index).getComputerName());
            txt_compModel.setText(getComputerList().get(index).getComputerModel());
            txt_compProcessor.setText(getComputerList().get(index).getProcessor());
            txt_compRAM.setText(getComputerList().get(index).getRam());
        }
        
        
            
        //   2 - Fill Equipment ArrayList With the Data
        public ArrayList<Equipment> getEquipmentList()
        {
            ArrayList<Equipment> equipmentList = new ArrayList<Equipment>();
            Connection con = getConnection();
            String query = "SELECT * FROM equipment";
            
            Statement st;
            ResultSet rs;
            
            try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Equipment equipment;
            
            while (rs.next())
            {
                equipment = new Equipment(rs.getInt("equipmentID"),rs.getString("equipmentName"),rs.getInt("computer_computerID"));
                equipmentList.add(equipment);
            }
            } catch (SQLException ex) {
                Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
            
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            return equipmentList;
        }
        
        public void Show_Equipment_In_JTable()
        {
            ArrayList<Equipment> list = getEquipmentList();
            DefaultTableModel model = (DefaultTableModel)jTable_Equipment.getModel();
            
            // Clear jTable content
            model.setRowCount(0);
            Object[] row = new Object[3];
            for(int i = 0; i<list.size(); i++)
            {
                row[0] = list.get(i).getEquipmentID();
                row[1] = list.get(i).getEquipmentName();
                row[2] = list.get(i).getEquipmentComp();              
                model.addRow(row);
            }
        }
        
        public void showItem2(int index)
        {
            txt_equipID.setText(Integer.toString(getEquipmentList().get(index).getEquipmentID()));
            txt_equipName.setText(getEquipmentList().get(index).getEquipmentName());
            txt_equipComp.setText(Integer.toString(getEquipmentList().get(index).getEquipmentComp()));
        }
        
        
        
        //   3 - Fill Software ArrayList With the Data
        public ArrayList<Software> getSoftwareList()
        {
            ArrayList<Software> softwareList = new ArrayList<Software>();
            Connection con = getConnection();
            String query = "SELECT * FROM software";
            
            Statement st;
            ResultSet rs;
            
            try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Software software;
            
            while (rs.next())
            {
                software = new Software(rs.getInt("softwareID"),rs.getString("softwareName"),rs.getString("version"),rs.getString("aquired"),rs.getString("license"));
                softwareList.add(software);
            }
            con.close();
            
            } catch (SQLException ex) {
                Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
            
            return softwareList;
        }
        
        
        public void Show_Software_In_JTable()
        {
            ArrayList<Software> list = getSoftwareList();
            DefaultTableModel model = (DefaultTableModel)jTable_Software.getModel();
            
            // Clear jTable content
            model.setRowCount(0);
            Object[] row = new Object[5];
            for(int i = 0; i<list.size(); i++)
            {
                row[0] = list.get(i).getSoftwareID();
                row[1] = list.get(i).getSoftwareName();
                row[2] = list.get(i).getVersion();
                row[3] = list.get(i).getAquired();
                row[4] = list.get(i).getLicense();
                
                model.addRow(row);
            }
        }
        
        public void showItem3(int index)
        {
            txt_softID.setText(Integer.toString(getSoftwareList().get(index).getSoftwareID()));
            txt_softName.setText(getSoftwareList().get(index).getSoftwareName());
            txt_softVersion.setText(getSoftwareList().get(index).getVersion());
            txt_softAquired.setText(getSoftwareList().get(index).getAquired());
            txt_softLicense.setText(getSoftwareList().get(index).getLicense());
        }
        
        
        //   4 - Fill Equipment ArrayList With the Data
        public ArrayList<User> getUserList()
        {
            ArrayList<User> userList = new ArrayList<User>();
            Connection con = getConnection();
            String query = "SELECT * FROM user";
            
            Statement st;
            ResultSet rs;
            
            try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            User user;
            
            while (rs.next())
            {
                user = new User(rs.getInt("userID"),rs.getString("userName"),rs.getInt("computer_computerID"));
                userList.add(user);
            }
            } catch (SQLException ex) {
                Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
            
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            return userList;
        }
        
        public void Show_User_In_JTable()
        {
            ArrayList<User> list = getUserList();
            DefaultTableModel model = (DefaultTableModel)jTable_User.getModel();
            
            // Clear jTable content
            model.setRowCount(0);
            Object[] row = new Object[3];
            for(int i = 0; i<list.size(); i++)
            {
                row[0] = list.get(i).getUserID();
                row[1] = list.get(i).getUserName();
                row[2] = list.get(i).getUserComp();              
                model.addRow(row);
            }
        }
        
        public void showItem4(int index)
        {
            txt_userID.setText(Integer.toString(getUserList().get(index).getUserID()));
            txt_userName.setText(getUserList().get(index).getUserName());
            txt_userComp.setText(Integer.toString(getUserList().get(index).getUserComp()));
        }
        
      



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_compID = new javax.swing.JTextField();
        txt_compName = new javax.swing.JTextField();
        txt_compModel = new javax.swing.JTextField();
        txt_compProcessor = new javax.swing.JTextField();
        txt_compRAM = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_Computer = new javax.swing.JTable();
        btnUpdate_comp = new javax.swing.JButton();
        btnAdd_comp = new javax.swing.JButton();
        btnDelete_comp = new javax.swing.JButton();
        comp_First = new javax.swing.JButton();
        comp_Back = new javax.swing.JButton();
        comp_Next = new javax.swing.JButton();
        comp_Last = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_equipID = new javax.swing.JTextField();
        txt_equipName = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_Equipment = new javax.swing.JTable();
        btnAdd_equip = new javax.swing.JButton();
        btnDelete_equip = new javax.swing.JButton();
        btnUpdate_equip = new javax.swing.JButton();
        equip_First = new javax.swing.JButton();
        equip_Back = new javax.swing.JButton();
        equip_Next = new javax.swing.JButton();
        equip_Last = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txt_equipComp = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_softID = new javax.swing.JTextField();
        txt_softVersion = new javax.swing.JTextField();
        txt_softName = new javax.swing.JTextField();
        txt_softLicense = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_Software = new javax.swing.JTable();
        btnAdd_soft = new javax.swing.JButton();
        btnDelete_soft = new javax.swing.JButton();
        btnUpdate_soft = new javax.swing.JButton();
        soft_First = new javax.swing.JButton();
        soft_Back = new javax.swing.JButton();
        soft_Next = new javax.swing.JButton();
        soft_Last = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        txt_softAquired = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txt_userID = new javax.swing.JTextField();
        txt_userName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_User = new javax.swing.JTable();
        btnAdd_user = new javax.swing.JButton();
        btnDelete_user = new javax.swing.JButton();
        btnUpdate_user = new javax.swing.JButton();
        user_First = new javax.swing.JButton();
        user_Back = new javax.swing.JButton();
        user_Next = new javax.swing.JButton();
        user_Last = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        txt_userComp = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(new javax.swing.border.MatteBorder(null));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Computer Database");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("ID:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Name:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Model:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Processor:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("RAM:");

        txt_compID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txt_compName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txt_compModel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_compModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_compModelActionPerformed(evt);
            }
        });

        txt_compProcessor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txt_compRAM.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTable_Computer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Model", "Processor", "RAM"
            }
        ));
        jTable_Computer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_ComputerMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable_Computer);

        btnUpdate_comp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/update.png"))); // NOI18N
        btnUpdate_comp.setText("Update");
        btnUpdate_comp.setToolTipText("");
        btnUpdate_comp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate_compActionPerformed(evt);
            }
        });

        btnAdd_comp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // NOI18N
        btnAdd_comp.setText("Add");
        btnAdd_comp.setToolTipText("");
        btnAdd_comp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd_compActionPerformed(evt);
            }
        });

        btnDelete_comp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        btnDelete_comp.setText("Delete");
        btnDelete_comp.setToolTipText("");
        btnDelete_comp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete_compActionPerformed(evt);
            }
        });

        comp_First.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comp_First.setIcon(new javax.swing.ImageIcon(getClass().getResource("/double-left-chevron.png"))); // NOI18N
        comp_First.setText("First");
        comp_First.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comp_FirstActionPerformed(evt);
            }
        });

        comp_Back.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comp_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/back.png"))); // NOI18N
        comp_Back.setText("Back");
        comp_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comp_BackActionPerformed(evt);
            }
        });

        comp_Next.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comp_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/next.png"))); // NOI18N
        comp_Next.setText("Next");
        comp_Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comp_NextActionPerformed(evt);
            }
        });

        comp_Last.setIcon(new javax.swing.ImageIcon(getClass().getResource("/double-angle-pointing-to-right.png"))); // NOI18N
        comp_Last.setText("Last");
        comp_Last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comp_LastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_compID, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(txt_compName)
                    .addComponent(txt_compModel)
                    .addComponent(txt_compProcessor)
                    .addComponent(txt_compRAM))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addComponent(btnAdd_comp, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnUpdate_comp, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete_comp, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(comp_First, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(comp_Back, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(comp_Next, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(comp_Last, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(txt_compID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_compName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_compModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txt_compProcessor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txt_compRAM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comp_First, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comp_Back, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comp_Next, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comp_Last, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete_comp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate_comp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd_comp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(195, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Computer", jPanel1);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel2.setForeground(java.awt.SystemColor.controlHighlight);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Equipment Database");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("ID:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Equipment:");

        txt_equipID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txt_equipName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_equipName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_equipNameActionPerformed(evt);
            }
        });

        jTable_Equipment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Equipment", "ComputerID"
            }
        ));
        jTable_Equipment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_EquipmentMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable_Equipment);

        btnAdd_equip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // NOI18N
        btnAdd_equip.setText("Add");
        btnAdd_equip.setToolTipText("");
        btnAdd_equip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd_equipActionPerformed(evt);
            }
        });

        btnDelete_equip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        btnDelete_equip.setText("Delete");
        btnDelete_equip.setToolTipText("");
        btnDelete_equip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete_equipActionPerformed(evt);
            }
        });

        btnUpdate_equip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/update.png"))); // NOI18N
        btnUpdate_equip.setText("Update");
        btnUpdate_equip.setToolTipText("");
        btnUpdate_equip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate_equipActionPerformed(evt);
            }
        });

        equip_First.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        equip_First.setIcon(new javax.swing.ImageIcon(getClass().getResource("/double-left-chevron.png"))); // NOI18N
        equip_First.setText("First");
        equip_First.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equip_FirstActionPerformed(evt);
            }
        });

        equip_Back.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        equip_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/back.png"))); // NOI18N
        equip_Back.setText("Back");
        equip_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equip_BackActionPerformed(evt);
            }
        });

        equip_Next.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        equip_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/next.png"))); // NOI18N
        equip_Next.setText("Next");
        equip_Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equip_NextActionPerformed(evt);
            }
        });

        equip_Last.setIcon(new javax.swing.ImageIcon(getClass().getResource("/double-angle-pointing-to-right.png"))); // NOI18N
        equip_Last.setText("Last");
        equip_Last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equip_LastActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("ComputerID:");

        txt_equipComp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(btnAdd_equip, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate_equip, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete_equip, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(equip_First, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(equip_Back, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(equip_Next, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(equip_Last, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_equipID, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(txt_equipName)
                            .addComponent(txt_equipComp)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_equipID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txt_equipName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txt_equipComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDelete_equip, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdate_equip, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAdd_equip, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(equip_First, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(equip_Back, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(equip_Next, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(equip_Last, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(209, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Equipment", jPanel2);

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));
        jPanel3.setBorder(new javax.swing.border.MatteBorder(null));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Software Database");
        jLabel3.setAlignmentX(0.5F);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("ID:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Name:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Version:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Aquired:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("License:");

        txt_softID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txt_softVersion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txt_softName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txt_softLicense.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTable_Software.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Version", "License", "Aquired"
            }
        ));
        jTable_Software.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_SoftwareMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_Software);

        btnAdd_soft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // NOI18N
        btnAdd_soft.setText("Add");
        btnAdd_soft.setToolTipText("");
        btnAdd_soft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd_softActionPerformed(evt);
            }
        });

        btnDelete_soft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        btnDelete_soft.setText("Delete");
        btnDelete_soft.setToolTipText("");
        btnDelete_soft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete_softActionPerformed(evt);
            }
        });

        btnUpdate_soft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/update.png"))); // NOI18N
        btnUpdate_soft.setText("Update");
        btnUpdate_soft.setToolTipText("");
        btnUpdate_soft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate_softActionPerformed(evt);
            }
        });

        soft_First.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        soft_First.setIcon(new javax.swing.ImageIcon(getClass().getResource("/double-left-chevron.png"))); // NOI18N
        soft_First.setText("First");
        soft_First.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soft_FirstActionPerformed(evt);
            }
        });

        soft_Back.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        soft_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/back.png"))); // NOI18N
        soft_Back.setText("Back");
        soft_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soft_BackActionPerformed(evt);
            }
        });

        soft_Next.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        soft_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/next.png"))); // NOI18N
        soft_Next.setText("Next");
        soft_Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soft_NextActionPerformed(evt);
            }
        });

        soft_Last.setIcon(new javax.swing.ImageIcon(getClass().getResource("/double-angle-pointing-to-right.png"))); // NOI18N
        soft_Last.setText("Last");
        soft_Last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soft_LastActionPerformed(evt);
            }
        });

        jLabel21.setText("              Use form: YYYY-MM-DD");

        txt_softAquired.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_softAquired.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_softAquiredActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(btnAdd_soft, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate_soft, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete_soft, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(soft_First, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(soft_Back, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(soft_Next, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(soft_Last, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                    .addComponent(txt_softID)
                    .addComponent(txt_softName)
                    .addComponent(txt_softVersion)
                    .addComponent(txt_softLicense)
                    .addComponent(txt_softAquired))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txt_softID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txt_softName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txt_softVersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txt_softLicense, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_softAquired, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(soft_First, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soft_Back, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soft_Next, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soft_Last, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete_soft, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate_soft, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd_soft, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(186, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Software", jPanel3);

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));
        jPanel4.setBorder(new javax.swing.border.MatteBorder(null));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("User Database");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("ID:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Name:");

        txt_userID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txt_userName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTable_User.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "ComputerID"
            }
        ));
        jTable_User.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_UserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_User);

        btnAdd_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // NOI18N
        btnAdd_user.setText("Add");
        btnAdd_user.setToolTipText("");
        btnAdd_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd_userActionPerformed(evt);
            }
        });

        btnDelete_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        btnDelete_user.setText("Delete");
        btnDelete_user.setToolTipText("");
        btnDelete_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete_userActionPerformed(evt);
            }
        });

        btnUpdate_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/update.png"))); // NOI18N
        btnUpdate_user.setText("Update");
        btnUpdate_user.setToolTipText("");
        btnUpdate_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate_userActionPerformed(evt);
            }
        });

        user_First.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        user_First.setIcon(new javax.swing.ImageIcon(getClass().getResource("/double-left-chevron.png"))); // NOI18N
        user_First.setText("First");
        user_First.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                user_FirstActionPerformed(evt);
            }
        });

        user_Back.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        user_Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/back.png"))); // NOI18N
        user_Back.setText("Back");
        user_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                user_BackActionPerformed(evt);
            }
        });

        user_Next.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        user_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/next.png"))); // NOI18N
        user_Next.setText("Next");
        user_Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                user_NextActionPerformed(evt);
            }
        });

        user_Last.setIcon(new javax.swing.ImageIcon(getClass().getResource("/double-angle-pointing-to-right.png"))); // NOI18N
        user_Last.setText("Last");
        user_Last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                user_LastActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setText("ComputerID:");

        txt_userComp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(btnAdd_user, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate_user, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDelete_user, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(user_First, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(user_Back, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(user_Next, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(user_Last, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_userID, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                    .addComponent(txt_userName)
                    .addComponent(txt_userComp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txt_userID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txt_userName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_userComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDelete_user, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdate_user, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAdd_user, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(user_First, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(user_Back, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(user_Next, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(user_Last, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(209, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("User", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdd_compActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd_compActionPerformed
 
        if(checkInputs1() && getConnection() != null)
        {
            try {
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO computer(computerID, computerName, computerModel, processor, ram)" + "values(?,?,?,?,?) ");
                ps.setString(1,txt_compID.getText());
                ps.setString(2,txt_compName.getText());
                ps.setString(3,txt_compModel.getText());
                ps.setString(4,txt_compProcessor.getText());
                ps.setString(5,txt_compRAM.getText());
                
                ps.executeUpdate();
                Show_Computers_In_JTable();
                
                JOptionPane.showMessageDialog(null, "Data Uploaded");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "One or More Fields Are Empty");
        }

    }//GEN-LAST:event_btnAdd_compActionPerformed

    private void btnAdd_equipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd_equipActionPerformed
               
        if(checkInputs3() && getConnection() != null)
        {
            try {
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO equipment(equipmentID, equipmentName, computer_computerID)" + "values(?,?,?) ");
                ps.setString(1,txt_equipID.getText());
                ps.setString(2,txt_equipName.getText());
                ps.setString(3,txt_equipComp.getText());
                
                ps.executeUpdate();
                Show_Equipment_In_JTable();
                
                JOptionPane.showMessageDialog(null, "Data Uploaded");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "One or More Fields Are Empty");
        }

    }//GEN-LAST:event_btnAdd_equipActionPerformed

    private void btnAdd_softActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd_softActionPerformed
                
        if(checkInputs2() && getConnection() != null)
        {
            try {
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO software(softwareID, softwareName, version, aquired, license)" + "values(?,?,?,?,?) ");
                ps.setString(1,txt_softID.getText());
                ps.setString(2,txt_softName.getText());
                ps.setString(3,txt_softVersion.getText());
                ps.setString(4,txt_softAquired.getText());
                ps.setString(5,txt_softLicense.getText());
                
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Uploaded");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "One or More Fields Are Empty");
        }
    }//GEN-LAST:event_btnAdd_softActionPerformed

    private void btnAdd_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd_userActionPerformed
                
        if(checkInputs4() && getConnection() != null)
        {
            try {
                Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO user(userID, userName, computer_computerID)" + "values(?,?,?) ");
                ps.setString(1,txt_userID.getText());
                ps.setString(2,txt_userName.getText());
                ps.setString(3,txt_userComp.getText());
                ps.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Data Uploaded");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "One or More Fields Are Empty");
        }
    }//GEN-LAST:event_btnAdd_userActionPerformed

    private void btnUpdate_compActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate_compActionPerformed
        
        if (checkInputs1() && txt_compID.getText() != null)
        {
            String UpdateQuery = null;
            PreparedStatement ps = null;
            Connection con = getConnection();
            
            try {
                UpdateQuery = "UPDATE computer SET computerName = ?, computerModel = ?, processor = ?, ram = ? WHERE computerID = ?";
                ps = con.prepareStatement(UpdateQuery);
                ps.setString(1, txt_compName.getText());
                ps.setString(2, txt_compModel.getText());
                ps.setString(3, txt_compProcessor.getText());
                ps.setString(4, txt_compRAM.getText());
                
                ps.setInt(5, Integer.parseInt(txt_compID.getText()));
                
                ps.executeUpdate();
                Show_Computers_In_JTable();
                
                JOptionPane.showMessageDialog(null, "Updated");
                
            } catch (SQLException ex) {
                Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "One or More Fields Are Empty or Wrong");
        }

    }//GEN-LAST:event_btnUpdate_compActionPerformed

    private void btnUpdate_equipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate_equipActionPerformed
                
        if (checkInputs3() && txt_equipID.getText() != null)
        {
            String UpdateQuery = null;
            PreparedStatement ps = null;
            Connection con = getConnection();
            
            try {
                UpdateQuery = "UPDATE equipment SET equipmentName = ?, computer_computerID = ? WHERE equipmentID = ?";
                ps = con.prepareStatement(UpdateQuery);
                ps.setString(1, txt_equipName.getText());
                ps.setString(2, txt_equipComp.getText());
                
                ps.setInt(3, Integer.parseInt(txt_equipID.getText()));
                
                ps.executeUpdate();
                Show_Equipment_In_JTable();
                JOptionPane.showMessageDialog(null, "Updated");
                
            } catch (SQLException ex) {
                Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "One or More Fields Are Empty or Wrong");
        }
    }//GEN-LAST:event_btnUpdate_equipActionPerformed

    private void btnUpdate_softActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate_softActionPerformed
        if (checkInputs2() && txt_softID.getText() != null)
        {
            String UpdateQuery = null;
            PreparedStatement ps = null;
            Connection con = getConnection();
            
            try {
                UpdateQuery = "UPDATE software SET softwareName = ?, version = ?, aquired = ?, license = ? WHERE softwareID = ?";
                ps = con.prepareStatement(UpdateQuery);
                ps.setString(1, txt_softName.getText());
                ps.setString(2, txt_softVersion.getText());
                ps.setString(3, txt_softAquired.getText());
                ps.setString(4, txt_softLicense.getText());
                
                ps.setInt(5, Integer.parseInt(txt_softID.getText()));
                
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Updated");
                
            } catch (SQLException ex) {
                Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "One or More Fields Are Empty or Wrong");
        }
    }//GEN-LAST:event_btnUpdate_softActionPerformed

    private void btnUpdate_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate_userActionPerformed
                        
        if (checkInputs4() && txt_userID.getText() != null)
        {
            String UpdateQuery = null;
            PreparedStatement ps = null;
            Connection con = getConnection();
            
            try {
                UpdateQuery = "UPDATE user SET userName = ?, computer_computerID = ? WHERE userID = ?";
                ps = con.prepareStatement(UpdateQuery);
                ps.setString(1, txt_userName.getText());
                ps.setString(2, txt_userComp.getText());
                
                ps.setInt(3, Integer.parseInt(txt_userID.getText()));
                
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Updated");
                
            } catch (SQLException ex) {
                Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "One or More Fields Are Empty or Wrong");
        }
    }//GEN-LAST:event_btnUpdate_userActionPerformed

    private void txt_compModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_compModelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_compModelActionPerformed

    private void txt_equipNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_equipNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_equipNameActionPerformed

    private void txt_softAquiredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_softAquiredActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_softAquiredActionPerformed

    private void btnDelete_compActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete_compActionPerformed
        
        if(!txt_compID.getText().equals(""))
        {
            try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM computer WHERE computerID = ?");
            int computerID = Integer.parseInt(txt_compID.getText());
            ps.setInt(1, computerID);
            
            ps.executeUpdate();
            Show_Computers_In_JTable();
            
            JOptionPane.showMessageDialog(null, "Row Deleted");
            
            } catch (SQLException ex) {
                Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Row Not Deleted");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Row Not Deleted \nEnter a ComputerID");
        }
        
    }//GEN-LAST:event_btnDelete_compActionPerformed

    private void btnDelete_equipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete_equipActionPerformed
                
        if(!txt_equipID.getText().equals(""))
        {
            try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM equipment WHERE equipmentID = ?");
            int equipmentID = Integer.parseInt(txt_equipID.getText());
            ps.setInt(1, equipmentID);
            
            ps.executeUpdate();
            Show_Equipment_In_JTable();
            
            JOptionPane.showMessageDialog(null, "Row Deleted");
            
            } catch (SQLException ex) {
                Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Row Not Deleted");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Row Not Deleted \nEnter a EquipmentID");
        }
    }//GEN-LAST:event_btnDelete_equipActionPerformed

    private void btnDelete_softActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete_softActionPerformed
                
        if(!txt_softID.getText().equals(""))
        {
            try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM software WHERE softwareID = ?");
            int softwareID = Integer.parseInt(txt_softID.getText());
            ps.setInt(1, softwareID);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Row Deleted");
            
            } catch (SQLException ex) {
                Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Row Not Deleted");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Row Not Deleted \nEnter a SoftwareID");
        }
    }//GEN-LAST:event_btnDelete_softActionPerformed

    private void btnDelete_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete_userActionPerformed
                
        if(!txt_userID.getText().equals(""))
        {
            try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM user WHERE userID = ?");
            int userID1 = Integer.parseInt(txt_userID.getText());
            ps.setInt(1, userID1);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Row Deleted");
            
            } catch (SQLException ex) {
                Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Row Not Deleted");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Row Not Deleted \nEnter a UserID");
        }
    }//GEN-LAST:event_btnDelete_userActionPerformed

    private void jTable_ComputerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_ComputerMouseClicked
        int index = jTable_Computer.getSelectedRow();
        showItem1(index);
    }//GEN-LAST:event_jTable_ComputerMouseClicked

    private void comp_FirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comp_FirstActionPerformed
        pos = 0;
        showItem1(pos);
    }//GEN-LAST:event_comp_FirstActionPerformed

    private void comp_LastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comp_LastActionPerformed
        pos = getComputerList().size()-1;
        showItem1(pos);
    }//GEN-LAST:event_comp_LastActionPerformed

    private void comp_NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comp_NextActionPerformed
        pos++;
        if(pos >= getComputerList().size())
        {
            pos = getComputerList().size()-1;
        }
        showItem1(pos);
    }//GEN-LAST:event_comp_NextActionPerformed

    private void comp_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comp_BackActionPerformed
        pos--;
        if(pos < 0)
        {
            pos = 0;
        }
        showItem1(pos);
    }//GEN-LAST:event_comp_BackActionPerformed

    private void equip_FirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equip_FirstActionPerformed
        pos = 0;
        showItem2(pos);
    }//GEN-LAST:event_equip_FirstActionPerformed

    private void equip_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equip_BackActionPerformed
        pos--;
        if(pos < 0)
        {
            pos = 0;
        }
        showItem2(pos);
    }//GEN-LAST:event_equip_BackActionPerformed

    private void equip_NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equip_NextActionPerformed
        pos++;
        if(pos >= getEquipmentList().size())
        {
            pos = getEquipmentList().size()-1;
        }
        showItem2(pos);
    }//GEN-LAST:event_equip_NextActionPerformed

    private void equip_LastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equip_LastActionPerformed
        pos = getEquipmentList().size()-1;
        showItem2(pos);
    }//GEN-LAST:event_equip_LastActionPerformed

    private void jTable_EquipmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_EquipmentMouseClicked
        int index = jTable_Equipment.getSelectedRow();
        showItem2(index);
    }//GEN-LAST:event_jTable_EquipmentMouseClicked

    private void soft_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soft_BackActionPerformed
        pos--;
        if(pos < 0)
        {
            pos = 0;
        }
        showItem3(pos);
    }//GEN-LAST:event_soft_BackActionPerformed

    private void user_LastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_user_LastActionPerformed
        pos = getComputerList().size()-1;
        showItem4(pos);
    }//GEN-LAST:event_user_LastActionPerformed

    private void jTable_SoftwareMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_SoftwareMouseClicked
        int index = jTable_Software.getSelectedRow();
        showItem3(index);
    }//GEN-LAST:event_jTable_SoftwareMouseClicked

    private void soft_FirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soft_FirstActionPerformed
        pos = 0;
        showItem3(pos);
    }//GEN-LAST:event_soft_FirstActionPerformed

    private void soft_NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soft_NextActionPerformed
        pos++;
        if(pos >= getComputerList().size())
        {
            pos = getComputerList().size()-1;
        }
        showItem3(pos);
    }//GEN-LAST:event_soft_NextActionPerformed

    private void soft_LastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soft_LastActionPerformed
        pos = getSoftwareList().size()-1;
        showItem3(pos);
    }//GEN-LAST:event_soft_LastActionPerformed

    private void jTable_UserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_UserMouseClicked
        int index = jTable_User.getSelectedRow();
        showItem4(index);
    }//GEN-LAST:event_jTable_UserMouseClicked

    private void user_FirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_user_FirstActionPerformed
        pos = 0;
        showItem4(pos);
    }//GEN-LAST:event_user_FirstActionPerformed

    private void user_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_user_BackActionPerformed
        pos--;
        if(pos < 0)
        {
            pos = 0;
        }
        showItem4(pos);
    }//GEN-LAST:event_user_BackActionPerformed

    private void user_NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_user_NextActionPerformed
        pos++;
        if(pos >= getUserList().size())
        {
            pos = getUserList().size()-1;
        }
        showItem4(pos);
    }//GEN-LAST:event_user_NextActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main_Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main_Window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd_comp;
    private javax.swing.JButton btnAdd_equip;
    private javax.swing.JButton btnAdd_soft;
    private javax.swing.JButton btnAdd_user;
    private javax.swing.JButton btnDelete_comp;
    private javax.swing.JButton btnDelete_equip;
    private javax.swing.JButton btnDelete_soft;
    private javax.swing.JButton btnDelete_user;
    private javax.swing.JButton btnUpdate_comp;
    private javax.swing.JButton btnUpdate_equip;
    private javax.swing.JButton btnUpdate_soft;
    private javax.swing.JButton btnUpdate_user;
    private javax.swing.JButton comp_Back;
    private javax.swing.JButton comp_First;
    private javax.swing.JButton comp_Last;
    private javax.swing.JButton comp_Next;
    private javax.swing.JButton equip_Back;
    private javax.swing.JButton equip_First;
    private javax.swing.JButton equip_Last;
    private javax.swing.JButton equip_Next;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable_Computer;
    private javax.swing.JTable jTable_Equipment;
    private javax.swing.JTable jTable_Software;
    private javax.swing.JTable jTable_User;
    private javax.swing.JButton soft_Back;
    private javax.swing.JButton soft_First;
    private javax.swing.JButton soft_Last;
    private javax.swing.JButton soft_Next;
    private javax.swing.JTextField txt_compID;
    private javax.swing.JTextField txt_compModel;
    private javax.swing.JTextField txt_compName;
    private javax.swing.JTextField txt_compProcessor;
    private javax.swing.JTextField txt_compRAM;
    private javax.swing.JTextField txt_equipComp;
    private javax.swing.JTextField txt_equipID;
    private javax.swing.JTextField txt_equipName;
    private javax.swing.JTextField txt_softAquired;
    private javax.swing.JTextField txt_softID;
    private javax.swing.JTextField txt_softLicense;
    private javax.swing.JTextField txt_softName;
    private javax.swing.JTextField txt_softVersion;
    private javax.swing.JTextField txt_userComp;
    private javax.swing.JTextField txt_userID;
    private javax.swing.JTextField txt_userName;
    private javax.swing.JButton user_Back;
    private javax.swing.JButton user_First;
    private javax.swing.JButton user_Last;
    private javax.swing.JButton user_Next;
    // End of variables declaration//GEN-END:variables
}
