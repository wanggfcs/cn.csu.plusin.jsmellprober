package cn.csu.plusin.jsmellprober.visitor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class DAC2Visitor extends ASTVisitor{

	public Set DAC2Set;
	
	public DAC2Visitor(){
		super();
		DAC2Set = new LinkedHashSet<FieldDeclaration>();
	}
	
	public boolean visit(TypeDeclaration node) {
		FieldDeclaration[] fd =  node.getFields();
		
		for (FieldDeclaration fdTemp : fd) {
			ITypeBinding fdTB = fdTemp.getType().resolveBinding();
			DAC2Set.add(fdTB.getKey());
//			int md =fdTemp.getModifiers();
//			Type tp = fdTemp.getType();
//			List fg = fdTemp.fragments();
//			for (Object fgTemp : fg){
//				VariableDeclarationFragment vdf = (VariableDeclarationFragment) fgTemp;
//				DACSet.add(fgTemp);
//				System.out.println(md+"\t"+tp.toString() +"\t"+vdf.getName().toString());
//			}
		}

		
		return false;
		
	}
	
	public int getResult(){
		return DAC2Set.size();
	}
}
