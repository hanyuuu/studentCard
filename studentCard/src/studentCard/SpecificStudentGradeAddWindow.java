package studentCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SpecificStudentGradeAddWindow extends Components {
	private JPanel mainPanel;
	private JFrame mainFrame;
	private String[] subjectsList;
	private String[] buttonNames = { "Сохранить", "Выйти" };
	private JButton[] button = new JButton[2];
	private JTextField[] textField = new JTextField[1];
	private String[] labelNames = { "Предмет", "Оценка" };
	private int[] labelBounds = { 10, 45, 280, 50, 10, 85, 280, 50 };
	private JComboBox mainComboBox;
	private int[] textFieldsBounds = { 250, 97, 200, 25 };
	private JLabel[] label = new JLabel[2];
	private int[] buttonBounds = { 250, 130, 200, 25 };
	private ArrayList<Subject> subjects = Connect.getSubjectsArray();
	private gradeFieldFilter filter;
	private int studentID;

	public SpecificStudentGradeAddWindow(int studentID) throws ClassNotFoundException, SQLException {
		this.studentID = studentID;
		mainPanel = CreateMainPanel();
		mainFrame = CreateMainFrame("Редактирование оценки", mainPanel, 500, 200);
		subjectsList = new String[subjects.size()];
		for (int k = 0; k < subjects.size(); k++) {
			subjectsList[k] = subjects.get(k).getName();
		}
		mainComboBox = CreateComboBox(subjectsList, 250, 57, 200, 25);
		mainFrame.add(mainComboBox);
		for (int i = 0; i < 2; i++) {
			label[i] = CreateLabel(labelNames[i], labelBounds[i * 4], labelBounds[i * 4 + 1], labelBounds[i * 4 + 2],
					labelBounds[i * 4 + 3]);
			mainFrame.add(label[i]);
			if (i < 1) {
				textField[i] = CreateTextField(textFieldsBounds[i * 4], textFieldsBounds[i * 4 + 1],
						textFieldsBounds[i * 4 + 2], textFieldsBounds[i * 4 + 3]);
				mainFrame.add(textField[i]);
				button[i] = CreateButton(buttonNames[i], buttonBounds[i * 4], buttonBounds[i * 4 + 1],
						buttonBounds[i * 4 + 2], buttonBounds[i * 4 + 3]);
				mainFrame.add(button[i]);
			}
		}
		filter = new gradeFieldFilter();
		filter.PTextFilter(textField[0], 1);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField[0].getText().equals("")) {
					callMessage(mainPanel, "Проверьте правильность введенных данных", "Внимание!");
				} else {
					try {
						Connect.Conn();
						Connect.CreateDB();
						Connect.AddGradeByStudID(studentID, mainComboBox.getSelectedIndex() + 1,
								Integer.parseInt(textField[0].getText()));
						Connect.CloseDB();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					try {
						SpecificStudentGradeListWindow.updateTable();
					} catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
					kill();
				}
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
