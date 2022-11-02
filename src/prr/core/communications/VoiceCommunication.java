package prr.core.communications;

import prr.core.terminals.Terminal;

public class VoiceCommunication extends InteractiveCommunication {

    public VoiceCommunication(Terminal from, Terminal to) {
        super(from, to);
    }

    protected double computeCost() {

        double cost = getFrom().getOwner().getClientLevel().computeVoiceCommCost(getSize());
        if (getFrom().isFriend(getTo()))
            cost *= 0.5;
        setCost(cost);
        return cost;
    }

    @Override
    public String toString() {
        return super.toString("VOICE");
    }
}
