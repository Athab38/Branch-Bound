
public class Main {

	public static void main(String[] args) {
		
		// create new 6*6 matrix and print it (values initialized in constructor)
		Matrice mx = new Matrice(6);
		System.out.println(mx.matricetoString());
		
		// minimize each line and keep the total
		int ligneSuppr = mx.minimisationMatriceLigne();
		System.out.println(ligneSuppr);
		System.out.println(mx.matricetoString());
		
		// minimize each column and keep the total
		int colonneSuppr = mx.minimisationMatriceColonne();
		System.out.println(colonneSuppr);
		System.out.println(mx.matricetoString());
		
		// compute regrets' values
		mx.calculRegrets();
		System.out.println(mx.regrettoString());
		
		// create the binary tree, root's value is the sum of what has been substracted in each line & column
		Noeud node = new Noeud(ligneSuppr+colonneSuppr, 1);
		int[] regretMax = mx.rechercheRegretMax();
		System.out.println(regretMax[2]);
		
		// create the left son, root's value is the sum of father's root value and maximum regret 
		node.setFilsg(new Noeud(node.getRacine() + regretMax[2], 2));
		
		// delete the line & the column of the maximum regret
		mx.supprimerLigne(regretMax[0]);
		mx.supprimerColonne(regretMax[1]);
		System.out.println(mx.matricetoString());
		
		
		// eliminate mirror path		
		mx.setCaseMatrice(regretMax[1], regretMax[0]);
		System.out.println(mx.matricetoString());
		
		
		// minimize each line and keep the total
		ligneSuppr = mx.minimisationMatriceLigne();
		System.out.println(ligneSuppr);
		System.out.println(mx.matricetoString());
		
		
		// minimize each column and keep the total
		colonneSuppr = mx.minimisationMatriceColonne();
		System.out.println(colonneSuppr);
		System.out.println(mx.matricetoString());
		
		
		// create the right son, root's value is the sum of father's root value and what has been substracted in each line & column
		node.setFilsd(new Noeud(node.getRacine() + ligneSuppr + colonneSuppr, 3));
		System.out.println(node.toString());
		
		//TODO : create Pile, and other iterations...
	}

}
