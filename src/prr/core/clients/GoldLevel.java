package prr.core.clients;

import prr.core.clients.Client.ClientLevel;

public class GoldLevel extends ClientLevel {

    public GoldLevel(Client client) {
        client.super();
    }

    @Override
    public void checkClientLevelComm() {

    }

    @Override
    public void checkClientLevelPayment() {
        Client c = getClient();
        if (c.getClientBalance() < 0)
            setLevel(new NormalLevel((c)));
        setLevel(new PlatinumLevel(c));
    }

    @Override
    public String toString() {
        return "GOLD";
    }
}
