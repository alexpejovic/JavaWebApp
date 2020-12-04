package modules.gateways;

import modules.entities.Message;

import java.util.ArrayList;

public class MessageGatewayDB implements MessageStrategy {
    @Override
    public ArrayList<Message> readData() {
        return null;
    }

    @Override
    public void writeData(ArrayList<Message> writeMessage) {

    }

    @Override
    public void setFilename(String newFilename) {

    }
}
