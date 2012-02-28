package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MFileCreator {
	
	private final String extension = ".m";
	
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
		content += "\ttol = " + testProblem.getTolerance() + ";\n";
		content += "\titmax = " + testProblem.getMaxIteration() + ";\n";
		content += "\ttic;\n";
		content += "\t[x_ssn,fval_ssn,it_ssn] = semismooth_newton('" + defFileName + "','" + gradFileName + "','" + hessFileName + "',lambda,a,b,x0,itmax,tol);\n";
		content += "\tt_ssn = toc;\n";
		content += "\tx1 = sprintf('%.3f ',x_ssn);\n";
		content += "\tf1 = sprintf('f(x_ssn) = %.3f ',fval_ssn);\n";
		content += "\tt1 = sprintf('solved in %.2f ms.',t_ssn*1000);\n";
		content += "\tstr1 = ['x_ssn = [ ', x1, '], ', f1, ', it = ', num2str(it_ssn), ', ', t1];\n";
		content += "\tdisp(str1);\n";
		content += "\tA = [ -eye(length(a)); eye(length(b)) ];\n";
		content += "\tc = [ -a; b ];\n";
		content += "\ttic;\n";
		content += "\t[x_sqp,fval_sqp,it_sqp] = sqp('" + defFileName+v0 + "','" + gradFileName+v0 + "','" + hessFileName+v0 + "',A,c,x0,itmax,tol);\n";
		content += "\tt_sqp = toc;\n";
		content += "\tx2 = sprintf('%.3f ',x_sqp);\n";
		content += "\tf2 = sprintf('f(x_sqp) = %.3f ',fval_sqp);\n";
		content += "\tt2 = sprintf('solved in %.2f ms.',t_sqp*1000);\n";
		content += "\tstr2 = ['x_sqp = [ ', x2, '], ', f2, ', it = ', num2str(it_sqp), ', ', t2];\n";
		content += "\tdisp(str2);\n";
		content += "end";
		createFile(fileName+extension, content);
	}

	private void createTestProblemFileWithFmincon() {
		String fileName = testFileName + "_with_fmincon_too";
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
		content += "\tf1 = sprintf('f(x_ssn) = %.3f ',fval_ssn);\n";
		content += "\tt1 = sprintf('solved in %.2f ms.',t_ssn*1000);\n";
		content += "\tstr1 = ['x_ssn = [ ', x1, '], ', f1, ', it = ', num2str(it_ssn), ', ', t1];\n";
		content += "\tdisp(str1);\n";
		content += "\tA = [ -eye(length(a)); eye(length(b)) ];\n";
		content += "\tc = [ -a; b ];\n";
		content += "\ttic;\n";
		content += "\t[x_sqp,fval_sqp,it_sqp] = sqp('" + defFileName+v0 + "','" + gradFileName+v0 + "','" + hessFileName+v0 + "',A,c,x0,itmax,tol);\n";
		content += "\tt_sqp = toc;\n";
		content += "\tx2 = sprintf('%.3f ',x_sqp);\n";
		content += "\tf2 = sprintf('f(x_sqp) = %.3f ',fval_sqp);\n";
		content += "\tt2 = sprintf('solved in %.2f ms.',t_sqp*1000);\n";
		content += "\tstr2 = ['x_sqp = [ ', x2, '], ', f2, ', it = ', num2str(it_sqp), ', ', t2];\n";
		content += "\tdisp(str2);\n";
		content += "\toptions = optimset('Algorithm','active-set','Display','off');\n";
		content += "\ttic;\n";
		content += "\t[x_fmc,fval_fmc,exitflag,output] = fmincon('" + defFileName+v0 + "',x0,[],[],[],[],a,b,[],options);\n";
		content += "\tt_fmc = toc;\n";
		content += "\tx3 = sprintf('%.3f ',x_fmc);\n";
		content += "\tf3 = sprintf('f(x_fmc) = %.3f ',fval_fmc);\n";
		content += "\tt3 = sprintf('solved in %.2f ms.',t_fmc*1000);\n";
		content += "\tstr3 = ['x_fmc = [ ', x3, '], ', f3, ', ', t3];\n";
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
	
	public static void create(TestProblem testProblem, String directoryPath) {
		MFileCreator mFileCreator = new MFileCreator(testProblem, directoryPath);
		mFileCreator.createFunctionDefinitionFile();
		mFileCreator.createFunctionGradientFile();
		mFileCreator.createFunctionHessianFile();
		mFileCreator.createTestProblemFile();
		mFileCreator.createTestProblemFileWithFmincon();
	}

}
