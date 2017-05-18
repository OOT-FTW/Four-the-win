package de.hsMannheim.informatik.oot.ss17.ttt.view;

public class UserInterface {
		
		char[][] Spielfeld;
			
		public UserInterface(int[]Size) {
			this.Spielfeld = init(Size[0], Size[1]);
		}
  
		private char[][] init(int sizeX, int sizeY){
			char[][] Spielfeld = new char[sizeX +2][sizeY+2];
			for(int i=0;i<(sizeX+2);i++){
				Spielfeld[i][0]=Spielfeld[i][sizeX+1]='|';
			}
			for(int i=0; i<(sizeY+2);i++){
				Spielfeld[0][i]=Spielfeld[sizeY+1][i]='-';
			}
			for(int i=1;i<Spielfeld.length-1;i++){
				for(int j=1;j<Spielfeld[0].length-1;j++){
					Spielfeld[i][j]=' ';
				}
			}
			return Spielfeld;
		}
		
		public void print(){
			for(int i=0; i<Spielfeld.length;i++){
				for(int j=0; j<Spielfeld[0].length;j++){
					System.out.print(Spielfeld[i][j]);
				}System.out.println();
			}
		}
//		private boolean isEmpty(int[] position){
//			
//			if(this.Spielfeld[position[0]][position[1]]==' '){
//				return true;
//			}
//			return false;
//		}
//		
		
}
