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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class GradeStudentListWindow extends Components {
	private static Object[] columnsHeader = new String[] { "№ЗК", "Фамилия", "Имя", "Отчество", "Группа", "Курс",
			"Выбрать" };
	JScrollPane pane;
	private static DefaultTableModel tableModel;
	private JPanel mainPanel;
	private JFrame mainFrame;
	public static JTable mainTable;
	private JLabel mainLabel;
	private String[] buttonNames = { "Выйти из формы" };
	private JButton[] button = new JButton[1];
	private int[] buttonBounds = { 450, 325, 135, 25};
	ArrayList<Student> students = Connect.getStudentsArray();

	public GradeStudentListWindow() {
		mainPanel = CreateMainPanel();
		mainFrame = CreateMainFrame("Управление оценками студентами", mainPanel, 600, 400);
		for (int i = 0; i < 1; i++) {
			button[i] = CreateButton(buttonNames[i], buttonBounds[i * 4], buttonBounds[i * 4 + 1],
					buttonBounds[i * 4 + 2], buttonBounds[i * 4 + 3]);
			mainPanel.add(button[i]);
		}

		// Создание таблицы с ползунком
		tableModel = new DefaultTableModel() {
			@Override
			   public boolean isCellEditable(int row, int column) {
			       //Only the third column
			       return column == 6;
			   }
		};
		tableModel.setColumnIdentifiers(columnsHeader);
		// Заполнение таблицы
		for (int i = students.size() - 1; i >= 0; i--) {
			Student temp = students.get(i);
			tableModel.insertRow(0, new Object[] { Integer.toString(temp.getIndex()), temp.getSurname(), temp.getName(),
					temp.getPatronymic(), temp.getGroup(), Integer.toString(temp.getCourse()), "Выбрать" });
		}
		mainTable = CreateTable(tableModel);
		pane = CreateScrollPane(mainTable, 25, 50, 550, 250);
		mainFrame.add(pane);
		mainTable.getColumn("Выбрать").setCellRenderer(new ButtonRendererInGrade());
		mainTable.getColumn("Выбрать").setCellEditor(new ButtonEditorInGrade(new JCheckBox()));
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

class ButtonRendererInGrade extends JButton implements TableCellRenderer {

	public ButtonRendererInGrade() {
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

class ButtonEditorInGrade extends DefaultCellEditor {

	protected JButton button;
	private String label;
	private boolean isPushed;
	private int rowNum;

	public ButtonEditorInGrade(JCheckBox checkBox) {
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
			System.out.println("Редактируем студента"
					+ GradeStudentListWindow.mainTable.getModel().getValueAt(rowNum - 1, 0).toString());
			SpecificStudentGradeListWindow StudentGradeWindow;
			try {
				StudentGradeWindow = new SpecificStudentGradeListWindow(Integer.valueOf(GradeStudentListWindow.mainTable.getModel().getValueAt(rowNum - 1, 0).toString()));
				StudentGradeWindow.makeActive();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		isPushed = false;
		return label;
	}
}