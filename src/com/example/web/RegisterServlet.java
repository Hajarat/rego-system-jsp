package com.example.web;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bo.*;
import com.example.business.*;
import com.example.common.ErrorCodes;
import com.example.database.*;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
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
    public RegisterServlet() {
        super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	/**
	 * This doPost method attempts to register user information by extracting that information
	 * from the request object and creating/writing user details into the given database connection,
	 * followed by redirecting to success.jsp on success
	 * @param request request object, carries user details as parameters
	 * @param response response object
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usage = request.getParameter("usage");
		switch (usage) {
		case "register":
			User user = new User();
			user.setName(request.getParameter("name"));
			user.setAge(Integer.parseInt(request.getParameter("age")));
			user.setLevel(Integer.parseInt(request.getParameter("level")));
			user.setEmail(request.getParameter("email"));
			user.setPassword(request.getParameter("password"));			
			UserRegistration registerServlet = new UserRegistrationImplementation();
			try {
				registerServlet.registerUser(user, dbc);
				sendEmail(user.getEmail());
				request.getRequestDispatcher("/success.jsp").forward(request, response);
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
		case "email":
			response.setContentType("text/html");
			UserRegistration emailChecker = new UserRegistrationImplementation();
			try {
				if(emailChecker.isEmailExists(request.getParameter("email"), dbc)) {
					response.getWriter().println("invalid");
				} else {
					response.getWriter().println("valid");
				}	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	/**
	 * Send confirmation email to registering user
	 * this method is only called if registration is successful
	 * reference - https://www.youtube.com/watch?v=UMfjndwGwnM
	 * @param email the email to send confirmation to
	 */
	private void sendEmail(String email) {
		Locale locale = new Locale("en", "US");
		ResourceBundle labels = ResourceBundle.getBundle("resources", locale);
		String host = labels.getString("host");
		String user = labels.getString("sender");
		String password = labels.getString("sender_password");
		String to = email;
		String from = labels.getString("sender");
		String subject = labels.getString("subject");
		String messageText = labels.getString("main_text");
		boolean sessionDebug = false;
		
		Properties props = System.getProperties();

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.required", "true");
		
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Session mailSession = Session.getDefaultInstance(props, null);
        mailSession.setDebug(sessionDebug);
        Message msg = new MimeMessage(mailSession);
        try {
        	msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new Date());
            msg.setText(messageText);

           Transport transport=mailSession.getTransport("smtp");
           transport.connect(host, user, password);
           transport.sendMessage(msg, msg.getAllRecipients());
           transport.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	


}
