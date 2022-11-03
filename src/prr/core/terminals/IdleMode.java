package prr.core.terminals;


import prr.core.clients.Client;
import prr.core.notification.NotificationType;
import prr.core.terminals.Terminal.TerminalMode;

public class IdleMode extends TerminalMode {

    //Idle mode can go to Silence(when silencing terminal),Busy(start of communication),Off(when turning off)

    public IdleMode(Terminal terminal) {
        terminal.super();
        String notiType = NotificationType.makeValidNotificationType(getPreviousMode(),toString());
        if ( notiType != null) {
            setNotificationType(notiType);
            sendNotifications();
        }
    }

    @Override
    public boolean toOff() {
        setPreviousMode(new IdleMode(getTerminal()));
        setMode(new OffMode(getTerminal()));
        return true;
    }

    @Override
    public boolean toIdle() {
        return false;
    }

    @Override
    public boolean toBusy() {
        setPreviousMode(new IdleMode(getTerminal()));
        setMode(new BusyMode(getTerminal()));
        return true;
    }

    @Override
    public boolean toSilence() {
        setPreviousMode(new IdleMode(getTerminal()));
        setMode(new SilenceMode(getTerminal()));
        return true;
    }

    @Override
    public void getCall(Client from) {
    }

    @Override
    public String toString() {
        return "IDLE";
    }
}
