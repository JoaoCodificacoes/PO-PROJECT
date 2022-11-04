package prr.core.communications;

import prr.core.terminals.Terminal;

public class TextCommunication extends Communication {
    private final String _message;

    public TextCommunication(String message, Terminal from, Terminal to) {
        super(from, to);
        _message = message;
        finishComm();
    }

    protected void computeCost() {
        double cost = getFrom().getOwner().computeTextCommCost(getSize());
        setCost(cost);
    }

    @Override
    public String toString() {
        return super.toString("TEXT");
    }

    protected int getSize() {
        return _message.length();
    }

}
