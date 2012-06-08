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
	
	public TestResultParser(String resultFile, LinkedList<TestProblem> problems) {
		this.resultFile = resultFile;
		testProblems = problems;
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
					double d = getTime(line);
					p.setSsnSolveTime(d);
					// Get solve time from SQP
					line = br.readLine();
					if (!line.startsWith(sqp_prefix)) {
						System.err.println("Result from SQP expected!");
						return;
					}
					d = getTime(line);
					p.setSqpSolveTime(d);
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
			
			System.out.println("Sorting..\n");
			sort();
			
			System.out.println("Results of SSN (for plot):");
			System.out.println(getResultForPlot(ssn));
			System.out.println("Results of SQP (for plot):");
			System.out.println(getResultForPlot(sqp));
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
		String s = " TP & SSN & SQP \\\\\n" +
					"    & Time (ms) \\\\\\\n";
		int i = 1;
		for (TestProblem p : testProblems) {
			double d1 = p.getSsnSolveTime();
			double d2 = p.getSqpSolveTime();
			s += "  " + i + " & " + d1 + " & " + d2 + " \\\\"
			  + " % " + p.getName() + "\n";
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

}