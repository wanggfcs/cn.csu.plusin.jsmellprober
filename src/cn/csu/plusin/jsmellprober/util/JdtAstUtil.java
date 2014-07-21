package cn.csu.plusin.jsmellprober.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class JdtAstUtil {

	private static ASTParser astParser = ASTParser.newParser(AST.JLS3);// �ǳ���

	/**
	 * ���javaԴ�ļ��ĽṹCompilationUnit
	 * 
	 * @param javaFilePath
	 *            java�ļ��ľ���·��
	 * @return CompilationUnit
	 * @throws Exception
	 */
	public static CompilationUnit getCompilationUnit(String javaFilePath)
			throws Exception {

		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(javaFilePath));

		byte[] input = new byte[bufferedInputStream.available()];
		bufferedInputStream.read(input);
		bufferedInputStream.close();
		astParser.setSource(new String(input).toCharArray());

		CompilationUnit result = (CompilationUnit) (astParser.createAST(null));// ����

		return result;
	}

	public static String getSource(String javaFilePath) throws Exception {

		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(javaFilePath));

		byte[] input = new byte[bufferedInputStream.available()];
		bufferedInputStream.read(input);
		bufferedInputStream.close();
		return (new String(input)).trim();

		// FileReader fr = new FileReader(javaFilePath);
		// BufferedReader br = new BufferedReader(fr);
		// String s = "";
		// while (br.readLine() != null) {
		// s += br.readLine();
		// s +="\n";
		// }
		// br.close();
		// return s;

	}

	public static File getFile(String javaFilePat) {

		return new File(javaFilePat);

	}

	public static String getFileName(String javaFilePat) {

		return new File(javaFilePat).getName();

	}
	
public static CompilationUnit getCompilationUnit(ICompilationUnit icu){
		
		astParser.setSource(icu);
		astParser.setBindingsRecovery(true);
		astParser.setResolveBindings(true);
		
		CompilationUnit result = (CompilationUnit) astParser.createAST(null);

		IProblem[] problems = result.getProblems();

		for (IProblem problem : problems) {
			//���뱨��
//			System.out.println(problem.toString());
		}

		if (result.getAST().hasBindingsRecovery()) {
			System.out.println("Binding activated.");
		}
		
		
		return result;
		
		
		
	}
}
