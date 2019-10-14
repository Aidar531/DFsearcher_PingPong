import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SecondBehaviour extends Behaviour {

    private boolean received = false;

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg!=null) {
            if (msg.getContent().equals("Ping")) {
                System.out.println("Принял Пинг от " + msg.getSender().getLocalName());
                ACLMessage reply = msg.createReply();
                reply.setPerformative( ACLMessage.INFORM );
                System.out.println("Отправил Pong -> " + msg.getSender().getLocalName());
                reply.setContent("Pong");
                getAgent().send(reply);
                received = true;
            }
        }

        else block();

    }

    @Override
    public int onEnd() {
       myAgent.addBehaviour(new FirstBehaviour());
        return super.onEnd();
    }

    @Override
    public boolean done() {
        return received;
    }
}
