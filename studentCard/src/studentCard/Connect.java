package studentCard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Connect {
	public static Connection conn;
	public static Statement statmt;
	public static ResultSet resSet;
	private static ArrayList<Student> students = new ArrayList<Student>();
	private static ArrayList<Subject> subjects = new ArrayList<Subject>();
	private static ArrayList<Grade> grades = new ArrayList<Grade>();

	/*
	 * */
	public static void Conn() throws ClassNotFoundException, SQLException {
		conn = null;
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:records.s3db");
	}

	public static void CreateDB() throws ClassNotFoundException, SQLException {
		statmt = conn.createStatement();
		statmt.execute("CREATE TABLE if not exists 'Students' (	'stud_id' INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "	'surname' varchar(100) NOT NULL, 'name' varchar(100) NOT NULL,"
				+ "	'patronymic' varchar(100) NOT NULL, 'studGroup' varchar(10) NOT NULL,"
				+ "	`course` int(1) NOT NULL, 'adress' varchar(200) NOT NULL,"
				+ "	`phoneNumber` varchar(11) NOT NULL, `hasDormitory` BOOLEAN NOT NULL);");
		statmt.execute(
				"CREATE TABLE if not exists 'Subjects' ('subj_id' INTEGER PRIMARY KEY AUTOINCREMENT,'subj_name' varchar(200) NOT NULL);");
		statmt.execute("CREATE TABLE if not exists 'Grade' ('id' INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "'studentID' int NOT NULL,'subjectID' int NOT NULL,`mark` int(1) NOT NULL,"
				+ "FOREIGN KEY (studentID) REFERENCES Students(id),"
				+ "FOREIGN KEY (subjectID) REFERENCES Subjects(id)););");
	}

	public static void WriteDB() throws SQLException {
		statmt.execute("INSERT INTO `Students` (`stud_id`, `surname`, `name`, `patronymic`, `studGroup`,"
				+ "`course`, `adress`, `phoneNumber`, `hasDormitory`) VALUES (NULL,"
				+ "'Юрлов', 'Михаил', 'Викторович', 'ПИ-315', '3',"
				+ "'ул. Первомайская, д. 75, кв. 31', '79279402064', '0');");
		statmt.execute("INSERT INTO `Subjects` (`subj_id`, `subj_name`) VALUES (NULL, 'Программная инженерия');");
		statmt.execute("INSERT INTO `Grade` (`id`, `studentID`, `subjectID`, `mark`) VALUES (NULL, '1', '1', '5');");
	}

	// Выборка отдельного студента по ID
	public static Student ReadStudentByID(int studentID) throws ClassNotFoundException, SQLException {
		System.out.println("");
		System.out.println(
				"==============================================================================================================");
		System.out.println("Считываю студента с ID = " + studentID);
		resSet = statmt.executeQuery(
				"SELECT `stud_id`, `surname`, `name`, `patronymic`, `studGroup`, `course`, `adress`, `phoneNumber`, `hasDormitory` FROM Students WHERE stud_id = "
						+ studentID);
		while (resSet.next()) {
			int stud_id = resSet.getInt("stud_id");
			String surname = resSet.getString("surname");
			String name = resSet.getString("name");
			String patronymic = resSet.getString("patronymic");
			String studGroup = resSet.getString("studGroup");
			int course = resSet.getInt("course");
			String adress = resSet.getString("adress");
			String phoneNumber = resSet.getString("phoneNumber");
			boolean hasDormitory = resSet.getBoolean("hasDormitory");
			Student tempStudent = new Student(surname, name, patronymic, stud_id, studGroup, course, adress,
					phoneNumber, hasDormitory);
			System.out.println(stud_id + " " + surname + " " + name + " " + patronymic + " " + studGroup + " " + course
					+ " " + adress + " " + phoneNumber + " " + hasDormitory);
			return tempStudent;
		}
		return null;
	}

	public static Subject ReadSubjectByID(int subjectID) throws ClassNotFoundException, SQLException {
		System.out.println("");
		System.out.println(
				"==============================================================================================================");
		System.out.println("Считываю предмет с ID = " + subjectID);
		resSet = statmt.executeQuery("SELECT `subj_id`, `subj_name` FROM Subjects WHERE subj_id = " + subjectID);
		while (resSet.next()) {
			int subj_id = resSet.getInt("subj_id");
			String subj_name = resSet.getString("subj_name");
			Subject tempSubject = new Subject(subj_id, subj_name);
			System.out.println(subj_id + " " + subj_name);
			return tempSubject;
		}
		return null;
	}

	//
	public static void EditStudentByID(int stud_id, String surname, String name, String patronymic, String studGroup,
			int course, String adress, String phoneNumber, boolean hasDormitory)
			throws SQLException, ClassNotFoundException {
		int dorm = 0;
		if (hasDormitory == true) {
			dorm = 1;
		} else if (hasDormitory == false) {
			dorm = 0;
		}
		statmt.execute("UPDATE Students SET `surname` = '" + surname + "', `name`='" + name + "',`patronymic`='"
				+ patronymic + "', `studGroup`='" + studGroup + "', `course`='" + course + "', `adress`='" + adress
				+ "', `phoneNumber`='" + phoneNumber + "', `hasDormitory`='" + dorm + "' WHERE stud_id = " + stud_id
				+ ";");
	}

	public static void EditGradeByID(int ID, int subject, int mark) throws SQLException, ClassNotFoundException {
		statmt.execute(
				"UPDATE Grade SET `subjectID` = '" + subject + "', `mark`='" + mark + "' WHERE id = " + ID + ";");
	}

	public static void AddGradeByStudID(int studID, int subjID, int mark) throws SQLException, ClassNotFoundException {
		statmt.execute("INSERT INTO `Grade` (`id`, `studentID`, `subjectID`, `mark`) VALUES (NULL, '" + studID + "', '"
				+ subjID + "', '" + mark + "');");
	}

	public static void EditSubjectByID(int subjectID, String name) throws SQLException, ClassNotFoundException {
		statmt.execute("UPDATE Subjects SET `subj_name` = '" + name + "' WHERE subj_id = " + subjectID + ";");
	}
	
	public static void AddStudentByID(String surname, String name, String patronymic, String studGroup, int course,
			String adress, String phoneNumber, boolean hasDormitory) throws SQLException, ClassNotFoundException {
		int dorm = 0;
		if (hasDormitory == true) {
			dorm = 1;
		} else if (hasDormitory == false) {
			dorm = 0;
		}
		statmt.execute("INSERT INTO `Students` (`stud_id`, `surname`, `name`, `patronymic`, `studGroup`,"
				+ "`course`, `adress`, `phoneNumber`, `hasDormitory`) VALUES (NULL," + "'" + surname + "', '" + name
				+ "', '" + patronymic + "', '" + studGroup + "', '" + course + "'," + "'" + adress + "', '"
				+ phoneNumber + "', '" + dorm + "');");
	}

	public static void AddSubjectByID(String name) throws SQLException, ClassNotFoundException {
		statmt.execute("INSERT INTO `Subjects` (`subj_id`, `subj_name`) VALUES (NULL, '" + name + "');");
	}

	/* Подгрузка БД */
	// Считывание студентов
	public static void ReadStudent() throws ClassNotFoundException, SQLException {
		System.out.println("");
		System.out.println(
				"==============================================================================================================");
		System.out.println("Печатаю студентов");
		resSet = statmt.executeQuery(
				"SELECT `stud_id`, `surname`, `name`, `patronymic`, `studGroup`, `course`, `adress`, `phoneNumber`, `hasDormitory` FROM Students");
		while (resSet.next()) {
			int stud_id = resSet.getInt("stud_id");
			String surname = resSet.getString("surname");
			String name = resSet.getString("name");
			String patronymic = resSet.getString("patronymic");
			String studGroup = resSet.getString("studGroup");
			int course = resSet.getInt("course");
			String adress = resSet.getString("adress");
			String phoneNumber = resSet.getString("phoneNumber");
			boolean hasDormitory = resSet.getBoolean("hasDormitory");
			Student tempStudent = new Student(surname, name, patronymic, stud_id, studGroup, course, adress,
					phoneNumber, hasDormitory);
			students.add(tempStudent);
			System.out.println(stud_id + " " + surname + " " + name + " " + patronymic + " " + studGroup + " " + course
					+ " " + adress + " " + phoneNumber + " " + hasDormitory);
		}
	}

	// Считывание предметов
	public static void ReadSubject() throws ClassNotFoundException, SQLException {
		System.out.println("");
		System.out.println(
				"==============================================================================================================");
		System.out.println("Печатаю предметы");
		resSet = statmt.executeQuery("SELECT `subj_id`, `subj_name` FROM Subjects");
		while (resSet.next()) {
			int subj_id = resSet.getInt("subj_id");
			String subj_name = resSet.getString("subj_name");
			Subject tempSubject = new Subject(subj_id, subj_name);
			subjects.add(tempSubject);
			System.out.println(subj_id + " " + subj_name);
		}
	}

	// Считывание оценок
	public static void ReadGrades() throws ClassNotFoundException, SQLException {
		System.out.println("");
		System.out.println(
				"==============================================================================================================");
		System.out.println("Печатаю оценки");
		resSet = statmt.executeQuery(
				"SELECT Grade.id AS gradeID, Students.stud_id AS studID, Subjects.subj_id AS subjID, Grade.mark AS studentsMark FROM Grade INNER JOIN Students ON Grade.studentID = Students.stud_id INNER JOIN Subjects ON Grade.subjectID = Subjects.subj_id");
		while (resSet.next()) {
			int gradeID = resSet.getInt("gradeID");
			int studID = resSet.getInt("studID");
			int subjID = resSet.getInt("subjID");
			int studentsMark = resSet.getInt("studentsMark");
			System.out.println(gradeID + " " + studID + " " + subjID + " " + studentsMark);
		}
	}

	public static void ReadGradesByStudent(int studentID) throws ClassNotFoundException, SQLException {
		System.out.println("");
		System.out.println(
				"==============================================================================================================");
		System.out.println("Печатаю оценки студента " + studentID);
		resSet = statmt
				.executeQuery("SELECT id, studentID, subjectID, mark FROM Grade WHERE studentID = '" + studentID + "'");
		while (resSet.next()) {
			int gradeID = resSet.getInt("id");
			int studID = resSet.getInt("studentID");
			int subjID = resSet.getInt("subjectID");
			int studentsMark = resSet.getInt("mark");
			System.out.println(gradeID + " " + studID + " " + subjID + " " + studentsMark);
			Grade tempGrade = new Grade(gradeID, studID, subjID, studentsMark);
			grades.add(tempGrade);
		}
	}

	public static Grade ReadGradesByID(int ID) throws ClassNotFoundException, SQLException {
		System.out.println("");
		resSet = statmt.executeQuery("SELECT id, studentID, subjectID, mark FROM Grade WHERE id = '" + ID + "'");
		while (resSet.next()) {
			int gradeID = resSet.getInt("id");
			int studID = resSet.getInt("studentID");
			int subjID = resSet.getInt("subjectID");
			int studentsMark = resSet.getInt("mark");
			System.out.println(gradeID + " " + studID + " " + subjID + " " + studentsMark);
			Grade tempGrade = new Grade(gradeID, studID, subjID, studentsMark);
			return tempGrade;
		}
		return null;
	}
	
	public static void DeleteGrade(int id) throws ClassNotFoundException, SQLException {
		statmt.execute("DELETE FROM `Grade` WHERE id = " + id + ";");
	}
	
	public static ArrayList<Student> getStudentsArray() {
		return students;
	}

	public static ArrayList<Subject> getSubjectsArray() {
		return subjects;
	}

	public static ArrayList<Grade> getGradesArray() {
		return grades;
	}

	public static void clearStudentsArray() {
		students.clear();
	}

	public static void clearSubjectsArray() {
		subjects.clear();
	}

	public static void clearGradesArray() {
		grades.clear();
	}

	public static void CloseDB() throws ClassNotFoundException, SQLException {
		conn.close();
		statmt.close();
		resSet.close();
	}
}
