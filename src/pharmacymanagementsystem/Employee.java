/* 
 * Copyright (c) 2014, Sachin
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package pharmacymanagementsystem;
import java.awt.*;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author Admin
 */
public class Employee extends javax.swing.JFrame {

    /**
     * Creates new form Employee
     */
    public Employee() {
        initComponents();
        RetrieveData(btmodel,"select Product_id,Name,Type,Manufacturar,Quantity from MedicineDetails where Quantity<8");
        RetrieveData(ctmodel,"select * from CustomerDetails");
        this.setIconImage(new ImageIcon(getClass().getResource("flogo.png")).getImage());
        if(emp.equalsIgnoreCase("Employee"))
            jMenuItem1.setVisible(false);
        setInvoice();
    }
    DefaultTableModel btmodel=new DefaultTableModel(new Object [][] {},new String [] {"Product ID", "Product Name", "Product Type", "Manufacturar", "Quantity Available"});
    DefaultTableModel atmodel=new DefaultTableModel(new Object [][] {},new String [] {"Barcode", "Product Id", "Product Name", "Batch", "Manufacturar", "Type", "Quantity", "Category", "Schedule", "Pack contains", "Date Of Expiry", "M.R.P.", "Composition"});
    DefaultTableModel ctmodel=new DefaultTableModel(new Object [][] {},new String [] {"ID", "MemberShip Card Number", "Name", "Date Of Birth", "Age", "Gender", "Address", "Referensed Doctor", "Doctor's Registration Number", "Email Address", "Contact No", "Mode Of Payment Uses"});
    DefaultTableModel itmodel=new DefaultTableModel(new Object [][] {},new String [] {"S.No", "Product Name", "Manufaturer", "Date Of Expiry", "Quantity", "M.R.P", "Amount"});
    Connection con=null;
    Statement st=null;
    int sn=1;
    java.util.Date date=new java.util.Date();
    SimpleDateFormat sde=new SimpleDateFormat("YYYYMMdd");
    long invoiceno=Long.parseLong(sde.format(date))*1000;
    double tamount=0d;
    double amt=0d;
    PharmacyManagementSystem pms=new PharmacyManagementSystem();
    
    private void RetrieveData (DefaultTableModel tmodel,String qry)
	{
		try
		{
			int row = tmodel.getRowCount();
			while(row > 0)
			{
				row--;
				tmodel.removeRow(row);
			}
			con=pms.makeConnection();
			st=con.createStatement();
			ResultSet rs = st.executeQuery(qry);
			ResultSetMetaData md = rs.getMetaData();
			int colcount = md.getColumnCount();
			Object[] data = new Object[colcount];
			while (rs.next())
			{
				for (int i=1; i<=colcount; i++)
				{
					data[i-1] = rs.getString(i);
				}
				tmodel.addRow(data);
			}
		}
		catch(Exception e) {
                   
                //e.printStackTrace();
JOptionPane.showMessageDialog(this, "No Bounce Item Found!!", "Information", JOptionPane.INFORMATION_MESSAGE);	
                }
	}
    private void updatet(int x,String name1)
    {
        int quant=0;
        String qry="select quantity from MedicineDetails where name='"+name1+"'";
        try 
        {
            con=pms.makeConnection();
            st=con.createStatement();
            ResultSet rs=st.executeQuery(qry);
            while(rs.next()){
            quant=((rs.getInt("quantity"))-x);
            }
            String qy="update MedicineDetails set quantity='"+quant+"'where name='"+name1+"'";
            st.executeUpdate(qy);
            con.commit();
            con.close();
        }
        catch(Exception e)
        {
           JOptionPane.showMessageDialog(this,"Insufficient Quantity!!\nPlease Check Available Stock.");
           e.printStackTrace();
        }
    }
    
    private void setInvoice()
    {
        try
        {
            con=pms.makeConnection();
            st=con.createStatement();
            ResultSet rs=st.executeQuery("select Invoice_No from SaleHistory");
            while(rs.next())
            {
                invoice1.setText(String.valueOf(rs.getLong(1)));
                if(invoiceno==rs.getLong(1))
                invoiceno=(rs.getLong(1))+1;
            }
        }
        catch(Exception e)
        {
            
        }
    }
    
    private void accessdata(String qry)
    {
        try
        {
            con=pms.makeConnection();
            st=con.createStatement();
            ResultSet rs=st.executeQuery(qry);
            while(rs.next())
            {
                barcode1.setText(rs.getString(1));
                pid1.setText(rs.getString(2));
                name1.setText(rs.getString(3));
                batch1.setText(rs.getString(4));
                manufature1.setText(rs.getString(5));
                type1.setText(rs.getString(6));
                category1.setText(rs.getString(8));
                sch1.setText(rs.getString(9));
                pack1.setText(rs.getString(10));
                expiry1.setText(String.valueOf(rs.getDate(11)));
                mrp1.setText(rs.getString(12));
                composition1.setText(rs.getString(13));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
 public void progressbar() throws InterruptedException
    {
        int i;
        for(i=0;i<=100;i++)
        {
            PB.setValue(i);
            PB.paintImmediately(0, 0, 200, 200);
            PBS.setText("Loading....");
            if(i==100)
                PBS.setText("DONE");
            
            try {
                Thread.sleep(i);
            } catch (InterruptedException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        PBS = new javax.swing.JLabel();
        PB = new javax.swing.JProgressBar();
        jSeparator1 = new javax.swing.JSeparator();
        EmployWelcomeScreen = new javax.swing.JTabbedPane();
        Sale_Creation = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        barcode1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pid1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        name1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        batch1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        quant1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        manufature1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        type1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        expiry1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        category1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        sch1 = new javax.swing.JTextField();
        pack1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tax1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        itablel = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        totalamount1 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        invoice1 = new javax.swing.JLabel();
        mrp1 = new javax.swing.JTextField();
        sq1 = new javax.swing.JComboBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel33 = new javax.swing.JLabel();
        composition1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        Available_stock = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        Bounce = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Employee Zone");
        setMinimumSize(new java.awt.Dimension(1024, 768));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel2.setMinimumSize(new java.awt.Dimension(100, 20));
        jPanel2.setPreferredSize(new java.awt.Dimension(586, 20));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(PBS, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PBS, javax.swing.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE)
                    .addComponent(jSeparator1)))
            .addComponent(PB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        EmployWelcomeScreen.setBackground(new java.awt.Color(0, 204, 255));
        EmployWelcomeScreen.setForeground(new java.awt.Color(0, 157, 10));
        EmployWelcomeScreen.setFont(new java.awt.Font("Pristina", 3, 18)); // NOI18N
        EmployWelcomeScreen.setMinimumSize(new java.awt.Dimension(1024, 768));
        EmployWelcomeScreen.setPreferredSize(new java.awt.Dimension(1024, 768));

        Sale_Creation.setBackground(new java.awt.Color(143, 111, 224));

        jLabel1.setFont(new java.awt.Font("Vladimir Script", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("Welcome To The Sale Creation Section");

        jLabel2.setForeground(new java.awt.Color(255, 255, 0));
        jLabel2.setText("Bar Code");

        jLabel3.setForeground(new java.awt.Color(255, 255, 0));
        jLabel3.setText("Product ID");

        pid1.setEnabled(false);

        jLabel4.setForeground(new java.awt.Color(255, 255, 0));
        jLabel4.setText("Name");

        jLabel5.setForeground(new java.awt.Color(255, 255, 0));
        jLabel5.setText("Batch");

        batch1.setEnabled(false);

        jLabel6.setForeground(new java.awt.Color(255, 255, 0));
        jLabel6.setText("Sale Quantity");

        quant1.setText("Enter Quntity Here In Number");
        quant1.setEnabled(false);
        quant1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quant1MouseClicked(evt);
            }
        });
        quant1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quant1ActionPerformed(evt);
            }
        });
        quant1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                quant1KeyTyped(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(255, 255, 0));
        jLabel7.setText("Manufacturing Company");

        manufature1.setEnabled(false);

        jLabel8.setForeground(new java.awt.Color(255, 255, 0));
        jLabel8.setText("Type");

        type1.setEnabled(false);

        jLabel9.setForeground(new java.awt.Color(255, 255, 0));
        jLabel9.setText("Date Of Expiry");

        expiry1.setEnabled(false);

        jLabel10.setForeground(new java.awt.Color(255, 255, 0));
        jLabel10.setText("Category");

        jLabel11.setForeground(new java.awt.Color(255, 255, 0));
        jLabel11.setText("Sch");

        category1.setEnabled(false);

        jLabel12.setForeground(new java.awt.Color(255, 255, 0));
        jLabel12.setText("Pack");

        sch1.setEnabled(false);

        pack1.setEnabled(false);

        jLabel13.setForeground(new java.awt.Color(255, 255, 0));
        jLabel13.setText("Tax");

        tax1.setText("12.5");
        tax1.setEnabled(false);

        jLabel14.setForeground(new java.awt.Color(255, 255, 0));
        jLabel14.setText("M.R.P.");

        jButton1.setFont(new java.awt.Font("Juice ITC", 1, 18)); // NOI18N
        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 102, 51));

        itablel.setModel(itmodel);
        jScrollPane2.setViewportView(itablel);
        if (itablel.getColumnModel().getColumnCount() > 0) {
            itablel.getColumnModel().getColumn(0).setMinWidth(50);
            itablel.getColumnModel().getColumn(0).setPreferredWidth(50);
            itablel.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("TOTAL  AMOUNT  : -");

        totalamount1.setText("Rs.");

        jButton6.setFont(new java.awt.Font("Juice ITC", 1, 18)); // NOI18N
        jButton6.setText("Done");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalamount1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(162, 162, 162)
                .addComponent(jButton6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(totalamount1)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Last Invoice Generated : -");

        invoice1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        invoice1.setForeground(new java.awt.Color(255, 255, 0));
        invoice1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        invoice1.setText("0");

        mrp1.setEnabled(false);

        sq1.setForeground(new java.awt.Color(255, 255, 0));
        sq1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        sq1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sq1ActionPerformed(evt);
            }
        });

        jCheckBox1.setForeground(new java.awt.Color(255, 153, 0));
        jCheckBox1.setText("If Greater than 10, Check Me");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel33.setForeground(new java.awt.Color(255, 255, 0));
        jLabel33.setText("Composition");

        composition1.setEnabled(false);

        jButton5.setFont(new java.awt.Font("Juice ITC", 3, 18)); // NOI18N
        jButton5.setText("Remove ALL");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Sale_CreationLayout = new javax.swing.GroupLayout(Sale_Creation);
        Sale_Creation.setLayout(Sale_CreationLayout);
        Sale_CreationLayout.setHorizontalGroup(
            Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Sale_CreationLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Sale_CreationLayout.createSequentialGroup()
                        .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Sale_CreationLayout.createSequentialGroup()
                                .addGap(439, 439, 439)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Sale_CreationLayout.createSequentialGroup()
                                .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Sale_CreationLayout.createSequentialGroup()
                                        .addComponent(jLabel33)
                                        .addGap(497, 497, 497)
                                        .addComponent(pack1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Sale_CreationLayout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(Sale_CreationLayout.createSequentialGroup()
                                                .addComponent(category1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(44, 44, 44)
                                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(sch1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(Sale_CreationLayout.createSequentialGroup()
                                                .addGap(74, 74, 74)
                                                .addComponent(composition1, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(Sale_CreationLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(Sale_CreationLayout.createSequentialGroup()
                                        .addComponent(name1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(batch1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(type1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(Sale_CreationLayout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(183, 183, 183)
                                        .addComponent(jButton5)
                                        .addGap(102, 102, 102)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Sale_CreationLayout.createSequentialGroup()
                                .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Sale_CreationLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(sq1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Sale_CreationLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(quant1)
                                            .addComponent(jCheckBox1)))
                                    .addGroup(Sale_CreationLayout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(expiry1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(Sale_CreationLayout.createSequentialGroup()
                                                .addComponent(jLabel14)
                                                .addGap(18, 18, 18)
                                                .addComponent(mrp1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(Sale_CreationLayout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tax1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(Sale_CreationLayout.createSequentialGroup()
                        .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Sale_CreationLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(barcode1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pid1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Sale_CreationLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(manufature1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Sale_CreationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(203, 203, 203))
            .addGroup(Sale_CreationLayout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(invoice1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Sale_CreationLayout.setVerticalGroup(
            Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Sale_CreationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(barcode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(pid1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Sale_CreationLayout.createSequentialGroup()
                        .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(name1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(batch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(sq1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(manufature1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(type1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(Sale_CreationLayout.createSequentialGroup()
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quant1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(expiry1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Sale_CreationLayout.createSequentialGroup()
                        .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(category1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(sch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pack1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel13)
                                .addComponent(tax1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14)
                                .addComponent(mrp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel33)
                                .addComponent(composition1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Sale_CreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(invoice1))
                .addContainerGap())
        );

        EmployWelcomeScreen.addTab("Create Sale", Sale_Creation);

        Available_stock.setBackground(new java.awt.Color(143, 111, 224));

        jLabel34.setFont(new java.awt.Font("Vladimir Script", 1, 36)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 0));
        jLabel34.setText("Welcome To Stock Report Section");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 0));
        jLabel36.setText("Select Category");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<Select Category>", "Containers", "Drops", "Fridge Items", "General", "Inhalers", "Injections", "Liquids", "Ointments", "Powder", "Surgerical Products", "Tablets" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTable3.setModel(atmodel);
        jScrollPane3.setViewportView(jTable3);

        jButton10.setFont(new java.awt.Font("Juice ITC", 1, 18)); // NOI18N
        jButton10.setText("Refresh Table");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Available_stockLayout = new javax.swing.GroupLayout(Available_stock);
        Available_stock.setLayout(Available_stockLayout);
        Available_stockLayout.setHorizontalGroup(
            Available_stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Available_stockLayout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addGroup(Available_stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addGroup(Available_stockLayout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(124, 124, 124)
                        .addComponent(jButton10)))
                .addGap(230, 230, 230))
            .addGroup(Available_stockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        Available_stockLayout.setVerticalGroup(
            Available_stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Available_stockLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel34)
                .addGap(28, 28, 28)
                .addGroup(Available_stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                .addContainerGap())
        );

        EmployWelcomeScreen.addTab("Available Stock", Available_stock);

        Bounce.setBackground(new java.awt.Color(143, 111, 224));

        jLabel35.setFont(new java.awt.Font("Vladimir Script", 0, 36)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 0));
        jLabel35.setText("Welcome To the Bounce Section");

        jTable1.setModel(btmodel);
        jScrollPane1.setViewportView(jTable1);

        jButton7.setFont(new java.awt.Font("Juice ITC", 1, 18)); // NOI18N
        jButton7.setText("Refresh Table");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BounceLayout = new javax.swing.GroupLayout(Bounce);
        Bounce.setLayout(BounceLayout);
        BounceLayout.setHorizontalGroup(
            BounceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BounceLayout.createSequentialGroup()
                .addGroup(BounceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BounceLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(BounceLayout.createSequentialGroup()
                        .addGap(331, 331, 331)
                        .addComponent(jLabel35)
                        .addGap(0, 276, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(BounceLayout.createSequentialGroup()
                .addGap(440, 440, 440)
                .addComponent(jButton7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BounceLayout.setVerticalGroup(
            BounceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BounceLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        EmployWelcomeScreen.addTab("Bounce", Bounce);

        jPanel1.setBackground(new java.awt.Color(143, 111, 224));

        jLabel19.setFont(new java.awt.Font("Vladimir Script", 3, 36)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 0));
        jLabel19.setText("Welcomr To See The Customer Details");

        jTable4.setModel(ctmodel);
        jScrollPane4.setViewportView(jTable4);

        jButton8.setFont(new java.awt.Font("Juice ITC", 1, 18)); // NOI18N
        jButton8.setText("Refresh");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(272, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(193, 193, 193))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(450, 450, 450)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        EmployWelcomeScreen.addTab("View Customers Details ", jPanel1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EmployWelcomeScreen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(EmployWelcomeScreen, javax.swing.GroupLayout.PREFERRED_SIZE, 741, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jScrollPane5.setViewportView(jPanel3);

        jMenu1.setText("System");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("As Admin");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Logout");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem3.setText("Shutdown Windows");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Shutdown Me");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem5.setText("About");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 962, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if(jCheckBox1.isSelected())
        {
        quant1.setEnabled(true);
        sq1.setEnabled(false);
        }
        else
        {
            quant1.setEnabled(false);
            sq1.setEnabled(true);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String[] s = new String[2];
        s[0]=String.valueOf(invoiceno);
        s[1]=String.valueOf(amt);
       // new PrintFile(itablel);
        System.out.println(s[0]+"\n"+s[1]);
        try {
		File f=new File("Invoice.txt");
		FileWriter fw=new FileWriter(f);
		int row=itmodel.getRowCount();
		int col=itmodel.getColumnCount();
                Object[][] tableData=new Object[row][col];
		BufferedWriter bw=new BufferedWriter(fw);
		String str="\n\t\t\t\t\t     ABCD Pharmaceuticals Limited    ";
		String st1="\n\t\t\t\t\t       Available At: XYZ Location     ";
		String st2="\n\t\t\t\t\t           Invoice Generation          ";
                String st9="\nInvoice No.:"+s[0];
		String st3="\n\t----------------------------------------------------------------------------------------------------------------------------------";
		String st4="\n\t NO.\t\tProduct Name \t Manufacturing Company \t Date Of Expiry      Quantity\t\t M.R.P.\t\t Amount";
                bw.write(str);
		bw.newLine();
		bw.write(st1);
		bw.newLine();
		bw.write(st2);
		bw.newLine();
                bw.write(st9);
		bw.newLine();
		bw.write(st3);
		bw.newLine();
		bw.write(st4);
		bw.newLine();
		bw.write(st3);
		bw.newLine();
              for(int i=0;i<row;i++)
                {
                    String temp="\n\t ";
                    bw.write(temp);
                
                    for(int j=0;j<col;j++)
                    {
                        tableData[i][j]=itmodel.getValueAt(i,j);
                        String st5=String.valueOf(tableData[i][j]);
                        bw.write(st5+"\t\t");
                    }
               }
                    bw.newLine();
                bw.write(st3);
                bw.newLine();
                bw.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t Total Amount = Rs."+s[1]);
                bw.newLine();
                bw.write(st3);
                bw.newLine();
                bw.write("Thank You for choosing our Services");
                bw.newLine();
		bw.flush();
                bw.close();
		}
		catch(Exception e)
		{
		JOptionPane.showMessageDialog(this,"Got Some Problem\n Try Again");
                e.printStackTrace();
		}
        FindCustomer.main(s);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void quant1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quant1MouseClicked
        quant1.setText("");
    }//GEN-LAST:event_quant1MouseClicked

    private void quant1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quant1KeyTyped
        if((quant1.getText()).equalsIgnoreCase("Enter Quntity Here In Number"))
            quant1.setText("");
    }//GEN-LAST:event_quant1KeyTyped

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
            RetrieveData(ctmodel,"select * from CustomerDetails");
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String search=String.valueOf(jComboBox1.getSelectedItem());
        String qry="select * from MedicineDetails where category='"+search+"'";
        RetrieveData(atmodel,qry);
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        String search=String.valueOf(jComboBox1.getSelectedItem());
        String qry="select * from MedicineDetails where category='"+search+"'";
        RetrieveData(atmodel,qry);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        RetrieveData(btmodel,"select ID,Name,Type,Manufacturar,Quantity from MedicineDetails where Quantity>5");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void sq1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sq1ActionPerformed
        String barcode=barcode1.getText();
        String name=name1.getText();
        if((barcode.length()==0) || (barcode.equalsIgnoreCase("")))
            accessdata("select * from MedicineDetails where Name='"+name+"'");
        else
            accessdata("select * from MedicineDetails where Barcode='"+barcode+"'");
    }//GEN-LAST:event_sq1ActionPerformed

    private void quant1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quant1ActionPerformed
        String barcode=barcode1.getText();
        String name=name1.getText();
        if((barcode.length()==0) || (barcode.equalsIgnoreCase("")))
            accessdata("select * from MedicineDetails where Name='"+name+"'");
        else
            accessdata("select * from MedicineDetails where Barcode='"+barcode+"'");
    }//GEN-LAST:event_quant1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String name,manufacturar,expiry;
        int quantity;
        Float mrp,amount;
        name=name1.getText();
        manufacturar=manufature1.getText();
        expiry=expiry1.getText();
        if(jCheckBox1.isSelected())
            quantity=Integer.parseInt(quant1.getText());
        else
            quantity=Integer.parseInt(String.valueOf(sq1.getSelectedItem()));
        mrp=Float.parseFloat(mrp1.getText());
        amount=((quantity*mrp)+(quantity*mrp*0.125f));
        Object[] addM={sn,name,manufacturar,expiry,quantity,mrp,amount};
        //Object[] addM={sn,"2","4","5","765","32","44"};
        try
        {
            con=pms.makeConnection();
            st=con.createStatement();
            ResultSet rs=st.executeQuery("select Quantity from MedicineDetails where Name='"+name+"'");
            int cout=0;
            while(rs.next())
            {
                if((rs.getInt(1))<=0)
                    cout++;
            }
            if(cout==1)
            {
                JOptionPane.showMessageDialog(this, "Insufficient Quantity\nOut OF Stock", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
            itmodel.addRow(addM);
            sn=sn+1;
            amt+=amount;
            totalamount1.setText(" RS(₹) "+amt);
            updatet(quantity,name);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int row = itmodel.getRowCount();
	while(row > 0)
	{
		row--;
		itmodel.removeRow(row);
	}
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.setVisible(false);
        String s[]={"Sachin"};
        AdminLogIn.main(s);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.setVisible(false);
        String s[]={"Sachin"};
        try {
            progressbar();
        } catch (InterruptedException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        EmployeeLogIn.main(s);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            String s="shutdown /s /f /t 30 /c Window will Shutdown Soon";
            Runtime rt=Runtime.getRuntime();
            rt.exec("cmd /c start cmd /k"+s);
        } catch (IOException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        String[] s={"Sachin"};
            About.main(s);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    /**
     * @param args the command line arguments
     */
    static String emp;
    public static void main(String args[]) {
        emp=args[0];
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
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Employee().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Available_stock;
    private javax.swing.JPanel Bounce;
    private javax.swing.JTabbedPane EmployWelcomeScreen;
    private javax.swing.JProgressBar PB;
    private javax.swing.JLabel PBS;
    private javax.swing.JPanel Sale_Creation;
    private javax.swing.JTextField barcode1;
    private javax.swing.JTextField batch1;
    private javax.swing.JTextField category1;
    private javax.swing.JTextField composition1;
    private javax.swing.JTextField expiry1;
    private javax.swing.JLabel invoice1;
    private javax.swing.JTable itablel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField manufature1;
    private javax.swing.JTextField mrp1;
    private javax.swing.JTextField name1;
    private javax.swing.JTextField pack1;
    private javax.swing.JTextField pid1;
    private javax.swing.JTextField quant1;
    private javax.swing.JTextField sch1;
    private javax.swing.JComboBox sq1;
    private javax.swing.JTextField tax1;
    private javax.swing.JLabel totalamount1;
    private javax.swing.JTextField type1;
    // End of variables declaration//GEN-END:variables
}
