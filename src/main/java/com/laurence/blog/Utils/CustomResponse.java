package com.laurence.blog.Utils;

import lombok.Data;

@Data
public class CustomResponse
{
	String errno;
	Object data;

	public CustomResponse(String errCode, Object data)
	{
		this.errno = errCode;
		this.data = data;
	}

	public CustomResponse()
	{
	}

	public static CustomResponse createResponse(String errCode,Object data)
	{
		return new CustomResponse(errCode,data);
	}
}
