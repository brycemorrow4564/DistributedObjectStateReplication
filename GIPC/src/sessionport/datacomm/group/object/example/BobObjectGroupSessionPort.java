package sessionport.datacomm.group.object.example;

import port.ParticipantChoice;

public class BobObjectGroupSessionPort {
	public static void main(String[] args) {
		AnObjectGroupSessionPortLauncher.launchSessionPartipant("9092", "Bob", ParticipantChoice.SYMMETRIC_JOIN);		
	}


}
