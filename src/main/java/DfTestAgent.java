import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class DfTestAgent extends Agent {
    @Override
    protected void setup() {
        super.setup();
        registerAgent();
        addBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage msg = getAgent().receive();
                if (msg!=null) {
                    System.out.println(msg.getContent());
                }
                else block();
            }

            @Override
            public boolean done() {
                return false;
            }
        });
        }
    private void registerAgent() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(Service.PingPong.toString());
        sd.setName(Service.PingPong.toString()+getLocalName());
        dfd.addServices(sd);

        try {
            DFService.register(this,dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
