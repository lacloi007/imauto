package tuanpv.imart.imauto.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

/**
 * For activating the formula inside the EXCEL file. And System can get the real
 * value like the display of EXCEL file.
 * 
 * @author TuanPV
 */
public class CellFormatter {
	private FormulaEvaluator evaluator;
	private DataFormatter formatter;

	public CellFormatter(FormulaEvaluator evaluator) {
		this.evaluator = evaluator;
		this.formatter = new DataFormatter();
	}

	public String formatCellValue(Cell cell) {
		return formatter.formatCellValue(cell, evaluator);
	}
}
