package prr.app.terminal;

import prr.core.Network;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

    DoEndInteractiveCommunication(Network context, Terminal terminal) {
        super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canEndCurrentCommunication());
        addIntegerField("duration", Message.duration());
    }

    @Override
    protected final void execute() throws CommandException {
        if (_receiver.canEndCurrentCommunication()) {

        }
    }
}
