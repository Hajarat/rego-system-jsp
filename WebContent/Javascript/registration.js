$(document).ready(function() {
	/**
	 * This function prevents page auto reload on a bad form submission
	 */
	document.getElementById("submitButton").addEventListener("click", function(event) {
		event.preventDefault();
	});
});

/**
 * This function validates the email entry given is of correct email format
 * @param email the email to validate
 * @returns whether email entry given is valid or not
 */
function validateEmail(email) {
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
}

/**
 * This function validates the user entry given is of correct name format
 * @param user the username to validate
 * @returns whether user entry given is valid or not
 */
function validateUser(user) {
	var re = /^[a-zA-Z ]{2,30}$/;
	return re.test(user);
}

/**
 * This function validates the age entry given is of correct number format
 * @param user the age value to validate
 * @returns whether age entry given is valid or not
 */
function validateAge(age) {
	var re = /^[0-9]{1,7}$/;
	return re.test(age);
}

/**
 * This function tests if the password provided is of valid format
 * the password criteria is: One lower case letter, one upper case letter, one digit, 6-13 length, and no spaces.
 * @param password the password value provided
 */
function validatePassword(password) {
	var re = /^(?=.*\d).{4,8}$/;
	return re.test(password);
}

/**
 * This function validates the form entries given and appends error messages
 * next to the incorrectly filled fields. If all the fields are filled correctly,
 * the form is submitted to the backend 
 */
function handleRegistration() {
	success=true
	var name = document.getElementById("reg_name")
	var age = document.getElementById("reg_age")
	var email = document.getElementById("reg_email")
	var password = document.getElementById("reg_password")
	// Name field handling
	if(!validateUser(name.value)) {
		success=false
		$("#nameErrorMessage").remove()
		$("#reg_name").after("<b id='nameErrorMessage'>\tPlease enter a valid name</b>")
	} else {
		$("#nameErrorMessage").remove()
	}
	// Age field handling
	if(!validateAge(age.value)) {
		success=false
		$("#ageErrorMessage").remove()
		$("#reg_age").after("<b id='ageErrorMessage'>\tPlease enter a valid number value</b>")
	} else if(parseInt(age.value) < 18 || parseInt(age.value) > 65) {
		success=false
		$("#ageErrorMessage").remove()
		$("#reg_age").after("<b id='ageErrorMessage'>\tPlease enter a Number between 18-65</b>")
	} else {
		$("#ageErrorMessage").remove()
	}
	// Email field handling
	if(!validateEmail(email.value)) {
		success=false
		$("#emailErrorMessage").remove()
		$("#reg_email").after("<b id='emailErrorMessage'>\tPlease enter a valid email</b>")
	} else {
		$("#emailErrorMessage").remove()
	}
	// Password field handling
	if(!validatePassword(password.value)) {
		success=false
		$("#passwordErrorMessage").remove()
		$("#reg_password").after("<b id='passwordErrorMessage'>\tPassword is not valid</b>")
	} else {
		$("#passwordErrorMessage").remove()
	}
	// Check successful entry set -> go to backend
	if(success) {
		$.ajax({
			type: 'POST',
			url: 'RegisterServlet',
			data: {'usage':'email','email':email.value},
			success: function(response) {
				if(response.localeCompare("valid") == 1) {
					//register user:
					var form = document.getElementById("registerform")
					form.action = "RegisterServlet"
					form.submit()
				} else { //Email is taken -> inform user
					$("#emailErrorMessage").remove()
					$("#reg_email").after("<b id='emailErrorMessage'>\tEmail taken</b>")
				}
			}
		});
	}
}

function handleUpdate() {
	success=true
	var id = document.getElementById("id_update")
	var name = document.getElementById("name_update")
	var age = document.getElementById("age_update")
	var old_password = document.getElementById("old_pass_update")
	var new_password = document.getElementById("new_pass_update")
	
	//Get the level value
	var level
	if(document.getElementById("level_update1").checked == true) {
		level = document.getElementById("level_update1")
	} else if (document.getElementById("level_update2").checked == true) {
		level = document.getElementById("level_update2")
	} else {
		level = document.getElementById("level_update3")
	}
	
	// Name field handling
	if(!validateUser(name.value)) {
		success=false
		$("#nameErrorMessage").remove()
		$("#name_update").after("<b id='nameErrorMessage'>\tPlease enter a valid name</b>")
	} else {
		$("#nameErrorMessage").remove()
	}
	// Age field handling
	if(!validateAge(age.value)) {
		success=false
		$("#ageErrorMessage").remove()
		$("#age_update").after("<b id='ageErrorMessage'>\tPlease enter a valid number value</b>")
	} else if(parseInt(age.value) < 18 || parseInt(age.value) > 65) {
		success=false
		$("#ageErrorMessage").remove()
		$("#age_update").after("<b id='ageErrorMessage'>\tPlease enter a Number between 18-65</b>")
	} else {
		$("#ageErrorMessage").remove()
	}
	// Password(s) field handling
	if(old_password.value.length == 0 && new_password.value.length == 0) {
		$("#passwordErrorMessage").remove()
	} else if(!validatePassword(old_password.value) || !validatePassword(new_password.value)) {
		success=false
		$("#passwordErrorMessage").remove()
		$("#new_pass_update").after("<b id='passwordErrorMessage'>\tOne or more password entry is invalid</b>")
	} else {
		$("#passwordErrorMessage").remove()
	}
	// Check successful entry set -> go to backend
	if(success) {
		$.ajax({
			type: 'POST',
			url: 'UpdateUserServlet',
			data: {'usage':'checkupdate','id':id.value,'name':name.value,'age':age.value,'level':level.value,'password':old_password.value},
			success: function(response) {
				console.log("to valid= " + response.localeCompare("valid"))
				console.log("to passwordmismatch= " + response.localeCompare("passwordmismatch"))
				console.log("to invalid= " + response.localeCompare("invalid"))
				if(response.localeCompare("valid") == 1) {
					//Update user:
					var form = document.getElementById("registerform");
					form.action = "UpdateUserServlet"
					form.submit()
				} else if (response.localeCompare("passwordmismatch") == 1) {
					$("#passwordErrorMessage").remove()
					$("#new_pass_update").after("<b id='passwordErrorMessage'>\tOld password is incorrect</b>")
				} else { //No changes need to be made -> inform user
					$("#nameErrorMessage").remove()
					$("#name_update").after("<b id='nameErrorMessage'>\tYou need to make some changes to invoke a rewrite</b>")
				}
			}
		});
	}
}