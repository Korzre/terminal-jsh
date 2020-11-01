package br.unifil.dc.sisop;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Write a description of class ComandoPrompt here.
 *
 * @author Ricardo Inacio Alvares e Silva
 * @version 180823
 */
public class ComandoPrompt {
    
	private String nome;
    private ArrayList<String> argumentos = new ArrayList<String>();
    
	public ComandoPrompt(String comando) {
		String strArray[] = comando.split(" ");
		
		nome = strArray[0];
		if(strArray.length > 1) {
			argumentos.add(strArray[1]);
		}
			
//			@Teste
//			System.out.println("Comando: " +getNome()); 
//			System.out.println("Argumentos: " +getArgumentos().get(0));
						
	}
    
    /**
     * Método acessor get para o nome do comando.
     * 
     * @return o nome do comando, exatamente como foi entrado.
     */
    public String getNome() { 
        return nome;
    }
    
    /**
     * Método acessor get para os argumentos que seguram ao nome do comando.
     * 
     * @return Lista de argumentos do comando, protegida contra modificações externas.
     */
    public ArrayList<String> getArgumentos() {
		return argumentos;
    }  
}