package prr.app.client;

import prr.core.Network;
import prr.core.exception.NotificationPreferenceAlreadySelectedException;
import prr.core.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

    DoEnableClientNotifications(Network receiver) {
        super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
        addStringField("clientID", Message.key());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.setClientNotificationPreference(stringField("clientID"), true);

        } catch (UnknownClientKeyException ucke) {
            throw new prr.app.exception.UnknownClientKeyException(ucke.getKey());

        } catch (NotificationPreferenceAlreadySelectedException npase) {
            _display.popup(Message.clientNotificationsAlreadyEnabled());
        }
    }
}
