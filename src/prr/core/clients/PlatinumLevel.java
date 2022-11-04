package prr.core.clients;

import prr.core.clients.Client.ClientLevel;

public class PlatinumLevel extends ClientLevel {

    private static final int DOWNGRADE_TEXT_COUNT = 2;

    public PlatinumLevel(Client client) {
        client.super();
    }

    @Override
    public void checkClientLevelComm() {
        Client c = getClient();
        //Can't be promoted best level is Platinum
        //Demotion
        if (c.getClientBalance() < 0)
            setLevel(new NormalLevel(c));
        else if (getTextCount() == DOWNGRADE_TEXT_COUNT)
            setLevel(new GoldLevel(c));

    }

    @Override
    public double computeTextCommCost(int n) {
        if (n < 50)
            return 0;
        return 4;
    }

    @Override
    public double computeVideoCommCost(int n) {
        return 10 * n;
    }

    @Override
    public double computeVoiceCommCost(int n) {
        return 10 * n;
    }


    @Override
    public String toString() {
        return "PLATINUM";
    }

}
