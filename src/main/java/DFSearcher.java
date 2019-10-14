import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class DFSearcher extends Agent {
    @Override
    protected void setup() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addBehaviour(new CyclicBehaviour(){
            @Override
            public void action() {
                DFAgentDescription dfd = new DFAgentDescription();
                ServiceDescription sd  = new ServiceDescription();
                sd.setType(Service.PingPong.toString());
                dfd.addServices(sd);

                    try {
                        DFAgentDescription[] foundAgents = DFService.search(getAgent(),dfd);
                        for (DFAgentDescription foundAgent : foundAgents) {
                            System.out.println("I've found " + foundAgent.getName().getLocalName());
                            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                            msg.setContent("Pong");
                            msg.addReceiver(foundAgent.getName());
                            myAgent.send(msg);

                } } catch (FIPAException e) {
                    e.printStackTrace();
                }
                }

        });
    }
}
