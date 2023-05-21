package com.dam.springboot.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dam.springboot.backend.dto.UsuarioDTO;
import com.dam.springboot.backend.entities.Usuario;
import com.dam.springboot.backend.mapper.UsuarioMapper;
import com.dam.springboot.backend.security.JwtTokenUtil;
import com.dam.springboot.backend.security.UserDetailsServiceImpl;
import com.dam.springboot.backend.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = {"http://localhost:4200"})
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioMapper usuarioMapper;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@GetMapping
	public List<UsuarioDTO> getUsuarioList() {
		return usuarioMapper.changeListToDTO(usuarioService.findAll());
	}

	@GetMapping("/{id}")
	public UsuarioDTO getUsuarioById(@PathVariable("id") Integer id) {
		return usuarioMapper.toUsuarioDTO(usuarioService.findById(id).get());
	}

	@GetMapping("/email")
	public UsuarioDTO getUsuarioByEmail(@RequestParam String email) {
		return usuarioMapper.toUsuarioDTO(usuarioService.findOneByEmail(email).get());
	}

	@PostMapping("/registro")
	public ResponseEntity<?> addUsuario(@RequestBody UsuarioDTO usuarioDTO, BindingResult result) {
		Map<String, Object> responseMap = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			responseMap.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);

		}
		try {
			Optional<Usuario> usuarioExistente = usuarioService.findOneByEmail(usuarioDTO.getEmail());
			if (usuarioExistente.isPresent()) {
				responseMap.put("mensaje", "El usuario ya está registrado");
				return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);
			}
			String hashedPassword = BCrypt.hashpw(usuarioDTO.getPassword(), BCrypt.gensalt());
			Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
			usuario.setPassword(hashedPassword);
			usuarioService.save(usuario);

			responseMap.put("mensaje", "El usuario ha sido añadido correctamente");
			responseMap.put("usuario", usuarioMapper.toUsuarioDTO(usuario));
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.CREATED);
		} catch (Exception e) {
			responseMap.put("mensaje", "Error al añadir en la base de datos");
			responseMap.put("error",
					e.getMessage().concat(": " + ((NestedRuntimeException) e).getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Integer id,
			@RequestBody UsuarioDTO usuarioActualizadoDTO) {
		Optional<Usuario> usuarioExistente = usuarioService.findById(id);
		if (usuarioExistente.isPresent()) {
			Usuario usuarioExistenteObj = usuarioExistente.get();
			usuarioExistenteObj.setNombre(usuarioActualizadoDTO.getNombre());
			usuarioExistenteObj.setApellidos(usuarioActualizadoDTO.getApellidos());
			usuarioExistenteObj.setEmail(usuarioActualizadoDTO.getEmail());
			usuarioService.save(usuarioExistenteObj);
			return ResponseEntity.ok(usuarioMapper.toUsuarioDTO(usuarioExistenteObj));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) {
		Optional<Usuario> usuarioOptional = usuarioService.findOneByEmail(usuarioDTO.getEmail());
		if (usuarioOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			if (BCrypt.checkpw(usuarioDTO.getPassword(), usuario.getPassword())) {
				try {
					final UserDetails userDetails = userDetailsService.loadUserByUsername(usuarioDTO.getEmail());
					String token;
					token = jwtTokenUtil.generateToken(userDetails);
					return ResponseEntity.ok(token);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
		Optional<Usuario> usuario = usuarioService.findById(id);
		if (usuario.isPresent()) {
			usuarioService.delete(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
