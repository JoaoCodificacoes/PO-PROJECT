package prr.core.communications;

import prr.core.terminals.Terminal;

public abstract class InteractiveCommunication extends Communication {
    private int _duration;

    public InteractiveCommunication(Terminal from, Terminal to) {
        super(from, to);
        startComm();
        from.getOwner().getClientLevel().setTextCount(0);
    }

    protected int getSize() {
        return _duration;
    }


    public void setDuration(int duration) {
        _duration = duration;
    }

}
