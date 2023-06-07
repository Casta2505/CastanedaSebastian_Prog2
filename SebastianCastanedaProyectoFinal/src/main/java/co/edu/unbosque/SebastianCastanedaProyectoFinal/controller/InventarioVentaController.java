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
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Empleado;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Inventario;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.InventarioVenta;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Proveedor;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Sucursal;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Venta;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.EmpleadoRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.InventarioRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.InventarioVentaRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.ProveedorRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.SucursalRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.VentaRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping
public class InventarioVentaController {

	@Autowired
	private InventarioRepository daoInventarioRepository;

	@Autowired
	private ProveedorRepository daoProveedorRepository;

	@Autowired
	private SucursalRepository daoSucursalRepository;
	
	@Autowired
	private EmpleadoRepository daoEmpleadoRepository;
	
	@Autowired
	private VentaRepository daoVentaRepository;
	
	@Autowired
	private InventarioVentaRepository daoInventarioVentaRepository;
	
	@PostMapping("guardarVenta")
	public String guardarVenta(Model model,@Valid @ModelAttribute("inventario") Inventario inventario,@RequestParam Integer personaid) {
		try {
			Optional<Empleado> personaop = daoEmpleadoRepository.findById(personaid);
			Optional<Inventario> inventarioop = daoInventarioRepository.findById(inventario.getId());
			if(inventarioop.isPresent()) {
				Inventario inv = inventarioop.get();
				if((inv.getCantidad()-inventario.getCantidad())==0) {
					daoInventarioRepository.delete(inv);
					return "Inicio";
				}else if((inv.getCantidad()-inventario.getCantidad())<0) {
					return "Inicio";
				}else {
					inv.setCantidad((inv.getCantidad()-inventario.getCantidad()));
					daoInventarioRepository.save(inv);
				}
				
				Venta venta = new Venta();
				venta.setFecha(new Date());
				venta.setIdEmpleado(personaop.get());
				
				InventarioVenta inventarioVenta = new InventarioVenta();
				inventarioVenta.setCantidad(inventario.getCantidad());
				inventarioVenta.setIdInventario(inventarioop.get());
				inventarioVenta.setPrecioUnitario(inventarioop.get().getPrecioUnitario());
				inventarioVenta.setIdVenta(venta);
				daoVentaRepository.save(venta);
				daoInventarioVentaRepository.save(inventarioVenta);
			}else {
				return "Inicio";
			}
		} catch (Exception e) {
			return "Inicio";
		}
		return "Inicio";
	}
	@GetMapping("/ListarInventarioPorSucursalVenta")
	public String mostrarInventarioPorSucursal(Model model) {
		List<Sucursal> sucursales = daoSucursalRepository.findAll();
		model.addAttribute("sucursalVenta", sucursales);
		model.addAttribute("ciudadSeleccionada", new Ciudad());
		return "Venta";
	}

	@GetMapping("/MostrarInventarioVenta")
	public String mostrarInventario(@RequestParam("sucursalV") Integer sucursalId, Model model) {
		Optional<Sucursal> sucursalOptional = daoSucursalRepository.findById(sucursalId);
		if (sucursalOptional.isPresent()) {
			Sucursal sucursal = sucursalOptional.get();
			List<Inventario> inventario = daoInventarioRepository.findAllByIdSucursal(sucursal);
			model.addAttribute("inventarioListVenta", inventario);
			model.addAttribute("sucursalSeleccionada", sucursal);
		}
		model.addAttribute("sucursalVenta", daoSucursalRepository.findAll());
		return "Venta";
	}

	@GetMapping("/vender/{id}")
	public String addCompra(@PathVariable Integer id, Model model) {
		Optional<Inventario> inventarioop = daoInventarioRepository.findById(id);
		Inventario inventario = inventarioop.get();
		model.addAttribute("inventario", inventario);
		model.addAttribute("empleados", daoEmpleadoRepository.findAll());
		return "FormularioVenta";
	}

	@GetMapping("/InventarioVenta")
	public String getPaginaCompra(Model model) {
		model.addAttribute("sucursalVenta",daoSucursalRepository.findAll());
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
