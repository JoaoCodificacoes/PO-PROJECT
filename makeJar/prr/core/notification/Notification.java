package prr.core.notification;


import prr.core.terminals.Terminal;

public class Notification {
    private NotificationType _type;
    private Terminal _notifyingTerminal;

    public Notification(NotificationType type, Terminal notifyingTerminal) {
        _type = type;
        _notifyingTerminal = notifyingTerminal;
    }


    @Override
    public String toString() {
        //FIXME implement
        return null;
    }
}
