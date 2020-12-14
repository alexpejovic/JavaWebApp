var userType;

// HTTP request server endpoints
var requests = {
    "getModel": "/getmodel",
    "sendMessage": "../sendmessage",
    "unattendEvent": "../unattendevent",
    "attendEvent": "../attendevent",
    "cancelEvent": "../cancelevent",
    "archiveMessage": "../archivemessage",
    "unarchiveMessage": "../unarchivemessage",
    "deleteMessage": "../deletemessage",
    "reschedule": "../reschedule"
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
    setTabs(userType);
    addFriends(response);
    addMessages(response);
    addAttendingEvents(response);
    addMoreEvents(response);
    addEventsToTab(response);
}

function setTabs(userType) {
    if (userType === "organizer") {
        var orgtabs = document.querySelectorAll(".orgtab");
        orgtabs.forEach(orgtab => {
            orgtab.removeAttribute("hidden");
        });
    }
    else if (userType === "speaker") {
        var speakertab = document.querySelector(".speakertab");
        speakertab.removeAttribute("hidden");
    }
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

function addEventsToTab(response) {
    var participating = document.querySelector("#manage");
    var fragment = document.createDocumentFragment();
    var title = document.createElement("h3");
    title.innerText = "Manage My Events:";
    fragment.appendChild(title);

    var events;
    if (userType == "speaker") {
        events = response.participating;
    }
    else {
        events = response.participating;
        events.push(...response.notParticipating);
    }
    if (events.length == 0) {
        var nothing = document.createElement("h4");
        nothing.innerText = "No events yet.";
        participating.appendChild(nothing);
    }
    for (var i=0; i<events.length; i++) {
        var newTable = makeTabEvent(events[i], fragment);
        participating.appendChild(newTable);
    }
}

function makeFriend(data, fragment) {
    var friendTable = document.createElement("table");
    var headings = ["UserID", "Name", "Send Message?"];
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
    if (response.participating.length == 0) {
        var nothing = document.createElement("h1");
        nothing.innerText = "Nothing yet.";
        attending.appendChild(nothing);
    }
    for (var i=0; i<response.participating.length; i++) {
        var newTable = makeEvent(response.participating[i], fragment, true);
        attending.appendChild(newTable);
    }
}

function addMoreEvents(response) {
    var events = document.querySelector("#moreEvents");
    var fragment = document.createDocumentFragment();
    if (response.notParticipating.length == 0) {
        var nothing = document.createElement("h1");
        nothing.innerText = "Nothing yet.";
        events.appendChild(nothing);
    }
    for (var i=0; i<response.notParticipating.length; i++) {
        var newTable = makeEvent(response.notParticipating[i], fragment, false);
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

function makeTabEvent(data, fragment) {
    var eventTable = document.createElement("table");
    var headings = ["Title:", "Send All:"];
    if (userType == "organizer") {
        headings.push("Reschedule To?");
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
    var tableElements = makeTableHeadings(getTabHeadingsFromData(data));
    tableElements.forEach(tableElement => {
        secondRow.appendChild(tableElement);
    })

    eventTable.appendChild(firstRow);
    eventTable.appendChild(secondRow);
    fragment.appendChild(eventTable);
    return fragment;
}

function getTabHeadingsFromData(data) {
    var headings = [];
    headings.push(createPElem(data.name));
    headings.push(createMessageForm("all", "Message", data.eventID));
    if (userType === "organizer") {
        headings.push(createScheduleForm(data.eventID));
    }
    return headings;
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
    var headings = ["Read?", "Delete?", "Archive?", "From:", "Message:"];
    if (data.isArchived === "false") {
        headings.push("Reply?");
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
    headings.push(createPElem(data.ID));
    //headings.push(createPElem(data.name));
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
    if (data.isArchived === "true") {
        headings.push(createButtonForm("Unarchive", requests.unarchiveMessage, "message", data.messageID));
        headings.push(createPElem(data.senderID));
        headings.push(createPElem(data.content));
    }
    else {
        headings.push(createButtonForm("Archive", requests.archiveMessage, "message", data.messageID));
        headings.push(createPElem(data.senderID));
        headings.push(createPElem(data.content));
        headings.push(createMessageForm(data.senderID, "Reply"));
    }
    return headings;
}

function createMessageForm(recipient, placeholder, event) {
    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", requests.sendMessage);
    var input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", "message");
    input.setAttribute("placeholder", placeholder);
    var hiddenInput = document.createElement("input");
    hiddenInput.setAttribute("type", "hidden");
    hiddenInput.setAttribute("name", "recipient");
    hiddenInput.setAttribute("value", recipient);
    if (recipient === "all") {
        var hidden2 = document.createElement("input");
        hidden2.setAttribute("type", "hidden");
        hidden2.setAttribute("name", "event");
        hidden2.setAttribute("value", event);
        form.appendChild(hidden2);
    }
    var btn = document.createElement("button");
    btn.innerText = "Send";
    form.appendChild(input);
    form.appendChild(hiddenInput);
    form.appendChild(btn);
    return form;
}

function createScheduleForm(eventID) {
    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", requests.reschedule);
    var input = document.createElement("input");
    input.setAttribute("type", "datetime-local");
    input.setAttribute("name", "date");
    input.setAttribute("placeholder", "day/month/year hour:minute");
    var hiddenInput = document.createElement("input");
    hiddenInput.setAttribute("type", "hidden");
    hiddenInput.setAttribute("name", "event");
    hiddenInput.setAttribute("value", eventID);
    var btn = document.createElement("button");
    btn.innerText = "Reschedule";
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
function openTab(event, tabName) {
//    if (isOrg == 0 || (isOrg == 1 && (userType == "organizer" || userType == "speaker"))) {
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
//    }
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
