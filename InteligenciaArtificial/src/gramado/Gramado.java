package gramado;

import java.util.Random;

public class Gramado {

	public int[][] gramado;
	
	int contador = 0;
	int linha = 7, coluna = 7;
	int linhaAux, colunaAux;
	int linhaRandom, colunaRandom, quantidadeFormigueiros = 7;
	int quantidadeAcoes = 0;

	public Gramado() {
		gramado = new int[linha][coluna];

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				gramado[i][j] = 0;
			}
		}
		gramado[linhaAux = linha -1][colunaAux = coluna -1] = 3;
	}

	public void mostrarGramado() {

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.print(((gramado[i][j] == 0)? "GR" : (gramado[i][j] == 1)? "FM" : (gramado[i][j] == 4)? "GA" : "ST") + " ");
			}
			System.out.println();
		}

	}

	public void atribuirFormigueiro() {
		Random random = new Random();
		for(int i = 0; i < quantidadeFormigueiros; i++) {
			linhaRandom = random.nextInt(7);
			colunaRandom = random.nextInt(7);
			while(gramado[linhaRandom][colunaRandom] == 1 || gramado[linhaRandom][colunaRandom] == 3) {
				linhaRandom = random.nextInt(7);
				colunaRandom = random.nextInt(7);
			}
			gramado[linhaRandom][colunaRandom] = 1;
		}
		
	}

	public boolean andarGramado() {
		int i = 0, j = 0;

		while (i != 7) {
			while(j != 7){
				while(gramado[i][j] == 1) {
					if (gramado[i][j] == 1) {
						if (i == 6) {
							i--;
							if(j == 6) {
								j--;
							}else {
								j++;
							}
						} else if (i >= 0) {
							i++;
							if(j == 6) {
								j--;
							}else {
								j++;
							}
						}
					}
				}
				quantidadeAcoes++;
				if (gramado[i][j] == 3) {
					return true;
				}else {
					gramado[i][j] = 4;
				}
				if(contador == 7) {
					j--;
					if(j == 0) {
						i++;
						contador = 0;
					}
				}else {
					j++;
				}
			}
			System.out.println();
			if(i < 7) {
				i++;
				contador = 7;
				j = 6;
			}
		}
		return false;
	}
	
	public void cls() {
		for (int i=0; i<50; i++)
		{
		    System.out.println();
		}
	}

}
