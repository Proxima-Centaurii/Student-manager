package data;

import java.io.Serializable;

public class ModuleRecord implements Comparable<ModuleRecord>, Serializable{
	public String code;
	public int mark;
	
	public ModuleRecord() {
		code = null;
		mark = 0;
	}

	public ModuleRecord(String code, int mark) {
		this.code = code;
		this.mark = mark;
	}

	@Override
	public int compareTo(ModuleRecord m) {
		return mark - m.mark;
	}
	
}
