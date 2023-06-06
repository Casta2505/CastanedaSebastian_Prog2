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
public class InventarioCompraController {
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

	@GetMapping("/ListarInventarioPorSucursalCompra")
	public String mostrarInventarioPorSucursal(Model model) {
		List<Sucursal> sucursales = daoSucursalRepository.findAll();
		model.addAttribute("sucursales", sucursales);
		model.addAttribute("ciudadSeleccionada", new Ciudad());
		return "Compra";
	}

	@GetMapping("/MostrarInventarioCompra")
	public String mostrarInventario(@RequestParam("sucursalId") Integer sucursalId, Model model) {
		Optional<Sucursal> sucursalOptional = daoSucursalRepository.findById(sucursalId);
		if (sucursalOptional.isPresent()) {
			Sucursal sucursal = sucursalOptional.get();
			List<Inventario> inventario = daoInventarioRepository.findAllByIdSucursal(sucursal);
			model.addAttribute("inventarioList", inventario);
			model.addAttribute("sucursalSeleccionada", sucursal);
		}
		model.addAttribute("sucursales", daoSucursalRepository.findAll());
		model.addAttribute("ciudadSeleccionada", new Ciudad());
		return "Compra";
	}

	@GetMapping("/comprar/{id}")
	public String addCompra(@PathVariable Integer id, Model model) {
		Optional<Inventario> inventarioop = daoInventarioRepository.findById(id);
		Inventario inventario = inventarioop.get();
		model.addAttribute("inventario", inventario);
		model.addAttribute("proveedores", daoProveedorRepository.findAll());
		return "FormularioCompra";
	}

	@GetMapping("/agregarProducto")
	public String agregarProducto(Model model) {
		model.addAttribute("ListaProductos", daoProductoRepository.findAll());
		model.addAttribute("ListaSucursales", daoSucursalRepository.findAll());
		model.addAttribute("ListaProveedores", daoProveedorRepository.findAll());
		return "FormularioCompraNuevo";
	}

	@PostMapping("/guardarCompraNueva")
	public String saveCompra(@RequestParam("cantidad") Integer cantidad,
			@RequestParam("costo_unitario") Double costoUnitario, @RequestParam("proveedor") Integer proveedorId,
			@RequestParam("producto") Integer productoId, @RequestParam("sucursal") Integer sucursalId, Model model) {
		try {
			Inventario inventario = new Inventario();
			inventario.setCantidad(cantidad);
			inventario.setCostoUnitario(costoUnitario);

			Optional<Producto> productoop = daoProductoRepository.findById(productoId);
			Producto producto = productoop.get();
			inventario.setIdProducto(producto);

			Optional<Sucursal> sucursalop = daoSucursalRepository.findById(sucursalId);
			Sucursal sucursal = sucursalop.get();
			inventario.setIdSucursal(sucursal);

			inventario.setPrecioUnitario(costoUnitario * 1.3);

			daoInventarioRepository.save(inventario);
			Inventario inventarioGuardado = daoInventarioRepository
					.findAllByIdSucursalAndIdProductoAndCantidad(sucursal, producto, cantidad);

			InventarioCompra inventarioCompra = new InventarioCompra();
			inventarioCompra.setCantidad(cantidad);
			inventarioCompra.setCostoUnitario(costoUnitario);

			Compra compra = new Compra();
			compra.setFecha(new Date());
			Optional<Proveedor> proveedorop = daoProveedorRepository.findById(proveedorId);
			Proveedor proveedor = proveedorop.get();
			compra.setIdProveedor(proveedor);
			inventarioCompra.setIdCompra(compra);
			daoCompraRepository.save(compra);

			inventarioCompra.setIdInventario(inventarioGuardado);

			daoInventarioCompraRepository.save(inventarioCompra);
		} catch (Exception e) {
			return "Inicio";
		}

		return "Inicio";
	}

	@PostMapping("/guardarCompra")
	public String guardarCompra(@Valid @ModelAttribute("inventario") Inventario inventario,
			@RequestParam Integer proveedorId, Model model) {
		Optional<Proveedor> proveedorop = daoProveedorRepository.findById(proveedorId);
		Optional<Inventario> inventarioop = daoInventarioRepository.findById(inventario.getId());
		if (proveedorop.isPresent()) {
			Proveedor proveedor = proveedorop.get();
			InventarioCompra inventarioCompra = new InventarioCompra();

			Compra compra = new Compra();
			compra.setFecha(new Date());
			compra.setIdProveedor(proveedor);

			Inventario inventarioaux = inventarioop.get();
			inventarioaux.setCantidad(inventarioaux.getCantidad() + inventario.getCantidad());
			inventarioaux.setCostoUnitario(inventario.getCostoUnitario());
			daoInventarioRepository.save(inventarioaux);

			inventarioCompra.setCantidad(inventario.getCantidad());
			inventarioCompra.setCostoUnitario(inventario.getCostoUnitario());
			inventarioCompra.setIdCompra(compra);
			inventarioCompra.setIdInventario(inventario);

			daoCompraRepository.save(compra);
			daoInventarioCompraRepository.save(inventarioCompra);
			return "Inicio";
		}
		return "Inicio";
	}

	@GetMapping("/InventarioCompra")
	public String getPaginaCompra(Model model) {
		model.addAttribute("sucursales",daoSucursalRepository.findAll());
		return "Compra";
	}

	@GetMapping("/compra")
	public String mostrarFormularioCompra(Model model) {
		List<Inventario> inventarios = daoInventarioRepository.findAll();
		List<Proveedor> proveedores = (List<Proveedor>) daoProveedorRepository.findAll();
		model.addAttribute("inventarios", inventarios);
		model.addAttribute("proveedores", proveedores);
		return "Compra";
	}
}
