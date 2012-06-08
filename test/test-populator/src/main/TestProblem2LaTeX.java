package main;

public class TestProblem2LaTeX {
	
	private TestProblem tp;
	
	public TestProblem2LaTeX(TestProblem testProblem) {
		this.tp = testProblem;
	}
	
	public String toString() {
		if (tp.getName().equals("problem_AG_opt_ctrl")) {
			return "%" + tp.getName();
		}
		String r = "";
		r += "\\begin{testproblem}\n";
		if (tp.getDescription() != null) {
			r += tp.getDescription() + "\n";
		}
		r += "\\begin{equation}\n";
		r += getFuncDef() + "\n";
		r += "\\end{equation}\n";
		r += "\\begin{equation*}\n";
		r += "\\begin{split}\n";
		r += "\\nb " + getConstraints();
		r += "\\end{split}\n";
		r += "\\end{equation*}\n";
		r += "\n\\begin{equation*}\n";
		r += "\\xopt = "+getVectorInLaTeX(tp.get_xstar())+"\n";
		r += "\\end{equation*}\n";
		r += "\\end{testproblem}\n";
		return r;
	}
	
	public String getStringForTestFile() {
		String r = tp.getName() + "\n";
		if (r.equals("problem_AG_opt_ctrl"+"\n")) {
			return r;
		}
		if (tp.getDescription() != null) {
			r += tp.getDescription() + "\n";
		}
		r += getFuncDef() + "\n";
		r += "\\nb " + getConstraints();
		if (tp.get_xstar() != null) {
			r += "\\xopt = "+getVectorInLaTeX(tp.get_xstar());
		}
		return r;
	}
	
	private String getFuncDef() {
		String y = tp.getTestFunction().getDefinition();
		if (tp.getTestFunction().getDefinitionInLaTeX() != null) {
			y = tp.getTestFunction().getDefinitionInLaTeX();
		}
		String[] s = y.split("\n");
		int n = tp.getDimension();
		if (s.length > 1) {
			y = "f(x) %TODO write f(x)";
			for (String str : s) {
				y += "\n% " + str;
			}
		} else {
			y = y.replaceFirst("y = ", "");
			y = y.replace(';', ' ');
			y = y.replace('*', ' ');
			y = y.replaceAll("'", "^T");
			for (int i=1; i<=n; i++) {
				String replace = "x_"+i+"";
				if (n >= 10) {
					replace = "x_{"+i+"}";
				}
				y = y.replaceAll("x\\("+i+"\\)", replace);
			}
		}
		if (n > 1) {
			y = "\\min_{x\\in\\R^" + n + "}\\ " + y;
		} else {
			y = "\\min_{x\\in\\R}\\ " + y;
		}
		return y;
	}
	
	private String getConstraints() {
		String r = "";
		if (tp.get_A() != "[]") {
			double[][] m = getMatrix(tp.get_A());
			double[][] v = getMatrix(tp.get_b());
			for (int i=0; i<m.length; i++) {
				String l = getLineX(m[i]);
				String k = getValue(v[i][0], false);
				r += l + "& = " + k + " \\\\\n";
			}
		}
		if (tp.get_G() != "[]") {
			double[][] m = getMatrix(tp.get_G());
			double[][] v = getMatrix(tp.get_r());
			for (int i=0; i<m.length; i++) {
				String l = getLineX(m[i]);
				String k = getValue(v[i][0], false);
				r += l + "& \\leq " + k + " \\\\\n";
			}
		}
		if (tp.get_u() != "[]") {
			double[][] u = getMatrix(tp.get_u());
			int n = u.length;
			double[][] v = null;
			if (tp.get_v() != "[]") {
				v = getMatrix(tp.get_v());
			}
			if (v == null) {
				if (allVectorElementsAreTheSame(u)) {
					r += getBoundedX(getValue(u[0][0], false), null, "i") +
							",\\ " + getIndexesTo("i", n) + " \\\\\n";
				} else {
					for (int i=0; i<u.length; i++) {
						r += getBoundedX(getValue(u[i][0], false), null, (i+1)+"") + " \\\\\n";
					}
				}
			} else {
				if (allVectorElementsAreTheSame(u) && allVectorElementsAreTheSame(v)) {
					r += getBoundedX(getValue(u[0][0],false), getValue(v[0][0], false), "i") +
							",\\ " + getIndexesTo("i", n) + " \\\\\n";
				} else {
					for (int i=0; i<u.length; i++) {
						r += getBoundedX(getValue(u[i][0], false), getValue(v[i][0], false), (i+1)+"") + " \\\\\n";
					}
				}
			}
		} else if (tp.get_v() != "[]") {
			double[][] v = getMatrix(tp.get_v());
			int n = v.length;
			if (allVectorElementsAreTheSame(v)) {
				r += getBoundedX(getValue(v[0][0], false), null, "i") +
						",\\ " + getIndexesTo("i", n) + " \\\\\n";
			} else {
				for (int i=0; i<v.length; i++) {
					r += getBoundedX(getValue(v[i][0], false), null, (i+1)+"") + " \\\\\n";
				}
			}
		}
		return r;
	}
	
	private double[][] getMatrix(String matrix) {
		matrix = matrix.replaceAll("\\[", "");
		matrix = matrix.replaceAll("\\]", "");
		String[] s = matrix.split(";");
		int m = s.length;
		int n = s[0].trim().split("\\s+").length;
		double[][] r = new double[m][n];
		for (int i=0; i<m; i++) {
			String[] t = s[i].trim().split("\\s+");
			for (int j=0; j<n; j++) {
				r[i][j] = Double.parseDouble(t[j]);
			}
		}
		return r;
	}
	
	private String getLineX(double[] m_i) {
		String l = "";
		for (int j=0; j<m_i.length; j++) {
			String x = getX(m_i[j], j);
			if (x != "") {
				if (l.equals("")) {
					l = x;
				} else {
					if (x.startsWith("-")) {
						l += "- " + x.substring(1);
					} else {
						l += "+ " + x;
					}
				}
			}
		}
		return l;
	}
	
	private String getX(double v, int j) {
		if (v == 0.0) {
			return "";
		} else {
			String s = getValue(v, true);
			String idx = (j+1) + "";
			if (j+1 >= 10) {
				idx = "{" + (j+1) + "}";
			}
			return s + "x_" + idx + " ";
		}
	}
	
	private String getValue(double v, boolean removeOne) {
		String s = v + "";
		if (s.endsWith(".0")) {
			s = s.substring(0, s.length()-2);
		}
		if (removeOne) {
			if (s.equals("1")) {
				s = "";
			} else if (s.equals("-1")) {
				s = "-";
			} else {
				s = s + " ";
			}
		}
		return s;
	}
	
	private boolean allVectorElementsAreTheSame(double[][] v) {
		if (v.length <= 1) {
			return false;
		}
		if (v[0].length != 1) {
			System.err.println("Expected is a vector!");
		}
		double e = v[0][0];
		for (int i=1; i<v.length; i++) {
			if (e != v[i][0]) {
				return false;
			}
		}
		return true;
	}
	
	private String getBoundedX(String u, String v, String idx) {
		String s = "";
		String x = "";
		if (idx.length() > 1) {
			x = "x_{" + idx + "}";
		} else {
			x = "x_" + idx + "";
		}
		if (u != null && v != null) {
			s = u + " \\leq " + x + " & \\leq " + v;
		} else {
			if (u != null) {
				s = u + " & \\leq " + x;
			}
			if (v != null) {
				s = x + " & \\leq " + v;
			}
		}
		return s;
	}
	
	private String getIndexesTo(String idxVar, int idx) {
		if (idx == 0) {
			return null;
		} else if (idx == 1) {
			return idxVar + " = 1";
		} else if (idx == 2) {
			return idxVar + " = 1,2";
		} else if (idx == 3) {
			return idxVar + " = 1,2,3";
		} else {
			return idxVar + " = 1,\\ldots," + idx;
		}
	}
	
	private String getVectorInLaTeX(String v) {
		String vec = "(";
		String[] s = v.split(";");
		vec += s[0].replace("[","").trim() + ", ";
		for (int i=1; i<s.length-1; i++) {
			vec += s[i].trim() + ", ";
		}
		vec += s[s.length-1].replace("]","").trim();
		vec += ")^T";
		return vec;
	}
	
	public static void printMatrix(double[][] matrix) {
		String r = "";
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[i].length; j++) {
				r += matrix[i][j] + "\t";
			}
			r += "\n";
		}
		System.out.println(r);
	}

}
