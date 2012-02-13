package main;

/**
 * Our test problem is in the form:
 *  min ( f(x) + (lambda/2)*|x|^2 )
 *   x
 *      s.t.  a <= x <= b
 */
public class TestProblem {
	
	private String testProblemName;
	
	private TestFunction f;

	private String lambda;

	private String a;
	
	private String b;
	
	private String x0;
	
	private String tolerance;
	
	private String maxIteration;

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

	public String get_lambda() {
		return lambda;
	}

	public void set_lambda(String lambda) {
		this.lambda = lambda;
	}

	public String get_a() {
		return a;
	}

	public void set_a(String a) {
		this.a = a;
	}

	public String get_b() {
		return b;
	}

	public void set_b(String b) {
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
	
}