package prr.core.terminals;

import prr.core.terminals.Terminal.TerminalMode;

public class OffMode extends TerminalMode {

    //Off mode can only go to Idle
    public OffMode(Terminal terminal){
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
        return true;
    }

    @Override
    public boolean toBusy() {
        return true;
    }

    @Override
    public boolean toSilence() {
        return true;
    }

    @Override
    public boolean canStartComm() {
        return false;
    }

    @Override
    public boolean canEndComm() {
        return false;
    }

    @Override
    public String toString(){
        return "OFF";
    }
}
