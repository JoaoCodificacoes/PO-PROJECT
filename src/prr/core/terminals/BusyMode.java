package prr.core.terminals;

import prr.core.exception.DestinationBusyException;
import prr.core.terminals.Terminal.TerminalMode;

class BusyMode extends TerminalMode {

    //Busy mode can go to Idle(end of Communication),Silence(end of Communication)

    public BusyMode(Terminal terminal) {
        terminal.super();
    }


    @Override
    public boolean canStartComm() {
        return false;
    }

    @Override
    public void getCall(Terminal from) throws DestinationBusyException {
        attach(from.getOwner());
        from.getTerminalMode().toPrevious();
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
    public void toOff() {

    }

    @Override
    public void toIdle() {

    }

    @Override
    public void toSilence() {

    }

    @Override
    public void toPrevious() {
        TerminalMode mode = getPreviousMode();
        setPreviousMode(new BusyMode(getTerminal()));
        setMode(mode);
    }
}
