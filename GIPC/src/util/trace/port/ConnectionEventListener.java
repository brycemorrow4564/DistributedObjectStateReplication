package util.trace.port;


public interface ConnectionEventListener  {
	public  void newEvent(ConnectionEvent anEvent);
	public  void newEvent(ReplaceConnectionEvent anEvent);
	public void receiveOnlySource(Object aSource);
	public void sendOnlySource(Object aSource);

	
}
