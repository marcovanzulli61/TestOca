
import java.lang.*;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestOca {
	 List<Partecipante> partecipanti = new ArrayList();
	 List<Integer> theGoose = new ArrayList<Integer>(Arrays.asList(5, 9, 14, 18, 23, 27));
	 int theBridge = 6;
	 boolean add;
	 
	public static void main( String[] args){
		
		TestOca testOca = new TestOca();
		
	//	testOca.partecipanti.add(new Partecipante("pippo",1) );
		
		while(true) {
			testOca.readInput();
		}
		 
	}
	private void readInput() {
		Scanner mioScanner = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Inserisci comando: ");
	    String command = mioScanner.nextLine();  // Read user input
	  //  System.out.println("comando: " + command);  // Output user input 
	    executeCommand( command);
	 // mioScanner.close();
	}
	
	private void executeCommand(String command) {
		Scanner commandScanner = new Scanner(command);
		while (commandScanner.hasNext()) {
			String comando = commandScanner.next();
			switch (comando) {
				case "add":
					if(commandScanner.hasNext() &&
							commandScanner.next().equals("player")	) {
						
						if(commandScanner.hasNext())
							addPartecipante(commandScanner.next());
						else 
							printError();
					}
						
					else 
						printError();
						
					
					
					break;
				case "move":
					String pos="0";
					String pos1="0";
					String pos2="0";
					if(commandScanner.hasNext()) {
					String nome1 = commandScanner.next();
					if(commandScanner.hasNext()) {
						 pos = commandScanner.next();
						
						 pos1 = pos.substring(0, 1);
					}
						
					
					if(commandScanner.hasNext())
						 pos2 = commandScanner.next();
					if( Integer.parseInt(pos1)<1 || 
							Integer.parseInt(pos1)>6 ||
							Integer.parseInt(pos2)<1 ||
							Integer.parseInt(pos2)>6)
					{
						printError();
						pos1= getRandom();
						pos2= getRandom();
					}
					movePartecipante(nome1,Integer.parseInt(pos1), Integer.parseInt(pos2));
					}
					else 
						printError();
					break;
				default:
					printError();
			}
			
		}
		commandScanner.close();
	}
	
	private void addPartecipante(String partecipante) {
		add = true;
		partecipanti.forEach(item->{
			if(partecipante.equals(item.nome)){
				System.out.println(item.nome+" already existing player");
				add = false;
			}
		});

		if (add) {
			partecipanti.add(new Partecipante(partecipante, 0));
		//	System.out.println("OK: partecipante aggiunto");
			System.out.println("Players: ");
			partecipanti.forEach(item->{
				
					System.out.println(item.nome+" ");
					
				
			});
		}
		
	}
	
	private void movePartecipante(String partecipante, int pos1, int pos2) {
	//	System.out.println ( partecipante + " " + pos1 +" "+pos2);
		
		partecipanti.forEach(item->{
			int diff=0;
			boolean partecipanteFound=false;
			if(partecipante.equals(item.nome)){
				partecipanteFound=true;
				//System.out.println(item.nome+" trovato");
				int currentSpace = item.punti;
				if(currentSpace + pos1 + pos2 == 63) {
					System.out.println(item.nome + " rolls " +pos1+", "+pos2+ ". "+item.nome+" moves from "+currentSpace+" to 63. ");
					item.punti= currentSpace + pos1 + pos2;
				}
				
					
				else if ( currentSpace + pos1 + pos2 ==  theBridge) {
					// the bridge
					item.punti= 12;
					System.out.println(item.nome + " rolls " +pos1+", "+pos2+ ". "+item.nome+" moves from "+currentSpace+" to The Bridge. "+item.nome+" jumps to 12");
					
				}
				else if ( currentSpace + pos1 + pos2 > 63) {
					diff= currentSpace + pos1 + pos2 -63;
					item.punti= 63- diff;
					System.out.println(item.nome + " rolls " +pos1+", "+pos2+ ". "+item.nome+" moves from "+currentSpace+" to 63. "+item.nome+" bounces! "+item.nome+" returns to "+item.punti+" ");
				// check su bridge o goose non necessari perchè non puossono accadere
				}
				else if ( theGoose.contains(new Integer(currentSpace + pos1 + pos2)) ) {
					// the goose
					Integer goose=currentSpace + pos1 + pos2;
					StringBuilder gooseString= new StringBuilder(" "+item.nome + " rolls "+pos1+", "+pos2+".");
					int newPos= currentSpace+pos1+pos2;
					int newPosAfterGoose=0;
					gooseString.append(" "+item.nome+" moves from "+currentSpace+" to "+newPos);
					int i=1;
					while(true) {
						i++;
						goose=currentSpace + pos1*(i-1) + pos2*(i-1);
						if (theGoose.contains(goose)) {
							 newPos= goose;
							 newPosAfterGoose= currentSpace + pos1*i + pos2*i;
							gooseString.append(", The Goose. "+item.nome+"  moves again and goes to "+newPosAfterGoose);
							goose = newPosAfterGoose;
						}
						else {
							break;
						}
							
						
					}
					item.punti= newPosAfterGoose;
					System.out.println(gooseString);
					//System.out.println(	item.punti);
				}
				else {
					item.punti= currentSpace + pos1 + pos2;
					System.out.println(item.nome + " rolls " +pos1+", "+pos2+ ". "+item.nome+" moves from "+currentSpace+" to "+item.punti+". ");
				
				}
				checkWin(item.nome, item.punti);
			}
		});
	}
	private void checkWin(String partecipante, int punti) {
		if( punti == 63) {
			System.out.print(" "+partecipante+" Wins!!");
			System.exit(0);
		}
			
	}
	
	private String getRandom() {
		 Random rand = new Random(); 
		 int rand_int1=0;
		 while (rand_int1==0)
			 rand_int1 = rand.nextInt(7); 
	     return Integer.toString(rand_int1);
	}
	
	private void printError() {
		System.out.println("Invalid command");
	}
}