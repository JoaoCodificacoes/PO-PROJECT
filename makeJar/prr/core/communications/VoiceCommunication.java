package prr.core.communications;

import prr.core.terminals.Terminal;

public class VoiceCommunication extends InteractiveCommunication {

    public VoiceCommunication(Terminal from, Terminal to,int id) {
        super(from, to,id);
    }

    protected void computeCost() {

        double cost = getFrom().getOwner().computeVoiceCommCost(getSize()) * isFriendModifier();
        setCost(cost);
    }

    @Override
    public String toString() {
        return super.toString("VOICE");
    }
}
