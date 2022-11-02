package prr.core.communications;

import prr.core.clients.Client.ClientLevel;
import prr.core.terminals.Terminal;

public class TextCommunication extends Communication {
    private String _message;

    public TextCommunication(String message, Terminal from, Terminal to) {
        super(from, to);
        _message = message;
        ClientLevel clientLevel = from.getOwner().getClientLevel();
        clientLevel.setTextCount(clientLevel.getTextCount()+1);
    }

    protected double computeCost() {
        double cost = getFrom().getOwner().getClientLevel().computeTextCommCost(getSize());
        if (getFrom().isFriend(getTo()))
            cost *= 0.5;
        setCost(cost);
        return cost;
    }

    @Override
    public String toString() {

        return super.toString("TEXT");
    }

    protected int getSize() {
        return _message.length();
    }

}
