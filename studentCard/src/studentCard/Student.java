package studentCard;

// �����, ����������� ��������

public class Student {
	private String surname; // �������
	private String name; // ���
	private String patronymic; // ��������
	private int index; // ����� �������� ������
	private String group; // ������ ��������
	private int course; // ���� ��������
	private String adress; // ����� ��������
	private String phoneNumber; // ����� ��������
	private boolean hasDormitory; // ��������� �� � ���������

	public Student(String surname, String name, String patronymic, int index, String group, int course, String adress,
			String phoneNumber, boolean hasDormitory) {
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
		this.index = index;
		this.group = group;
		this.course = course;
		this.adress = adress;
		this.phoneNumber = phoneNumber;
		this.hasDormitory = hasDormitory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean gotDormitory() {
		return hasDormitory;
	}

	public void setDormitory(boolean hasDormitory) {
		this.hasDormitory = hasDormitory;
	}
}
