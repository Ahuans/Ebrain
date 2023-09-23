package com.whx.netty_server.client;

public class Response {
	private Long id;
	private Object content;
	private String code="00000";//状态码
	private String msg;//失败原因
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public char[] getResult() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String status) {
		this.code = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
