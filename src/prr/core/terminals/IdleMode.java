package prr.core.terminals;

import prr.core.terminals.Terminal.TerminalMode;

public class IdleMode extends TerminalMode {

    //Idle mode can go to Silence(when silencing terminal),Busy(start of communication),Off(when turning off)

    public IdleMode(Terminal terminal) {
        terminal.super();
    }

    @Override
    public boolean toOff() {
        setMode(new OffMode(getTerminal()));
        return true;
    }

    @Override
    public boolean toIdle() {
        return false;
    }

    @Override
    public boolean toBusy() {
        setMode(new BusyMode(getTerminal()));
        return true;
    }

    @Override
    public boolean toSilence() {
        setMode(new SilenceMode(getTerminal()));
        return true;
    }

    @Override
    public boolean canStartComm() {
        return true;
    }

    @Override
    public boolean canEndComm() {
        return false;
    }

    @Override
    public String toString(){
        return "IDLE";
    }
}
