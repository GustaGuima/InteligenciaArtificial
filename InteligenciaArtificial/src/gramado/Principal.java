package gramado;

public class Principal {

	public static void main(String[] args) {
		Gramado gramado = new Gramado();

		gramado.mostrarGramado();

		gramado.atribuirFormigueiro();
		System.out.println();

		gramado.mostrarGramado();

		System.out.println();

		System.out.println(gramado.andarGramado());
	}

}
