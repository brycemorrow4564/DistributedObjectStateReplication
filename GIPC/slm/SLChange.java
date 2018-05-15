package slm;
import shapes.RemoteShape;
public class SLChange {
	RemoteShape shapeModel;
	String key;
	public SLChange (String theKey, RemoteShape theShapeModel){
		key = theKey;
		shapeModel = theShapeModel;
	}
	public String getKey() {
		return key;
	}
	public RemoteShape getShapeModel() {
		return shapeModel;
	}
}