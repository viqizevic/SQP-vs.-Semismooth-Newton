package main;

import java.util.HashMap;

public class TestFunction {
	
	private String name;
	
	private String var;
	
	private HashMap<String, String> constants;
	
	private String definition;
	
	private String gradient;
	
	private String hessianMatrix;
	
	private boolean usingApproximationDifferentiation;
	
	private String eps;

	public TestFunction(String name) {
		this.name = name;
		constants = new HashMap<String, String>();
		eps = "0.001";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVar() {
		return var;
	}
	
	public void setVar(String var) {
		this.var = var;
	}

	public void putConstant(String name, String value) {
		constants.put(name, value);
	}

	public HashMap<String, String> getConstants() {
		return constants;
	}
	
	public String getConstantValue(String constant) {
		return constants.get(constant);
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public void setUsingApproximationDifferentiation(
			boolean usingApproximationDifferentiation) {
		this.usingApproximationDifferentiation = usingApproximationDifferentiation;
	}

	public String getGradient() {
		if (usingApproximationDifferentiation || gradient == null) {
			gradient = "g = approx_gradient('"+name+"',x,"+eps+");";
		}
		return gradient;
	}

	public void setGradient(String gradient) {
		this.gradient = gradient;
	}

	public String getHessianMatrix() {
		if (usingApproximationDifferentiation || hessianMatrix == null) {
			hessianMatrix = "H = approx_hessian('"+name+"',x,"+eps+");";
		}
		return hessianMatrix;
	}

	public void setHessianMatrix(String hessianMatrix) {
		this.hessianMatrix = hessianMatrix;
	}
	
	public String extraDefinition() {
		return "(lambda/2)*norm(x)^2";
	}
	
	public String extraGradient() {
		return "lambda*x";
	}
	
	public String extraHessian() {
		return "lambda*eye(length(x))";
	}
	
	public String toString() {
		String str = "Name : " + name + "\n";
		str += "Var  : " + var + "\n";
		for (String s : constants.keySet()) {
			str += "Cons : " + s + " = " + constants.get(s) + ";\n";
		}
		str += "Def  : " + definition + "\n";
		str += "Grad : " + gradient + "\n";
		str += "Hess : " + hessianMatrix + "\n";
		return str;
	}
	
	public TestFunction clone() {
		TestFunction f = new TestFunction(name);
		f.setVar(var);
		f.setDefinition(definition);
		f.setGradient(gradient);
		f.setHessianMatrix(hessianMatrix);
		if (constants.size() != 0) {
			for (String s : constants.keySet()) {
				f.putConstant(s, constants.get(s));
			}
		}
		return f;
	}
	
}