import java.util.Scanner;

public class RobotLivraison extends RobotConnect {
protected String colisActuel;
protected String destination;
protected boolean enLivraison;
protected static final int ENERGIE_LIVRAISON=15;
protected static final int ENERGIE_CHARGEMENT =5;
	public RobotLivraison(String id,int x,int y) {
		super(id,x,y); 
		colisActuel = "0";
		destination = null ;
		enLivraison = false;
		
	}
	@Override
	public void effectuerTache() throws RobotException{
		
		if(!this.enMarche) {
			 ajouterHistorique("Le robot éteint, echec d'effectuer une tâche.");
			throw new RobotException("Le robot doit être démarré pour effectuer une tâche");
		}
	      if(enLivraison ) {
			 Scanner scanner = new Scanner(System.in);
			 System.out.print("Entrez la coordonnée x : ");
		        int x = scanner.nextInt();

		        System.out.print("Entrez la coordonnée y : ");
		        int y = scanner.nextInt();
		        FaireLivraison(x,y);
		       
		}
	      else {
	    	  Scanner scanner = new Scanner(System.in);
	      
	    System.out.print("Voulez-vous charger un nouveau colis ? (oui/non) : ");
	   
	          String reponse = scanner.nextLine().trim().toLowerCase();

	          if (reponse.equals("oui")) {  
	        	  
	        	  verifierEnergie(ENERGIE_CHARGEMENT);
	                System.out.print("Entrez la destination du colis : ");
	                String dest = scanner.nextLine();
	                chargerColis(dest);
	            } else {
	                ajouterHistorique("En attente de colis.");
	            }
	          
	          
	      }
	     
	}
	

@Override
	public void deplacer(int x1, int y1) throws RobotException {
	
		double distance = Math.sqrt(Math.pow(x1 - this.x, 2) + Math.pow(y1 - this.y, 2));
		if(distance>100) {
			throw new RobotException("La distance entre la position actuelle du robot et la distination depasse 100 unites !");
		}
		try {
	        verifierEnergie(0.3*distance); 
	    } catch (Energie_Insuffisante_Exception e) {
	        ajouterHistorique("Échec de deplaçement : " + e.getMessage());
	        throw new Energie_Insuffisante_Exception("Énergie insuffisante pour se deplacer !",e);
	    }
		try {
	        verifierMaintenance(); 
	    } catch ( Maintenance_Requise_Exception e) {
	        ajouterHistorique("Échec de deplaçement : " + e.getMessage());
	        throw new  Maintenance_Requise_Exception("Le robot a besoin de maintenance avant de se deplacer!",e);
	    }
		
		this.x=x1;
		this.y=y1;
		
		consommerEnergie(0.3*distance);
		
		
		this.heuresUtilisation+=(distance/10);
		  ajouterHistorique("deplacement de robot avec consommation de "+String.format("%.2f",(0.3*distance))+"%" );
		
		
	}
	
	public void chargerColis(String destination) throws RobotException{
		
		try {
	        verifierEnergie(ENERGIE_CHARGEMENT); 
	    } catch (Energie_Insuffisante_Exception e) {
	        ajouterHistorique("Échec de chargement du colis : " + e.getMessage());
	        throw new Energie_Insuffisante_Exception("Énergie insuffisante pour charger le colis !",e);
	    }
		if(colisActuel.equals("0") && !enLivraison) {
			colisActuel = "1";
			this.destination=destination;
			consommerEnergie(ENERGIE_CHARGEMENT);
			 this.enLivraison = true;
			 ajouterHistorique("Chargement de colis avec consommation de " + String.format("%.2f", consommationReelle) + "% d'énergie");

			
		}
		else {
		    throw new RobotException("Impossible de charger un colis : déjà en livraison ou un colis est en cours.");
		}

		
	}
	@Override
	public String toString() {
	return "RobotIndustriel [ID : " +this.id+", Position : ("+ this.x+ ","+this.y+"), Energie : "+this.energie+"%, Heures : "+ this.heuresUtilisation 
				+", Colis:"+ this.colisActuel+", Destination: "+ this.destination +", Connecté : " +this.connecte + "L'empreinte carbone du robot est : " + empreinteCarbone + " g CO2"+"]";
	}	
	
	
	public void FaireLivraison(int Destx, int Desty) throws RobotException {
		deplacer(Destx, Desty);
		colisActuel = "0";
		enLivraison = false;
	
		ajouterHistorique("livraison terminé à "+ this.destination);
		
		this.destination=null;
		
		
		
	}
	
}

