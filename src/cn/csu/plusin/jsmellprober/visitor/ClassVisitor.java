package cn.csu.plusin.jsmellprober.visitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import cn.csu.plusin.jsmellprober.analyzer.SumJavaCode;
import cn.csu.plusin.jsmellprober.model.MethodParam;
import cn.csu.plusin.jsmellprober.util.FileUtil;
import cn.csu.plusin.jsmellprober.util.JdtAstUtil;
import cn.csu.plusin.jsmellprober.util.XlsOutput;


public class ClassVisitor extends ASTVisitor{
	private int LineOfImports= 0;


	private boolean isInterface = false, visited = false;
	private String unitPath, minPath, projectName;
	private CompilationUnit unit;
	private String className;
	private int fieldFragmentsCount;
	private int normerClassCount, interfaceCount, classCount;
	private int methodCount;
	private int maxTotalLine, maxNoCommentCodeLine;
	private int minCodeLine = 1000, minNoCommentCodeLine = 1000;
	private int totalNoCommentCodeLine, totalCommentLines, totalWhiteLines;
	private int noCommentCodeLine, commentLines, whiteLines, totalLines;
	private MethodParam param;
	private SumJavaCode sjc;

	private String source, outPut;
	private File file;



	public ClassVisitor(String dirPath) throws Exception {
		super();
		FileUtil util = new FileUtil();
		ArrayList<String> pathList = util.getAllJavaFilePath(dirPath);
		// System.out.println(dirPath);
		sjc = new SumJavaCode();
		for (String path : pathList) {

			source = "";
			unitPath = path;
	
			unit = JdtAstUtil.getCompilationUnit(unitPath);
			source = JdtAstUtil.getSource(unitPath);
			unit.accept(this);

			/*
			 * 判断是否为接口
			 */
//			if (isInterface == false) {
				// System.out.println(path);
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

				/*
				 * 计算最大最小类代码行数
				 */
				// if (totalLines >= maxTotalLine) {
				// maxTotalLine = totalLines;
				// }
				// if (noCommentCodeLine >= maxNoCommentCodeLine) {
				// maxNoCommentCodeLine = noCommentCodeLine;
				// 显示最大最小代码行数文件位置
				// minPath = unitPath;
			

			// if (totalLines <= minCodeLine) {
			// minCodeLine = totalLines;
			// }
			// if (noCommentCodeLine <= minNoCommentCodeLine) {
			// minNoCommentCodeLine = noCommentCodeLine;
			//
			// }
		
		
//	}
		isInterface = false;
		visited = false;

		fieldFragmentsCount = 0;
		outPut = "";
//		maxTotalLine = 0;
//		maxNoCommentCodeLine = 0;
//		minCodeLine = 1000;
//		minNoCommentCodeLine = 1000;



		}
	
	/*
	 * 显示类代码行数
	 */
		int all=totalNoCommentCodeLine+totalCommentLines+totalWhiteLines;
	 System.out.println(totalNoCommentCodeLine+"\t"+totalCommentLines+"\t"+totalWhiteLines+"\t"+all);
	/*
	 * 显示全部类代码行数
	 */
	// System.out.println(maxTotalLine + "\t" + maxNoCommentCodeLine + "\t"
	// + minCodeLine + "\t" + minNoCommentCodeLine+"\t");

	/*
	 * 显示类个数
	 */
	// System.out.println(classCount+"\t"+normerClassCount+"\t"+interfaceCount);
	//
	}

public boolean visit(TypeDeclaration node) {

	if (node.isInterface()) {
		isInterface = true;
		interfaceCount++;
	} else if (!visited) {
		className = node.getName().toString();
		FieldDeclaration[] fd = node.getFields();
		for (FieldDeclaration fdTemp : fd) {
			// 获得Modifiers
			// int md =fdTemp.getModifiers();
			// 获得Type类型
			Type tp = fdTemp.getType();
			// 获得所有的fragments
			List fg = fdTemp.fragments();
			// 显示field
			// for (Object fgTemp : fg){
			// VariableDeclarationFragment vdf =
			// (VariableDeclarationFragment) fgTemp;
			// System.out.println(md+"\t"+tp.toString()
			// +"\t"+vdf.getName().toString());
			// }
			fieldFragmentsCount += fg.size();
		}

		normerClassCount++;
		visited = true;
	}
	classCount++;

	return true;

}
	public boolean visit(EnumDeclaration  node) {
		
		System.out.println(node.getName().toString());
		return true;
	
	}

	
}
