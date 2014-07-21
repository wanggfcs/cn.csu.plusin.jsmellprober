package cn.csu.plusin.jsmellprober.handler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import cn.csu.plusin.jsmellprober.model.SmellsManager;
import cn.csu.plusin.jsmellprober.util.*;
import cn.csu.plusin.jsmellprober.views.MetricView;
public class AddToProjectHandler extends AbstractHandler {
	
	
	private Shell shell;
	private ISelection selection;
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection)
			this.selection = selection;
		String path = this.getProject().getLocation().toOSString();
		System.out.println("current project name==="
				+ path);
		SingleProjectUtil spu = new SingleProjectUtil(path);
		
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		if (window == null)
			return null;
		
		// Get the active page

		IWorkbenchPage page = window.getActivePage();
		if (page == null)
			return null;
		MetricView mv = (MetricView) page.findView(MetricView.ID);
		if(mv == null){
			try {
				page.showView(MetricView.ID);
				mv = (MetricView) page.findView(MetricView.ID);
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		mv.setViewInput(spu.getOutput(),this.getProject());
		return null;
	}

	public static Object getAdapter(Object adaptable, Class c) {
		if (c.isInstance(adaptable)) {
			return adaptable;
		}
		if (adaptable instanceof IAdaptable) {
			IAdaptable a = (IAdaptable) adaptable;
			Object adapter = a.getAdapter(c);
			if (c.isInstance(adapter)) {
				return adapter;
			}
		}
		return null;
	}

	private Object[] getSelectedResources(IStructuredSelection selection,
			Class c) {
		return getSelectedAdaptables(selection, c);
	}

	private static Object[] getSelectedAdaptables(ISelection selection, Class c) {
		ArrayList result = null;
		if (!selection.isEmpty()) {
			result = new ArrayList();
			Iterator elements = ((IStructuredSelection) selection).iterator();
			while (elements.hasNext()) {
				Object adapter = getAdapter(elements.next(), c);
				if (c.isInstance(adapter)) {
					result.add(adapter);
				}
			}
		}
		if (result != null && !result.isEmpty()) {
			return result
					.toArray((Object[]) Array.newInstance(c, result.size()));
		}
		return (Object[]) Array.newInstance(c, 0);
	}

	/**
	 * 这个方法保存了ISelection的引用， 请注意：ISelection的实际类型因不同的应用，其实际类型可能不同，
	 * 共有三种可能，请查阅eclipse API。
	 * 
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
//	public void selectionChanged(IAction action, ISelection selection) {
//		this.selection = (IStructuredSelection) selection;
//		System.out.println("current project name==="
//				+ this.getProject().getName());
//	}

	/**
	 * 这个方法可以得到current project。
	 * 
	 * @return
	 */
	private IProject getProject() {
		IResource[] rs = (IResource[]) getSelectedResources(
				(IStructuredSelection) selection, IResource.class);
		IProject project = null;
		
		for (int i = 0; i < rs.length; i++) {
			IResource r = rs[i];
			int type = r.getType();
			switch(type){
			case 4:  project = (IProject) r; break;
//			case 1:  project = (IFile) r; break;
//			case 2:  project = (IFolder) r; break;
			default: ;
			
			}
//			if (type == IResource.FILE) {
//				project = (IProject) r;
//				break;
//			}
		}
		return project;
	}

}