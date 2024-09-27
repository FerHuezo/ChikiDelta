/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.UUID;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import Vista.frmEnfermero;
/**
 *
 * @author Estudiante
 */
public class Enfermeros {
    
    private String Nombre_Enfermero;
    private String Correo_Enfermero;
    private int Edad_Enfermero;
    private double Peso_Enfermero;

    public double getPeso_Enfermero() {
        return Peso_Enfermero;
    }

    public void setPeso_Enfermero(double Peso_Enfermero) {
        this.Peso_Enfermero = Peso_Enfermero;
    }
    
    private String UUID_Enfermero;

    public String getUUID_Enfermero() {
        return UUID_Enfermero;
    }

    public void setUUID_Enfermero(String UUID_Enfermero) {
        this.UUID_Enfermero = UUID_Enfermero;
    }

    public String getNombre_Enfermero() {
        return Nombre_Enfermero;
    }

    public void setNombre_Enfermero(String Nombre_Enfermero) {
        this.Nombre_Enfermero = Nombre_Enfermero;
    }

    public String getCorreo_Enfermero() {
        return Correo_Enfermero;
    }

    public void setCorreo_Enfermero(String Correo_Enfermero) {
        this.Correo_Enfermero = Correo_Enfermero;
    }

    public int getEdad_Enfermero() {
        return Edad_Enfermero;
    }

    public void setEdad_Enfermero(int Edad_Enfermero) {
        this.Edad_Enfermero = Edad_Enfermero;
    }

    
    
    public void Mostrar(JTable tabla) {
        //Creamos una variable de la clase de conexion
        Connection conexion = ClaseConexion.getConexion();
        //Definimos el modelo de la tabla
        DefaultTableModel modeloDeDatos = new DefaultTableModel();
        modeloDeDatos.setColumnIdentifiers(new Object[]{"UUID_Enfermero", "Nombre", "Edad", "Peso", "Correo Electrónico"});
        try {
            //Creamos un Statement
            Statement statement = conexion.createStatement();
            //Ejecutamos el Statement con la consulta y lo asignamos a una variable de tipo ResultSet
            ResultSet rs = statement.executeQuery("SELECT * FROM tbEnfermero");
            //Recorremos el ResultSet
            while (rs.next()) {
                //Llenamos el modelo por cada vez que recorremos el resultSet
                modeloDeDatos.addRow(new Object[]{
                    rs.getString("UUID_Enfermero"), 
                    rs.getString("Nombre_Enfermero"), 
                    rs.getInt("Edad_Enfermero"), 
                    rs.getString("Peso_Enfermero"),
                    rs.getString("Correo_Enfermero")
                });
            }
            //Asignamos el nuevo modelo lleno a la tabla
            tabla.setModel(modeloDeDatos);
        } catch (Exception e) {
            System.out.println("Este es el error en el modelo, metodo mostrar " + e);
        }
    }

    public void Guardar() {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClaseConexion.getConexion();
        try {
            //Creamos el PreparedStatement que ejecutará la Query
            PreparedStatement addEnfermero = conexion.prepareStatement("INSERT INTO tbEnfermero(UUID_Enfermero, Nombre_Enfermero, Edad_Enfermero, Peso_Enfermero, Correo_Enfermero) VALUES (?, ?, ?, ?, ?)");
            //Establecer valores de la consulta SQL
            addEnfermero.setString(1, UUID.randomUUID().toString());
            addEnfermero.setString(2, getNombre_Enfermero());
            addEnfermero.setInt(3, getEdad_Enfermero());
            addEnfermero.setDouble(4, getPeso_Enfermero());
            addEnfermero.setString(5, getCorreo_Enfermero());
            //Ejecutar la consulta
            addEnfermero.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("este es el error en el modelo:metodo guardar " + ex);
        }
    }
    
    public void Eliminar(JTable tabla) {

        //Creamos una variable igual a ejecutar el método de la clase de conexión

        Connection conexion = ClaseConexion.getConexion();
 
        //obtenemos que fila seleccionó el usuario

        int filaSeleccionada = tabla.getSelectedRow();

        //Obtenemos el id de la fila seleccionada

        String miId = tabla.getValueAt(filaSeleccionada, 0).toString();

        //borramos 

        try {

            PreparedStatement deleteEnfermero = conexion.prepareStatement("DELETE FROM tbEnfermero where UUID_Enfermero = ?");
            deleteEnfermero.setString(1, miId);
            deleteEnfermero.executeUpdate();

        } catch (Exception e) {

            System.out.println("este es el error metodo de eliminar" + e);

        }

    }
    
    public void cargarDatosTabla(frmEnfermero Vista) {
        // Obtén la fila seleccionada 
        int filaSeleccionada = Vista.jTable1.getSelectedRow();
 
        // Debemos asegurarnos que haya una fila seleccionada antes de acceder a sus valores
        if (filaSeleccionada != -1) {
            String UUIDDeTb = Vista.jTable1.getValueAt(filaSeleccionada, 0).toString();
            String NombreDeTB = Vista.jTable1.getValueAt(filaSeleccionada, 1).toString();
            String EdadDeTb = Vista.jTable1.getValueAt(filaSeleccionada, 2).toString();
            String PesoDeTB = Vista.jTable1.getValueAt(filaSeleccionada, 3).toString();
            String CorreoDeTB = Vista.jTable1.getValueAt(filaSeleccionada, 4).toString();
 
            // Establece los valores en los campos de texto
            Vista.txtNombre.setText(NombreDeTB);
            Vista.txtEdad.setText(EdadDeTb);
            Vista.txtPeso.setText(PesoDeTB);
            Vista.txtCorreo.setText(CorreoDeTB);
        }
    }
    
    
    public void Actualizar(JTable tabla) {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClaseConexion.getConexion();
 
        //obtenemos que fila seleccionó el usuario
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            //Obtenemos el id de la fila seleccionada
            String miUUId = tabla.getValueAt(filaSeleccionada, 0).toString();
            try { 
                //Ejecutamos la Query
                PreparedStatement updateUser = conexion.prepareStatement("UPDATE tbEnfermero set Nombre_Enfermero = ?, Edad_Enfermero = ?, Peso_Enfermero = ?, Correo_Enfermero = ? WHERE UUID_Enfermero = ?");
 
                updateUser.setString(1, getNombre_Enfermero());
                updateUser.setInt(2, getEdad_Enfermero());
                updateUser.setDouble(3, getPeso_Enfermero());
                updateUser.setString(4, getCorreo_Enfermero());
                updateUser.setString(5, miUUId);
                updateUser.executeUpdate();
            } catch (Exception e) {
                System.out.println("este es el error en el metodo de actualizar" + e);
            }
        } else {
            System.out.println("no");
        }
    }
    
    public void limpiar (frmEnfermero Vista) {
        Vista.txtNombre.setText("");
        Vista.txtEdad.setText("");
        Vista.txtPeso.setText(""); 
        Vista.txtCorreo.setText("");
    }

    public void setNombre(String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
