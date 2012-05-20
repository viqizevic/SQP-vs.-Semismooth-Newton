package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	
	/**
	 * A hash map containing all test functions as values
	 * and the functions names as keys.
	 */
	private static HashMap<String, TestFunction> testFunctions = new HashMap<String, TestFunction>();

	/**
	 * A hash map with the functions names as keys
	 * and the number of occurrence of this function in the test problems.
	 * Needed for naming the test file of the problems.
	 */
	private static HashMap<String, Integer> functionOccurences = new HashMap<String, Integer>();
	
	/**
	 * A list containing all test problems.
	 */
	private static LinkedList<TestProblem> testProblems = new LinkedList<TestProblem>();
	
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
		String functionsXMLFile = pathToDataDir + configs.get("functions_xml_file");
		
		LinkedList<String> problemsXMLFiles = getMultipleVars("problems_xml_file_", configs);
		boolean useApproxDiff = Boolean.parseBoolean(configs.get("use_approx_diff"));
		//boolean useOctave = Boolean.parseBoolean(configs.get("use_octave"));
		
		String templateFileExtension = configs.get("template_file_extension");
		HashMap<String, String> testTemplates = new HashMap<String, String>();
		for (String templateName : getMultipleVars("test_template_file_name_", configs)) {
			testTemplates.put(templateName, pathToDataDir+templateName+templateFileExtension);
		}
		String prefixForMainTestFile = configs.get("prefix_for_main_test_file");
		
		parseFunctionsFromXMLFile(functionsXMLFile);
		for (String problemsXMLFile: problemsXMLFiles) {
			parseProblemsFromXMLFile(pathToDataDir+problemsXMLFile);
		}
		
		for (TestProblem p : testProblems) {
			if (useApproxDiff) {
				p.getTestFunction().setUsingApproximationDifferentiation(useApproxDiff);
			}
			TestFileCreator.create(p, pathToTestDir+p.getName(), testTemplates);
		}
		
		TestFileCreator.createMainTestFile(testProblems,
				prefixForMainTestFile, pathToTestDir, testTemplates.keySet());
		System.out.println("Finish!");
		
		if (!testProblems.isEmpty()) {
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
			int[] dimensions = new int[25];
			for (TestProblem p : testProblems) {
				int n = p.getDimension();
				dimensions[n]++;
			}
			for (int i=0; i<dimensions.length; i++) {
				if (dimensions[i] > 0) {
					System.out.println(i + "\t" + dimensions[i]);
				}
			}
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
	
	private static LinkedList<String> getMultipleVars(String prefix,
			HashMap<String, String> configs) {
		int k = 0;
		LinkedList<String> vars = new LinkedList<String>();
		boolean keepReadingVars = true;
		while (keepReadingVars) {
			String var = prefix + k;
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

	/**
	 * Read a .xml file defining all available test functions
	 * @param fileName The name of the .xml file
	 */
	private static void parseFunctionsFromXMLFile(String fileName) {
		/*
		This is a simple example how the file should be:
		<functions>
			<function>
				<name>quad_func</name>
				<var>x</var>
				<constant>
					<constant_name>xd</constant_name>
					<constant_value>3</constant_value>
				</constant>
				<def>y = norm(x-xd)^2;</def>
				<grad>g = 2*(x-xd);</grad>
				<hess>H = 2*eye(length(x));</hess>
			</function>
		</functions>
		Every function should be defined in a <function> tag.
		The tags name, var and def are required.
		The tags constant, grad and hess are optional.
		*/
		File file = new File(fileName);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			document.getDocumentElement().normalize();
			Element functions = (Element) document.getElementsByTagName("functions").item(0);
			if (functions == null) {
				System.err.println("Cannot find <functions> tag.");
				return;
			}
			LinkedList<Element> listOfFunctionElements =
				getListOfElementsInParentElement(functions, "function"); 
			for (Element element : listOfFunctionElements) {
				TestFunction function = parseTestFunction(element);
				testFunctions.put(function.getName(), function);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return a test function defined by the xml element.
	 * @param element The xml element defining the test function.
	 * @return The test function.
	 */
	private static TestFunction parseTestFunction(Element element) {
		String name = getTagValue("name", element);
		TestFunction function = new TestFunction(name);
		String var = getTagValue("var", element);
		String def = getTagValue("def", element);
		String grad = getTagValueIfExists("grad", element);
		String hess = getTagValueIfExists("hess", element);
		function.setVar(var);
		function.setDefinition(def);
		function.setGradient(grad);
		function.setHessianMatrix(hess);
		LinkedList<Element> listOfConstantElements =
			getListOfElementsInParentElement(element, "constant");
		for (Element constantElement: listOfConstantElements) {
			String constantName = getTagValue("constant_name", constantElement);
			String constantValue = getTagValue("constant_value", constantElement);
			function.putConstant(constantName, constantValue);
		}
		return function;
	}
	
	private static LinkedList<Element> getListOfElementsInParentElement(Element parentElement,
			String elementName) {
		LinkedList<Element> list = new LinkedList<Element>();
		NodeList nodeList = null;
		if (parentElement != null) {
			nodeList = parentElement.getElementsByTagName(elementName);
		} else {
			return list;
		}
		for (int i=0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				list.add(element);
			}
		}
		return list;
	}
	
	/**
	 * Get tag value of an xml.
	 * @param tag The searched tag in the xml element.
	 * @param e The xml element.
	 * @return The value inside this tag.
	 */
	private static String getTagValue(String tag, Element e) {
		NodeList nodeList = e.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = nodeList.item(0);
		if (node == null) {
			return "";
		}
		return node.getNodeValue().trim();
	}
	
	/**
	 * Get tag value of an xml, if the tag exists in the element given.
	 * @param tag The searched tag in the xml element.
	 * @param element The xml element.
	 * @return The value inside this tag, if it exists, otherwise return null.
	 */
	private static String getTagValueIfExists(String tag, Element element) {
		NodeList nList = element.getElementsByTagName(tag);
		if (nList.getLength() != 0) {
			return getTagValue(tag, element);
		} else {
			return null;
		}
	}

	/**
	 * Read a .xml file defining all test problems.
	 * @param fileName The name of the .xml file.
	 */
	private static void parseProblemsFromXMLFile(String fileName) {
		/*
		This is a simple example how the file should be:
		<problems>
			<problem>
				<function_name>quad_func</function_name>
				<constant>
					<constant_name>xd</constant_name>
					<constant_value>4</constant_value>
				</constant>
				<u>3</u>
				<v>10</v>
				<x0>8</x0>
				<tolerance>0.001</tolerance>
				<max_iteration>100</max_iteration>
			</problem>
		</problems>
		Every problem should be defined in a <problem> tag.
		The tags function_name and x0 are required.
		*/
		File file = new File(fileName);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			document.getDocumentElement().normalize();
			Element problems = (Element) document.getElementsByTagName("problems").item(0);
			if (problems == null) {
				System.err.println("Cannot find <problems> tag.");
				return;
			}
			LinkedList<Element> listOfProblemElements =
				getListOfElementsInParentElement(problems, "problem");
			for (Element element: listOfProblemElements) {
				TestProblem problem = parseTestProblem(element);
				if (problem == null) {
					testProblems.clear();
					return;
				}
				testProblems.add(problem);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return a test problem defined in the xml file.
	 * @param element The xml element containing the test problem.
	 * @return
	 */
	private static TestProblem parseTestProblem(Element element) {
		String functionName = getTagValue("function_name", element);
		String problemName = functionName;
		if (!functionOccurences.containsKey(functionName)) {
			functionOccurences.put(functionName, 1);
		} else {
			int nr = functionOccurences.get(functionName);
			functionOccurences.put(functionName, nr+1);
			problemName = functionName + "_" + nr;
		}
		if (!testFunctions.containsKey(functionName)) {
			System.err.println("Cannot find function: " + functionName);
			return null;
		}
		TestFunction function = testFunctions.get(functionName);
		TestProblem problem = new TestProblem(problemName, function);
		problem.getTestFunction().setName(problemName);
		String description = getTagValueIfExists("description", element);
		LinkedList<Element> listOfConstantElements =
			getListOfElementsInParentElement(element, "constant");
		for (Element constantElement: listOfConstantElements) {
			String constantName = getTagValue("constant_name", constantElement);
			String constantValue = getTagValue("constant_value", constantElement);
			problem.getTestFunction().putConstant(constantName, constantValue);
		}
		String A = getTagValueIfExists("A", element);
		String b = getTagValueIfExists("b", element);
		String G = getTagValueIfExists("G", element);
		String r = getTagValueIfExists("r", element);
		String u = getTagValueIfExists("u", element);
		String v = getTagValueIfExists("v", element);
		String x0 = getTagValue("x0", element);
		String tolerance = getTagValueIfExists("tolerance", element);
		String maxIteration = getTagValueIfExists("max_iteration", element);
		problem.setDescription(description);
		problem.set_A(A);
		problem.set_b(b);
		problem.set_G(G);
		problem.set_r(r);
		problem.set_u(u);
		problem.set_v(v);
		problem.set_x0(x0);
		problem.setTolerance(tolerance);
		problem.setMaxIteration(maxIteration);
		String classification = "";
		if (A != null) {
			classification += "A";
		}
		if (G != null) {
			classification += "G";
		}
		if (u != null || v != null) {
			classification += "u";
		}
		problem.setClassification(classification);
		return problem;
	}
}