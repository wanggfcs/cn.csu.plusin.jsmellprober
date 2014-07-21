package cn.csu.plusin.jsmellprober.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;

import cn.csu.plusin.jsmellprober.Activator;







public class SmellsManager {
	
	   private static final String TAG_FAVORITES = "Favorites";
	   private static final String TAG_FAVORITE = "Favorite";
	   private static final String TAG_TYPEID = "TypeId";
	   private static final String TAG_INFO = "Info";
	   
	private static SmellsManager manager;
	private Collection<ISmellItem> smells;
	 private List<SmellsManagerListener> listeners =
	         new ArrayList<SmellsManagerListener>();

	private SmellsManager() {

	}

	public static SmellsManager getManager() {
		if (manager == null) {
			manager = new SmellsManager();
		}
		return manager;
	}

	public ISmellItem[] getSmells() {
		if (smells == null) {
			loadSmells();
		}
		return smells.toArray(new ISmellItem[smells.size()]);
	}

	
	
	  public void addSmells(Object[] objects) {
	      if (objects == null)
	         return;
	      if (smells == null)
	         loadSmells();
	      Collection<ISmellItem> items =
	            new HashSet<ISmellItem>(objects.length);
	      for (int i = 0; i < objects.length; i++) {
	         ISmellItem item = existingSmellFor(objects[i]);
	         if (item == null) {
	            item = newSmellFor(objects[i]);
	            if (smells.add(item))
	               items.add(item);
	         }
	      }
	      if (items.size() > 0) {
	         ISmellItem[] added =
	               items.toArray(new ISmellItem[items.size()]);
	         fireSmellsChanged(added, ISmellItem.NONE);
	      }
	   }

	   public void removeSmells(Object[] objects) {
	      if (objects == null)
	         return;
	      if (smells == null)
	         loadSmells();
	      Collection<ISmellItem> items =
	            new HashSet<ISmellItem>(objects.length);
	      for (int i = 0; i < objects.length; i++) {
	         ISmellItem item = existingSmellFor(objects[i]);
	         if (item != null && smells.remove(item))
	            items.add(item);
	      }
	      if (items.size() > 0) {
	         ISmellItem[] removed =
	               items.toArray(new ISmellItem[items.size()]);
	         fireSmellsChanged(ISmellItem.NONE, removed);
	      }
	   }

	   public ISmellItem newSmellFor(Object obj) {
	      SmellItemType2[] types = SmellItemType2.getTypes();
	      for (int i = 0; i < types.length; i++) {
	         ISmellItem item = types[i].newSmell(obj);
	         if (item != null)
	            return item;
	      }
	      return null;
	   }

	   private ISmellItem existingSmellFor(Object obj) {
	      if (obj == null)
	         return null;
	      if (obj instanceof ISmellItem)
	         return (ISmellItem) obj;
	      Iterator<ISmellItem> iter = smells.iterator();
	      while (iter.hasNext()) {
	         ISmellItem item = iter.next();
	         if (item.isSmellFor(obj))
	            return item;
	      }
	      return null;
	   }

	   public ISmellItem[] existingSmellsFor(Iterator<?> iter) {
	      List<ISmellItem> result = new ArrayList<ISmellItem>(10);
	      while (iter.hasNext()) {
	         ISmellItem item = existingSmellFor(iter.next());
	         if (item != null)
	            result.add(item);
	      }
	      return (ISmellItem[]) result.toArray(new ISmellItem[result.size()]);
	   }

	   
	   // /////////////////////////////////////////////////////////////////////////
	   //
	   // Event Handling
	   //
	   // /////////////////////////////////////////////////////////////////////////

	   public void addSmellsManagerListener(
			   SmellsManagerListener listener) {
	      if (!listeners.contains(listener))
	         listeners.add(listener);
	   }

	   public void removeSmellsManagerListener(
			   SmellsManagerListener listener) {
	      listeners.remove(listener);
	   }

	   private void fireSmellsChanged(ISmellItem[] itemsAdded,
	         ISmellItem[] itemsRemoved) {
		   SmellsManagerEvent event =
	            new SmellsManagerEvent(this, itemsAdded, itemsRemoved);
	      for (Iterator<SmellsManagerListener> iter =
	            listeners.iterator(); iter.hasNext();)
	         iter.next().smellsChanged(event);
	   }

	   // /////////////////////////////////////////////////////////////////////////
	   //
	   // Persisting favorites
	   //
	   // /////////////////////////////////////////////////////////////////////////

	   private void loadSmells() {
	      smells = new HashSet<ISmellItem>(20);
	      FileReader reader = null;
	      try {
	         reader = new FileReader(getSmellsFile());
	         loadSmells(XMLMemento.createReadRoot(reader));
	      }
	      catch (FileNotFoundException e) {
	         // Ignored... no Favorites items exist yet.
	      }
	      catch (Exception e) {
	         // Log the exception and move on.
//	         FavoritesLog.logError(e);
	      }
	      finally {
	         try {
	            if (reader != null)
	               reader.close();
	         }
	         catch (IOException e) {
//	            FavoritesLog.logError(e);
	         }
	      }
	   }

	   private void loadSmells(XMLMemento memento) {
	      IMemento[] children = memento.getChildren(TAG_FAVORITE);
	      for (int i = 0; i < children.length; i++) {
	         ISmellItem item =
	        		 newSmellFor(children[i].getString(TAG_TYPEID),
	                     children[i].getString(TAG_INFO));
	         if (item != null)
	            smells.add(item);
	      }
	   }

	   public ISmellItem newSmellFor(String typeId, String info) {
		   SmellItemType2[] types = SmellItemType2.getTypes();
	      for (int i = 0; i < types.length; i++)
	         if (types[i].getId().equals(typeId))
	            return types[i].loadSmell(info);
	      return null;
	   }

	   public void saveSmells() {
	      if (smells == null)
	         return;
	      XMLMemento memento = XMLMemento.createWriteRoot(TAG_FAVORITES);
	      saveSmells(memento);
	      FileWriter writer = null;
	      try {
	         writer = new FileWriter(getSmellsFile());
	         memento.save(writer);
	      }
	      catch (IOException e) {
//	         FavoritesLog.logError(e);
	      }
	      finally {
	         try {
	            if (writer != null)
	               writer.close();
	         }
	         catch (IOException e) {
//	            FavoritesLog.logError(e);
	         }
	      }
	   }

	   private void saveSmells(XMLMemento memento) {
	      Iterator<ISmellItem> iter = smells.iterator();
	      while (iter.hasNext()) {
	         ISmellItem item = iter.next();
	         IMemento child = memento.createChild(TAG_FAVORITE);
	         child.putString(TAG_TYPEID, item.getType().getId());
	         child.putString(TAG_INFO, item.getInfo());
	      }
	   }

	   private File getSmellsFile() {
	      return Activator.getDefault()
	            .getStateLocation()
	            .append("smells.xml")
	            .toFile();
	   }
}
