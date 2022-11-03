package prr.core.clients;

import prr.core.clients.Client.ClientLevel;

public class GoldLevel extends ClientLevel {
    private static final int UPGRADE_VIDEO_COUNT = 5;

    public GoldLevel(Client client) {
        client.super();
    }

    @Override
    public void checkClientLevelComm() {
        Client c = getClient();
        //demote
        if (c.getClientBalance() < 0)
            setLevel(new NormalLevel((c)));
            //promote
        else if (getVideoCount() == UPGRADE_VIDEO_COUNT)
            setLevel(new PlatinumLevel(c));
    }

    @Override
    public double computeTextCommCost(int n) {
        if (n >= 100)
            return 2 * n;
        else
            return 10;
    }

    @Override
    public double computeVideoCommCost(int n) {
        return 20 * n;
    }

    @Override
    public double computeVoiceCommCost(int n) {
        return 10 * n;
    }

    @Override
    public String toString() {
        return "GOLD";
    }
}
