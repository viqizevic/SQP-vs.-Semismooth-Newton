package main;

/**
 * TestProblem represents test problem in the form:
 *    min  f(x)
 *     x
 *   s.t.  A*x = b
 *         G*x <= r
 *       u <= x <= v
 * 
 * This problem will be written at the end in a Matlab file.
 * That's why all components of this problem are from the type String
 * and will be written using Matlab syntax.
 * @author Vicky H. Tanzil
 */
public class TestProblem {
	
	/**
	 * The name of the problem.
	 */
	private String name;

	/**
	 * The objective function.
	 */
	private TestFunction f;
	
	/**
	 * The description of the problem.
	 * Especially, from where the problem was taken.
	 */
	private String description;
	
	/**
	 * The classification of the problem.
	 */
	private String classification;
	
	/**
	 * The matrix defining the left hand side of the equality constraints.
	 */
	private String A;
	
	/**
	 * The vector defining the right hand side of the equality constraints.
	 */
	private String b;
	
	/**
	 * The matrix defining the left hand side of the inequality constraints.
	 */
	private String G;

	/**
	 * The vector defining the right hand side of the inequality constraints.
	 */
	private String r;

	/**
	 * The vector defining the lower bound.
	 */
	private String u;

	/**
	 * The vector defining the upper bound.
	 */
	private String v;

	/**
	 * The point as the starting point for the algorithms.
	 * This should be a feasible point.
	 */
	private String x0;

	/**
	 * The tolerance for the stop criteria in the algorithms
	 */
	private String tolerance;

	/**
	 * The maximum number of iterations the algorithms can have.
	 */
	private String maxIteration;

	/**
	 * Create an initial test problem.
	 * @param testProblemName The name of the problem.
	 * @param testFunction The objective function.
	 */
	public TestProblem(String testProblemName, TestFunction testFunction) {
		this.name = testProblemName;
		f = testFunction.clone();
	}

	public String getName() {
		return name;
	}

	public TestFunction getTestFunction() {
		return f;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String get_A() {
		return A;
	}

	public void set_A(String A) {
		if (A == null) {
			A = "[]";
		}
		this.A = A;
	}

	public String get_b() {
		return b;
	}

	public void set_b(String b) {
		if (b == null) {
			b = "[]";
		}
		this.b = b;
	}

	public String get_G() {
		return G;
	}

	public void set_G(String G) {
		if (G == null) {
			G = "[]";
		}
		this.G = G;
	}

	public String get_r() {
		return r;
	}

	public void set_r(String r) {
		if (r == null) {
			r = "[]";
		}
		this.r = r;
	}

	public String get_u() {
		return u;
	}

	public void set_u(String u) {
		if (u == null) {
			u = "[]";
		}
		this.u = u;
	}

	public String get_v() {
		return v;
	}

	public void set_v(String v) {
		if (v == null) {
			v = "[]";
		}
		this.v = v;
	}

	public String get_x0() {
		return x0;
	}

	public void set_x0(String x0) {
		this.x0 = x0;
	}

	public String getTolerance() {
		return tolerance;
	}

	public void setTolerance(String tolerance) {
		this.tolerance = tolerance;
	}

	public String getMaxIteration() {
		return maxIteration;
	}

	public void setMaxIteration(String maxIteration) {
		this.maxIteration = maxIteration;
	}
	
	public int getDimension() {
		if (x0 != null) {
			String[] s = x0.split(";");
			return s.length;
		} else {
			System.err.println("Unable to compute dimension, since x0 undefined");
			return 0;
		}
	}
	
	public String toLaTeX() {
		String result = "";
		result += "u = " + getVectorInLaTeX(u) + "\n";
		result += "v = " + getVectorInLaTeX(v) + "\n";
		result += "x^0 = " + getVectorInLaTeX(x0) + "\n";
		return result;
	}
	
	private String getVectorInLaTeX(String vec) {
		String[] s = vec.replace("[", "").replace("]", "").split(";");
		for (int i=0; i<s.length; i++) {
			s[i] = s[i].trim();
		}
		String v = "\\left(\\begin{array}{c} ";
		for (int i=0; i<s.length; i++) {
			if (i < s.length-1) {
				v += s[i] + " \\\\ ";
			} else {
				v += s[i];
			}
		}
		v += " \\end{array}\\right)";
		return v;
	}
}