package prr.core.notification;


import prr.core.terminals.Terminal;

public class Notification {
    private final String _type;
    private Terminal _notifyingTerminal;

    public Notification(String type, Terminal notifyingTerminal) {
        _type = type;
        _notifyingTerminal = notifyingTerminal;
    }


    @Override
    public String toString() {
        return _type + "|" + _notifyingTerminal.getId();
    }
}
