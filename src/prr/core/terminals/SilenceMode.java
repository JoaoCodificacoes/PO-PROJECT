package prr.core.terminals;

import prr.core.terminals.Terminal.TerminalMode;

public class SilenceMode extends TerminalMode {

    //Silence can go to Idle(when told to idle),Busy(start of Communication),Off(when turning off)

    public SilenceMode(Terminal terminal) {
        terminal.super();
    }

    @Override
    public boolean toOff() {
        setMode(new OffMode(getTerminal()));
        return true;
    }

    @Override
    public boolean toIdle() {
        setMode(new IdleMode(getTerminal()));
        return true;
    }

    @Override
    public boolean toBusy() {
        setMode(new BusyMode(getTerminal()));
        return true;
    }

    @Override
    public boolean toSilence() {
        return false;
    }

    public boolean canStartComm(){
        return true;
    }

    @Override
    public boolean canEndComm() {
        return false;
    }

    @Override
    public String toString(){
        return "SILENCE";
    }
}
