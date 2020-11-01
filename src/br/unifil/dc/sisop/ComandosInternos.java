package br.unifil.dc.sisop;

import java.io.File;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Write a description of class ComandosInternos here.
 *
 * @author Ricardo Inacio Alvares e Silva
 * @version 180823
 */

public final class ComandosInternos {
	
 //	Para Mac
	static String userLog = System.getProperty("user.name");
	public static String workdir = "/Users/"+userLog+"/";
	public static String workdirOrigin = "/Users/"+userLog+"/";
	
//	Para Windows
//	public static String workdir = "C:\\Users\\Korzre\\";
//	public static String workdirOrigin = "C:\\Users\\Korzre\\";

    public static String exibirRelogio() {
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  

    	return dtf.format(now);
    }
    
    public static String escreverListaArquivos(Optional<String> nomeDir) {
    	String[] pathnames;

        File file = new File(ComandosInternos.workdir);

        pathnames = file.list();
        String files = "";
        try {             
        	for (String pathname : pathnames) {    	
        		files += pathname+"\n";
        	}      
        }catch(Exception NullPointerException) {
        	System.out.println("O Diretorio não foi encontrado!");
        	ComandosInternos.workdir = ComandosInternos.workdirOrigin;
        }
        
    	return files;
    }
    
	public static void criarNovoDiretorio(String nomeDir) {

		File file = new File(ComandosInternos.workdir+"/"+nomeDir);
        try{
            if(file.mkdir()) { 
                System.out.println(nomeDir+" :)");
            } else {
                System.out.println(nomeDir+" :(");
            }
        } catch(Exception e){
            e.printStackTrace();
        }

	}
    
    public static void apagarDiretorio(String nomeDir) {
    	File file = new File(ComandosInternos.workdir+nomeDir);
        try{
            if(file.delete()) { 
                System.out.println(nomeDir+" :)");
            } else {
                System.out.println(nomeDir+" :(");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void mudarDiretorioTrabalho(String nomeDir){
    	if(!nomeDir.contains("/")) {
        	ComandosInternos.workdir = ComandosInternos.workdir+"/"+nomeDir; 
    	}else {
        	ComandosInternos.workdir = nomeDir; 
    	}
    }
      
    /**
     * Essa classe não deve ser instanciada.
     */
    private ComandosInternos() {}
}
