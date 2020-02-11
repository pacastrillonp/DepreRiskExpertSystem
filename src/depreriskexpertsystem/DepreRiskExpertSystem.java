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
        fis.setVariable("puntajePrueba", message);
        fis.setVariable("edad", edad);
        fis.setVariable("intentosDeSuicidio", intentosSuicidio);

        // Evaluate
        fis.evaluate();

        // Show
        JFuzzyChart.get().chart(fis.getFunctionBlock("nivelDeRiesgo"));

        Double x = fis.getVariable("nivelDeRiesgo").getLatestDefuzzifiedValue();
        System.err.println("Para los valores de salida el grado de pertenencia es: " + x);

        // Show rules
        for (Rule r : fis.getFunctionBlock("nivelDeRiesgo").getFuzzyRuleBlock("No1").getRules()) {
            System.out.println(r);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        serverRun = false;
        super.finalize();

    }
}
