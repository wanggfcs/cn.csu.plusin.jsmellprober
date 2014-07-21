package cn.csu.plusin.jsmellprober.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ProjectUtil {
	private ArrayList<String> filePath = new ArrayList<String>();
	private ArrayList<String> projectPathList = new ArrayList<String>();
	private HashMap<Integer, ArrayList<Project>> projectCounter = new HashMap<Integer, ArrayList<Project>>();
	private String projectsPath;

	public ProjectUtil(String projectsPath) {
		setProjectsPath(projectsPath);

	}

	public ProjectUtil(IProject project)  {
		IJavaProject javaProject = JavaCore.create(project);
		IPackageFragment[] packages;
		try {
			packages = javaProject.getPackageFragments();
			for (IPackageFragment mypackage : packages) {
			    if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
			        for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
			        	new CompilationUnitUtil(unit);
			        }
			    }
			}
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	/**
	 * get all Java file
	 * 
	 * 
	 * @return
	 */

	public ArrayList<String> getJavaProjects() {
		File dir = new File(projectsPath);
		File[] fileList = dir.listFiles();

		for (File file : fileList) {
			String path = file.getAbsolutePath();

			if (path.endsWith(".java")) {
				filePath.add(file.getAbsolutePath());
			} else if (file.isDirectory()) {
				projectPathList.add(file.getAbsolutePath());
				// System.out.println(path.substring(8, path.length()));
			}
		}
		return projectPathList;
	}

	private String getProjectsPath() {
		return projectsPath;
	}

	private void setProjectsPath(String projectsPath) {
		this.projectsPath = projectsPath;
	}

	class Project {
		String projectPath;
		String projectName;
		String version;

		public String getProjectPath() {
			return projectPath;
		}

		public void setProjectPathh(String path) {
			this.projectPath = path;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

	}

}
