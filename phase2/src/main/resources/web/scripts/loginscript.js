//var form = document.querySelector(".login-form");
//var usr = document.querySelector("#username");
//var pwd = document.querySelector("#password");

const http = new XMLHttpRequest();
url = "/getstatus";
http.open("GET", url);
http.send();

http.onreadystatechange = function() {
    if (this.readyState == this.DONE) {
        if (this.onreadystatechange) {
            handle(http.response);
            http.onreadystatechange = null;
        }
    }
}

function handle(response) {
    response = JSON.parse(response);
    console.log(response.statusMessage);
    console.log(http.response);

    if (response.status == "error") {
            const form = document.querySelector(".form");
            const newText = document.createElement("p");
            newText.id = "errorMessage"
            newText.innerText = response.statusMessage;
            form.appendChild(newText);
    }
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