package ar.edu.unlam.tallerweb1.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorOperacion {

	@RequestMapping(path = "/{nombreOperacion}/{cadena}")
	public ModelAndView Operaciones(@PathVariable String nombreOperacion, @PathVariable String cadena) {

		String cadenaResultado = null;

		switch (nombreOperacion) {
		case "pasar-a-mayuscula":
			cadenaResultado = cadena.toUpperCase();
			break;
		case "pasar-a-minuscula":
			cadenaResultado = cadena.toLowerCase();;
			break;
		case "invertir-orden":
			cadenaResultado = new StringBuffer(cadena).reverse().toString();
			break;
		case "cantidad-de-caracteres":
			Integer largoCadena= cadena.length();
			cadenaResultado = largoCadena.toString();
			break;

		default: return new ModelAndView("error");
		}

		ModelMap model = new ModelMap();
		model.put("nombreOperacion", nombreOperacion);
		model.put("cadena", cadena);
		model.put("resultado", cadenaResultado);
		
		return new ModelAndView("operaciones", model);
	}
	
	@RequestMapping(path = "/error")
		public ModelAndView errorOperaciones () {
		return new ModelAndView("error");
	}
	
}
