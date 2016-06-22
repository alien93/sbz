package projara.util.interceptors;

import java.lang.reflect.Field;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import projara.rest.beans.BillRestBean;

public class IsLoggedInterception {

	
	@AroundInvoke
	public Object checkLogged(InvocationContext ictx) throws Exception{
		
		Object target = ictx.getTarget();
		
		System.out.println(target.getClass().getSimpleName());
		

		
		
		return ictx.proceed();
	}

}
