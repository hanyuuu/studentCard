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

public class SubjectEditWindow extends Components {
	private int subjectID;
	private Subject tempSubject;
	private JPanel mainPanel;
	private JFrame mainFrame;
	private String[] buttonNames = { "Сохранить", "Выйти" };
	private JButton[] button = new JButton[2];
	private JTextField[] textField = new JTextField[2];
	private String[] labelNames = { "Идентификационный номер", "Название предмета"};
	private int[] labelBounds = { 10, 5, 280, 50, 10, 45, 280, 50 };
	private int[] textFieldsBounds = { 250, 17, 200, 25, 250, 57, 200, 25};// 250, 337, 200, 25
	private JLabel[] label = new JLabel[2];
	private int[] buttonBounds = { 250, 130, 200, 25 };

	public SubjectEditWindow(int subjectID) throws ClassNotFoundException, SQLException {
		this.subjectID = subjectID;
		Connect.Conn();
		Connect.CreateDB();
		tempSubject = Connect.ReadSubjectByID(subjectID);
		Connect.CloseDB();
		mainPanel = CreateMainPanel();
		mainFrame = CreateMainFrame("Редактирование предмета " + tempSubject.getName(), mainPanel, 500, 200);
		for (int i = 0; i < 2; i++) {
			label[i] = CreateLabel(labelNames[i], labelBounds[i * 4], labelBounds[i * 4 + 1], labelBounds[i * 4 + 2],
					labelBounds[i * 4 + 3]);
			mainFrame.add(label[i]);
				textField[i] = CreateTextField(textFieldsBounds[i * 4], textFieldsBounds[i * 4 + 1],
						textFieldsBounds[i * 4 + 2], textFieldsBounds[i * 4 + 3]);
				mainFrame.add(textField[i]);
			if (i < 1) {
				button[i] = CreateButton(buttonNames[i], buttonBounds[i * 4], buttonBounds[i * 4 + 1],
						buttonBounds[i * 4 + 2], buttonBounds[i * 4 + 3]);
				mainFrame.add(button[i]);
			}
		}
		textField[0].setText(Integer.toString(tempSubject.getNumber()));
		textField[0].setEditable(false);
		textField[1].setText(tempSubject.getName());
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField[1].getText().equals("")) {
					callMessage(mainPanel, "Проверьте правильность введенных данных", "Внимание!");
				} else {
					try {
						Connect.Conn();
						Connect.CreateDB();
						Connect.EditSubjectByID(Integer.valueOf(textField[0].getText()), textField[1].getText());
						Connect.CloseDB();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					try {
						SubjectListWindow.updateTable();
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
