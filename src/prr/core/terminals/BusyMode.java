package prr.core.terminals;

import prr.core.terminals.Terminal.TerminalMode;

public class BusyMode extends TerminalMode {

    //Busy mode can go to Idle(end of Communication),Silence(end of Communication)

    public BusyMode(Terminal terminal) {
        terminal.super();
    }

    @Override
    public boolean toIdle() {
        setMode(new IdleMode(getTerminal()));
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
    public boolean canEndComm() {
        return true;
    }

    @Override
    public String toString() {
        return "BUSY";
    }
}
