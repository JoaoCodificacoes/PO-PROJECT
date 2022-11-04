package prr.core.notification;


import prr.core.terminals.Terminal;

public class Notification {
    /**
     * type of event that set off the notification
     */
    private final String _type;
    /**
     * terminal that originated the notification
     */
    private final Terminal _notifyingTerminal;

    /**
     * @param type              event that set off the notification
     * @param notifyingTerminal terminal that originated the notification
     */
    public Notification(String type, Terminal notifyingTerminal) {
        _type = type;
        _notifyingTerminal = notifyingTerminal;
    }


    /**
     * @return String that has description of the notification
     */
    @Override
    public String toString() {
        return _type + "|" + _notifyingTerminal.getId();
    }
}
