package cn.csu.plusin.jsmellprober.model;

import org.eclipse.core.runtime.IAdaptable;

public interface ISmellItem extends IAdaptable {
	String getName();
	void setName(String newName);
	String getLocation();
	boolean isSmellFor(Object obj);
	String getInfo();
	
	static ISmellItem[] NONE = new ISmellItem[] {};

	SmellItemType2 getType();
}
