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

public class SpecificStudentGradeEditWindow extends Components {
	private int gradeID;
	private Grade grade;
	private JPanel mainPanel;
	private JFrame mainFrame;
	private String[] subjectsList;
	private String[] buttonNames = { "Сохранить", "Выйти" };
	private JButton[] button = new JButton[2];
	private JTextField[] textField = new JTextField[2];
	private String[] labelNames = { "Идентификационный номер", "Предмет", "Оценка" };
	private int[] labelBounds = { 10, 5, 280, 50, 10, 45, 280, 50, 10, 85, 280, 50 };
	private JLabel[] label = new JLabel[3];
	private JComboBox mainComboBox;
	private int[] textFieldsBounds = { 250, 17, 200, 25, 250, 97, 200, 25 };
	private int[] buttonBounds = { 250, 130, 200, 25 };
	private ArrayList<Subject> subjects = Connect.getSubjectsArray();
	private gradeFieldFilter filter;

	public SpecificStudentGradeEditWindow(int gradeID) throws ClassNotFoundException, SQLException {
		this.gradeID = gradeID;
		Connect.Conn();
		Connect.CreateDB();
		grade = Connect.ReadGradesByID(gradeID);
		Connect.CloseDB();
		mainPanel = CreateMainPanel();
		mainFrame = CreateMainFrame("Редактирование оценки", mainPanel, 500, 200);
		subjectsList = new String[subjects.size()];
		for (int k = 0; k < subjects.size(); k++) {
			subjectsList[k] = subjects.get(k).getName();
		}
		mainComboBox = CreateComboBox(subjectsList, 250, 57, 200, 25);
		mainComboBox.setSelectedIndex(grade.getSubjID());
		mainFrame.add(mainComboBox);
		for (int i = 0; i < 3; i++) {
			label[i] = CreateLabel(labelNames[i], labelBounds[i * 4], labelBounds[i * 4 + 1], labelBounds[i * 4 + 2],
					labelBounds[i * 4 + 3]);
			mainFrame.add(label[i]);
			if (i < 2) {
				textField[i] = CreateTextField(textFieldsBounds[i * 4], textFieldsBounds[i * 4 + 1],
						textFieldsBounds[i * 4 + 2], textFieldsBounds[i * 4 + 3]);
				mainFrame.add(textField[i]);
			}
			if (i < 1) {
				button[i] = CreateButton(buttonNames[i], buttonBounds[i * 4], buttonBounds[i * 4 + 1],
						buttonBounds[i * 4 + 2], buttonBounds[i * 4 + 3]);
				mainFrame.add(button[i]);
			}
		}
		filter = new gradeFieldFilter();
		filter.PTextFilter(textField[1], 1);
		textField[0].setText(Integer.toString(grade.getNumber()));
		textField[0].setEditable(false);
		textField[1].setText(Integer.toString(grade.getMark()));
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField[1].getText().equals("")) {
					callMessage(mainPanel, "Проверьте правильность введенных данных", "Внимание!");
				} else {
					try {
						Connect.Conn();
						Connect.CreateDB();
						Connect.EditGradeByID(grade.getNumber(), mainComboBox.getSelectedIndex() + 1,
								Integer.parseInt(textField[1].getText()));
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
