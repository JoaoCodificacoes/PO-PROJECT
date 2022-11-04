package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.AlreadyInModeException;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {

    DoTurnOnTerminal(Network context, Terminal terminal) {
        super(Label.POWER_ON, context, terminal);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.setOnIdle();
        } catch (AlreadyInModeException aime) {
            _display.popup(Message.alreadyOn());
        }
    }
}
