package stagecommand;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor; // pentru cursor “mână” pe card
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;    
import java.awt.event.ActionListener; 


public class HomeWindow {

    private JFrame frame;
    private final MessageBoard board;

    public HomeWindow(MessageBoard board) {
        this.board = board;
        initialize();
        frame.setVisible(true); // IMPORTANT: afișează fereastra
    }

    public static void showUI(MessageBoard board) {
        SwingUtilities.invokeLater(() -> new HomeWindow(board)); // deschide fereastra HomeWindow în mod corect
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Stage Command");
        frame.setSize(1200, 600); // setează dimensiunea ferestrei
        frame.setLocationRelativeTo(null); // fereastra apare fix în mijloc
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(new Color(12, 12, 14)); // fundal dark

        // Layout-ul ferestrei
        frame.setLayout(new BorderLayout());

        // TITLUL
        JLabel titleLabel = new JLabel("Stage Command"); // afișează textul
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // centrare
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 48)); // text mare
        titleLabel.setForeground(Color.WHITE); // titlul alb

        // SUBTITLUL
        JLabel subtitleLabel = new JLabel(
                "<html><div style='text-align: center;'>"
                        + "Centralized communication hub for theater production.<br>"
                        + "Select your operational role to begin."
                        + "</div></html>"
        );
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER); // centrare
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 16)); // text mai mic
        subtitleLabel.setForeground(new Color(160, 160, 170)); // gri

        // PANEL pentru TITLU + SUBTITLU
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false); // transparent (ca să se vadă fundalul dark)
        headerPanel.setBorder(new EmptyBorder(25, 0, 25, 0)); // spațiu sus și jos pentru header

        // centrare pe orizontală
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        subtitleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // spațiere între titlu și subtitlu
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(subtitleLabel);

        // adăugăm header-ul SUS
        frame.add(headerPanel, BorderLayout.NORTH);

        // PANEL pentru "carduri" (zona din mijloc)
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new GridLayout(1, 4, 15, 0)); // 1 rând, 4 coloane, spațiu între ele
        cardsPanel.setOpaque(false); // transparent (să se vadă fundalul dark)
        cardsPanel.setBorder(new EmptyBorder(0, 25, 0, 25)); // puțin spațiu stânga/dreapta

        frame.add(cardsPanel, BorderLayout.CENTER);

        // =========================
        // CARD 1: Manager
        // =========================
        JButton managerCard = new JButton("<html><center>"
                + "<b>Manager</b><br><br>"
                + "<span style='font-size:10px;'>Send instructions to all departments</span>"
                + "</center></html>");

        managerCard.setFont(new Font("SansSerif", Font.BOLD, 18));

        Color normalColor = new Color(18, 18, 22);
        Color hoverColor = new Color(28, 28, 34);

        managerCard.setBackground(normalColor);
        managerCard.setForeground(Color.WHITE);
        managerCard.setFocusPainted(false);

        Border normalBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(40, 40, 46), 1),
                new EmptyBorder(20, 20, 20, 20)
        );
        managerCard.setBorder(normalBorder);

        managerCard.setBorderPainted(false);
        managerCard.setContentAreaFilled(true);
        managerCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Border hoverBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(160, 110, 255), 2),
                new EmptyBorder(19, 19, 19, 19)
        );

        managerCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                managerCard.setBackground(hoverColor); // când intră cursorul
                managerCard.setBorder(hoverBorder);    // border mov pe hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                managerCard.setBackground(normalColor); // când iese cursorul
                managerCard.setBorder(normalBorder);    // revine la border normal
            }
        });
        
        managerCard.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                // când dai click pe Manager
                // aici deschidem fereastra Manager
                ManagerWindow.showUI(board); 
                frame.dispose();             // închide HomeWindow
            }
        });


        cardsPanel.add(managerCard);

        // =================================================
        // CARD 2: Curtain Ops
        // =================================================
        JButton curtainCard = new JButton("<html><center>"
                + "<b>Curtain Ops</b><br><br>"
                + "<span style='font-size:10px;'>Control curtain and stage decor</span>"
                + "</center></html>");

        curtainCard.setFont(new Font("SansSerif", Font.BOLD, 18));

        Color curtainHoverColor = new Color(34, 22, 22); // accent roșu subtil

        curtainCard.setBackground(normalColor);
        curtainCard.setForeground(Color.WHITE);
        curtainCard.setFocusPainted(false);
        curtainCard.setBorder(normalBorder);
        curtainCard.setBorderPainted(false);
        curtainCard.setContentAreaFilled(true);
        curtainCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Border curtainHoverBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 90, 90), 2),
                new EmptyBorder(19, 19, 19, 19)
        );

        curtainCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                curtainCard.setBackground(curtainHoverColor); // când intră cursorul
                curtainCard.setBorder(curtainHoverBorder);    // border roșu pe hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                curtainCard.setBackground(normalColor); // când iese cursorul
                curtainCard.setBorder(normalBorder);    // revine la border normal
            }
        });
        
        curtainCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // când dai click pe Curtain Ops
                // aici deschidem fereastra Curtain Ops
                CurtainWindow.showUI(board);
                frame.dispose(); // închide HomeWindow
            }
        });

        cardsPanel.add(curtainCard);

        // =================================================
        // CARD 3: Lights Ops
        // =================================================
        JButton lightsCard = new JButton("<html><center>"
                + "<b>Lights Ops</b><br><br>"
                + "<span style='font-size:10px;'>Manage lighting cues and spots</span>"
                + "</center></html>");

        lightsCard.setFont(new Font("SansSerif", Font.BOLD, 18));

        Color lightsHoverColor = new Color(34, 30, 18); // accent galben subtil

        lightsCard.setBackground(normalColor);
        lightsCard.setForeground(Color.WHITE);
        lightsCard.setFocusPainted(false);
        lightsCard.setBorder(normalBorder);
        lightsCard.setBorderPainted(false);
        lightsCard.setContentAreaFilled(true);
        lightsCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Border lightsHoverBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 200, 70), 2), // galben
                new EmptyBorder(19, 19, 19, 19)
        );

        lightsCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lightsCard.setBackground(lightsHoverColor); // când intră cursorul
                lightsCard.setBorder(lightsHoverBorder);    // border galben pe hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lightsCard.setBackground(normalColor); // când iese cursorul
                lightsCard.setBorder(normalBorder);    // revine la border normal
            }
        });
        
        lightsCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // când dai click pe Lights Ops
                // aici deschidem fereastra Lights Ops
                LightsWindow.showUI(board);
                frame.dispose(); // închide HomeWindow
            }
        });

        cardsPanel.add(lightsCard);

        // =================================================
        // CARD 4: Sound Ops
        // =================================================
        JButton soundCard = new JButton("<html><center>"
                + "<b>Sound Ops</b><br><br>"
                + "<span style='font-size:10px;'>Control audio levels and cues</span>"
                + "</center></html>");

        soundCard.setFont(new Font("SansSerif", Font.BOLD, 18));

        Color soundHoverColor = new Color(18, 24, 34); //  accent albastru subtil

        soundCard.setBackground(normalColor);
        soundCard.setForeground(Color.WHITE);
        soundCard.setFocusPainted(false);
        soundCard.setBorder(normalBorder);
        soundCard.setBorderPainted(false);
        soundCard.setContentAreaFilled(true);
        soundCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Border soundHoverBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 150, 255), 2), //  albastru
                new EmptyBorder(19, 19, 19, 19)
        );

        soundCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                soundCard.setBackground(soundHoverColor); // când intră cursorul
                soundCard.setBorder(soundHoverBorder);    // border albastru pe hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                soundCard.setBackground(normalColor); // când iese cursorul
                soundCard.setBorder(normalBorder);    // revine la border normal
            }
        });
        
        soundCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // când dai click pe Sound Ops
                // aici deschidem fereastra Sound Ops
                SoundWindow.showUI(board);
                frame.dispose(); // închide HomeWindow
            }
        });
        
        cardsPanel.add(soundCard);
    }
}

/*
 	am un singur normalColor + normalBorder refolosite ( nu  se repeta inutil )
 	fiecare card are hover background + hover border pe culoarea lui
 */

