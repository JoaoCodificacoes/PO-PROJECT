package prr.core.terminals;

import prr.core.clients.Client;
import prr.core.exception.DestinationOffException;
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
        setPreviousMode(new OffMode(getTerminal()));
        setMode(new IdleMode(getTerminal()));
        return true;
    }

    @Override
    public boolean toSilence() {
        setPreviousMode(new OffMode(getTerminal()));
        setMode(new SilenceMode(getTerminal()));
        return true;
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
    public void getCall(Client from) throws DestinationOffException{
        attach(from);
        throw new DestinationOffException(getTerminal().getId());
    }
    @Override
    public String toString() {
        return "OFF";
    }
}
