
import java.awt.Toolkit;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author robwo
 */
public class frm_BowlingScoreCard extends javax.swing.JFrame {

    private int strikes = 0;
    private int spares = 0;

    /**
     * This method enters a strike in the specified frame and calculates the
     * score when the strike button is clicked.
     *
     * @param frame
     */
    public void Strike(int frame) {
        ArrayList<JTextField> boxes = new ArrayList<>();
        boxes.add(txt_frame1_ball1_Scorecard);
        boxes.add(txt_frame2_ball1_Scorecard);
        boxes.add(txt_frame3_ball1_Scorecard);
        boxes.add(txt_frame4_ball1_Scorecard);
        boxes.add(txt_frame5_ball1_Scorecard);
        boxes.add(txt_frame6_ball1_Scorecard);
        boxes.add(txt_frame7_ball1_Scorecard);
        boxes.add(txt_frame8_ball1_Scorecard);
        boxes.add(txt_frame9_ball1_Scorecard);
        boxes.add(txt_frame10_ball1_Scorecard);
        boxes.add(txt_frame10_ball2_Scorecard);
        boxes.add(txt_frame10_ball3_Scorecard);

        boxes.get(frame).setText("X");
        boxes.get(frame).setEditable(false);
        boxes.get(frame + 1).setEnabled(true);
        boxes.get(frame + 1).setEditable(true);
        boxes.get(frame + 1).requestFocus();
        strikes++;
    }

    /**
     * This method enters a spare in the specified frame and calculates the
     * score when the spare button is clicked.
     */
    public void Spare(int frame) {
        ArrayList<JTextField> boxes = new ArrayList<>();
        boxes.add(txt_frame1_ball2_Scorecard);
        boxes.add(txt_frame2_ball1_Scorecard);
        boxes.add(txt_frame2_ball2_Scorecard);
        boxes.add(txt_frame3_ball1_Scorecard);
        boxes.add(txt_frame3_ball2_Scorecard);
        boxes.add(txt_frame4_ball1_Scorecard);
        boxes.add(txt_frame4_ball2_Scorecard);
        boxes.add(txt_frame5_ball1_Scorecard);
        boxes.add(txt_frame5_ball2_Scorecard);
        boxes.add(txt_frame6_ball1_Scorecard);
        boxes.add(txt_frame6_ball2_Scorecard);
        boxes.add(txt_frame7_ball1_Scorecard);
        boxes.add(txt_frame7_ball2_Scorecard);
        boxes.add(txt_frame8_ball1_Scorecard);
        boxes.add(txt_frame8_ball2_Scorecard);
        boxes.add(txt_frame9_ball1_Scorecard);
        boxes.add(txt_frame9_ball2_Scorecard);
        boxes.add(txt_frame10_ball1_Scorecard);
        boxes.add(txt_frame10_ball2_Scorecard);
        boxes.add(txt_frame10_ball3_Scorecard);

        boxes.get(frame).setText("/");
        boxes.get(frame).setEditable(false);
        boxes.get(frame + 1).setEnabled(true);
        boxes.get(frame + 1).setEditable(true);
        boxes.get(frame + 1).requestFocus();
        spares++;
    }

    /**
     * This method displays the score when the score card is completed
     */
    public void gameFinished() {
        String score = lbl_Frame10_Score.getText();
        lbl_Scorecard_Gamescore.setText(score);
        //lbl_Scorecard_Strikes.setText("" + strikes);
        
        // INSERTING BOWLER AVERAGES -------------------------------------
        try {
            String type = cmb_ScoreCard_Type.getItemAt(cmb_ScoreCard_Type.getSelectedIndex());
            // Select the scores from the Games table
            ResultSet rs = con.getStatement().executeQuery("SELECT TotalScore"
                    + " FROM Games WHERE PlayerId = '" + playerId + "' AND "
                    + "Gametype = '" + type + "'");

            int count = 0;
            int sum = 0;
            // Add all the scores together
            while (rs.next()) {
                sum += Integer.parseInt(rs.getString(1));
                count++;
            }
            // Add in the game that was just played.
            sum += Integer.parseInt(lbl_Frame10_Score.getText());
            count++;
            // Get the average of all the scores
            int average = sum / count;
            // Select the averages from the BowlerAverages table
            ResultSet rs2 = con.getStatement().executeQuery("SELECT PrivateAverage, "
                    + "LeagueAverage, TournamentAverage FROM BowlerAverages "
                    + "WHERE PlayerId = '" + playerId + "'");
            int sumAll = 0;
            int privateAverage = 0;
            int leagueAverage = 0;
            int tournamentAverage = 0;
            // Add all the averages together
            while (rs2.next()) {
                switch (type) {
                    case "Private":
                        privateAverage = average;
                        break;
                    case "League":
                        leagueAverage = average;
                        break;
                    case "Tournament":
                        tournamentAverage = average;
                        break;
                    default:
                    
                        privateAverage = Integer.parseInt(rs2.getString(1));
                        leagueAverage = Integer.parseInt(rs2.getString(2));
                        tournamentAverage = Integer.parseInt(rs2.getString(3));
                        
                
                }
                sumAll += privateAverage;
                sumAll += leagueAverage;
                sumAll += tournamentAverage;
            }
            // Get the overall average
            int averageAll = 0;
            // Only add gametype average if there is an average for that game type
            if (privateAverage > 0 && leagueAverage > 0 && tournamentAverage > 0) {
                averageAll = sumAll / 3;
            } else if (privateAverage > 0 && leagueAverage > 0) {
                averageAll = sumAll / 2;
            } else if (privateAverage > 0 && tournamentAverage > 0) {
                averageAll = sumAll / 2;
            } else if (leagueAverage > 0 && tournamentAverage > 0) {
                averageAll = sumAll / 2;
            } else {
                averageAll = sumAll;
            }

                lbl_ScoreCard_Info_Avg_After.setText("" + averageAll);
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        }


    /**
     * This method calculates the score for the first frame.
     *
     * @param frame
     * @param nextFrame
     * @param frameAfterThat
     */
    public void calcScoreForFrame(javax.swing.JPanel frame, javax.swing.JPanel nextFrame, javax.swing.JPanel frameAfterThat) { //frame 1's score calculation function
        //Takes score from 3 rolls and calculate frame scores
        
        try {
            int score = 0;
            String roll1 = ((javax.swing.JTextField) frame.getComponent(0)).getText();
            String roll2 = ((javax.swing.JTextField) frame.getComponent(1)).getText();
            String roll3; //just declare, initialize later
            if ((roll1.equals("X")) || (roll1.equals("x"))) {
                score = 10;
                roll2 = ((javax.swing.JTextField) nextFrame.getComponent(0)).getText();
                if (roll2.equals("X")) {
                    score += 10;
                    roll3 = ((javax.swing.JTextField) frameAfterThat.getComponent(0)).getText();
                    if (roll3.equals("X")) {
                        score += 10;
                    } else {
                        score += Integer.parseInt(roll3);
                    }
                } else {
                    roll3 = ((javax.swing.JTextField) nextFrame.getComponent(1)).getText();
                    if (roll3.equals("/")) {
                        score += 10;
                    } else {
                        score += Integer.parseInt(roll2) + Integer.parseInt(roll3);
                    }
                }
            } else if (roll2.equals("/")) {
                score += 10;
                roll3 = ((javax.swing.JTextField) nextFrame.getComponent(0)).getText();
                if (roll3.equals("X")) {
                    score += 10;
                } else {
                    score += Integer.parseInt(roll3);
                }
            } else {
                score = Integer.parseInt(roll1) + Integer.parseInt(roll2);
            }
            ((javax.swing.JLabel) frame.getComponent(2)).setText(score + "");
        } catch (Exception e) {
        }
    }

    /**
     * This method calculates the score for frames 2 through 8.
     *
     * @param frame
     * @param nextFrame
     * @param frameAfterThat
     * @param previousFrame
     */
    public void calcScoreForFrame(javax.swing.JPanel frame, javax.swing.JPanel nextFrame, javax.swing.JPanel frameAfterThat, javax.swing.JPanel previousFrame) { //frame 2-8's score calculation function
        try {
            int score = 0;
            int previousScore = Integer.parseInt(((javax.swing.JLabel) previousFrame.getComponent(2)).getText());
            String roll1 = ((javax.swing.JTextField) frame.getComponent(0)).getText();
            String roll2 = ((javax.swing.JTextField) frame.getComponent(1)).getText();
            String roll3; //just declare, initialize later
            if (roll1.equals("X")) {
                score = 10;
                roll2 = ((javax.swing.JTextField) nextFrame.getComponent(0)).getText();
                if (roll2.equals("X")) {
                    score += 10;
                    roll3 = ((javax.swing.JTextField) frameAfterThat.getComponent(0)).getText();
                    if (roll3.equals("X")) {
                        score += 10;
                    } else {
                        score += Integer.parseInt(roll3);
                    }
                } else {
                    roll3 = ((javax.swing.JTextField) nextFrame.getComponent(1)).getText();
                    if (roll3.equals("/")) {
                        score += 10;
                    } else {
                        score += Integer.parseInt(roll2) + Integer.parseInt(roll3);
                    }
                }
            } else if (roll2.equals("/")) {
                score += 10;
                roll3 = ((javax.swing.JTextField) nextFrame.getComponent(0)).getText();
                if (roll3.equals("X")) {
                    score += 10;
                } else {
                    score += Integer.parseInt(roll3);
                }
            } else {
                score = Integer.parseInt(roll1) + Integer.parseInt(roll2);
            }
            ((javax.swing.JLabel) frame.getComponent(2)).setText((previousScore + score) + "");
        } catch (Exception e) {
        }
    }

    /**
     * This method calculates the score for frame 9.
     *
     * @param frame
     * @param nextFrame
     * @param previousFrame
     */
    public void calcScoreForFrameNine(javax.swing.JPanel frame, javax.swing.JPanel nextFrame, javax.swing.JPanel previousFrame) { //frame 9's score calculation function
        try {
            int score = 0;
            int previousScore = Integer.parseInt(((javax.swing.JLabel) previousFrame.getComponent(2)).getText());
            String roll1 = ((javax.swing.JTextField) frame.getComponent(0)).getText();
            String roll2 = ((javax.swing.JTextField) frame.getComponent(1)).getText();
            String roll3; //just declare, initialize later
            boolean gotStrikeOrSpare = false;
            if (roll1.equals("X")) {
                score = 10;
                roll2 = ((javax.swing.JTextField) nextFrame.getComponent(0)).getText();
                roll3 = ((javax.swing.JTextField) nextFrame.getComponent(1)).getText();
                if (roll2.equals("X")) {
                    score += 10;
                    gotStrikeOrSpare = true;
                    if (roll3.equals("X")) {
                        score += 10;
                    } else {
                        score += Integer.parseInt(roll3);
                    }
                }
                if (roll3.equals("/")) {
                    score += 10;
                    gotStrikeOrSpare = true;
                }
                if (!gotStrikeOrSpare) {
                    score += Integer.parseInt(roll2) + Integer.parseInt(roll3);
                }
            } else {
                if (roll2.equals("/")) {
                    score += 10;
                    roll3 = ((javax.swing.JTextField) nextFrame.getComponent(0)).getText();
                    if (roll3.equals("X")) {
                        score += 10;
                    } else {
                        score += Integer.parseInt(roll3);
                    }
                } else {
                    score += Integer.parseInt(roll1) + Integer.parseInt(roll2);
                }
            }
            ((javax.swing.JLabel) frame.getComponent(2)).setText((previousScore + score) + "");
        } catch (Exception e) {
        }
    }

    /**
     * This method calculates the score for frame 10.
     *
     * @param frame
     * @param previousFrame
     */
    public void calcScoreForFrameTen(javax.swing.JPanel frame, javax.swing.JPanel previousFrame) { //frame 10's score calculation function
        // Frame 10 needs a new method to calculate right score. Basically the same as normal but with extra rolls
        
        try {
            int score = 0;
            int previousScore = Integer.parseInt(((javax.swing.JLabel) previousFrame.getComponent(2)).getText());
            String roll1 = ((javax.swing.JTextField) frame.getComponent(0)).getText();
            String roll2 = ((javax.swing.JTextField) frame.getComponent(1)).getText();
            String roll3 = ((javax.swing.JTextField) frame.getComponent(2)).getText();
            boolean gotStrikeOrSpare = false;
            if ((roll1.equals("X") && roll3.equals("")) || (roll2.equals("/") && roll3.equals(""))) {
                return;
            }
            if (roll1.equals("X")) {
                score += 10;
                gotStrikeOrSpare = true;
            }
            if (roll2.equals("X")) {
                score += 10;
                gotStrikeOrSpare = true;
            }
            if (roll2.equals("/")) {
                score += 10;
                gotStrikeOrSpare = true;
            }
            if (roll3.equals("X")) {
                score += 10;
                gotStrikeOrSpare = true;
            }
            if (roll3.equals("/")) {
                score += 10;
                gotStrikeOrSpare = true;
            }
            if (!(roll3.equals("X") || roll3.equals("/") || roll3.equals(""))) {
                score += Integer.parseInt(roll3);
            }
            if (!gotStrikeOrSpare) {
                score += Integer.parseInt(roll1) + Integer.parseInt(roll2);
            }
            ((javax.swing.JLabel) frame.getComponent(3)).setText((previousScore + score) + "");
        } catch (Exception e) {
        }
    }

    /**
     * This method checks if the first ball in a frame is a valid score.
     *
     * @param ball
     * @return
     */
    public boolean isValidBall1(String ball) {//will work for all frames
        
        //Error Checking for what is valid 
        if (ball.equals("X")) {
            return true;
        }
        try {
            int numericValue = Integer.parseInt(ball);
            if (numericValue >= 0 && numericValue <= 10) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method checks if the second ball in a frame is a valid score.
     *
     * @param ball1
     * @param ball2
     * @return
     */
    public boolean isValidBall2(String ball1, String ball2) {//will work for frames 1-9, but not frame 10 ball 2
        
                //Error Checking for what is valid 

        if (ball2.equals("/")) {
            return true;
        }
        try {
            int numericValueForBall1 = Integer.parseInt(ball1);
            int numericValueForBall2 = Integer.parseInt(ball2);
            if (numericValueForBall1 + numericValueForBall2 > 10) {
                return false;
            }
            if (numericValueForBall2 >= 0 && numericValueForBall2 <= 10) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method checks if the second ball in frame 10 is a valid score.
     *
     * @param ball1
     * @param ball2
     * @return
     */
    public boolean isValidFrame10Ball2(String ball1, String ball2) {
                //Error Checking for what is valid 

        
        if (ball1.equals("X")) {
            return isValidBall1(ball2);
        } else {
            return isValidBall2(ball1, ball2);
        }
    }

    /**
     * This method checks if the last ball is a valid score.
     *
     * @param ball2
     * @param ball3
     * @return
     */
    public boolean isValidFrame10Ball3(String ball2, String ball3) {
                //Error Checking for what is valid 

        if (ball2.equals("X") || ball2.equals("/")) {
            return isValidBall1(ball3);
        } else {
            return isValidBall2(ball2, ball3);
        }
    }

    private static int playerId;
    private DBConnect con;
    private ResultSet rs2;

    /**
     * Creates new form frm_BowlingScoreCard
     *
     * @param playerId - the playerId used to get player information from the
     * database tables
     */
    public frm_BowlingScoreCard(int playerId) {
        initComponents();
        setIcon();
        currentDate();
        this.playerId = playerId;
        lbl_Scorecard_Gamescore.setText("");
        //lbl_Scorecard_Strikes.setText("");

        // Connect to the database and display user's name
        try {
            // Connect to the database
            con = new DBConnect();
            // Select playerId
            ResultSet rs = con.getStatement().executeQuery("SELECT * FROM "
                    + "PlayerInfo WHERE [PlayerId] = " + playerId);
            // Select player's overall average
            ResultSet rs2 = con.getStatement().executeQuery("SELECT [Overall Average] "
                    + "FROM BowlerAverages WHERE PlayerId = '" + playerId + "'");

            // Display user's full name
            while (rs.next()) {
                lbl_Scorecard_PlayerName.setText(rs.getString(2) + " "
                        + rs.getString(3));
                lbl_ScoreCard_Info_Namelabel.setText(rs.getString(2) + " "
                        + rs.getString(3));
                lbl_ScoreCard_Info_PlayerID.setText(rs.getString(1));
            }

            // Display the bowler's average
            while (rs2.next()) {
                txt_hdcScorecard.setText(rs2.getString(1));
                lbl_ScoreCard_Info_Avg_before.setText(rs2.getString(1));
            }
        } catch (Exception e) { // Print error is there is a problem with database
            System.out.println("ERROR:" + e);
        }
    }

    public void currentDate() {
        Calendar cal = new GregorianCalendar();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        //String date = (month + ""); 
        //dp_ScoreCard_Options_Date.setDate(date);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jPanel2 = new javax.swing.JPanel();
        lbl_score_Scorecard = new javax.swing.JLabel();
        lbl_strikes_Scorecard = new javax.swing.JLabel();
        lbl_spares_Scorecard = new javax.swing.JLabel();
        lbl_Score = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_playerName_Scorecard = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        pnl_ScoreCard = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        txt_hdcScorecard = new javax.swing.JLabel();
        lbl_frame1 = new javax.swing.JLabel();
        lbl_frame2 = new javax.swing.JLabel();
        lbl_Frame3 = new javax.swing.JLabel();
        lbl_frame4 = new javax.swing.JLabel();
        lbl_frame5 = new javax.swing.JLabel();
        lbl_frame6 = new javax.swing.JLabel();
        lbl_frame7 = new javax.swing.JLabel();
        lbl_frame8 = new javax.swing.JLabel();
        lbl_frame9 = new javax.swing.JLabel();
        pnl_frame3_Scorecard = new javax.swing.JPanel();
        txt_frame3_ball1_Scorecard = new javax.swing.JTextField();
        txt_frame3_ball2_Scorecard = new javax.swing.JTextField();
        lbl_Frame3_Score = new javax.swing.JLabel();
        pnl_frame4_Scorecard = new javax.swing.JPanel();
        txt_frame4_ball1_Scorecard = new javax.swing.JTextField();
        txt_frame4_ball2_Scorecard = new javax.swing.JTextField();
        lbl_Frame4_Score = new javax.swing.JLabel();
        pnl_frame5_Scorecard = new javax.swing.JPanel();
        txt_frame5_ball1_Scorecard = new javax.swing.JTextField();
        txt_frame5_ball2_Scorecard = new javax.swing.JTextField();
        lbl_Frame5_Score = new javax.swing.JLabel();
        pnl_frame1_Scorecard = new javax.swing.JPanel();
        txt_frame1_ball1_Scorecard = new javax.swing.JTextField();
        txt_frame1_ball2_Scorecard = new javax.swing.JTextField();
        lbl_Frame1_Score = new javax.swing.JLabel();
        pnl_frame2_Scorecard = new javax.swing.JPanel();
        txt_frame2_ball1_Scorecard = new javax.swing.JTextField();
        txt_frame2_ball2_Scorecard = new javax.swing.JTextField();
        lbl_Frame2_Score = new javax.swing.JLabel();
        pnl_frame6_Scorecard = new javax.swing.JPanel();
        txt_frame6_ball1_Scorecard = new javax.swing.JTextField();
        txt_frame6_ball2_Scorecard = new javax.swing.JTextField();
        lbl_Frame6_Score = new javax.swing.JLabel();
        pnl_frame7_Scorecard = new javax.swing.JPanel();
        txt_frame7_ball1_Scorecard = new javax.swing.JTextField();
        txt_frame7_ball2_Scorecard = new javax.swing.JTextField();
        lbl_Frame7_Score = new javax.swing.JLabel();
        pnl_frame8_Scorecard = new javax.swing.JPanel();
        txt_frame8_ball1_Scorecard = new javax.swing.JTextField();
        txt_frame8_ball2_Scorecard = new javax.swing.JTextField();
        lbl_Frame8_Score = new javax.swing.JLabel();
        pnl_frame9_Scorecard = new javax.swing.JPanel();
        txt_frame9_ball1_Scorecard = new javax.swing.JTextField();
        txt_frame9_ball2_Scorecard = new javax.swing.JTextField();
        lbl_Frame9_Score = new javax.swing.JLabel();
        lbl_Scorecard_PlayerName = new javax.swing.JLabel();
        lbl_frame10 = new javax.swing.JLabel();
        pnl_frame10_Scorecard = new javax.swing.JPanel();
        txt_frame10_ball1_Scorecard = new javax.swing.JTextField();
        txt_frame10_ball2_Scorecard = new javax.swing.JTextField();
        txt_frame10_ball3_Scorecard = new javax.swing.JTextField();
        lbl_Frame10_Score = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btn_ScoreCard_Frame1_Strike = new javax.swing.JButton();
        btn_ScoreCard_Frame1_Spare = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        btn_Done = new javax.swing.JButton();
        btn_Clear = new javax.swing.JButton();
        btn_EnterScore = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lbl_Scorecard_GamscoreLBL = new javax.swing.JLabel();
        lbl_Scorecard_Gamescore = new javax.swing.JLabel();
        lbl_ScoreCard_Info_Title = new javax.swing.JLabel();
        lbl_ScoreCard_Info_Playerlb = new javax.swing.JLabel();
        lbl_ScoreCard_Info_Namelabel = new javax.swing.JLabel();
        lbl_ScoreCard_Info_PlayeridLabel = new javax.swing.JLabel();
        lbl_ScoreCard_Info_PlayerID = new javax.swing.JLabel();
        lbl_ScoreCard_Info_Avg_Afterlabel = new javax.swing.JLabel();
        lbl_ScoreCard_Info_Avg_After = new javax.swing.JLabel();
        lbl_ScoreCard_Info_AVGBefore = new javax.swing.JLabel();
        lbl_ScoreCard_Info_Avg_before = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cmb_ScoreCard_Type = new javax.swing.JComboBox<>();
        lbl_ScoreCard_Options_TypeGame_label = new javax.swing.JLabel();
        lbl_ScoreCard_Options_Title = new javax.swing.JLabel();
        dp_ScoreCard_Options_Date = new org.jdesktop.swingx.JXDatePicker();
        lbl_ScoreCard_Options_Date_Label = new javax.swing.JLabel();
        lbl_ScoreCard_Options_GameName_Label = new javax.swing.JLabel();
        txt_ScoreCard_Options_gameName = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        menu_Main = new javax.swing.JMenuBar();
        menu_main_File = new javax.swing.JMenu();
        menu_File_Seperator1 = new javax.swing.JPopupMenu.Separator();
        menu_File_Admin = new javax.swing.JMenuItem();
        menu_Delete = new javax.swing.JMenuItem();
        menu_Logout = new javax.swing.JMenuItem();
        menu_Menu_File_Exit = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        menu_Menu_Player_ViewScores = new javax.swing.JMenuItem();
        menu_MainMenu_Player_scorecard = new javax.swing.JMenuItem();
        menu_Menu_Player_EnterScores = new javax.swing.JMenuItem();
        menu_Tournamants = new javax.swing.JMenuItem();
        menu_Leagues = new javax.swing.JMenuItem();
        menu_UpdateInfo = new javax.swing.JMenuItem();
        menu_main_Help = new javax.swing.JMenu();
        menu_About = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menu_Date = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        lbl_score_Scorecard.setFont(new java.awt.Font("Elephant", 1, 18)); // NOI18N
        lbl_score_Scorecard.setText("Score = ");
        lbl_score_Scorecard.setPreferredSize(new java.awt.Dimension(54, 16));

        lbl_strikes_Scorecard.setFont(new java.awt.Font("Elephant", 1, 18)); // NOI18N
        lbl_strikes_Scorecard.setText("Strikes = ");

        lbl_spares_Scorecard.setFont(new java.awt.Font("Elephant", 1, 18)); // NOI18N
        lbl_spares_Scorecard.setText("Spares = ");

        lbl_Score.setFont(new java.awt.Font("Elephant", 1, 18)); // NOI18N
        lbl_Score.setText("jLabel4");

        jLabel5.setFont(new java.awt.Font("Elephant", 1, 18)); // NOI18N
        jLabel5.setText("jLabel5");

        jLabel6.setFont(new java.awt.Font("Elephant", 1, 18)); // NOI18N
        jLabel6.setText("jLabel6");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_spares_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_strikes_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_score_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_Score, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(175, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_score_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_strikes_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_spares_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lbl_playerName_Scorecard.setForeground(new java.awt.Color(241, 243, 206));
        lbl_playerName_Scorecard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_playerName_Scorecard.setText("PlayerName");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bowling ScoreCard");
        setResizable(false);

        pnl_ScoreCard.setBackground(new java.awt.Color(0, 41, 60));
        pnl_ScoreCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 0)));
        pnl_ScoreCard.setForeground(new java.awt.Color(241, 243, 206));

        jSeparator2.setBackground(new java.awt.Color(242, 133, 0));

        jPanel1.setBackground(new java.awt.Color(0, 41, 60));

        txt_hdcScorecard.setForeground(new java.awt.Color(241, 243, 206));
        txt_hdcScorecard.setText("Average");

        lbl_frame1.setBackground(new java.awt.Color(241, 243, 206));
        lbl_frame1.setForeground(new java.awt.Color(241, 243, 206));
        lbl_frame1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_frame1.setText("Frame 1");

        lbl_frame2.setBackground(new java.awt.Color(241, 243, 206));
        lbl_frame2.setForeground(new java.awt.Color(241, 243, 206));
        lbl_frame2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_frame2.setText("Frame 2");

        lbl_Frame3.setBackground(new java.awt.Color(241, 243, 206));
        lbl_Frame3.setForeground(new java.awt.Color(241, 243, 206));
        lbl_Frame3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Frame3.setText("Frame 3");

        lbl_frame4.setBackground(new java.awt.Color(241, 243, 206));
        lbl_frame4.setForeground(new java.awt.Color(241, 243, 206));
        lbl_frame4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_frame4.setText("Frame 4");

        lbl_frame5.setBackground(new java.awt.Color(241, 243, 206));
        lbl_frame5.setForeground(new java.awt.Color(241, 243, 206));
        lbl_frame5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_frame5.setText("Frame 5");

        lbl_frame6.setBackground(new java.awt.Color(241, 243, 206));
        lbl_frame6.setForeground(new java.awt.Color(241, 243, 206));
        lbl_frame6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_frame6.setText("Frame 6");

        lbl_frame7.setForeground(new java.awt.Color(241, 243, 206));
        lbl_frame7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_frame7.setText("Frame 7");

        lbl_frame8.setForeground(new java.awt.Color(241, 243, 206));
        lbl_frame8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_frame8.setText("Frame 8");

        lbl_frame9.setForeground(new java.awt.Color(241, 243, 206));
        lbl_frame9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_frame9.setText("Frame 9");

        pnl_frame3_Scorecard.setBackground(new java.awt.Color(242, 133, 0));
        pnl_frame3_Scorecard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_frame3_ball1_Scorecard.setEditable(false);
        txt_frame3_ball1_Scorecard.setEnabled(false);
        txt_frame3_ball1_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame3_ball1_ScorecardActionPerformed(evt);
            }
        });

        txt_frame3_ball2_Scorecard.setEditable(false);
        txt_frame3_ball2_Scorecard.setEnabled(false);
        txt_frame3_ball2_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame3_ball2_ScorecardActionPerformed(evt);
            }
        });

        lbl_Frame3_Score.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Frame3_Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnl_frame3_ScorecardLayout = new javax.swing.GroupLayout(pnl_frame3_Scorecard);
        pnl_frame3_Scorecard.setLayout(pnl_frame3_ScorecardLayout);
        pnl_frame3_ScorecardLayout.setHorizontalGroup(
            pnl_frame3_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame3_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame3_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_frame3_ScorecardLayout.createSequentialGroup()
                        .addGap(0, 27, Short.MAX_VALUE)
                        .addComponent(txt_frame3_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_frame3_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_Frame3_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_frame3_ScorecardLayout.setVerticalGroup(
            pnl_frame3_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame3_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame3_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_frame3_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_frame3_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbl_Frame3_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_frame4_Scorecard.setBackground(new java.awt.Color(242, 133, 0));
        pnl_frame4_Scorecard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_frame4_ball1_Scorecard.setEditable(false);
        txt_frame4_ball1_Scorecard.setEnabled(false);
        txt_frame4_ball1_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame4_ball1_ScorecardActionPerformed(evt);
            }
        });

        txt_frame4_ball2_Scorecard.setEditable(false);
        txt_frame4_ball2_Scorecard.setEnabled(false);
        txt_frame4_ball2_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame4_ball2_ScorecardActionPerformed(evt);
            }
        });

        lbl_Frame4_Score.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Frame4_Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnl_frame4_ScorecardLayout = new javax.swing.GroupLayout(pnl_frame4_Scorecard);
        pnl_frame4_Scorecard.setLayout(pnl_frame4_ScorecardLayout);
        pnl_frame4_ScorecardLayout.setHorizontalGroup(
            pnl_frame4_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame4_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame4_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_frame4_ScorecardLayout.createSequentialGroup()
                        .addGap(0, 27, Short.MAX_VALUE)
                        .addComponent(txt_frame4_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_frame4_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_Frame4_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_frame4_ScorecardLayout.setVerticalGroup(
            pnl_frame4_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame4_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame4_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_frame4_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_frame4_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbl_Frame4_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_frame5_Scorecard.setBackground(new java.awt.Color(242, 133, 0));
        pnl_frame5_Scorecard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_frame5_ball1_Scorecard.setEditable(false);
        txt_frame5_ball1_Scorecard.setEnabled(false);
        txt_frame5_ball1_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame5_ball1_ScorecardActionPerformed(evt);
            }
        });

        txt_frame5_ball2_Scorecard.setEditable(false);
        txt_frame5_ball2_Scorecard.setEnabled(false);
        txt_frame5_ball2_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame5_ball2_ScorecardActionPerformed(evt);
            }
        });

        lbl_Frame5_Score.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Frame5_Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnl_frame5_ScorecardLayout = new javax.swing.GroupLayout(pnl_frame5_Scorecard);
        pnl_frame5_Scorecard.setLayout(pnl_frame5_ScorecardLayout);
        pnl_frame5_ScorecardLayout.setHorizontalGroup(
            pnl_frame5_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame5_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame5_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_frame5_ScorecardLayout.createSequentialGroup()
                        .addGap(0, 27, Short.MAX_VALUE)
                        .addComponent(txt_frame5_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_frame5_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_Frame5_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_frame5_ScorecardLayout.setVerticalGroup(
            pnl_frame5_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame5_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame5_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_frame5_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_frame5_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbl_Frame5_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_frame1_Scorecard.setBackground(new java.awt.Color(242, 133, 0));
        pnl_frame1_Scorecard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_frame1_ball1_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame1_ball1_ScorecardActionPerformed(evt);
            }
        });

        txt_frame1_ball2_Scorecard.setEditable(false);
        txt_frame1_ball2_Scorecard.setEnabled(false);
        txt_frame1_ball2_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame1_ball2_ScorecardActionPerformed(evt);
            }
        });

        lbl_Frame1_Score.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Frame1_Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnl_frame1_ScorecardLayout = new javax.swing.GroupLayout(pnl_frame1_Scorecard);
        pnl_frame1_Scorecard.setLayout(pnl_frame1_ScorecardLayout);
        pnl_frame1_ScorecardLayout.setHorizontalGroup(
            pnl_frame1_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame1_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame1_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_frame1_ScorecardLayout.createSequentialGroup()
                        .addGap(0, 27, Short.MAX_VALUE)
                        .addComponent(txt_frame1_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_frame1_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_Frame1_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_frame1_ScorecardLayout.setVerticalGroup(
            pnl_frame1_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame1_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame1_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_frame1_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_frame1_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_Frame1_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_frame2_Scorecard.setBackground(new java.awt.Color(242, 133, 0));
        pnl_frame2_Scorecard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_frame2_ball1_Scorecard.setEditable(false);
        txt_frame2_ball1_Scorecard.setEnabled(false);
        txt_frame2_ball1_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame2_ball1_ScorecardActionPerformed(evt);
            }
        });

        txt_frame2_ball2_Scorecard.setEditable(false);
        txt_frame2_ball2_Scorecard.setEnabled(false);
        txt_frame2_ball2_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame2_ball2_ScorecardActionPerformed(evt);
            }
        });

        lbl_Frame2_Score.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Frame2_Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnl_frame2_ScorecardLayout = new javax.swing.GroupLayout(pnl_frame2_Scorecard);
        pnl_frame2_Scorecard.setLayout(pnl_frame2_ScorecardLayout);
        pnl_frame2_ScorecardLayout.setHorizontalGroup(
            pnl_frame2_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame2_ScorecardLayout.createSequentialGroup()
                .addGroup(pnl_frame2_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_frame2_ScorecardLayout.createSequentialGroup()
                        .addContainerGap(37, Short.MAX_VALUE)
                        .addComponent(txt_frame2_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_frame2_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_Frame2_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_frame2_ScorecardLayout.setVerticalGroup(
            pnl_frame2_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame2_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame2_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_frame2_ball1_Scorecard)
                    .addComponent(txt_frame2_ball2_Scorecard))
                .addGap(14, 14, 14)
                .addComponent(lbl_Frame2_Score, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnl_frame6_Scorecard.setBackground(new java.awt.Color(242, 133, 0));
        pnl_frame6_Scorecard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnl_frame6_Scorecard.setPreferredSize(new java.awt.Dimension(112, 76));

        txt_frame6_ball1_Scorecard.setEditable(false);
        txt_frame6_ball1_Scorecard.setEnabled(false);
        txt_frame6_ball1_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame6_ball1_ScorecardActionPerformed(evt);
            }
        });

        txt_frame6_ball2_Scorecard.setEditable(false);
        txt_frame6_ball2_Scorecard.setEnabled(false);
        txt_frame6_ball2_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame6_ball2_ScorecardActionPerformed(evt);
            }
        });

        lbl_Frame6_Score.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Frame6_Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnl_frame6_ScorecardLayout = new javax.swing.GroupLayout(pnl_frame6_Scorecard);
        pnl_frame6_Scorecard.setLayout(pnl_frame6_ScorecardLayout);
        pnl_frame6_ScorecardLayout.setHorizontalGroup(
            pnl_frame6_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame6_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame6_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_frame6_ScorecardLayout.createSequentialGroup()
                        .addGap(0, 27, Short.MAX_VALUE)
                        .addComponent(txt_frame6_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_frame6_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_Frame6_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_frame6_ScorecardLayout.setVerticalGroup(
            pnl_frame6_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame6_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame6_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_frame6_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_frame6_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbl_Frame6_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_frame7_Scorecard.setBackground(new java.awt.Color(242, 133, 0));
        pnl_frame7_Scorecard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_frame7_ball1_Scorecard.setEditable(false);
        txt_frame7_ball1_Scorecard.setEnabled(false);
        txt_frame7_ball1_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame7_ball1_ScorecardActionPerformed(evt);
            }
        });

        txt_frame7_ball2_Scorecard.setEditable(false);
        txt_frame7_ball2_Scorecard.setEnabled(false);
        txt_frame7_ball2_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame7_ball2_ScorecardActionPerformed(evt);
            }
        });

        lbl_Frame7_Score.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Frame7_Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnl_frame7_ScorecardLayout = new javax.swing.GroupLayout(pnl_frame7_Scorecard);
        pnl_frame7_Scorecard.setLayout(pnl_frame7_ScorecardLayout);
        pnl_frame7_ScorecardLayout.setHorizontalGroup(
            pnl_frame7_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame7_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame7_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_frame7_ScorecardLayout.createSequentialGroup()
                        .addGap(0, 27, Short.MAX_VALUE)
                        .addComponent(txt_frame7_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_frame7_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_Frame7_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_frame7_ScorecardLayout.setVerticalGroup(
            pnl_frame7_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame7_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame7_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_frame7_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_frame7_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbl_Frame7_Score, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnl_frame8_Scorecard.setBackground(new java.awt.Color(242, 133, 0));
        pnl_frame8_Scorecard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_frame8_ball1_Scorecard.setEditable(false);
        txt_frame8_ball1_Scorecard.setEnabled(false);
        txt_frame8_ball1_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame8_ball1_ScorecardActionPerformed(evt);
            }
        });

        txt_frame8_ball2_Scorecard.setEditable(false);
        txt_frame8_ball2_Scorecard.setEnabled(false);
        txt_frame8_ball2_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame8_ball2_ScorecardActionPerformed(evt);
            }
        });

        lbl_Frame8_Score.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Frame8_Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnl_frame8_ScorecardLayout = new javax.swing.GroupLayout(pnl_frame8_Scorecard);
        pnl_frame8_Scorecard.setLayout(pnl_frame8_ScorecardLayout);
        pnl_frame8_ScorecardLayout.setHorizontalGroup(
            pnl_frame8_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame8_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame8_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_frame8_ScorecardLayout.createSequentialGroup()
                        .addGap(0, 27, Short.MAX_VALUE)
                        .addComponent(txt_frame8_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_frame8_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_Frame8_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_frame8_ScorecardLayout.setVerticalGroup(
            pnl_frame8_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame8_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame8_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_frame8_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_frame8_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbl_Frame8_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_frame9_Scorecard.setBackground(new java.awt.Color(242, 133, 0));
        pnl_frame9_Scorecard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_frame9_ball1_Scorecard.setEditable(false);
        txt_frame9_ball1_Scorecard.setEnabled(false);
        txt_frame9_ball1_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame9_ball1_ScorecardActionPerformed(evt);
            }
        });

        txt_frame9_ball2_Scorecard.setEditable(false);
        txt_frame9_ball2_Scorecard.setEnabled(false);
        txt_frame9_ball2_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame9_ball2_ScorecardActionPerformed(evt);
            }
        });

        lbl_Frame9_Score.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Frame9_Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnl_frame9_ScorecardLayout = new javax.swing.GroupLayout(pnl_frame9_Scorecard);
        pnl_frame9_Scorecard.setLayout(pnl_frame9_ScorecardLayout);
        pnl_frame9_ScorecardLayout.setHorizontalGroup(
            pnl_frame9_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame9_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame9_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_frame9_ScorecardLayout.createSequentialGroup()
                        .addGap(0, 27, Short.MAX_VALUE)
                        .addComponent(txt_frame9_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_frame9_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_Frame9_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_frame9_ScorecardLayout.setVerticalGroup(
            pnl_frame9_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame9_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame9_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_frame9_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_frame9_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbl_Frame9_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        lbl_Scorecard_PlayerName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Scorecard_PlayerName.setForeground(new java.awt.Color(241, 243, 206));
        lbl_Scorecard_PlayerName.setText("PlayerName");

        lbl_frame10.setForeground(new java.awt.Color(241, 243, 206));
        lbl_frame10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_frame10.setText("Frame 10");

        pnl_frame10_Scorecard.setBackground(new java.awt.Color(242, 133, 0));
        pnl_frame10_Scorecard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_frame10_ball1_Scorecard.setEditable(false);
        txt_frame10_ball1_Scorecard.setEnabled(false);
        txt_frame10_ball1_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame10_ball1_ScorecardActionPerformed(evt);
            }
        });

        txt_frame10_ball2_Scorecard.setEditable(false);
        txt_frame10_ball2_Scorecard.setEnabled(false);
        txt_frame10_ball2_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame10_ball2_ScorecardActionPerformed(evt);
            }
        });

        txt_frame10_ball3_Scorecard.setEditable(false);
        txt_frame10_ball3_Scorecard.setEnabled(false);
        txt_frame10_ball3_Scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_frame10_ball3_ScorecardActionPerformed(evt);
            }
        });

        lbl_Frame10_Score.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_Frame10_Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnl_frame10_ScorecardLayout = new javax.swing.GroupLayout(pnl_frame10_Scorecard);
        pnl_frame10_Scorecard.setLayout(pnl_frame10_ScorecardLayout);
        pnl_frame10_ScorecardLayout.setHorizontalGroup(
            pnl_frame10_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame10_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame10_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_frame10_ScorecardLayout.createSequentialGroup()
                        .addGap(0, 20, Short.MAX_VALUE)
                        .addComponent(txt_frame10_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_frame10_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_frame10_ball3_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_Frame10_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_frame10_ScorecardLayout.setVerticalGroup(
            pnl_frame10_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_frame10_ScorecardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_frame10_ScorecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_frame10_ball1_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_frame10_ball3_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_frame10_ball2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbl_Frame10_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(0, 41, 60));

        btn_ScoreCard_Frame1_Strike.setBackground(new java.awt.Color(246, 42, 0));
        btn_ScoreCard_Frame1_Strike.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        btn_ScoreCard_Frame1_Strike.setForeground(new java.awt.Color(241, 243, 206));
        btn_ScoreCard_Frame1_Strike.setText("Strike");
        btn_ScoreCard_Frame1_Strike.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ScoreCard_Frame1_StrikeActionPerformed(evt);
            }
        });

        btn_ScoreCard_Frame1_Spare.setBackground(new java.awt.Color(246, 42, 0));
        btn_ScoreCard_Frame1_Spare.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        btn_ScoreCard_Frame1_Spare.setForeground(new java.awt.Color(241, 243, 206));
        btn_ScoreCard_Frame1_Spare.setText("Spare");
        btn_ScoreCard_Frame1_Spare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ScoreCard_Frame1_SpareActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(237, 237, 237)
                .addComponent(btn_ScoreCard_Frame1_Strike, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(136, 136, 136)
                .addComponent(btn_ScoreCard_Frame1_Spare, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_ScoreCard_Frame1_Spare, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ScoreCard_Frame1_Strike, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_Scorecard_PlayerName)
                    .addComponent(txt_hdcScorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_frame1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_frame2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_Frame3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_frame4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_frame5, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_frame6, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_frame7, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_frame8, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_frame9, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(lbl_frame10, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(pnl_frame1_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnl_frame2_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnl_frame3_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnl_frame4_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnl_frame5_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnl_frame6_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnl_frame7_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnl_frame8_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnl_frame9_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnl_frame10_Scorecard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_frame2)
                    .addComponent(lbl_Frame3)
                    .addComponent(lbl_frame4)
                    .addComponent(lbl_frame5)
                    .addComponent(lbl_frame6)
                    .addComponent(lbl_frame7)
                    .addComponent(lbl_frame8)
                    .addComponent(lbl_frame9)
                    .addComponent(lbl_frame10)
                    .addComponent(lbl_frame1))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(lbl_Scorecard_PlayerName)
                        .addGap(29, 29, 29)
                        .addComponent(txt_hdcScorecard)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pnl_frame9_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_frame10_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_frame8_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_frame7_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_frame6_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                            .addComponent(pnl_frame4_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_frame5_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_frame3_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_frame2_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnl_frame1_Scorecard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel5.setBackground(new java.awt.Color(0, 41, 60));

        btn_Done.setBackground(new java.awt.Color(246, 42, 0));
        btn_Done.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        btn_Done.setForeground(new java.awt.Color(241, 243, 206));
        btn_Done.setText("Done");
        btn_Done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DoneActionPerformed(evt);
            }
        });

        btn_Clear.setBackground(new java.awt.Color(246, 42, 0));
        btn_Clear.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        btn_Clear.setForeground(new java.awt.Color(241, 243, 206));
        btn_Clear.setText("Clear");
        btn_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ClearActionPerformed(evt);
            }
        });

        btn_EnterScore.setBackground(new java.awt.Color(246, 42, 0));
        btn_EnterScore.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        btn_EnterScore.setForeground(new java.awt.Color(241, 243, 206));
        btn_EnterScore.setText("Enter Score");
        btn_EnterScore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EnterScoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_EnterScore, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Clear, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Done, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_EnterScore, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Clear, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Done, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        jPanel4.setBackground(new java.awt.Color(0, 41, 60));

        lbl_Scorecard_GamscoreLBL.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lbl_Scorecard_GamscoreLBL.setForeground(new java.awt.Color(241, 243, 206));
        lbl_Scorecard_GamscoreLBL.setText("Game Score");

        lbl_Scorecard_Gamescore.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_Scorecard_Gamescore.setForeground(new java.awt.Color(241, 243, 206));
        lbl_Scorecard_Gamescore.setText("GAMESCORE");

        lbl_ScoreCard_Info_Title.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lbl_ScoreCard_Info_Title.setForeground(new java.awt.Color(241, 243, 206));
        lbl_ScoreCard_Info_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_ScoreCard_Info_Title.setText("Information");

        lbl_ScoreCard_Info_Playerlb.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lbl_ScoreCard_Info_Playerlb.setForeground(new java.awt.Color(241, 243, 206));
        lbl_ScoreCard_Info_Playerlb.setText("Player");

        lbl_ScoreCard_Info_Namelabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_ScoreCard_Info_Namelabel.setForeground(new java.awt.Color(241, 243, 206));
        lbl_ScoreCard_Info_Namelabel.setText("jLabel9");

        lbl_ScoreCard_Info_PlayeridLabel.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lbl_ScoreCard_Info_PlayeridLabel.setForeground(new java.awt.Color(241, 243, 206));
        lbl_ScoreCard_Info_PlayeridLabel.setText("Player ID");

        lbl_ScoreCard_Info_PlayerID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_ScoreCard_Info_PlayerID.setForeground(new java.awt.Color(241, 243, 206));
        lbl_ScoreCard_Info_PlayerID.setText("jLabel11");

        lbl_ScoreCard_Info_Avg_Afterlabel.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lbl_ScoreCard_Info_Avg_Afterlabel.setForeground(new java.awt.Color(241, 243, 206));
        lbl_ScoreCard_Info_Avg_Afterlabel.setText("Average After");

        lbl_ScoreCard_Info_Avg_After.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_ScoreCard_Info_Avg_After.setForeground(new java.awt.Color(241, 243, 206));
        lbl_ScoreCard_Info_Avg_After.setText("jLabel13");

        lbl_ScoreCard_Info_AVGBefore.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lbl_ScoreCard_Info_AVGBefore.setForeground(new java.awt.Color(241, 243, 206));
        lbl_ScoreCard_Info_AVGBefore.setText("Average Before");

        lbl_ScoreCard_Info_Avg_before.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_ScoreCard_Info_Avg_before.setForeground(new java.awt.Color(241, 243, 206));
        lbl_ScoreCard_Info_Avg_before.setText("jLabel15");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_ScoreCard_Info_Title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lbl_ScoreCard_Info_Playerlb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_ScoreCard_Info_Namelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_ScoreCard_Info_Avg_Afterlabel, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                    .addComponent(lbl_ScoreCard_Info_PlayeridLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(lbl_Scorecard_GamscoreLBL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbl_ScoreCard_Info_AVGBefore, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_ScoreCard_Info_Avg_before, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_Scorecard_Gamescore, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                                    .addComponent(lbl_ScoreCard_Info_Avg_After, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_ScoreCard_Info_PlayerID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(25, 25, 25)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_ScoreCard_Info_Title, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ScoreCard_Info_Playerlb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_ScoreCard_Info_Namelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ScoreCard_Info_PlayeridLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_ScoreCard_Info_PlayerID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ScoreCard_Info_AVGBefore, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_ScoreCard_Info_Avg_before, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ScoreCard_Info_Avg_Afterlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_ScoreCard_Info_Avg_After, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Scorecard_GamscoreLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Scorecard_Gamescore, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96))
        );

        jPanel3.setBackground(new java.awt.Color(0, 41, 60));
        jPanel3.setForeground(new java.awt.Color(0, 41, 60));

        cmb_ScoreCard_Type.setBackground(new java.awt.Color(242, 133, 0));
        cmb_ScoreCard_Type.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cmb_ScoreCard_Type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Private", "League", "Tournament" }));
        cmb_ScoreCard_Type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_ScoreCard_TypeActionPerformed(evt);
            }
        });

        lbl_ScoreCard_Options_TypeGame_label.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lbl_ScoreCard_Options_TypeGame_label.setForeground(new java.awt.Color(241, 243, 206));
        lbl_ScoreCard_Options_TypeGame_label.setText("Type of Game");

        lbl_ScoreCard_Options_Title.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lbl_ScoreCard_Options_Title.setForeground(new java.awt.Color(241, 243, 206));
        lbl_ScoreCard_Options_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_ScoreCard_Options_Title.setText("Options");

        dp_ScoreCard_Options_Date.setBackground(new java.awt.Color(242, 133, 0));
        dp_ScoreCard_Options_Date.setForeground(new java.awt.Color(241, 243, 206));

        lbl_ScoreCard_Options_Date_Label.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lbl_ScoreCard_Options_Date_Label.setForeground(new java.awt.Color(241, 243, 206));
        lbl_ScoreCard_Options_Date_Label.setText("Date");

        lbl_ScoreCard_Options_GameName_Label.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        lbl_ScoreCard_Options_GameName_Label.setForeground(new java.awt.Color(241, 243, 206));
        lbl_ScoreCard_Options_GameName_Label.setText("Game Name");

        txt_ScoreCard_Options_gameName.setBackground(new java.awt.Color(241, 243, 206));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_ScoreCard_Options_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(lbl_ScoreCard_Options_TypeGame_label, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lbl_ScoreCard_Options_GameName_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_ScoreCard_Options_Date_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dp_ScoreCard_Options_Date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmb_ScoreCard_Type, 0, 195, Short.MAX_VALUE)
                            .addComponent(txt_ScoreCard_Options_gameName))
                        .addGap(22, 22, 22)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lbl_ScoreCard_Options_Title, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_ScoreCard_Type, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_ScoreCard_Options_TypeGame_label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ScoreCard_Options_Date_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dp_ScoreCard_Options_Date, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ScoreCard_Options_GameName_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ScoreCard_Options_gameName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(99, Short.MAX_VALUE))
        );

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout pnl_ScoreCardLayout = new javax.swing.GroupLayout(pnl_ScoreCard);
        pnl_ScoreCard.setLayout(pnl_ScoreCardLayout);
        pnl_ScoreCardLayout.setHorizontalGroup(
            pnl_ScoreCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_ScoreCardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_ScoreCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_ScoreCardLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jSeparator2)
        );
        pnl_ScoreCardLayout.setVerticalGroup(
            pnl_ScoreCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_ScoreCardLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_ScoreCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator5)
                    .addGroup(pnl_ScoreCardLayout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnl_ScoreCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_ScoreCardLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );

        menu_Main.setBackground(new java.awt.Color(242, 133, 0));
        menu_Main.setForeground(new java.awt.Color(242, 133, 0));

        menu_main_File.setText("File");
        menu_main_File.setToolTipText("File");
        menu_main_File.add(menu_File_Seperator1);

        menu_File_Admin.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        menu_File_Admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settingssmall.png"))); // NOI18N
        menu_File_Admin.setText("Administration");
        menu_File_Admin.setToolTipText("Under Contruction");
        menu_main_File.add(menu_File_Admin);

        menu_Delete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        menu_Delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/trash, bin1.png"))); // NOI18N
        menu_Delete.setText("Delete Account");
        menu_Delete.setToolTipText("Under Cpntruction");
        menu_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_DeleteActionPerformed(evt);
            }
        });
        menu_main_File.add(menu_Delete);

        menu_Logout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        menu_Logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/warning1.png"))); // NOI18N
        menu_Logout.setText("Logout");
        menu_Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_LogoutActionPerformed(evt);
            }
        });
        menu_main_File.add(menu_Logout);

        menu_Menu_File_Exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        menu_Menu_File_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/powerlittle.png"))); // NOI18N
        menu_Menu_File_Exit.setText("Exit");
        menu_Menu_File_Exit.setToolTipText("Exit System");
        menu_Menu_File_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_Menu_File_ExitActionPerformed(evt);
            }
        });
        menu_main_File.add(menu_Menu_File_Exit);

        menu_Main.add(menu_main_File);

        jMenu1.setText("Menu");

        menu_Menu_Player_ViewScores.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        menu_Menu_Player_ViewScores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stats2.png"))); // NOI18N
        menu_Menu_Player_ViewScores.setText("View Scores");
        menu_Menu_Player_ViewScores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_Menu_Player_ViewScoresActionPerformed(evt);
            }
        });
        jMenu1.add(menu_Menu_Player_ViewScores);

        menu_MainMenu_Player_scorecard.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menu_MainMenu_Player_scorecard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/app draw1.png"))); // NOI18N
        menu_MainMenu_Player_scorecard.setText("ScoreCard");
        menu_MainMenu_Player_scorecard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_MainMenu_Player_scorecardActionPerformed(evt);
            }
        });
        jMenu1.add(menu_MainMenu_Player_scorecard);

        menu_Menu_Player_EnterScores.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        menu_Menu_Player_EnterScores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/foursquare1.png"))); // NOI18N
        menu_Menu_Player_EnterScores.setText("Enter Scores");
        menu_Menu_Player_EnterScores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_Menu_Player_EnterScoresActionPerformed(evt);
            }
        });
        jMenu1.add(menu_Menu_Player_EnterScores);

        menu_Tournamants.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        menu_Tournamants.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye in a sky1.png"))); // NOI18N
        menu_Tournamants.setText("Tournaments");
        menu_Tournamants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_TournamantsActionPerformed(evt);
            }
        });
        jMenu1.add(menu_Tournamants);

        menu_Leagues.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        menu_Leagues.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/browser1.png"))); // NOI18N
        menu_Leagues.setText("Leagues");
        menu_Leagues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_LeaguesActionPerformed(evt);
            }
        });
        jMenu1.add(menu_Leagues);

        menu_UpdateInfo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        menu_UpdateInfo.setText("Player Infomation");
        menu_UpdateInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_UpdateInfoActionPerformed(evt);
            }
        });
        jMenu1.add(menu_UpdateInfo);

        menu_Main.add(jMenu1);

        menu_main_Help.setText("Help");

        menu_About.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        menu_About.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/radarlogoIcon.png"))); // NOI18N
        menu_About.setText("About");
        menu_About.setToolTipText("About");
        menu_About.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_AboutActionPerformed(evt);
            }
        });
        menu_main_Help.add(menu_About);

        menu_Main.add(menu_main_Help);
        menu_Main.add(jMenu2);

        menu_Date.setText("Date");
        menu_Main.add(menu_Date);

        setJMenuBar(menu_Main);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_ScoreCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnl_ScoreCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * This method enables ball2 of frame1 if ball1 is not a strike. If ball1 is
     * a strike then it enables ball1 of frame2.
     *
     * @param evt
     */
    private void txt_frame1_ball1_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame1_ball1_ScorecardActionPerformed
        while (!isValidBall1(txt_frame1_ball1_Scorecard.getText())) {
            txt_frame1_ball1_Scorecard.setText("");
            txt_frame1_ball1_Scorecard.requestFocus();
            return;
        }
        if (!(txt_frame1_ball1_Scorecard.getText().equals("X") || txt_frame1_ball1_Scorecard.getText().equals("10"))) {
            txt_frame1_ball2_Scorecard.setEnabled(true);
            txt_frame1_ball2_Scorecard.setEditable(true);
            txt_frame1_ball2_Scorecard.requestFocus();
        } else {
            Strike(0);
        }
        txt_frame1_ball1_Scorecard.setEditable(false);
    }//GEN-LAST:event_txt_frame1_ball1_ScorecardActionPerformed

    private void txt_frame1_ball2_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame1_ball2_ScorecardActionPerformed
        while (!isValidBall2(txt_frame1_ball1_Scorecard.getText(), txt_frame1_ball2_Scorecard.getText())) {
            txt_frame1_ball2_Scorecard.setText("");
            txt_frame1_ball2_Scorecard.requestFocus();
            return;
        }
        if ((txt_frame1_ball2_Scorecard.getText().equals("/") || Integer.parseInt(txt_frame1_ball1_Scorecard.getText()) + Integer.parseInt(txt_frame1_ball2_Scorecard.getText()) == 10)) {
            spares++;
            txt_frame1_ball2_Scorecard.setText("/");
        }
        txt_frame1_ball2_Scorecard.setEditable(false);
        txt_frame2_ball1_Scorecard.setEnabled(true);
        txt_frame2_ball1_Scorecard.setEditable(true);
        txt_frame2_ball1_Scorecard.requestFocus();
        calcScoreForFrame(pnl_frame1_Scorecard, pnl_frame2_Scorecard, pnl_frame3_Scorecard);//frame1 score
    }//GEN-LAST:event_txt_frame1_ball2_ScorecardActionPerformed

    /**
     * This method enables ball2 of frame2 if ball1 is not a strike. If ball1 is
     * a strike then it enables ball1 of frame3. Then calculates the score for
     * frame1.
     *
     * @param evt
     */
    private void txt_frame2_ball1_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame2_ball1_ScorecardActionPerformed
        // Reset the text box if the user enters a number greater than 10 or below zero
        while (!isValidBall1(txt_frame2_ball1_Scorecard.getText())) {
            //JOptionPane.showMessageDialog(null, "Invalid Score");
            txt_frame2_ball1_Scorecard.setText("");
            txt_frame2_ball1_Scorecard.requestFocus();
            return;
        }
        // enable ball 2 if ball 1 is not a strike
        if (!(txt_frame2_ball1_Scorecard.getText().equals("X") || txt_frame2_ball1_Scorecard.getText().equals("10"))) {
            txt_frame2_ball2_Scorecard.setEnabled(true);
            txt_frame2_ball2_Scorecard.setEditable(true);
            txt_frame2_ball2_Scorecard.requestFocus();
        } else { // skip to next frame if ball 1 is a strike
            Strike(1);
        }
        txt_frame2_ball1_Scorecard.setEditable(false);
        calcScoreForFrame(pnl_frame1_Scorecard, pnl_frame2_Scorecard, pnl_frame3_Scorecard);//frame1 score
    }//GEN-LAST:event_txt_frame2_ball1_ScorecardActionPerformed

    private void txt_frame2_ball2_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame2_ball2_ScorecardActionPerformed
        // TODO add your handling code here:
        while (!isValidBall2(txt_frame2_ball1_Scorecard.getText(), txt_frame2_ball2_Scorecard.getText())) {
            //JOptionPane.showMessageDialog(null, "Invalid Score");
            txt_frame2_ball2_Scorecard.setText("");
            txt_frame2_ball2_Scorecard.requestFocus();
            return;
        }
        if ((txt_frame2_ball2_Scorecard.getText().equals("/") || Integer.parseInt(txt_frame2_ball1_Scorecard.getText()) + Integer.parseInt(txt_frame2_ball2_Scorecard.getText()) == 10)) {
            spares++;
            txt_frame2_ball2_Scorecard.setText("/");
        }
        txt_frame2_ball2_Scorecard.setEditable(false);
        txt_frame3_ball1_Scorecard.setEnabled(true);
        txt_frame3_ball1_Scorecard.setEditable(true);
        txt_frame3_ball1_Scorecard.requestFocus();
        calcScoreForFrame(pnl_frame1_Scorecard, pnl_frame2_Scorecard, pnl_frame3_Scorecard);//frame1 score
        calcScoreForFrame(pnl_frame2_Scorecard, pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame1_Scorecard);//frame2 score
    }//GEN-LAST:event_txt_frame2_ball2_ScorecardActionPerformed

    /**
     * This method enables ball2 of frame3 if ball1 is not a strike. If ball1 is
     * a strike then it enables ball1 of frame4. Then calculates the score for
     * frame1 and frame2.
     *
     * @param evt
     */
    private void txt_frame3_ball1_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame3_ball1_ScorecardActionPerformed
        // TODO add your handling code here:
        while (!isValidBall1(txt_frame3_ball1_Scorecard.getText())) {
            //JOptionPane.showMessageDialog(null, "Invalid Score");
            txt_frame3_ball1_Scorecard.setText("");
            txt_frame3_ball1_Scorecard.requestFocus();
            return;
        }
        if (!(txt_frame3_ball1_Scorecard.getText().equals("X") || txt_frame3_ball1_Scorecard.getText().equals("10"))) {
            txt_frame3_ball2_Scorecard.setEnabled(true);
            txt_frame3_ball2_Scorecard.setEditable(true);
            txt_frame3_ball2_Scorecard.requestFocus();
        } else {
            Strike(2);
        }
        txt_frame3_ball1_Scorecard.setEditable(false);
        calcScoreForFrame(pnl_frame1_Scorecard, pnl_frame2_Scorecard, pnl_frame3_Scorecard);//frame1 score
        calcScoreForFrame(pnl_frame2_Scorecard, pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame1_Scorecard);//frame2 score
    }//GEN-LAST:event_txt_frame3_ball1_ScorecardActionPerformed

    private void txt_frame3_ball2_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame3_ball2_ScorecardActionPerformed
        // TODO add your handling code here:
        while (!isValidBall2(txt_frame3_ball1_Scorecard.getText(), txt_frame3_ball2_Scorecard.getText())) {
            //JOptionPane.showMessageDialog(null, "Invalid Score");
            txt_frame3_ball2_Scorecard.setText("");
            txt_frame3_ball2_Scorecard.requestFocus();
            return;
        }
        if ((txt_frame3_ball2_Scorecard.getText().equals("/") || Integer.parseInt(txt_frame3_ball1_Scorecard.getText()) + Integer.parseInt(txt_frame3_ball2_Scorecard.getText()) == 10)) {
            spares++;
            txt_frame3_ball2_Scorecard.setText("/");
        }
        txt_frame3_ball2_Scorecard.setEditable(false);
        txt_frame4_ball1_Scorecard.setEnabled(true);
        txt_frame4_ball1_Scorecard.setEditable(true);
        txt_frame4_ball1_Scorecard.requestFocus();
        calcScoreForFrame(pnl_frame2_Scorecard, pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame1_Scorecard);//frame2 score
        calcScoreForFrame(pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame2_Scorecard);//frame3 score
    }//GEN-LAST:event_txt_frame3_ball2_ScorecardActionPerformed

    /**
     * This method enables ball2 of frame4 if ball1 is not a strike. If ball1 is
     * a strike then it enables ball1 of frame5. Then calculates the score for
     * frame2 and frame3.
     *
     * @param evt
     */
    private void txt_frame4_ball1_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame4_ball1_ScorecardActionPerformed
        // TODO add your handling code here:
        while (!isValidBall1(txt_frame4_ball1_Scorecard.getText())) {
            //JOptionPane.showMessageDialog(null, "Invalid Score");
            txt_frame4_ball1_Scorecard.setText("");
            txt_frame4_ball1_Scorecard.requestFocus();
            return;
        }
        if (!(txt_frame4_ball1_Scorecard.getText().equals("X") || txt_frame4_ball1_Scorecard.getText().equals("10"))) {
            txt_frame4_ball2_Scorecard.setEnabled(true);
            txt_frame4_ball2_Scorecard.setEditable(true);
            txt_frame4_ball2_Scorecard.requestFocus();
        } else {
            Strike(3);
        }
        txt_frame4_ball1_Scorecard.setEditable(false);
        calcScoreForFrame(pnl_frame2_Scorecard, pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame1_Scorecard);//frame2 score
        calcScoreForFrame(pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame2_Scorecard);//frame3 score
    }//GEN-LAST:event_txt_frame4_ball1_ScorecardActionPerformed

    private void txt_frame4_ball2_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame4_ball2_ScorecardActionPerformed
        // TODO add your handling code here:
        while (!isValidBall2(txt_frame4_ball1_Scorecard.getText(), txt_frame4_ball2_Scorecard.getText())) {
            //JOptionPane.showMessageDialog(null, "Invalid Score");
            txt_frame4_ball2_Scorecard.setText("");
            txt_frame4_ball2_Scorecard.requestFocus();
            return;
        }
        if ((txt_frame4_ball2_Scorecard.getText().equals("/") || Integer.parseInt(txt_frame4_ball1_Scorecard.getText()) + Integer.parseInt(txt_frame4_ball2_Scorecard.getText()) == 10)) {
            spares++;
            txt_frame4_ball2_Scorecard.setText("/");
        }
        txt_frame4_ball2_Scorecard.setEditable(false);
        txt_frame5_ball1_Scorecard.setEnabled(true);
        txt_frame5_ball1_Scorecard.setEditable(true);
        txt_frame5_ball1_Scorecard.requestFocus();
        calcScoreForFrame(pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame2_Scorecard);//frame3 score
        calcScoreForFrame(pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame3_Scorecard);//frame4 score
    }//GEN-LAST:event_txt_frame4_ball2_ScorecardActionPerformed

    /**
     * This method enables ball2 of frame5 if ball1 is not a strike. If ball1 is
     * a strike then it enables ball1 of frame6. Then calculates the score for
     * frame3 and frame4.
     *
     * @param evt
     */
    private void txt_frame5_ball1_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame5_ball1_ScorecardActionPerformed
        // TODO add your handling code here:
        while (!isValidBall1(txt_frame5_ball1_Scorecard.getText())) {
            //JOptionPane.showMessageDialog(null, "Invalid Score");
            txt_frame5_ball1_Scorecard.setText("");
            txt_frame5_ball1_Scorecard.requestFocus();
            return;
        }
        if (!(txt_frame5_ball1_Scorecard.getText().equals("X") || txt_frame5_ball1_Scorecard.getText().equals("10"))) {
            txt_frame5_ball2_Scorecard.setEnabled(true);
            txt_frame5_ball2_Scorecard.setEditable(true);
            txt_frame5_ball2_Scorecard.requestFocus();
        } else {
            Strike(4);
        }
        txt_frame5_ball1_Scorecard.setEditable(false);
        calcScoreForFrame(pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame2_Scorecard);//frame3 score
        calcScoreForFrame(pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame3_Scorecard);//frame4 score
    }//GEN-LAST:event_txt_frame5_ball1_ScorecardActionPerformed

    private void txt_frame5_ball2_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame5_ball2_ScorecardActionPerformed
        // TODO add your handling code here:
        while (!isValidBall2(txt_frame5_ball1_Scorecard.getText(), txt_frame5_ball2_Scorecard.getText())) {
            //JOptionPane.showMessageDialog(null, "Invalid Score");
            txt_frame5_ball2_Scorecard.setText("");
            txt_frame5_ball2_Scorecard.requestFocus();
            return;
        }
        if ((txt_frame5_ball2_Scorecard.getText().equals("/") || Integer.parseInt(txt_frame5_ball1_Scorecard.getText()) + Integer.parseInt(txt_frame5_ball2_Scorecard.getText()) == 10)) {
            spares++;
            txt_frame5_ball2_Scorecard.setText("/");
        }
        txt_frame5_ball2_Scorecard.setEditable(false);
        txt_frame6_ball1_Scorecard.setEnabled(true);
        txt_frame6_ball1_Scorecard.setEditable(true);
        txt_frame6_ball1_Scorecard.requestFocus();
        calcScoreForFrame(pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame3_Scorecard);//frame4 score
        calcScoreForFrame(pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame4_Scorecard);//frame5 score
    }//GEN-LAST:event_txt_frame5_ball2_ScorecardActionPerformed

    /**
     * This method enables ball2 of frame6 if ball1 is not a strike. If ball1 is
     * a strike then it enables ball1 of frame7. Then calculates the score for
     * frame4 and frame5.
     *
     * @param evt
     */
    private void txt_frame6_ball1_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame6_ball1_ScorecardActionPerformed
        // TODO add your handling code here:
        while (!isValidBall1(txt_frame6_ball1_Scorecard.getText())) {
            //JOptionPane.showMessageDialog(null, "Invalid Score");
            txt_frame6_ball1_Scorecard.setText("");
            txt_frame6_ball1_Scorecard.requestFocus();
            return;
        }
        if (!(txt_frame6_ball1_Scorecard.getText().equals("X") || txt_frame6_ball1_Scorecard.getText().equals("10"))) {
            txt_frame6_ball2_Scorecard.setEnabled(true);
            txt_frame6_ball2_Scorecard.setEditable(true);
            txt_frame6_ball2_Scorecard.requestFocus();
        } else {
            Strike(5);
        }
        txt_frame6_ball1_Scorecard.setEditable(false);
        calcScoreForFrame(pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame3_Scorecard);//frame4 score
        calcScoreForFrame(pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame4_Scorecard);//frame5 score
    }//GEN-LAST:event_txt_frame6_ball1_ScorecardActionPerformed

    private void txt_frame6_ball2_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame6_ball2_ScorecardActionPerformed
        // TODO add your handling code here:
        while (!isValidBall2(txt_frame6_ball1_Scorecard.getText(), txt_frame6_ball2_Scorecard.getText())) {
            //JOptionPane.showMessageDialog(null, "Invalid Score");
            txt_frame6_ball2_Scorecard.setText("");
            txt_frame6_ball2_Scorecard.requestFocus();
            return;
        }
        if ((txt_frame6_ball2_Scorecard.getText().equals("/") || Integer.parseInt(txt_frame6_ball1_Scorecard.getText()) + Integer.parseInt(txt_frame6_ball2_Scorecard.getText()) == 10)) {
            spares++;
            txt_frame6_ball2_Scorecard.setText("/");
        }
        txt_frame6_ball2_Scorecard.setEditable(false);
        txt_frame7_ball1_Scorecard.setEnabled(true);
        txt_frame7_ball1_Scorecard.setEditable(true);
        txt_frame7_ball1_Scorecard.requestFocus();
        calcScoreForFrame(pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame4_Scorecard);//frame5 score
        calcScoreForFrame(pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame5_Scorecard);//frame6 score
    }//GEN-LAST:event_txt_frame6_ball2_ScorecardActionPerformed

    /**
     * This method enables ball2 of frame7 if ball1 is not a strike. If ball1 is
     * a strike then it enables ball1 of frame8. Then calculates the score for
     * frame5 and frame6.
     *
     * @param evt
     */
    private void txt_frame7_ball1_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame7_ball1_ScorecardActionPerformed
        // TODO add your handling code here:
        while (!isValidBall1(txt_frame7_ball1_Scorecard.getText())) {
            //JOptionPane.showMessageDialog(null, "Invalid Score");
            txt_frame7_ball1_Scorecard.setText("");
            txt_frame7_ball1_Scorecard.requestFocus();
            return;
        }
        if (!(txt_frame7_ball1_Scorecard.getText().equals("X") || txt_frame7_ball1_Scorecard.getText().equals("10"))) {
            txt_frame7_ball2_Scorecard.setEnabled(true);
            txt_frame7_ball2_Scorecard.setEditable(true);
            txt_frame7_ball2_Scorecard.requestFocus();
        } else {
            Strike(6);
        }
        txt_frame7_ball1_Scorecard.setEditable(false);
        calcScoreForFrame(pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame4_Scorecard);//frame5 score
        calcScoreForFrame(pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame5_Scorecard);//frame6 score
    }//GEN-LAST:event_txt_frame7_ball1_ScorecardActionPerformed

    private void txt_frame7_ball2_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame7_ball2_ScorecardActionPerformed
        while (!isValidBall2(txt_frame7_ball1_Scorecard.getText(), txt_frame7_ball2_Scorecard.getText())) {
            txt_frame7_ball2_Scorecard.setText("");
            txt_frame7_ball2_Scorecard.requestFocus();
            return;
        }
        if ((txt_frame7_ball2_Scorecard.getText().equals("/") || Integer.parseInt(txt_frame7_ball1_Scorecard.getText()) + Integer.parseInt(txt_frame7_ball2_Scorecard.getText()) == 10)) {
            spares++;
            txt_frame7_ball2_Scorecard.setText("/");
        }
        txt_frame7_ball2_Scorecard.setEditable(false);
        txt_frame8_ball1_Scorecard.setEnabled(true);
        txt_frame8_ball1_Scorecard.setEditable(true);
        txt_frame8_ball1_Scorecard.requestFocus();
        calcScoreForFrame(pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame5_Scorecard);//frame6 score
        calcScoreForFrame(pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame6_Scorecard);//frame7 score
    }//GEN-LAST:event_txt_frame7_ball2_ScorecardActionPerformed

    /**
     * This method enables ball2 of frame8 if ball1 is not a strike. If ball1 is
     * a strike then it enables ball1 of frame9. Then calculates the score for
     * frame6 and frame7.
     *
     * @param evt
     */
    private void txt_frame8_ball1_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame8_ball1_ScorecardActionPerformed
        while (!isValidBall1(txt_frame8_ball1_Scorecard.getText())) {
            txt_frame8_ball1_Scorecard.setText("");
            txt_frame8_ball1_Scorecard.requestFocus();
            return;
        }
        if (!(txt_frame8_ball1_Scorecard.getText().equals("X") || txt_frame8_ball1_Scorecard.getText().equals("10"))) {
            txt_frame8_ball2_Scorecard.setEnabled(true);
            txt_frame8_ball2_Scorecard.setEditable(true);
            txt_frame8_ball2_Scorecard.requestFocus();
        } else {
            Strike(7);
        }
        txt_frame8_ball1_Scorecard.setEditable(false);
        calcScoreForFrame(pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame5_Scorecard);//frame6 score
        calcScoreForFrame(pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame6_Scorecard);//frame7 score
    }//GEN-LAST:event_txt_frame8_ball1_ScorecardActionPerformed

    private void txt_frame8_ball2_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame8_ball2_ScorecardActionPerformed
        while (!isValidBall2(txt_frame8_ball1_Scorecard.getText(), txt_frame8_ball2_Scorecard.getText())) {
            txt_frame8_ball2_Scorecard.setText("");
            txt_frame8_ball2_Scorecard.requestFocus();
            return;
        }
        if ((txt_frame8_ball2_Scorecard.getText().equals("/") || Integer.parseInt(txt_frame8_ball1_Scorecard.getText()) + Integer.parseInt(txt_frame8_ball2_Scorecard.getText()) == 10)) {
            spares++;
            txt_frame8_ball2_Scorecard.setText("/");
        }
        txt_frame8_ball2_Scorecard.setEditable(false);
        txt_frame9_ball1_Scorecard.setEnabled(true);
        txt_frame9_ball1_Scorecard.setEditable(true);
        txt_frame9_ball1_Scorecard.requestFocus();
        calcScoreForFrame(pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame6_Scorecard);//frame7 score
        calcScoreForFrame(pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame7_Scorecard);//frame8 score
    }//GEN-LAST:event_txt_frame8_ball2_ScorecardActionPerformed

    /**
     * This method enables ball2 of frame9 if ball1 is not a strike. If ball1 is
     * a strike then it enables ball1 of frame10. Then calculates the score for
     * frame7 and frame8.
     *
     * @param evt
     */
    private void txt_frame9_ball1_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame9_ball1_ScorecardActionPerformed
        while (!isValidBall1(txt_frame9_ball1_Scorecard.getText())) {
            txt_frame9_ball1_Scorecard.setText("");
            txt_frame9_ball1_Scorecard.requestFocus();
            return;
        }
        if (!(txt_frame9_ball1_Scorecard.getText().equals("X") || txt_frame9_ball1_Scorecard.getText().equals("10"))) {
            txt_frame9_ball2_Scorecard.setEnabled(true);
            txt_frame9_ball2_Scorecard.setEditable(true);
            txt_frame9_ball2_Scorecard.requestFocus();
        } else {
            Strike(8);
        }
        txt_frame9_ball1_Scorecard.setEditable(false);
        calcScoreForFrame(pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame6_Scorecard);//frame7 score
        calcScoreForFrame(pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame7_Scorecard);//frame8 score
    }//GEN-LAST:event_txt_frame9_ball1_ScorecardActionPerformed

    private void txt_frame9_ball2_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame9_ball2_ScorecardActionPerformed
        while (!isValidBall2(txt_frame9_ball1_Scorecard.getText(), txt_frame9_ball2_Scorecard.getText())) {
            txt_frame9_ball2_Scorecard.setText("");
            txt_frame9_ball2_Scorecard.requestFocus();
            return;
        }
        if ((txt_frame9_ball2_Scorecard.getText().equals("/") || Integer.parseInt(txt_frame9_ball1_Scorecard.getText()) + Integer.parseInt(txt_frame9_ball2_Scorecard.getText()) == 10)) {
            spares++;
            txt_frame9_ball2_Scorecard.setText("/");
        }
        txt_frame9_ball2_Scorecard.setEditable(false);
        txt_frame10_ball1_Scorecard.setEnabled(true);
        txt_frame10_ball1_Scorecard.setEditable(true);
        txt_frame10_ball1_Scorecard.requestFocus();
        calcScoreForFrame(pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame7_Scorecard);//frame8 score
        calcScoreForFrameNine(pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame8_Scorecard);//frame9 score
    }//GEN-LAST:event_txt_frame9_ball2_ScorecardActionPerformed

    /**
     * This method enables ball2 of frame10. Then calculates the score for
     * frame8 and frame9.
     *
     * @param evt
     */
    private void txt_frame10_ball1_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame10_ball1_ScorecardActionPerformed
        while (!isValidBall1(txt_frame10_ball1_Scorecard.getText())) {
            txt_frame10_ball1_Scorecard.setText("");
            txt_frame10_ball1_Scorecard.requestFocus();
            return;
        }
        if ((txt_frame10_ball1_Scorecard.getText().equals("X") || txt_frame10_ball1_Scorecard.getText().equals("10"))) {
            strikes++;
            txt_frame10_ball1_Scorecard.setText("X");
        }
        txt_frame10_ball1_Scorecard.setEditable(false);
        txt_frame10_ball2_Scorecard.setEnabled(true);
        txt_frame10_ball2_Scorecard.setEditable(true);
        txt_frame10_ball2_Scorecard.requestFocus();
        calcScoreForFrame(pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame7_Scorecard);//frame8 score
        calcScoreForFrameNine(pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame8_Scorecard);//frame9 score
    }//GEN-LAST:event_txt_frame10_ball1_ScorecardActionPerformed

    /**
     * This method enables ball3 of frame10 if ball1 is a strike or if ball1 is
     * not a strike and ball2 is a spare. If ball1 is not a strike and ball2 is
     * not a spare then it finishes the game. Then calculates the score for
     * frame9 and frame10.
     *
     * @param evt
     */
    private void txt_frame10_ball2_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame10_ball2_ScorecardActionPerformed
        while (!isValidFrame10Ball2(txt_frame10_ball1_Scorecard.getText(), txt_frame10_ball2_Scorecard.getText())) {
            txt_frame10_ball2_Scorecard.setText("");
            txt_frame10_ball2_Scorecard.requestFocus();
            return;
        }
        if (txt_frame10_ball2_Scorecard.getText().equals("X") || txt_frame10_ball2_Scorecard.getText().equals("10")) {
            Strike(10);
        }
        try {
            if ((txt_frame10_ball2_Scorecard.getText().equals("/") || Integer.parseInt(txt_frame10_ball1_Scorecard.getText()) + Integer.parseInt(txt_frame10_ball2_Scorecard.getText()) == 10)) {
                spares++;
                txt_frame10_ball2_Scorecard.setText("/");
                txt_frame10_ball3_Scorecard.setEnabled(true);
                txt_frame10_ball3_Scorecard.setEditable(true);
                txt_frame10_ball3_Scorecard.requestFocus();
            }
        } catch (Exception e) {
            txt_frame10_ball3_Scorecard.setEnabled(true);
            txt_frame10_ball3_Scorecard.setEditable(true);
            txt_frame10_ball3_Scorecard.requestFocus();
        }
        if (txt_frame10_ball1_Scorecard.getText().equals("X")) {
            txt_frame10_ball3_Scorecard.setEnabled(true);
            txt_frame10_ball3_Scorecard.setEditable(true);
            txt_frame10_ball3_Scorecard.requestFocus();
        }
        txt_frame10_ball2_Scorecard.setEditable(false);
        calcScoreForFrameNine(pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame8_Scorecard);//frame9 score
        calcScoreForFrameTen(pnl_frame10_Scorecard, pnl_frame9_Scorecard);//frame10 score
        if (!lbl_Frame10_Score.getText().equals("")) {
            gameFinished();
        }
    }//GEN-LAST:event_txt_frame10_ball2_ScorecardActionPerformed

    /**
     * This method finishes the game and calculates the score for frame10.
     *
     * @param evt
     */
    private void txt_frame10_ball3_ScorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_frame10_ball3_ScorecardActionPerformed
        if (!isValidFrame10Ball3(txt_frame10_ball2_Scorecard.getText(), txt_frame10_ball3_Scorecard.getText())) {
            txt_frame10_ball3_Scorecard.setText("");
            txt_frame10_ball3_Scorecard.requestFocus();
            return;
        }
        if (txt_frame10_ball3_Scorecard.getText().equals("10")) {
            strikes++;
            txt_frame10_ball3_Scorecard.setText("X");
        }
        try {
            if (Integer.parseInt(txt_frame10_ball2_Scorecard.getText()) + Integer.parseInt(txt_frame10_ball2_Scorecard.getText()) == 10) {
                spares++;
                txt_frame10_ball3_Scorecard.setText("/");
            }
        } catch (Exception e) {
        }
        txt_frame10_ball3_Scorecard.setEditable(false);
        calcScoreForFrameTen(pnl_frame10_Scorecard, pnl_frame9_Scorecard);//frame10 score
        gameFinished();
    }//GEN-LAST:event_txt_frame10_ball3_ScorecardActionPerformed

    /**
     * This method puts a strike into each enabled frame when the strike button
     * is clicked.
     *
     * @param evt
     */
    private void btn_ScoreCard_Frame1_StrikeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ScoreCard_Frame1_StrikeActionPerformed
        // Only works if the first ball in the frame has not been thrown yet
        // frame 1
        if (txt_frame1_ball1_Scorecard.isEditable()) {
            Strike(0);
        } else if (txt_frame2_ball1_Scorecard.isEditable()) { // frame 2
            Strike(1);
            calcScoreForFrame(pnl_frame1_Scorecard, pnl_frame2_Scorecard, pnl_frame3_Scorecard);
        } else if (txt_frame3_ball1_Scorecard.isEditable()) { // frame 3
            Strike(2);
            calcScoreForFrame(pnl_frame1_Scorecard, pnl_frame2_Scorecard, pnl_frame3_Scorecard);//frame1 score
            calcScoreForFrame(pnl_frame2_Scorecard, pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame1_Scorecard);//frame2 score
        } else if (txt_frame4_ball1_Scorecard.isEditable()) { // frame 4
            Strike(3);
            calcScoreForFrame(pnl_frame2_Scorecard, pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame1_Scorecard);//frame2 score
            calcScoreForFrame(pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame2_Scorecard);//frame3 score
        } else if (txt_frame5_ball1_Scorecard.isEditable()) { // frame 5
            Strike(4);
            calcScoreForFrame(pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame2_Scorecard);//frame3 score
            calcScoreForFrame(pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame3_Scorecard);//frame4 score
        } else if (txt_frame6_ball1_Scorecard.isEditable()) { // frame 6
            Strike(5);
            calcScoreForFrame(pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame3_Scorecard);//frame4 score
            calcScoreForFrame(pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame4_Scorecard);//frame5 score
        } else if (txt_frame7_ball1_Scorecard.isEditable()) { // frame 7
            Strike(6);
            calcScoreForFrame(pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame4_Scorecard);//frame5 score
            calcScoreForFrame(pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame5_Scorecard);//frame6 score
        } else if (txt_frame8_ball1_Scorecard.isEditable()) { // frame 8
            Strike(7);
            calcScoreForFrame(pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame5_Scorecard);//frame6 score
            calcScoreForFrame(pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame6_Scorecard);//frame7 score
        } else if (txt_frame9_ball1_Scorecard.isEditable()) { // frame 9
            Strike(8);
            calcScoreForFrame(pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame6_Scorecard);//frame7 score
            calcScoreForFrame(pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame7_Scorecard);//frame8 score
        } else if (txt_frame10_ball1_Scorecard.isEditable()) { // frame 10
            Strike(9);
            calcScoreForFrame(pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame7_Scorecard);//frame8 score
            calcScoreForFrameNine(pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame8_Scorecard);//frame9 score
        } else if (txt_frame10_ball2_Scorecard.isEditable() && txt_frame10_ball1_Scorecard.getText().equals("X")) {
            Strike(10);
            calcScoreForFrameNine(pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame8_Scorecard);//frame9 score
            calcScoreForFrameTen(pnl_frame10_Scorecard, pnl_frame9_Scorecard);//frame10 score
        } else if (txt_frame10_ball3_Scorecard.isEditable()
                && txt_frame10_ball2_Scorecard.getText().equals("X") || txt_frame10_ball2_Scorecard.getText().equals("/")) {
            txt_frame10_ball3_Scorecard.setText("X");
            txt_frame10_ball3_Scorecard.setEditable(false);
            strikes++;
            calcScoreForFrameTen(pnl_frame10_Scorecard, pnl_frame9_Scorecard);//frame10 score
            gameFinished();
        }
    }//GEN-LAST:event_btn_ScoreCard_Frame1_StrikeActionPerformed

    /**
     * This method clears the scorecard by destroying and recreating the score
     * card when the Clear button is clicked.
     *
     * @param evt
     */
    private void btn_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ClearActionPerformed
        this.dispose();
        new frm_BowlingScoreCard(playerId).setVisible(true);
    }//GEN-LAST:event_btn_ClearActionPerformed

    /**
     * This method puts a spare into each enabled frame when the spare button is
     * clicked.
     *
     * @param evt
     */
    private void btn_ScoreCard_Frame1_SpareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ScoreCard_Frame1_SpareActionPerformed
        // Only works if the first ball has been thrown
        // frame 1
        if (txt_frame1_ball2_Scorecard.isEditable()) {
            Spare(0);
            calcScoreForFrame(pnl_frame1_Scorecard, pnl_frame2_Scorecard, pnl_frame3_Scorecard);//frame1 score
        } else if (txt_frame2_ball2_Scorecard.isEditable()) { // frame 2
            Spare(2);
            calcScoreForFrame(pnl_frame1_Scorecard, pnl_frame2_Scorecard, pnl_frame3_Scorecard);//frame1 score
            calcScoreForFrame(pnl_frame2_Scorecard, pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame1_Scorecard);//frame2 score
        } else if (txt_frame3_ball2_Scorecard.isEditable()) { // frame 3
            Spare(4);
            calcScoreForFrame(pnl_frame2_Scorecard, pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame1_Scorecard);//frame2 score
            calcScoreForFrame(pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame2_Scorecard);//frame3 score
        } else if (txt_frame4_ball2_Scorecard.isEditable()) { // frame 4
            Spare(6);
            calcScoreForFrame(pnl_frame3_Scorecard, pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame2_Scorecard);//frame3 score
            calcScoreForFrame(pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame3_Scorecard);//frame4 score
        } else if (txt_frame5_ball2_Scorecard.isEditable()) { // frame 5
            Spare(8);
            calcScoreForFrame(pnl_frame4_Scorecard, pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame3_Scorecard);//frame4 score
            calcScoreForFrame(pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame4_Scorecard);//frame5 score
        } else if (txt_frame6_ball2_Scorecard.isEditable()) { // frame 6
            Spare(10);
            calcScoreForFrame(pnl_frame5_Scorecard, pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame4_Scorecard);//frame5 score
            calcScoreForFrame(pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame5_Scorecard);//frame6 score
        } else if (txt_frame7_ball2_Scorecard.isEditable()) { // frame 7
            Spare(12);
            calcScoreForFrame(pnl_frame6_Scorecard, pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame5_Scorecard);//frame6 score
            calcScoreForFrame(pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame6_Scorecard);//frame7 score
        } else if (txt_frame8_ball2_Scorecard.isEditable()) { // frame 8
            Spare(14);
            calcScoreForFrame(pnl_frame7_Scorecard, pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame6_Scorecard);//frame7 score
            calcScoreForFrame(pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame7_Scorecard);//frame8 score
        } else if (txt_frame9_ball2_Scorecard.isEditable()) { // frame 9
            Spare(16);
            calcScoreForFrame(pnl_frame8_Scorecard, pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame7_Scorecard);//frame8 score
            calcScoreForFrameNine(pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame8_Scorecard);//frame9 score
        } else if (txt_frame10_ball2_Scorecard.isEditable() // frame 10
                && !txt_frame10_ball1_Scorecard.getText().equals("X") && !txt_frame10_ball1_Scorecard.getText().equals("")) {
            Spare(18);
            calcScoreForFrameNine(pnl_frame9_Scorecard, pnl_frame10_Scorecard, pnl_frame8_Scorecard);//frame9 score
            calcScoreForFrameTen(pnl_frame10_Scorecard, pnl_frame9_Scorecard);//frame10 score
            if (!lbl_Frame10_Score.getText().equals("")) {
                gameFinished();
            }
        } else if (txt_frame10_ball3_Scorecard.isEditable()
                && !txt_frame10_ball2_Scorecard.getText().equals("/") && !txt_frame10_ball2_Scorecard.getText().equals("X")) {
            txt_frame10_ball3_Scorecard.setText("/");
            txt_frame10_ball3_Scorecard.setEditable(false);
            spares++;
            calcScoreForFrameTen(pnl_frame10_Scorecard, pnl_frame9_Scorecard);//frame10 score
            gameFinished();
        }
    }//GEN-LAST:event_btn_ScoreCard_Frame1_SpareActionPerformed

    /**
     * This method enters the game score when the Enter Score button is clicked.
     *
     * @param evt
     */
    private void btn_EnterScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EnterScoreActionPerformed
        if (!lbl_Frame10_Score.getText().equals("")) {
            try {
                // Get the game type from the combo box
                String type = cmb_ScoreCard_Type.getItemAt(cmb_ScoreCard_Type.getSelectedIndex());

                // Get todays date
                DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
                Calendar currentDate = Calendar.getInstance();
                String date = fmt.format(currentDate.getTime());

                // Get the date from the date picker
                long z = 0;
                try {
                    z = dp_ScoreCard_Options_Date.getDate().getTime();
                } catch (NullPointerException ex) {
                }
                // Set the date to the selected date
                if (z != 0) {
                    date = fmt.format(z);
                }
                
                // Get the game name
                String name = txt_ScoreCard_Options_gameName.getText();

                // Insert information into Scores table
                int a = con.getStatement().executeUpdate("INSERT INTO Games "
                        + "(PlayerId, Gamename, Gametype, GameDate, Frame1, Frame2, Frame3, "
                        + "Frame4, Frame5, Frame6, Frame7, Frame8, Frame9, Frame10, "
                        + "TotalScore, NumberOfStrikes, [NumberOf Spares]) VALUES ('"
                        + playerId + "', '" + name + "', '" + type + "', '" + date
                        + "', '" + lbl_Frame1_Score.getText() + "', '"
                        + lbl_Frame2_Score.getText() + "', '" + lbl_Frame3_Score.getText()
                        + "', '" + lbl_Frame4_Score.getText() + "', '"
                        + lbl_Frame5_Score.getText() + "', '" + lbl_Frame6_Score.getText()
                        + "', '" + lbl_Frame7_Score.getText() + "', '"
                        + lbl_Frame8_Score.getText() + "', '" + lbl_Frame9_Score.getText()
                        + "', '" + lbl_Frame10_Score.getText() + "', '"
                        + lbl_Frame10_Score.getText() + "', '" + strikes + "', '"
                        + spares + "')");
                // INSERTING BOWLER AVERAGES ---------------------------------------
                // Select the scores from the Games table
                ResultSet rs = con.getStatement().executeQuery("SELECT TotalScore"
                        + " FROM Games WHERE PlayerId = '" + playerId + "' AND "
                        + "Gametype = '" + type + "'");
                
                int count = 0;
                int sum = 0;
                // Add all the scores together
                while (rs.next()) {
                    sum += Integer.parseInt(rs.getString(1));
                    count++;
                }
                // Get the average of all the scores
                int average = sum / count;
                // Update the averages of the BowlerAverages table
                int b = 0;
                switch (type) {
                    case "Private":
                        b = con.getStatement().executeUpdate("UPDATE BowlerAverages "
                                + "SET GamesPlayed='" + count + "', PrivateAverage='"
                                + average + "' WHERE PlayerId = '" + playerId + "'");
                        break;
                    case "League":
                        b = con.getStatement().executeUpdate("UPDATE BowlerAverages "
                                + "SET GamesPlayed='" + count + "', LeagueAverage='"
                                + average + "' WHERE PlayerId = '" + playerId + "'");
                        break;
                    case "Tournament":
                        b = con.getStatement().executeUpdate("UPDATE BowlerAverages "
                                + "SET GamesPlayed='" + count + "', TournamentAverage='"
                                + average + "' WHERE PlayerId = '" + playerId + "'");
                        break;
                }
                // Select the averages from the BowlerAverages table
                ResultSet rs2 = con.getStatement().executeQuery("SELECT PrivateAverage, "
                        + "LeagueAverage, TournamentAverage FROM BowlerAverages "
                        + "WHERE PlayerId = '" + playerId + "'");
                int sumAll = 0;
                int privateAverage = 0;
                int leagueAverage = 0;
                int tournamentAverage = 0;
                // Add all the averages together
                while (rs2.next()) {
                    privateAverage = Integer.parseInt(rs2.getString(1));
                    leagueAverage = Integer.parseInt(rs2.getString(2));
                    tournamentAverage = Integer.parseInt(rs2.getString(3));
                    sumAll += privateAverage;
                    sumAll += leagueAverage;
                    sumAll += tournamentAverage;
                }
                // Get the overall average
                int averageAll = 0;
                // Only add gametype average if there is an average for that game type
                if (privateAverage > 0 && leagueAverage > 0 && tournamentAverage > 0) {
                    averageAll = sumAll / 3;
                } else if (privateAverage > 0 && leagueAverage > 0) {
                    averageAll = sumAll / 2;
                } else if (privateAverage > 0 && tournamentAverage > 0) {
                    averageAll = sumAll / 2;
                } else if (leagueAverage > 0 && tournamentAverage > 0) {
                    averageAll = sumAll / 2;
                } else {
                    averageAll = sumAll;
                }
                // Update the overall average of the BowlerAverages table
                int c = con.getStatement().executeUpdate("UPDATE BowlerAverages "
                        + "SET [Overall Average]='" + averageAll + "' WHERE PlayerId = '"
                        + playerId + "'");
                //------------------------------------------------------------------
                /* If INSERT statement was successful then ask user if they want
            to enter another score */
                if (a == 1 && b == 1 && c == 1) {
                    int again = JOptionPane.showConfirmDialog(null,"Enter another score?","Successful",JOptionPane.YES_NO_OPTION);
//                    int again = JOptionPane.showConfirmDialog(null, "Successful, "
//                            + "Enter another score?");
                    // If user selects "no" then return to the Main form
                    if (again == JOptionPane.NO_OPTION) {
                        // Create and display the Main form
                        new frm_Main(playerId).setVisible(true);
                        // Close database connection and dispose of this form
                        con.getConnection().close();
                        this.dispose();
                    } else {
                        // Clear the BowlingScoreCard by creating a new form
                        new frm_BowlingScoreCard(playerId).setVisible(true);
                        // Dispose of this form when new form is created
                        this.dispose();
                    }
                } else { // If the score entry fails then tell the user and reset text
                    JOptionPane.showMessageDialog(null, "Failed");
                }
            } catch (Exception e) { // Print error if there is a problem with database
                System.out.println("Error: " + e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "You must complete the game before you can enter the score.");
        }
    }//GEN-LAST:event_btn_EnterScoreActionPerformed

    /**
     * This method returns the user to the Main form when the Done button is
     * clicked.
     *
     * @param evt
     */
    private void btn_DoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DoneActionPerformed
        new frm_Main(playerId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_DoneActionPerformed

    private void cmb_ScoreCard_TypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_ScoreCard_TypeActionPerformed
        // Get the selected item
        String type = cmb_ScoreCard_Type.getItemAt(cmb_ScoreCard_Type.getSelectedIndex());

        // Display the selected game information
        try {
            // Display all games and overall average if "All Games" is selected
            switch (type) {
                case "Private":
                    // Display private average if "Private" is selected
                    rs2 = con.getStatement().executeQuery("SELECT PrivateAverage "
                            + "FROM BowlerAverages WHERE PlayerId = '" + playerId + "'");
                    break;
                case "Tournament":
                    // Display tournament average if "Tournament" is selected
                    rs2 = con.getStatement().executeQuery("SELECT TournamentAverage "
                            + "FROM BowlerAverages WHERE PlayerId = '" + playerId + "'");
                    break;
                case "League":
                    // Display league average if "League" is selected
                    rs2 = con.getStatement().executeQuery("SELECT LeagueAverage "
                            + "FROM BowlerAverages WHERE PlayerId = '" + playerId + "'");
                    break;
            }
            // Display averages
            while (rs2.next()) {
                txt_hdcScorecard.setText(rs2.getString(1));
            }
        } catch (Exception e) { // Print error if there is a problem with database
            System.out.println("Error: " + e);
        }
    }//GEN-LAST:event_cmb_ScoreCard_TypeActionPerformed

    private void menu_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_DeleteActionPerformed
        int ask = JOptionPane.showConfirmDialog(null, "Do you want to delete your account?");
        // Ask again to make sure the user really wants to delete their account
        if (ask == JOptionPane.YES_OPTION) {
            int delete = JOptionPane.showConfirmDialog(null, "This will permanently"
                    + " delete your account and information!\nAre you sure you"
                    + " want to delete your account?");
            // Delete account if user answers "yes"
            if (delete == JOptionPane.YES_OPTION) {
                try {
                    // Database connection
                    DBConnect con = new DBConnect();
                    // Delete user's averages
                    int a = con.getStatement().executeUpdate("DELETE FROM BowlerAverages"
                            + " WHERE PlayerId = '" + playerId + "'");
                    // Delete user's games
                    int b = con.getStatement().executeUpdate("DELETE FROM Games"
                            + " WHERE PlayerId = '" + playerId + "'");
                    // Delete user's info
                    int c = con.getStatement().executeUpdate("DELETE FROM LoginInfo"
                            + " WHERE PlayerId = '" + playerId + "'");
                    // Delete user's login info
                    int d = con.getStatement().executeUpdate("DELETE FROM PlayerInfo"
                            + " WHERE PlayerId = '" + playerId + "'");

                    /* Tell user their account has been successfully deleted
                    and return them to the login page */
                    if (a == 1 && c == 1 && d == 1) {
                        new frm_LoginPage().setVisible(true);
                        this.dispose();
                        JOptionPane.showMessageDialog(null, "Your account has been"
                                + " deleted.");

                    }
                } catch (Exception ex) {
                    System.out.println("ERROR: " + ex);
                }
            }
        }
    }//GEN-LAST:event_menu_DeleteActionPerformed

    private void menu_LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_LogoutActionPerformed
        new frm_LoginPage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_LogoutActionPerformed

    private void menu_Menu_File_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_Menu_File_ExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menu_Menu_File_ExitActionPerformed

    private void menu_Menu_Player_ViewScoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_Menu_Player_ViewScoresActionPerformed
        new frm_DisplayScores(playerId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_Menu_Player_ViewScoresActionPerformed

    private void menu_MainMenu_Player_scorecardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_MainMenu_Player_scorecardActionPerformed
        new frm_BowlingScoreCard(playerId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_MainMenu_Player_scorecardActionPerformed

    private void menu_Menu_Player_EnterScoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_Menu_Player_EnterScoresActionPerformed
        new frm_EnterBowlingScores(playerId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_Menu_Player_EnterScoresActionPerformed

    private void menu_TournamantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_TournamantsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menu_TournamantsActionPerformed

    private void menu_LeaguesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_LeaguesActionPerformed
        new frm_Leagues(playerId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_LeaguesActionPerformed

    private void menu_UpdateInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_UpdateInfoActionPerformed
        //new frm_UpdateInfo(playerId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menu_UpdateInfoActionPerformed

    private void menu_AboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_AboutActionPerformed
        new frm_About().setVisible(true);
    }//GEN-LAST:event_menu_AboutActionPerformed

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
            java.util.logging.Logger.getLogger(frm_BowlingScoreCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_BowlingScoreCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_BowlingScoreCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_BowlingScoreCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_BowlingScoreCard(playerId).setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Clear;
    private javax.swing.JButton btn_Done;
    private javax.swing.JButton btn_EnterScore;
    private javax.swing.JButton btn_ScoreCard_Frame1_Spare;
    private javax.swing.JButton btn_ScoreCard_Frame1_Strike;
    private javax.swing.JComboBox<String> cmb_ScoreCard_Type;
    private org.jdesktop.swingx.JXDatePicker dp_ScoreCard_Options_Date;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lbl_Frame10_Score;
    private javax.swing.JLabel lbl_Frame1_Score;
    private javax.swing.JLabel lbl_Frame2_Score;
    private javax.swing.JLabel lbl_Frame3;
    private javax.swing.JLabel lbl_Frame3_Score;
    private javax.swing.JLabel lbl_Frame4_Score;
    private javax.swing.JLabel lbl_Frame5_Score;
    private javax.swing.JLabel lbl_Frame6_Score;
    private javax.swing.JLabel lbl_Frame7_Score;
    private javax.swing.JLabel lbl_Frame8_Score;
    private javax.swing.JLabel lbl_Frame9_Score;
    private javax.swing.JLabel lbl_Score;
    private javax.swing.JLabel lbl_ScoreCard_Info_AVGBefore;
    private javax.swing.JLabel lbl_ScoreCard_Info_Avg_After;
    private javax.swing.JLabel lbl_ScoreCard_Info_Avg_Afterlabel;
    private javax.swing.JLabel lbl_ScoreCard_Info_Avg_before;
    private javax.swing.JLabel lbl_ScoreCard_Info_Namelabel;
    private javax.swing.JLabel lbl_ScoreCard_Info_PlayerID;
    private javax.swing.JLabel lbl_ScoreCard_Info_PlayeridLabel;
    private javax.swing.JLabel lbl_ScoreCard_Info_Playerlb;
    private javax.swing.JLabel lbl_ScoreCard_Info_Title;
    private javax.swing.JLabel lbl_ScoreCard_Options_Date_Label;
    private javax.swing.JLabel lbl_ScoreCard_Options_GameName_Label;
    private javax.swing.JLabel lbl_ScoreCard_Options_Title;
    private javax.swing.JLabel lbl_ScoreCard_Options_TypeGame_label;
    private javax.swing.JLabel lbl_Scorecard_Gamescore;
    private javax.swing.JLabel lbl_Scorecard_GamscoreLBL;
    private javax.swing.JLabel lbl_Scorecard_PlayerName;
    private javax.swing.JLabel lbl_frame1;
    private javax.swing.JLabel lbl_frame10;
    private javax.swing.JLabel lbl_frame2;
    private javax.swing.JLabel lbl_frame4;
    private javax.swing.JLabel lbl_frame5;
    private javax.swing.JLabel lbl_frame6;
    private javax.swing.JLabel lbl_frame7;
    private javax.swing.JLabel lbl_frame8;
    private javax.swing.JLabel lbl_frame9;
    private javax.swing.JLabel lbl_playerName_Scorecard;
    private javax.swing.JLabel lbl_score_Scorecard;
    private javax.swing.JLabel lbl_spares_Scorecard;
    private javax.swing.JLabel lbl_strikes_Scorecard;
    private javax.swing.JMenuItem menu_About;
    private javax.swing.JMenu menu_Date;
    private javax.swing.JMenuItem menu_Delete;
    private javax.swing.JMenuItem menu_File_Admin;
    private javax.swing.JPopupMenu.Separator menu_File_Seperator1;
    private javax.swing.JMenuItem menu_Leagues;
    private javax.swing.JMenuItem menu_Logout;
    private javax.swing.JMenuBar menu_Main;
    private javax.swing.JMenuItem menu_MainMenu_Player_scorecard;
    private javax.swing.JMenuItem menu_Menu_File_Exit;
    private javax.swing.JMenuItem menu_Menu_Player_EnterScores;
    private javax.swing.JMenuItem menu_Menu_Player_ViewScores;
    private javax.swing.JMenuItem menu_Tournamants;
    private javax.swing.JMenuItem menu_UpdateInfo;
    private javax.swing.JMenu menu_main_File;
    private javax.swing.JMenu menu_main_Help;
    private javax.swing.JPanel pnl_ScoreCard;
    private javax.swing.JPanel pnl_frame10_Scorecard;
    private javax.swing.JPanel pnl_frame1_Scorecard;
    private javax.swing.JPanel pnl_frame2_Scorecard;
    private javax.swing.JPanel pnl_frame3_Scorecard;
    private javax.swing.JPanel pnl_frame4_Scorecard;
    private javax.swing.JPanel pnl_frame5_Scorecard;
    private javax.swing.JPanel pnl_frame6_Scorecard;
    private javax.swing.JPanel pnl_frame7_Scorecard;
    private javax.swing.JPanel pnl_frame8_Scorecard;
    private javax.swing.JPanel pnl_frame9_Scorecard;
    private javax.swing.JTextField txt_ScoreCard_Options_gameName;
    private javax.swing.JTextField txt_frame10_ball1_Scorecard;
    private javax.swing.JTextField txt_frame10_ball2_Scorecard;
    private javax.swing.JTextField txt_frame10_ball3_Scorecard;
    private javax.swing.JTextField txt_frame1_ball1_Scorecard;
    private javax.swing.JTextField txt_frame1_ball2_Scorecard;
    private javax.swing.JTextField txt_frame2_ball1_Scorecard;
    private javax.swing.JTextField txt_frame2_ball2_Scorecard;
    private javax.swing.JTextField txt_frame3_ball1_Scorecard;
    private javax.swing.JTextField txt_frame3_ball2_Scorecard;
    private javax.swing.JTextField txt_frame4_ball1_Scorecard;
    private javax.swing.JTextField txt_frame4_ball2_Scorecard;
    private javax.swing.JTextField txt_frame5_ball1_Scorecard;
    private javax.swing.JTextField txt_frame5_ball2_Scorecard;
    private javax.swing.JTextField txt_frame6_ball1_Scorecard;
    private javax.swing.JTextField txt_frame6_ball2_Scorecard;
    private javax.swing.JTextField txt_frame7_ball1_Scorecard;
    private javax.swing.JTextField txt_frame7_ball2_Scorecard;
    private javax.swing.JTextField txt_frame8_ball1_Scorecard;
    private javax.swing.JTextField txt_frame8_ball2_Scorecard;
    private javax.swing.JTextField txt_frame9_ball1_Scorecard;
    private javax.swing.JTextField txt_frame9_ball2_Scorecard;
    private javax.swing.JLabel txt_hdcScorecard;
    // End of variables declaration//GEN-END:variables

    /**
     * This method sets the icon for the BowlingScoreCard.
     */
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("radarlogoIcon.png")));
    }
}
