package tuanpv.imart.imauto.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtils {
	public static Map<String, Object> readWorkbook(Map<String, Object> data, Workbook book) {

		// evaluator workbook
		FormulaEvaluator evaluator = book.getCreationHelper().createFormulaEvaluator();

		// working
		CellFormatter dataFormatter = new CellFormatter(evaluator);

		for (Sheet sheet : book) {
			evaluator.evaluateAll();

			String shKey = sheet.getSheetName();
			if (shKey.equalsIgnoreCase("constant"))
				continue;

			// get data from sheet
			String type = dataFormatter.formatCellValue(sheet.getRow(0).getCell(1));
			int start = Integer.parseInt(dataFormatter.formatCellValue(sheet.getRow(1).getCell(1))) - 1;
			int check = Integer.parseInt(dataFormatter.formatCellValue(sheet.getRow(2).getCell(1))) - 1;

			// put value to data MAP
			Map<String, Object> shVal = new TreeMap<>();
			shVal.put("type", type);
			shVal.put("start", start);
			shVal.put("check", check);

			// process read
			Object shObj = null;
			if (type.equals("Map")) {
				shObj = sheet2Map(evaluator, sheet, start, check);
			}

			if (type.equals("Array")) {
				shObj = sheet2Array(evaluator, sheet, start, check);
			}

			if (type.equals("List")) {
				shObj = sheet2List(evaluator, sheet, start, check);
			}

			if (shObj != null) {
				shVal.put("object", shObj);
				data.put(shKey, shVal);
			}
		}

		return data;
	}

	public static String[] sheet2Array(FormulaEvaluator evaluator, Sheet sheet, int start, int check) {
		List<String> array = new ArrayList<>();

		// Create a DataFormatter to format and get each cell's value as String
		CellFormatter dataFormatter = new CellFormatter(evaluator);

		for (Row row : sheet) {
			if (row.getRowNum() < start) {
				continue;
			}

			String cellCheck = dataFormatter.formatCellValue(row.getCell(check));
			if (StringUtils.isNotEmpty(cellCheck)) {
				String value = dataFormatter.formatCellValue(row.getCell(check + 1));
				array.add(value);
			}
		}

		return array.toArray(new String[array.size()]);
	}

	public static Map<String, String> sheet2Map(FormulaEvaluator evaluator, Sheet sheet, int start, int check) {
		Map<String, String> map = new TreeMap<String, String>();

		// Create a DataFormatter to format and get each cell's value as String
		CellFormatter dataFormatter = new CellFormatter(evaluator);

		for (Row row : sheet) {
			if (row.getRowNum() < start) {
				continue;
			}

			String cellCheck = dataFormatter.formatCellValue(row.getCell(check));
			if (StringUtils.isNotEmpty(cellCheck)) {
				String key = dataFormatter.formatCellValue(row.getCell(check + 1));
				String value = dataFormatter.formatCellValue(row.getCell(check + 2));
				map.put(key, value);
			}
		}

		return map;
	}

	public static List<String[]> sheet2List(FormulaEvaluator evaluator, Sheet sheet, int start, int check) {
		List<String[]> list = new ArrayList<>();
		// Create a DataFormatter to format and get each cell's value as String
		CellFormatter dataFormatter = new CellFormatter(evaluator);

		for (Row row : sheet) {
			if (row.getRowNum() < start) {
				continue;
			}

			String cellCheck = dataFormatter.formatCellValue(row.getCell(check));
			if (StringUtils.isNotEmpty(cellCheck)) {
				List<String> strings = new ArrayList<>();
				for (Cell cell : row) {
					if (cell.getColumnIndex() <= check)
						continue;
					strings.add(dataFormatter.formatCellValue(cell));
				}

				list.add(strings.toArray(new String[strings.size()]));
			}
		}
		return list;
	}
}
