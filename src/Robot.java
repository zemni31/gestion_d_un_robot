
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public abstract class Robot {
protected String id;
protected int x,y;
protected int energie;
protected int heuresUtilisation;
protected boolean enMarche;
protected ArrayList<String> historiqueActions;
protected boolean historiqueVisible ;
protected int taille;
protected double energieConsommeeTotale;
protected static final double CO2_PAR_UNITE = 0.1; 
protected boolean modeEco;
protected static final int EnergieCritiqe=30;
protected double empreinteCarbone;
protected double consommationReelle;
 public Robot(String id,int x,int y) {
	 this.id=id;
	 this.x=x;
	 this.y=y;
	 energie=100;
	 heuresUtilisation=0;
	 enMarche=false;
	 historiqueActions=new ArrayList<>();
	 historiqueVisible = false;
	 modeEco=false;
	 energieConsommeeTotale=0;
	 empreinteCarbone=0;
	 this.ajouterHistorique("Robot créé");
	 
		 }
 public void ajouterHistorique(String action) {
	 LocalDateTime t= LocalDateTime.now();
	 DateTimeFormatter forme=DateTimeFormatter.ofPattern("dd MM yyyy HH:mm:ss");
	 String date=t.format(forme);
	 historiqueActions.add(date+" " +action +"\n");
 }
 

public boolean verifierEnergie(double energieRequise) throws RobotException {
	if (energie<energieRequise) {
		throw new Energie_Insuffisante_Exception("Energie insuffisante ! ");
	
	}
	else return true;
	
}
public void verifierMaintenance() throws RobotException  { 
	if ( heuresUtilisation>=100) {
		throw new Maintenance_Requise_Exception("l'utilisation depasse 100 heures, maintenance necessaire ! ");
	}
}
	public void demarrer() throws RobotException {
		if(enMarche) {
			throw new  RobotException("Robot est deja allumé !");
		}
		   try {
	            verifierEnergie(10);
	        
	        } catch (Energie_Insuffisante_Exception e) {
	            ajouterHistorique("Échec du démarrage: " + e.getMessage());
	            throw new RobotException("Impossible de démarrer le robot: " + e.getMessage(),e);
	        }
		    this.enMarche = true;
		    consommerEnergie(10);
            ajouterHistorique("Démarrage du robot avec consommation de "+consommationReelle+"% d'energie");
		   
	}
	public void arreter() throws RobotException {
		if(!enMarche) {
			throw new  RobotException("Robot est deja éteint !");
		}
			 enMarche=false;
			 ajouterHistorique("robot éteint ");
		
	}
public void consommerEnergie(double d) {
	 consommationReelle = modeEco ? d * 0.8 : d;
	if((energie-consommationReelle) > 0) {
		energie-=consommationReelle;
		energieConsommeeTotale+=consommationReelle;
	}
	else {
		 energieConsommeeTotale += energie;
		energie=0;
		
	}
}

public void recharger(int quantite) {
	if((energie+quantite) < 100) {
		energie+=quantite;
	
	ajouterHistorique("recharge effectuée (+"+quantite+"%)");}
	else {
		int q=100-this.energie;
		energie=100;
		ajouterHistorique("recharge effectuée (+"+q+"%)");}
	}

public abstract void  deplacer(int x, int y) throws RobotException;
public abstract void effectuerTache() throws RobotException;
public String getHistorique() {
	return  String.join("\n", historiqueActions);
}
@Override
public String toString() {
	
return "RobotIndustriel [ID : " +this.id+", Position : ("+ this.x+ ","+this.y+"), Energie : "+this.energie+"%, Heures : "+ this.heuresUtilisation 
		+"]";
}
public double getEmpreinteCarbone() {
	empreinteCarbone = energieConsommeeTotale * CO2_PAR_UNITE;
    return 	empreinteCarbone;
}
public void activerModeEco() {
    this.modeEco = true;
    ajouterHistorique("Mode Éco activé");
}

public void desactiverModeEco() {
    this.modeEco = false;
    ajouterHistorique("Mode Éco désactivé");
}

public void verifierModeEco() {
    if (energie < EnergieCritiqe) {
        modeEco = true;
        ajouterHistorique("Mode Éco activé automatiquement (énergie faible)");
  
}}
    
    public void rechargerSolaire(int quantite) {
    
    	    if (Math.random() < 0.5) {
    	        ajouterHistorique("Recharge solaire effectuée (+10% bonus)");
    	        recharger(quantite + 10);
    	    } else {
    	        ajouterHistorique("Pas assez de soleil pour recharger aujourd'hui");
    	        recharger(quantite);
    	    }
    	}

}









