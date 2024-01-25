/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.ShowUsers;
import org.basex.core.cmd.XQuery;

/**
 *
 * @author manana
 */
public class Controlador {

    private Context contexto;
    private String directorioBD = "src/Recursos/coches.xml";

    public void crearBD() {
        if (contexto == null) {
            contexto = new Context();

            try {
                CreateDB baseDatos = new CreateDB("Coches", directorioBD);

                baseDatos.execute(contexto);

                System.out.print(new ShowUsers().execute(contexto));
            } catch (BaseXException ex) {
                System.out.println("-->LA BASE DE DATOS NO HA PODIDO CREARSE:");
                System.err.println(ex);
            }
        }

    }
    
    public boolean guardarCoches(String ruta){
        if (contexto == null) {
            crearBD();
        }
        try {
            for (int i = 1; i < 7; i++) {
                String xq = new XQuery(" for $b in //coche[@id=\""+i+"\"]\n" +" return $b").execute(contexto);
                guardarTextoEnXML(ruta,xq,"coche"+String.valueOf(i));
            }
            return true;
           
        } catch (BaseXException ex) {
            System.out.println("-->ERROR AL CONSULTAR LA BASE DE DATOS:");
            System.err.println(ex);
            return false;
        }
        
    }

  
    
    public boolean guardarComercial(String ruta){
        if (contexto == null) {
            crearBD();
        }
        try {
            for (int i = 1; i < 7; i++) {
                String xq = new XQuery(" for $b in //coche[@id=\""+i+"\"]\n" +" return $b/comercial").execute(contexto);
                guardarTextoEnXML(ruta,xq,"comercial"+String.valueOf(i));
            }
            return true;
           
        } catch (BaseXException ex) {
            System.out.println("-->ERROR AL CONSULTAR LA BASE DE DATOS:");
            System.err.println(ex);
            return false;
        }
        
    }
    
    
    public static void guardarTextoEnXML(String ruta,String contenido, String nombreArchivo) {
        try (Writer writer = new FileWriter(ruta + "/" + nombreArchivo+".txt")) {
            // Escribir el encabezado XML
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

            // Escribir el contenido
            writer.write(contenido);

            System.out.println("Contenido guardado en: " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
