package inputport.nio.manager;

import inputport.nio.manager.commands.RequestResponse;
import inputport.nio.manager.commands.Response;
import inputport.nio.manager.listeners.SocketChannelCloseListener;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;

public interface SelectionConnectionManager {

	public abstract void putRequestResponse(RequestResponse request);

	public abstract void preProcessConnectAndAccepts();

	public abstract void close(SelectionKey selectionKey,
			Exception readException);

	public abstract void registerCloseListener(SelectableChannel aChannel,
			SocketChannelCloseListener aCloseListener);

	Response get(SelectableChannel channel);

}