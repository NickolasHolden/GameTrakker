import com.sun.glass.events.KeyEvent;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
import java.sql.*;
import java.util.Arrays;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author robwo
 */
public class frm_LoginPage extends javax.swing.JFrame {

    private int playerId;
    
    /**
     * Creates new form frm_LoginPage
     */
    public frm_LoginPage() {
        initComponents();
        setIcon();
        txt_userName.setText("");
        txt_userName.requestFocus();
        password_password.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_Login = new javax.swing.JPanel();
        lbl_Login_Username = new javax.swing.JLabel();
        lbl_Login_Passwprd = new javax.swing.JLabel();
        btn_Login = new javax.swing.JButton();
        btn_Exit = new javax.swing.JButton();
        txt_userName = new javax.swing.JTextField();
        lbl_Login_Title = new javax.swing.JLabel();
        password_password = new javax.swing.JPasswordField();
        btn_Login_Register = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        menu_Login = new javax.swing.JMenuBar();
        menu_Login_File = new javax.swing.JMenu();
        menu_Login_seperator1 = new javax.swing.JPopupMenu.Separator();
        menu_Exit = new javax.swing.JMenuItem();
        menu_Help = new javax.swing.JMenu();
        menu_About = new javax.swing.JMenuItem();
        menu_Help_Howto = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gametrakker - Login Page");
        setResizable(false);

        pnl_Login.setBackground(new java.awt.Color(0, 41, 60));

        lbl_Login_Username.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_Login_Username.setForeground(new java.awt.Color(246, 42, 0));
        lbl_Login_Username.setText("Username");

        lbl_Login_Passwprd.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_Login_Passwprd.setForeground(new java.awt.Color(246, 42, 0));
        lbl_Login_Passwprd.setText("Password");

        btn_Login.setBackground(new java.awt.Color(246, 42, 0));
        btn_Login.setFont(new java.awt.Font("Tahoma", 3, 22)); // NOI18N
        btn_Login.setForeground(new java.awt.Color(241, 243, 206));
        btn_Login.setText("Login");
        btn_Login.setName(""); // NOI18N
        btn_Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LoginActionPerformed(evt);
            }
        });

        btn_Exit.setBackground(new java.awt.Color(246, 42, 0));
        btn_Exit.setFont(new java.awt.Font("Tahoma", 3, 22)); // NOI18N
        btn_Exit.setForeground(new java.awt.Color(241, 243, 206));
        btn_Exit.setText("Exit");
        btn_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ExitActionPerformed(evt);
            }
        });

        txt_userName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt_userName.setText("uName");

        lbl_Login_Title.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        lbl_Login_Title.setForeground(new java.awt.Color(246, 42, 0));
        lbl_Login_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Login_Title.setText("Login");

        password_password.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        password_password.setText("jPasswordField1");
        password_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                password_passwordKeyPressed(evt);
            }
        });

        btn_Login_Register.setBackground(new java.awt.Color(246, 42, 0));
        btn_Login_Register.setFont(new java.awt.Font("Tahoma", 3, 22)); // NOI18N
        btn_Login_Register.setForeground(new java.awt.Color(241, 243, 206));
        btn_Login_Register.setText("Register");
        btn_Login_Register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Login_RegisterActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/radarlogo2.png"))); // NOI18N
        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout pnl_LoginLayout = new javax.swing.GroupLayout(pnl_Login);
        pnl_Login.setLayout(pnl_LoginLayout);
        pnl_LoginLayout.setHorizontalGroup(
            pnl_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_LoginLayout.createSequentialGroup()
                .addGroup(pnl_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_LoginLayout.createSequentialGroup()
                        .addGap(2, 37, Short.MAX_VALUE)
                        .addGroup(pnl_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_LoginLayout.createSequentialGroup()
                                .addComponent(lbl_Login_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_userName, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_LoginLayout.createSequentialGroup()
                                .addComponent(lbl_Login_Passwprd, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(password_password, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_LoginLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnl_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_LoginLayout.createSequentialGroup()
                                .addComponent(btn_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(btn_Login_Register, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbl_Login_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(23, 23, 23)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_LoginLayout.setVerticalGroup(
            pnl_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_LoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Login_Title, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(pnl_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Login_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_LoginLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txt_userName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39)
                .addGroup(pnl_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Login_Passwprd, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_LoginLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(password_password, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnl_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_Login_Register, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel1.setBackground(new java.awt.Color(0, 41, 60));

        menu_Login_File.setText("File");
        menu_Login_File.add(menu_Login_seperator1);

        menu_Exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        menu_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/trash, bin1.png"))); // NOI18N
        menu_Exit.setText("Exit");
        menu_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_ExitActionPerformed(evt);
            }
        });
        menu_Login_File.add(menu_Exit);

        menu_Login.add(menu_Login_File);

        menu_Help.setText("Help");

        menu_About.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        menu_About.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/radarlogoIcon.png"))); // NOI18N
        menu_About.setText("About");
        menu_About.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_AboutActionPerformed(evt);
            }
        });
        menu_Help.add(menu_About);

        menu_Help_Howto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/messages2small.png"))); // NOI18N
        menu_Help_Howto.setText("Help");
        menu_Help_Howto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_Help_HowtoActionPerformed(evt);
            }
        });
        menu_Help.add(menu_Help_Howto);

        menu_Login.add(menu_Help);

        setJMenuBar(menu_Login);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_Login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_Login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * This method creates and displays the About Form when the About Menu Item 
     * is clicked.
     * @param evt 
     */
    private void menu_AboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_AboutActionPerformed
        new frm_About().setVisible(true); 
    }//GEN-LAST:event_menu_AboutActionPerformed

    /**
     * This method exits the program when the Exit Menu Item is clicked.
     * @param evt 
     */
    private void menu_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_ExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menu_ExitActionPerformed

    /**
     * This method creates and displays the PlayerInfo form when the Register
     * button is clicked.
     * @param evt 
     */
    private void btn_Login_RegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Login_RegisterActionPerformed
        new frm_PlayerRegistration().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_Login_RegisterActionPerformed

    /**
     * This method exits the program when the Exit button is clicked.
     * @param evt 
     */
    private void btn_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_ExitActionPerformed

    /**
     * This method logs the user in when the Login button is clicked.
     * User must have an account and enter the correct login credentials
     * to be logged in.
     * @param evt 
     */
    private void btn_LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LoginActionPerformed
        // Connect to database and verify login credentials
        try{
            String valid = "";
            // Connect to the database
            DBConnect con = new DBConnect();
            // SQL query statement
            ResultSet rs = con.getStatement().executeQuery("SELECT [PlayerId]" +
                ", [Username], [Password] FROM LoginInfo");

            // Validate the username and password
            while(rs.next()){
                /* If the username and password are incorrect then skip through
                the loop */
                if(!rs.getString(2).equals(txt_userName.getText()) ||
                    !Arrays.equals(rs.getString(3).toCharArray(),
                        password_password.getPassword())){
                    continue;
                }
                // If username and password are correct then log in
                playerId = Integer.parseInt(rs.getString(1));
                valid = "Login Successful";

                // Create and display Main Form
                new frm_Main(playerId).setVisible(true);

                // Close database connection and dispose of this form
                con.getConnection().close();
                this.dispose();
            }
            // If username and password are not valid then print invalid
            if(valid.equals("")){
                JOptionPane.showMessageDialog(null, "Incorrect Username or Password!!");
                txt_userName.setText("");
                password_password.setText("");
                txt_userName.requestFocus();
            }
        }catch(Exception ex){ // Print error is there is a problem with database
            System.out.println("Error: " + ex);
        }
    }//GEN-LAST:event_btn_LoginActionPerformed

    /**
     * This method logs the user in when the Enter key is clicked.
     * User must have an account and enter the correct login credentials
     * to be logged in.
     * @param evt 
     */
    private void password_passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_password_passwordKeyPressed
        // Connect to the database and verify login credentials when Enter is pressed
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            try{
            String valid = "";
            // Connect to the database
            DBConnect con = new DBConnect();
            // SQL query statement
            ResultSet rs = con.getStatement().executeQuery("SELECT [PlayerId]" +
                ", [Username], [Password] FROM LoginInfo");

            // Validate the username and password
            while(rs.next()){
                /* If the username and password are incorrect then skip through
                the loop */
                if(!rs.getString(2).equals(txt_userName.getText()) ||
                    !Arrays.equals(rs.getString(3).toCharArray(),
                        password_password.getPassword())){
                    continue;
                }
                // If username and password are correct then log in
                playerId = Integer.parseInt(rs.getString(1));
                valid = "Login Successful";

                // Create and display Main Form
                new frm_Main(playerId).setVisible(true);

                // Close database connection and dispose of this form
                con.getConnection().close();
                this.dispose();
            }
            // If username and password are not valid then print invalid
            if(valid.equals("")){
                JOptionPane.showMessageDialog(null, "Incorrect Username or Password!!");
                txt_userName.setText("");
                password_password.setText("");
                txt_userName.requestFocus();
            }
        }catch(Exception ex){ // Print error is there is a problem with database
            System.out.println("Error: " + ex);
        }
        
        }
    }//GEN-LAST:event_password_passwordKeyPressed

    private void menu_Help_HowtoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_Help_HowtoActionPerformed
        // TODO add your handling code here:
        try{
            //            String URL = "Howto/GameTrakker_HowTo.html";
            //            java.awt.Desktop.getDesktop().browse(java.net.URI.create(URL));

            Desktop.getDesktop().open(new File("Howto\\GameTrakker_HowTo.html"));

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

    }//GEN-LAST:event_menu_Help_HowtoActionPerformed

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
            java.util.logging.Logger.getLogger(frm_LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_LoginPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Exit;
    private javax.swing.JButton btn_Login;
    private javax.swing.JButton btn_Login_Register;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbl_Login_Passwprd;
    private javax.swing.JLabel lbl_Login_Title;
    private javax.swing.JLabel lbl_Login_Username;
    private javax.swing.JMenuItem menu_About;
    private javax.swing.JMenuItem menu_Exit;
    private javax.swing.JMenu menu_Help;
    private javax.swing.JMenuItem menu_Help_Howto;
    private javax.swing.JMenuBar menu_Login;
    private javax.swing.JMenu menu_Login_File;
    private javax.swing.JPopupMenu.Separator menu_Login_seperator1;
    private javax.swing.JPasswordField password_password;
    private javax.swing.JPanel pnl_Login;
    private javax.swing.JTextField txt_userName;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("radarlogoIcon.png")));
    }
}
