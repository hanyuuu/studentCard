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

public class SubjectAddWindow extends Components {
	private Subject tempSubject;
	private JPanel mainPanel;
	private JFrame mainFrame;
	private String[] buttonNames = { "Сохранить", "Выйти" };
	private JButton[] button = new JButton[2];
	private JTextField[] textField = new JTextField[1];
	private String[] labelNames = { "Название предмета" };
	private int[] labelBounds = { 10, 5, 280, 50 };
	private int[] textFieldsBounds = { 250, 17, 200, 25 };// 250, 337, 200, 25
	private JLabel[] label = new JLabel[1];
	private int[] buttonBounds = { 250, 130, 200, 25 };

	public SubjectAddWindow() throws ClassNotFoundException, SQLException {
		mainPanel = CreateMainPanel();
		mainFrame = CreateMainFrame("Добавление предмета", mainPanel, 500, 200);
		for (int i = 0; i < 1; i++) {
			label[i] = CreateLabel(labelNames[i], labelBounds[i * 4], labelBounds[i * 4 + 1], labelBounds[i * 4 + 2],
					labelBounds[i * 4 + 3]);
			mainFrame.add(label[i]);
			textField[i] = CreateTextField(textFieldsBounds[i * 4], textFieldsBounds[i * 4 + 1],
					textFieldsBounds[i * 4 + 2], textFieldsBounds[i * 4 + 3]);
			mainFrame.add(textField[i]);
			button[i] = CreateButton(buttonNames[i], buttonBounds[i * 4], buttonBounds[i * 4 + 1],
					buttonBounds[i * 4 + 2], buttonBounds[i * 4 + 3]);
			mainFrame.add(button[i]);
		}
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField[0].getText().equals("")) {
					callMessage(mainPanel, "Проверьте правильность введенных данных", "Внимание!");
				} else {
					try {
						Connect.Conn();
						Connect.CreateDB();
						Connect.AddSubjectByID(textField[0].getText());
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
