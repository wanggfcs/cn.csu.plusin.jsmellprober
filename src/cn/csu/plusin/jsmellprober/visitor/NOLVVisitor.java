package cn.csu.plusin.jsmellprober.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import cn.csu.plusin.jsmellprober.model.MethodParam;

public class NOLVVisitor extends ASTVisitor{
	private MethodParam param;
	private List<MethodParam> paramList = new ArrayList<MethodParam>();
	public boolean visit(MethodDeclaration node){
		
		param = new MethodParam();
		param.setMethodName(node.getName().getIdentifier());
		node.accept(new methodVisitor());
//		System.out.println(param.getMethodName()+" "+param.getNumberOfVarible());
		return false;
		
	}
	
	
	class methodVisitor extends ASTVisitor {

		@Override
		public boolean visit(VariableDeclarationStatement node) {
			param.addNumberOfVarible(node.fragments().size());
//			for (Object o :node.fragments() ) {
//				VariableDeclarationFragment var = (VariableDeclarationFragment) o;
//				
//				String name = var.getName().getIdentifier();
//
//				//KEY: "Lcn/csu/plusin/jsmellprober/visitor/NOLVVisitor$PreVisitor;.visit(Lorg/eclipse/jdt/core/dom/VariableDeclarationStatement;)Z#0#o"
//				//KEY: "Lcn/csu/plusin/jsmellprober/visitor/NOLVVisitor$PreVisitor;.visit(Lorg/eclipse/jdt/core/dom/VariableDeclarationStatement;)Z#1#o"
//			}
			

			return false;
		}
		
		public boolean visit(SingleVariableDeclaration node){
			param.addNumberOfVarible(1);
//			node.getName().getIdentifier();
//			IVariableBinding ivb = node.resolveBinding();
//			ivb.getKey();
			
			return false;
			
		}
		
		public boolean visit(VariableDeclarationExpression node) {
			param.addNumberOfVarible(node.fragments().size());
//			for (Object o : node.fragments()) {
//				VariableDeclarationFragment var = (VariableDeclarationFragment) o;
//				String name = var.getName().getIdentifier();
//
//				
//			}

			return false;
		}

	}

	public int getResult(){
		int result;
		result = param.getNumberOfVarible();
		return result;
	}
	
}
