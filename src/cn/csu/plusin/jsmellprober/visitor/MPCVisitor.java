package cn.csu.plusin.jsmellprober.visitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import cn.csu.plusin.jsmellprober.visitor.MRCVisitor.MethodInClass;



public class MPCVisitor extends ASTVisitor {
	private Set MethodInClassSet;
	private Set MethodInClassKeySet;
	private MethodInClass tempMIC;
	public MPCVisitor() {
		super();
		MethodInClassSet = new LinkedHashSet<MethodInClass>();
		MethodInClassKeySet = new LinkedHashSet<String>();
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
		return false;
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
		public List MethodInvocationKeySet;

		MethodInClass() {
			MethodInvocationKeySet = new ArrayList<String>();

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
		List resultSet = new ArrayList<String>();

		
		for (Object o : MethodInClassSet) {
			MethodInClass mic = (MethodInClass) o;
			resultSet.addAll(mic.MethodInvocationKeySet);
			result +=mic.MethodInvocationKeySet.size();
		}
		
		if (result != 0) {
//			System.out.println(Arrays.toString(resultSet.toArray(new String[0]))); 
			for (Object o : resultSet) {
//				 System.out.println((String)o);
			}
		}

		return result;

	}
	
}
