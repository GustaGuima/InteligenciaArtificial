package gramado;

public class Gramado {

	public int[][] gramado;

	public Gramado() {
		gramado = new int[7][7];

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				gramado[i][j] = 0;
			}
		}
	}

	public void mostrarGramado() {

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.print(gramado[i][j]);
			}
			System.out.println();
		}

	}

	public void atribuirFormigueiro() {
		gramado[1][4] = 1;
		gramado[2][1] = 1;
		gramado[2][5] = 1;
		gramado[3][2] = 1;
		gramado[5][5] = 1;

		gramado[6][6] = 3;
	}

	public boolean andarGramado() {
		int i = 0, j = 0;

		while (i != 7) {
			for (j = 0; j < 7; j++) {
				if (gramado[i][j] == 1) {
					if (i >= 0) {
						i++;
					} else if (i == 6) {
						i--;
					}
				}
				System.out.println(gramado[i][j] + " - Posicao: " + i + ", " + j);
				if (gramado[i][j] == 3) {
					return true;
				}
			}
			if (i < 7) {
				i++;
			}
		}
		return false;
	}

}
