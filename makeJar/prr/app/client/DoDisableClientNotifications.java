package prr.app.client;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.exception.NotificationPreferenceAlreadySelectedException;
import prr.core.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

    DoDisableClientNotifications(Network receiver) {
        super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
        addStringField("clientID", Message.key());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.setClientNotificationPreference(stringField("clientID"), false);

        } catch (UnknownClientKeyException ucke) {
            throw new UnknownTerminalKeyException(ucke.getKey());

        } catch (NotificationPreferenceAlreadySelectedException npase) {
            _display.popup(Message.clientNotificationsAlreadyDisabled());
        }
    }
}
