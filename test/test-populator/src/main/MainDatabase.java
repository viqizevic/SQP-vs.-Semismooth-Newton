package main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
 * The database for our test.
 * All informations about the test functions
 * and the test problems are saved in XML files.
 * This class can be used to read those files
 * and create the needed {@link TestFunction}
 * and {@link TestProblem} objects.
 * @author Vicky H. Tanzil
 */
public class MainDatabase {
	
	/**
	 * A hash map containing all test functions as values
	 * and the functions names as keys.
	 */
	private HashMap<String, TestFunction> testFunctions;
	
	/**
	 * A hash map with the functions names as keys
	 * and the number of occurrence of this function in the test problems.
	 * Needed for naming the test file of the problems.
	 */
	private HashMap<String, Integer> functionOccurences;
	
	/**
	 * A hash map containing all test problems as values
	 * and the problem names as keys.
	 */
	private HashMap<String, TestProblem> testProblems;
	
	public MainDatabase(String pathToDataDirectory,
			LinkedList<String> functionsXMLFiles, LinkedList<String> problemsXMLFiles) {
		
		testFunctions = new HashMap<String, TestFunction>();
		for (String fileName : functionsXMLFiles) {
			LinkedList<Element> list = parseElementsFromXMLFile(
					pathToDataDirectory+fileName,"functions", "function"); 
			for (Element e : list) {
				TestFunction f = parseTestFunction(e);
				testFunctions.put(f.getName(), f);
			}
		}
		
		functionOccurences = new HashMap<String, Integer>();
		testProblems = new HashMap<String, TestProblem>();
		for (String fileName : problemsXMLFiles) {
			LinkedList<Element> list = parseElementsFromXMLFile(
					pathToDataDirectory+fileName, "problems", "problem");
			for (Element e : list) {
				TestProblem p = parseTestProblem(e);
				testProblems.put(p.getName(), p);
				if (p.getName().endsWith("_"+0)) {
					p.setName(p.getName().substring(0, p.getName().length()-2));
				}
				p.getTestFunction().setName("func_for_"+p.getName());
			}
		}
	}
	
	public LinkedList<TestFunction> getTestFunctions() {
		LinkedList<TestFunction> list = new LinkedList<TestFunction>();
		Object[] functionNames = testFunctions.keySet().toArray();
		Arrays.sort(functionNames);
		for (Object name : functionNames) {
			list.add(testFunctions.get(name));
		}
		return list;
	}
	
	public LinkedList<TestProblem> getTestProblems() {
		Object[] problemNames = testProblems.keySet().toArray();
		Arrays.sort(problemNames);
		LinkedList<TestProblem> list = new LinkedList<TestProblem>();
		for (Object name : problemNames) {
			list.add(testProblems.get(name));
		}
		return list;
	}
	
	private LinkedList<Element> parseElementsFromXMLFile(String fileName,
			String mainTag, String searchedElementTag) {
		LinkedList<Element> result = null;
		File file = new File(fileName);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			document.getDocumentElement().normalize();
			Element mainElement = (Element) document.getElementsByTagName(mainTag).item(0);
			if (mainElement == null) {
				System.err.println("Cannot find the main tag: " + mainTag);
				return result;
			}
			result = getListOfElementsInParentElement(mainElement, searchedElementTag); 
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private LinkedList<Element> getListOfElementsInParentElement(Element parentElement,
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
	 * Get tag value of an xml, if the tag exists in the element given.
	 * @param tag The searched tag in the xml element.
	 * @param element The xml element.
	 * @return The value inside this tag, if it exists, otherwise return null.
	 */
	private String getTagValue(String tag, Element element) {
		NodeList nList = element.getElementsByTagName(tag);
		if (nList.getLength() != 0) {
			nList = nList.item(0).getChildNodes();
			Node node = nList.item(0);
			return node.getNodeValue().trim();
		} else {
			return null;
		}
	}
	
	/**
	 * Return a test function defined by the xml element.
	 * @param element The xml element defining the test function.
	 * @return The test function.
	 */
	private TestFunction parseTestFunction(Element element) {
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
		LinkedList<Element> listOfConstantElements =
			getListOfElementsInParentElement(element, "constant");
		for (Element constantElement: listOfConstantElements) {
			String constantName = getTagValue("constant_name", constantElement);
			String constantValue = getTagValue("constant_value", constantElement);
			function.putConstant(constantName, constantValue);
		}
		return function;
	}
	
	/**
	 * Return a test problem defined in the xml file.
	 * @param element The xml element containing the test problem.
	 * @return
	 */
	private TestProblem parseTestProblem(Element element) {
		
		// Get the objective function for the problem
		String functionName = getTagValue("function_name", element);
		if (!testFunctions.containsKey(functionName)) {
			System.err.println("Cannot find function: " + functionName);
			return null;
		}
		TestFunction function = testFunctions.get(functionName);
		
		String A = getTagValue("A", element);
		String b = getTagValue("b", element);
		String G = getTagValue("G", element);
		String r = getTagValue("r", element);
		String u = getTagValue("u", element);
		String v = getTagValue("v", element);
		String x0 = getTagValue("x0", element);
		String tolerance = getTagValue("tolerance", element);
		String maxIteration = getTagValue("max_iteration", element);
		String classification = "";
		if (A != null) {
			classification += "A";
		}
		if (G != null) {
			classification += "G";
		}
		if (u != null || v != null) {
			classification += "v";
		}
		if (classification.equals("")) {
			classification = "0";
		}

		String problemName = getTagValue("name", element);
		problemName = "problem" + "_" + classification + "_" + problemName + "_" + 0;
		int k = 1;
		while (testProblems.containsKey(problemName)) {
			String suffix = "_"+k;
			problemName = problemName.substring(0, problemName.length()-2);
			problemName += suffix;
			k++;
		}
		
		// Create a new test problem object
		TestProblem problem = new TestProblem(problemName, function);
		
		// Set a new name for the function, if it's used by another problem too
		if (!functionOccurences.containsKey(functionName)) {
			functionOccurences.put(functionName, 1);
		} else {
			int nr = functionOccurences.get(functionName);
			functionOccurences.put(functionName, nr+1);
			problem.getTestFunction().setName(functionName+"_"+nr);
		}
		
		String description = getTagValue("description", element);
		LinkedList<Element> listOfConstantElements =
			getListOfElementsInParentElement(element, "constant");
		for (Element constantElement: listOfConstantElements) {
			String constantName = getTagValue("constant_name", constantElement);
			String constantValue = getTagValue("constant_value", constantElement);
			problem.getTestFunction().putConstant(constantName, constantValue);
		}
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
		problem.setClassification(classification);
		return problem;
	}
}