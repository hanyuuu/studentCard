package studentCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class StudentInspectWindow extends Components {
	private textFieldFilter filter;
	private static Object[] columnsHeader = new String[] { "№", "Предмет", "Оценка" };
	private int studentID;
	private Student tempStudent;
	private static DefaultTableModel tableModel;
	private JPanel mainPanel;
	private JFrame mainFrame;
	public static JTable mainTable;
	private String[] buttonNames = { "Выйти", "Выйти" };
	private ArrayList<Grade> grades;
	JScrollPane pane;
	private JButton[] button = new JButton[2];
	private JTextField[] textField = new JTextField[9];
	private String[] labelNames = { "Идентификационный номер", "Фамилия", "Имя", "Отчество", "Группа", "Курс", "Адрес",
			"Номер телефона", "Имеет общежитие" };
	private int[] labelBounds = { 10, 5, 280, 50, 10, 45, 280, 50, 10, 85, 280, 50, 10, 125, 280, 50, 10, 165, 280, 50,
			10, 205, 280, 50, 10, 245, 280, 50, 10, 285, 280, 50, 10, 325, 280, 50 };
	private int[] textFieldsBounds = { 377, 17, 200, 25,
			377, 57, 200, 25,
			377, 97, 200, 25,
			377, 137, 200, 25,
			377,177, 200, 25,
			377, 217, 200, 25,
			377, 257, 200, 25,
			377, 297, 200, 25,
			377, 337, 200, 25 };
	private JLabel[] label = new JLabel[9];
	private int[] buttonBounds = { 377, 666, 200, 25 };

	public StudentInspectWindow(int studentID) throws ClassNotFoundException, SQLException {
		this.studentID = studentID;
		Connect.Conn();
		Connect.CreateDB();
		Connect.ReadGradesByStudent(studentID);
		grades = Connect.getGradesArray();
		tempStudent = Connect.ReadStudentByID(studentID);
		Connect.CloseDB();
		mainPanel = CreateMainPanel();
		mainFrame = CreateMainFrame("Личная карточка студента (" + tempStudent.getSurname() + " "
				+ tempStudent.getName().charAt(0) + "." + tempStudent.getPatronymic().charAt(0) + ".)", mainPanel, 600,
				750);
		for (int i = 0; i < 9; i++) {
			label[i] = CreateLabel(labelNames[i], labelBounds[i * 4], labelBounds[i * 4 + 1], labelBounds[i * 4 + 2],
					labelBounds[i * 4 + 3]);
			mainFrame.add(label[i]);
			if (i < 9) {
				textField[i] = CreateTextField(textFieldsBounds[i * 4], textFieldsBounds[i * 4 + 1],
						textFieldsBounds[i * 4 + 2], textFieldsBounds[i * 4 + 3]);
				mainFrame.add(textField[i]);
				textField[i].setEditable(false);
			}
			if (i < 1) {
				button[i] = CreateButton(buttonNames[i], buttonBounds[i * 4], buttonBounds[i * 4 + 1],
						buttonBounds[i * 4 + 2], buttonBounds[i * 4 + 3]);
				mainFrame.add(button[i]);
			}
		}
		filter = new textFieldFilter();
		filter.PTextFilter(textField[5], 1);
		textField[0].setText(Integer.toString(tempStudent.getIndex()));
		textField[1].setText(tempStudent.getSurname());
		textField[2].setText(tempStudent.getName());
		textField[3].setText(tempStudent.getPatronymic());
		textField[4].setText(tempStudent.getGroup());
		textField[5].setText(Integer.toString(tempStudent.getCourse()));
		textField[6].setText(tempStudent.getAdress());
		textField[7].setText(tempStudent.getPhoneNumber());
		if (tempStudent.gotDormitory() == true) {
			textField[8].setText("Да");
		}
		else if (tempStudent.gotDormitory() == false) {
			textField[8].setText("Нет");
		}
		tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Only the third column
				return column == 3;
			}
		};
		tableModel.setColumnIdentifiers(columnsHeader);
		// Заполнение таблицы
		for (int i = grades.size() - 1; i >= 0; i--) {
			Grade temp = grades.get(i);
			tableModel.insertRow(0, new Object[] { Integer.toString(temp.getNumber()),
					temp.getSubjNameBySubjID(temp.getSubjID()), temp.getMark()});
		}
		Connect.clearGradesArray();
		mainTable = CreateTable(tableModel);
		pane = CreateScrollPane(mainTable, 25, 400, 550, 250);
		mainFrame.add(pane);
		button[0].addActionListener(new ActionListener() {
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
