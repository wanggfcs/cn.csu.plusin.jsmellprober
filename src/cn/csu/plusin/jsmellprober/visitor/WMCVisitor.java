package cn.csu.plusin.jsmellprober.visitor;

import java.util.ArrayList;
import java.util.List;

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

public class WMCVisitor extends ASTVisitor {

	private int cyclomatic =1;
	private MethodParam param;
	private List<MethodParam> paramList = new ArrayList<MethodParam>();
	private CompilationUnit unit;
	private String source;

	public void setSource(String source) {
		this.source = source;
	}

	public void setUnit(CompilationUnit unit) {
		this.unit = unit;
	}

	public boolean visit(CatchClause node) {
		cyclomatic ++;
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
			paramList.add(param);
//			System.out.println(param.getMethodName()+"\t"+param.getMethodCyclomatic());
//			cyclomatic = 1;
		


	}

	public boolean visit(MethodDeclaration node) {


		param = new MethodParam();
	
		param.setMethodName(node.getName().toString());
		param.setStartLineNum(unit.getLineNumber(node.getStartPosition()));
		param.setEndLineNum(unit.getLineNumber(node.getStartPosition()
				+ node.getLength()));

		// return super.visit(node);
		return true;
	}
	
	public int getResult(){
		int result =0;
		for(MethodParam p:paramList){
			result += p.getMethodCyclomatic();
		}
		return result;
	}

	
}
