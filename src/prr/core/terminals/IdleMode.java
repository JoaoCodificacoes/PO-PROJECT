package prr.core.terminals;


import prr.core.exception.AlreadyInModeException;
import prr.core.terminals.Terminal.TerminalMode;

class IdleMode extends TerminalMode {

    //Idle mode can go to Silence(when silencing terminal),Busy(start of communication),Off(when turning off)

    public IdleMode(Terminal terminal) {
        terminal.super();
        canSendNotification();
    }

    @Override
    public void toOff() {
        setPreviousMode(new IdleMode(getTerminal()));
        setMode(new OffMode(getTerminal()));
    }

    @Override
    public void toIdle() throws AlreadyInModeException {
        throw new AlreadyInModeException();
    }

    @Override
    public void toBusy() {
        setPreviousMode(new IdleMode(getTerminal()));
        setMode(new BusyMode(getTerminal()));
    }

    @Override
    public void toSilence() {
        setPreviousMode(new IdleMode(getTerminal()));
        setMode(new SilenceMode(getTerminal()));
    }

    @Override
    public void getCall(Terminal from) {
    }

    @Override
    public String toString() {
        return "IDLE";
    }
}
