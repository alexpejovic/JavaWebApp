#Design Patterns
####Below are the design patterns we implemented, along with how and why we implemented them:
Builder - this design pattern was implemented through the ConfBuilder class.  Since a conference is a
very complex structure with many features, such as an AttendeeManager, OrganizerManager, MessageManager, UserGateway etc.,
we decided that creating a conference using a builder class would be much cleaner as it meant we wouldn't have a large chunk
of code in the main method that is necessary to instantiate a conference.  Instead, the ConfBuilder class
instantiates all the necessary use case and gateway classes, and uses them to read in data from a file, and store it in
the appropriate use case classes while the program is running.  This means that these steps won't have to repeated in the main method
each time a new conference is created.

Strategy - This design pattern was implemented in the gateway classes. In Phase 1 we were using .ser files to store our 
data. In Phase 2, we decided to store our data in an SQLite database instead. Rather than delete all the work in the 
gateways implementing the .ser strategy, we decided to use the Strategy design pattern so that the code wouldn't have 
to change anywhere else in the program. We did this by having a context gateway (EventGateway, MessageGateway, 
RoomGateway, and UserGateway) decide which strategy to use with a setStrategy method, but the default strategy is set 
to database since that is the better way to store data. The context gateway then pulls methods from the strategy 
interface for the respective gateway which has the necesary methods to run the program.