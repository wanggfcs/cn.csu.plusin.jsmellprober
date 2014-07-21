package cn.csu.plusin.jsmellprober.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class DACVisitor extends ASTVisitor {
	public List DACSet;
	
	public DACVisitor(){
		super();
		DACSet = new ArrayList<VariableDeclarationFragment>();
		
		
	}

	public boolean visit(TypeDeclaration node) {
		FieldDeclaration[] fd =  node.getFields();
		
		for (FieldDeclaration fdTemp : fd) {
			
			int md =fdTemp.getModifiers();
			Type tp = fdTemp.getType();
			List fg = fdTemp.fragments();
			for (Object fgTemp : fg){
				VariableDeclarationFragment vdf = (VariableDeclarationFragment) fgTemp;
				DACSet.add(fgTemp);
//				System.out.println(md+"\t"+tp.toString() +"\t"+vdf.getName().toString());
			}
		}

		
		return false;
		
	}
	
	public int getResult(){
		return DACSet.size();
	}
}
