package studentCard;

// Класс, описывающий студента

public class Student {
	private String surname; // Фамилия
	private String name; // Имя
	private String patronymic; // Отчество
	private int index; // Номер зачетной книжки
	private String group; // Группа студента
	private int course; // Курс студента
	private String adress; // Адрес студента
	private String phoneNumber; // Номер телефона
	private boolean hasDormitory; // Проживает ли в общежитии

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
