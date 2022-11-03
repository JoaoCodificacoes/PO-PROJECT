package prr.core.notification;

import prr.core.terminals.Terminal.TerminalMode;

import java.util.HashSet;
import java.util.Set;

public enum NotificationType {
    O2S,
    O2I,
    S2I,
    B2I;
    private static Set<String> _notiTypes = new HashSet<>();
    static {
        for ( NotificationType noti: NotificationType.values()){
            _notiTypes.add(noti.toString());
        }
    }
    public static String makeValidNotificationType(TerminalMode previous, String newMode){
        if (previous== null)
            return null;
        String notiType = previous.toString().charAt(0) + "2" + newMode.charAt(0);
        if (_notiTypes.contains(notiType))
            return notiType;
        return null;

    }
}
