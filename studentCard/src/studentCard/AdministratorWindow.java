package studentCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AdministratorWindow extends Components {
	
	private JPanel mainPanel;
	private JFrame mainFrame;
	private String[] buttonNames = { "Управление студентами", "Управление предметами", "Управление оценками", "Выйти из режима" };
	private JButton[] button = new JButton[4];
	private int[] buttonBounds = { 66, 37, 250, 25, 66, 67, 250, 25, 66, 97, 250, 25,  181, 127, 135, 25 };

	public AdministratorWindow() {
		mainPanel = CreateMainPanel();
		mainFrame = CreateMainFrame("Интерфейс администратора", mainPanel, 400, 250);
		for (int i = 0; i < 4; i++) {
			button[i] = CreateButton(buttonNames[i], buttonBounds[i * 4], buttonBounds[i * 4 + 1],
					buttonBounds[i * 4 + 2], buttonBounds[i * 4 + 3]);
			mainPanel.add(button[i]);
		}
		mainFrame.setVisible(true);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentListWindow studWindow = new StudentListWindow();
				studWindow.makeActive();
			}
		});
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubjectListWindow subjWindow = new SubjectListWindow();
				subjWindow.makeActive();
			}
		});
		button[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GradeStudentListWindow gradeWindow = new GradeStudentListWindow();
				gradeWindow.makeActive();
			}
		});
		button[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kill();
			}
		});
	}
	public void makeActive() {
		mainFrame.setVisible(true);
	}
	public void kill() {
		mainFrame.dispose();
	}
}
