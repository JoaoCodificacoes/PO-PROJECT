package prr.core.terminals;


import prr.core.clients.Client;

public class FancyTerminal extends Terminal {
    public FancyTerminal(String id, Client c) {
        super(id, c);
    }

    @Override
    public String toString() {
        return super.toString("FANCY");
    }

    public void makeVideoCall(Terminal to) {
        //FIXME implement
    }

    protected void acceptVideoCall(Terminal from) {
        //FIXME implement
    }
}
