package cn.csu.plusin.jsmellprober.visitor;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.csu.plusin.jsmellprober.analyzer.LineOfCode;
import cn.csu.plusin.jsmellprober.analyzer.SumJavaCode;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;

import cn.csu.plusin.jsmellprober.model.MethodParam;
import cn.csu.plusin.jsmellprober.util.FileUtil;
import cn.csu.plusin.jsmellprober.util.JdtAstUtil;
import cn.csu.plusin.jsmellprober.util.XlsOutput;

public class CompilationUnitVisitor extends ASTVisitor {
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
	private int cyclomatic = 1;
	private String source, outPut;
	private File file;
	private List<MethodParam> methodList;
	private List<List<String>> output;

	public CompilationUnitVisitor(String dirPath) throws Exception {
		super();
		projectName = projectName(dirPath);
		output = new ArrayList<List<String>>();
		// initOutput();
		FileUtil util = new FileUtil();
		ArrayList<String> pathList = util.getAllJavaFilePath(dirPath);
		// System.out.println(dirPath);
		sjc = new SumJavaCode();
		for (String path : pathList) {

			source = "";
			unitPath = path;
			methodList = new ArrayList<MethodParam>();
			unit = JdtAstUtil.getCompilationUnit(unitPath);
			source = JdtAstUtil.getSource(unitPath);
			unit.accept(this);

			/*
			 * 判断是否为接口
			 */
			if (isInterface == false) {
				// System.out.println(path);
				int[] temp = sjc.LineOfCode(JdtAstUtil.getFile(unitPath));
				noCommentCodeLine = temp[0];
				commentLines = temp[1];
				whiteLines = temp[2];
				totalLines = noCommentCodeLine + commentLines;
				initOutput();
				/*
				 * 所有普通类的代码行数统计
				 */
				// totalNoCommentCodeLine += noCommentCodeLine;
				// totalCommentLines += commentLines;
				// totalWhiteLines += totalLines;

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
			}

			// if (totalLines <= minCodeLine) {
			// minCodeLine = totalLines;
			// }
			// if (noCommentCodeLine <= minNoCommentCodeLine) {
			// minNoCommentCodeLine = noCommentCodeLine;
			//
			// }

			isInterface = false;
			visited = false;
			methodCount = 0;
			fieldFragmentsCount = 0;
			outPut = "";
			maxTotalLine = 0;
			maxNoCommentCodeLine = 0;
			minCodeLine = 1000;
			minNoCommentCodeLine = 1000;

			// System.out.println("空白行数: " + whiteLines);
			// // LOCsum = unit.getLineNumber(unit.getLength() -1) ;

		}
		XlsOutput.generateExcel2003(projectName, getExcleOutput());
		/*
		 * 显示类代码行数
		 */
		// System.out.println(normerClassCount+"\t"+totalWhiteLines+"\t"+totalNoCommentCodeLine+"\t");
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

	private String projectName(String projectPath) {
		String projectName = "";
		for (int i = projectPath.length() - 1; i >= 0; i--) {
			if ((int) projectPath.charAt(i) == 92) {
				projectName = projectPath.substring(i + 1);
				break;
			}
		}
		return projectName;
	}

	public List<List<String>> getOutput() {
		return output;

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

	public boolean visit(MethodDeclaration node) {

		methodCount++;
		param = new MethodParam();
		param.setPath(unitPath);
		param.setMethodName(node.getName().toString());
		param.setStartLineNum(unit.getLineNumber(node.getStartPosition()));
		param.setEndLineNum(unit.getLineNumber(node.getStartPosition()
				+ node.getLength()));
		int[] result = sjc.LineOfCode(JdtAstUtil.getFile(unitPath),
				param.getStartLineNum(), param.getEndLineNum());
		param.setNoCommentCodeLine(result[0]);
		param.setLineOfCodeComment(result[1]);
		methodList.add(param);

		// return super.visit(node);
		return true;
	}

	public boolean visit(CatchClause node) {
		cyclomatic++;
		return true;
	}

	public boolean visit(ConditionalExpression node) {
		cyclomatic++;
		inspectExpression(node.getExpression());
		return true;
	}

	public boolean visit(DoStatement node) {
		cyclomatic++;
		inspectExpression(node.getExpression());
		return true;
	}

	public boolean visit(ForStatement node) {
		cyclomatic++;
		inspectExpression(node.getExpression());
		return true;
	}

	public boolean visit(IfStatement node) {
		cyclomatic++;
		inspectExpression(node.getExpression());
		return true;
	}

	public boolean visit(SwitchCase node) {
		if (!node.isDefault())
			cyclomatic++;
		return true;
	}

	public boolean visit(WhileStatement node) {
		cyclomatic++;
		inspectExpression(node.getExpression());
		return true;
	}

	public boolean visit(ExpressionStatement exs) {
		inspectExpression(exs.getExpression());
		return false;
	}

	/**
	 * Count occurrences of && and || (conditional and or) Fix for BUG 740253
	 * 
	 * @param ex
	 */
	private void inspectExpression(Expression ex) {
		if (ex != null) {
			int start = ex.getStartPosition();
			int end = ex.getStartPosition() + ex.getLength();
			String expression = source.substring(start, end);
			// System.out.print(expression);
			char[] chars = expression.toCharArray();
			for (int i = 0; i < chars.length - 1; i++) {
				char next = chars[i];
				if ((next == '&' || next == '|') && (next == chars[i + 1])) {

					cyclomatic++;
				}
			}
		}
	}

	public boolean preVisit2(ASTNode node) {
		if ((node instanceof MethodDeclaration) && (param != null)) {
			// param.setMethodCyclomatic(cyclomatic);
			// System.out.println(param.getMethodName()+"\t"+param.getMethodCyclomatic());
			cyclomatic = 1;
		}
		// if (node instanceof EnumDeclaration ){
		//
		// if (!visited) {
		// className = ((EnumDeclaration) node).getName().toString();
		// normerClassCount++;
		// visited = true;
		// }
		// classCount++;
		//
		// }
		return true;

	}

	public void endVisit(MethodDeclaration node) {

		param.setMethodCyclomatic(cyclomatic);
		// System.out.println(param.getMethodName()+"\t"+param.getMethodCyclomatic());
		// cyclomatic = 1;

	}

	public List<List<String>> getExcleOutput() {

		List<String> tempStr = new ArrayList<String>();
		tempStr.add("类名");
		tempStr.add("方法个数");
		tempStr.add("属性个数");
		tempStr.add("总代码行数（含注释）");
		tempStr.add("总代码行数（无注释）");
		tempStr.add("方法总代码行数（含注释）");
		tempStr.add("方法总代码行数（无注释）");
		tempStr.add("方法平均代码行数（含）");
		tempStr.add("方法平均代码行数（无）");
		tempStr.add("方法最大代码行数（含）");
		tempStr.add("方法最大代码行数（无）");
		tempStr.add("方法最小代码行数（含）");
		tempStr.add("方法最小代码行数（无）");
		tempStr.add("方法总圈复杂度");
		tempStr.add("方法平均圈复杂度");
		tempStr.add("方法最大圈复杂度");
		tempStr.add("方法最小圈复杂度");
		List<List<String>> temp =  new ArrayList<List<String>>();
		temp.addAll(output);
		temp.add(0, tempStr);
		return temp;
	}

	private void initOutput() {
		boolean isZero = false;
		List<String> tempStr = new ArrayList<String>();
		tempStr.add(className);
		tempStr.add(((Integer) methodCount).toString());
		if (methodCount == 0)
			isZero = true;
		tempStr.add(((Integer) fieldFragmentsCount).toString());
		tempStr.add(((Integer) totalLines).toString());
		tempStr.add(((Integer) noCommentCodeLine).toString());
		int noCommentCodeLineSum = 0, totalLinesSum = 0, totalCyclomatic = 0, maxCyclomatic = 0, minCyclomatic = 100;

		for (MethodParam param : methodList) {
			noCommentCodeLine = param.getNoCommentCodeLine();
			totalLines = param.getLineOfCodeComment() + noCommentCodeLine;
			noCommentCodeLineSum += noCommentCodeLine;
			totalLinesSum += totalLines;
			if (totalLines >= maxTotalLine) {
				maxTotalLine = totalLines;
			}
			if (noCommentCodeLine >= maxNoCommentCodeLine) {
				maxNoCommentCodeLine = noCommentCodeLine;
			}

			if (totalLines <= minCodeLine) {
				minCodeLine = totalLines;
			}
			if (noCommentCodeLine <= minNoCommentCodeLine) {
				minNoCommentCodeLine = noCommentCodeLine;

			}
			/*
			 * 获得全复杂度
			 */
			if (param.getMethodCyclomatic() >= maxCyclomatic)
				maxCyclomatic = param.getMethodCyclomatic();
			if (param.getMethodCyclomatic() <= minCyclomatic)
				minCyclomatic = param.getMethodCyclomatic();
			totalCyclomatic += param.getMethodCyclomatic();

		}
		tempStr.add(((Integer) totalLinesSum).toString());
		tempStr.add(((Integer) noCommentCodeLineSum).toString());

		tempStr.add("");
		tempStr.add("");
		if (isZero) {
			tempStr.add("NULL");
			tempStr.add("NULL");
			tempStr.add("NULL");
			tempStr.add("NULL");
			tempStr.add("NULL");
			tempStr.add("NULL");
			tempStr.add("NULL");
			tempStr.add("NULL");
			tempStr.add(unitPath);
		} else {
			tempStr.add(((Integer) maxTotalLine).toString());
			tempStr.add(((Integer) maxNoCommentCodeLine).toString());
			tempStr.add(((Integer) minCodeLine).toString());
			tempStr.add(((Integer) minNoCommentCodeLine).toString());
			tempStr.add(((Integer) totalCyclomatic).toString());
			tempStr.add("");
			tempStr.add(((Integer) maxCyclomatic).toString());
			tempStr.add(((Integer) minCyclomatic).toString());
			tempStr.add(unitPath);
		}
		output.add(tempStr);

	}

}
