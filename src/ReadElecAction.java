

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosta.model.dto.Electronics;
import kosta.model.service.ElectronicsService;

public class ReadElecAction implements Action {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ModelAndView mv = new ModelAndView();
		
		String flag = request.getParameter("flag"); //수정이 완료된후 전달된다.
		boolean state = false;
		if(flag==null) state = true; //list에서 제목을 클릭했을때 상황이다
		
		// 모델번호 받기
		String modelNum = request.getParameter("modelNum");
		
		try {
			Electronics electronics = ElectronicsService.selectByModelNum(modelNum, state); //조회수 증가시켜줘
			request.setAttribute("elec", electronics); //elec ; view(read.jsp)에서 사용하는 이름과 동일
			mv.setPath("elecView/read.jsp"); //둘다 forward방식
			
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			mv.setPath("errorView/error.jsp");//둘다 forward방식
		}
		
		return mv;
	}

}





