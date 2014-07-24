package com.nodemanager.managedbean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Login;
import com.nodemanager.service.LoginService;

@ManagedBean
public class UserLoginMB {

	private static final String persistenceUnitName = "NodeManagerPU";
	private SimpleEntityManager simpleEntityManager;

	private Login login;
	private LoginService loginService;

	private String username;
	private String password;

	public UserLoginMB() {
		this.setLogin(new Login());
		this.simpleEntityManager = new SimpleEntityManager(persistenceUnitName);
		this.loginService = new LoginService(simpleEntityManager);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the login
	 */
	public Login getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(Login login) {
		this.login = login;
	}

	public void login(ActionEvent event) {
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = null;
		boolean loggedIn = false;

		if (username != null && username.equals("admin") && password != null
				&& password.equals("admin")) {
			loggedIn = true;
			login.setLogin(username);
			login.setSenha(password);
			loginService.save(login);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome",
					username);
		} else {
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Loggin Error", "Invalid credentials");
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		context.addCallbackParam("loggedIn", loggedIn);
	}
}
