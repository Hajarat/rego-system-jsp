package com.example.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bo.*;
import com.example.business.*;
import com.example.common.ErrorCodes;
import com.example.database.*;

import java.util.ArrayList;

/**
 * Servlet implementation class ViewUsersServlet
 */
public class ViewUsersServlet extends HttpServlet {
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
    public ViewUsersServlet() {
        super();
    }

	/**
	 * This doGet method attempts to retrieve all user information from the given database
	 * connection and send them to display_users.jsp for display
	 * @param request request object
	 * @param response response object
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserRegistration fetchUsers = new UserRegistrationImplementation();
		try {
			ArrayList<User> users = fetchUsers.displayAllUsers(dbc);
			request.setAttribute("users", users);
			request.getRequestDispatcher("/display_users.jsp").forward(request, response);
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.addHeader("redirectReason", e.getLocalizedMessage());
			request.getRequestDispatcher("/redirect.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			response.addHeader("redirectReason", ErrorCodes.DATABASE_QUERY);
			request.getRequestDispatcher("/redirect.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
