package tuanpv.imart.imauto;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tuanpv.imart.imauto.spring.system.IMConfig;
import tuanpv.imart.imauto.spring.system.IMRun;
import tuanpv.imart.imauto.utils.ExcelUtils;

public class IMTest {
	private static ApplicationContext context;

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.err.println("please input path of excel file");
			return;
		}

		// read data from EXCEL
		Map<String, Object> data = new TreeMap<String, Object>();
		for (String filePath : args) {
			File file = new File(filePath);
			if (file.exists() && file.isFile() && file.canRead()) {

				// read data input from Excel file
				Workbook workbook = WorkbookFactory.create(file);
				data = ExcelUtils.readWorkbook(data, workbook);
				workbook.close();
			}
		}

		// initialize context
		context = new ClassPathXmlApplicationContext(Constant.FILE_APPLICATION_CONTEXT);

		// initialize data to IMConfig
		IMConfig config = context.getBean(IMConfig.class);

		try {

			// start read data
			config.start(data);

			// run default action
			IMRun imRun = context.getBean(IMRun.class);
			imRun.execute(null, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {

			// close
			config.destroy();
		}
	}
}
