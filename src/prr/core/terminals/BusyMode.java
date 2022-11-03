package prr.core.terminals;

import prr.core.clients.Client;
import prr.core.exception.DestinationBusyException;
import prr.core.terminals.Terminal.TerminalMode;

public class BusyMode extends TerminalMode {

    //Busy mode can go to Idle(end of Communication),Silence(end of Communication)

    public BusyMode(Terminal terminal) {
        terminal.super();
    }

    @Override
    public boolean toIdle() {
        setPreviousMode(new BusyMode(getTerminal()));
        setMode(new IdleMode(getTerminal()));
        return true;
    }

    @Override
    public boolean toBusy() {
        return false;
    }

    @Override
    public boolean toSilence() {
        setPreviousMode(new BusyMode(getTerminal()));
        setMode(new SilenceMode(getTerminal()));
        return true;
    }

    @Override
    public boolean canStartComm() {
        return false;
    }

    @Override
    public void getCall(Client from) throws DestinationBusyException {
        attach(from);
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

    @Override
    public void toPrevious(){
        TerminalMode mode = getPreviousMode();
        setPreviousMode(new BusyMode(getTerminal()));
        setMode(mode);
    }
}
