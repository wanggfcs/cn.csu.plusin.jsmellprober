package cn.csu.plusin.jsmellprober.visitor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import cn.csu.plusin.jsmellprober.analyzer.LineOfCode;
import cn.csu.plusin.jsmellprober.analyzer.SumJavaCode;
import cn.csu.plusin.jsmellprober.util.FileUtil;
import cn.csu.plusin.jsmellprober.util.JdtAstUtil;

public class MethodVisitor extends ASTVisitor {
	private int totalNoCommentCodeLine, totalCommentLines, totalWhiteLines,
			noCommentCodeLine, commentLines, whiteLines, totalLines;;
	private String unitPath;
	private String packageName = "";
	private String className = "";
	private CompilationUnit unit;
	private FileUtil util;
	private boolean isInterface = false, visited = false, innerClass = false;
	private long totalParamCount = 0;
	private int fileCount = 0;
	private int fragments;
	private int methodNumber = 0;
	private int classCount = 0, totalClassCount = 0;
	private int interfaceCout = 0;
	private int LineOfMethodSum = 0;
	private int max = 0;
	private String maxMethod = "";
	private String source;
	private SumJavaCode sjc;

	public MethodVisitor(String dirPath) throws Exception {
		super();
		System.out.println(dirPath.substring(8));
		util = new FileUtil();
		ArrayList<String> pathList = util.getAllJavaFilePath(dirPath);
		sjc = new SumJavaCode();
		for (String path : pathList) {
			source = "";
			unitPath = path;
			unit = JdtAstUtil.getCompilationUnit(unitPath);
			source = JdtAstUtil.getSource(unitPath);

			unit.accept(this);
			int[] temp = sjc.LineOfCode(JdtAstUtil.getFile(unitPath));
			noCommentCodeLine = temp[0];
			commentLines = temp[1];
			whiteLines = temp[2];
			totalLines = noCommentCodeLine + commentLines;

			/*
			 * 所有普通类的代码行数统计
			 */
			totalNoCommentCodeLine += noCommentCodeLine;
			totalCommentLines += commentLines;
			totalWhiteLines += whiteLines;
			fileCount++;
			visited = false;
			isInterface = false;

		}
		getResult();
	}

	public void getResult() {

		// System.out.println("TotalClassCount: "+classCount);
		// System.out.println("Method Number: " + methodNumber);
		//
		// System.out.println("Average Params Length: " + avg);
		// System.out.println("Maximal Params Length: " + max);
		// System.out.println("Maximal Params Length Method: " + maxMethod);
		// System.out.println("LineOfMethodSum: "+getLineOfMethodSum());
		System.out.println(totalNoCommentCodeLine + "\t" + fileCount + "\t"
				+ totalClassCount + "\t" + fragments + "\t" + methodNumber);
		// + "\t" + avg + "\t" + max + "\t" + packageName);
		// System.out.println("LOC: "+ LOCsum);

	}

	@Override
	public boolean visit(MethodDeclaration node) {
		int paramLength = node.parameters().size();

		totalParamCount += paramLength;
//		if (!innerClass) {
			methodNumber++;
//		}
		if (paramLength > max) {
			max = paramLength;
			// maxMethod = unitPath ;

			// + "\nMethodName: " + node.getName();
			// +"\nLine Number: " +
			// unit.getLineNumber(node.getStartPosition()+node.getLength());
			if (unit.getPackage() != null) {
				PackageDeclaration pkg = unit.getPackage();
				Name pn = pkg.getName();
				packageName = pn.toString() + "\t" + className;

			}

		}

		return true;
	}

	public boolean visit(TypeDeclaration node) {
		if (node.isInterface()) {
			interfaceCout++;
			isInterface = true;

		}
		if (!visited) {
			visited = true;
			className = node.getName().toString();
			classCount++;
			FieldDeclaration[] fd = node.getFields();
			for (FieldDeclaration fdTemp : fd) {
				int md = fdTemp.getModifiers();
				Type tp = fdTemp.getType();
				List fg = fdTemp.fragments();
				fragments += fg.size();
			}
		}else {
			innerClass =true;
		}

		totalClassCount++;

		return true;

	}

	public void endVisit(TypeDeclaration node) {
		if(innerClass)
			innerClass=false;
	}

	

}
