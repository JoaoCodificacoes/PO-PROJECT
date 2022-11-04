package prr.core.terminals;

import prr.core.clients.Client;
import prr.core.exception.AlreadyInModeException;
import prr.core.exception.DestinationOffException;
import prr.core.terminals.Terminal.TerminalMode;

class OffMode extends TerminalMode {

    //Off mode can only go to Idle
    public OffMode(Terminal terminal) {
        terminal.super();
    }

    @Override
    public void toOff() throws AlreadyInModeException {
        throw new AlreadyInModeException();
    }

    @Override
    public void toIdle() {
        //can go to idle
        setPreviousMode(new OffMode(getTerminal()));
        setMode(new IdleMode(getTerminal()));
    }

    @Override
    public void toSilence() {
        setPreviousMode(new OffMode(getTerminal()));
        setMode(new SilenceMode(getTerminal()));
    }

    @Override
    public boolean canStartComm() {
        return false;
    }

    @Override
    public void getText(Client from) throws DestinationOffException {
        attach(from);
        throw new DestinationOffException(getTerminal().getId());
    }

    public void getCall(Terminal from) throws DestinationOffException {
        attach(from.getOwner());
        from.getTerminalMode().toPrevious();
        throw new DestinationOffException(getTerminal().getId());
    }

    @Override
    public String toString() {
        return "OFF";
    }
}
