package studentCard;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class SpecificStudentGradeListWindow extends Components {
	private static Object[] columnsHeader = new String[] { "№", "Предмет", "Оценка", "Редактировать" };
	JScrollPane pane;
	private static DefaultTableModel tableModel;
	private JPanel mainPanel;
	private JFrame mainFrame;
	public static JTable mainTable;
	private String[] buttonNames = { "Выйти из формы", "Выставить оценку", "Удалить" };
	private String[] labelNames = { "Введите номер оценки и нажмите кнопку 'Удалить'", "№ Оценки для удаления:" };
	private int[] labelBounds = { 25, 275, 300, 25, 25, 300, 250, 25 };
	private JLabel[] label = new JLabel[2];
	private JButton[] button = new JButton[3];
	private int[] buttonBounds = { 450, 345, 135, 25, 300, 345, 145, 25, 450, 300, 135, 25 };
	private int[] textFieldsBounds = { 300, 300, 145, 25 };
	private JTextField[] textField = new JTextField[1];
	private ArrayList<Grade> grades;
	private static int studentID;
	private Student tempStudent;
	private textFieldFilter filter;
	public SpecificStudentGradeListWindow(int studentID) throws ClassNotFoundException, SQLException {
		this.studentID = studentID;
		Connect.Conn();
		Connect.CreateDB();
		tempStudent = Connect.ReadStudentByID(studentID);
		Connect.ReadGradesByStudent(studentID);
		grades = Connect.getGradesArray();
		Connect.CloseDB();
		mainPanel = CreateMainPanel();
		mainFrame = CreateMainFrame("Управление оценками студента " + tempStudent.getSurname() + " "
				+ tempStudent.getName().charAt(0) + "." + tempStudent.getPatronymic().charAt(0) + ".", mainPanel, 600,
				425);
		for (int i = 0; i < 3; i++) {
			button[i] = CreateButton(buttonNames[i], buttonBounds[i * 4], buttonBounds[i * 4 + 1],
					buttonBounds[i * 4 + 2], buttonBounds[i * 4 + 3]);
			mainPanel.add(button[i]);
			if (i < 2) {
				label[i] = CreateLabel(labelNames[i], labelBounds[i * 4], labelBounds[i * 4 + 1],
						labelBounds[i * 4 + 2], labelBounds[i * 4 + 3]);
				mainFrame.add(label[i]);
			}
			if (i < 1) {
				textField[i] = CreateTextField(textFieldsBounds[i * 4], textFieldsBounds[i * 4 + 1],
						textFieldsBounds[i * 4 + 2], textFieldsBounds[i * 4 + 3]);
				mainFrame.add(textField[i]);
			}
		}
		// Создание таблицы с ползунком
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
					temp.getSubjNameBySubjID(temp.getSubjID()), temp.getMark(), "Редактировать" });
		}
		Connect.clearGradesArray();
		mainTable = CreateTable(tableModel);
		pane = CreateScrollPane(mainTable, 25, 20, 550, 250);
		mainFrame.add(pane);
		mainTable.getColumn("Редактировать").setCellRenderer(new ButtonRendererInSSGLW());
		mainTable.getColumn("Редактировать").setCellEditor(new ButtonEditorInSSGLW(new JCheckBox()));
		// mainTable.setEnabled(false);
		filter = new textFieldFilter();
		filter.PTextFilter(textField[0], 5);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kill();
			}
		});
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SpecificStudentGradeAddWindow AddWindow = new SpecificStudentGradeAddWindow(studentID);
					AddWindow.makeActive();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connect.Conn();
					Connect.CreateDB();
					Connect.DeleteGrade(Integer.parseInt(textField[0].getText()));
					Connect.CloseDB();
					updateTable();
					textField[0].setText("");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public static void updateTable() throws ClassNotFoundException, SQLException {
		Connect.Conn();
		Connect.CreateDB();
		Connect.clearGradesArray();
		Connect.ReadGradesByStudent(studentID);
		ArrayList<Grade> tempArray = Connect.getGradesArray();
		DefaultTableModel NEWtableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Only the third column
				return column == 3;
			}
		};
		NEWtableModel.setColumnIdentifiers(columnsHeader);
		DefaultTableModel dm = (DefaultTableModel) mainTable.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged();
		for (int i = tempArray.size() - 1; i >= 0; i--) {
			Grade temp = tempArray.get(i);
			tableModel.insertRow(0, new Object[] { Integer.toString(temp.getNumber()),
					temp.getSubjNameBySubjID(temp.getSubjID()), temp.getMark(), "Редактировать" });
		}
		mainTable.setModel(dm);
		Connect.clearGradesArray();
		Connect.CloseDB();
		System.out.println("Таблица была обновлена");
	}

	public static void deleteMark() {

	}

	public void makeActive() {
		mainFrame.setVisible(true);
	}

	public void kill() {
		mainFrame.dispose();
	}
}

class ButtonRendererInSSGLW extends JButton implements TableCellRenderer {

	public ButtonRendererInSSGLW() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(UIManager.getColor("Button.background"));
		}
		setText((value == null) ? "" : value.toString());
		return this;
	}
}

class ButtonEditorInSSGLW extends DefaultCellEditor {

	protected JButton button;
	private String label;
	private boolean isPushed;
	private int rowNum;

	public ButtonEditorInSSGLW(JCheckBox checkBox) {
		super(checkBox);
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground(table.getForeground());
			button.setBackground(table.getBackground());
		}
		rowNum = row + 1;
		label = (value == null) ? "" : value.toString();
		button.setText(label);
		isPushed = true;
		return button;
	}

	@Override
	public Object getCellEditorValue() {
		if (isPushed) {
			/*
			 * System.out.println("Редактируем оценку" +
			 * SpecificStudentGradeListWindow.mainTable.getModel().getValueAt(rowNum - 1,
			 * 0).toString());
			 */
			SpecificStudentGradeEditWindow Window;
			try {
				Window = new SpecificStudentGradeEditWindow(Integer.valueOf(
						SpecificStudentGradeListWindow.mainTable.getModel().getValueAt(rowNum - 1, 0).toString()));
				Window.makeActive();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		isPushed = false;
		return label;
	}
}
