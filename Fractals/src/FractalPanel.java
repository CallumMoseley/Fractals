import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class FractalPanel extends JPanel implements MouseListener,
		MouseMotionListener, KeyListener
{
	private int iterations;
	private int n;
	private int fractalNum;

	public FractalPanel()
	{
		setPreferredSize(new Dimension(1920, 1080));

		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);

		iterations = 1;
		n = 6;
		fractalNum = 0;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.BLACK);
		g.drawString("Iterations: " + iterations, 5, 15);
		if (fractalNum == 0)
		{
			g.drawString("n: " + n, 5, 25);
			drawFractal(g, iterations, n, 300, getWidth() / 2, getHeight() / 2);
		}
		else if (fractalNum == 1)
		{
			sierpinskiCarpet(g, iterations, getWidth() / 2, getHeight() / 2, 1000);
		}
		else if (fractalNum == 2)
		{
			sierpinskiTriangle(g, iterations, getWidth() / 2, getHeight() / 2, 500);
		}
	}

	private void drawFractal(Graphics g, int iter, int n, double size,
			double x, double y)
	{
		if (iter == 0 || size < 0.25)
		{
			// g.fillRect((int) x, (int) y, 1, 1);
			return;
		}

		for (double angle = 0; angle < 2 * Math.PI; angle += Math.PI * 2 / n)
		{
			double xDiff = Math.cos(angle);
			double yDiff = Math.sin(angle);
			g.drawLine((int) x, (int) y, (int) (x + xDiff * size),
					(int) (y + yDiff * size));
			drawFractal(g, iter - 1, n, size / 3, x + xDiff * size, y
					+ yDiff * size);
		}
	}

	private void sierpinskiCarpet(Graphics g, int iter, double x, double y,
			double size)
	{
		if (iter == 0)
		{
			g.setColor(Color.BLACK);
			g.fillRect((int) (x - size / 2), (int) (y - size / 2), (int) size,
					(int) size);
			return;
		}
		g.setColor(Color.BLACK);
		g.fillRect((int) (x - size / 2), (int) (y - size / 2), (int) size,
				(int) size);
		g.setColor(Color.WHITE);
		g.fillRect((int) (x - size / 6), (int) (y - size / 6),
				(int) (size / 3), (int) (size / 3));

		for (int xd = -1; xd <= 1; xd++)
		{
			for (int yd = -1; yd <= 1; yd++)
			{
				if (xd != 0 || yd != 0)
				{
					sierpinskiCarpet(g, iter - 1, x + (xd * size / 3), y
							+ (yd * size / 3), size / 3);
				}
			}
		}
	}

	private void sierpinskiTriangle(Graphics g, int iter, double x, double y,
			double size)
	{
		if (iter == 0)
		{
			g.setColor(Color.BLACK);
			drawTriangle(g, x, y, size, 0);
			return;
		}

		g.setColor(Color.BLACK);
		drawTriangle(g, x, y, size, 0);
		g.setColor(Color.WHITE);
		drawTriangle(g, x, y, size / 2, Math.PI / 3);

		sierpinskiTriangle(g, iter - 1, x, y - size / 2, size / 2);
		sierpinskiTriangle(g, iter - 1, x + Math.cos(Math.PI * 11 / 6) * size
				/ 2, y - Math.sin(Math.PI * 11 / 6) * size / 2, size / 2);
		sierpinskiTriangle(g, iter - 1, x + Math.cos(Math.PI * 7 / 6) * size
				/ 2, y - Math.sin(Math.PI * 7 / 6) * size / 2, size / 2);
	}

	private void drawTriangle(Graphics g, double x, double y, double size,
			double angle)
	{
		g.fillPolygon(
				new int[] { (int) (x + Math.cos(Math.PI / 2 + angle) * size),
						(int) (x + Math.cos(Math.PI * 11 / 6 + angle) * size),
						(int) (x + Math.cos(Math.PI * 7 / 6 + angle) * size) },
				new int[] { (int) (y - Math.sin(Math.PI / 2 + angle) * size),
						(int) (y - Math.sin(Math.PI * 11 / 6 + angle) * size),
						(int) (y - Math.sin(Math.PI * 7 / 6 + angle) * size) },
				3);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			iterations++;
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			iterations--;
			iterations = Math.max(0, iterations);
		}
		else if (e.getKeyCode() == KeyEvent.VK_UP && fractalNum == 0)
		{
			n++;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN && fractalNum == 0)
		{
			n--;
			n = Math.max(0, n);
		}
		else if (e.getKeyCode() == KeyEvent.VK_A)
		{
			fractalNum--;
			fractalNum = Math.max(0, fractalNum);
		}
		else if (e.getKeyCode() == KeyEvent.VK_D)
		{
			fractalNum++;
		}

		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}
}