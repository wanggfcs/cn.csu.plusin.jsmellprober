package cn.csu.plusin.jsmellprober.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ICPVisitor extends ASTVisitor {

	private List<methodInfo> methodInfoList = new ArrayList<methodInfo>();
	public boolean visit(TypeDeclaration node){
		MethodDeclaration[] mds = node.getMethods();
		for(int i = 0;i<mds.length;i++){
			methodInfo mi = new methodInfo();
			mi.methodName = mds[i].getName().getIdentifier();
			mi.parameter = mds[i].parameters().size();
			methodInfoList.add(mi);
		}
		return false;
	}
	class methodInfo{
		String methodName;
		int parameter;
	}
	public int getRusult(){
		int result = 0;
		for(methodInfo mi:methodInfoList){
			result += mi.parameter; 
		}
		return result;
	}
}
