package com.sam.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String from;
	private String to;
	private String msg;
	private String serverName;
	
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
	private Date   time;
}
