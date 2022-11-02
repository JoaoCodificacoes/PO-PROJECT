package prr.app.terminal;

import prr.core.Network;
import prr.core.exception.*;
import prr.core.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

    DoStartInteractiveCommunication(Network context, Terminal terminal) {
        super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, Terminal::canStartCommunication);
        addStringField("toID", Message.terminalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String commType = optionField("commType");
        try {
            _network.sendInteractiveCommunication(_receiver,stringField("toID"),commType);

        } catch (DestinationOffException doe) {
            _display.popup(Message.destinationIsOff(doe.getKey()));

        } catch (DestinationSilentException dse) {
            _display.popup(Message.destinationIsSilent(dse.getKey()));

        } catch (UnsupportedAtDestinationException uade) {
            _display.popup(Message.unsupportedAtDestination(uade.getKey(), commType));

        } catch (DestinationBusyException dbe) {
            _display.popup(Message.destinationIsBusy(dbe.getKey()));

        } catch (UnsupportedAtOriginException uaoe) {
            _display.popup(Message.unsupportedAtOrigin(uaoe.getKey(), commType));

        } catch (prr.core.exception.UnknownTerminalKeyException e) {
            throw new prr.app.exception.UnknownTerminalKeyException(e.getKey());
        }
    }
}
