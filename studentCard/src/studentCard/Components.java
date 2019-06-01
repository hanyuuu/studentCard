package studentCard;

/**
*Импорт библиотек, описывающих элементы графического интерфейса
*/
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

public class Components extends JFrame {
	/**
	 * Метод, создающий и возвращающий фрейм на основе переданных ему параметров
	 * 
	 * @param FrameName Название рамки
	 * @param Panel     Панель, прикрепляемая к фрейму
	 * @param w         Ширина фрейма
	 * @param h         Высота фрейма
	 * @return Фрейм с примененными к нему параметрами
	 */
	protected JFrame CreateMainFrame(String FrameName, JPanel Panel, int w, int h) {
		JFrame mainFrame = new JFrame(FrameName);
		mainFrame.setSize(w, h);
		mainFrame.setContentPane(Panel);
		mainFrame.setResizable(false);
		return mainFrame;
	}

	/**
	 * Метод, создающий и возвращающий лейбл на основе переданных ему параметров
	 * 
	 * @param name Надпись, отображаемая лейблом
	 * @param x    Начальная точка местонахождения лейбла по абсциссе относительно
	 *             левого верхнего угла панели
	 * @param y    Начальная точка местонахождения лейбла по ординате относительно
	 *             левого верхнего угла панели
	 * @param w    Ширина лейбла
	 * @param h    Высота лейбла
	 * @return Лейбл с примененными к нему параметрами
	 */
	protected JLabel CreateLabel(String name, int x, int y, int w, int h) {
		JLabel Label = new JLabel(name);
		Label.setBounds(x, y, w, h);
		return Label;
	}

	/**
	 * Метод, создающий и возвращающий текстовое поле на основе переданных ему
	 * параметров
	 * 
	 * @param x Начальная точка местонахождения текстового поля по абсциссе
	 *          относительно левого верхнего угла панели
	 * @param y Начальная точка местонахождения текстового поля по ординате
	 *          относительно левого верхнего угла панели
	 * @param w Ширина текстового поля
	 * @param h Высота текстового поля
	 * @return Текстовое поле с примененными к нему параметрами
	 */
	protected JTextField CreateTextField(int x, int y, int w, int h) {
		JTextField textField = new JTextField();
		textField.setBounds(x, y, w, h);
		return textField;
	}

	/**
	 * Метод, создающий и применяющий свободный способ расположения элементов к
	 * панели
	 * 
	 * @return Возвращаемая панели 3
	 */
	protected JPanel CreateMainPanel() {
		JPanel MainPanel = new JPanel();
		MainPanel.setLayout(null);
		return MainPanel;
	}

	/**
	 * Метод, создающий и возвращающий чекбокс на основе переданных ему параметров
	 * 
	 * @param name Надпись чекбокса
	 * @param x    Начальная точка местонахождения чекбокса по абсциссе относительно
	 *             левого верхнего угла панели
	 * @param y    Начальная точка местонахождения чекбокса по ординате относительно
	 *             левого верхнего угла панели
	 * @param w    Ширина чекбокса
	 * @param h    Высота чекбокса
	 * @return Чекбокс с примененными к нему параметрами
	 */
	protected JCheckBox CreateCheckBox(String name, int x, int y, int w, int h) {
		JCheckBox CheckBox = new JCheckBox();
		CheckBox.setText(name);
		CheckBox.setBounds(x, y, w, h);
		return CheckBox;
	}

	protected JComboBox CreateComboBox(String[] items, int x, int y, int w, int h) {
		JComboBox ComboBox = new JComboBox(items);
		ComboBox.setBounds(x, y, w, h);
		ComboBox.setEditable(false);
		return ComboBox;
	}

	/**
	 * Метод, создающий и возвращающий переключатель на основе переданных ему
	 * параметров
	 * 
	 * @param name Надпись переключателя
	 * @param x    Начальная точка местонахождения переключателя по абсциссе
	 *             относительно левого верхнего угла панели
	 * @param y    Начальная точка местонахождения переключателя по ординате
	 *             относительно левого верхнего угла панели
	 * @param w    Ширина переключателя
	 * @param h    Высота переключателя
	 * @return Переключатель с примененными к нему параметрами
	 */
	protected JRadioButton CreateRadioButton(String name, int x, int y, int w, int h) {
		JRadioButton RadioButton = new JRadioButton();
		RadioButton.setText(name);
		RadioButton.setBounds(x, y, w, h);
		return RadioButton;
	}

	/**
	 * Метод, создающий панель с рамкой на основе переданных ему параметров
	 * 
	 * @param name Надпись, используемая на рамке
	 * @param x    Начальная точка местонахождения панели по абсциссе относительно
	 *             левого верхнего угла фрейма
	 * @param y    Начальная точка местонахождения панели по ординате относительно
	 *             левого верхнего угла фрейма
	 * @param w    Ширина панели
	 * @param h    Высота панели
	 * @return Панель с примененными к ней параметрами
	 */
	protected JPanel CreateSmallPanel(String name, int x, int y, int w, int h) {
		JPanel MainPanel = new JPanel();
		MainPanel.setLayout(null);
		MainPanel.setBorder(BorderFactory.createTitledBorder(name));
		MainPanel.setBounds(x, y, w, h);
		return MainPanel;
	}

	/**
	 * Метод, создающий кнопку на основе переданных ему параметров
	 * 
	 * @param name Надпись на кнопке
	 * @param x    Начальная точка местонахождения кнопки по абсциссе относительно
	 *             левого верхнего угла панели
	 * @param y    Начальная точка местонахождения кнопки по ординате относительно
	 *             левого верхнего угла панели
	 * @param h    Ширина кнопки
	 * @param w    Высота кнопки 5
	 * @return Кнопка с примененными к ней параметрами
	 */
	protected JButton CreateButton(String name, int x, int y, int h, int w) {
		JButton a = new JButton(name);
		a.setBounds(x, y, h, w);
		return a;
	}

	protected JScrollPane CreateScrollPane(JTable table, int x, int y, int h, int w) {
		JScrollPane a = new JScrollPane(table);
		a.setBounds(x, y, h, w);
		return a;
	}

	protected JTable CreateTable(TableModel model/*, int x, int y, int h, int w*/) {
		JTable a = new JTable(model);
		//a.setBounds(x, y, h, w);
		return a;
	}

	/**
	 * Метод, создающий диалог сообщения
	 * 
	 * @param Panel          Панель, прикрепляемая к фрейму
	 * @param mainMessage    Надпись на теле сообщения
	 * @param previewMessage Надпись, используемая в названии окна сообщения
	 */
	protected void callMessage(JPanel Panel, String mainMessage, String previewMessage) {
		JOptionPane.showMessageDialog(Panel, mainMessage, previewMessage, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Метод, производящий конвертирование числа в тип с плавающей точкой
	 */
	protected double toDouble(String a) {
		return Double.parseDouble(a);
	}
}