package tuanpv.imart.imauto;

import java.io.File;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;
import tuanpv.imart.imauto.utils.ExcelUtils;

public class IMTest {
	private static ApplicationContext context;

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.err.println("please input path of excel file");
			return;
		}

		String filePath = args[0];
		File file = new File(filePath);
		if (file.exists() && file.isFile() && file.canRead()) {

			// read data input from Excel file
			Workbook workbook = WorkbookFactory.create(file);
			Map<String, Object> data = ExcelUtils.readWorkbook(workbook);
			workbook.close();

			// initialize context
			context = new ClassPathXmlApplicationContext(Constant.FILE_APPLICATION_CONTEXT);

			// initialize data to IMConfig
			IMConfig config = (IMConfig) context.getBean("imConfig");

			try {

				// start read data
				config.start(data);

				// run default action
				Action imRun = (Action) context.getBean("imRun");
				imRun.execute(null, null);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				// close
				config.destroy();
			}
		}
	}
}
