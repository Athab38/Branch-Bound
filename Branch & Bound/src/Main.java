
public class Main {

	public static void main(String[] args) {
		Matrice mx = new Matrice(6);
		System.out.println(mx.toString());
		System.out.println(mx.minimisationMatriceLigne());
		System.out.println(mx.toString());
		System.out.println(mx.minimisationMatriceColonne());
		System.out.println(mx.toString());

	}

}
