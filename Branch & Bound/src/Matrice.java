
public class Matrice {

	Integer[][] matrice;
	Integer[][] regret; 
	public static final int maxval = 9999;
	// nbC is useless because we'll always have a square matrix !!
	private int nbL = 6;
	
	// create test matrix
	public Matrice(int taille) {
		this.nbL = taille;
		matrice = new Integer[this.nbL][this.nbL];
		regret = new Integer[this.nbL][this.nbL];
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
	public String matricetoString() {
		String ch = "";
		// add matrix's values to ch
		for (int i = 0; i < this.nbL; i ++) {
			if (this.matrice[i] != null) {
				for (int j = 0; j < this.nbL; j++) {
					if (this.matrice[i][j] != null) {
						ch += this.matrice[i][j] + " ";
					}
				}
				ch += "\n";
			}
		}
		return ch;
	}
	
	// quick & dirty code but it's just a test sample D:
	public String regrettoString() {
		String ch = "";
		// add matrix's values to ch
		for (int i = 0; i < this.nbL; i ++) {
				if (this.regret[i] != null) {
				for (int j = 0; j < this.nbL; j++) {
					if (this.regret[i][j] != null) {
						ch += this.regret[i][j] + " ";
					}
				}
				ch += "\n";
			}
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
			if (this.matrice[i] != null) {
				min = rechercheMin(this.matrice[i]);
				for (int j = 0; j < this.nbL; j++) {
					// don't touch at maxval value
					if (this.matrice[i][j] != null && i != j)
						this.matrice[i][j] -= min;
				}
				total += min;
			}
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
			// if [0][j] is null, the whole column is
			if (this.matrice[0][j] != null) {
				// set column's values
				min = this.rechercheMin(this.extraireColonne(j));
				for (int i = 0; i < this.nbL; i++) {
					// don't touch at maxval value
					if (this.matrice[i] != null && i != j)
						this.matrice[i][j] -= min;
				}
				total += min;
			}
		}
		return total;
	}
	
	// create a tab, with all values from a column
	private Integer[] extraireColonne (int numCol) {
		Integer[] column = new Integer[this.nbL];
		for (int i = 0; i < this.nbL; i++) {
			if (this.matrice[i] != null) {
				column[i] = this.matrice[i][numCol];
			}
		}
		return column;
	}
	
	// basic minimum search 
	private int rechercheMin(Integer[] tab) {
		int min = Matrice.maxval;
		for (int i = 0; i < this.nbL; i++) {
			if (tab[i] != null && tab[i] < min)
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
					// retrieve minimum on line & column (we have to skip the case containing the 0)
					minLigne = this.rechercheMinSkip(this.matrice[i], j);
					minColonne = this.rechercheMinSkip(extraireColonne(j), i);
					// update regret's value in corresponding case
					this.regret[i][j] = minLigne + minColonne;
				}
			}
		}
	}
	
	// basic minimum search with skip, to avoid to take in account the 0 itself
	private int rechercheMinSkip(Integer[] tab, int ind) {
		int min = Matrice.maxval;
		for (int i = 0; i < this.nbL; i++) {
			if (i != ind && tab[i] < min)
				min = tab[i];
		}
		return min;
	}
	
	// retrieve maximum regret 
	public int[] rechercheRegretMax() {
		// max[0] -> line / max[1] -> column / max[2] -> value /
		int[] max = new int [3];
		max[2] = -1;
		for (int i = 0; i < this.nbL; i++) {
			for (int j = 0; j < this.nbL; j++) {
				if (this.regret[i][j] > max[2]) {
					max[2] = this.regret[i][j];
					max[0] = i;
					max[1] = j;
				}
			}
		}
		return max;
	}
	// set line @ null
	public void supprimerLigne(int ligne) {
		this.matrice[ligne] = null;
		this.regret[ligne] = null;
	}
	
	// set column @ null 
	public void supprimerColonne(int colonne) {
		for (int i = 0; i < this.nbL; i++) {
			if (this.matrice[i] != null) {
				this.matrice[i][colonne] = null;
				this.regret[i][colonne] = null;
			}
		}
	}
	
	public void setCaseMatrice(int ligne, int colonne) {
		this.matrice[ligne][colonne] = Matrice.maxval;
	}
}
