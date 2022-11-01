package prr.app.terminals;

import prr.core.Network;
import prr.core.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

    DoOpenMenuTerminalConsole(Network receiver) {
        super(Label.OPEN_MENU_TERMINAL, receiver);
        addStringField("key", Message.terminalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            (new prr.app.terminal.Menu(_receiver, _receiver.getTerminal(stringField("key")))).open();
        } catch (UnknownTerminalKeyException e) {
            throw new prr.app.exception.UnknownTerminalKeyException(e.getKey());
        }
    }
}
