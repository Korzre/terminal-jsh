package br.unifil.dc.sisop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Write a description of class Jsh here.
 *
 * @author Ricardo Inacio Alvares e Silva
 * @version 180823
 */
public final class Jsh {
 
	public static ArrayList <String> verifiedList = new ArrayList<String>();
	public static Process proc = null;
    public static void promptTerminal() {

        while (true) {
    		exibirPrompt();
    		ComandoPrompt comandoEntrado = lerComando();
    		executarComando(comandoEntrado);
    		executarPrograma(comandoEntrado);
    	}
    }

    /**
    * Escreve o prompt na saida padrao para o usuário reconhecê-lo e saber que o
    * terminal está pronto para receber o próximo comando como entrada.
    */
    public static void exibirPrompt() {
    	String user = System.getProperty("user.name");
    	String command = "id -u";
    	      
		try {
			proc = Runtime.getRuntime().exec(command);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        String line = "";
        String uid = "";
        try {
			while((line = reader.readLine()) != null) {
			    uid+=line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

        try {
			proc.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  
        
    	System.out.print(user+"#"+uid+":"+ComandosInternos.workdir+"% ");
    	
    }

    /**
    * Preenche as strings comando e parametros com a entrada do usuario do terminal.
    * A primeira palavra digitada eh sempre o nome do comando desejado. Quaisquer
    * outras palavras subsequentes sao parametros para o comando. A palavras sao
    * separadas pelo caractere de espaco ' '. A leitura de um comando eh feita ate
    * que o usuario pressione a tecla <ENTER>, ou seja, ate que seja lido o caractere
    * EOL (End Of Line).
    *
    * @return 
    */
    public static ComandoPrompt lerComando() {
    	Scanner input = new Scanner(System.in);
    	String comando = input.nextLine();
    	return new ComandoPrompt(comando);
    }

    /**
    * Recebe o comando lido e os parametros, verifica se eh um comando interno e,
    * se for, o executa.
    * 
    * Se nao for, verifica se é o nome de um programa terceiro localizado no atual 
    * diretorio de trabalho. Se for, cria um novo processo e o executa. Enquanto
    * esse processo executa, o processo do uniterm deve permanecer em espera.
    *
    * Se nao for nenhuma das situacoes anteriores, exibe uma mensagem de comando ou
    * programa desconhecido.
    */
    
    public static void verify() {
         verifiedList.add("ad"); //Apagar diretorio
         verifiedList.add("cd"); //Criar diretorio
         verifiedList.add("mdt"); //trocar de diretorio
         verifiedList.add("encerrar"); //encerrar o programa retornando 0.
         verifiedList.add("la"); //listar os diretorios  e os ficheiros.
         verifiedList.add("relogio"); //Mostrar o relogio
         verifiedList.add("Relogio"); //Mostrar o relogio
         verifiedList.add("Relogio"); //Mostrar o relogio
         verifiedList.add("clang"); //Compilar o codigo em c
         verifiedList.add("./"); //Executar o codigo em c
    }
    
    public static void executarComando(ComandoPrompt comando) {
       Jsh.verify();
    	if(comando.getArgumentos().isEmpty() && (comando.getNome().equals("Relogio") || comando.getNome().equals("relogio"))) {        	
    		System.out.println(ComandosInternos.exibirRelogio());
    	}
    	  
    	if(comando.getArgumentos().isEmpty() && comando.getNome().equals("la")) {     
    		System.out.println(ComandosInternos.escreverListaArquivos(null));
    	}
    	
    	if(comando.getNome().equals("cd")) {  
    		ComandosInternos.criarNovoDiretorio(comando.getArgumentos().get(0));
    	}
    	
    	if (comando.getArgumentos().isEmpty() && comando.getNome().equals("encerrar")) {
    		System.out.println("0");
    		System.exit(0);
    	}
    	
    	if(comando.getNome().equals("ad")) {  
    		ComandosInternos.apagarDiretorio(comando.getArgumentos().get(0));
    	}
    	
    	if(comando.getNome().equals("mdt")) {
    		ComandosInternos.mudarDiretorioTrabalho(comando.getArgumentos().get(0));  	
    	}
   	
    	if((!verifiedList.contains(comando.getNome()))) {
    		System.out.println("Comando não encontrado!");
    	 }
    }

    public static void executarPrograma(ComandoPrompt comando) { 
    	//Para executar o programa (regras):
    	//1-) clang filename
    	
    	Jsh.verify();
    	if(comando.getNome().equals("clang")) { 
        	System.out.println();
        	String program = ComandosInternos.workdir+"/"+comando.getArgumentos().get(0)+".c";
        	String filename = ComandosInternos.workdir+"/"+comando.getArgumentos().get(0);
    		String command = "clang "+program+" -o"+filename;
        	try {
    			proc = Runtime.getRuntime().exec(command);
    		} catch (IOException e1) {
      			System.out.println("Não compilou!");

    		}
        	
        	try {
     			proc.waitFor();
     		} catch (InterruptedException e) {
     			e.printStackTrace();
     		}
    		   		
    		command = filename;
      		try {
      			proc = Runtime.getRuntime().exec(command);
      		} catch (IOException e1) {
      			System.out.println("Não executou!");
      		}
          BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line = "";
            String text = "";
            try {
    			while((line = reader.readLine()) != null) {
    			    text+=line;
    			}
    		} catch (IOException e) {
      			System.out.println("Não leu!");

    		}

            try {
    			proc.waitFor();
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}  
            
            System.out.println(text);

    	}
    }
    
    /**
     * Entrada do programa. Provavelmente você não precisará modificar esse método.
     */
    
    public static void main(String[] args) {
        promptTerminal();
    }
    
    Jsh() {}
}
