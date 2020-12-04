package modules.gateways;

import modules.entities.Event;
import modules.entities.Message;

import java.util.ArrayList;

public class MessageGateway {

    private MessageStrategy strategy;

    public MessageGateway() {
        this.strategy = new MessageGatewayDB();
    }

    public ArrayList<Message> readData() {
        return this.strategy.readData();
    }

    public void writeData(ArrayList<Message> writeMessages) {
        this.strategy.writeData(writeMessages);
    }

    public void setStrategy(MessageStrategy newStrat) {
        this.strategy = newStrat;
    }

    public void setFilename(String newFilename) {
        this.strategy.setFilename(newFilename);
    }
}