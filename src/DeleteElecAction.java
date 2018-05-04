

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosta.model.dto.Electronics;
import kosta.model.service.ElectronicsService;

public class DeleteElecAction implements Action {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelAndView mv = new ModelAndView();
		mv.setPath("errorView/error.jsp");
				
		String modelNum = request.getParameter("modelNum");
		String password = request.getParameter("password");
		//int delete(String modelNum, String password) throws SQLException;
		  	
		try {				
			if(modelNum==null || password==null) {
				throw new SQLException("정보가 정확하지 않");
			}
			
			//비밀번호 확인
			Electronics dbElec = ElectronicsService.selectByModelNum(modelNum,false);
				
			if(dbElec.getPassword().equals(password)) { //일치
				int re = ElectronicsService.delete(modelNum,password );
				if(re>0) { //삭제가 완료되었다 (성공)
					mv.setPath("elec?command=list"); //default가 list여서 ?command=list 안넣어줘도됨
					mv.setInRedirect(true); //redirect방식으로 이동				
				}
			}	
			else {
				throw new SQLException("비밀번호 오류야");
			}
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			//mv.setPath("errorView/error.jsp");
		}		
		
		return mv;
	}

}
