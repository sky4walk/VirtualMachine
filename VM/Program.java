//written by André Betz 
//http://www.andrebetz.de


class Program
{
	String Label;
	String CmdName;
	MyList Params;
	Befehl function;
	int[]  Mem;
	
	Program(String Lbl,String CmdNm,MyList Prms,Befehl func,int[] arry){
		Label = Lbl;
		CmdName = CmdNm;
		Params = Prms;
		function = func;
		Mem = arry;
	}
	
	Program(){
		Label = "";
		CmdName = "";
		Params = new MyList();
		function = null;
		Mem = null;		
	}
	
	public void SetLabel(String Lbl){
		Label = Lbl;	
	}
	
	public void SetCmdNm(String cn){
		CmdName = cn;
	}
	
	public void AddParams(String Prms){
		Params.Add(Prms);
	}
	
	public void SetFunc(Befehl f){
		function = f;
	}
	
	public void SetMem(int len){
		Mem = new int[len];
	}
	
	public void StartCmd(){
		if(function!=null){
			function.StartCmd(Params);
		}	
	}
	
	public int[] GetMem(){
		return Mem;
	}

	public String GetLabel(){
		return Label;
	}
}