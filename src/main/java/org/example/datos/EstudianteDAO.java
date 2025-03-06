package org.example.datos;

import org.example.dominio.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.example.conexion.Conexion.getConexion;

//DAO - Data Access Object
public class EstudianteDAO {
    public List<Estudiante> ListarEstudiantes(){
        List<Estudiante> estudiantes = new ArrayList<>();

        //Prepara la sentencia para la base de datos
        PreparedStatement preparedStatement;
        //Objeto que almacena el resultado de la base de datos
        ResultSet resultSet;
        // Objeto de tipo connection para hacer conexión a la base de datos
        Connection connection = getConexion();
        //Sentencia sql
        String sentenciaSql = "SELECT * FROM estudiante ORDER BY id_estudiante";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSql);
            resultSet = preparedStatement.executeQuery();
            //se itera sobre cada registro devuelto por la base de datos
            while (resultSet.next()){
                var estudiante = new Estudiante();
                estudiante.setIdEstudiante(resultSet.getInt("id_estudiante"));
                estudiante.setNombre(resultSet.getString("nombre"));
                estudiante.setApellido(resultSet.getString("apellido"));
                estudiante.setTelefono(resultSet.getString("telefono"));
                estudiante.setEmail(resultSet.getString("email"));
                estudiantes.add(estudiante);
            }
        } catch (Exception e){
            System.out.println("Ocurrio un error al seleccionar datos: " + e.getMessage());
        }
        finally {
            try {
                connection.close();
            } catch (Exception e){
                System.out.println("Ocurrió un error al cerrar conexión: " + e.getMessage());
            }
        }

        return estudiantes;
    }

    //find by id
    public boolean buscarEstudiantePorId(Estudiante estudiante){
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        String sql = "SELECT * FROM estudiante WHERE id_estudiante = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1,estudiante.getIdEstudiante());
            rs = ps.executeQuery();
            if (rs.next()){
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                return true;
            }
        } catch (Exception e){
            System.out.println("Ocurrio un error al buscar estudiante: " + e.getMessage());
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Ocurrio un error al cerrar conexion: " + e.getMessage());
            }
        }
        return false;

    }

    public boolean agregarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        String sql = "INSERT INTO estudiante(nombre, apellido, telefono, email) VALUES(?,?,?,?)";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.execute();
            return true;

        }catch (Exception e){
            System.out.println("Error al agregar al estudiante: " + e.getMessage());
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Ocurrio un error al cerrar conexion: " + e.getMessage());
            }
        }
        return false;
    }




    public static void main(String[] args) {
        var estudianteDAO = new EstudianteDAO();

        //---------------Agregar nuevo estudiante------------------------
        var nuevoEstudiante = new Estudiante("federico","ramirez","34568889","fede@gmail.com");
        var agregado = estudianteDAO.agregarEstudiante(nuevoEstudiante);
        if(agregado){
            System.out.println("Estudiante agregado: " + nuevoEstudiante);
        }else {
            System.out.println("No se agrego el estudiante: " + nuevoEstudiante);
        }


        //-----------------Listar los Estudiantes-------------------------
        System.out.println("Listado Estudiantes: ");
        List<Estudiante> estudiantes = estudianteDAO.ListarEstudiantes();
        estudiantes.forEach(System.out::println);

        //----------------------Buscar por id----------------------------
        var estudiante1 = new Estudiante(2);
        System.out.println("Estudiante antes de busqueda: " + estudiante1);
        var encontrado = estudianteDAO.buscarEstudiantePorId(estudiante1);
        if(encontrado){
            System.out.println("Estudiante encontrado: " + estudiante1);
        }else {
            System.out.println("No se encontro estudiante: " + estudiante1);
        }

    }


}
