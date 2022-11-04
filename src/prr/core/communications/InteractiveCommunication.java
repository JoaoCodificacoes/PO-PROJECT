package prr.core.communications;

import prr.core.terminals.Terminal;

public abstract class InteractiveCommunication extends Communication {
    private int _duration;

    public InteractiveCommunication(Terminal from, Terminal to) {
        super(from, to);
        startComm();
    }

    protected int getSize() {
        return _duration;
    }


    public void setDuration(int duration) {
        _duration = duration;
    }

    /**
     * @return discount or not if terminal is friend
     */
    public double isFriendModifier() {
        if (getFrom().isFriend(getTo()))
            return 0.5;
        return 1;
    }

    /**
     * finish the interactive communication
     */
    public void finishInteractiveComm() {
        finishComm();
        getTo().setOngoingComm(null);
        getFrom().setOngoingComm(null);
        getTo().setOnPrevious();
        getFrom().setOnPrevious();
        getFrom().addDebt(getCost());

    }

}
