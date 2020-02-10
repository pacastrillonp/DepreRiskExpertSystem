/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package depreriskexpertsystem;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static void main(String[] args) {
        try {
            serverRun = true;
            serverSocket = new ServerSocket(4000);
            while (serverRun) {
                socket = serverSocket.accept();
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                String message = bufferedReader.readLine();

                Gson gson = new Gson();
                AnswerRequest[] answers = gson.fromJson(message, AnswerRequest[].class);
                System.out.println(message);
                for (int i = 0; i < answers.length; i++) {
                    System.out.println(answers[i].getAnswer());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        serverRun = false;
        super.finalize();

    }
}
