package prr.core.communications;


import prr.core.terminals.Terminal;

public class VideoCommunication extends InteractiveCommunication {
    public VideoCommunication(Terminal from, Terminal to) {
        super(from, to);
    }

    @Override
    protected void computeCost() {
        double cost = getFrom().getOwner().computeVideoCommCost(getSize()) * isFriendModifier();
        setCost(cost);
    }

    @Override
    public String toString() {
        return super.toString("VIDEO");
    }
}
