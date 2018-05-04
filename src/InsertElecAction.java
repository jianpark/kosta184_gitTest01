

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kosta.model.dto.Electronics;
import kosta.model.service.ElectronicsService;

public class InsertElecAction implements Action {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		 /*MultipartRequest m = new MultipartRequest(request , 저장폴더 , 최대용량 ,
	             한글인코딩 , 같은이름파일처리방법 );

	   m.getParameter("name") ;//text박스의 값 가져오기
	   m.getFilesystemName("file"); //file 박스의 첨부파일가져오기

	   File f = m.getFile("file") ;//첨부된 파일의 정보 
		*/
		String saveDir=request.getServletContext().getRealPath("/save");
		int maxSize=1024*1024*100; //100M
		String encoding="UTF-8"; 
		
		//new DefaultFileRenamePolicy() 객체는 동일한 파일이름이 업로드되면 첨부된 파일이름 옆에 숫자가 붙는다
		// 예) a.txt -> a1.txt -> a2.txt
		MultipartRequest m = new MultipartRequest(request, saveDir, maxSize, encoding , new DefaultFileRenamePolicy());
		
		//MultipartRequest이거 제대로 생성했으면 자동으로 save에 생성????
		
		
		//para가져옴
		String modelNum = m.getParameter("model_num");
		String modelName = m.getParameter("model_name");
		String price = m.getParameter("price");
		String description = m.getParameter("description");
		String password = m.getParameter("password");
		
		
		////////예외처리
		
/*		String fileSystemName = m.getFilesystemName("file");
		String originalFileName = m.getOriginalFileName("file");
		
		long fileSize = m.getFile("file").length(); //파일용량
		
		//다운로드를 할 수 있도록 save폴더에 있는 모든 목록을 저장한다
		String [] fileNames = new File(saveDir).list();*/
			
		//유효성 체크
		if(modelNum==null ||modelName==null|| price==null||description==null||password==null) {
			try { // 바로 url주소로 들어왔을때 알려주기 위해 (http://localhost:8000/ex12_MVCElecBoard/elec?command=insert)
				throw new SQLException("입력값이 충분하지 않습니다.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//description안에 태그를 문자로 변환! 문서를 <script>alert("sdf");라고 입력하면 html로 변환되서 실행됨
		description = description.replaceAll("<", "$lt");
		
		//dto에 저장
		Electronics electronics = new Electronics(modelNum, modelName, Integer.parseInt(price), description, password);
		
		if(m.getFilesystemName("file")!=null) { //파일첨부가 되었다면
			electronics.setfName(m.getFilesystemName("file"));
			electronics.setfSize((int)m.getFile("file").length());
		}
		
		
		ModelAndView mv = new ModelAndView();
		
		try {			
			int re = ElectronicsService.insert(electronics);
			if(re>0) { //등록이 완료되었다
				mv.setPath("elec?command=list"); //default가 list여서 ?command=list 안넣어줘도됨
				mv.setInRedirect(true); //redirect방식으로 이동				
			}			
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			mv.setPath("errorView/error.jsp");
		}		
		return mv;
	}
}
