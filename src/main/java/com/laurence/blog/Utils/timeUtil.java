package com.laurence.blog.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class timeUtil
{
	static public String getTimeString()
	{
		return new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date());
	}

	static public String getDateString()
	{
		return new SimpleDateFormat("yyyy-MMM-dd").format(new Date());
	}
}
