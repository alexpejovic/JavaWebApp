var userType;

// HTTP request server endpoints
var requests = {
    "getModel": "/getmodel",
    "sendMessage": "../sendmessage",
    "unattendEvent": "../unattendevent",
    "attendEvent": "../attendevent",
    "cancelEvent": "../cancelevent",
    "archiveMessage": "../archivemessage",
    "deleteMessage": "../deletemessage"
}

const http = new XMLHttpRequest();
http.open("GET", requests.getModel, true);
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
    userType = response.userType;
    addFriends(response);
    addMessages(response);
    addAttendingEvents(response);
    addMoreEvents(response);
}

function addFriends(response) {
    var friends = document.querySelector("#friends");
    var fragment = document.createDocumentFragment();
    var title = document.createElement("h3");
    title.innerText = "My Friends:";
    fragment.appendChild(title);

    if (response.friends.length == 0) {
        var nothing = document.createElement("h4");
        nothing.innerText = "No friends yet. Add one below!";
        fragment.appendChild(nothing);
    }
    else {
        var div = document.createElement("div");
        div.classList.add("subtable");
        for (var i=0; i<response.friends.length; i++) {
            var newTable = makeFriend(response.friends[i], fragment);
            div.appendChild(newTable);
        }
        fragment.appendChild(div);
    }

    friends.insertBefore(fragment, friends.firstChild);
}

function makeFriend(data, fragment) {
    var friendTable = document.createElement("table");
    var headings = ["Name", "Send Message?"];
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
    var tableElements = makeTableHeadings(getFriendHeadingsFromData(data));
    tableElements.forEach(tableElement => {
        secondRow.appendChild(tableElement);
    });

    friendTable.appendChild(firstRow);
    friendTable.appendChild(secondRow);
    fragment.appendChild(friendTable);
    return fragment;
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
    var headings = ["ID:", "Title:", "Start:", "End:"];
    if (userType === "attendee" || userType === "organizer") {
        if (attending == true) {
            headings.push("Remove?");
        }
        else {
            headings.push("Join?");
        }
    }
    if (userType === "organizer") {
        headings.push("Cancel?");
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
    var tableElements = makeTableHeadings(getEventHeadingsFromData(data, attending));
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

function getFriendHeadingsFromData(data) {
    var headings = [];
    headings.push(createPElem(data.name));
    headings.push(createMessageForm(data.ID, "Message"));
    return headings;
}

function getEventHeadingsFromData(data, attending) {
    var headings = [];
    headings.push(createPElem(data.eventID));
    headings.push(createPElem(data.name));
    headings.push(createPElem(data.startTime));
    headings.push(createPElem(data.endTime));
    if (userType === "attendee" || userType === "organizer") {
        if (attending == true) {
            headings.push(createButtonForm("Remove", requests.unattendEvent, "event", data.eventID));
        }
        else {
            headings.push(createButtonForm("Join", requests.attendEvent, "event", data.eventID));
        }
    }
    if (userType === "organizer") {
        headings.push(createButtonForm("Cancel", requests.cancelEvent, "event", data.eventID));
    }
    return headings;
}

function getMessageHeadingsFromData(data) {
    var headings = [];
    text = data.hasBeenRead === "true" ? "O" : "X";
    headings.push(createPElem(text));
    headings.push(createButtonForm("Delete", requests.deleteMessage, "message", data.messageID));
    headings.push(createButtonForm("Archive", requests.archiveMessage, "message", data.messageID));
    headings.push(createPElem(data.senderID));
    headings.push(createPElem(data.content));
    headings.push(createMessageForm(data.senderID, "Reply"));
    return headings;
}

function createMessageForm(recipient, placeholder) {
    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", requests.sendMessage);
    var input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", "reply");
    input.setAttribute("placeholder", placeholder);
    var hiddenInput = document.createElement("input");
    hiddenInput.setAttribute("type", "hidden");
    hiddenInput.setAttribute("name", "recipient");
    hiddenInput.setAttribute("value", recipient);
    var btn = document.createElement("button");
    btn.innerText = "Send";
    form.appendChild(input);
    form.appendChild(hiddenInput);
    form.appendChild(btn);
    return form;
}

function createPElem(text) {
    var elem = document.createElement("p");
    elem.innerText = text;
    return elem;
}

function createButtonForm(name, action, hiddenName, hiddenData) {
    var elem = document.createElement("form");
    elem.setAttribute("method", "post");
    elem.setAttribute("action", action);
    var btn = document.createElement("button");
    btn.classList.add(name.toLowerCase());
    btn.innerText = name;
    var hiddenElem = document.createElement("input");
    hiddenElem.setAttribute("type", "hidden");
    hiddenElem.setAttribute("name", hiddenName);
    hiddenElem.setAttribute("value", hiddenData);

    elem.appendChild(hiddenElem);
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
function openTab(event, tabName, isOrg) {
    if (isOrg == 0 || (isOrg == 1 && userType == "organizer")) {
        var i;
        var content = document.getElementsByClassName("tabcontent");
        var userLinks = document.getElementsByClassName("tablinks");

        for (i = 0; i < content.length; i++) {
            content[i].style.display = "none";
        }

        for (i = 0; i < userLinks.length; i++) {
            userLinks[i].className = userLinks[i].className.replace(" active", "");
        }

        document.getElementById(tabName).style.display = "block";
        event.currentTarget.className += " active";
    }
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
