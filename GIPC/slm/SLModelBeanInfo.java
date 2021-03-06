package slm;
import java.beans.*;
import java.lang.reflect.*;
//import bus.uigen.AttributeNames;

public class SLModelBeanInfo extends SimpleBeanInfo {
  public BeanDescriptor getBeanDescriptor() {
    try {
      Class c;
      Class[] params;
      c = slm.SLModel.class;
      BeanDescriptor bd = new BeanDescriptor(c);
      return bd;
    } catch (Exception e) {
      return null;
    }
  }
  public MethodDescriptor[] getMethodDescriptors() {
    try {
      Class c;
      Class[] params;
      c = slm.SLModel.class;
      Method m;
      MethodDescriptor[] array = new MethodDescriptor[32];
      MethodDescriptor md;
      params = new Class[0];
      m = c.getMethod("elements",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Elements");
      md.setValue("toolbar", new java.lang.Boolean(false));
      md.setValue("menuName", "SLModel");
      array[0] = md;

      params = new Class[1];
      params[0] = java.lang.Object.class;
      m = c.getMethod("setController",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Set Controller ...");
      md.setValue("menuName", "SLModel");
      array[1] = md;

      params = new Class[1];
      params[0] = slm.ShapesList.class;
      m = c.getMethod("set",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Set ...");
      md.setValue("menuName", "SLModel");
      array[2] = md;

      params = new Class[1];
      params[0] = util.undo.Listener.class;
      m = c.getMethod("addListener",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Add Listener ...");
      md.setValue("menuName", "Listenable");
      array[3] = md;

      params = new Class[0];
      m = c.getMethod("notifyListeners",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Notify Listeners");
      md.setValue("menuName", "Listenable");
      array[4] = md;

      params = new Class[1];
      params[0] = java.lang.String.class;
      m = c.getMethod("removeLabel",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Remove Label ...");
      md.setValue("toolbar", new java.lang.Boolean(false));
      md.setValue("menuName", "SLModel");
      array[5] = md;

      params = new Class[0];
      m = c.getMethod("notifyAll",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Notify All");
      md.setValue("menuName", "Object");
      array[6] = md;

      params = new Class[0];
      m = c.getMethod("labels",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Labels");
      md.setValue("toolbar", new java.lang.Boolean(false));
      md.setValue("menuName", "SLModel");
      array[7] = md;

      params = new Class[1];
      params[0] = java.lang.Object.class;
      m = c.getMethod("containsKey",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Contains Key ...");
      md.setValue("toolbar", new java.lang.Boolean(false));
      md.setValue("menuName", "SLModel");
      array[8] = md;

      params = new Class[1];
      params[0] = java.lang.String.class;
      m = c.getMethod("remove",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Remove ...");
      md.setValue("toolbar", new java.lang.Boolean(false));
      md.setValue("menuName", "SLModel");
      array[9] = md;

      params = new Class[1];
      params[0] = java.lang.Long.TYPE;
      m = c.getMethod("wait",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Wait ...");
      md.setValue("menuName", "Object");
      array[10] = md;

      params = new Class[2];
      params[0] = java.lang.Long.TYPE;
      params[1] = java.lang.Integer.TYPE;
      m = c.getMethod("wait",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Wait ...");
      md.setValue("menuName", "Object");
      array[11] = md;

      params = new Class[0];
      m = c.getMethod("toString",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("To String");
      md.setValue("menuName", "Object");
      array[12] = md;

      params = new Class[0];
      m = c.getMethod("keys",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Keys");
      md.setValue("menuName", "SLModel");
      array[13] = md;

      params = new Class[1];
      params[0] = java.lang.Object.class;
      m = c.getMethod("contains",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Contains ...");
      md.setValue("menuName", "SLModel");
      array[14] = md;

      params = new Class[0];
      m = c.getMethod("notify",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Notify");
      md.setValue("menuName", "Object");
      array[15] = md;

      params = new Class[2];
      params[0] = java.lang.String.class;
      params[1] = java.awt.Rectangle.class;
      m = c.getMethod("setBounds",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Set Bounds ...");
      md.setValue("menuName", "SLModel");
      array[16] = md;

      params = new Class[2];
      params[0] = java.util.Hashtable.class;
      params[1] = java.lang.Object.class;
      m = c.getMethod("getKey",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Get Key ...");
      md.setValue("menuName", "SLModel");
      array[17] = md;

      params = new Class[0];
      m = c.getMethod("clone",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Clone");
      md.setValue("menuName", "Listenable");
      array[18] = md;

      params = new Class[1];
      params[0] = java.lang.Object.class;
      m = c.getMethod("equals",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Equals ...");
      md.setValue("menuName", "Object");
      array[19] = md;

      params = new Class[1];
      params[0] = java.lang.String.class;
      m = c.getMethod("getLabel",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Get Label ...");
      md.setValue("menuName", "SLModel");
      array[20] = md;

      params = new Class[1];
      params[0] = java.lang.String.class;
      m = c.getMethod("get",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Get ...");
      md.setValue("menuName", "SLModel");
      array[21] = md;

      params = new Class[1];
      params[0] = util.undo.Listener.class;
      m = c.getMethod("removeListener",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Remove Listener ...");
      md.setValue("menuName", "Listenable");
      array[22] = md;

      params = new Class[2];
      params[0] = java.lang.String.class;
      params[1] = java.lang.String.class;
      m = c.getMethod("put",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Put ...");
      md.setValue("menuName", "SLModel");
      array[23] = md;

      params = new Class[0];
      m = c.getMethod("hashCode",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Hash Code");
      md.setValue("menuName", "Object");
      array[24] = md;

      params = new Class[1];
      params[0] = java.lang.Object.class;
      m = c.getMethod("notifyListeners",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Notify Listeners ...");
      md.setValue("menuName", "Listenable");
      array[25] = md;

      params = new Class[0];
      m = c.getMethod("clear",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Clear");
      md.setValue("menuName", "SLModel");
      array[26] = md;

      params = new Class[1];
      params[0] = java.lang.String.class;
      m = c.getMethod("getKey",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Get Key ...");
      md.setValue("menuName", "SLModel");
      array[27] = md;

      params = new Class[0];
      m = c.getMethod("getClass",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Get Class");
      md.setValue("menuName", "Bean methods");
      array[28] = md;

      params = new Class[0];
      m = c.getMethod("wait",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Wait");
      md.setValue("menuName", "Object");
      array[29] = md;

      params = new Class[1];
      params[0] = shapes.RemoteShape.class;
      m = c.getMethod("getKey",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Get Key ...");
      md.setValue("menuName", "SLModel");
      array[30] = md;

      params = new Class[2];
      params[0] = java.lang.String.class;
      params[1] = shapes.RemoteShape.class;
      m = c.getMethod("put",params);
      md = new MethodDescriptor(m);
      md.setDisplayName("Put ...");
      md.setValue("menuName", "SLModel");
      array[31] = md;

      return array;
    } catch (Exception e) {
      return null;
    }
  }
  public PropertyDescriptor[] getPropertyDescriptors() {

    try {
      Class c;
      Class[] params;
      PropertyDescriptor[] array = new PropertyDescriptor[2];
      PropertyDescriptor pd;
      pd = new PropertyDescriptor("class", slm.SLModel.class
      , "getClass", null);
      array[0] = pd;

      pd = new PropertyDescriptor("controller", slm.SLModel.class
      );
      array[1] = pd;

      return array;
    }catch (Exception e) {return null;}
  }
}
