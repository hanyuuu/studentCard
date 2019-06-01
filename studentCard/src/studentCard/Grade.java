package studentCard;

import java.sql.SQLException;

public class Grade {
	private int number;
	private int studID;
	private int subjID;
	private int mark;

	public Grade(int number, int studID, int subjID, int mark) {
		this.number = number;
		this.studID = studID;
		this.subjID = subjID;
		this.mark = mark;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getStudID() {
		return studID;
	}

	public void setStudID(int studID) {
		this.studID = studID;
	}

	public int getSubjID() {
		return subjID;
	}

	public void setSubjID(int subjID) {
		this.subjID = subjID;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public String getSubjNameBySubjID(int subjID) throws ClassNotFoundException, SQLException {
		Connect.Conn();
		Connect.CreateDB();
		Subject temp = Connect.ReadSubjectByID(subjID);
		Connect.CloseDB();
		return temp.getName();
	}
}
