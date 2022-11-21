package prr.core.communications;


import prr.core.terminals.Terminal;

public class VideoCommunication extends InteractiveCommunication {
    public VideoCommunication(Terminal from, Terminal to, int id) {
        super(from, to, id);
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
