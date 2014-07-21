package cn.csu.plusin.jsmellprober.views;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.*;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
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

import cn.csu.plusin.jsmellprober.Activator;
import cn.csu.plusin.jsmellprober.model.PeopleEntity;
import cn.csu.plusin.jsmellprober.model.PeopleFactory;
import cn.csu.plusin.jsmellprober.model.SmellsManager;
import cn.csu.plusin.jsmellprober.model.ViewMetricEntity;

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

public class MetricView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "cn.csu.plusin.jsmellprober.views.MetricView";

	private TableViewer viewer;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;
	private TableColumn typeColumn;
	private TableColumn nameColumn;
	private TableColumn locationColumn;
	private IProject project;
	private ViewMetricEntity vme;

	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public MetricView() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */

	private void createTableViewer(Composite parent) {

		viewer = new TableViewer(parent, SWT.MULTI | SWT.BORDER
				| SWT.FULL_SELECTION);
		// 第二步：通过表格内含的Table对象设置布局方式
		Table table = viewer.getTable();
		table.setHeaderVisible(true); // 显示表头
		table.setLinesVisible(true); // 显示表格线
		TableLayout layout = new TableLayout(); // 专用于表格的布局
		table.setLayout(layout);
		// 第三步：用TableColumn类创建表格列

		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("类名");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("方法个数");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("属性个数");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("总代码行数（含注释）");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("总代码行数（无注释）");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("方法总代码行数（含注释）");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("方法总代码行数（无注释）");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("方法平均代码行数（含）");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("方法平均代码行数（无）");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("方法最大代码行数（含）");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("方法最大代码行数（无）");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("方法最小代码行数（含）");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("方法最小代码行数（无）");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("方法总圈复杂度");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("方法平均圈复杂度");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("方法最大圈复杂度");
		layout.addColumnData(new ColumnWeightData(200));
		new TableColumn(table, SWT.NONE).setText("方法最小圈复杂度");
		layout.addColumnData(new ColumnWeightData(70));// ID列宽13像素
		new TableColumn(table, SWT.NONE).setText("文件路径");

		// 第四步：设置内容器和标签器
		viewer.setContentProvider(new MetricContentProvider());
		viewer.setLabelProvider(new MetricLabelProvider());
	}

	public void createPartControl(Composite parent) {
		// viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
		// | SWT.V_SCROLL);
		// viewer.setContentProvider(new ViewContentProvider());
		// viewer.setLabelProvider(new ViewLabelProvider());
		// viewer.setSorter(new NameSorter());
		// viewer.setInput(getViewSite());
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
				MetricView.this.fillContextMenu(manager);
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
				vme = (ViewMetricEntity) obj;
				showMessage("Double-click detected on " + obj.toString());
				try {
					IWorkbenchPage page = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					
					String name = project.getName();
					String path = vme.getLocal().substring(name.length()+4);
					IFile java_file = project.getFile(path);
					IDE.openEditor(page, java_file,
							"org.eclipse.jdt.ui.CompilationUnitEditor");
				} catch (CoreException e) {
					IStatus status = new Status(IStatus.ERROR, "myplugin", 102,
							"打开工作区内文件出错", e);
					Activator.getDefault().getLog().log(status);
				}

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

	public void setViewInput(Object vme, IProject project) {
		viewer.setInput(vme);
		this.project = project;

	}
}