# Conference App
## Phase 2

#### Running the program
Run the main method in the *Routes* class.  
Then, go to a browser and in the url bar enter: localhost:8000/home
Login with one of the sample accounts found in the *PopulateData* class.


#### Initializing Sample Data
The outline of our sample data is specified in the following google sheets document:
https://docs.google.com/spreadsheets/d/1OEoy0KsoStEYwMr6JGJyPQXe6ZQaX8DPHDFgEOVMGQA/edit?usp=sharing

For convenience here are example accounts for of each type of user you can log in as:
1. (speaker)   username: steve,  password: p1
2. (organizer) username: spot,   password: 123
3. (attendee)  username: mochi,  password: woof

If you wish to reset the data back to the sample data,
make conference.db (in resources/web/database) a empty file by deleting the content
 and then run *PopulateData* again

#### Additional Features
The features that we added are:
1. Web GUI
2. Database
3. Formatted schedule of conference (can be accessed in the link under "upcoming events" in the webpage)
4. Message Enhancing

#### Additional Notes
If you are getting a data base error message please make sure that the phase2 folder 
is the top dictionary.

If a Locked Database error occurs changing line 34 in *pom.xml* to version 3.34.0 should fix the issue

Organizers can attend events regardless of their own schedule, unlike Attendees who cannot double book themselves

Displayed messages are only received messages, not both sent and received. 
To see a sent message, log into the user that the message was sent to, and the message should appear.

Additional users can be created from the homepage after logging in with a organizer account

the new usertype we added are VIP users who can sign up for VIP only events