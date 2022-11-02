package prr.core.communications;

import prr.core.clients.Client.ClientLevel;
import prr.core.terminals.Terminal;

public class VideoCommunication extends InteractiveCommunication {
    public VideoCommunication(Terminal from, Terminal to) {
        super(from, to);
        ClientLevel clientLevel = from.getOwner().getClientLevel();
        clientLevel.setVideoCount(clientLevel.getVideoCount() + 1);
    }

    @Override
    protected double computeCost() {
        double cost = getFrom().getOwner().getClientLevel().computeVideoCommCost(getSize());
        if (getFrom().isFriend(getTo()))
            cost *= 0.5;
        setCost(cost);
        return cost;
    }

    @Override
    public String toString() {
        return super.toString("VIDEO");
    }
}
