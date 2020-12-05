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