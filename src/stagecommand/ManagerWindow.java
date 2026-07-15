package stagecommand;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.*;

public class ManagerWindow {
	private JFrame frame;
	private final MessageBoard board;
	private JTextArea messageArea;
	private JTextArea logArea;
	private String selectedTarget = "ALL STATIONS";
	private JButton allButton, curtainButton, lightsButton, soundButton;
	
//porneste fereastra corecta pe threadul swing, creare prin invoke
	public static void showUI(MessageBoard board) {
		SwingUtilities.invokeLater(() -> new ManagerWindow(board));
	}

	public ManagerWindow(MessageBoard board) {
		this.board = board;
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame("Manager");
		frame.setSize(1100, 750);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(12, 12, 14));
		frame.setLayout(new BorderLayout());
		
		
		// ================= HEADER =================
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(new Color(12, 12, 14));
		header.setBorder(new EmptyBorder(15, 15, 15, 15));
		JButton back = new JButton("←");
		back.setFont(new Font("SansSerif", Font.BOLD, 20));
		back.setForeground(Color.WHITE);
		back.setBackground(new Color(18, 18, 22));
		back.setBorder(new EmptyBorder(5, 10, 5, 10));
		back.setFocusPainted(false);
		back.addActionListener(e -> {
			frame.dispose();
			HomeWindow.showUI(board);
		});
		
		JLabel title = new JLabel("Manager Console", SwingConstants.CENTER);
		title.setFont(new Font("SansSerif", Font.BOLD, 28));
		title.setForeground(Color.WHITE);
		header.add(back, BorderLayout.WEST);
		header.add(title, BorderLayout.CENTER);
		header.add(Box.createHorizontalStrut(50), BorderLayout.EAST);
		frame.add(header, BorderLayout.NORTH);
		
		
		// ================= MAIN =================
		JPanel main = new JPanel(new GridLayout(1, 2, 20, 0));
		main.setBorder(new EmptyBorder(20, 40, 20, 40));
		main.setBackground(new Color(12, 12, 14));
		
		
		
		// ================= LEFT =================
		JPanel left = new JPanel(new BorderLayout(15, 15));
		left.setBackground(new Color(18, 18, 22));
		left.setBorder(new CompoundBorder(new LineBorder(new Color(40, 40, 46), 1), new EmptyBorder(25, 25, 25, 25)));
		JLabel compose = new JLabel("TRANSMIT INSTRUCTION");
		compose.setForeground(new Color(160, 110, 255));
		compose.setFont(new Font("SansSerif", Font.BOLD, 18));
		left.add(compose, BorderLayout.NORTH);
		
		
		// TARGET BUTTONS
		JPanel targets = new JPanel(new GridLayout(2, 2, 10, 10));
		targets.setBackground(new Color(18, 18, 22));
		allButton = createTarget("ALL STATIONS");
		curtainButton = createTarget("CURTAIN");
		lightsButton = createTarget("LIGHTS");
		soundButton = createTarget("SOUND");
		targets.add(allButton);
		targets.add(curtainButton);
		targets.add(lightsButton);
		targets.add(soundButton);
		left.add(targets, BorderLayout.CENTER);
		
		
		
		// MESSAGE AREA 
		messageArea = new JTextArea("Enter explicit instruction");
		messageArea.setFont(new Font("SansSerif", Font.PLAIN, 15));
		messageArea.setForeground(new Color(160, 160, 160));
		messageArea.setBackground(new Color(28, 28, 34));
		messageArea.setLineWrap(true);
		messageArea.setWrapStyleWord(true);
		messageArea.setRows(7);
		messageArea.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				if (messageArea.getText().equals("Enter explicit instruction")) {
					messageArea.setText("");
					messageArea.setForeground(Color.WHITE);
				}
			}

			public void focusLost(java.awt.event.FocusEvent e) {
				if (messageArea.getText().isEmpty()) {
					messageArea.setText("Enter explicit instruction");
					messageArea.setForeground(new Color(160, 160, 160));
				}
			}
		});
		left.add(new JScrollPane(messageArea), BorderLayout.SOUTH);
		
		
		
		// ================= RIGHT =================
		JPanel right = new JPanel(new BorderLayout(15, 15));
		right.setBackground(new Color(18, 18, 22));
		right.setBorder(new CompoundBorder(new LineBorder(new Color(40, 40, 46), 1), new EmptyBorder(25, 25, 25, 25)));
		JLabel history = new JLabel("Command Log");
		history.setForeground(new Color(160, 110, 255));
		history.setFont(new Font("SansSerif", Font.BOLD, 18));
		logArea = new JTextArea();
		logArea.setEditable(false);
		logArea.setBackground(new Color(28, 28, 34));
		logArea.setForeground(new Color(200, 200, 200));
		logArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
		updateLog();
		right.add(history, BorderLayout.NORTH);
		right.add(new JScrollPane(logArea), BorderLayout.CENTER);
		
		
		
		// ================= SEND BUTTON =================
		JButton send = new JButton("TRANSMIT INSTRUCTION");
		send.setFont(new Font("SansSerif", Font.BOLD, 18));
		send.setBackground(new Color(160, 110, 255));
		send.setForeground(Color.WHITE);
		send.setBorder(new EmptyBorder(14, 40, 14, 40));
		send.setFocusPainted(false);
		send.addActionListener(this::sendCommand);
		JPanel bottom = new JPanel();
		bottom.setBackground(new Color(12, 12, 14));
		bottom.add(send);
		frame.add(main, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.SOUTH);
		main.add(left);
		main.add(right);
		updateTargetSelection();
	}

	//retin catre cine trimit mesaj
	private JButton createTarget(String name) {
		JButton b = new JButton(name);
		b.setBackground(new Color(28, 28, 34));
		b.setForeground(Color.WHITE);
		b.setFont(new Font("SansSerif", Font.BOLD, 14));
		b.setBorder(new LineBorder(new Color(60, 60, 70), 1));
		b.addActionListener(e -> {
			selectedTarget = name;
			updateTargetSelection();
		});
		return b;
	}

	
	private void updateTargetSelection() {
		JButton[] buttons = { allButton, curtainButton, lightsButton, soundButton };
		for (JButton b : buttons) {
			b.setBackground(new Color(28, 28, 34));
		}
		switch (selectedTarget) {
		case "ALL STATIONS" -> allButton.setBackground(new Color(160, 110, 255));
		case "CURTAIN" -> curtainButton.setBackground(new Color(160, 110, 255));
		case "LIGHTS" -> lightsButton.setBackground(new Color(160, 110, 255));
		case "SOUND" -> soundButton.setBackground(new Color(160, 110, 255));
		}
	}

	private void sendCommand(ActionEvent e) {
		String text = messageArea.getText().trim();
		if (text.isEmpty() || text.equals("Enter explicit instruction"))
			return;
		String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		board.sendMessageToTarget(selectedTarget, time + "\n  " + text);
		updateLog();
		messageArea.setText("Enter explicit instruction");
		messageArea.setForeground(new Color(160, 160, 160));
	}

	private void updateLog() {
		StringBuilder sb = new StringBuilder();
		for (String s : board.getCommandHistory()) {
			sb.append(s).append("\n\n");
		}
		logArea.setText(sb.toString());
		logArea.setCaretPosition(0);
	}
}