package cn.csu.plusin.jsmellprober.visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class NOLVVisitor extends ASTVisitor{
	
	class PreVisitor extends ASTVisitor {

		@Override
		public boolean visit(VariableDeclarationStatement node) {
			for (Object o : node.fragments()) {
				VariableDeclarationFragment var = (VariableDeclarationFragment) o;
				
				String name = var.getName().getIdentifier();

				//KEY: "Lcn/csu/plusin/jsmellprober/visitor/NOLVVisitor$PreVisitor;.visit(Lorg/eclipse/jdt/core/dom/VariableDeclarationStatement;)Z#0#o"
				//KEY: "Lcn/csu/plusin/jsmellprober/visitor/NOLVVisitor$PreVisitor;.visit(Lorg/eclipse/jdt/core/dom/VariableDeclarationStatement;)Z#1#o"
			}
			for (Object o : node.fragments()) {
				
			}

			return false;
		}
		
		public boolean visit(SingleVariableDeclaration node){
			return false;
			
		}
		
		public boolean visit(VariableDeclarationExpression node) {
			for (Object o : node.fragments()) {
				VariableDeclarationFragment var = (VariableDeclarationFragment) o;
				String name = var.getName().getIdentifier();

				
			}

			return false;
		}

	}

}
