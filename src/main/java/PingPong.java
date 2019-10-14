import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class PingPong extends Agent {

    @Override
    protected void setup() {

        registerAgent();

        if (getLocalName().equals("Ping")) {
            addBehaviour(new FirstBehaviour() {
            });
        }
        else addBehaviour(new SecondBehaviour() {
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
