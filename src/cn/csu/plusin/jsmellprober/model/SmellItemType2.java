package cn.csu.plusin.jsmellprober.model;



import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public abstract class SmellItemType2 implements Comparable {
	private static final ISharedImages PLATFORM_IMAGES = PlatformUI
			.getWorkbench().getSharedImages();
	private final String id;
	private final String printName;
	private final int ordinal;

	private SmellItemType2(String id, String name, int position) {
		this.id = id;
		this.printName = name;
		this.ordinal = position;
	}

	public String getId() {
		return id;

	}

	public String getName() {
		return printName;
	}

	public abstract Image getImage();

	public abstract ISmellItem newSmell(Object obj);

	public abstract ISmellItem loadSmell(String info);

	public int compareTo(SmellItemType2 other) {
		return this.ordinal - other.ordinal;
	}

	
	public static final SmellItemType2 UNKNOWN = new SmellItemType2("Unknown","Unknown",0){
		public Image getImage(){
			return null;
		}
		public ISmellItem newSmell(Object obj){
			return null;
		}
		public ISmellItem loadSmell(String info){
			return null;
		}
		@Override
		public int compareTo(Object arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
	public static final SmellItemType2 WORKBENCH_FILE = new SmellItemType2("Unknown","Unknown",0){
		public Image getImage(){
			return PLATFORM_IMAGES.getImage(org.eclipse.ui.ISharedImages.IMG_OBJ_FILE);
		}
		public ISmellItem newSmell(Object obj){
			if(!(obj instanceof IFile)){
				return null;
			}
			return new SmellResource(this,(IFile)obj);
		}
		public ISmellItem loadSmell(String info){
			return SmellResource.loadSmell(this,info);
		}
		@Override
		public int compareTo(Object arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
	public static final SmellItemType2 WORKBENCH_PROJECT = new SmellItemType2("WBProject","Workbench Project",0){
		public Image getImage(){
			return PLATFORM_IMAGES.getImage(org.eclipse.ui.ISharedImages.IMG_OBJ_PROJECT);
		}
		public ISmellItem newSmell(Object obj){
			if(!(obj instanceof IProject)){
				return null;
			}
			return new SmellResource(this,(IProject)obj);
		}
		public ISmellItem loadSmell(String info){
			return SmellResource.loadSmell(this,info);
		}
		@Override
		public int compareTo(Object arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
	private static final SmellItemType2[] TYPES = {
		UNKNOWN,WORKBENCH_FILE
	};
	
	public static SmellItemType2[] getTypes(){
		return TYPES;
		
		
	}
}
