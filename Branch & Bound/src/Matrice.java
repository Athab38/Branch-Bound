
public class Matrice {

	int[][] matrice;
	int[][] regret; 
	public static final int maxval = 9999;
	// nbC is useless because we'll always have a square matrix !!
	private int nbL = 6;
	
	// create test matrix
	public Matrice(int taille) {
		this.nbL = taille;
		matrice = new int[this.nbL][this.nbL];
		regret = new int[this.nbL][this.nbL];
		// set max values for cycles
		for (int i = 0; i < this.nbL; i++) {
			matrice[i][i] = maxval;
		}
		// set arbitrary values for other paths
		// line 0
		matrice[0][1] = 780;
		matrice[0][2] = 320;
		matrice[0][3] = 580;
		matrice[0][4] = 480;
		matrice[0][5] = 660;
		// line 1
		matrice[1][0] = 780;
		matrice[1][2] = 700;
		matrice[1][3] = 460;
		matrice[1][4] = 300;
		matrice[1][5] = 200;
		// line 2
		matrice[2][0] = 320;
		matrice[2][1] = 700;
		matrice[2][3] = 380;
		matrice[2][4] = 820;
		matrice[2][5] = 630;
		// line 3
		matrice[3][0] = 580;
		matrice[3][1] = 460;
		matrice[3][2] = 380;
		matrice[3][4] = 750;
		matrice[3][5] = 310;
		// line 4
		matrice[4][0] = 480;
		matrice[4][1] = 300;
		matrice[4][2] = 820;
		matrice[4][3] = 750;
		matrice[4][5] = 500;
		// line 5
		matrice[5][0] = 660;
		matrice[5][1] = 200;
		matrice[5][2] = 630;
		matrice[5][3] = 310;
		matrice[5][4] = 500;
		
		// set all regrets at -1
		for (int i = 0; i < this.nbL; i++) {
			for (int j = 0; j < this.nbL; j++) {
				this.regret[i][j] = -1;
			}
		}
		
	}
	
	// quick & dirty code but it's just a test sample D:
	@Override
	public String toString() {
		String ch = "";
		// add matrix's values to ch
		for (int i = 0; i < this.nbL; i ++) {
			for (int j = 0; j < this.nbL; j++) {
				ch += matrice[i][j] + " ";
			}
			ch += "\n";
		}
		return ch;
	}
	
	// retrieve minimum on each line, then substract this minimum on each line, then return the total substracted
	public int minimisationMatriceLigne () {
		// initialize min & total
		int total = 0;
		int min = Matrice.maxval;
		// for each line, search for the minimum & substract on each column
		for (int i = 0; i < this.nbL; i++) {
			min = rechercheMin(this.matrice[i]);
			for (int j = 0; j < this.nbL; j++) {
				// don't touch at maxval value
				if (i != j)
					this.matrice[i][j] -= min;
			}
			total += min;
		}
		return total;
	}
	
	// retrieve minimum on each column, then substract this minimum on each line, then return the total substracted
	// this one sucks a little more because I can't extract directly a column from the matrix
	public int minimisationMatriceColonne () {
		// initialize min & total
		int total = 0;
		int min = Matrice.maxval;
		// for each line, search for the minimum & substract on each column
		for (int j = 0; j < this.nbL; j++) {
			// set column's values
			min = this.rechercheMin(this.extraireColonne(j));
			for (int i = 0; i < this.nbL; i++) {
				// don't touch at maxval value
				if (i != j)
					this.matrice[i][j] -= min;
			}
			total += min;
		}
		return total;
	}
	
	// create a tab, with all values from a column
	private int[] extraireColonne (int numCol) {
		int[] column = new int[this.nbL];
		for (int i = 0; i < this.nbL; i++) {
			column[i] = this.matrice[i][numCol];
		}
		return column;
	}
	
	// basic minimum search 
	private int rechercheMin(int[] tab) {
		int min = Matrice.maxval;
		for (int i = 0; i < this.nbL; i++) {
			if (tab[i] < min)
				min = tab[i];
		}
		return min;
	}
	
	// update regrets' values
	public void calculRegrets () {
		// needed to compute the regret
		int minLigne = 0;
		int minColonne = 0;
		for (int i = 0; i < this.nbL; i++) {
			for (int j = 0; j < this.nbL; j++) {
				if (this.matrice[i][j] == 0) {
					// retrieve minimum on line & column
					minLigne = this.rechercheMin(this.matrice[i]);
					minColonne = this.rechercheMin(extraireColonne(j));
					// update regret's value in corresponding case
					this.regret[i][j] = minLigne + minColonne;
				}
			}
		}
	}
}
