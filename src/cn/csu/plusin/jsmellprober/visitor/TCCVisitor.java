package cn.csu.plusin.jsmellprober.visitor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import cn.csu.plusin.jsmellprober.model.MethodParam;

public class TCCVisitor extends ASTVisitor {

	private MethodParam param;
	private Set<String> attributeSet = new HashSet<String>();
	private Set<MethodParam> methodList = new HashSet<MethodParam>();

	public boolean visit(TypeDeclaration node) {
		FieldDeclaration[] fd = node.getFields();
		for (FieldDeclaration fdTemp : fd) {
			int md = fdTemp.getModifiers();
			Type tp = fdTemp.getType();
			List fg = fdTemp.fragments();
			for (Object fgTemp : fg) {
				VariableDeclarationFragment vdf = (VariableDeclarationFragment) fgTemp;
				IVariableBinding ivb = vdf.resolveBinding();
				attributeSet.add(ivb.getKey());

			}
		}

		return false;

	}
	//获得方法遍历
	public boolean visit(MethodDeclaration node) {
		param = new MethodParam();
		node.accept(new MethodVisitor());
		param.setMethodName(node.getName().getIdentifier());
		methodList.add(param);
		// KEY:
		// "Lcn/csu/plusin/jsmellprober/visitor/TCCVisitor;.param)Lcn/csu/plusin/jsmellprober/model/MethodParam;"
		// KEY:
		// "Lcn/csu/plusin/jsmellprober/visitor/TCCVisitor;.param)Lcn/csu/plusin/jsmellprober/model/MethodParam;"
		return false;

	}

	class MethodVisitor extends ASTVisitor {
		public boolean visit(SimpleName node) {
			//获得全部变量
			if (node.resolveBinding() instanceof IVariableBinding) {
				IVariableBinding itb = (IVariableBinding) node.resolveBinding();
				param.addMethodVarible(itb.getKey());
			}

			return false;

		}
	}
	//计算阶乘数
	private int fac(int n){
		int i,t=1;
		if(n>0){
			for(i=1;i<=n;i++){
				t=t*i;
			}
		}
		return t;
	}
	
	//计算组合数
	public  int zuhe(int n ,int r){
		return fac(n)/(fac(n-r)*fac(r));
	}
	
	
	public int getResult() {
		int pairsOfMethod =0,methodNum = methodList.size();
		int pairsOfComAttrMethod=0;
		pairsOfMethod = zuhe(methodNum,2);
		//遍历类的所有属性
		for(String akey: attributeSet){
			//属性在多少方法中出现
			int attributeUseTime=0;
			//变量所有方法，看是否有使用属性
			for(MethodParam mp:methodList){
				if(mp.getMethodVaribleList().contains(akey)){
					attributeUseTime++;
				}
			}
			//计算出现的组合
			pairsOfComAttrMethod+=zuhe(attributeUseTime,2);
			
		}
		
		return pairsOfComAttrMethod/pairsOfMethod*100;

	}
}