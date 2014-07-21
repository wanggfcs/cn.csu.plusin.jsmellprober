package cn.csu.plusin.jsmellprober.handler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import cn.csu.plusin.jsmellprober.model.SmellsManager;
import cn.csu.plusin.jsmellprober.util.ProjectUtil;

public class AddToFavoritesHandler extends AbstractHandler {

	private IProject project;
	private ISelection selection;

	public Object execute(ExecutionEvent event) throws ExecutionException {
		selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection)
			project = this.getProject();
		new ProjectUtil(project);
		return null;
	}

	private IProject getProject() {
		IResource[] rs = (IResource[]) getSelectedResources(
				(IStructuredSelection) selection, IResource.class);
		IProject project = null;

		for (int i = 0; i < rs.length; i++) {
			IResource r = rs[i];
			int type = r.getType();
			switch (type) {
			case 4:
				project = (IProject) r;
				break;
			// case 1: project = (IFile) r; break;
			// case 2: project = (IFolder) r; break;
			default:
				;

			}
			
		}
		return project;
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
}