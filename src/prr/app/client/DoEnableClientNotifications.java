package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

  DoEnableClientNotifications(Network receiver) {
    super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("clientID",Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    boolean notisOn = true;
    try {
      if (!_receiver.changeClientNotificationState(stringField("clientID"), notisOn))
        _display.popup(Message.clientNotificationsAlreadyEnabled());
    } catch (UnknownClientKeyException ucke) {
      throw new prr.app.exception.UnknownClientKeyException(ucke.getKey());
    }
  }
}
