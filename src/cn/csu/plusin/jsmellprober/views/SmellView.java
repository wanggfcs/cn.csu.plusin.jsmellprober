package cn.csu.plusin.jsmellprober.views;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.TableColumn;

import cn.csu.plusin.jsmellprober.model.PeopleEntity;
import cn.csu.plusin.jsmellprober.model.PeopleFactory;
import cn.csu.plusin.jsmellprober.model.SmellsManager;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class SmellView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "cn.csu.plusin.jsmellprober.views.SmellView";

	private TableViewer viewer;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	private TableColumn typeColumn;
	private TableColumn nameColumn;
	private TableColumn locationColumn;

	/*
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */

//	class ViewContentProvider implements IStructuredContentProvider {
//		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
//			System.out.println("Input changed!");
//		}
//
//		public void dispose() {
//		}
//
//		public Object[] getElements(Object parent) {
//			return new String[] { "One", "Two", "Three" };
//		}
//	}
//
//	class ViewLabelProvider extends LabelProvider implements
//			ITableLabelProvider {
//		public String getColumnText(Object obj, int index) {
//			System.out.println("getText!");
//			return getText(obj);
//		}
//		
////		 public String getColumnText(Object element, int col) {  
////		        PeopleEntity o = (PeopleEntity) element; // 类型转换  
////		        if (col == 0)// 第一列要显示什么数据  
////		            return o.getId().toString();  
////		        if (col == 1)  
////		            return o.getName();  
////		        if (col == 2)  
////		            return o.isSex() ? "男" : "女";  
////		        if (col == 3)  
////		            return String.valueOf(o.getAge()); // 将int型转为String型  
////		        if (col == 4)  
////		            return o.getCreateDate().toString();  
////		        return null; // 方法可以返回空值  
////		    }  
//
//		public Image getColumnImage(Object obj, int index) {
//			return getImage(obj);
//		}
//
//		public Image getImage(Object obj) {
//			return PlatformUI.getWorkbench().getSharedImages()
//					.getImage(ISharedImages.IMG_OBJ_ELEMENT);
//		}
//	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public SmellView() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */


	 private void createTableViewer(Composite parent) {
		 
		  viewer = new TableViewer(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);  
	        // 第二步：通过表格内含的Table对象设置布局方式  
	        Table table = viewer.getTable();  
	        table.setHeaderVisible(true); // 显示表头  
	        table.setLinesVisible(true); // 显示表格线  
	        TableLayout layout = new TableLayout(); // 专用于表格的布局  
	        table.setLayout(layout);  
	        // 第三步：用TableColumn类创建表格列  
	        layout.addColumnData(new ColumnWeightData(13));// ID列宽13像素  
	        new TableColumn(table, SWT.NONE).setText("序号");  
	        layout.addColumnData(new ColumnWeightData(40));  
	        new TableColumn(table, SWT.NONE).setText("类名");  
	        layout.addColumnData(new ColumnWeightData(40));  
	        new TableColumn(table, SWT.NONE).setText("代码味道");  
	        layout.addColumnData(new ColumnWeightData(20));  
	        new TableColumn(table, SWT.NONE).setText("起始行数");  
	        layout.addColumnData(new ColumnWeightData(20));  
	        new TableColumn(table, SWT.NONE).setText("结束行数");  
	        layout.addColumnData(new ColumnWeightData(60));  
	        new TableColumn(table, SWT.NONE).setText("文件路径");  
	        // 第四步：设置内容器和标签器  
	        viewer.setContentProvider(new SmellContentProvider());  
	        viewer.setLabelProvider(new SmellLabelProvider());  
	        // 第五步：用TableViewer的setInput方法将数据输入到表格  
	        Object data = PeopleFactory.getSmell();  
	        viewer.setInput(data);  
		 
//	 viewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL
//	 | SWT.MULTI | SWT.FULL_SELECTION);
//	 final Table table = viewer.getTable();
//	
//	 TableColumnLayout layout = new TableColumnLayout();
//	 parent.setLayout(layout);
//	
//	 typeColumn = new TableColumn(table, SWT.LEFT);
//	 typeColumn.setText("");
//	 layout.setColumnData(typeColumn, new ColumnPixelData(18));
//	
//	 nameColumn = new TableColumn(table, SWT.LEFT);
//	 nameColumn.setText("Name");
//	 layout.setColumnData(nameColumn, new ColumnWeightData(4));
//	
//	 locationColumn = new TableColumn(table, SWT.LEFT);
//	 locationColumn.setText("Location");
//	 layout.setColumnData(locationColumn, new ColumnWeightData(9));
//	
//	 table.setHeaderVisible(true);
//	 table.setLinesVisible(false);
//	
//	 viewer.setContentProvider(new SmellsViewContentProvider());
//	 viewer.setLabelProvider(new SmellsViewLabelProvider());
//	 viewer.setInput(SmellsManager.getManager());
//	
//	 getSite().setSelectionProvider(viewer);
	 }

	
	
	public void createPartControl(Composite parent) {
//		 viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
//		 | SWT.V_SCROLL);
//		 viewer.setContentProvider(new ViewContentProvider());
//		 viewer.setLabelProvider(new ViewLabelProvider());
//		 viewer.setSorter(new NameSorter());
//		 viewer.setInput(getViewSite());
		createTableViewer(parent);
		// Create the help context id for the viewer's control
		PlatformUI
				.getWorkbench()
				.getHelpSystem()
				.setHelp(viewer.getControl(),
						"cn.csu.plusin.jsmellprober.viewer");

		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SmellView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		action2 = new Action() {
			public void run() {
				System.out.println("Action2!");

				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();
				showMessage("Double-click detected on " + obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(),
				"Smell View", message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}