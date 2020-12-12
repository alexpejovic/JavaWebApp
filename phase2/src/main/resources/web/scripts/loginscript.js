const http = new XMLHttpRequest();
url = "/getmodel";
http.open("GET", url, true);
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
