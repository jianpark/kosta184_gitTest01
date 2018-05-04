

public class ModelAndView {
	private String path;
	private boolean inRedirect; //true면 redirect방식 false이면 forward방식 / default forward
	
	
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
