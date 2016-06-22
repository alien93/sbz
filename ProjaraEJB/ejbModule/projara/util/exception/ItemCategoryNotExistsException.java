package projara.util.exception;
/**
 * 
 * @author Nina
 *
 */
public class ItemCategoryNotExistsException extends ItemCategoryException{

	private static final long serialVersionUID = 1L;

	public ItemCategoryNotExistsException(){
		
	}
	
	public ItemCategoryNotExistsException(String message){
		super(message);
	}
	
	public ItemCategoryNotExistsException(Throwable cause){
		super(cause);
	}
	
	public ItemCategoryNotExistsException(String message, Throwable cause){
		super(message, cause);
	}
	
	public ItemCategoryNotExistsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
