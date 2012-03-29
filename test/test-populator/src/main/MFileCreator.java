package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class MFileCreator {
	
	private static final String extension = ".m";

	private static final String withFmincon = "_with_fmincon";
	
	private static final String withFminconToo = "_with_fmincon_too";

	private final String v0 = "_v0";
	
	private TestProblem testProblem;
	
	private TestFunction f;
	
	private String defFileName;
	
	private String gradFileName;
	
	private String hessFileName;
	
	private String testFileName;
	
	private String directoryPath;
	
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
		String content = "function y = " + fileName + "(" + f.getVar() + ")\n";
		if (f.getConstants().size() != 0) {
			for (String s : f.getConstants().keySet()) {
				content += "\t" + s + " = " + f.getConstantValue(s) + ";\n";
			}
		}
		content += "\t" + f.getDefinition() + "\n";
		content += "end";
		createFile(fileName+extension, content);
		content = "function y = " + fileName + v0 + "(" + f.getVar() + ")\n";
		content += "\tlambda = " + testProblem.get_lambda() + ";\n";
		content += "\ty = " + fileName + "(" + f.getVar() + ") + " + f.extraDefinition() + ";\n";
		content += "end";
		createFile(fileName+v0+extension, content);
	}
	
	private void createFunctionGradientFile() {
		String fileName = gradFileName;
		String content = "function g = " + fileName + "(" + f.getVar() + ")\n";
		if (f.getConstants().size() != 0) {
			for (String s : f.getConstants().keySet()) {
				content += "\t" + s + " = " + f.getConstantValue(s) + ";\n";
			}
		}
		content += "\t" + f.getGradient() + "\n";
		content += "end";
		createFile(fileName+extension, content);
		content = "function g = " + fileName + v0 + "(" + f.getVar() + ")\n";
		content += "\tlambda = " + testProblem.get_lambda() + ";\n";
		content += "\tg = " + fileName + "(" + f.getVar() + ") + " + f.extraGradient() + ";\n";
		content += "end";
		createFile(fileName+v0+extension, content);
	}

	private void createFunctionHessianFile() {
		String fileName = hessFileName;
		String content = "function H = " + fileName + "(" + f.getVar() + ")\n";
		if (f.getConstants().size() != 0) {
			for (String s : f.getConstants().keySet()) {
				content += "\t" + s + " = " + f.getConstantValue(s) + ";\n";
			}
		}
		content += "\t" + f.getHessianMatrix() + "\n";
		content += "end";
		createFile(fileName+extension, content);
		content = "function H = " + fileName + v0 + "(" + f.getVar() + ")\n";
		content += "\tlambda = " + testProblem.get_lambda() + ";\n";
		content += "\tH = " + fileName + "(" + f.getVar() + ") + " + f.extraHessian() + ";\n";
		content += "end";
		createFile(fileName+v0+extension, content);
	}
	
	private void createTestProblemFile() {
		String fileName = testFileName;
		String content = "function " + fileName + "()\n";
		content += "\tlambda = " + testProblem.get_lambda() + ";\n";
		content += "\ta = " + testProblem.get_a() + ";\n";
		content += "\tb = " + testProblem.get_b() + ";\n";
		content += "\tx0 = " + testProblem.get_x0() + ";\n";
		content += "\tm0 = zeros(" + testProblem.getDimension() + ",1);\n";
		content += "\ttol = " + testProblem.getTolerance() + ";\n";
		content += "\titmax = " + testProblem.getMaxIteration() + ";\n";
		content += "\ttic;\n";
		content += "\t[x_ssn,fval_ssn,it_ssn] = active_set_strategy('" + defFileName + "','" + gradFileName + "','" + hessFileName + "',lambda,a,b,x0,m0,itmax,tol);\n";
		content += "\t%[x_ssn,fval_ssn,it_ssn] = semismooth_newton('" + defFileName + "','" + gradFileName + "','" + hessFileName + "',lambda,a,b,x0,itmax,tol);\n";
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
		content += "\ta = sprintf('%.3f ',a);\n";
		content += "\tb = sprintf('%.3f ',b);\n";
		content += "\tx0 = sprintf('%.3f ',x0);\n";
		content += "\tstr0 = ['a = [ ', a, '], b = [ ', b, '], x0 = [ ', x0, ']'];\n";
		content += "\tdisp(str0);\n";
		content += "\tdisp(str1);\n";
		content += "\tdisp(str2);\n";
		content += "end";
		createFile(fileName+extension, content);
	}

	private void createTestProblemFileWithFmincon(String templateFilePath) {
		HashMap<String, String> vars = new HashMap<String, String>();
		vars.put("{var_function_name}", defFileName);
		vars.put("{var_lambda}", testProblem.get_lambda());
		vars.put("{var_a}", testProblem.get_a());
		vars.put("{var_b}", testProblem.get_b());
		vars.put("{var_x0}", testProblem.get_x0());
		vars.put("{var_tol}", testProblem.getTolerance());
		vars.put("{var_itmax}", testProblem.getMaxIteration());
		vars.put("{var_function_name_v0}", defFileName+v0);
		
		String content = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(templateFilePath));
			String line = "";
			while ( (line=br.readLine()) != null ) {
				for (String s : vars.keySet()) {
					if (line.contains(s)) {
						line = line.replace(s, vars.get(s));
					}
				}
				content += line + "\n";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileName = testFileName + withFmincon;
		createFile(fileName+extension, content);
	}

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
	
	public static void create(TestProblem testProblem, String directoryPath, String templateFilePath) {
		MFileCreator mFileCreator = new MFileCreator(testProblem, directoryPath);
		mFileCreator.createFunctionDefinitionFile();
		mFileCreator.createFunctionGradientFile();
		mFileCreator.createFunctionHessianFile();
		mFileCreator.createTestProblemFile();
		mFileCreator.createTestProblemFileWithFminconToo();
		mFileCreator.createTestProblemFileWithFmincon(templateFilePath);
	}
	
	public static void createMainTestFile(LinkedList<TestProblem> list, String fileName, String directoryPath) {
		MFileCreator mFileCreator = new MFileCreator(list.getFirst(), directoryPath);
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
