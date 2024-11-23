package com.fitnessbet.product.controller;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnessbet.product.model.dto.DateFilter;
import com.fitnessbet.product.model.dto.Product;
import com.fitnessbet.product.model.service.ProductService;
import com.fitnessbet.user.model.dto.PointHistory;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ProductController {
	
	private final ProductService service;
	
	public ProductController(ProductService service) {
		this.service = service;
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllProduct(){
		List<Product> list =  service.getAllProduct();
		if(list.size()>0 && list != null) {
			return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
			
		}
		return new ResponseEntity<String>("상품이 존재하지 않습니다.", HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("")
	public ResponseEntity<String> registProduct(@RequestBody Product product){
		if(service.registProduct(product)) {
			return new ResponseEntity<String>("등록 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("등록 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("")
	public ResponseEntity<?> modifyProduct(@RequestBody Product product){
		if(service.modifyProduct(product)) {
			return new ResponseEntity<String>("수정 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/change")
	public ResponseEntity<?> exchangeProduct(@RequestBody PointHistory info){
		if(service.exchangeProduct(info)) {
			return new ResponseEntity<String>("교환 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("교환 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/exchange/history")
	public ResponseEntity<?> getAllExchangList(@ModelAttribute DateFilter date){
		List<PointHistory> list =  service.getAllExchangList(date);
		if(list.size()>0 && list != null) {
			return new ResponseEntity<List<PointHistory>>(list, HttpStatus.OK);
			
		}
		return new ResponseEntity<String>("교환 목록이 존재하지 않습니다.", HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/download")
	public void download(HttpServletResponse res, @ModelAttribute DateFilter date) throws Exception {
	    Workbook workbook = new XSSFWorkbook();
	    try {
	        // Excel Sheet 생성 및 데이터 작성
	        Sheet sheet = workbook.createSheet("상품 교환 내역");
	        sheet.setDefaultColumnWidth(28);

	        // 스타일, 헤더 생성 (생략 가능)
	        XSSFCellStyle headerStyle = (XSSFCellStyle) workbook.createCellStyle();
	        headerStyle.setBorderLeft(BorderStyle.THIN);
	        headerStyle.setBorderRight(BorderStyle.THIN);
	        headerStyle.setBorderTop(BorderStyle.THIN);
	        headerStyle.setBorderBottom(BorderStyle.THIN);
	        headerStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 159, (byte) 158, (byte) 158}));
	        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	        String[] headers = {"신청일", "신청 품목", "캠퍼스", "반", "이름", "연락처"};
	        Row headerRow = sheet.createRow(0);
	        for (int i = 0; i < headers.length; i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(headers[i]);
	            cell.setCellStyle(headerStyle);
	        }

	        // Body 데이터 추가
	        List<PointHistory> list = service.getAllExchangList(date);
	       
	        
	        String[] idx = {"recordDate", "ProductName", "userInfo.campus", "userInfo.classNum", "userInfo.name", "userInfo.phone"};

	        int rowCount = 1;
	        for (int k = 0; k < list.size(); k++) {
	            Row row = sheet.createRow(rowCount++);
	            for (int i = 0; i < idx.length; i++) {
	                Cell cell = row.createCell(i);
	                Object value = getFieldValue(list.get(k), idx[i].split("\\."));
	                cell.setCellValue(value != null ? value.toString() : "");
	            }
	        }

	        // 다운로드 설정
	        String fileName = "exchange_list.xlsx";
	        res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	        res.setHeader("Pragma", "no-cache");
	        res.setDateHeader("Expires", 0);

	        // 파일 전송
	        ServletOutputStream outputStream = res.getOutputStream();
	        workbook.write(outputStream);
	        outputStream.flush();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        workbook.close();
	    }
	}

	
	private Object getFieldValue(Object obj, String[] fields) {
	    try {
	        Object current = obj;
	        for (String field : fields) {
	            if (current == null) {
	                return null; // 중간 객체가 null이면 null 반환
	            }
	            Field declaredField = current.getClass().getDeclaredField(field);
	            declaredField.setAccessible(true); // private 필드 접근 허용
	            current = declaredField.get(current); // 필드 값 갱신
	        }
	        return current;
	    } catch (Exception e) {
	        e.printStackTrace(); // 디버그용 출력
	        return null; // 예외 발생 시 null 반환
	    }
	}

}
