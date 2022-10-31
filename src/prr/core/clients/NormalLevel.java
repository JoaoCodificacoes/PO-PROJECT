package prr.core.clients;

import prr.core.clients.Client.ClientLevel;

public class NormalLevel extends ClientLevel {
    private final static int UPGRADE_BALANCE = 500;

    public NormalLevel(Client client) {
        client.super();
    }

    @Override
    public void checkLevel() {
        //Promotion
        if (getClient().getClientBalance() > UPGRADE_BALANCE)
            setLevel(new GoldLevel(getClient()));
    }

    @Override
    public String getLevel() {
        return "NORMAL";
    }

}
