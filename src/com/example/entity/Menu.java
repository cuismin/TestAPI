package com.example.entity;

import java.util.Date;

@SuppressWarnings("all")
public class Menu implements java.io.Serializable{
	 
	    private String uuid;
	    private String module_name;
	    private String pid;
	    private Boolean moduel;
	    private Date create_date;
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public String getModule_name() {
			return module_name;
		}
		public void setModule_name(String module_name) {
			this.module_name = module_name;
		}
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public Boolean getModuel() {
			return moduel;
		}
		public void setModuel(Boolean moduel) {
			this.moduel = moduel;
		}
		public Date getCreate_date() {
			return create_date;
		}
		public void setCreate_date(Date create_date) {
			this.create_date = create_date;
		}
	    
	    
	 
	
}
