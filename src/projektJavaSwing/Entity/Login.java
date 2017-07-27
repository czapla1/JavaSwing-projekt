package projektJavaSwing.Entity;

public class Login {
	private int id;
	private String login;
	private String pass;
	private String priviliges;
	
	public Login() {
		super();
	}

	public Login(int id, String login, String pass, String priviliges) {
		super();
		this.id = id;
		this.login = login;
		this.pass = pass;
		this.priviliges = priviliges;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPriviliges() {
		return priviliges;
	}

	public void setPriviliges(String priviliges) {
		this.priviliges = priviliges;
	}

	@Override
	public String toString() {
		return "Login [id=" + id + ", login=" + login + ", pass=" + pass + ", priviliges=" + priviliges + "]";
	}
	
}

