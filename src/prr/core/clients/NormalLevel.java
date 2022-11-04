package prr.core.clients;

import prr.core.clients.Client.ClientLevel;

public class NormalLevel extends ClientLevel {
    private final static int UPGRADE_BALANCE = 500;

    public NormalLevel(Client client) {
        client.super();
    }

    @Override
    public void checkClientLevelComm() {
    }

    @Override
    public void checkClientLevelPayment() {
        //Promotion
        if (getClient().getClientBalance() > UPGRADE_BALANCE)
            setLevel(new GoldLevel(getClient()));
    }

    @Override
    public double computeTextCommCost(int n) {
        if (n < 50)
            return 10;
        if (n >= 100)
            return 2 * n;
        return 16;
    }

    @Override
    public double computeVideoCommCost(int n) {
        return 30 * n;
    }

    @Override
    public double computeVoiceCommCost(int n) {
        return 20 * n;
    }

    @Override
    public String toString() {
        return "NORMAL";
    }

}
