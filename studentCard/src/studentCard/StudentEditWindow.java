package studentCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class StudentEditWindow extends Components {
	private textFieldFilter filter;
	private int studentID;
	private Student tempStudent;
	private JPanel mainPanel;
	private JFrame mainFrame;
	private String[] buttonNames = { "Сохранить", "Выйти" };
	private JButton[] button = new JButton[2];
	private JTextField[] textField = new JTextField[9];
	private JRadioButton[] radiobutton = new JRadioButton[2];
	private int[] radiobuttonBounds = { 250, 327, 120, 50, 400, 327, 80, 50 };
	private String[] radiobuttonNames = { "Да", "Нет" };
	private String[] labelNames = { "Идентификационный номер", "Фамилия", "Имя", "Отчество", "Группа", "Курс", "Адрес",
			"Номер телефона", "Имеет общежитие" };
	private int[] labelBounds = { 10, 5, 280, 50, 10, 45, 280, 50, 10, 85, 280, 50, 10, 125, 280, 50, 10, 165, 280, 50,
			10, 205, 280, 50, 10, 245, 280, 50, 10, 285, 280, 50, 10, 325, 280, 50 };
	private int[] textFieldsBounds = { 250, 17, 200, 25, 250, 57, 200, 25, 250, 97, 200, 25, 250, 137, 200, 25, 250,
			177, 200, 25, 250, 217, 200, 25, 250, 257, 200, 25, 250, 297, 200, 25 };// 250, 337, 200, 25
	private JLabel[] label = new JLabel[9];
	private int[] buttonBounds = { 250, 400, 200, 25 };

	public StudentEditWindow(int studentID) throws ClassNotFoundException, SQLException {
		this.studentID = studentID;
		Connect.Conn();
		Connect.CreateDB();
		tempStudent = Connect.ReadStudentByID(studentID);
		Connect.CloseDB();
		mainPanel = CreateMainPanel();
		mainFrame = CreateMainFrame("Личная карточка студента (" + tempStudent.getSurname() + " "
				+ tempStudent.getName().charAt(0) + "." + tempStudent.getPatronymic().charAt(0) + ".)", mainPanel, 500,
				500);
		for (int i = 0; i < 9; i++) {
			label[i] = CreateLabel(labelNames[i], labelBounds[i * 4], labelBounds[i * 4 + 1], labelBounds[i * 4 + 2],
					labelBounds[i * 4 + 3]);
			mainFrame.add(label[i]);
			if (i < 8) {
				textField[i] = CreateTextField(textFieldsBounds[i * 4], textFieldsBounds[i * 4 + 1],
						textFieldsBounds[i * 4 + 2], textFieldsBounds[i * 4 + 3]);
				mainFrame.add(textField[i]);
			}
			if (i < 1) {
				button[i] = CreateButton(buttonNames[i], buttonBounds[i * 4], buttonBounds[i * 4 + 1],
						buttonBounds[i * 4 + 2], buttonBounds[i * 4 + 3]);
				mainFrame.add(button[i]);
			}
			if (i < 2) {
				radiobutton[i] = CreateRadioButton(radiobuttonNames[i], radiobuttonBounds[i * 4],
						radiobuttonBounds[i * 4 + 1], radiobuttonBounds[i * 4 + 2], radiobuttonBounds[i * 4 + 3]);
				mainFrame.add(radiobutton[i]);
			}
		}
		filter = new textFieldFilter();
		filter.PTextFilter(textField[5], 1);
		textField[0].setText(Integer.toString(tempStudent.getIndex()));
		textField[0].setEditable(false);
		textField[1].setText(tempStudent.getSurname());
		textField[2].setText(tempStudent.getName());
		textField[3].setText(tempStudent.getPatronymic());
		textField[4].setText(tempStudent.getGroup());
		textField[5].setText(Integer.toString(tempStudent.getCourse()));
		textField[6].setText(tempStudent.getAdress());
		textField[7].setText(tempStudent.getPhoneNumber());
		selectRadioBtn(radiobutton[0], radiobutton[1]);
		if (tempStudent.gotDormitory() == true) {
			radiobutton[0].setSelected(true);
		} else if (tempStudent.gotDormitory() == false) {
			radiobutton[1].setSelected(true);
		}
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField[1].getText().equals("") || textField[2].getText().equals("")
						|| textField[3].getText().equals("") || textField[4].getText().equals("")
						|| textField[5].getText().equals("") || textField[6].getText().equals("")
						|| textField[7].getText().equals("")) {
					callMessage(mainPanel, "Проверьте правильность введенных данных", "Внимание!");
				} else {
					try {
						boolean tempDorm = false;
						if (radiobutton[0].isSelected()) {
							tempDorm = true;
						} else if (radiobutton[1].isSelected()) {
							tempDorm = false;
						}
						Connect.Conn();
						Connect.CreateDB();
						Connect.EditStudentByID(Integer.valueOf(textField[0].getText()), textField[1].getText(),
								textField[2].getText(), textField[3].getText(), textField[4].getText(),
								Integer.valueOf(textField[5].getText()), textField[6].getText(), textField[7].getText(),
								tempDorm);
						Connect.CloseDB();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					try {
						StudentListWindow.updateTable();
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

	public void selectRadioBtn(final JRadioButton radio1, final JRadioButton radio2) {
		radio1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (radio1.isSelected())
					radio2.setSelected(false);
			}
		});
		radio2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (radio2.isSelected())
					radio1.setSelected(false);
			}
		});
	}
}
