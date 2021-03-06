package cn.csu.plusin.jsmellprober.visitor;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.WhileStatement;

import cn.csu.plusin.jsmellprober.model.MethodParam;
import cn.csu.plusin.jsmellprober.util.FileUtil;
import cn.csu.plusin.jsmellprober.util.JdtAstUtil;

public class McCabe extends ASTVisitor {

	private String unitPath;
	private CompilationUnit unit;
	private int cyclomatic = 1;
	private String source;
	private File file;
	MethodParam param;

	public McCabe(String dirPath) throws Exception {
		super();

		FileUtil util = new FileUtil();
		ArrayList<String> pathList = util.getAllJavaFilePath(dirPath);

		for (String path : pathList) {
			System.out.println(path);
			unitPath = path;
			unit = JdtAstUtil.getCompilationUnit(unitPath);
			source = JdtAstUtil.getSource(unitPath);
			// source = unit.getSource();
			unit.accept(this);

//			System.out.println(cyclomatic);

			source = "";
		}
	}

	public void getResult() {

		System.out.println(cyclomatic);

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

	public int getCyclomatic() {
		return cyclomatic;
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
//			param.setMethodCyclomatic(cyclomatic);
//			System.out.println(param.getMethodName()+"\t"+param.getMethodCyclomatic());
			cyclomatic = 1;
		}
		return true;

	}
	
	public void endVisit(MethodDeclaration node) {
		
			param.setMethodCyclomatic(cyclomatic);
			System.out.println(param.getMethodName()+"\t"+param.getMethodCyclomatic());
//			cyclomatic = 1;
		


	}

	public boolean visit(MethodDeclaration node) {


		param = new MethodParam();
		param.setPath(unitPath);
		param.setMethodName(node.getName().toString());
		param.setStartLineNum(unit.getLineNumber(node.getStartPosition()));
		param.setEndLineNum(unit.getLineNumber(node.getStartPosition()
				+ node.getLength()));

		// return super.visit(node);
		return true;
	}

}
