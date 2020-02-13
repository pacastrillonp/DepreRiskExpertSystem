/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package depreriskexpertsystem;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jess.ConsolePanel;
import jess.Deffacts;
import jess.Defrule;
import jess.Deftemplate;
import jess.Fact;
import jess.JessException;
import jess.PrettyPrinter;
import jess.RU;
import jess.Rete;
import jess.Value;

/**
 *
 * @author pactr
 */
public class DepreRiskExpertSystem {

    /**
     * @param args the command line arguments
     */
    static Socket socket;
    static ServerSocket serverSocket;
    static InputStreamReader inputStreamReader;
    static BufferedReader bufferedReader;
    static Boolean serverRun;
    static int message = 0;
    static int edad = 0;
    static int intentosSuicidio = 0;

    public static void main(String[] args) {
        try {
            serverRun = true;
            serverSocket = new ServerSocket(4000);

            while (serverRun) {

                socket = serverSocket.accept();
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                message = Integer.parseInt(bufferedReader.readLine());

                if (message != 0) {
                    System.out.println(message);
                    fuzzyLogic();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void fuzzyLogic() {
        ///////////////////////////////////
        /// Logica difusa
        // Load from 'FCL' file
        String fileName = "Archivo FCL.fcl";
        FIS fis = FIS.load(fileName, true);
        // Error while loading?
        if (fis == null) {
            System.err.println("Can't load file: '" + fileName + "'");
            return;
        }
        // Set inputs
        edad = Integer.parseInt(JOptionPane.showInputDialog("Por favor, ingrese su edad"));
        intentosSuicidio = Integer.parseInt(JOptionPane.showInputDialog("Por favor, ingrese el numero de intentos de suicidio"));
        int horasSueno = Integer.parseInt(JOptionPane.showInputDialog("Por favor, ingrese el numero de horas de sueno"));
        fis.setVariable("puntajePrueba", message);
        fis.setVariable("edad", edad);
        fis.setVariable("intentosDeSuicidio", intentosSuicidio);

        // Evaluate
        fis.evaluate();

        // Show
        JFuzzyChart.get().chart(fis.getFunctionBlock("nivelDeRiesgo"));

        Double nivelDeRiesgo = fis.getVariable("nivelDeRiesgo").getLatestDefuzzifiedValue();
        System.err.println("Para los valores de salida el grado de pertenencia es: " + nivelDeRiesgo);

        try {
            expertSystem(nivelDeRiesgo.intValue(), horasSueno);
        } catch (JessException ex) {
            Logger.getLogger(DepreRiskExpertSystem.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Show rules
        for (Rule r : fis.getFunctionBlock("nivelDeRiesgo").getFuzzyRuleBlock("No1").getRules()) {
            System.out.println(r);
        }

    }

    public static void expertSystem(int nivelRiesgoLD, int horasSuenoLD) throws JessException {
        Rete rete = new Rete();
        rete.batch("Sistema experto.CLP");

        ConsolePanel c = new ConsolePanel(rete);
        interfaz a = new interfaz(c);

        Deffacts hechosIniciales = new Deffacts("hechosini", null, rete);
        Fact f = new Fact("usuario", rete);
        f.setSlotValue("nivelRiesgo", new Value(nivelRiesgoLD, RU.INTEGER));
        f.setSlotValue("horasSueno", new Value(horasSuenoLD, RU.INTEGER));
        hechosIniciales.addFact(f);
        rete.addDeffacts(hechosIniciales);

        rete.reset();

        rete.executeCommand("(assert (usuario\n"
                + "			(nivelRiesgo " + nivelRiesgoLD + ")\n"
                + "			(horasSueno " + horasSuenoLD + ")))");

        
        System.out.println(new PrettyPrinter(hechosIniciales));

        rete.executeCommand("(defrule saludo\n"
                + "	(declare (salience 10))	\n"
                + "	=>\n"
                + "	(printout t \"Hola, este es el sistema Depre IA, ahora te daremos algunas recomendacines basandonos en el nivel de riesgo que obtuviste en el cuestionario.\" crlf)\n"
                + "	(assert (ingresarDatos))\n"
                + ")");
        Defrule regla = (Defrule) rete.findDefrule("saludo");
        System.out.println(new PrettyPrinter(regla));
        Defrule regla2 = (Defrule) rete.findDefrule("recomendaciones");
        System.out.println(new PrettyPrinter(regla2));

        a.add(c);
        a.setVisible(true);

        rete.run();



    }

    @Override
    protected void finalize() throws Throwable {
        serverRun = false;
        super.finalize();

    }
}
