package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This is a project for a bachelor thesis using Matlab
 * to make a comparison of two methods of the nonlinear optimization:
 * SQP and Semismooth-Newton.
 * 
 * This Java project should create the needed Matlab test files.
 * Therefore is the name of this project: Matlab-Test-Files-Creator.
 * 
 * This class is called Main, since it is the only one that has a main function.
 * The main function reads the xml files
 * containing the test functions and the test problems
 * and then calls the file creator.
 * 
 * Directories tree:
 * |
 * |-- workspace (Eclipse workspace)
 *    |-- SQP-vs.-SSN (Project name)
 *       |-- SQP-vs.-Semismooth-Newton (The Git repository)
 *           |-- tex
 *           |-- test
 *              |-- 1st_test_problem_func
 *              |-- ...
 *              |-- n-th_test_problem_func
 *              |-- test-populator
 *                  |-- src
 *                       |-- data
 *                       |-- main
 * 
 * The source of this project is linked from the folder in test-populator.
 * Read readme.markdown in the folder test-populator for more informations.
 * 
 * @author Vicky H. Tanzil
 */
public class Main {
	
	/**
	 * The path to find the configuration file.
	 */
	private static String configFile = "SQP-vs.-Semismooth-Newton/test.config";
	
	public static boolean printInfo = true;
	
	/**
	 * The main function.
	 * Reads the xml files containing the test functions and the test problems
	 * and then calls the file creator.
	 */
	public static void main(String[] args) {
		
		// Save the variables found in configuration file in a hash map.
		HashMap<String, String> configs = readConfigFile(configFile);
		if (configs == null) {
			return;
		}
		
		// Get each variable.
		String pathToTestDir = configs.get("path_to_test_dir");
		String pathToDataDir = pathToTestDir + configs.get("path_to_data_dir");
		LinkedList<String> functionsXMLFiles = getMultipleConfigVars("functions_xml_file", configs);
		
		LinkedList<String> problemsXMLFiles = getMultipleConfigVars("problems_xml_file", configs);
		boolean useApproxDiff = Boolean.parseBoolean(configs.get("use_approx_diff"));
		//boolean useOctave = Boolean.parseBoolean(configs.get("use_octave"));
		
		String templateFileExtension = configs.get("template_file_extension");
		HashMap<String, String> testTemplates = new HashMap<String, String>();
		for (String templateName : getMultipleConfigVars("test_template_file_name", configs)) {
			testTemplates.put(templateName, pathToDataDir+templateName+templateFileExtension);
		}
		String prefixForMainTestFile = configs.get("prefix_for_main_test_file");
		
		MainDatabase db = new MainDatabase(pathToDataDir, functionsXMLFiles, problemsXMLFiles);
		LinkedList<TestProblem> problems = db.getTestProblemsInGivenOrder();
		for (TestProblem p : problems) {
			if (useApproxDiff) {
				p.getTestFunction().setUsingApproximationDifferentiation(useApproxDiff);
			}
			TestFileCreator.create(p, pathToTestDir+p.getName(), testTemplates);
		}
		
		TestFileCreator.createMainTestFile(problems,
				prefixForMainTestFile, pathToTestDir, testTemplates.keySet());
		if (printInfo) {
			System.out.println("Finished creating test files!");
		}
		
		if (problems.isEmpty()) {
			System.out.println("No Problem found.");
		} else {
			if (printInfo) {
				printStatistic(db.getTestFunctions(), problems);
			}
			String resultFile = pathToDataDir+configs.get("result_file");
			TestResultParser trp = new TestResultParser(resultFile, problems);
			trp.parse();
//			for (TestProblem p : problems) {
//				TestProblem2LaTeX tp2tex = new TestProblem2LaTeX(p);
//				System.out.println(tp2tex.toString());
//			}
		}
	}
	
	private static void printStatistic(LinkedList<TestFunction> testFunctions,
			LinkedList<TestProblem> testProblems) {
		System.out.println("\nTotal number of problems: " + testProblems.size());
		
		HashMap<String, Integer> classifications = new HashMap<String, Integer>();
		for (TestProblem p : testProblems) {
			String cl = p.getClassification();
			if (!classifications.containsKey(cl)) {
				classifications.put(cl, 1);
			} else {
				classifications.put(cl, classifications.get(cl)+1);
			}
		}
		System.out.println("\nClass\t#Problem");
		for (String cl : classifications.keySet()) {
			System.out.println(cl + "\t" + classifications.get(cl));
		}
		
		System.out.println("\nn\t#Problem");
		int[] dimensions = new int[35];
		for (TestProblem p : testProblems) {
			int n = p.getDimension();
			dimensions[n]++;
		}
		for (int i=0; i<dimensions.length; i++) {
			if (dimensions[i] > 0) {
				System.out.println(i + "\t" + dimensions[i]);
			}
		}
		
		System.out.println("\nTest functions (" + testFunctions.size() + "):");
		for (TestFunction f : testFunctions) {
			System.out.println(f.getName());
		}
		
		System.out.println("\nTest problems (" + testProblems.size() + "):");
		for (TestProblem p : testProblems) {
			System.out.println(p.getName());
		}
	}
	
	/**
	 * Read the configuration file given.
	 * @param fileName The path to the configuration file.
	 * @return A hash map containing all variables in this file.
	 */
	private static HashMap<String, String> readConfigFile(String fileName) {
		HashMap<String, String> configs = new HashMap<String, String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = "";
			while ( (line=br.readLine()) != null ) {
				// Ignore lines started with '#'.
				if (!line.equals("") && !line.startsWith("#")) {
					// The configuration variable should be defined as followed.
					// <variable_name> = <variable_value>
					String[] str = line.split("=");
					String var = str[0].trim();
					String value = str[1].trim();
					configs.put(var, value);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return configs;
	}
	
	/**
	 * Get variables in the config files from the type
	 * <var_name>_<index>, where <var_name> the name of the variable is
	 * and <index> a number starting from 0.
	 * @param varName
	 * @param configs
	 * @return
	 */
	private static LinkedList<String> getMultipleConfigVars(String varName,
			HashMap<String, String> configs) {
		int k = 0;
		LinkedList<String> vars = new LinkedList<String>();
		boolean keepReadingVars = true;
		while (keepReadingVars) {
			String var = varName + "_" + k;
			if (!configs.containsKey(var)) {
				keepReadingVars = false;
			} else {
				String name = configs.get(var);
				vars.add(name);
				k++;
			}
		}
		return vars;
	}
	
}