package prr.core.terminals;

import prr.core.exception.DestinationOffException;
import prr.core.exception.DestinationSilentException;
import prr.core.notification.O2iNotification;
import prr.core.notification.O2sNotification;
import prr.core.terminals.Terminal.TerminalMode;

public class OffMode extends TerminalMode {

    //Off mode can only go to Idle
    public OffMode(Terminal terminal) {
        terminal.super();
    }

    @Override
    public boolean toOff() {
        return false;
    }

    @Override
    public boolean toIdle() {
        //can go to idle
        setMode(new IdleMode(getTerminal()));
        setNewNotificationType(new O2iNotification());
        notify(getNewNotificationType());
        return true;
    }

    @Override
    public boolean toSilence() {
        setMode(new SilenceMode(getTerminal()));
        setNewNotificationType(new O2sNotification());
        notify(getNewNotificationType());
        return true;
    }

    @Override
    public boolean canStartComm() {
        return false;
    }
    @Override
    public void getText() throws DestinationOffException {
        throw new DestinationOffException(getTerminal().getId());
    }
    public void getCall() throws DestinationOffException{
        throw new DestinationOffException(getTerminal().getId());
    }
    @Override
    public String toString() {
        return "OFF";
    }
}
