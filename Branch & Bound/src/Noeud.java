
public class Noeud {
	
	public int num;
	public int racine;
	public Noeud filsg;
	public Noeud filsd;

	
	public Noeud (int val, int num) {
		this.racine = val;
		this.num = num;
		this.filsd = null;
		this.filsg = null; 
	}


	public Noeud getFilsg() {
		return filsg;
	}


	public void setFilsg(Noeud filsg) {
		this.filsg = filsg;
	}


	public Noeud getFilsd() {
		return filsd;
	}


	public void setFilsd(Noeud filsd) {
		this.filsd = filsd;
	}


	public int getRacine() {
		return racine;
	}
	
	// should be recursive but flemme
	@Override
	public String toString() {
		String ch = "";
		ch += "père : " + this.getRacine() + " / fils gauche : " + this.getFilsg().getRacine() + " / fils droit : " + this.getFilsd().getRacine();
		return ch;
	}
}
