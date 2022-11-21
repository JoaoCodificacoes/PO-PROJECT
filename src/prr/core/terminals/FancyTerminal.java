package prr.core.terminals;


import prr.core.clients.Client;
import prr.core.exception.DestinationBusyException;
import prr.core.exception.DestinationOffException;
import prr.core.exception.DestinationSilentException;
import prr.core.exception.UnsupportedAtDestinationException;

public class FancyTerminal extends Terminal {
    public FancyTerminal(String id, Client c) {
        super(id, c);
    }

    @Override
    public String toString() {
        return super.toString("FANCY");
    }

    @Override
    public void makeVideoCall(Terminal to) throws DestinationSilentException,
            DestinationOffException, DestinationBusyException, UnsupportedAtDestinationException {
        getTerminalMode().toBusy();
        to.acceptVideoCall(this);
    }

    @Override
    protected void acceptVideoCall(Terminal from) throws DestinationBusyException, DestinationOffException,
            DestinationSilentException {

        getTerminalMode().getCall(from);
        getTerminalMode().toBusy();
    }
}
