package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.AlreadyInModeException;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {

    DoSilenceTerminal(Network context, Terminal terminal) {
        super(Label.MUTE_TERMINAL, context, terminal);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.setOnSilent();
        } catch (AlreadyInModeException aime) {
            _display.popup(Message.alreadySilent());
        }

    }
}
