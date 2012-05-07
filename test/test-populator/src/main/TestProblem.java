package main;

/**
 * TestProblem represents test problem in the form:
 *    min  f(x)
 *     x
 *   s.t.  G*x <= r
 *        a <= x <= b
 * 
 * This problem will be written at the end in a Matlab file.
 * That's why all components of this problem are from the type String
 * and will be written using Matlab syntax.
 */
public class TestProblem {
	
	/**
	 * The name of the problem.
	 */
	private String testProblemName;

	/**
	 * The test function f.
	 */
	private TestFunction f;

	/**
	 * The matrix G defining the left hand side of the inequality constraints.
	 */
	private String G;

	/**
	 * The vector r defining the right hand side of the inequality constraints.
	 */
	private String r;

	/**
	 * The vector a defining the lower bound.
	 */
	private String a;

	/**
	 * The vector b defining the upper bound.
	 */
	private String b;

	/**
	 * The point x0 as the starting point for the algorithms.
	 * x0 should be a feasible point.
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
	 * @param testFunction The function f.
	 */
	public TestProblem(String testProblemName, TestFunction testFunction) {
		this.testProblemName = testProblemName;
		f = testFunction.clone();
	}

	public String getTestProblemName() {
		return testProblemName;
	}

	public TestFunction getTestFunction() {
		return f;
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

	public String get_a() {
		return a;
	}

	public void set_a(String a) {
		if (a == null) {
			a = "[]";
		}
		this.a = a;
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
		result += "a = " + getVectorInLaTeX(a) + "\n";
		result += "b = " + getVectorInLaTeX(b) + "\n";
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