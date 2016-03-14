import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;




public class Screen extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;

	private final static int CELL_SIZE = 25;
	
	private static final int SIZE = CELL_SIZE;
//	private static final int BORDER = 0;

	private int width;
	private int height;

//	private int xLeft;
//	private int xRight;

	private int xBoneco;
	private int yBoneco;
	
	public int getxBot() {
		return xBot;
	}

	public int getyBot() {
		return yBot;
	}

	private int xBot;
	private int yBot;

	private Image image;
	private Image imageBot;


	private boolean[][] labyrinth;

	public Screen(boolean[][] labyrinth) {
		this.labyrinth = labyrinth;

		this.width = this.labyrinth[0].length;
		this.height = this.labyrinth.length;

		xBoneco = SIZE / 2;
		yBoneco = SIZE / 2;
		
		xBot = width * 25 - SIZE / 2;
		yBot = SIZE / 2;
		System.out.println(xBot);
		System.out.println(yBot);
		
		image = new ImageIcon(getClass().getResource("/img/fabiomiranda.png")).getImage();
		imageBot = new ImageIcon(getClass().getResource("/img/example.png")).getImage();

		
		Timer timer = new Timer(1000, this);
		timer.start();

		setPreferredSize(new Dimension(this.width * CELL_SIZE, this.height * CELL_SIZE));

//		botAI();
	}

	public void paintComponent(Graphics g) {
		for(int i = 0; i < this.height; i++) {
			int y = i * CELL_SIZE;

			for(int j = 0; j < this.width; j++) {
				int x = j * CELL_SIZE;

				if(labyrinth[i][j]) {
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
    	
    	botAI();
    	
    	getToolkit().sync();
    }
    
	public void keyPressed(KeyEvent e) {
    	int key = e.getKeyCode();
    	int xPos = (xBoneco - SIZE / 2) / SIZE;
    	int yPos = (yBoneco - SIZE / 2) / SIZE;
		System.out.println(xPos);
		System.out.println(yPos);    	    	
    	
    	// Se a tecla apertada foi a seta para a esquerda...
    	if(key == KeyEvent.VK_LEFT) {
	    	// checamos se ele pode ir para a direção desejada	
    		if(xPos != 0 && labyrinth[yPos][xPos-1]) {
	    		// ...movemos o boneco para a esquerda...
	    		xBoneco -= SIZE;
	    		// ...e redesenhamos a tela.
	    		repaint();
    		}
    	}

    	// Se a tecla apertada foi a seta para a direita...
    	if(key == KeyEvent.VK_RIGHT) {
    		// podemos ir para direita?
    		if(xPos != width && labyrinth[yPos][xPos+1]) {
	    		// ...movemos o boneco para a direita!
	    		xBoneco += SIZE;
	    		// ...e redesenhamos a tela.
	    		repaint();
	    	}
    	}
    	
    	if(key == KeyEvent.VK_DOWN) {
    		// para baixo...
    		if(yPos != height && labyrinth[yPos+1][xPos]) {
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
    
	public void botAI() {
		Node nodes[][] = new Node[height][width];
		
		for(int i = 0; i >= height; i++) {
			for(int j = 0; j >= width; j++) {
				nodes[i][j] = new Node(i, j);
				
			}
		}

		
//		para testes
//		
//		
//		for (int i = 0; i < nodes.length; i++) {
//		    for (int j = 0; j < nodes[0].length; j++) {
//		        System.out.print(nodes[i][j] + " ");
//		    }
//		    System.out.print("\n");
//		}
//		
		Stack<Crumb> stack = new Stack<Crumb>();
		stack.push(new Crumb(nodes[xBot][yBot]));
		
		while(!stack.empty()) {
			Crumb crumb = new Crumb(stack.peek().getNode());
			
			if (crumb.getNode().getLeft() != null && labyrinth[yBot][xBot-1]) {
				Crumb crumb2 = new Crumb(crumb.getNode().getLeft());
				crumb.getNode().setLeft(null);
				stack.push(crumb2);
				xBot--;
				
			} else if (crumb.getNode().getRight() != null && labyrinth[yBot][xBot+1]) {
				Crumb crumb2 = new Crumb(crumb.getNode().getRight());
				crumb.getNode().setRight(null);
				stack.push(crumb2);
				xBot++;
				
			} else if (crumb.getNode().getUp() != null && labyrinth[yBot-1][xBot]) {
				Crumb crumb2 = new Crumb(crumb.getNode().getRight());
				crumb.getNode().setRight(null);
				stack.push(crumb2);
				yBot--;
				
			} else if (crumb.getNode().getDown() != null && labyrinth[yBot+1][xBot]) {
				Crumb crumb2 = new Crumb(crumb.getNode().getRight());
				crumb.getNode().setRight(null);
				stack.push(crumb2);
				yBot++;
				
			} else {
				stack.pop();
				
			}
		}
	}
	
	public void keyReleased(KeyEvent event) {
	}
	
	public void keyTyped(KeyEvent event) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
