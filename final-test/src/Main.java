import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Authors: Leonardo Fiedler e Matheus Eduardo.
//Tutorial: http://mnemstudio.org/path-finding-q-learning-tutorial.htm

public class Main {
	
	static int END_STATE = 4;
	
	public static void main(String[] args) {
		int Q[][] = new int[5][5];
		//Initialize with 0
		for (int i = 0; i < Q.length; i++) {
			for (int j = 0; j < Q[i].length; j++) {
				Q[i][j] = 0;
			}
		}
		
		int R[][] = new int[5][5];
		int r0[] = new int[] {-1, -1, 100, 0 , -1};
		int r1[] = new int[] {-1, -1, 0, 0 , 100};
		int r2[] = new int[] {0, 0, -1, 100 , -1};
		int r3[] = new int[] {0, 100, 0, -1 , -1};
		int r4[] = new int[] {100, -1, -1, -1 , -1};
		
		R[0] = r0;
		R[1] = r1;
		R[2] = r2;
		R[3] = r3;
		R[4] = r4;
		
		//0 <= Gamma < 1
		float gamma = 0.8f;
		int processed = 1;
		int[] validState = new int[5];
		
		int initialState = 0;
		int nextState = initialState;
		int currentState = nextState;
		
		System.out.println("Current State " + initialState);
		
		validState[0] = initialState;
		Random r = new Random();
		
		//Iterating over episodes
		boolean endEpisode = false;
		
		while(processed < 6) {			
			endEpisode = false;
			
			while (!endEpisode) {
				nextState = getIndexNextState(R[currentState]);
				System.out.println("Goes to " + nextState);
				
				//Q(state, action) = R(state, action) + Gamma * Max[Q(next state, all actions)]
				Q[currentState][nextState] = R[currentState][nextState] + (int) (gamma * getMaxFromRow(Q[nextState]));				
				currentState = nextState;
				
				if (currentState == END_STATE) {
					endEpisode = true;
				}			
			}
			
			if (processed < 5) {
				boolean valid = false;
				while(!valid) {
					valid = true;
					//Set currentState randomically
					initialState = r.nextInt(R.length);
					for (int i = 0; i < validState.length; i++) {
						if (initialState == validState[i]) {
							valid = false;
							break;
						}
					}				
				}
				
				System.out.println("Current State " + initialState);
				validState[processed] = initialState;
				currentState = initialState;
				processed++;
			} else {
				processed++;
			}
		}
		
		for(int i=0; i< Q.length; i++){
	        for(int j=0; j< Q[i].length; j++){
	            System.out.print(String.format("%20s", Q[i][j]));
	        }
	        System.out.println("");
	    }
		
		
		//Get Menor caminho starting from 0
		int initialPosition = 0;
		List<Integer> caminho = new ArrayList<Integer>();
		caminho.add(initialPosition);
		
		
		while (caminho.size() < 5) {
			int max = 0;
			int iMax = 0;
			for (int j = 0; j < Q[initialPosition].length; j++) {
				if (Q[initialPosition][j] > max && !caminho.contains(j)) {
					iMax = j;
					max = Q[initialPosition][j]; 
				}
			}
			
			caminho.add(iMax);
			initialPosition = iMax;
		}
		
		for (int i = 0; i < caminho.size(); i++) {
			System.out.println(caminho.get(i));
		}
	}
	
	public static int getIndexNextState(int[] values) {
		Random r = new Random();
		int randomValue = 0;
		boolean find = false;
		while (!find) {
			randomValue = r.nextInt(values.length);
			if (values[randomValue] >= 0) {
				return randomValue;
			}
		}
		
		return 0;
	}
	
	public static int getMaxFromRow(int[] values) {
		int max = 0;
		
		for (int i = 0; i < values.length; i++) {
			if (values[i] > max) {
				max = values[i]; 
			}
		}
		
		return max;
	}

}
