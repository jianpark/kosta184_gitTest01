

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/elec")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Map<String, Action> map;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		//application������ ����� map�� ������ ���������� �����Ѵ�
		map =(Map<String, Action>)config.getServletContext().getAttribute("map");
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//parameter�� �Ѿ���� command�ޱ� = key
		String key = request.getParameter("command");
		if(key==null)key="list";
		
		//map���� key�� �ش��ϴ� Ŭ������ ������ �޼ҵ带 ȣ���ϰ� ���ϰ��� �޴´�
		
		//return��(ModelAndView)�� �� �̵���İ� �̵���θ� ���Ѵ�
		
		ModelAndView mv =map.get(key).execute(request, response);
		
		if(mv.isInRedirect()) {//redirect
			response.sendRedirect(mv.getPath());
		}else {//forward
			request.getRequestDispatcher(mv.getPath()).forward(request, response);
		}
		
	}

}
