package prr.core.terminals;

import prr.core.exception.DestinationBusyException;
import prr.core.exception.DestinationOffException;
import prr.core.exception.DestinationSilentException;
import prr.core.notification.B2iNotification;
import prr.core.terminals.Terminal.TerminalMode;

public class BusyMode extends TerminalMode {

    //Busy mode can go to Idle(end of Communication),Silence(end of Communication)

    public BusyMode(Terminal terminal) {
        terminal.super();
    }

    @Override
    public boolean toIdle() {
        setMode(new IdleMode(getTerminal()));
        setNewNotificationType(new B2iNotification());
        notify(getNewNotificationType());
        return true;
    }

    @Override
    public boolean toBusy() {
        return false;
    }

    @Override
    public boolean toSilence() {
        setMode(new SilenceMode(getTerminal()));
        return true;
    }

    @Override
    public boolean canStartComm() {
        return false;
    }

    @Override
    public void getCall() throws DestinationOffException, DestinationSilentException, DestinationBusyException {
        throw new DestinationBusyException(getTerminal().getId());
    }

    @Override
    public boolean canEndComm() {
        return true;
    }

    @Override
    public String toString() {
        return "BUSY";
    }
}
