package cn.csu.plusin.jsmellprober.util;

import java.util.ArrayList;
import java.util.List;

import cn.csu.plusin.jsmellprober.model.ViewMetricEntity;
import cn.csu.plusin.jsmellprober.model.ViewSmellEntity;
import cn.csu.plusin.jsmellprober.visitor.ClassVisitor;
import cn.csu.plusin.jsmellprober.visitor.CompilationUnitVisitor;

public class SingleProjectUtil {
	CompilationUnitVisitor visitor;

	public SingleProjectUtil(String path) {
		long start = System.currentTimeMillis();
		try {
			visitor = new CompilationUnitVisitor(path);
			//

		} catch (Exception e) {

			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	public List<ViewMetricEntity> getOutput() {
		List<ViewMetricEntity> vmel = new ArrayList<ViewMetricEntity>();
		for (List<String> ls : visitor.getOutput()) {
			ViewMetricEntity temp = new ViewMetricEntity();
			temp.setClassName(ls.get(0));
			temp.setMethodNumber(ls.get(1));
			temp.setFiledNumber(ls.get(2));
			temp.setTotalCodeLine(ls.get(3));
			temp.setTotalCodeLineWOC(ls.get(4));
			temp.setMethodtotalCodeLine(ls.get(5));
			temp.setMethodtotalCodeLineWOC(ls.get(6));
			temp.setAverCodeLine(ls.get(7));
			temp.setAverCodeLineWOC(ls.get(8));
			temp.setMaxCodeLine(ls.get(9));
			temp.setMaxCodeLineWOC(ls.get(10));
			temp.setMinCodeLine(ls.get(11));
			temp.setMinCodeLineWOC(ls.get(12));
			temp.setMethodAvgMc(ls.get(13));
			temp.setMethodTotalMc(ls.get(14));
			temp.setMethodMaxMc(ls.get(15));
			temp.setMethodMinMc(ls.get(16));
			temp.setLocal(ls.get(17));
			vmel.add(temp);

		}
		return vmel;
	}

}
