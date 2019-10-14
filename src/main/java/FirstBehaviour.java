import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class FirstBehaviour extends Behaviour {

    private boolean receive = false;

    @Override
    public void onStart() {

        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd  = new ServiceDescription();
        sd.setType(Service.PingPong.toString());
        dfd.addServices(sd);

        DFAgentDescription[] foundAgents = new DFAgentDescription[0];
        try {
            foundAgents = DFService.search(getAgent(),dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        for (DFAgentDescription foundAgent : foundAgents) {
            if (!foundAgent.getName().getLocalName().equals(getAgent().getLocalName())) {
                System.out.println("Отправил Пинг -> " + foundAgent.getName().getLocalName());
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setContent("Ping");
                msg.addReceiver(foundAgent.getName());
                myAgent.send(msg);
            }
        }
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg!=null) {
            if (msg.getContent().equals("Pong")) {
                System.out.println("принял ПОНГ от " + msg.getSender().getLocalName());
                receive = true;
            }
        }
        else block();

    }

    @Override
    public int onEnd() {
        myAgent.addBehaviour( new SecondBehaviour());
        return super.onEnd();
    }

    @Override
    public boolean done() {
        return receive;
    }

}
