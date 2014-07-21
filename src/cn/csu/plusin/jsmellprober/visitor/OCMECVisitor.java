package cn.csu.plusin.jsmellprober.visitor;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import cn.csu.plusin.jsmellprober.visitor.MPCVisitor.MethodInClass;
import cn.csu.plusin.jsmellprober.visitor.MPCVisitor.MethodInvocationVisitor;

public class OCMECVisitor extends ASTVisitor {
	public Set OCMECSet;

	public OCMECVisitor() {
		super();
		OCMECSet = new LinkedHashSet<>();
	}

	public boolean visit(TypeDeclaration node) {

		// 对类中的方法进行分析
		for (MethodDeclaration md : node.getMethods()) {
			for (Object o : md.parameters()) {
				SingleVariableDeclaration svd = (SingleVariableDeclaration) o;
				Type type = svd.getType();
				ITypeBinding iType = type.resolveBinding();
				OCMECSet.add(iType.getKey());
			}
//			

		}
		return false;
	}

	public int getResult() {
		int result = 0;
		result = OCMECSet.size();
		return result;
		
	}
	

}
