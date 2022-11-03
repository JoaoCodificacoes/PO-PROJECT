package prr.app.client;

import prr.core.Network;
import prr.core.clients.Client;
import prr.core.exception.UnknownClientKeyException;
import prr.core.notification.Notification;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

    DoShowClient(Network receiver) {
        super(Label.SHOW_CLIENT, receiver);
        addStringField("key", Message.key());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            Client c = _receiver.getClient(stringField("key"));
            _display.addLine(c.toString());
            for (Notification n : c.readNotifications())
                _display.addLine(n.toString());
            _display.display();

        } catch (UnknownClientKeyException ucke) {
            throw new prr.app.exception.UnknownClientKeyException(ucke.getKey());
        }
    }
}
