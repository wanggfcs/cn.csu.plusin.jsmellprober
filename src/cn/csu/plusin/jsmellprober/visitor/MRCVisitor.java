package cn.csu.plusin.jsmellprober.visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

public class MRCVisitor extends ASTVisitor {

	private Set MethodInClassSet;
	private Set MethodInClassKeySet;
	private MethodInClass tempMIC;

	// private Map VariableMap;

	public MRCVisitor() throws Exception {

		super();
		MethodInClassSet = new LinkedHashSet<MethodInClass>();
		MethodInClassKeySet = new LinkedHashSet<String>();
		// VariableMap = new HashMap<String, Type>();
	}

	public boolean visit(TypeDeclaration node) {

		
		// 对类中的方法进行分析
		for (MethodDeclaration md : node.getMethods()) {
			IMethodBinding i = md.resolveBinding();
			String MKey = i.getKey();
			// System.out.println(MKey);
			MethodInClassKeySet.add(MKey);
			tempMIC = new MethodInClass();
			tempMIC.setMDInstant(md);
			md.accept(new MethodInvocationVisitor());

			MethodInClassSet.add(tempMIC);
		}
		System.out.println();
		System.out.println(node.getName().getIdentifier());
		return true;

	}

	// 统计方法中调研的方法
	class MethodInvocationVisitor extends ASTVisitor {
		// List<MethodInvocation> MIL = new ArrayList<MethodInvocation>() ;

		public boolean visit(MethodInvocation MI) {
			IMethodBinding i = MI.resolveMethodBinding();
			String MIKey = i.getKey();
			// System.out.println(MIKey);

			tempMIC.addMI(MIKey);

			return false;
		}
	}

	class MethodInClass {
		MethodDeclaration MDInstant;
		public Set MethodInvocationKeySet;

		MethodInClass() {
			MethodInvocationKeySet = new HashSet<String>();

		}

		void addMI(String MIKey) {

			MethodInvocationKeySet.add(MIKey);
		}

		void setMDInstant(MethodDeclaration md) {
			MDInstant = md;
		}
	}

	public int getResult() {
		int result = 0;
		Set resultSet = new LinkedHashSet<String>();

		resultSet.addAll(MethodInClassKeySet);
		for (Object o : MethodInClassSet) {
			MethodInClass mic = (MethodInClass) o;
			resultSet.addAll(mic.MethodInvocationKeySet);
		}

		if (result == 0) {
//			System.out.println(Arrays.toString(resultSet.toArray(new String[0]))); 
			for (Object o : resultSet) {
//				 System.out.println((String)o);
			}
		}

		return resultSet.size();

	}

}
