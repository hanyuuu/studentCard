package studentCard;

import java.sql.SQLException;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connect.Conn();
		Connect.CreateDB();
		Connect.ReadStudent();
		Connect.ReadSubject();
		Connect.ReadGrades();
		Connect.CloseDB();
		MainWindow window = new MainWindow();
	}
}
