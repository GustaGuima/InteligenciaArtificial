package gramado;

public class Principal {

	public static void main(String[] args) {
		Gramado gramado = new Gramado();

		gramado.atribuirFormigueiro();
		System.out.println();

		System.out.println();
		gramado.mostrarGramado();
		
		System.out.println(gramado.andarGramado());
		
		gramado.mostrarGramado();

	}

}
