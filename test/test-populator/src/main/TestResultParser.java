package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * A parser for the test results.
 * The test result received from the programs
 * should be saved in a file.
 * @author Vicky H. Tanzil
 */
public class TestResultParser {
	
	private String resultFile;
	
	private String ssn = "ssn";
	
	private String sqp = "sqp";
	
	private String ssn_prefix = "x_"+ssn;
	
	private String sqp_prefix = "x_"+sqp;
	
	private LinkedList<TestProblem> testProblems;
	
	@SuppressWarnings("unchecked")
	public TestResultParser(String resultFile, LinkedList<TestProblem> problems) {
		this.resultFile = resultFile;
		testProblems = (LinkedList<TestProblem>) problems.clone();
	}

	public void parse() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(resultFile));
			String line = "";
			while ( (line=br.readLine()) != null ) {
				if (line.startsWith("test_problem_")) {
					// Find problem
					line = line.substring("test_".length());
					TestProblem p = null;
					for (TestProblem problem : testProblems) {
						if (problem.getName().equals(line)) {
							p = problem;
						}
					}
					if (p == null) {
						System.out.println("Cannot find problem:" + line);
						return;
					}
					// Get solve time from SSN
					line = br.readLine();
					if (!line.startsWith(ssn_prefix)) {
						System.err.println("Result from SSN expected!");
						return;
					}
					double t = getTime(line);
					int it = getIterationNumber(line);
					p.setSsnSolveTime(t);
					p.setSsnSolveIterationNumber(it);
					// Get solve time from SQP
					line = br.readLine();
					if (!line.startsWith(sqp_prefix)) {
						System.err.println("Result from SQP expected!");
						return;
					}
					t = getTime(line);
					it = getIterationNumber(line);
					p.setSqpSolveTime(t);
					p.setSqpSolveIterationNumber(it);
					// Get x*
					String xstar = getXStar(line);
					p.set_xstar(xstar);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (Main.printInfo) {
			System.out.println("Total time SSN: " + getTotalTime(ssn));
			System.out.println("Total time SQP: " + getTotalTime(sqp));
			
			System.out.println();
			System.out.println("Comparison Table:");
			System.out.println(getComparisonTable());
			
//			System.out.println("Sorting..\n");
//			sort();
			
			System.out.println("Results of SSN (for plot):");
			System.out.println(getResultForPlot(ssn));
			System.out.println("Results of SQP (for plot):");
			System.out.println(getResultForPlot(sqp));
			
//			printResultOfProductionPlanProblemForTikZ();
		}
	}
	
	private String getXStar(String line) {
		String d = line.split("\\], it =")[0].split("= \\[")[1].trim();
		String[] s = d.split(" ");
		d = "[";
		for (int i=0; i<s.length-1; i++) {
			d += getDoubleValue(s[i]) + "; ";
		}
		d += getDoubleValue(s[s.length-1]) + "]";
		return d;
	}
	
	private String getDoubleValue(String d) {
		double dbl = Double.parseDouble(d);
		long l = Math.round(dbl);
		if ((l+".0").equals(dbl+"")) {
			return l+"";
		}
		return dbl+"";
	}
	
	private double getTime(String line) {
		String d = line.split("solved in")[1].split("ms")[0].trim();
		return Double.parseDouble(d);
	}
	
	private int getIterationNumber(String line) {
		String it = line.split(", solved in")[0].split("it =")[1].trim();
		return Integer.parseInt(it);
	}

	private String getResultForPlot(String algo) {
		if (!algo.equals(ssn) && !algo.equals(sqp)) {
			System.err.println("Algorithm name given unrecognized: "+algo);
		}
		String r = "";
		int k = 1;
		for (TestProblem p : testProblems) {
			double d = 0;
			if (algo.equals(ssn)) {
				d = p.getSsnSolveTime();
			} else if (algo.equals(sqp)) {
				d = p.getSqpSolveTime();
			}
			long i = Math.round(d);
			r += k + " " + i + " \t# " + p.getName() +"\n";
			k++;
		}
		return r;
	}
	
	private double getTotalTime(String algo) {
		if (!algo.equals(ssn) && !algo.equals(sqp)) {
			System.err.println("Algorithm name given unrecognized: "+algo);
		}
		double total = 0;
		for (TestProblem p : testProblems) {
			double d = 0;
			if (algo.equals(ssn)) {
				d = p.getSsnSolveTime();
			} else if (algo.equals(sqp)) {
				d = p.getSqpSolveTime();
			}
			total += d;
		}
		total = Math.round(total*100)/100.0;
		return total;
	}
	
	private String getComparisonTable() {
		String s = " TP & SSN t[ms] & SQP t(ms) " +
				"& SSN #it & SQP #it \\\\\n";
		int i = 1;
		for (TestProblem p : testProblems) {
			String d1 = p.getSsnSolveTime()+"";
			if (d1.split("\\.")[1].length() == 1) {
				d1 += "0";
			}
			String d2 = p.getSqpSolveTime()+"";
			if (d2.split("\\.")[1].length() == 1) {
				d2 += "0";
			}
			s += "  " + i + " & " + d1 + " & " + d2
				+ " & " + p.getSsnSolveIterationNumber()
				+ " & " + p.getSqpSolveIterationNumber()
				+ " \\\\"
				+ " % " + p.getName() + "\n";
			i++;
		}
		return s;
	}
	
	private void sort() {
		int n = testProblems.size();
		int bottom;
		for (bottom=1; bottom<n; bottom++) {
			for (int i=n-1; i>=bottom; i--) {
				TestProblem p1 = testProblems.get(i-1);
				TestProblem p2 = testProblems.get(i);
				if (p1.getSqpSolveTime() > p2.getSqpSolveTime()) {
					testProblems.set(i-1, p2);
					testProblems.set(i, p1);
				}
			}
		}
	}
	
	private void printResultOfProductionPlanProblemForTikZ() {
		String s = "10.977 5.117 11.037 5.170 11.097 5.159 11.157 5.086 11.217 4.955 11.277 4.770 11.337 4.534 11.397 4.255 11.457 3.938 11.517 3.591 11.577 3.221 11.637 2.836 11.697 2.445 11.757 2.056 11.817 1.677 11.877 1.318 11.937 0.987 11.997 0.691 12.057 0.439 12.117 0.238 12.177 0.094 12.237 0.013 12.297 0.000 11.862 0.000 11.293 0.000 10.706 0.000 10.108 0.000 9.509 0.000 8.917 0.000 8.340 0.000 8.011 0.027 8.071 0.123 8.131 0.285 8.191 0.506 8.251 0.780 8.311 1.100 8.371 1.459 8.431 1.849 8.491 2.261 8.551 2.687 8.611 3.118 8.671 3.545 8.731 3.961 8.791 4.357 8.851 4.725 8.911 5.058 8.971 5.349 9.031 5.593 9.091 5.783 9.151 5.917 9.211 5.990 9.271 6.000 9.784 6.000 10.384 6.000 10.745 5.972 10.805 5.882 10.865 5.732 10.925 5.526 10.985 5.268 11.045 4.964 11.105 4.621 11.165 4.244 11.225 3.842 11.285 3.422 11.345 2.992 11.405 2.562 11.465 2.139 11.525 1.732 11.585 1.350 11.645 1.001 11.705 0.693 11.765 0.433 11.825 0.228 11.885 0.084 11.945 0.007 12.005 0.000 11.500 0.000 10.919 0.000 10.324 0.000 9.724 0.000 9.128 0.000 8.545 0.000 7.983 0.000 7.450 0.000 6.953 0.000 6.600 0.012 6.660 0.079 6.720 0.195 6.780 0.353 6.840 0.544 6.900 0.761 6.960 0.994 7.020 1.236 7.080 1.478 7.140 1.710 7.200 1.926 7.260 2.116 7.320 2.275 7.380 2.393 7.440 2.466";
		String[] a = s.split(" ");
		int n = 100;
		int t = 12;
		double eta = 5;
		double tao = (t+0.0)/n;
		String v = "";
		String w = "(0," + eta + ") -- ";
		String r = "(0,10) -- ";
		for (int i=0; i<a.length; i++) {
			if (i%2 == 0) {
				double x = Math.round(tao*100*(i/2.0))/100.0;
				v += x + "/" + a[i] + ", ";
			} else {
				double x = Math.round(tao*100*((i+1)/2.0))/100.0;
				w += "(" + x + "," + a[i] + ") -- ";
				double f = Math.round((10+5*Math.sin(x))*100)/100.0;
				r += "(" + x + "," +f + ") -- ";
			}
		}
		v = "{" + v.substring(0, v.length()-2) + "}";
		System.out.println(v);
		w = w.substring(0, w.length()-4);
		System.out.println(w);
		r = r.substring(0, r.length()-4);
		System.out.println(r);
	}

}