package main;

import java.util.HashMap;

/**
 * TestFunction represents a scalar function
 * f : R^n -> R
 * This function will be written at the end in a Matlab file.
 * That's why all components of this function are from the type String
 * and will be written using Matlab syntax.
 * @author Vicky H. Tanzil
 */
public class TestFunction {
	
	/**
	 * The name of the test function.
	 */
	private String name;

	/**
	 * The variable of the function.
	 */
	private String var;

	/**
	 * A hash map containing all constants needed by the function.
	 */
	private HashMap<String, String> constants;

	/**
	 * The function definition.
	 * It should be a Matlab code defining a variable 'y'.
	 * That means it should start with 'y = '
	 * and end with a semicolon ';'.
	 */
	private String definition;

	/**
	 * The gradient of the function.
	 * It should be a Matlab code defining a variable 'g'.
	 * That means it should start with 'g = '
	 * and end with a semicolon ';'.
	 */
	private String gradient;
	
	/**
	 * The hessian matrix of the function.
	 * It should be a Matlab code defining a variable 'H'.
	 * That means it should start with 'H = '
	 * and end with a semicolon ';'.
	 */
	private String hessianMatrix;
	
	/**
	 * A boolean telling if the function provided a gradient and hessian matrix
	 * or not.
	 */
	private boolean usingApproximationDifferentiation;
	
	/**
	 * The variable needed by the approximation of the gradient
	 * and the hessian matrix.
	 */
	private String eps;
	
	private String definitionInLaTeX;

	/**
	 * Create a test function with the name given.
	 * The components of this function are at first empty.
	 * @param name The function name.
	 */
	public TestFunction(String name) {
		this.name = name;
		constants = new HashMap<String, String>();
		eps = "0.00001";
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

	public String getEps() {
		return eps;
	}

	public void setEps(String eps) {
		if (eps == null) {
			return;
		}
		this.eps = eps;
	}

	public String getGradient() {
		if (usingApproximationDifferentiation || gradient.equals("not available")) {
			gradient = "g = approx_gradient('"+name+"',x,"+eps+");";
		}
		return gradient;
	}

	public void setGradient(String gradient) {
		if (gradient == null) {
			gradient = "not available";
		}
		this.gradient = gradient;
	}

	public String getHessianMatrix() {
		if (usingApproximationDifferentiation || hessianMatrix.equals("not available")) {
			hessianMatrix = "H = approx_hessian('"+name+"',x,"+eps+");";
		}
		return hessianMatrix;
	}

	public void setHessianMatrix(String hessianMatrix) {
		if (hessianMatrix == null) {
			hessianMatrix = "not available";
		}
		this.hessianMatrix = hessianMatrix;
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
	
	public String getDefinitionInLaTeX() {
		return definitionInLaTeX;
	}

	public void setDefinitionInLaTeX(String definitionInLaTeX) {
		this.definitionInLaTeX = definitionInLaTeX;
	}

	public TestFunction clone() {
		TestFunction f = new TestFunction(name);
		f.setVar(var);
		f.setDefinition(definition);
		f.setGradient(gradient);
		f.setHessianMatrix(hessianMatrix);
		f.setUsingApproximationDifferentiation(usingApproximationDifferentiation);
		f.setDefinitionInLaTeX(definitionInLaTeX);
		if (constants.size() != 0) {
			for (String s : constants.keySet()) {
				f.putConstant(s, constants.get(s));
			}
		}
		return f;
	}
	
}