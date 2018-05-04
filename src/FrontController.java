

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
		//application영역에 저장된 map을 꺼내서 전역변수에 저장한다
		map =(Map<String, Action>)config.getServletContext().getAttribute("map");
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//parameter로 넘어오는 command받기 = key
		String key = request.getParameter("command");
		if(key==null)key="list";
		
		//map에서 key에 해당하는 클래스를 꺼내서 메소드를 호출하고 리턴값을 받는다
		
		//return값(ModelAndView)에 딸 이동방식과 이동경로를 정한다
		
		ModelAndView mv =map.get(key).execute(request, response);
		
		if(mv.isInRedirect()) {//redirect
			response.sendRedirect(mv.getPath());
		}else {//forward
			request.getRequestDispatcher(mv.getPath()).forward(request, response);
		}
		
	}

}
