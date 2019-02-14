package com.example.web;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bo.User;
import com.example.business.*;
import com.example.common.ErrorCodes;
import com.example.database.*;

/**
 * Servlet implementation class UpdateUserServlet
 */
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseConnection dbc;
    
	public void init() {
		dbc = new DatabaseConnectionImplementation();
        dbc.createConnection();
	}
	
	public void destroyed() {
		dbc.closeConnection();
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserServlet() {
        super();
    }

	/**
	 * This doGet method attempts to extract the user information given as request 
	 * parameters and update the given information on the given database connection, 
	 * and redirect back to the user list view via the "ViewUsersServlet" on success
	 * @param request request object, carrying user information as parameters
	 * @param response response object
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usage = request.getParameter("usage");
		switch(usage) {
		case "checkupdate":
			response.setContentType("text/html");
			User checkUser = new User();
			checkUser.setId(Integer.parseInt(request.getParameter("id")));
			checkUser.setName(request.getParameter("name"));
			checkUser.setAge(Integer.parseInt(request.getParameter("age")));
			checkUser.setLevel(Integer.parseInt(request.getParameter("level")));
			checkUser.setPassword(request.getParameter("password"));
			
			UserRegistration updateChecker = new UserRegistrationImplementation();
			try {
				boolean passMatch = updateChecker.checkPasswordMatch(checkUser.getId(), checkUser.getPassword(), dbc);
				if(updateChecker.shouldUpdate(checkUser, dbc) && checkUser.getPassword().length() == 0) {
					response.getWriter().println("valid");
				} else if (passMatch) {
					response.getWriter().println("valid");
				} else if (!passMatch && !(checkUser.getPassword().length() == 0)) {
					response.getWriter().println("passwordmismatch");
				} else {
					response.getWriter().println("invalid");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.addHeader("redirectReason", ErrorCodes.DATABASE_QUERY);
				request.getRequestDispatcher("/redirect.jsp").forward(request, response);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				response.addHeader("redirectReason", ErrorCodes.ENCRYPTION_ERROR);
				request.getRequestDispatcher("/redirect.jsp").forward(request, response);
			}
			break;
		case "doupdate":
			User user = new User();
			user.setId(Integer.parseInt(request.getParameter("id")));
			user.setName(request.getParameter("name"));
			user.setAge(Integer.parseInt(request.getParameter("age")));
			user.setLevel(Integer.parseInt(request.getParameter("level")));
			user.setPassword(request.getParameter("new_password"));
			UserRegistration updateServlet = new UserRegistrationImplementation();
			try {
				updateServlet.updateUser(user, dbc);
				request.getRequestDispatcher("/ViewUsersServlet").forward(request, response);
			} catch (RuntimeException e) {
				e.printStackTrace();
				response.addHeader("redirectReason", e.getLocalizedMessage());
				request.getRequestDispatcher("/redirect.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				response.addHeader("redirectReason", ErrorCodes.DATABASE_QUERY);
				request.getRequestDispatcher("/redirect.jsp").forward(request, response);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				response.addHeader("redirectReason", ErrorCodes.ENCRYPTION_ERROR);
				request.getRequestDispatcher("/redirect.jsp").forward(request, response);
			}
			break;
		}
	}

}
