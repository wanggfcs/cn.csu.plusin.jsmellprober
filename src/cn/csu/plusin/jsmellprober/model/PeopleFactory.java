package cn.csu.plusin.jsmellprober.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PeopleFactory {
	public static List<PeopleEntity> getPeoples() { // �����ľ�̬����
		List<PeopleEntity> list = new ArrayList<PeopleEntity>();
		{ // ��1��ʵ�������
			PeopleEntity o = new PeopleEntity();
			o.setId(new Long(1));// id�ֶε����ͱ��������Long������Ҫת��һ��
			o.setName("�¸�");
			o.setSex(true);
			o.setAge(28);
			o.setCreateDate(new Date()); // ��ǰ����
			list.add(o);
		}
		{ // ��2��ʵ�������
			PeopleEntity o = new PeopleEntity();
			o.setId(2L); // ����JDK5.0���Զ�װ�书�ܣ�ʡ��long��Long�����ת��
			o.setName("����");
			o.setSex(false);
			o.setAge(18);
			o.setCreateDate(new Date());
			list.add(o);
		}
		{ // ��3��ʵ�������
			PeopleEntity o = new PeopleEntity();
			o.setId(3L);
			o.setName("�³���");
			o.setSex(true);
			o.setAge(27);
			o.setCreateDate(new Date());
			list.add(o);
		}
		return list;
	}

	public static Object getPeople2() {
		List<PeopleEntity> list = new ArrayList<PeopleEntity>();
		{ // ��1��ʵ�������
			PeopleEntity o = new PeopleEntity();
			o.setId(new Long(1));// id�ֶε����ͱ��������Long������Ҫת��һ��
			o.setName("�¸�");
			o.setSex(true);
			o.setAge(28);
			o.setCreateDate(new Date()); // ��ǰ����
			list.add(o);
		}

		return list;
	}

	public static Object getSmell() {
		List<ViewSmellEntity> list = new ArrayList<ViewSmellEntity>();
		{ // ��1��ʵ�������
			ViewSmellEntity o = new ViewSmellEntity();
			o.setId(new Long(1));// id�ֶε����ͱ��������Long������Ҫת��һ
			o.setClassName("Happy");
			o.setSmellName("���������б�");
			o.setStart(20);
			o.setEnd(30);
			o.setLocation((new Date()).toString()); // ��ǰ����
			list.add(o);
		}
		{ // ��2��ʵ�������
			ViewSmellEntity o = new ViewSmellEntity();
			o.setId(new Long(2));// id�ֶε����ͱ��������Long������Ҫת��һ
			o.setClassName("UnHappy");
			o.setSmellName("�ϵ���");
			o.setStart(25);
			o.setEnd(39);
			o.setLocation((new Date()).toString()); // ��ǰ����
			list.add(o);
		}
		{ // ��3��ʵ�������
			ViewSmellEntity o = new ViewSmellEntity();
			o.setId(new Long(3));// id�ֶε����ͱ��������Long������Ҫת��һ
			o.setClassName("Sad");
			o.setSmellName("���ܾ�������");
			o.setStart(40);
			o.setEnd(80);
			o.setLocation((new Date()).toString()); // ��ǰ����
			list.add(o);
		}
		return list;
	}
}
