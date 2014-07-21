package cn.csu.plusin.jsmellprober.views;

import java.util.List;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
//���������ɴ�������뵽�������ݽ���ɸѡ��ת����
//����Ҫʵ�ֽӿڵ����ַ����� ����getElements����Ҫ�����������������������õ�����ʵ�־�����

public class MetricContentProvider implements IStructuredContentProvider {
	// �����뵽�������ݼ��Ͻ���ɸѡ��ת��
	// ��������ݼ�ȫ��Ҫת�������飬ÿһ������Ԫ�ؾ���һ��ʵ�������Ҳ���Ǳ���е�һ����¼��
	public Object[] getElements(Object element) {
		// ����element����ͨ��setInput(Object input)����Ķ���input�������������setInput��List����
		if (element instanceof List)// ��һ��List�����ж�
			return ((List) element).toArray(); // �����ݼ�Listת��Ϊ����
		else
			return new Object[0]; // ���List�����򷵻�һ��������
	}

	// ��TableViewer���󱻹ر�ʱ����ִ�д˷���
	public void dispose() {
	}

	// ��TableViewer�ٴε���setInput()ʱ����ִ�д˷���
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}
}
