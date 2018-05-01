/** Kush Patel **/

// Imports
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

@SuppressWarnings("serial")
public class SortVisual extends JPanel  // extend JPanel for the GUI
{
	// instantiating the arrays
	public static int[] bubbleArray;
	public static int[] insertionArray;
	public static int[] selectionArray;

	// variables for the display
	private static final int ARRAY_SIZE = 50, BAR_WIDTH = 8, SPACE_APART = 5, MAX = 80, MIN = 1, SS_Y_START = 120, IS_Y_START = 270, BS_Y_START = 420;
	private JButton changeButton;

	public SortVisual() 
	{
		// arrays used for sorting
		selectionArray = new int[ARRAY_SIZE];
		insertionArray = new int[ARRAY_SIZE];
		bubbleArray = new int[ARRAY_SIZE];

		// randomly populate the arrays
		fill(selectionArray);
		fill(insertionArray);
		fill(bubbleArray);

		// set up the graphics
		setBackground(Color.white);
		setPreferredSize(new Dimension(300, 300));

		// GUI button setup
		changeButton = new JButton("Click to start animimation!");
		add(changeButton);
		changeButton.addActionListener(new ButtonHandler());
	}

	// settting up the paint component that visualizes the array
	public void paintComponent(Graphics pen) 
	{
		super.paintComponent(pen);

		int xPos = 20;

		// Paint the Selection Sort Visual
		pen.setColor(Color.blue);
		for (int index = 0; index < ARRAY_SIZE; index++) 
		{
			pen.fillRect(xPos, SS_Y_START - selectionArray[index], BAR_WIDTH, selectionArray[index]);
			xPos = xPos + BAR_WIDTH + SPACE_APART; // adds width and spaces so bars don't overlap
		}

		xPos = 20; // resets x position

		// Label the Selection Sort Visual
		pen.setColor(Color.black);
		pen.drawString("Selection Sort", xPos, SS_Y_START + 20);

		// Paint the Insertion Sort Visual
		pen.setColor(Color.red);
		for (int index = 0; index < ARRAY_SIZE; index++) 
		{
			pen.fillRect(xPos, IS_Y_START - insertionArray[index], BAR_WIDTH, insertionArray[index]);
			xPos = xPos + BAR_WIDTH + SPACE_APART; // adds width and spaces so bars don't overlap
		}

		xPos = 20; // resets x position

		// Label the Insertion Sort Visual
		pen.setColor(Color.black);
		pen.drawString("Insertion Sort", xPos, IS_Y_START + 20);

		// Paint the Bubble Sort Visual
		pen.setColor(Color.magenta);
		for (int index = 0; index < ARRAY_SIZE; index++) 
		{
			pen.fillRect(xPos, BS_Y_START - bubbleArray[index], BAR_WIDTH, bubbleArray[index]);
			xPos = xPos + BAR_WIDTH + SPACE_APART; // adds width and spaces so bars don't overlap
		}

		xPos = 20; // resets x position

		// Label the Bubble Sort Visual
		pen.setColor(Color.black);
		pen.drawString("Bubble Sort", xPos, BS_Y_START + 20);
	}

	// Helper Method that randomly poplulates each array from bar heights 1 through 80
	void fill(int[] a)
	{
		int length = a.length;
		Random r = new Random();

		for (int i = 0; i < length; i++) 
			a[i] = i+1;

		for (int q = length-1; q > 0; q--) 
		{
			int j = r.nextInt(q);
			int temp = a[q];
			a[q] = a[j];
			a[j] = temp;
		}
	}

	// ActionListener that starts the three concurrent threads
	private class ButtonHandler implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			// Instantiate the BubbleSort Thread
			BubbleSortRunnable ret1 = new BubbleSortRunnable(bubbleArray);
			Thread bubblet = new Thread(ret1); 
			bubblet.start();

			// Instantiate the SelectionSort Thread
			SelectionSortRunnable ret2 = new SelectionSortRunnable(selectionArray);
			Thread select = new Thread(ret2); 
			select.start();

			// Instantiate the InsertionSort Thread
			InsertionSortRunnable ret3 = new InsertionSortRunnable(insertionArray);
			Thread insert = new Thread(ret3); 
			insert.start();	

			// final repaint
			repaint();
		}
	}

	// Main Method
	public static void main(String[] args)
	{
		SortVisual panel = new SortVisual(); //Instantiate the panel
		JFrame frame = new JFrame("Sort Visual"); //Instantiate the frame
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setSize(700, 510);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Insertion Sort Class
	public class InsertionSortRunnable implements Runnable 
	{
		private int[] array;

		// Constructor
		public InsertionSortRunnable(int[] a)
		{
			this.array = a;
		}

		// run method for the thread
		@Override 
		public void run()
		{
			// Algorithm for Insertion Sort
			int n = array.length;
			for (int i=1; i<n; ++i)
			{
				int key = array[i];
				int j = i-1;

				while (j>=0 && array[j] > key)
				{
					array[j+1] = array[j];
					j = j-1;
				}
				array[j+1] = key;

				// pause running thread for animation effects and then repaint()
				try {Thread.sleep(80);} catch (InterruptedException e) {	}
				SortVisual.this.paintComponent(SortVisual.this.getGraphics());
			}	
		}
	}

	// Bubble Sort Class
	public class BubbleSortRunnable implements Runnable 
	{
		private int[] array;

		// Constructor
		public BubbleSortRunnable(int[] a)
		{
			this.array = a;
		}

		// run method for the thread
		public void run()
		{
			// Algorithm for Bubble Sort
			int n = array.length;
			for (int i = 0; i < n-1; i++)
			{
				for (int j = 0; j < n-i-1; j++)
				{
					if (array[j] > array[j+1])
					{  
						int temp = array[j];
						array[j] = array[j+1];
						array[j+1] = temp;

						// pause running thread for animation effects and then repaint()
						try {Thread.sleep(50);} catch (InterruptedException e) {	}
						SortVisual.this.paintComponent(SortVisual.this.getGraphics());
					}  
				}
			} 
		}
	}

	// Selection Sort Class
	class SelectionSortRunnable implements Runnable 
	{
		private int[] array;

		// Constructor
		public SelectionSortRunnable(int[] a)
		{
			this.array = a;
		}

		// run method for the thread
		public void run()
		{
			// Algorithm for Selection Sort
			int n = array.length;
			for (int i = 0; i < n-1; i++)
			{
				int min_idx = i;
				for (int j = i+1; j < n; j++)
					if (array[j] < array[min_idx])
						min_idx = j;
				int temp = array[min_idx];
				array[min_idx] = array[i];
				array[i] = temp;

				// pause running thread for animation effects and then repaint()
				try {Thread.sleep(80);} catch (InterruptedException e) {	}
				SortVisual.this.paintComponent(SortVisual.this.getGraphics());
			}
		}
	}
}