package main;

import java.io.File;
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
 *  |
 *  |-- Eclipse-Workspace
 *  |     |-- Matlab-Test-Files-Creator
 *  |
 *  |-- SQP-vs.-Semismooth-Newton
 *        |-- tex
 *        |-- test
 *              |-- 1st_test_problem_func
 *              |-- ...
 *              |-- n-th_test_problem_func
 *              |-- test-populator
 *                    |-- src
 *                          |-- data
 *                          |-- main
 * 
 * @author Vicky H. Tanzil
 */
public class Main {
	/*
	 * TODO use .ini file
	 * TODO try not to use feval
	 * TODO get ready if the lambda variable should be removed
	 */
	
	/**
	 * A hash map containing all test functions as values
	 * and the functions names as keys.
	 */
	private static HashMap<String, TestFunction> testFunctions = new HashMap<String, TestFunction>();

	/**
	 * A hash map with the functions names as keys
	 * and the number of occurence of this function in the test problems.
	 * Needed for naming the test file of the problems.
	 */
	private static HashMap<String, Integer> functionOccurences = new HashMap<String, Integer>();
	
	/**
	 * A list containing all test problems.
	 */
	private static LinkedList<TestProblem> testProblems = new LinkedList<TestProblem>();
	
	/**
	 * The path to the test directory.
	 * All the new test files created will be saved here.
	 * See directories tree in the comment of this class.
	 */
	private static String testDirPath = "../../SQP-vs.-Semismooth-Newton/test/";
	//private static String testDirPath = "../../minimix/SQP-vs.-Semismooth-Newton/test/";
	// The last line over this line is to be used in grafix.math.tu-berlin.de (another directories tree)
	
	/**
	 * The main function.
	 * Reads the xml files containing the test functions and the test problems
	 * and then calls the file creator.
	 */
	public static void main(String[] args) {
		String dataPath = testDirPath+"test-populator/src/data/";
		
		parseFunctionsFromXMLFile(dataPath+"functions.xml");
		parseProblemsFromXMLFile(dataPath+"problems.xml");
		
		LinkedList<String> testTemplates = new LinkedList<String>();
		testTemplates.add(dataPath+"test.tpl");
		testTemplates.add(dataPath+"test100Times.tpl");
		testTemplates.add(dataPath+"testWithFmincon.tpl");
		testTemplates.add(dataPath+"testWithFminconToo.tpl");
		
		boolean usingApproxDiff = true;
		// set this to false to use given gradient and hessian definitions
		// set to true to approximate all gradient and hessian
		
		for (TestProblem p : testProblems) {
			if (usingApproxDiff) {
				p.getTestFunction().setUsingApproximationDifferentiation(usingApproxDiff);
			}
			MFileCreator.create(p, testDirPath+p.getTestProblemName(), testTemplates);
		}
		
		MFileCreator.createMainTestFile(testProblems, "test_all", testDirPath);
		System.out.println("Finish!");
	}

	/**
	 * Read a .xml file defining all available test functions
	 * @param fileName The name of the .xml file
	 */
	public static void parseFunctionsFromXMLFile(String fileName) {
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
		The tags name, var, def, grad and hess are required.
		The tag constant is optional.
		*/
		File file = new File(fileName);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			document.getDocumentElement().normalize();
			NodeList nodeList = document.getElementsByTagName("function");
			for (int i=0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					TestFunction function = parseTestFunction(element);
					testFunctions.put(function.getName(), function);
				}
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
		String grad = getTagValue("grad", element);
		String hess = getTagValue("hess", element);
		function.setVar(var);
		function.setDefinition(def);
		function.setGradient(grad);
		function.setHessianMatrix(hess);
		NodeList constantsNodeList = element.getElementsByTagName("constant");
		if (constantsNodeList.getLength() != 0) {
			for (int i=0; i<constantsNodeList.getLength(); i++) {
				Node node = constantsNodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element constantElement = (Element) node;
					String constantName = getTagValue("constant_name", constantElement);
					String constantValue = getTagValue("constant_value", constantElement);
					function.putConstant(constantName, constantValue);
				}
			}
		}
		return function;
	}
	
	/**
	 * Get tag value of an xml.
	 * @param tag The searched tag in the xml.
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
				<lambda>4</lambda>
				<a>3</a>
				<b>10</b>
				<x0>8</x0>
				<tolerance>0.001</tolerance>
				<max_iteration>100</max_iteration>
			</problem>
		</problems>
		Every problem should be defined in a <problem> tag.
		The tags function_name, lambda, a, b, x0, tolerance and max_iteration are required.
		The tag constant is optional.
		*/
		File file = new File(fileName);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			document.getDocumentElement().normalize();
			NodeList nodeList = document.getElementsByTagName("problem");
			for (int i=0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					TestProblem problem = parseTestProblem(element);
					if (problem == null) {
						testProblems.clear();
						return;
					}
					testProblems.add(problem);
				}
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
		NodeList constantsNodeList = element.getElementsByTagName("constant");
		if (constantsNodeList.getLength() != 0) {
			for (int i=0; i<constantsNodeList.getLength(); i++) {
				Node node = constantsNodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element constantElement = (Element) node;
					String constantName = getTagValue("constant_name", constantElement);
					String constantValue = getTagValue("constant_value", constantElement);
					function.putConstant(constantName, constantValue);
				}
			}
		}
		TestProblem problem = new TestProblem(problemName, function);
		problem.getTestFunction().setName(problemName);
		String lambda = getTagValue("lambda", element);
		String a = getTagValue("a", element);
		String b = getTagValue("b", element);
		String x0 = getTagValue("x0", element);
		String tolerance = getTagValue("tolerance", element);
		String maxIteration = getTagValue("max_iteration", element);
		problem.set_lambda(lambda);
		problem.set_a(a);
		problem.set_b(b);
		problem.set_x0(x0);
		problem.setTolerance(tolerance);
		problem.setMaxIteration(maxIteration);
		return problem;
	}
	
}