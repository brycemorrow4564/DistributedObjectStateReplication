package examples.mvc.rmi.collaborative.relaying;

import util.models.PropertyListenerRegisterer;

public interface Echoer extends PropertyListenerRegisterer {
	void echo(String value);
}
