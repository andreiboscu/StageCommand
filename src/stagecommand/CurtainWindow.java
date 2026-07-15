package stagecommand;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurtainWindow implements MessageListener {
	private JFrame frame;
	private final MessageBoard board;
	private JLabel currentText;
	private JLabel timeLabel;
	private JTextArea historyArea;


	public static void showUI(MessageBoard board) {
		SwingUtilities.invokeLater(() -> new CurtainWindow(board));
	}

	public CurtainWindow(MessageBoard board) {
		this.board = board;
		board.addMessageListener(this);
		init();
		frame.setVisible(true);
	}

//	@Override
//	public void onMessageReceived(String msg) {
//		SwingUtilities.invokeLater(() -> {
//			String[] lines = msg.split("\n", 2);
//			String log = lines[0];
//			String text = lines[1];
//			currentText.setText(text);
//			timeLabel.setText(now());
//			historyArea.setText(now() + "   " + log + "\n" + text + "\n\n" + historyArea.getText());
//		});
//	}

	
	
	@Override
	public void onMessageReceived(String msg) {
	    SwingUtilities.invokeLater(() -> {
	        String[] lines = msg.split("\n", 2);
	        if (lines.length < 2) return;
	        
	        String log = lines[0];
	        String text = lines[1];
	        
	        // Verifică dacă mesajul este pentru CURTAIN
	        if (log.startsWith("TO: CURTAIN") || log.startsWith("TO: ALL STATIONS")) {
	            // Actualizează instrucțiunea curentă doar dacă e cel mai recent mesaj
	            // Poți adăuga o verificare aici dacă vrei să nu suprascrii mesajul curent
	            // cu unul vechi din istoric
	            
	            currentText.setText(text);
	            timeLabel.setText(now());
	            
	            // Verifică dacă mesajul nu există deja în istoric (pentru a evita duplicate)
	            String currentHistory = historyArea.getText();
	            if (!currentHistory.contains(text) || currentHistory.isEmpty()) {
	                historyArea.setText(now() + "   " + log + "\n" + text + "\n\n" + historyArea.getText());
	            }
	        }
	    });
	}
	
	
	@Override
	public String getStation() {
		return "CURTAIN";
	}

	private String now() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}

	private void init() {
		frame = new JFrame("Curtain Station");
		frame.setSize(1000, 720);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(12, 12, 14));
		frame.setLayout(new BorderLayout());
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				board.removeMessageListener(CurtainWindow.this);
			}
		
		})
		;

		// ===== HEADER =====
		JPanel header = new JPanel(new BorderLayout());
		header.setBorder(new EmptyBorder(15, 20, 15, 20));
		header.setBackground(new Color(12, 12, 14));
		JButton back = new JButton("←");
		back.setFont(new Font("SansSerif", Font.BOLD, 22));
		back.setForeground(Color.WHITE);
		back.setBackground(new Color(18, 18, 22));
		back.setBorder(new EmptyBorder(5, 12, 5, 12));
		back.setFocusPainted(false);
		back.addActionListener(e -> {
			frame.dispose();
			HomeWindow.showUI(board);
		});
		JLabel title = new JLabel("Curtain Station", SwingConstants.CENTER);
		title.setFont(new Font("SansSerif", Font.BOLD, 24));
		title.setForeground(Color.WHITE);
		header.add(back, BorderLayout.WEST);
		header.add(title, BorderLayout.CENTER);
		frame.add(header, BorderLayout.NORTH);

		// ===== CONTENT =====
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBorder(new EmptyBorder(20, 40, 20, 40));
		content.setBackground(new Color(12, 12, 14));

		// ===== CURRENT INSTRUCTION (DREPTUNGHI SUS) =====
		JPanel current = new JPanel(new BorderLayout(10, 10));
		current.setBackground(new Color(18, 18, 22));
		current.setBorder(
				new CompoundBorder(new LineBorder(new Color(255, 90, 90), 2), new EmptyBorder(25, 25, 25, 25)));
		JLabel prio = new JLabel("PRIORITY: HIGH");
		prio.setForeground(new Color(255, 90, 90));
		prio.setFont(new Font("SansSerif", Font.BOLD, 14));
		currentText = new JLabel("Waiting for manager…");
		currentText.setForeground(Color.WHITE);
		currentText.setFont(new Font("SansSerif", Font.BOLD, 32));
		timeLabel = new JLabel("");
		timeLabel.setForeground(new Color(160, 160, 170));
		current.add(prio, BorderLayout.NORTH);
		current.add(currentText, BorderLayout.CENTER);
		current.add(timeLabel, BorderLayout.EAST);
		content.add(current);
		content.add(Box.createVerticalStrut(25));

		// ===== HISTORY =====
		JLabel hTitle = new JLabel("HISTORY LOG");
		hTitle.setForeground(new Color(160, 110, 255));
		hTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
		content.add(hTitle);
		content.add(Box.createVerticalStrut(10));
		historyArea = new JTextArea();
		historyArea.setEditable(false);
		historyArea.setBackground(new Color(28, 28, 34));
		historyArea.setForeground(Color.WHITE);
		historyArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		JScrollPane scroll = new JScrollPane(historyArea);
		scroll.setBorder(new LineBorder(new Color(40,40,46)));
		scroll.setPreferredSize(new Dimension(600, 300));
		scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

		content.add(scroll);

		frame.add(content, BorderLayout.CENTER);
	}
}