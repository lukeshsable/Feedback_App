		const formOpenBtn = document.querySelector("#form-open"),
		  home = document.querySelector(".home"),
		  formContainer = document.querySelector(".form_container"),
		  formCloseBtn = document.querySelector(".form_close"),
		  signupBtn = document.querySelector("#signup"),
		  loginBtn = document.querySelector("#login"),
		  pwShowHide = document.querySelectorAll(".pw_hide");
		
		formOpenBtn.addEventListener("click", () => home.classList.add("show"));
		formCloseBtn.addEventListener("click", () => home.classList.remove("show"));
		
		pwShowHide.forEach((icon) => {
		  icon.addEventListener("click", () => {
		    let getPwInput = icon.parentElement.querySelector("input");
		    if (getPwInput.type === "password") {
		      getPwInput.type = "text";
		      icon.classList.replace("uil-eye-slash", "uil-eye");
		    } else {
		      getPwInput.type = "password";
		      icon.classList.replace("uil-eye", "uil-eye-slash");
		    }
		  });
		});
		
		window.addEventListener("DOMContentLoaded", () => {
		  const urlParams = new URLSearchParams(window.location.search);
		  const alreadyRegistered = urlParams.get("alreadyRegistered");
		
		  if (alreadyRegistered === "true") {
		    // Show modal form container
		    home.classList.add("show");
		
		    // Switch to login form
		    document.querySelector(".form.login_form").classList.add("active");
		    document.querySelector(".form.signup_form").classList.remove("active");
		
		    // Optional alert
		    alert("You are already registered. Please log in.");
		  }
		});
		
		signupBtn.addEventListener("click", (e) => {
		  e.preventDefault();
		  formContainer.classList.add("active");
		});
		
		loginBtn.addEventListener("click", (e) => {
		  e.preventDefault();
		  formContainer.classList.remove("active");
		});
		
	
			const params = new URLSearchParams(window.location.search);
			const error = params.get("error");
			const success = params.get("success");
			
			if (error === "alreadyRegistered") {
			    document.getElementById("errorMsg").textContent = "You are already registered!";
			}
			if (success === "registered") {
			  document.getElementById("successMsg").textContent = "You have successfully registered!";
			}
		
