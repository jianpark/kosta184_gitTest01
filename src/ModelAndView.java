

public class ModelAndView {
	private String path;
	private boolean inRedirect; //true�� redirect��� false�̸� forward��� / default forward
	
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isInRedirect() {
		return inRedirect;
	}
	public void setInRedirect(boolean inRedirect) {
		this.inRedirect = inRedirect;
	}
}
