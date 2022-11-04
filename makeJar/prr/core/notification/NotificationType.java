package prr.core.notification;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NotificationType implements Serializable {
    private static NotificationType _instance;
    private final Set<String> _notiTypes;


    private NotificationType() {
        _notiTypes = new HashSet<>(Arrays.asList("O2S", "S2I", "O2I", "B2I"));
    }


    @Serial
    private static final long serialVersionUID = 202208091753L;

    public static NotificationType getInstance() {
        if (_instance == null)
            _instance = new NotificationType();
        return _instance;
    }

    public String makeValidNotificationType(String previous, String newMode) {
        String notiType = previous.charAt(0) + "2" + newMode.charAt(0);
        if (_notiTypes.contains(notiType))
            return notiType;
        return null;

    }

}
