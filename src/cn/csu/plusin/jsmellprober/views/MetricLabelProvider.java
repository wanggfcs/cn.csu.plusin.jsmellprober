package cn.csu.plusin.jsmellprober.views;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import cn.csu.plusin.jsmellprober.model.ViewMetricEntity;
import cn.csu.plusin.jsmellprober.model.ViewSmellEntity;
//标签器。如果说内容器是对输入表格的数据集作处理，
//那么标签器则是对数据集中的单个实体对象进行处理和转化，由标签器来决定实体对象中的字段显示在表格的哪一列中。

public class MetricLabelProvider implements ITableLabelProvider {
	// 创建几个图像
	private Image[] images = new Image[] {
	// new Image(null, "icons/refresh.gif"),
	// new Image(null, "icons/star.jpg"),
	// new Image(null, "icons/moon.jpg")
	};

	// 由此方法决定数据记录在表格的每一列显示什么文字。
	// element参数是一个实体类对象。col是当前要设置的列的列号，0是第一列。
	public String getColumnText(Object element, int col) {
		ViewMetricEntity o = (ViewMetricEntity) element; // 类型转换
		if (col == 0)// 第一列要显示什么数据
			return o.getClassName();
		if (col == 1)
			return o.getMethodNumber();
		if (col == 2)
			return o.getFiledNumber();
		if (col == 3)
			return o.getTotalCodeLine();
		if (col == 4)
			return o.getTotalCodeLineWOC();
		if (col == 5)
			return o.getMethodtotalCodeLine();
		if (col == 6)
			return o.getMethodtotalCodeLineWOC();
		if (col == 7)
			return o.getAverCodeLine();
		if (col == 8)
			return o.getAverCodeLineWOC();
		if (col == 9)
			return o.getMaxCodeLine();
		if (col == 10)
			return o.getMaxCodeLineWOC();
		if (col == 11)
			return o.getMinCodeLine();
		if (col == 12)
			return o.getMinCodeLineWOC();
		if (col == 13)
			return o.getMethodAvgMc();
		if (col == 14)
			return o.getMethodTotalMc();
		if (col == 15)
			return o.getMethodMaxMc();
		if (col == 16)
			return o.getMethodMinMc();
		if (col == 17)
			return o.getLocal();

		return null; // 方法可以返回空值

	}

	// getColumnText方法用于显示文字，本方法用于显示图片。
	public Image getColumnImage(Object element, int col) {
		// PeopleEntity o = (PeopleEntity) element;
		// // 只让“陈刚”这条记录显示图片
		// if (o.getName().equals("陈刚") || o.getName().equals("周阅")) {
		// if (col == 0)// 第一列要显示的图片
		// // return images[0];
		// if (col == 2)// 根据性别显示不同的图标
		// return o.isSex() ? images[1] : images[2];
		// }
		return null; // 方法可以返回空值
	}

	public void dispose() {
		// 别忘了SWT组件的原则：自己创建，自释放
		for (Image image : images) {
			image.dispose();
		}
	}

	// -------------以下方法很少使用,先不用管，让它们空实现-----------------
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void removeListener(ILabelProviderListener listener) {
	}
}