package com.example.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.business.*;
import com.example.common.ErrorCodes;
import com.example.database.*;

/**
 * Servlet implementation class DeleteUserServlet
 */
public class DeleteUserServlet extends HttpServlet {
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
    public DeleteUserServlet() {
        super();
    }

	/**
	 * This doGet method has to have a toDelete parameter (integer) included in the request
	 * object to be invoked correctly, it will delete the user with that given id then return 
	 * to the user list view via the "ViewUsersServlet" on success
	 * @param request request object, carries User Id for the user to be deleted as parameter
	 * @param response response object
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserRegistration deleter = new UserRegistrationImplementation();
		try {
			deleter.deleteUser(Integer.parseInt(request.getParameter("toDelete")), dbc);
		} catch (SQLException e) {
			e.printStackTrace();
			response.addHeader("redirectReason", ErrorCodes.DATABASE_QUERY);
			request.getRequestDispatcher("/redirect.jsp").forward(request, response);
		}
		
		request.getRequestDispatcher("/ViewUsersServlet").forward(request, response);
	}

}
