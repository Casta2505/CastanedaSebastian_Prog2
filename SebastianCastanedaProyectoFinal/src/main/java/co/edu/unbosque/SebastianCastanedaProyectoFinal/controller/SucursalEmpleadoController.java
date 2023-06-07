package co.edu.unbosque.SebastianCastanedaProyectoFinal.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Empleado;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.InventarioCompra;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.InventarioVenta;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.SucursalEmpleado;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.EmpleadoRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.InventarioCompraRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.InventarioVentaRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.SucursalEmpleadoRepository;

@Controller
@RequestMapping
public class SucursalEmpleadoController {

	@Autowired
	private InventarioVentaRepository daoInventarioVentaRepository;

	@Autowired
	private InventarioCompraRepository daoInventarioCompraRepository;

	@Autowired
	private SucursalEmpleadoRepository daoSucursalEmpleado;

	@Autowired
	private EmpleadoRepository daoEmpleadoRepository;

	@GetMapping("/ListarEmpleadosCedula")
	public String getEmpleados(@RequestParam(required = false) Integer cedula, Model model) {
		if (cedula != null) {
			Empleado empleado = daoEmpleadoRepository.findAllByCedula(cedula);
			if (empleado != null) {
				List<SucursalEmpleado> all = daoSucursalEmpleado.findAllByIdEmpleado(empleado);
				model.addAttribute("empleados", all);
			} else {
				model.addAttribute("empleados", new ArrayList<>());
			}
		} else {
			List<SucursalEmpleado> all = daoSucursalEmpleado.findAll();
			model.addAttribute("empleados", all);
		}
		return "Personal";
	}

	@GetMapping("/ListarEmpleados")
	public String getEmpleados(Model model) {
		List<SucursalEmpleado> all = daoSucursalEmpleado.findAll();
		model.addAttribute("empleados", all);
		return "Personal";
	}
	@GetMapping("/generarPDF")
	public ResponseEntity<Resource> generarPDF() throws DocumentException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Document document = new Document();
		PdfWriter.getInstance(document, outputStream);
		document.open();
		List<InventarioVenta> ventas = daoInventarioVentaRepository.findAll();
		List<InventarioCompra> compras = daoInventarioCompraRepository.findAll();
		if (ventas.isEmpty() || compras.isEmpty()) {

			return null;
		} else {
			double totalVenta = 0.0;
			double totalCompra = 0.0;
			for (InventarioVenta iv : ventas) {
				double venta = iv.getCantidad() * iv.getPrecioUnitario();
				totalVenta = totalVenta + venta;
			}
			for (InventarioCompra ic : compras) {
				double compra = ic.getCantidad() * ic.getCostoUnitario();
				totalCompra = totalCompra + compra;
			}

			try {
				Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

				document.add(new Paragraph("Contabilidad La Rebaja", font));
				document.add(new Paragraph("Ventas: " + totalVenta, font));
				document.add(new Paragraph("Compras: " + totalCompra, font));
				if (totalCompra > totalVenta) {
					document.add(
							new Paragraph("La Rebaja Esta Teniendo Perdidas de " + (totalCompra - totalVenta), font));
				} else {
					document.add(new Paragraph("La Rebaja Tiene ganancias de " + (totalVenta - totalCompra), font));
				}
				document.close();
				byte[] pdfBytes = outputStream.toByteArray();

				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=GananciasOPerdidas.pdf");
				return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
						.contentLength(pdfBytes.length).body(new ByteArrayResource(pdfBytes));
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
