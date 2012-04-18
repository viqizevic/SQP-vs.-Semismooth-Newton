package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * MFileCreator ables to create the needed Matlab files
 * for the test problem.
 * @author Vicky H. Tanzil
 */
public class MFileCreator {
	
	/**
	 * The extension for the Matlab files.
	 */
	private static final String extension = ".m";

	private static final String withFmincon = "_with_fmincon";
	
	private static final String withFminconToo = "_with_fmincon_too";
	
	private final String v0 = "_v0";
	
	/**
	 * The test problem.
	 */
	private TestProblem testProblem;

	/**
	 * The test function f in the test problem.
	 */
	private TestFunction f;

	/**
	 * The name of the file defining the function definition.
	 */
	private String defFileName;
	
	/**
	 * The name of the file defining the function gradient.
	 */
	private String gradFileName;
	
	/**
	 * The name of the file defining the function hessian matrix.
	 */
	private String hessFileName;
	
	/**
	 * The name of the file calling the algorithms.
	 */
	private String testFileName;

	/**
	 * The path to the directory where all created files should be placed.
	 */
	private String directoryPath;
	
	/**
	 * Create MFileCreator for the given test problem.
	 * @param testProblem The test problem.
	 * @param directoryPath The directory where the created files should be placed. 
	 */
	public MFileCreator(TestProblem testProblem, String directoryPath) {
		this.testProblem = testProblem;
		f = testProblem.getTestFunction();
		defFileName = f.getName();
		gradFileName = "grad_" + defFileName;
		hessFileName = "hess_" + defFileName;
		testFileName = "test_" + defFileName;
		if (directoryPath.endsWith("/")) {
			directoryPath = directoryPath.substring(0, directoryPath.length()-1);
		}
		File dir = new File(directoryPath);
		if (!dir.exists()) {
			if (dir.mkdirs()) {
				System.out.println("Create directory: " + directoryPath);
			}
		}
		this.directoryPath = directoryPath;
	}
	
	private void createFunctionDefinitionFile() {
		String fileName = defFileName;
		// create the definition file for function f
		String content = "function y = " + fileName + "(" + f.getVar() + ")\n";
		if (f.getConstants().size() != 0) {
			for (String s : f.getConstants().keySet()) {
				content += "\t" + s + " = " + f.getConstantValue(s) + ";\n";
			}
		}
		content += "\t" + f.getDefinition() + "\n";
		content += "end";
		createFile(fileName+extension, content);
		// create the definition file for the complete function in the problem
		content = "function y = " + fileName + v0 + "(" + f.getVar() + ")\n";
		content += "\tlambda = " + testProblem.get_lambda() + ";\n";
		content += "\ty = " + fileName + "(" + f.getVar() + ") + " + f.extraDefinition() + ";\n";
		content += "end";
		createFile(fileName+v0+extension, content);
	}
	
	private void createFunctionGradientFile() {
		String fileName = gradFileName;
		// create the file for the gradient of function f
		String content = "function g = " + fileName + "(" + f.getVar() + ")\n";
		if (f.getConstants().size() != 0) {
			for (String s : f.getConstants().keySet()) {
				content += "\t" + s + " = " + f.getConstantValue(s) + ";\n";
			}
		}
		content += "\t" + f.getGradient() + "\n";
		content += "end";
		createFile(fileName+extension, content);
		// create the file for the gradient of the complete function in the problem 
		content = "function g = " + fileName + v0 + "(" + f.getVar() + ")\n";
		content += "\tlambda = " + testProblem.get_lambda() + ";\n";
		content += "\tg = " + fileName + "(" + f.getVar() + ") + " + f.extraGradient() + ";\n";
		content += "end";
		createFile(fileName+v0+extension, content);
	}

	private void createFunctionHessianFile() {
		String fileName = hessFileName;
		// create the file for the hessian matrix of function f
		String content = "function H = " + fileName + "(" + f.getVar() + ")\n";
		if (f.getConstants().size() != 0) {
			for (String s : f.getConstants().keySet()) {
				content += "\t" + s + " = " + f.getConstantValue(s) + ";\n";
			}
		}
		content += "\t" + f.getHessianMatrix() + "\n";
		content += "end";
		createFile(fileName+extension, content);
		// create the file for the hessian matrix of the complete function in the problem 
		content = "function H = " + fileName + v0 + "(" + f.getVar() + ")\n";
		content += "\tlambda = " + testProblem.get_lambda() + ";\n";
		content += "\tH = " + fileName + "(" + f.getVar() + ") + " + f.extraHessian() + ";\n";
		content += "end";
		createFile(fileName+v0+extension, content);
	}
	
	private void createTestProblemFile(String templateFilePath) {
		String fileName = testFileName;
		String content = getTestFileContentUsingTemplate(templateFilePath);
		createFile(fileName+extension, content);
	}

	private void createTestProblemFileWithFmincon(String templateFilePath) {
		String fileName = testFileName + withFmincon;
		String content = getTestFileContentUsingTemplate(templateFilePath);
		createFile(fileName+extension, content);
	}

	// FIXME use template file
	private void createTestProblemFileWithFminconToo() {
		String fileName = testFileName + withFminconToo;
		String content = "function " + fileName + "()\n";
		content += "\tlambda = " + testProblem.get_lambda() + ";\n";
		content += "\ta = " + testProblem.get_a() + ";\n";
		content += "\tb = " + testProblem.get_b() + ";\n";
		content += "\tx0 = " + testProblem.get_x0() + ";\n";
		content += "\ttol = " + testProblem.getTolerance() + ";\n";
		content += "\titmax = " + testProblem.getMaxIteration() + ";\n";
		content += "\ttic;\n";
		content += "\t[x_ssn,fval_ssn,it_ssn] = semismooth_newton('" + defFileName + "','" + gradFileName + "','" + hessFileName + "',lambda,a,b,x0,itmax,tol);\n";
		content += "\tt_ssn = toc;\n";
		content += "\tx1 = sprintf('%.3f ',x_ssn);\n";
		content += "\tf1 = sprintf('f(x_ssn) = %.3f',fval_ssn);\n";
		content += "\tt1 = sprintf('solved in %.2f ms.',t_ssn*1000);\n";
		content += "\tstr1 = ['x_ssn = [ ', x1, '], ', f1, ', it = ', num2str(it_ssn), ', ', t1];\n";
		content += "\tA = [ -eye(length(a)); eye(length(b)) ];\n";
		content += "\tc = [ -a; b ];\n";
		content += "\ttic;\n";
		content += "\t[x_sqp,fval_sqp,it_sqp] = sqp('" + defFileName+v0 + "','" + gradFileName+v0 + "','" + hessFileName+v0 + "',A,c,x0,itmax,tol);\n";
		content += "\tt_sqp = toc;\n";
		content += "\tx2 = sprintf('%.3f ',x_sqp);\n";
		content += "\tf2 = sprintf('f(x_sqp) = %.3f',fval_sqp);\n";
		content += "\tt2 = sprintf('solved in %.2f ms.',t_sqp*1000);\n";
		content += "\tstr2 = ['x_sqp = [ ', x2, '], ', f2, ', it = ', num2str(it_sqp), ', ', t2];\n";
		content += "\toptions = optimset('Algorithm','active-set','Display','off');\n";
		content += "\ttic;\n";
		content += "\t[x_fmc,fval_fmc,exitflag,output] = fmincon('" + defFileName+v0 + "',x0,[],[],[],[],a,b,[],options);\n";
		content += "\tt_fmc = toc;\n";
		content += "\tx3 = sprintf('%.3f ',x_fmc);\n";
		content += "\tf3 = sprintf('f(x_fmc) = %.3f',fval_fmc);\n";
		content += "\tt3 = sprintf('solved in %.2f ms.',t_fmc*1000);\n";
		content += "\tstr3 = ['x_fmc = [ ', x3, '], ', f3, ', ', t3];\n";
		content += "\ta = sprintf('%.3f ',a);\n";
		content += "\tb = sprintf('%.3f ',b);\n";
		content += "\tx0 = sprintf('%.3f ',x0);\n";
		content += "\tstr0 = ['a = [ ', a, '], b = [ ', b, '], x0 = [ ', x0, ']'];\n";
		content += "\tdisp(str0);\n";
		content += "\tdisp(str1);\n";
		content += "\tdisp(str2);\n";
		content += "\tdisp(str3);\n";
		content += "end";
		createFile(fileName+extension, content);
	}
	
	/**
	 * Get the content of the test file.
	 * @param templateFilePath The template file defining the test.
	 * The variables placed within this file should be
	 * started with '{var_' and ended with '}'.
	 * @return The content of the test file.
	 */
	private String getTestFileContentUsingTemplate(String templateFilePath) {
		// define the variables to be replaced in the template file
		HashMap<String, String> vars = new HashMap<String, String>();
		vars.put("{var_function_name}", defFileName);
		vars.put("{var_grad_function_name}", gradFileName);
		vars.put("{var_hess_function_name}", hessFileName);
		vars.put("{var_lambda}", testProblem.get_lambda());
		vars.put("{var_a}", testProblem.get_a());
		vars.put("{var_b}", testProblem.get_b());
		vars.put("{var_x0}", testProblem.get_x0());
		vars.put("{var_n}", testProblem.getDimension()+"");
		vars.put("{var_tol}", testProblem.getTolerance());
		vars.put("{var_itmax}", testProblem.getMaxIteration());
		vars.put("{var_function_name_v0}", defFileName+v0);
		vars.put("{var_grad_function_name_v0}", gradFileName+v0);
		vars.put("{var_hess_function_name_v0}", hessFileName+v0);
		
		// get the content of the template file
		String content = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(templateFilePath));
			String line = "";
			while ( (line=br.readLine()) != null ) {
				content += line + "\n";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// replace the variables in the templates content
		for (String s : vars.keySet()) {
			while (content.contains(s)) {
				content = content.replace(s, vars.get(s));
			}
		}
		return content;
	}
	
	/**
	 * Write a file.
	 * @param fileName The file name.
	 * @param fileContens The content of the file.
	 */
	private void createFile(String fileName, String fileContens) {
		if (!directoryPath.equals("")) {
			fileName = directoryPath + "/" + fileName;
		}
		System.out.println("Create file: " + fileName);
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			fileWriter.write(fileContens);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create all files for the test problem given.
	 * @param testProblem The test problem.
	 * @param directoryPath The directory to place the files for the test problem.
	 * @param testTemplates
	 */
	public static void create(TestProblem testProblem, String directoryPath, LinkedList<String> testTemplates) {
		MFileCreator mFileCreator = new MFileCreator(testProblem, directoryPath);
		mFileCreator.createFunctionDefinitionFile();
		mFileCreator.createFunctionGradientFile();
		mFileCreator.createFunctionHessianFile();
		mFileCreator.createTestProblemFile(testTemplates.getFirst());
		mFileCreator.createTestProblemFileWithFmincon(testTemplates.get(1));
		mFileCreator.createTestProblemFileWithFminconToo();
	}
	
	public static void createMainTestFile(LinkedList<TestProblem> list, String fileName, String directoryPath) {
		if (list.isEmpty()) {
			return;
		}
		MFileCreator mFileCreator = new MFileCreator(list.getFirst(), directoryPath);
		// create a dummy MFileCreator object, to be able to use the createFile() method
		String content1 = "function " + fileName + "()\n";
		String content2 = "function " + fileName + withFminconToo + "()\n";
		String content3 = "function " + fileName + withFmincon + "()\n";
		for (TestProblem p : list) {
			content1 += "\tdisp('test_" + p.getTestProblemName() + "');\n";
			content2 += "\tdisp('test_" + p.getTestProblemName() + "');\n";
			content3 += "\tdisp('test_" + p.getTestProblemName() + "');\n";
			content1 += "\ttest_" + p.getTestProblemName() + "();\n";
			if (!p.getTestProblemName().startsWith("exp_func")) {
				content2 += "\ttest_" + p.getTestProblemName() + withFminconToo + "();\n";
				content3 += "\ttest_" + p.getTestProblemName() + withFmincon + "();\n";
			} else {
				content2 += "\ttest_" + p.getTestProblemName() + "();\n";
			}
			content1 += "\tdisp(sprintf('\\n'));\n";
			content2 += "\tdisp(sprintf('\\n'));\n";
			content3 += "\tdisp(sprintf('\\n'));\n";
		}
		content1 += "end";
		content2 += "end";
		content3 += "end";
		mFileCreator.createFile(fileName+extension, content1);
		mFileCreator.createFile(fileName+withFminconToo+extension, content2);
		mFileCreator.createFile(fileName+withFmincon+extension, content3);
	}

}
