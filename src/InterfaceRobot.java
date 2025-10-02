import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceRobot extends JFrame {
	public Color couleur=Color.LIGHT_GRAY;
	public int screenX=300;
	public int screenY=200;
	public int robotX;
	public int robotY;
    private JButton btnDemarrer;
    private JButton btnArreter;
    private JButton btnDeplacer;
    private JButton btnRecharger;
    private JButton btnEffectuerTache;
    private JButton btnModeEco;
    private JButton btnHistorique;
    private JButton btnConnect;
    private JButton btnDonnee;
    private JButton btnColis;
    private JButton btnLivraison;
    private JTextArea historiqueTextArea;  
    private JScrollPane scrollPane; 
    private JPanel panelRobot;
    private JLabel etatLabel;
    private RobotLivraison robot;

    public InterfaceRobot() {
        
        robot = new RobotLivraison("R2-D2", 0, 0);
       

      
           
          

        
        setTitle("Robot de Livraison");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelCommandes = new JPanel();
        panelCommandes.setBackground(Color.pink);
    
       // panelCommandes.setLayout(new GridLayout(4, 2));
        panelCommandes.setLayout(new BoxLayout(panelCommandes, BoxLayout.Y_AXIS));

        btnDemarrer = new JButton("Démarrer");
        btnArreter = new JButton("Arrêter");
        btnDeplacer = new JButton("Déplacer");
        btnRecharger = new JButton("Recharger");
        btnEffectuerTache = new JButton("Effectuer une tâche");
        btnModeEco = new JButton("Passer en mode éco");
        btnHistorique=new JButton("Afficher l'historique");
        btnConnect = new JButton("Connecter");
        btnDonnee = new JButton("Envoyer donnees");
        btnColis = new JButton("Charger Colis");
        btnLivraison = new JButton("Faire Livraison");
        etatLabel = new JLabel();
        etatLabel.setFont(new Font("Arial", Font.BOLD, 14));
        etatLabel.setForeground(Color.pink);
     
        for (JComponent comp : new JComponent[] {
                btnDemarrer, btnArreter, btnModeEco, btnRecharger,
                btnConnect, btnDonnee, btnEffectuerTache, btnDeplacer,
                btnColis, btnLivraison, btnHistorique, etatLabel
        }) {
            comp.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelCommandes.add(Box.createVerticalStrut(10));
            panelCommandes.add(comp);
        }

    
        
        
        updateEtatLabel();
     
        add(panelCommandes, BorderLayout.EAST);
        add ( etatLabel, BorderLayout.NORTH);
  
        panelRobot = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRobot(g);
            }
        };

        panelRobot.setBackground(Color.WHITE);
        add(panelRobot, BorderLayout.CENTER);

       
        btnDemarrer.addActionListener(e -> {
            try {
                robot.demarrer();
                couleur=Color.green;
                updateEtatLabel();
                repaint();
            } catch (RobotException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnArreter.addActionListener(e -> {
            try {
				robot.arreter();
			} catch (RobotException e1) {
				
				 JOptionPane.showMessageDialog(this, e1.getMessage());
			}
            updateEtatLabel();
            couleur=Color.LIGHT_GRAY;
            updateEtatLabel();
            repaint();
        });

        btnDeplacer.addActionListener(e -> {
        	
           
            try {
            	if (!robot.enMarche) {
                    throw new RobotException("Le robot doit être démarré avant de se déplacer.");
                }
            	try {
            	 String x = JOptionPane.showInputDialog("Entrez la coordonnée X de destination : ");
            	
            	
            	
                 String y = JOptionPane.showInputDialog("Entrez la coordonnée Y de destination : ");
                 int destX = Integer.parseInt(x);
                 int destY = Integer.parseInt(y);
                 robot.deplacer(destX, destY);
                 robotX=destX;
                 robotY=destY;
                 updateEtatLabel();
                 repaint();}
            	
            	catch(NumberFormatException ex) {
            		JOptionPane.showMessageDialog(this,"valeur(s) manquante(s) de x et/ ou y");
              
              
            } 
            	}catch (RobotException   ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage());
            }
        });

        btnRecharger.addActionListener(e -> {
        	try {
       	 String r= JOptionPane.showInputDialog("Entrez la valeur de recharge : ");
            robot.rechargerSolaire(Integer.parseInt(r)); 
            updateEtatLabel();
            repaint();} catch( NumberFormatException ex) {
            	
            JOptionPane.showMessageDialog(this,ex.getMessage());}
        });

        btnEffectuerTache.addActionListener(e -> {
            try {
                robot.effectuerTache();
                updateEtatLabel();
                repaint();
            } catch (RobotException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        btnModeEco.addActionListener(e -> {
        	if(!robot.modeEco) {
           robot.activerModeEco();
           updateEtatLabel();
           JOptionPane.showMessageDialog(null,"Le robot est passé en mode économique pour économiser de l’énergie.");
           btnModeEco.setBackground(Color.YELLOW);
           repaint();}
        	else {
        		robot.desactiverModeEco();
        		 btnModeEco.setBackground(UIManager.getColor("Button.background"));
                repaint();
        	}
           
        });
        

     
        btnHistorique.addActionListener(e -> {
        	if(!robot.historiqueVisible) {
            
            if (scrollPane != null) {
                remove(scrollPane); 
            }

           
            historiqueTextArea = new JTextArea(10, 30);
            historiqueTextArea.setEditable(false);

           
            scrollPane = new JScrollPane(historiqueTextArea);

            
            String historique = robot.getHistorique();
            historiqueTextArea.setText(historique);

           
            add(scrollPane, BorderLayout.SOUTH);
            btnHistorique.setBackground(Color.YELLOW);
        	 robot.historiqueVisible = true;
        	 
        }
        	 else {
        		 remove(scrollPane);
        	        btnHistorique.setBackground(UIManager.getColor("Button.background"));

        	        robot.historiqueVisible = false;
        	       
        	 }
            revalidate(); 
           
        
            repaint();    
           
        });
        btnConnect.addActionListener(e -> {
        	try {
        	if (!robot.enMarche) {
                throw new RobotException("Le robot doit être démarré pour pouvoir connecter !");
            }
        	if(!robot.connecte) {
            try {
            	   String reseau = JOptionPane.showInputDialog("Veuillez entrer le nom du réseau auquel vous souhaitez connecter le robot : ");
                 
                robot.connecter(reseau);
              btnConnect.setBackground(Color.YELLOW);
                updateEtatLabel();
                repaint();
            } catch (RobotException ex ) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }}
            else {
            	   robot.deconnecter();
            	   btnConnect.setBackground(UIManager.getColor("Button.background"));
                   updateEtatLabel();
                   repaint();
            	   
            }}catch (RobotException   ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage());
            }}
            		
        );
        btnDonnee.addActionListener(e -> {
        	try {
        		if (!robot.enMarche) {
                    throw new RobotException("Le robot doit être démarré pour envoyer des données !");
                }
        		
        		 String donnee = JOptionPane.showInputDialog("Entrez la donnée à envoyer : ");
        		robot.envoyerDonnees(donnee);
        		
        	 } 
    	catch (RobotException   ex) {
        JOptionPane.showMessageDialog(this,ex.getMessage());
    }
        	
        	
        });
        btnColis.addActionListener(e -> {
        	try {
        		if (!robot.enMarche) {
                    throw new RobotException("Le robot doit être démarré pour charger colis !");
                }
        		 String dest = JOptionPane.showInputDialog("Entrez la destination de la livraison : ");
        		   if (dest == null || dest.trim().isEmpty()) {
        	          
        	            throw new IllegalArgumentException("La destination ne peut pas être vide !");
        	        }
        		 robot.chargerColis( dest);
        		 updateEtatLabel();
        		 repaint();
        		
        	}	catch (RobotException ex) {
                
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
               
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        	
        	
        	
        });
        btnLivraison.addActionListener(e -> {
        	try {
        		if (!robot.enMarche) {
                    throw new RobotException("Le robot doit être démarré pour faire la livraison !");
                }
        		if(!robot.colisActuel.equals("1")) {
        			throw new RobotException("il y'a pas de Colis à livrer!\n charger Colis avant de faire la livraison");
        		}
        		
        		 String x = JOptionPane.showInputDialog("Entrez la coordonnée X de destination : ");
             	
             	
             	
                 String y = JOptionPane.showInputDialog("Entrez la coordonnée Y de destination : ");
                 int destX = Integer.parseInt(x);
                 int destY = Integer.parseInt(y);
        		robot.FaireLivraison(destX,destY);
        		  robotX=destX;
                  robotY=destY;
        		repaint();
        		
        	 } 
    	catch (RobotException   ex) {
        JOptionPane.showMessageDialog(this,ex.getMessage());
    }
        	
        });
    
    }
      
    
   
    
     
       
    private void drawRobot(Graphics g) {
    
    	int screenX = 50 + robotX * 5;
        int screenY = 50 + robotY * 5;

        g.setColor(Color.GRAY);
        g.fillRect(screenX, screenY, 100, 60); 

       
       g.setColor(couleur);
         
       g.fillRect(screenX + 20, screenY - 40, 60, 40); 

        g.setColor(Color.BLACK);
       
      g.drawLine(screenX + 30, screenY - 40, screenX + 30, screenY - 60);
        g.drawLine(screenX + 50, screenY - 40, screenX + 50, screenY - 60);

        g.setColor(Color.WHITE);
       
      g.fillOval(screenX + 30, screenY - 30, 10, 10);
        g.fillOval(screenX + 50, screenY - 30, 10, 10);
        
        g.setColor(Color.BLACK);
        g.fillOval(screenX + 10, screenY + 60, 20, 20);
        g.fillOval(screenX + 70, screenY + 60, 20, 20);

        g.drawString(robot.id, screenX + 25, screenY + 90);
        if (robot.enLivraison) {
            g.setColor(Color.ORANGE); 
            g.fillRect(screenX + 35, screenY + 10, 30, 30); 
            g.setColor(Color.BLACK);
            g.drawRect(screenX + 35, screenY + 10, 30, 30); 
        }}
        private void updateEtatLabel() {
        	String texte = String.format(
        		    "État du robot : %s | Énergie : %d%% | Quantité de CO2 émis : %.2f g | Mode éco : %s | Livraison : %s | Destination : %s",
        		    robot.enMarche ? "En marche" : "Éteint",
        		    robot.energie, 
        		    robot. getEmpreinteCarbone(),
        		    robot.modeEco ? "activé" : "désactivé",
        		    robot.enLivraison ? "En cours" : "Aucune",
        		    robot.destination != null ? robot.destination : "aucune"
        		);

            etatLabel.setText(texte);
        }
    

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            new InterfaceRobot().setVisible(true);
        });
    }
}

