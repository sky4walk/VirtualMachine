//written by André Betz 
//http://www.andrebetz.de

class Mnemonic
{
	private String MnemName;
	private int AnzParams;
	private Befehl m_func;
	
	Mnemonic(String Name, int Params, Befehl func){
		MnemName = Name;
		AnzParams = Params;
		m_func = func;
	}
	public String GetMnemName(){
		return MnemName;
	}
	public int GetParamsCount(){
		return AnzParams;
	}
	public Befehl GetFunc(){
		return m_func;
	}
}