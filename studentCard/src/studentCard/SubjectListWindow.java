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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class SubjectListWindow extends Components {
	private static Object[] columnsHeader = new String[] { "№", "Название предмета", "Редактировать" };
	JScrollPane pane;
	private static DefaultTableModel tableModel;
	private JPanel mainPanel;
	private JFrame mainFrame;
	public static JTable mainTable;
	private String[] buttonNames = { "Выйти из формы", "Добавить предмет" };
	private JButton[] button = new JButton[2];
	private int[] buttonBounds = { 450, 325, 135, 25, 300, 325, 145, 25 };
	ArrayList<Subject> subjects = Connect.getSubjectsArray();

	public SubjectListWindow() {
		mainPanel = CreateMainPanel();
		mainFrame = CreateMainFrame("Управление предметами", mainPanel, 600, 400);
		for (int i = 0; i < 2; i++) {
			button[i] = CreateButton(buttonNames[i], buttonBounds[i * 4], buttonBounds[i * 4 + 1],
					buttonBounds[i * 4 + 2], buttonBounds[i * 4 + 3]);
			mainPanel.add(button[i]);
		}

		// Создание таблицы с ползунком
		tableModel = new DefaultTableModel() {
			@Override
			   public boolean isCellEditable(int row, int column) {
			       //Only the third column
			       return column == 2;
			   }
		};
		tableModel.setColumnIdentifiers(columnsHeader);
		// Заполнение таблицы
		for (int i = subjects.size() - 1; i >= 0; i--) {
			Subject temp = subjects.get(i);
			tableModel.insertRow(0, new Object[] { Integer.toString(temp.getNumber()), temp.getName(), "Редактировать" });
		}
		mainTable = CreateTable(tableModel);
		pane = CreateScrollPane(mainTable, 25, 20, 550, 250);
		mainFrame.add(pane);
		mainTable.getColumn("Редактировать").setCellRenderer(new ButtonRendererInSubj());
		mainTable.getColumn("Редактировать").setCellEditor(new ButtonEditorInSubj(new JCheckBox()));
		//mainTable.setEnabled(false);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kill();
			}
		});
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SubjectAddWindow AddWindow = new SubjectAddWindow();
					AddWindow.makeActive();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public static void updateTable() throws ClassNotFoundException, SQLException {
		Connect.Conn();
		Connect.CreateDB();
		Connect.clearSubjectsArray();
		Connect.ReadSubject();
		ArrayList<Subject> tempArray = Connect.getSubjectsArray();
		DefaultTableModel NEWtableModel = new DefaultTableModel(){
			@Override
			   public boolean isCellEditable(int row, int column) {
			       //Only the third column
			       return column == 6;
			   }
			};
		NEWtableModel.setColumnIdentifiers(columnsHeader);
		DefaultTableModel dm = (DefaultTableModel) mainTable.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged();
		for (int i = tempArray .size() - 1; i >= 0; i--) {
			Subject temp = tempArray.get(i);
			tableModel.insertRow(0, new Object[] { Integer.toString(temp.getNumber()), temp.getName(), "Редактировать" });
		}
		mainTable.setModel(dm);
		Connect.CloseDB();
		System.out.println("Таблица была обновлена");
	}

	public void makeActive() {
		mainFrame.setVisible(true);
	}

	public void kill() {
		mainFrame.dispose();
	}
}

class ButtonRendererInSubj extends JButton implements TableCellRenderer {

	public ButtonRendererInSubj() {
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

class ButtonEditorInSubj extends DefaultCellEditor {

	protected JButton button;
	private String label;
	private boolean isPushed;
	private int rowNum;

	public ButtonEditorInSubj(JCheckBox checkBox) {
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
			System.out.println("Редактируем предмет" + SubjectListWindow.mainTable.getModel().getValueAt(rowNum - 1, 0).toString());
			SubjectEditWindow EditWindow;
			try {
				EditWindow = new SubjectEditWindow(Integer.valueOf(SubjectListWindow.mainTable.getModel().getValueAt(rowNum - 1, 0).toString()));
				EditWindow.makeActive();
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
