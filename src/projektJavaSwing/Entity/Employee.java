package projektJavaSwing.Entity;

public class Employee {
	private int id;
	private String firstName;
	private String lastName;
	private String pesel;
	private String city;
	private int salary;
	private int seniority;
	
	
	public Employee() {
	}
	
	

	public Employee(int id) {
		super();
		this.id = id;
	}



	public Employee(String firstName, String lastName, String pesel,String city, int salary, int seniority) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.setPesel(pesel);
		this.city = city;
		this.salary = salary;
		this.seniority = seniority;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getSeniority() {
		return seniority;
	}

	public void setSeniority(int seniority) {
		this.seniority = seniority;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", city=" + city
				+ ", salary=" + salary + ", seniority=" + seniority + "]";
	}



	public String getPesel() {
		return pesel;
	}



	public void setPesel(String pesel) {
		this.pesel = pesel;
	}
	
	
	
	
}
