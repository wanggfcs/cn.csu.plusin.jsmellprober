package cn.csu.plusin.jsmellprober.views;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import cn.csu.plusin.jsmellprober.model.ViewMetricEntity;
import cn.csu.plusin.jsmellprober.model.ViewSmellEntity;
//��ǩ�������˵�������Ƕ�����������ݼ�������
//��ô��ǩ�����Ƕ����ݼ��еĵ���ʵ�������д����ת�����ɱ�ǩ��������ʵ������е��ֶ���ʾ�ڱ�����һ���С�

public class MetricLabelProvider implements ITableLabelProvider {
	// ��������ͼ��
	private Image[] images = new Image[] {
	// new Image(null, "icons/refresh.gif"),
	// new Image(null, "icons/star.jpg"),
	// new Image(null, "icons/moon.jpg")
	};

	// �ɴ˷����������ݼ�¼�ڱ���ÿһ����ʾʲô���֡�
	// element������һ��ʵ�������col�ǵ�ǰҪ���õ��е��кţ�0�ǵ�һ�С�
	public String getColumnText(Object element, int col) {
		ViewMetricEntity o = (ViewMetricEntity) element; // ����ת��
		if (col == 0)// ��һ��Ҫ��ʾʲô����
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

		return null; // �������Է��ؿ�ֵ

	}

	// getColumnText����������ʾ���֣�������������ʾͼƬ��
	public Image getColumnImage(Object element, int col) {
		// PeopleEntity o = (PeopleEntity) element;
		// // ֻ�á��¸ա�������¼��ʾͼƬ
		// if (o.getName().equals("�¸�") || o.getName().equals("����")) {
		// if (col == 0)// ��һ��Ҫ��ʾ��ͼƬ
		// // return images[0];
		// if (col == 2)// �����Ա���ʾ��ͬ��ͼ��
		// return o.isSex() ? images[1] : images[2];
		// }
		return null; // �������Է��ؿ�ֵ
	}

	public void dispose() {
		// ������SWT�����ԭ���Լ����������ͷ�
		for (Image image : images) {
			image.dispose();
		}
	}

	// -------------���·�������ʹ��,�Ȳ��ùܣ������ǿ�ʵ��-----------------
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void removeListener(ILabelProviderListener listener) {
	}
}