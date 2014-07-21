package cn.csu.plusin.jsmellprober.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PeopleFactory {
	public static List<PeopleEntity> getPeoples() { // 工厂的静态方法
		List<PeopleEntity> list = new ArrayList<PeopleEntity>();
		{ // 第1个实体类对象
			PeopleEntity o = new PeopleEntity();
			o.setId(new Long(1));// id字段的类型被定义成了Long，所以要转化一下
			o.setName("陈刚");
			o.setSex(true);
			o.setAge(28);
			o.setCreateDate(new Date()); // 当前日期
			list.add(o);
		}
		{ // 第2个实体类对象
			PeopleEntity o = new PeopleEntity();
			o.setId(2L); // 利用JDK5.0的自动装箱功能，省了long到Long对象的转化
			o.setName("周阅");
			o.setSex(false);
			o.setAge(18);
			o.setCreateDate(new Date());
			list.add(o);
		}
		{ // 第3个实体类对象
			PeopleEntity o = new PeopleEntity();
			o.setId(3L);
			o.setName("陈常恩");
			o.setSex(true);
			o.setAge(27);
			o.setCreateDate(new Date());
			list.add(o);
		}
		return list;
	}

	public static Object getPeople2() {
		List<PeopleEntity> list = new ArrayList<PeopleEntity>();
		{ // 第1个实体类对象
			PeopleEntity o = new PeopleEntity();
			o.setId(new Long(1));// id字段的类型被定义成了Long，所以要转化一下
			o.setName("陈刚");
			o.setSex(true);
			o.setAge(28);
			o.setCreateDate(new Date()); // 当前日期
			list.add(o);
		}

		return list;
	}

	public static Object getSmell() {
		List<ViewSmellEntity> list = new ArrayList<ViewSmellEntity>();
		{ // 第1个实体类对象
			ViewSmellEntity o = new ViewSmellEntity();
			o.setId(new Long(1));// id字段的类型被定义成了Long，所以要转化一
			o.setClassName("Happy");
			o.setSmellName("过长参数列表");
			o.setStart(20);
			o.setEnd(30);
			o.setLocation((new Date()).toString()); // 当前日期
			list.add(o);
		}
		{ // 第2个实体类对象
			ViewSmellEntity o = new ViewSmellEntity();
			o.setId(new Long(2));// id字段的类型被定义成了Long，所以要转化一
			o.setClassName("UnHappy");
			o.setSmellName("上帝类");
			o.setStart(25);
			o.setEnd(39);
			o.setLocation((new Date()).toString()); // 当前日期
			list.add(o);
		}
		{ // 第3个实体类对象
			ViewSmellEntity o = new ViewSmellEntity();
			o.setId(new Long(3));// id字段的类型被定义成了Long，所以要转化一
			o.setClassName("Sad");
			o.setSmellName("被拒绝的赠遗");
			o.setStart(40);
			o.setEnd(80);
			o.setLocation((new Date()).toString()); // 当前日期
			list.add(o);
		}
		return list;
	}
}
