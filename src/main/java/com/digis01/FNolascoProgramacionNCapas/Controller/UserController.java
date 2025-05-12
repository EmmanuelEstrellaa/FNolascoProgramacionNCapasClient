package com.digis01.FNolascoProgramacionNCapas.Controller;

import com.digis01.FNolascoProgramacionNCapas.ML.Colonia;
import com.digis01.FNolascoProgramacionNCapas.ML.Direccion;
import com.digis01.FNolascoProgramacionNCapas.ML.Estado;
import com.digis01.FNolascoProgramacionNCapas.ML.Municipio;
import com.digis01.FNolascoProgramacionNCapas.ML.Pais;
import com.digis01.FNolascoProgramacionNCapas.ML.Result;
import com.digis01.FNolascoProgramacionNCapas.ML.ResultFile;
import com.digis01.FNolascoProgramacionNCapas.ML.Roll;
import com.digis01.FNolascoProgramacionNCapas.ML.Usuario;
import com.digis01.FNolascoProgramacionNCapas.ML.UsuarioDireccion;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/Usuario")
public class UserController {

    String urlBase = "http://localhost:8081/";
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public String Index(Model model) {
        Result result = new Result();
        try {
            ResponseEntity<Result<UsuarioDireccion>> responseEntity = restTemplate.exchange("http://localhost:8081/usuarioapi",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
            });

            Result response = responseEntity.getBody();

            ResponseEntity<Result<List<Roll>>> responseRoll = restTemplate.exchange(urlBase + "rollapi",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<List<Roll>>>() {
            });

            Usuario usuarioBusqueda = new Usuario();
            usuarioBusqueda.Roll = new Roll();

            model.addAttribute("roles", responseRoll.getBody().object);
            model.addAttribute("listaUsuarios", response.objects);
            model.addAttribute("usuarioBusqueda", usuarioBusqueda);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;

        }

        return "UsuarioIndex";
    }

    @GetMapping("Form/{IdUsuario}")
    public String Form(@PathVariable int IdUsuario, Model model) {
        if (IdUsuario == 0) { // Agregar
            UsuarioDireccion usuarioDIreccion = new UsuarioDireccion();
            usuarioDIreccion.Usuario = new Usuario();
            usuarioDIreccion.Usuario.Roll = new Roll();
            usuarioDIreccion.Direccion = new Direccion();
            usuarioDIreccion.Direccion.Colonia = new Colonia();
            usuarioDIreccion.Direccion.Colonia.Municipio = new Municipio();
            usuarioDIreccion.Direccion.Colonia.Municipio.Estado = new Estado();
            usuarioDIreccion.Direccion.Colonia.Municipio.Estado.Pais = new Pais();

            ResponseEntity<Result<List<Roll>>> response = restTemplate.exchange(urlBase + "rollapi",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<List<Roll>>>() {
            });

            ResponseEntity<Result<List<Pais>>> responsePais = restTemplate.exchange(urlBase + "paisapi",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<List<Pais>>>() {
            });

            model.addAttribute("rolls", response.getBody().object);
            model.addAttribute("paises", responsePais.getBody().object);
            model.addAttribute("usuarioDireccion", usuarioDIreccion);
            return "UsuarioForm";
        } else { // Editar
            //Este es un ResponseEntity de un Result que recibe UsuarioDireccion, necesita algo más
            ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange(urlBase + "usuarioapi/getbyid/" + IdUsuario,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
            });

            model.addAttribute("usuarioDirecciones", response.getBody().object);
            return "UsuarioView";
        }

    }

    @GetMapping("Delete")
    public String Delete(@RequestParam int IdDireccion) {

        ResponseEntity<Result> responseEntity = restTemplate.exchange(urlBase + "usuarioapi/deleteDir/" + IdDireccion,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result>() {
        });
        Result result = responseEntity.getBody();
        return "redirect:/Usuario";
    }

    @GetMapping("DeleteUsuario/{IdUsuario}")
    public String DeleteUsuario(@PathVariable int IdUsuario) {
        try {

            ResponseEntity<Result> responseEntity = restTemplate.exchange(urlBase + "usuarioapi/delete/" + IdUsuario,
                    HttpMethod.DELETE,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result>() {
            });
            Result result = responseEntity.getBody();

        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return "redirect:/Usuario";
    }

    @GetMapping("/formEditable")
    public String FormEditable(Model model, @RequestParam int IdUsuario, @RequestParam(required = false) Integer IdDireccion) {
        if (IdDireccion == null) { //Editar Usuario
            Result result = new Result();
            try {

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Direccion = new Direccion();
                usuarioDireccion.Usuario.Roll = new Roll();
                usuarioDireccion.Usuario = new Usuario();

                ResponseEntity<Result<Usuario>> response = restTemplate.exchange(urlBase + "usuarioapi/getbyid/" + IdUsuario,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<Result<Usuario>>() {
                });
                usuarioDireccion.Usuario = (Usuario) response.getBody().object;

                ResponseEntity<Result<List<Roll>>> responseRoll = restTemplate.exchange(urlBase + "rollapi",
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<Result<List<Roll>>>() {
                });

                usuarioDireccion.Direccion.setIdDireccion(-1);
                model.addAttribute("usuarioDireccion", usuarioDireccion);
                model.addAttribute("rolls", responseRoll.getBody().object);

            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }

        } else if (IdDireccion == 0) { //Agregar dirección
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.setIdDireccion(0);

            ResponseEntity<Result<List<Pais>>> responsePais = restTemplate.exchange(urlBase + "paisapi",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<List<Pais>>>() {
            });

            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("paises", responsePais.getBody().object);

        } else { //Editar dirección
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.setIdDireccion(IdDireccion);
//            usuarioDireccion.Direccion = (Direccion) DireccionDAOImplementation.DireccionById(IdDireccion).object;
            model.addAttribute("usuarioDireccion", usuarioDireccion);
//            model.addAttribute("paises", PaisDAOImplementation.GetAll().correct ? PaisDAOImplementation.GetAll().objects : null);
//            model.addAttribute("paises", PaisDAOImplementation.GetAllJPA().correct ? PaisDAOImplementation.GetAllJPA().object : null);
//            model.addAttribute("estados", EstadoDAOImplementation.EstadoByIdPais(IdDireccion));
//            model.addAttribute("municipios", MunicipioDAOImplementation.MunicipioByIdEstado(IdDireccion));
//            model.addAttribute("colonia", ColoniaDAOImplementation.ColoniaByIdMunicipio(IdDireccion));

        }

        return "UsuarioForm";
    }

    @PostMapping("Form")
    public String Form(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion, BindingResult BindingResult, @RequestParam(required = false) MultipartFile imagenFile, Model model) {
//                usuarioDAOImplementation.Add(usuarioDireccion);
//
//        if(BindingResult.hasErrors()){
//            
//            model.addAttribute("usuarioDireccion", usuarioDireccion);
//            return "UsuarioForm";
//        }

        try {
            if (!imagenFile.isEmpty()) {
                byte[] bytes = imagenFile.getBytes();
                String imgBase64 = Base64.getEncoder().encodeToString(bytes);
                usuarioDireccion.Usuario.setImagen(imgBase64);
            }
        } catch (Exception ex) {

        }
        if (usuarioDireccion.Usuario.getIdUsuario() == 0) { //Agregar
            System.out.println("Estoy agregando un nuevo usuario y direccion");

            HttpEntity<UsuarioDireccion> entity = new HttpEntity<>(usuarioDireccion);
            restTemplate.exchange(urlBase + "/usuarioapi/Add",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<Result>() {
            });

        } else {
            if (usuarioDireccion.Direccion.getIdDireccion() == -1) { //Editar usuario
//                usuarioDAOImplementation.UsuarioUpdate(usuarioDireccion.Usuario);
//                usuarioDAOImplementation.UsuarioUpdateJPA(usuarioDireccion.Usuario);
                System.out.println("Estoy actualizando un usuario");
            } else if (usuarioDireccion.Direccion.getIdDireccion() == 0) { //Agregar direccion

                HttpEntity<UsuarioDireccion> entity = new HttpEntity(usuarioDireccion);
                restTemplate.exchange(urlBase + "usuarioapi/AddDireccion",
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<Result>() {
                });

                System.out.println("Estoy agregando direccion");
            } else { //Editar direccion
                //                usuarioDAOImplementation.DieccionUpdateJPA(usuarioDireccion);

//                HttpEntity<>
            }
        }

        return "redirect:/Usuario";
    }

    @GetMapping("/CargaMasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("/CargaMasiva")
    public String CargaMasiva(@RequestParam MultipartFile archivo, Model model, HttpSession session) {
        try {

            if (archivo != null && !archivo.isEmpty()) {

                //Body
                ByteArrayResource byteArrayResource = new ByteArrayResource(archivo.getBytes()) {
                    @Override
                    public String getFilename() {
                        return archivo.getOriginalFilename();
                    }
                };

                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("archivo", byteArrayResource);

                //Headers
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

                //Entidad de la peticion
                HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity(body, httpHeaders);

                ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                        urlBase + "/usuarioapi/CargaMasiva",
                        HttpMethod.POST,
                        httpEntity,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                });

                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    model.addAttribute("correcto", true);
                    session.setAttribute("urlFile", responseEntity.getBody().get("object"));

                } else {
                    if (responseEntity.getStatusCode().is4xxClientError()) {
                        model.addAttribute("listaErrores", (String) responseEntity.getBody().get("objects"));
                    }
                }

            }
        } catch (Exception ex) {
            return "redirect:/Usuario/CargaMasiva";
        }
        return "CargaMasiva";
    }

    @GetMapping("CargaMasiva/Procesar")
    public String Procesar(HttpSession session) {

        String absolutePath = session.getAttribute("urlFile").toString();

        ResponseEntity<Result> responseEntity = restTemplate.exchange(
                urlBase + "usuarioapi/CargaMasiva/Procesar",
                HttpMethod.POST,
                new HttpEntity<>(absolutePath),
                new ParameterizedTypeReference<Result>() {
        });

        if (responseEntity.getBody().correct) {

        }

        if (responseEntity.getStatusCode().equals(200)) {

        }

        return "/CargaMasiva";
    }

    @PostMapping("/GetAllDinamico")
    public String BusquedaDinamica(@ModelAttribute Usuario usuario, Model model) {

        ResponseEntity<Result<List<Roll>>> response = restTemplate.exchange(urlBase + "rollapi",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<List<Roll>>>() {
        });

        HttpEntity<Usuario> entity = new HttpEntity<>(usuario);
        restTemplate.exchange(urlBase + "usuarioapi/busquedaDinamica",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Result>() {
        });

        ResponseEntity<Result<List<Usuario>>> responseUsuarios = restTemplate.exchange(urlBase + "usuarioapi/busquedaDinamica",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<List<Usuario>>>() {
        });

        model.addAttribute("roles", response.getBody().object);
        model.addAttribute("usuarioBusqueda", usuario);
        model.addAttribute("listaUsuarios", responseUsuarios.getBody().objects);

        return "AlumnoIndex";
    }
}
