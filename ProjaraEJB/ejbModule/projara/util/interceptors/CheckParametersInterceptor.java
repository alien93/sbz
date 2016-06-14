package projara.util.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import projara.util.exception.BadArgumentsException;

public class CheckParametersInterceptor {

	@AroundInvoke
	public Object check(InvocationContext context) throws Exception{
		Object[] parameters = context.getParameters();
		for(int i=0;i<parameters.length;i++){
			Object parameter = parameters[i];
			if(parameter == null)
				throw new BadArgumentsException("At least one parameter is null");
			
			if(parameter instanceof String){
				String stringPar = ((String)parameter).trim();
				if(stringPar.isEmpty())
					throw new BadArgumentsException("String parameter can not be empty");
				parameters[i] = stringPar;
			}
		}
		
		context.setParameters(parameters);
		return context.proceed();
	}
}
