package modules.gateways;

import modules.entities.User;

import java.util.ArrayList;

public class UserGateway {

    private UserStrategy strategy;

    public UserGateway() {
        this.strategy = new UserGatewayDB();
    }

    public ArrayList<User> readData() {
        return this.strategy.readData();
    }

    public void writeData(ArrayList<User> writeUsers) {
        this.strategy.writeData(writeUsers);
    }

    public void setStrategy(UserStrategy newStrat) {
        this.strategy = newStrat;
    }

    public void setFilename(String newFilename) {
        this.strategy.setFilename(newFilename);
    }
}
