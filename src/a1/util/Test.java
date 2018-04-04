package a1.util;

import java.io.Serializable;

public class Test implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4181295920816295908L;
	private String test; 
	public Test(String test) { this.test = test; }
	public void print() { System.out.println(this.test); }
}
