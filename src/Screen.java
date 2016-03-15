import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;




public class Screen extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	private final static int CELL_SIZE = 25;
	
	private static final int SIZE = CELL_SIZE;

	private int width;
	private int height;

	private int xBoneco;
	private int yBoneco;

	private int xBot;
	private int yBot;
	
	private int xPos;
	private int yPos;
	
	private int xMeme;
	private int yMeme;
		
	Stack<Crumb> stack = new Stack<Crumb>();
	
	public int getxBot() {
		return xBot;
	}

	public int getyBot() {
		return yBot;
	}
	
	private Image image;
	private Image imageBot;
	private Image meme;
	

	private boolean[][] labyrinth;
	private boolean[][] labirinto;
	
	Timer timer = new Timer(10, this);

	public Screen(boolean[][] labyrinth) {
		this.labyrinth = labyrinth;
		labirinto = deepCopy(labyrinth);
		
		this.width = this.labyrinth[0].length;
		this.height = this.labyrinth.length;

		xBoneco = SIZE / 2;
		yBoneco = SIZE / 2;
		
		xBot = SIZE / 2;
		yBot = SIZE / 2;
		
		Random r = new Random();
		
		while(true) {
			xMeme = r.nextInt(this.width);
			yMeme = r.nextInt(this.height);
						
			if(labyrinth[yMeme][xMeme]) {
				break;
			}
		}
		
		xMeme = xMeme * SIZE;
		yMeme = yMeme * SIZE;
		System.out.println("xMeme: " + xMeme + " yMeme: " + yMeme);
					
		image = new ImageIcon(getClass().getResource("/img/fabiomiranda.png")).getImage();
		imageBot = new ImageIcon(getClass().getResource("/img/example.png")).getImage();
		meme = new ImageIcon(getClass().getResource("/img/coolface.jpg")).getImage();
		
		int xPos = (xBot - SIZE / 2) / SIZE;
    	int yPos = (yBot - SIZE / 2) / SIZE;

		stack.push(new Crumb(xPos, yPos));
		labirinto[yPos][xPos] = false;	
		
		timer.start();
		
		setPreferredSize(new Dimension(this.width * CELL_SIZE, this.height * CELL_SIZE));
	}

	public void paintComponent(Graphics g) {
		for(int i = 0; i < this.height; i++) {
			int y = i * CELL_SIZE;

			for(int j = 0; j < this.width; j++) {
				int x = j * CELL_SIZE;

				if(!labirinto[i][j] && labyrinth[i][j]) {
					g.setColor(Color.RED);
				}
				else if(labyrinth[i][j]) {
					g.setColor(Color.WHITE);
				}
				else {
					g.setColor(Color.BLACK);
				}

				g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
			}
		}

    	
		// Por fim, desenhamos a imagem.
    	g.drawImage(image, xBoneco - SIZE / 2, yBoneco - SIZE / 2, SIZE, SIZE, null);
    	g.drawImage(imageBot, xBot - SIZE / 2, yBot - SIZE / 2, SIZE, SIZE, null);
    	g.drawImage(meme, xMeme, yMeme, SIZE, SIZE, null);
    	
    	getToolkit().sync();
    }
    
	public void keyPressed(KeyEvent e) {
    	int key = e.getKeyCode();
    	int xPos = (xBoneco - SIZE / 2) / SIZE;
    	int yPos = (yBoneco - SIZE / 2) / SIZE;
   	
    	// Se a tecla apertada foi a seta para a esquerda...
    	if(key == KeyEvent.VK_LEFT) {
	    	// checamos se ele pode ir para a direção desejada	
    		if(xPos != 0 && labyrinth[yPos][xPos-1]) {
	    		// ...movemos o boneco para a esBLACKquerda...
	    		xBoneco -= SIZE;
	    		// ...e redesenhamos a tela.
	    		repaint();
    		}
    	}

    	// Se a tecla apertada foi a seta para a direita...
    	if(key == KeyEvent.VK_RIGHT) {
    		// podemos ir para direita?
    		if(xPos != width-1 && labyrinth[yPos][xPos+1]) {
	    		// ...movemos o boneco para a direita!
	    		xBoneco += SIZE;
	    		// ...e redesenhamos a tela.
	    		repaint();
	    	}
    	}
    	
    	if(key == KeyEvent.VK_DOWN) {
    		// para baixo...
    		if(yPos != height-1 && labyrinth[yPos+1][xPos]) {
	    		// movemos para baixo
	    		yBoneco += SIZE;
	    		repaint();
	    	}
    	}
    	
    	if(key == KeyEvent.VK_UP) {
    		// para cima!
    		if(yPos != 0 && labyrinth[yPos-1][xPos]) {
	    		// movemos para cima
	    		yBoneco -= SIZE;
	    		repaint();
	    	}
    	}    	
	}
    

	public void keyReleased(KeyEvent event) {
	}
	
	public void keyTyped(KeyEvent event) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {	
		System.out.println("xMeme: " + xMeme + " yMeme: " + yMeme);
		try {
			// Caso a stack esteja vazia, ele não passará pelo erro graças à este try
			if(xMeme == (xBoneco - SIZE / 2) && yMeme == (yBoneco - SIZE / 2)) {
				System.out.println("Você ganhou! Parabéns!");
				timer.stop();
			}
			
			else if(xMeme == (xBot - SIZE / 2) && yMeme == (yBot - SIZE / 2)) {
				System.out.println("HHAHAH VOCÊ PERDEU!");
				timer.stop();
			} 
			
			else {				
				// "Anda" pelo labirinto
				Crumb crumb = stack.peek();
				
				if(stack.peek().getPasses() == 0) {
					if(xPos > 0 && labirinto[yPos][xPos-1]) {
						// Esquerda
						labirinto[yPos][xPos] = false;
						xPos--;
						System.out.println(stack.peek().getPasses() + " Esquerda " + stack.size());
						stack.push(new Crumb(xPos, yPos));
						repaint();
					}
					crumb.incrementPasses();
				}			
				
				
				else if(stack.peek().getPasses() == 1) {
					if(xPos < width-1 && labirinto[yPos][xPos+1]) {
						// Direita
						labirinto[yPos][xPos] = false;
						xPos++;
						System.out.println(stack.peek().getPasses() + " Direita " + stack.size());
						stack.push(new Crumb(xPos, yPos));
						repaint();
					}
					crumb.incrementPasses();
				}
				
				
				else if(stack.peek().getPasses() == 2) {
					if(yPos > 0 && labirinto[yPos-1][xPos]) {
						// Cima
						labirinto[yPos][xPos] = false;
						yPos--;
						System.out.println(stack.peek().getPasses() + " Cima " + stack.size());
						stack.push(new Crumb(xPos, yPos));
						repaint();
					}
					crumb.incrementPasses();
				}
		
				
				else if(stack.peek().getPasses() == 3) {
					if(yPos < height-1 && labirinto[yPos+1][xPos]) {
						// Baixo
						labirinto[yPos][xPos] = false;
						yPos++;
						System.out.println(stack.peek().getPasses() + " Baixo " + stack.size());
						stack.push(new Crumb(xPos, yPos));
						repaint();
					}
					crumb.incrementPasses();
				}
				
				
				else if(stack.peek().getPasses() == 4){
					// Terminou este passo
					labirinto[yPos][xPos] = false;
					stack.pop();
					System.out.println(stack.size());
					xPos = stack.peek().getX();
					yPos = stack.peek().getY();
		
					repaint();
				}
				
				yBot = yPos * SIZE + SIZE / 2;
				xBot = xPos * SIZE + SIZE / 2;
				
				System.out.println(yBot - SIZE / 2);
				System.out.println(xBot - SIZE / 2);
		
			}
		} catch(EmptyStackException e) {
			// Caso a stack esteja vazia, isso significa que a IA já varreu todos os pontos do labirinto
			System.out.println("Já varri todas as opções existentes nesse labirinto!");
		
		}
	}	
	
	public static boolean[][] deepCopy(boolean[][] original) {
	    // Hash, me desculpe, mas roubei este método de um generoso 
		// senhor que ensinava como copiar os valores de uma matriz
		// em uma nova, não o seu endereço de memória
				
		if (original == null) {
	        return null;
	    }

	    final boolean[][] result = new boolean[original.length][];
	    for (int i = 0; i < original.length; i++) {
	        result[i] = Arrays.copyOf(original[i], original[i].length);
	        // For Java versions prior to Java 6 use the next:
	        // System.arraycopy(original[i], 0, result[i], 0, original[i].length);
	    }
	    return result;
	}
}	
	
