package gramado;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Interface extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;

	//Janela Game
	public static JFrame frame;
			
	//Dimensoes da Janela Game
	private final int WIDTH = 825;
	private final int HEIGHT = 825;
	//private final int SCALE = 3;
	
	//Thread para rodar simultaneamente
	private Thread thread;
	
	private boolean isRunning;
	
	private BufferedImage image;
	//private BufferedImage bg;
	
	int espacamentoX=0, espacamentoY=0;
	
	private Rectangle[][] gramadoInterface;
	Gramado campo;
	
	private int posCortadorX = 0, posCortadorY = 0;
	
	private int frames = 0, maxFrames = 50;

	public static void main(String[] args) {
		Interface in = new Interface();
		in.start();
	}
	
	int contadorI = 0, contadorJ = 0;
	
	public Interface() {
		campo = new Gramado();
		campo.atribuirFormigueiro();
		this.setPreferredSize(new Dimension(WIDTH , HEIGHT ));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT , BufferedImage.TYPE_INT_RGB);
		
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				gramadoInterface[i][j] = new Rectangle(espacamentoX,espacamentoY, 100,100);
				espacamentoX += 120;
			}
			espacamentoY += 120;
			espacamentoX = 0;
		}	
	}
	
	private void initFrame() {
		frame = new JFrame("Cortador Grama");
		frame.add(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		gramadoInterface = new Rectangle[7][7];
		
	}
	
	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		frames++;
		if(frames >= maxFrames) {
			frames = 0;
			
			if(campo.gramado[contadorI][contadorJ] == 0) {
				campo.gramado[contadorI][contadorJ] = 2;
			}
			
			posCortadorX = gramadoInterface[contadorI][contadorJ].x;
			posCortadorY = gramadoInterface[contadorI][contadorJ].y;
			
			contadorJ++;
			if(contadorJ > 6) {
				contadorI++;
				contadorJ = 0;
			}
			
			if(contadorI > 6) {
				contadorI = 0;
			}
		}
	}
	
	public void render() {
		//Quantidade de buffer para otimizar a renderizaçao
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(Color.black);
		//Desenha um retangulo
		g.fillRect(0, 0, WIDTH, HEIGHT);
		espacamentoX = 0;
		espacamentoY = 0;
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				gramadoInterface[i][j] = new Rectangle(espacamentoX,espacamentoY, 100,100);
				if(campo.gramado[i][j] == 1) {
					g.setColor(Color.red);
				}if(campo.gramado[i][j] == 0) {
					g.setColor(new Color(0,150,0));
				} if(campo.gramado[i][j] == 2) {
					g.setColor(Color.green);
				}
				g.fillRect(gramadoInterface[i][j].x, gramadoInterface[i][j].y,
						   gramadoInterface[i][j].width, gramadoInterface[i][j].height);
				espacamentoX += 120;
			}
			espacamentoY += 120;
			espacamentoX = 0;
		}
		
		g.setColor(Color.blue);
		g.fillOval(posCortadorX, posCortadorY, 50, 50);

		g.dispose();
		
		g = bs.getDrawGraphics();
		//g2.drawImage(bg, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		bs.show();
		
		
		/***/
		
	}

	@Override
	public void run() {
		//Ultimo tempo em nano segundos pela precisao
		long lastTime = System.nanoTime();
		// 60 frames por segundos(Rodar em 60 fps)
		double amountOfTick = 60.0;
		//1000000000 = 1 seg em nano segundos / pelos frames por segundo, sera o calculo pra ver
		//o momento correto de dar o update e renderizar o game
		double ns = 1000000000 / amountOfTick;
		double delta = 0;
		
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		while(isRunning) {
			//Tempo atual que foi rodado o game
			long now = System.nanoTime();
			//Tempo atual - tempo anterior / ns
			delta += (now - lastTime) / ns;
			//Tempo anterior passa ser o atual nesse Loop
			lastTime = now;
			// Se calculo der 1 seg ou mais atualiza o game e renderiza e aumenta um frame
			// e reseta o delta
			if(delta >= 1) {
				update();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer += 1000;
			}
		}
		
		stop();
	}
}
