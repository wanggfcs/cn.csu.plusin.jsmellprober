package cn.csu.plusin.jsmellprober.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;

import cn.csu.plusin.jsmellprober.visitor.CompilationUnitVisitor;
import cn.csu.plusin.jsmellprober.visitor.*;

public class CompilationUnitUtil {

	public CompilationUnitUtil(ICompilationUnit icu) {

		CompilationUnit cu = JdtAstUtil.getCompilationUnit(icu);
		long start = System.currentTimeMillis();
		try {

//			MRCVisitor MRCv = new MRCVisitor();
//			MPCVisitor MPCv = new MPCVisitor();
//			DACVisitor DACV = new DACVisitor();
//			DAC2Visitor DAC2V = new DAC2Visitor();
//			OCMECVisitor OCMECV = new OCMECVisitor();
//			WMCVisitor WMCV = new WMCVisitor();
//			NOLVVisitor NOLV = new NOLVVisitor();
			TCCVisitor TCCV = new TCCVisitor();
			ICPVisitor ICPV = new ICPVisitor();
//			WMCV.setUnit(cu);
//			WMCV.setSource(icu.getSource());
//			cu.accept(MRCv);
//			cu.accept(MPCv);
//			cu.accept(DACV);
//			cu.accept(DAC2V);
//			cu.accept(OCMECV);
//			cu.accept(WMCV);
//			cu.accept(NOLV);
			cu.accept(TCCV);
			cu.accept(ICPV);
//			System.out.println("MRC: "+MRCv.getResult());
//			System.out.println("MPC: "+MPCv.getResult());
//			System.out.println("DAC: "+DACV.getResult());
//			System.out.println("DAC2: "+DAC2V.getResult());
//			System.out.println("OCMEC: "+OCMECV.getResult());
//			System.out.println("WMC: "+WMCV.getResult());
//			System.out.println("NOLV: "+NOLV.getResult());
			System.out.println("TCCV: "+TCCV.getResult()+"%");
			System.out.println("ICPV: "+ICPV.getRusult());
			//

		} catch (Exception e) {

			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		// System.out.println(end - start);
	}
}
