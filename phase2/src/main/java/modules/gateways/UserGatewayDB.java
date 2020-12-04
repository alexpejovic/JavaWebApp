package modules.gateways;

import modules.entities.User;

import java.util.ArrayList;

public class UserGatewayDB implements UserStrategy {
    @Override
    public ArrayList<User> readData() {
        return null;
    }

    @Override
    public void writeData(ArrayList<User> writeUsers) {

    }

    @Override
    public void setFilename(String newFilename) {

    }
}
