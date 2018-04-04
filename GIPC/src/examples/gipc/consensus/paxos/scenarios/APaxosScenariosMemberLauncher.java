package examples.gipc.consensus.paxos.scenarios;

import inputport.datacomm.group.GroupSendMessageForwarderSelector;
import consensus.ConcurrencyKind;
import consensus.ConsensusMechanismFactory;
import consensus.ProposalState;
import consensus.ReplicationSynchrony;
import consensus.paxos.sequential.ASequentialPaxosConsensusMechanismFactory;
import examples.gipc.consensus.AnExampleProposerLauncher;
import examples.gipc.consensus.paxos.APaxosMemberLauncher;

public class APaxosScenariosMemberLauncher extends APaxosMemberLauncher {

	public APaxosScenariosMemberLauncher(String aLocalName, int aPortNumber) {
		super(aLocalName, aPortNumber);
	}
	@Override
	protected  void createConsensusMechanisms(short anId) {
		createMeaningConsensusMechanism();
	}
	@Override
	protected void customizeConsensusMechanisms() {
		simulateBasicPaxos();
		setThreads();// needed only for creating example cases		
	}
	protected void addListenersAndVetoersToConsensusMechanisms() {
		addListenersAndVetoersToMeaningMechanism();;
	}
	// for case 7 call this
	protected void doNotOverrideRetry() {
		overrideRetry = false;
	}
	protected void setThreads() {
		meaningOfLifeMechanism.setAcceptedInSeparareThread(true);
		meaningOfLifeMechanism.setAcceptInSeparateThread(true);
		meaningOfLifeMechanism.setPrepareInSeparateThread(true);
		meaningOfLifeMechanism.setPreparedInSeparateThread(true);
	}
	@Override
	// needed only for creating example cases
    protected  void setFactories() {
		GroupSendMessageForwarderSelector.setGroupSendMessageForwarderFactory(
		new APaxosMultiCastFactory());
	}
}
