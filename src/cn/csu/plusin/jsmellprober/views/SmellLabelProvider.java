package cn.csu.plusin.jsmellprober.views;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;


import cn.csu.plusin.jsmellprober.model.ViewSmellEntity;
//��ǩ�������˵�������Ƕ�����������ݼ�������
//��ô��ǩ�����Ƕ����ݼ��еĵ���ʵ�������д����ת�����ɱ�ǩ��������ʵ������е��ֶ���ʾ�ڱ�����һ���С�
public class SmellLabelProvider implements ITableLabelProvider {
	// ��������ͼ��
	private Image[] images = new Image[] {
//			new Image(null, "icons/refresh.gif"),
//			new Image(null, "icons/star.jpg"),
//			new Image(null, "icons/moon.jpg") 
			};
	// �ɴ˷����������ݼ�¼�ڱ���ÿһ����ʾʲô���֡�
	// element������һ��ʵ�������col�ǵ�ǰҪ���õ��е��кţ�0�ǵ�һ�С�
	public String getColumnText(Object element, int col) {
		ViewSmellEntity o = (ViewSmellEntity) element; // ����ת��
		if (col == 0)// ��һ��Ҫ��ʾʲô����
			return o.getId().toString();
		if (col == 1)
			return o.getClassName();
		if (col == 2)
			return o.getSmellName();
		if (col == 3)
			return String.valueOf(o.getStart()); 
		if (col == 4)
			return String.valueOf(o.getEnd());
		if (col == 5)
			return o.getLocation();
		return null; // �������Է��ؿ�ֵ
	}
	// getColumnText����������ʾ���֣�������������ʾͼƬ��
	public Image getColumnImage(Object element, int col) {
//		PeopleEntity o = (PeopleEntity) element;
//		// ֻ�á��¸ա�������¼��ʾͼƬ
//		if (o.getName().equals("�¸�") || o.getName().equals("����")) {
//			if (col == 0)// ��һ��Ҫ��ʾ��ͼƬ
////				return images[0];
//			if (col == 2)// �����Ա���ʾ��ͬ��ͼ��
//				return o.isSex() ? images[1] : images[2];
//		}
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