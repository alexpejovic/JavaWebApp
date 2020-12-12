console.log("homecsript");
const http = new XMLHttpRequest();
url = "/getmodel";
http.open("GET", url, true);
http.send();

http.onreadystatechange = function() {
    if (this.readyState == this.DONE) {
        if (this.onreadystatechange) {
            populateData(http.response);
            http.onreadystatechange = null;
        }
    }
}

function populateData(httpResponse) {
    var response = JSON.parse(httpResponse);
    addMessages(response);
    addAttendingEvents(response);
    addMoreEvents(response);
}

function addAttendingEvents(response) {
    var attending = document.querySelector("#myEvents");
    var fragment = document.createDocumentFragment();
    if (response.attending.length == 0) {
        var nothing = document.createElement("h1");
        nothing.innerText = "Nothing yet.";
        attending.appendChild(nothing);
    }
    for (var i=0; i<response.attending.length; i++) {
        var newTable = makeEvent(response.attending[i], fragment, true);
        attending.appendChild(newTable);
    }
}

function addMoreEvents(response) {
    var events = document.querySelector("#moreEvents");
    var fragment = document.createDocumentFragment();
    if (response.notAttending.length == 0) {
        var nothing = document.createElement("h1");
        nothing.innerText = "Nothing yet.";
        events.appendChild(nothing);
    }
    for (var i=0; i<response.notAttending.length; i++) {
        var newTable = makeEvent(response.notAttending[i], fragment, false);
        events.appendChild(newTable);
    }
}

function addMessages(response) {
    var messages = document.querySelector("#messages");
    var fragment = document.createDocumentFragment();
    if (response.messages.length == 0) {
        var nothing = document.createElement("h1");
        nothing.innerText = "Nothing yet.";
        messages.appendChild(nothing);
    }
    for (var i=0; i<response.messages.length; i++) {
        var newTable = makeMessage(response.messages[i], fragment);
        messages.appendChild(newTable);
    }
}

function makeEvent(data, fragment, attending) {
    var eventTable = document.createElement("table");
    var headings = ["ID:", "Title:", "Time:"];
    if (attending) {
        headings.push("Cancel?");
    }
    else {
        headings.push("Join?");
    }
    var tableHeadings = [];
    headings.forEach(heading => {
        tableHeadings.push(createPElem(heading));
    });
    var firstRow = document.createElement("tr");
    tableHeadings = makeTableHeadings(tableHeadings);
    tableHeadings.forEach(tableHeading => {
        firstRow.appendChild(tableHeading);
    });
    var secondRow = document.createElement("tr");
    var tableElements = makeTableHeadings(getEventHeadingsFromData(data));
    tableElements.forEach(tableElement => {
        secondRow.appendChild(tableElement);
    })

    eventTable.appendChild(firstRow);
    eventTable.appendChild(secondRow);
    fragment.appendChild(eventTable);
    return fragment;
}

function makeMessage(data, fragment) {
    var messageTable = document.createElement("table");
    var headings = ["Read?", "Delete?", "Archive?", "From:", "Message:", "Reply?"];
    var tableHeadings = [];
    headings.forEach(heading => {
        tableHeadings.push(createPElem(heading));
    });
    var firstRow = document.createElement("tr");
    tableHeadings = makeTableHeadings(tableHeadings);
    tableHeadings.forEach(tableHeading => {
        firstRow.appendChild(tableHeading);
    });
    var secondRow = document.createElement("tr");
    var tableElements = makeTableHeadings(getMessageHeadingsFromData(data));
    tableElements.forEach(tableElement => {
        secondRow.appendChild(tableElement);
    });

    messageTable.appendChild(firstRow);
    messageTable.appendChild(secondRow);
    fragment.appendChild(messageTable);
    return fragment;
}

function getEventHeadingsFromData(data, attending) {
    var headings = [];
    headings.push(createPElem(data.eventID));
    headings.push(createPElem(data.name));
    headings.push(createPElem(data.startTime));
    if (attending) {
        headings.push(createButtonForm("Cancel"));
    }
    else {
        headings.push(createButtonForm("Join"));
    }
    return headings;
}

function getMessageHeadingsFromData(data) {
    var headings = [];
    text = data.hasBeenRead === "true" ? "O" : "X";
    headings.push(createPElem(text));
    headings.push(createButtonForm("Delete"));
    headings.push(createButtonForm("Archive"));
    headings.push(createPElem(data.senderID));
    headings.push(createPElem(data.content));
    headings.push(createReplyForm());
    return headings;
}

function createReplyForm() {
    var form = document.createElement("form");
    var input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", "reply");
    input.setAttribute("placeholder", "Reply");
    var btn = document.createElement("button");
    btn.innerText = "Send";
    form.appendChild(input);
    form.appendChild(btn);
    return form;
}

function createPElem(text) {
    var elem = document.createElement("p");
    elem.innerText = text;
    return elem;
}

function createButtonForm(name) {
    var elem = document.createElement("form");
    var btn = document.createElement("button");
    btn.classList.add(name.toLowerCase());
    btn.innerText = name;
    elem.appendChild(btn);
    return elem;
}

function makeTableHeadings(headings) {
    var headingElements = [];
    headings.forEach(heading => {
        var elem = document.createElement("th");
        elem.appendChild(heading);
        headingElements.push(elem);
    });
    return headingElements;
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

/* Resize the top navigation bar when the user scrolls down the page */
window.onscroll = function() {
    resizeNav();
}

function resizeNav() {
    if (document.body.scrollTop > 80 || document.documentElement.scrollTop > 80) {
        document.getElementById("nav").style.padding = "30px 10px";
        document.querySelector(".nav-text").style.fontSize = "25px";
    } else {
        document.getElementById("nav").style.padding = "80px 10px";
        document.querySelector(".nav-text").style.fontSize = "35px";
    }
}
