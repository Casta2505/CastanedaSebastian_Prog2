package co.edu.unbosque.SebastianCastanedaProyectoFinal.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Ciudad;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Compra;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Inventario;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.InventarioCompra;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Producto;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Proveedor;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Sucursal;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.CompraRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.InventarioCompraRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.InventarioRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.ProductoRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.ProveedorRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.SucursalRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping
public class InventarioVentaController {
	
	@Autowired
	private InventarioCompraRepository daoInventarioCompraRepository;

	@Autowired
	private CompraRepository daoCompraRepository;

	@Autowired
	private InventarioRepository daoInventarioRepository;

	@Autowired
	private ProveedorRepository daoProveedorRepository;

	@Autowired
	private SucursalRepository daoSucursalRepository;

	@Autowired
	private ProductoRepository daoProductoRepository;

	@GetMapping("/ListarInventarioPorSucursalVenta")
	public String mostrarInventarioPorSucursal(Model model) {
		List<Sucursal> sucursales = daoSucursalRepository.findAll();
		model.addAttribute("sucursal", sucursales);
		model.addAttribute("ciudadSeleccionada", new Ciudad());
		return "Venta";
	}

	@GetMapping("/MostrarInventarioVenta")
	public String mostrarInventario(@RequestParam("sucursalId") Integer sucursalId, Model model) {
		Optional<Sucursal> sucursalOptional = daoSucursalRepository.findById(sucursalId);
		if (sucursalOptional.isPresent()) {
			Sucursal sucursal = sucursalOptional.get();
			List<Inventario> inventario = daoInventarioRepository.findAllByIdSucursal(sucursal);
			model.addAttribute("inventarioList", inventario);
			model.addAttribute("sucursalSeleccionada", sucursal);
		}
		model.addAttribute("sucursales", daoSucursalRepository.findAll());
		return "Venta";
	}

	@GetMapping("/vender/{id}")
	public String addCompra(@PathVariable Integer id, Model model) {
		Optional<Inventario> inventarioop = daoInventarioRepository.findById(id);
		Inventario inventario = inventarioop.get();
		model.addAttribute("inventario", inventario);
		model.addAttribute("proveedores", daoProveedorRepository.findAll());
		return "FormularioVenta";
	}

	@GetMapping("/InventarioVenta")
	public String getPaginaCompra(Model model) {
		return "Venta";
	}

	@GetMapping("/venta")
	public String mostrarFormularioCompra(Model model) {
		List<Inventario> inventarios = daoInventarioRepository.findAll();
		List<Proveedor> proveedores = (List<Proveedor>) daoProveedorRepository.findAll();
		model.addAttribute("inventarios", inventarios);
		model.addAttribute("proveedores", proveedores);
		return "Venta";
	}
}
