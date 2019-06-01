package studentCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class getStudentZKWindow extends Components {
	private JPanel mainPanel;
	private JFrame mainFrame;
	private String[] buttonNames = { "Просмотреть карточку", "Выйти" };
	private JButton[] button = new JButton[2];
	private JTextField[] textField = new JTextField[1];
	private int[] textFieldsBounds = { 25, 57, 96, 25};
	private int[] buttonBounds = { 166, 57, 200, 25, 166, 97, 200, 25 };
	private String[] labelNames = { "Введите номер студента для просмотра его карточки" };
	private int[] labelBounds = { 25, 17, 366, 25 };
	private JLabel[] label = new JLabel[1];
	private textFieldFilter filter;
	
	public getStudentZKWindow() throws ClassNotFoundException, SQLException {
		Connect.Conn();
		Connect.CreateDB();
		Connect.clearStudentsArray();
		Connect.ReadStudent();
		ArrayList<Student> students = Connect.getStudentsArray();
		Connect.CloseDB();
		filter = new textFieldFilter();
		mainPanel = CreateMainPanel();
		mainFrame = CreateMainFrame("Приложение по работе с карточками студентов", mainPanel, 400, 170);
		for (int i = 0; i < 2; i++) {
			button[i] = CreateButton(buttonNames[i], buttonBounds[i * 4], buttonBounds[i * 4 + 1],
					buttonBounds[i * 4 + 2], buttonBounds[i * 4 + 3]);
			mainPanel.add(button[i]);
			if (i<1) {
				textField[i] = CreateTextField(textFieldsBounds[i * 4], textFieldsBounds[i * 4 + 1],
						textFieldsBounds[i * 4 + 2], textFieldsBounds[i * 4 + 3]);
				mainFrame.add(textField[i]);
				filter.PTextFilter(textField[i], 5);
				label[i] = CreateLabel(labelNames[i], labelBounds[i * 4], labelBounds[i * 4 + 1], labelBounds[i * 4 + 2],
						labelBounds[i * 4 + 3]);
				mainFrame.add(label[i]);
			}
		}
		mainFrame.setVisible(true);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentInspectWindow inspectWindow;
				try {
					boolean isThereSuchStudent = false;
					for (int i = 0;i<students.size();i++) {
						if (students.get(i).getIndex() == Integer.valueOf(textField[0].getText())) {
							inspectWindow = new StudentInspectWindow(Integer.valueOf(textField[0].getText()));
							inspectWindow.makeActive();
							isThereSuchStudent = true;
							break;
						}
						isThereSuchStudent = false;
					}
					if (isThereSuchStudent == false) {
						callMessage(mainPanel, "Студент с таким номером отсутствует", "Внимание!");
					}
					isThereSuchStudent = false;
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button[1].addActionListener(new ActionListener() {
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
