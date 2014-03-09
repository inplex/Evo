package me.inplex.evo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public final class ObjectInspector {

	private AtomicReference<Object> reference;
	private HashMap<Integer, Object> values;
	private HashMap<Integer, Object> lastValues;
	private int interval;
	private boolean output;
	private Thread thread;
	private ArrayList<ObjectChange> changes;
	private boolean changed = false;
	private boolean running;

	public ObjectInspector(AtomicReference<Object> reference) {
		this(reference, 20, false);
	}
	
	public ObjectInspector(AtomicReference<Object> reference, int interval) {
		this(reference, interval, false);
	}
	
	public ObjectInspector(AtomicReference<Object> reference, boolean output) {
		this(reference, 20, output);
	}
	
	public ObjectInspector(AtomicReference<Object> reference, int interval, boolean output) {
		this.reference = reference;
		this.values = new HashMap<Integer, Object>();
		this.lastValues = new HashMap<Integer, Object>();
		this.interval = interval;
		this.changes = new ArrayList<ObjectChange>();
		this.output = output;
	}
	
	public boolean hasChanged() {
		if(changed) {
			changed = false;
			return true;
		}
		return false;
	}
	
	public ObjectChange getLastChange() {
		return changes.size() > 0 ? changes.get(changes.size() - 1) : null;
	}
	
	public ArrayList<ObjectChange> getChanges() {
		return changes;
	}

	public void start() {
		this.running = true;
		this.thread = new Thread() {
			@Override
			public void run() {
				try {
					Object object = reference.get();
					for (int i = 0; i < object.getClass().getFields().length; i++) {
						object.getClass().getFields()[i].setAccessible(true);
						values.put(i, object.getClass().getFields()[i].get(object));
					}
					for (int i = 0; i < object.getClass().getFields().length; i++) {
						object.getClass().getFields()[i].setAccessible(true);
						lastValues.put(i, object.getClass().getFields()[i].get(object));
					}
					while (running) {
						object = reference.get();
						for (int i = 0; i < object.getClass().getFields().length; i++) {
							object.getClass().getFields()[i].setAccessible(true);
							values.put(i, object.getClass().getFields()[i].get(object));
						}
						for (int key : values.keySet()) {
							Object value = values.get(key);
							if(!lastValues.get(key).equals(value)) {
								ObjectChange c = new ObjectChange(object.getClass().getFields()[key].getName(), lastValues.get(key), values.get(key));
								changes.add(c);
								changed = true;
								if(output) {
									System.out.println("Change: " + c.getName() + " from " + c.getFrom() + " to " + c.getTo());
								}
							}
						}
						for (int key : values.keySet()) {
							lastValues.put(key, values.get(key));
						}
						sleep(interval);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
		this.thread.start();
	}
	
	public void stop() throws Exception {
		this.running = false;
		this.thread.join();
	}
	
}