package prr.core.terminals;


import prr.core.clients.Client;

public class FancyTerminal extends Terminal{
    public FancyTerminal(String id, Client c) {
        super(id, c);
    }

    @Override
    public String toString() {
        StringBuilder friends = new StringBuilder();
        if (!getFriends().isEmpty())
            for (Terminal t: getFriends()) {
                friends.append(t.getId());
            }

        return "FANCY|%s|%s|%s|%d|%d|%s".formatted(getId(),
                getOwner().getClientKey(),
                getTerminalStatus(),
                Math.round(getBalancePaid()),
                Math.round(getBalanceDebt()),
                friends.toString());
    }

    public void makeVideoCall(Terminal to){

    }

    protected void acceptVideoCall(Terminal from){

    }
}
