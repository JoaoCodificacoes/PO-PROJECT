package prr.core.terminals;


import prr.core.clients.Client;

public class BasicTerminal extends Terminal{

    public BasicTerminal(String id, Client c){
        super(id, c);
    }

    @Override
    public String toString() {
        String friends = "";
        if (!getFriends().isEmpty()) {
            StringBuilder friendsBuilder = new StringBuilder();
            for (Terminal t : getFriends()) {
                friendsBuilder.append(t.getId()).append(",");
            }
            friends = friendsBuilder.toString();
            friends = friends.substring(0, friends.length() - 1);
        }
        return "BASIC|%s|%s|%s|%d|%d|%s".formatted(getId(),
                getOwner().getClientKey(),
                getTerminalStatus(),
                Math.round(getBalancePaid()),
                Math.round(getBalanceDebt()),
                friends);
    }

    public void makeVideoCall(Terminal to){

    }

    protected void acceptVideoCall(Terminal from){

    }

}
