package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.UnknownTerminalKeyException;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {

    DoAddFriend(Network context, Terminal terminal) {
        super(Label.ADD_FRIEND, context, terminal);
        addStringField("friendID", Message.terminalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _network.addFriend(_receiver.getId(), stringField("friendID"));
        } catch (UnknownTerminalKeyException utke) {
            throw new prr.app.exception.UnknownTerminalKeyException(utke.getKey());
        }
    }
}
