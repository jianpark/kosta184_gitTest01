

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
		
		 /*MultipartRequest m = new MultipartRequest(request , �������� , �ִ�뷮 ,
	             �ѱ����ڵ� , �����̸�����ó����� );

	   m.getParameter("name") ;//text�ڽ��� �� ��������
	   m.getFilesystemName("file"); //file �ڽ��� ÷�����ϰ�������

	   File f = m.getFile("file") ;//÷�ε� ������ ���� 
		*/
		String saveDir=request.getServletContext().getRealPath("/save");
		int maxSize=1024*1024*100; //100M
		String encoding="UTF-8"; 
		
		//new DefaultFileRenamePolicy() ��ü�� ������ �����̸��� ���ε�Ǹ� ÷�ε� �����̸� ���� ���ڰ� �ٴ´�
		// ��) a.txt -> a1.txt -> a2.txt
		MultipartRequest m = new MultipartRequest(request, saveDir, maxSize, encoding , new DefaultFileRenamePolicy());
		
		//MultipartRequest�̰� ����� ���������� �ڵ����� save�� ����????
		
		
		//para������
		String modelNum = m.getParameter("model_num");
		String modelName = m.getParameter("model_name");
		String price = m.getParameter("price");
		String description = m.getParameter("description");
		String password = m.getParameter("password");
		
		
		////////����ó��
		
/*		String fileSystemName = m.getFilesystemName("file");
		String originalFileName = m.getOriginalFileName("file");
		
		long fileSize = m.getFile("file").length(); //���Ͽ뷮
		
		//�ٿ�ε带 �� �� �ֵ��� save������ �ִ� ��� ����� �����Ѵ�
		String [] fileNames = new File(saveDir).list();*/
			
		//��ȿ�� üũ
		if(modelNum==null ||modelName==null|| price==null||description==null||password==null) {
			try { // �ٷ� url�ּҷ� �������� �˷��ֱ� ���� (http://localhost:8000/ex12_MVCElecBoard/elec?command=insert)
				throw new SQLException("�Է°��� ������� �ʽ��ϴ�.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//description�ȿ� �±׸� ���ڷ� ��ȯ! ������ <script>alert("sdf");��� �Է��ϸ� html�� ��ȯ�Ǽ� �����
		description = description.replaceAll("<", "$lt");
		
		//dto�� ����
		Electronics electronics = new Electronics(modelNum, modelName, Integer.parseInt(price), description, password);
		
		if(m.getFilesystemName("file")!=null) { //����÷�ΰ� �Ǿ��ٸ�
			electronics.setfName(m.getFilesystemName("file"));
			electronics.setfSize((int)m.getFile("file").length());
		}
		
		
		ModelAndView mv = new ModelAndView();
		
		try {			
			int re = ElectronicsService.insert(electronics);
			if(re>0) { //����� �Ϸ�Ǿ���
				mv.setPath("elec?command=list"); //default�� list���� ?command=list �ȳ־��൵��
				mv.setInRedirect(true); //redirect������� �̵�				
			}			
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", e.getMessage());
			mv.setPath("errorView/error.jsp");
		}		
		return mv;
	}
}
