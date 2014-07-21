package cn.csu.plusin.jsmellprober.model;

import java.util.EventObject;



public class SmellsManagerEvent extends EventObject 
{
	   private static final long serialVersionUID = 3697053173931102953L;

	   private final ISmellItem[] added;
	   private final ISmellItem[] removed;

	   public SmellsManagerEvent(
			   SmellsManager source,
	      ISmellItem[] itemsAdded, ISmellItem[] itemsRemoved
	   ) {
	      super(source);
	      added = itemsAdded;
	      removed = itemsRemoved;
	   }

	   public ISmellItem[] getItemsAdded() {
	      return added;
	   }

	   public ISmellItem[] getItemsRemoved() {
	      return removed;
	   }
}
