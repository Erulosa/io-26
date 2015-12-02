package mainJava;

import java.io.Serializable;
import java.util.ArrayList;

public class DataBase implements Serializable{
	 private ArrayList<Task> tasks;
	    public DataBase() {
	        tasks = new ArrayList<Task>();
	    }
	    public void addTask(Task task) {
	         tasks.add(task);
	    }
	    public void removeTask(int index) {
	        tasks.remove(index);
	    }
	    public Task getTask(int index) {
	        return tasks.get(index);
	    }
	    public Integer getTaskCount() {
	        return tasks.size();
	    }
	    public boolean containsTask(String name) {
	        for (Task task : tasks)
	            if (task.getSubject().equals(name))
	                return true;
	        return false;
	    }
	}
