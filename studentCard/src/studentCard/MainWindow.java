package studentCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends Components {
	private JPanel mainPanel;
	private JFrame mainFrame;
	private String[] buttonNames = { "Управление", "Личная карточка студента" };
	private JButton[] button = new JButton[2];
	private int[] buttonBounds = { 66, 37, 250, 25, 66, 67, 250, 25 };

	public MainWindow() {
		mainPanel = CreateMainPanel();
		mainFrame = CreateMainFrame("Приложение по работе с карточками студентов", mainPanel, 400, 170);
		for (int i = 0; i < 2; i++) {
			button[i] = CreateButton(buttonNames[i], buttonBounds[i * 4], buttonBounds[i * 4 + 1],
					buttonBounds[i * 4 + 2], buttonBounds[i * 4 + 3]);
			mainPanel.add(button[i]);
		}
		mainFrame.setVisible(true);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdministratorWindow window = new AdministratorWindow();
				window.makeActive();
			}
		});
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						getStudentZKWindow inspectWindow = new getStudentZKWindow();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
	}
}
