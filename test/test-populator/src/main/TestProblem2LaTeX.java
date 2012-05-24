package main;

public class TestProblem2LaTeX {
	
	private TestProblem tp;
	
	public TestProblem2LaTeX(TestProblem testProblem) {
		this.tp = testProblem;
	}
	
	public String getResult() {
		String r = "";
		r += "\\begin{testproblem}\n";
		r += "%\\emph(" + tp.getName() + ")\\\\\n";
		r += "\\begin{equation}\n";
		r += "\\begin{split}\n";
		if (tp.getDescription() != null) {
			r += "%" + tp.getDescription() + "\n";
		}
		r += getFuncDef() + "\n";
		r += getConstraints();
		r += "\\end{split}\n";
		r += "\\end{equation}\n";
		r += "\\end{testproblem}\n";
		return r;
	}
	
	private String getFuncDef() {
		String y = tp.getTestFunction().getDefinition();
		String[] s = y.split("\n");
		int n = tp.getDimension();
		if (s.length > 1) {
			y = "f(x) \\\\%TODO write f(x)";
			for (String str : s) {
				y += "\n% " + str;
			}
		} else {
			y = y.replaceFirst("y = ", "");
			y = y.replace(';', ' ');
			y = y.replace('*', ' ');
			y = y.replaceAll("'", "^T");
			y = y.replaceAll("\\s?0\\.5\\s+", "\\\\tfrac{1}{2} ");
			for (int i=1; i<=n; i++) {
				String replace = "x_"+i+"";
				if (n >= 10) {
					replace = "x_{"+i+"}";
				}
				y = y.replaceAll("x\\("+i+"\\)", replace);
			}
			y = y + "\\\\";
		}
		if (n > 1) {
			y = "\\min_{x\\in\\R^" + n + "}\\ & " + y;
		} else {
			y = "\\min_{x\\in\\R}\\ & " + y;
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
			double[][] v = null;
			if (tp.get_v() != "[]") {
				v = getMatrix(tp.get_v());
			}
			if (allElementsAreTheSame(u)) { //TODO
				String u_i = getValue(u[0][0], false);
				r += u_i + " & \\leq x_i";
				if (v != null) {
					if (allElementsAreTheSame(v)) {
						String v_i = getValue(v[0][0], false);
						r += " \\leq " + v_i + " \\\\\n";
					} else {
						for (int i=0; i<u.length; i++) {
							u_i = getValue(u[i][0], false);
							if (i+1 >= 10) {
								r += u_i + " & \\leq x_{" + (i+1) + "}";
							} else {
								r += u_i + " & \\leq x_" + (i+1);
							}
							if (v != null) {
								String v_i = getValue(v[i][0], false);
								r += " \\leq " + v_i + " \\\\\n";
							} else {
								r += " \\\\\n";
							}
						}
					}
				} else {
					r += " \\\\\n";
				}
			} else {
				for (int i=0; i<u.length; i++) {
					String u_i = getValue(u[i][0], false);
					if (i+1 >= 10) {
						r += u_i + " & \\leq x_{" + (i+1) + "}";
					} else {
						r += u_i + " & \\leq x_" + (i+1);
					}
					if (v != null) {
						String v_i = getValue(v[i][0], false);
						r += " \\leq " + v_i + " \\\\\n";
					} else {
						r += " \\\\\n";
					}
				}
			}
		} else if (tp.get_v() != "[]") {
			double[][] v = getMatrix(tp.get_v());
			for (int i=0; i<v.length; i++) {
				String v_i = getValue(v[i][0], false);
				if (i+1 >= 10) {
					r += "x_{" + (i+1) + "} & \\leq " + v_i + " \\\\\n";
				} else {
					r += "x_" + (i+1) + " & \\leq " + v_i + " \\\\\n";
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
	
	private boolean allElementsAreTheSame(double[][] v) {
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
	
	private void printMatrix(double[][] matrix) {
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
