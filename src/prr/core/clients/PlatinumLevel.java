package prr.core.clients;

import prr.core.clients.Client.ClientLevel;

public class PlatinumLevel extends ClientLevel {

    public PlatinumLevel(Client client) {
        client.super();
    }

    @Override
    public void checkClientLevelComm() {
        Client c = getClient();
        //Can't be promoted best level is Platinum
        //Demotion
        if (c.getClientBalance() < 0)
            setLevel(new GoldLevel(c));
        setLevel(new NormalLevel(c));
    }

    @Override
    public String toString() {
        return "PLATINUM";
    }

}
