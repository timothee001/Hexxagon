public class Action{

		private String posDepart;
		private String typeDeplacement;
		private String posArrive;
		private int utility=0 ; 

		public Action(String posDepart, String posArrive, String typeDeplacement){
			this.posDepart = posDepart; 
			this.typeDeplacement = typeDeplacement;
			this.posArrive = posArrive;
			
		}

		public String getPosDepart(){
			return this.posDepart;
		}

		public String getTypeDeplacement(){
			return this.typeDeplacement;
		}

		public String getPosArrive(){
			return this.posArrive;
		}

		public int getUtility(){
			return this.utility;
		}

		public void setUtility(int utility){
			this.utility = utility;
		}
		
	}
