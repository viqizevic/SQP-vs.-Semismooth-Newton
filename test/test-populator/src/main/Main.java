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

public class Main {
	
	private static HashMap<String, TestFunction> testFunctions = new HashMap<String, TestFunction>();
	
	private static HashMap<String, Integer> functionOccurences = new HashMap<String, Integer>();
	
	private static LinkedList<TestProblem> testProblems = new LinkedList<TestProblem>();
	
	private static String testDirectoryPath = "../../SQP-vs.-Semismooth-Newton/test/";
	
	public static void main(String[] args) {
		parseFunctionsFromXMLFile("src/data/functions.xml");
		parseProblemsFromXMLFile("src/data/problems.xml");
		for (TestProblem p : testProblems) {
			MFileCreator.create(p, testDirectoryPath+p.getTestProblemName());
		}
	}

	public static void parseFunctionsFromXMLFile(String fileName) {
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
	
	private static String getTagValue(String tag, Element e) {
		NodeList nodeList = e.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = nodeList.item(0);
		if (node == null) {
			return "";
		}
		return node.getNodeValue().trim();
	}

	
	private static void parseProblemsFromXMLFile(String fileName) {
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