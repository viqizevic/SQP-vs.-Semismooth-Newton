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
		content += "\t[x,fval,it] = semismooth_newton('" + defFileName + "','" + gradFileName + "','" + hessFileName + "',lambda,a,b,x0,itmax,tol)\n";
		content += "\tA = [ -eye(length(a)); eye(length(b)) ];\n";
		content += "\tc = [ -a; b ];\n";
		content += "\t[x,fval,it] = sqp('" + defFileName+v0 + "','" + gradFileName+v0 + "','" + hessFileName+v0 + "',A,c,x0,itmax,tol)\n";
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
		content += "\t[x,fval,it] = semismooth_newton('" + defFileName + "','" + gradFileName + "','" + hessFileName + "',lambda,a,b,x0,itmax,tol)\n";
		content += "\tA = [ -eye(length(a)); eye(length(b)) ];\n";
		content += "\tc = [ -a; b ];\n";
		content += "\t[x,fval,it] = sqp('" + defFileName+v0 + "','" + gradFileName+v0 + "','" + hessFileName+v0 + "',A,c,x0,itmax,tol)\n";
		content += "\toptions=optimset('Algorithm','active-set');\n";
		content += "\t[x,fval] = fmincon('" + defFileName+v0 + "',x0,[],[],[],[],a,b,[],options)\n";
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
