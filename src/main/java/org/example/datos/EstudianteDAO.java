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


    }



    public static void main(String[] args) {
        var estudianteDAO = new EstudianteDAO();
        //Listar los Estudiantes
        System.out.println("Listado Estudiantes: ");
        List<Estudiante> estudiantes = estudianteDAO.ListarEstudiantes();
        estudiantes.forEach(System.out::println);
    }


}
