package prr.app.client;

import prr.core.Network;
import prr.core.exception.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

    DoRegisterClient(Network receiver) {
        super(Label.REGISTER_CLIENT, receiver);
        addStringField("key", Message.key());
        addStringField("name", Message.name());
        addIntegerField("taxId", Message.taxId());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.registerClient(stringField("key"), stringField("name"), integerField("taxId"));
        } catch (DuplicateClientKeyException dcke) {
            throw new prr.app.exception.DuplicateClientKeyException(dcke.getKey());
        }
    }
}
