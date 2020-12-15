#Design Patterns
###Below are the design patterns we implemented, along with how and why we implemented them:
Builder - this design pattern was implemented through the ConfBuilder class.  Since a conference is a
very complex structure with many features, such as an AttendeeManager, OrganizerManager, MessageManager, UserGateway etc.,
we decided that creating a conference using a builder class would be much cleaner as it meant we wouldn't have a large chunk
of code in the main method that is necessary to instantiate a conference.  Instead, the ConfBuilder class
instantiates all the necessary use case and gateway classes, and uses them to read in data from a file, and store it in
the appropriate use case classes while the program is running.  This means that these steps won't have to repeated in the main method
each time a new conference is created.