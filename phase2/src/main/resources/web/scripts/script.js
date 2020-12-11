//window.onload = function() {
var form = document.querySelector(".login-form");
var usr = document.querySelector("#username");
var pwd = document.querySelector("#password");

console.log("I'm here");
const http = new XMLHttpRequest();
url = "/getstatus";
http.open("GET", url);
http.send();

http.onreadystatechange = (e) => {
    e.stopImmediatePropagation();
    var response = JSON.parse(http.response);
    console.log(response.message);
    console.log(http.response);

    if (response.status == "error") {
        const newText = document.querySelector("#errorMessage");
        if (typeof(newText) == "undefined" || newText == null) {
            const form = document.querySelector(".form");
            const newText = document.createElement("p");
            newText.id = "errorMessage"
            newText.innerText = response.message;
            form.appendChild(newText);
        }
    }
}
//}


//register.onclick = function() {
//    window.location.href = `${urlpath}/signup.html`;
//    signin = document.querySelector("#signin");
//}
//
//signin.onclick = function() {
//    window.location.href = `${urlpath}/login.html`;
//    register = document.querySelector("#register");
//}

/*
    Opens tab to access organizer-specific functions. NOTE: Still requires code to check if user is an organizer.
*/
function openTab(event, tabName) {
    var i;
    var content = document.getElementsByClassName("tabcontent");
    var links = document.getElementsByClassName("tablinks");

    for (i = 0; i < content.length; i++) {
        content[i].style.display = "none";
    }

    for (i = 0; i < links.length; i++) {
        links[i].className = links[i].className.replace(" active", "");
    }

    document.getElementById(tabName).style.display = "block";
    event.currentTarget.className += " active";
}

/* Resize the top navigation bar when the user scrolls down the page */
window.onscroll = function() {
    resizeNav();
}

function resizeNav() {
    if (document.body.scrollTop > 80 || document.documentElement.scrollTop > 80) {
        document.getElementById("nav").style.padding = "30px 10px";
        document.getElementById("nav-text").style.fontSize = "25px";
    } else {
        document.getElementById("nav").style.padding = "80px 10px";
        document.getElementById("nav-text").style.fontSize = "35px";
    }
}