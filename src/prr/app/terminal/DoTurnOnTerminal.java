package prr.app.terminal;

import prr.core.Network;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {

    DoTurnOnTerminal(Network context, Terminal terminal) {
        super(Label.POWER_ON, context, terminal);
    }

    @Override
    protected final void execute() throws CommandException {
        if (!_receiver.setOnIdle())
            _display.popup(Message.alreadyOn());
    }
}
