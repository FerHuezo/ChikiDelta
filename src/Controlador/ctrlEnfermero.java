/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Enfermeros;
import Vista.frmEnfermero;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

public class ctrlEnfermero implements MouseListener {
    
    private Enfermeros Modelo;
    private frmEnfermero Vista;

    public ctrlEnfermero(Enfermeros Modelo, frmEnfermero Vista) {
        this.Modelo = Modelo;
        this.Vista = Vista;

        Vista.btnAgregar.addMouseListener(this);
        Modelo.Mostrar(Vista.jTable1);

        Vista.btnEliminar.addMouseListener(this);
        Vista.jTable1.addMouseListener(this);
        Vista.btnEditar.addMouseListener(this);
        Vista.btnLimpiar.addMouseListener(this);  

        // Validación para txtNombre
        Vista.txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (Vista.txtNombre.getText().length() >= 50 || 
                    !Character.isLetter(e.getKeyChar()) && e.getKeyChar() != ' ') {
                    e.consume();  // Evita más entradas si supera 50 caracteres o no es letra
                }
            }
        });
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == Vista.btnAgregar) {
            // Validar que los campos no estén vacíos
            if (Vista.txtNombre.getText().isEmpty() || Vista.txtEdad.getText().isEmpty() || 
                Vista.txtPeso.getText().isEmpty() || Vista.txtCorreo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vista, "Todos los campos deben estar llenos", "Error", JOptionPane.ERROR_MESSAGE);
                return;  // No se continúa si hay campos vacíos
            }

            // Validar formato de correo
            if (!Vista.txtCorreo.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(Vista, "El correo no tiene un formato válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Captura de posibles errores en la conversión de los valores
            try {
                Modelo.setNombre_Enfermero(Vista.txtNombre.getText());
                Modelo.setEdad_Enfermero(Integer.parseInt(Vista.txtEdad.getText()));
                Modelo.setPeso_Enfermero(Double.parseDouble(Vista.txtPeso.getText()));
                Modelo.setCorreo_Enfermero(Vista.txtCorreo.getText());
                Modelo.Guardar();
                Modelo.Mostrar(Vista.jTable1);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Vista, "Error al convertir los valores numéricos", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(Vista, "Ocurrió un error al guardar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
                
        if (e.getSource() == Vista.btnEliminar) {
            Modelo.Eliminar(Vista.jTable1);
            Modelo.Mostrar(Vista.jTable1);
        }
        
        if (e.getSource() == Vista.jTable1) {
            Modelo.cargarDatosTabla(Vista);
        }
                
        if (e.getSource() == Vista.btnEditar) {
            if (Vista.txtNombre.getText().isEmpty() || Vista.txtEdad.getText().isEmpty() || 
                Vista.txtPeso.getText().isEmpty() || Vista.txtCorreo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vista, "Todos los campos deben estar llenos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!Vista.txtCorreo.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(Vista, "El correo no tiene un formato válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Modelo.setNombre_Enfermero(Vista.txtNombre.getText());
                Modelo.setEdad_Enfermero(Integer.parseInt(Vista.txtEdad.getText()));
                Modelo.setPeso_Enfermero(Double.parseDouble(Vista.txtPeso.getText()));
                Modelo.setCorreo_Enfermero(Vista.txtCorreo.getText());
                Modelo.Actualizar(Vista.jTable1);
                Modelo.Mostrar(Vista.jTable1);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Vista, "Error al convertir los valores numéricos", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(Vista, "Ocurrió un error al actualizar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        if (e.getSource() == Vista.btnLimpiar) {
            Modelo.limpiar(Vista);
        }        
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    @Override
    public void mouseExited(MouseEvent e) {}
}