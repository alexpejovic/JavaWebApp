const urlpath = "../endpoints";
const register = document.querySelector("#register");
const signin = document.querySelector("#signin");

register.onclick = function() {
    window.location.href = `${urlpath}/signup.html`;
    signin = document.querySelector("#signin");
}

signin.onclick = function() {
    window.location.href = `${urlpath}/login.html`;
    register = document.querySelector("#register");
}