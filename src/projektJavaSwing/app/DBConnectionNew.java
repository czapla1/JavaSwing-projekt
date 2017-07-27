package projektJavaSwing.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JPasswordField;

import projektJavaSwing.Entity.Employee;
import projektJavaSwing.Entity.Login;
 
public class DBConnectionNew {
	
	private final String ADD_NEW_EMPLOYEE = "INSERT INTO employee(firstname, lastname, pesel,city, salary, seniority) VALUES(?,?,?,?,?,?)";
	private final String REMOVE_EMPLOYEE="delete from employee where id_employee= ?";
	private final String UPDATE_EMPLOYEE="update employee set firstname=?, lastname=?, pesel=?,city=?, salary=?, seniority=? where id_employee= ?";
	private final String ADD_NEW_LOGIN = "INSERT INTO logins(id_employee, login, pass, priviliges) VALUES(?,?,?,?)";
	private final String REMOVE_LOGIN="delete from logins where id_employee= ?";
	private final String UPDATE_LOGIN="update logins set login=?, pass=?, priviliges=? where id_employee= ?";
	
	
	private Connection con;
	private Statement stmt;
	String root="root";
	String pass="kwiat23";
	String login;
	String password;
	
	
	private DBConnectionNew(String login, String haslo) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employees",login,haslo);
		stmt=con.createStatement();
		
		
	}
	public static DBConnectionNew makeConnection(String login, String haslo) throws MySuperExc {
		try {
			DBConnectionNew newCon =new DBConnectionNew(login,haslo);
			return newCon;
		} catch (Exception e) {
			throw new MySuperExc();
		}
	}
	
	
	public ResultSet getTable(String nazwaTabeli) throws SQLException{
		String query= "select * from "+ nazwaTabeli;
		ResultSet rs= stmt.executeQuery(query);
		return rs;
		
	}
	
	public ResultSet getUserPriviliges(String login) throws SQLException{
		String query= "select priviliges from logins where login= "+"\""+login+"\"";
		ResultSet rs= stmt.executeQuery(query);
		return rs;
		
	}
	public	ResultSet getUser(String login) throws SQLException{
		String query= "select * from employee join logins on logins.id_employee = employee.id_employee where login =" +"\""+Log_in.textLogin.getText()+"\"";
		ResultSet rs= stmt.executeQuery(query);
		return rs;
	}
	
	public	Employee getEmployeeIdByNameAndSurname(String firstName, String lastName) throws SQLException{
		String query= "select * from employee where firstname =" +"\""+firstName+"\""+" AND lastname =" +"\""+lastName+"\"";
		ResultSet rs= stmt.executeQuery(query);
		rs.next();
		Employee employee = new Employee();
		employee.setId(rs.getInt(1));
		employee.setFirstName(rs.getString(2));
		employee.setLastName(rs.getString(3));
		employee.setPesel(rs.getString(4));
		employee.setCity(rs.getString(5));
		employee.setSalary(rs.getInt(6));
		employee.setSeniority(rs.getInt(7));
		
		
		return employee;
	}
	
	public void addEmployee(Employee employee) { 
		
		try {
			PreparedStatement addEmployeeStm = con.prepareStatement(ADD_NEW_EMPLOYEE);
			addEmployeeStm.setString(1, employee.getFirstName());
			addEmployeeStm.setString(2, employee.getLastName());
			addEmployeeStm.setString(3, employee.getPesel());
			addEmployeeStm.setString(4, employee.getCity());
			addEmployeeStm.setInt(5, employee.getSalary());
			addEmployeeStm.setInt(6, employee.getSeniority());
			addEmployeeStm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Adding data failed");
			e.printStackTrace();
		}
	}
	
	public void removeEmployeeById(int id) { 
		
		try {
			PreparedStatement addEmployeeStm = con.prepareStatement(REMOVE_EMPLOYEE);
			addEmployeeStm.setInt(1, id);
			addEmployeeStm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Romoving data failed");
			e.printStackTrace();
		}
		
	}
	
	
	
	public void updateEmployee(Employee employee) { 
		
		try {
			PreparedStatement addEmployeeStm = con.prepareStatement(UPDATE_EMPLOYEE);
			addEmployeeStm.setString(1, employee.getFirstName());
			addEmployeeStm.setString(2, employee.getLastName());
			addEmployeeStm.setString(3, employee.getCity());
			addEmployeeStm.setString(4, employee.getPesel());
			addEmployeeStm.setInt(5, employee.getSalary());
			addEmployeeStm.setInt(6, employee.getSeniority());
			addEmployeeStm.setInt(7, employee.getId());
			addEmployeeStm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Updating data failed");
			e.printStackTrace();
		}
	}
	
	
	
	
	public void addLogin(Login login) { 
		
		try {
			PreparedStatement addLoginStm = con.prepareStatement(ADD_NEW_LOGIN);
			addLoginStm.setInt(1, login.getId());
			addLoginStm.setString(2, login.getLogin());
			addLoginStm.setString(3, login.getPass());
			addLoginStm.setString(4, login.getPriviliges());
			addLoginStm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Adding data failed");
			e.printStackTrace();
		}
	}
	
	public	Login getLoginIdByLoginAndPassword(String login, String password) throws SQLException{
		String query= "select * from logins where login =" +"\""+login+"\""+" AND pass =" +"\""+password+"\"";
		ResultSet rs= stmt.executeQuery(query);
		rs.next();
		Login log = new Login();
		log.setId(rs.getInt(1));
		log.setLogin(rs.getString(2));
		log.setPass(rs.getString(3));
		log.setPriviliges(rs.getString(4));
		
		
		return log;
	}
	
	
	public void removeLoginById(int id) { 
		
		try {
			PreparedStatement removeLoginStm = con.prepareStatement(REMOVE_LOGIN);
			removeLoginStm.setInt(1, id);
			removeLoginStm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Removing data failed");
			e.printStackTrace();
		}
		
	}
	
	
		public void updateLogin(Login login) { 
		
		try {
			PreparedStatement updateLoginStm = con.prepareStatement(UPDATE_LOGIN);
			
			updateLoginStm.setString(1, login.getLogin());
			updateLoginStm.setString(2, login.getPass());
			updateLoginStm.setString(3, login.getPriviliges());
			updateLoginStm.setInt(4, login.getId());
			updateLoginStm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Updating data failed");
			e.printStackTrace();
		}
	}
	
	
	public Connection getCon() {
		return con;
	}
	public void setCon(Connection con) {
		this.con = con;
	}
	public Statement getStmt() {
		return stmt;
	}
	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
	void closeConn() throws SQLException{
		con.close();
	}
	
}
