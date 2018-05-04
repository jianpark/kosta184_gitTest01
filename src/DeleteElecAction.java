

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
				throw new SQLException("������ ��Ȯ���� ��");
			}
			
			//��й�ȣ Ȯ��
			Electronics dbElec = ElectronicsService.selectByModelNum(modelNum,false);
				
			if(dbElec.getPassword().equals(password)) { //��ġ
				int re = ElectronicsService.delete(modelNum,password );
				if(re>0) { //������ �Ϸ�Ǿ��� (����)
					mv.setPath("elec?command=list"); //default�� list���� ?command=list �ȳ־��൵��
					mv.setInRedirect(true); //redirect������� �̵�				
				}
			}	
			else {
				throw new SQLException("��й�ȣ ������");
			}
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			//mv.setPath("errorView/error.jsp");
		}		
		
		return mv;
	}

}
