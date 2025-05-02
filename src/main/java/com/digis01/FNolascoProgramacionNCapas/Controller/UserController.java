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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping
    public String Index(Model model) {
        Result result = new Result();
        try {
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<Result> responseEntity = restTemplate.exchange("http://localhost:8081/usuarioapi",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result>() {
            });

            Result response = responseEntity.getBody();

            model.addAttribute("listaUsuarios", response.objects);
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;

        }

////        model.addAttribute("listaUsuarios", result.objects);
//        model.addAttribute("rolls", resultRollJPA.object);
        return "UsuarioIndex";
    }

//    @GetMapping("Form/{IdUsuario}")
//    public String Form(@PathVariable int IdUsuario, Model model) {
//        if (IdUsuario == 0) { // Agregar
//            UsuarioDireccion usuarioDIreccion = new UsuarioDireccion();
//            usuarioDIreccion.Usuario = new Usuario();
//            usuarioDIreccion.Usuario.Roll = new Roll();
//            usuarioDIreccion.Direccion = new Direccion();
//            usuarioDIreccion.Direccion.Colonia = new Colonia();
//            usuarioDIreccion.Direccion.Colonia.Municipio = new Municipio();
//            usuarioDIreccion.Direccion.Colonia.Municipio.Estado = new Estado();
//            usuarioDIreccion.Direccion.Colonia.Municipio.Estado.Pais = new Pais();
//
////            model.addAttribute("rolls", RollDAOImplementation.GetAll().object);
//            model.addAttribute("rolls", RollDAOImplementation.GetAllJPA().object);
////            model.addAttribute("paises", PaisDAOImplementation.GetAll().correct ? PaisDAOImplementation.GetAll().objects : null);
//            model.addAttribute("paises", PaisDAOImplementation.GetAllJPA().correct ? PaisDAOImplementation.GetAllJPA().object : null);
//            model.addAttribute("usuarioDireccion", usuarioDIreccion);
//            return "UsuarioForm";
//        } else { // Editar
//            System.out.println("Voy a editar");
//            Result result = usuarioDAOImplementation.UsuarioDireccionesById(IdUsuario);
//            model.addAttribute("usuarioDirecciones", result.object);
//            return "UsuarioView";
//        }
//
//    }
//
//    @GetMapping("Delete")
//    public String Delete(@RequestParam int IdDireccion) {
//            usuarioDAOImplementation.DireccionDeleteJPA(IdDireccion);
//        return "redirect:/Usuario";
//    }
//    
//    @GetMapping("DeleteUsuario/{IdUsuario}")
//    public String DeleteUsuario(@PathVariable int IdUsuario) {
//        Result result = usuarioDAOImplementation.DeleteUsuarioDireccionJPA(IdUsuario);
//        return "redirect:/Usuario";
//    }
//
//    @GetMapping("/formEditable")
//    public String FormEditable(Model model, @RequestParam int IdUsuario, @RequestParam(required = false) Integer IdDireccion) {
//
//        if (IdDireccion == null) { //Editar Alumno
//            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
//            usuarioDireccion = (UsuarioDireccion) usuarioDAOImplementation.UsuarioDireccionesById(IdUsuario).object;
//            usuarioDireccion.Direccion = new Direccion();
//            usuarioDireccion.Direccion.setIdDireccion(-1);
//            model.addAttribute("usuarioDireccion", usuarioDireccion);
////            model.addAttribute("rolls", RollDAOImplementation.GetAll().object);
//            model.addAttribute("rolls", RollDAOImplementation.GetAllJPA().object);
//        } else if (IdDireccion == 0) { //Agregar dirección
//            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
//            usuarioDireccion.Usuario = new Usuario();
//            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
//            usuarioDireccion.Direccion = new Direccion();
//            usuarioDireccion.Direccion.setIdDireccion(0);
//            model.addAttribute("usuarioDireccion", usuarioDireccion);
////            model.addAttribute("paises", PaisDAOImplementation.GetAll().correct ? PaisDAOImplementation.GetAll().objects : null);
//            model.addAttribute("paises", PaisDAOImplementation.GetAllJPA().correct ? PaisDAOImplementation.GetAllJPA().object : null);
//        } else { //Editar dirección
//            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
//            usuarioDireccion.Usuario = new Usuario();
//            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
//            usuarioDireccion.Direccion = new Direccion();
//            usuarioDireccion.Direccion.setIdDireccion(IdDireccion);
//            usuarioDireccion.Direccion = (Direccion) DireccionDAOImplementation.DireccionById(IdDireccion).object;
//            model.addAttribute("usuarioDireccion", usuarioDireccion);
////            model.addAttribute("paises", PaisDAOImplementation.GetAll().correct ? PaisDAOImplementation.GetAll().objects : null);
//            model.addAttribute("paises", PaisDAOImplementation.GetAllJPA().correct ? PaisDAOImplementation.GetAllJPA().object : null);
//            model.addAttribute("estados", EstadoDAOImplementation.EstadoByIdPais(IdDireccion));
//            model.addAttribute("municipios", MunicipioDAOImplementation.MunicipioByIdEstado(IdDireccion));
//            model.addAttribute("colonia", ColoniaDAOImplementation.ColoniaByIdMunicipio(IdDireccion));
//
//        }
//
//        return "UsuarioForm";
//    }
//
//    @PostMapping("Form")
//    public String Form(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion, BindingResult BindingResult, @RequestParam(required = false) MultipartFile imagenFile, Model model) {
////                usuarioDAOImplementation.Add(usuarioDireccion);
////
////        if(BindingResult.hasErrors()){
////            
////            model.addAttribute("usuarioDireccion", usuarioDireccion);
////            return "UsuarioForm";
////        }
//
//        try {
//            if (!imagenFile.isEmpty()) {
//                byte[] bytes = imagenFile.getBytes();
//                String imgBase64 = Base64.getEncoder().encodeToString(bytes);
//                usuarioDireccion.Usuario.setImagen(imgBase64);
//            }
//        } catch (Exception ex) {
//
//        }
//        if (usuarioDireccion.Usuario.getIdUsuario() == 0) { //Agregar
//            System.out.println("Estoy agregando un nuevo usuario y direccion");
////            usuarioDAOImplementation.Add(usuarioDireccion);
//            usuarioDAOImplementation.AddJPA(usuarioDireccion);
//        } else {
//            if (usuarioDireccion.Direccion.getIdDireccion() == -1) { //Editar usuario
////                usuarioDAOImplementation.UsuarioUpdate(usuarioDireccion.Usuario);
//                usuarioDAOImplementation.UsuarioUpdateJPA(usuarioDireccion.Usuario);
//                System.out.println("Estoy actualizando un usuario");
//            } else if (usuarioDireccion.Direccion.getIdDireccion() == 0) { //Agregar direccion
////                usuarioDAOImplementation.UsuarioADDdireccion(usuarioDireccion);
//                usuarioDAOImplementation.AddDireccionJPA(usuarioDireccion);
//                System.out.println("Estoy agregando direccion");
//            } else { //Editar direccion
//                //usuarioDAOImplementation.DireccionUpdate(usuarioDireccion);
//                usuarioDAOImplementation.DieccionUpdateJPA(usuarioDireccion);
//                System.out.println("Estoy actualizando direccion");
//            }
//        }
//
//        return "redirect:/Usuario";
//    }
//
//    @GetMapping("EstadoByIdPais/{IdPais}")
//    @ResponseBody
//    public Result EstadoByIdPais(@PathVariable int IdPais) {
//        Result result = EstadoDAOImplementation.EstadoByIdPais(IdPais);
//
//        return result;
//    }
//
//    @GetMapping("MunicipioByIdEstado/{IdEstado}")
//    @ResponseBody
//    public Result MunicipioByIdEstado(@PathVariable int IdEstado) {
//        Result result = MunicipioDAOImplementation.MunicipioByIdEstado(IdEstado);
//
//        return result;
//    }
//
//    @GetMapping("ColoniaByIdMunicipio/{IdMunicipio}")
//    @ResponseBody
//    public Result ColoniaByIdMunicipio(@PathVariable int IdMunicipio) {
//        Result result = ColoniaDAOImplementation.ColoniaByIdMunicipio(IdMunicipio);
//
//        return result;
//    }
//
//    @GetMapping("ColoniaByCP/{CodigoPostal}")
//    @ResponseBody
//    public Result ColoniaByCP(@PathVariable int CodigoPostal) {
//        Result result = ColoniaDAOImplementation.ColoniaByCP(CodigoPostal);
//
//        return result;
//    }
//
//    @GetMapping("/CargaMasiva")
//    public String CargaMasiva() {
//        return "CargaMasiva";
//    }
//
//    @PostMapping("/CargaMasiva")
//    public String CargaMasiva(@RequestParam MultipartFile archivo, Model model, HttpSession session) {
//        try {
//            //Guardarlo en un punto del sistema
//            if (archivo != null && !archivo.isEmpty()) { //El archivo no sea nulo ni este vacio
//                String tipoArchivo = archivo.getOriginalFilename().split("\\.")[1];
//
//                String root = System.getProperty("user.dir");
//                String path = "src/main/resources/static/archivos";
//                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
//                String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();
//                archivo.transferTo(new File(absolutePath));
//
//                //Leer el archivo
//                List<UsuarioDireccion> listaUsuarios = new ArrayList();
//                if (tipoArchivo.equals("txt")) {
//                    listaUsuarios = LecturaArchivoTXT(new File(absolutePath)); //método para leer la lista
//                } else {
//                    listaUsuarios = LecturaArchivoExcel(new File(absolutePath));
//                }
//
//                //Validar el archivo
//                List<ResultFile> listaErrores = ValidarArchivo(listaUsuarios);
//
//                if (listaErrores.isEmpty()) {
//                    //Proceso mi archivo
//                    session.setAttribute("urlFile", absolutePath);
//                    model.addAttribute("listaErrores", listaErrores);
//                } else {
//                    //Mando mis errores
//                    model.addAttribute("listaErrores", listaErrores);
//                }
//            }
//
//        } catch (Exception ex) {
//            return "redirect:/Usuario/CargaMasiva";
//        }
//        return "CargaMasiva";
//    }
//
//    public List<UsuarioDireccion> LecturaArchivoTXT(File archivo) {
//        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();
//
//        try (FileReader fileReader = new FileReader(archivo); BufferedReader bufferedReader = new BufferedReader(fileReader);) {
//
//            String linea;
//
//            while ((linea = bufferedReader.readLine()) != null) {
//                String[] campos = linea.split("\\|");
//
//                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
//                usuarioDireccion.Usuario = new Usuario();
//                usuarioDireccion.Usuario.setNombre(campos[0]);
//                usuarioDireccion.Usuario.setApellidoPaterno(campos[1]);
//                usuarioDireccion.Usuario.setApellidoMaterno(campos[2]);
//                usuarioDireccion.Usuario.setUserName(campos[3]);
//                usuarioDireccion.Usuario.setEmail(campos[4]);
//                usuarioDireccion.Usuario.setSexo(campos[5]);
//                usuarioDireccion.Usuario.setTelefono(campos[6]);
//                usuarioDireccion.Usuario.setCelular(campos[7]);
//                usuarioDireccion.Usuario.setCurp(campos[8]);
//                usuarioDireccion.Usuario.setPassword(campos[9]);
//                //Darle formato a la fecha de nacimiento
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //Dar formato a la fecha
//                usuarioDireccion.Usuario.setFechaNacimiento(formatter.parse(campos[10]));
////                usuarioDireccion.Usuario.setStatus(Integer.parseInt(campos[6]));
//                usuarioDireccion.Usuario.setImagen(null);
//                usuarioDireccion.Usuario.Roll = new Roll();
//                usuarioDireccion.Usuario.Roll.setIdRoll(Integer.parseInt(campos[11]));
//
//                usuarioDireccion.Direccion = new Direccion();
//                usuarioDireccion.Direccion.setCalle(campos[12]);
//                usuarioDireccion.Direccion.setNumeroExterior(campos[13]);
//                usuarioDireccion.Direccion.setNumeroInterior(campos[14]);
//
//                usuarioDireccion.Direccion.Colonia = new Colonia();
//                usuarioDireccion.Direccion.Colonia.setIdColonia(Integer.parseInt(campos[15]));
//
//                listaUsuarios.add(usuarioDireccion);
//            }
//
//        } catch (Exception ex) {
//            listaUsuarios = null;
//        }
//
//        return listaUsuarios;
//    }
//
//    public List<UsuarioDireccion> LecturaArchivoExcel(File archivo) {
//        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();
//        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
//            for (Sheet sheet : workbook) {
//
//                for (Row row : sheet) {
//
//                    UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
//                    usuarioDireccion.Usuario = new Usuario();
//                    usuarioDireccion.Usuario.setNombre(row.getCell(0).toString());
//                    usuarioDireccion.Usuario.setApellidoPaterno(row.getCell(1).toString());
//                    usuarioDireccion.Usuario.setApellidoMaterno(row.getCell(2).toString());
//                    usuarioDireccion.Usuario.setEmail(row.getCell(3).toString());
//                    usuarioDireccion.Usuario.setSexo(row.getCell(4).toString());
//                    usuarioDireccion.Usuario.setTelefono(row.getCell(5).toString());
//                    usuarioDireccion.Usuario.setCelular(row.getCell(6).toString());
//                    usuarioDireccion.Usuario.setCurp(row.getCell(7).toString());
//                    usuarioDireccion.Usuario.setUserName(row.getCell(8).toString());
//                    Cell cell = row.getCell(9);
//                    Date fechaNacimiento;
//                    if (cell.getCellType() == CellType.NUMERIC) {
//                        fechaNacimiento = cell.getDateCellValue();
//                    } else {
//                        String fechaS = cell.toString();
//                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
//                        fechaNacimiento = formato.parse(fechaS);
//                    }
//                    usuarioDireccion.Usuario.setFechaNacimiento(fechaNacimiento);
//                    usuarioDireccion.Usuario.Roll = new Roll();
//                    usuarioDireccion.Usuario.Roll.setIdRoll((int) row.getCell(10).getNumericCellValue());
////                    usuarioDireccion.Usuario.setStatus(row.getCell(3) != null ? (int) row.getCell(3).getNumericCellValue() : 0 );
//                    usuarioDireccion.Direccion = new Direccion();
//                    usuarioDireccion.Direccion.setCalle(row.getCell(11).toString());
//                    usuarioDireccion.Direccion.setNumeroExterior(row.getCell(12).toString());
//                    usuarioDireccion.Direccion.setNumeroInterior(row.getCell(13).toString());
//
//                    usuarioDireccion.Direccion.Colonia = new Colonia();
//                    usuarioDireccion.Direccion.Colonia.setIdColonia((int) row.getCell(14).getNumericCellValue());
//                    listaUsuarios.add(usuarioDireccion);
//                }
//
//            }
//        } catch (Exception ex) {
//            System.out.println("Error al abrir el archivo");
//        }
//
//        return listaUsuarios;
//    }
//
//    public List<ResultFile> ValidarArchivo(List<UsuarioDireccion> listaUsuarios) {
//        List<ResultFile> listaErrores = new ArrayList<>();
//
//        if (listaUsuarios == null) {
//            listaErrores.add(new ResultFile(0, "La lista es nula", "La lista es nula"));
//        } else if (listaUsuarios.isEmpty()) {
//            listaErrores.add(new ResultFile(0, "La lista está vacía", "La lista está vacía"));
//        } else {
//            int fila = 1;
//            for (UsuarioDireccion usuarioDireccion : listaUsuarios) {
//                if (usuarioDireccion.Usuario.getNombre() == null || usuarioDireccion.Usuario.getNombre().equals("")) {
//                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getNombre(), "El nombre es un campo oligatorio"));
//                }
//
//                if (usuarioDireccion.Usuario.getApellidoPaterno() == null || usuarioDireccion.Usuario.getApellidoPaterno().equals("")) {
//                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getApellidoPaterno(), "El Apellido Paterno es un campo oligatorio"));
//                }
//
//                if (usuarioDireccion.Usuario.getUserName() == null || usuarioDireccion.Usuario.getUserName().equals("")) {
//                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getApellidoPaterno(), "El Username es un campo oligatorio"));
//                }
//                fila++;
//            }
//        }
//        return listaErrores;
//    }
//
//    @PostMapping("CargaMasiva/procesar")
//    public String Procesar(@RequestParam MultipartFile archivo, Model model, HttpSession session) {
//        
//        
//        return "/CargaMasiva";
//    }
}
