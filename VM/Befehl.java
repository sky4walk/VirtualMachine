//written by André Betz 
//http://www.andrebetz.de


class Befehl
{
	MyList programm;
	Befehl(MyList prgList){
		programm = prgList;
	}
	public void StartCmd(MyList Prms){
	}
	
	protected void NextPos(){
		programm.SetNext();
	}
	protected void NextPos(String Label){
		int pos = SearchLabel(Label);
		if(pos>=0){
			programm.SetAt(pos);
		}
	}	
	protected Program GetLabel(String Label){
		int pos = SearchLabel(Label);
		return (Program)programm.GetAt(pos);
	}
	protected int SearchLabel(String Label){
		for(int i=0;i<programm.GetLength();i++){
			Program prg = (Program)programm.GetAt(i);
			if(Label.equals(prg.GetLabel())){
				return i;
			}
		}
		return -1;
	}
}

class CLR extends Befehl
{
	CLR(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		Prms.SetFirst();
		String Lbl = (String)Prms.GetAt(0);
		Program prg = super.GetLabel(Lbl);
		prg.GetMem()[0] = 0;
		super.NextPos();
	}
}

class JMP extends Befehl
{
	JMP(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl = (String)Prms.GetAt(0);
		super.NextPos(Lbl);
	}
}

class BNZ extends Befehl
{
	BNZ(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		
		Program prg = super.GetLabel(Lbl1);
		if(prg.GetMem()[0]==0){
			super.NextPos();
		}else{
			super.NextPos(Lbl2);
		}
	}
}

class BZ extends Befehl
{
	BZ(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		
		Program prg = super.GetLabel(Lbl1);
		if(prg.GetMem()[0]==0){
			super.NextPos(Lbl2);
		}else{
			super.NextPos();
		}
	}
}

class INC extends Befehl
{
	INC(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl = (String)Prms.GetAt(0);
		Program prg = super.GetLabel(Lbl);
		prg.GetMem()[0]++;
		super.NextPos();
	}
}

class DEC extends Befehl
{
	DEC(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl = (String)Prms.GetAt(0);
		Program prg = super.GetLabel(Lbl);
		prg.GetMem()[0]--;
		super.NextPos();
	}
}

class CPY extends Befehl
{
	CPY(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);

		prg2.GetMem()[0] = prg1.GetMem()[0];
		
		super.NextPos(); 
	}
}

class ADD extends Befehl
{
	ADD(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		String Lbl3 = (String)Prms.GetAt(2);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);
		Program prg3 = super.GetLabel(Lbl3);

		prg3.GetMem()[0] = prg1.GetMem()[0] + prg2.GetMem()[0];
		
		super.NextPos(); 
	}
}

class SUB extends Befehl
{
	SUB(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		String Lbl3 = (String)Prms.GetAt(2);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);
		Program prg3 = super.GetLabel(Lbl3);

		prg3.GetMem()[0] = prg1.GetMem()[0] - prg2.GetMem()[0];
		
		super.NextPos(); 
	}
}

class EQU extends Befehl
{
	EQU(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		String Lbl3 = (String)Prms.GetAt(2);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);
		Program prg3 = super.GetLabel(Lbl3);

		if(prg1.GetMem()[0] == prg2.GetMem()[0]){
			super.NextPos(Lbl3);
		}else{
			super.NextPos();
		}
	}
}

class NEQ extends Befehl
{
	NEQ(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		String Lbl3 = (String)Prms.GetAt(2);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);
		Program prg3 = super.GetLabel(Lbl3);

		if(prg1.GetMem()[0] == prg2.GetMem()[0]){
			super.NextPos();
		}else{
			super.NextPos(Lbl3);
		}
	}
}

class GT extends Befehl
{
	GT(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		String Lbl3 = (String)Prms.GetAt(2);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);
		Program prg3 = super.GetLabel(Lbl3);

		if(prg1.GetMem()[0] > prg2.GetMem()[0]){
			super.NextPos(Lbl3);
		}else{
			super.NextPos();
		}
	}
}

class GTE extends Befehl
{
	GTE(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		String Lbl3 = (String)Prms.GetAt(2);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);
		Program prg3 = super.GetLabel(Lbl3);

		if(prg1.GetMem()[0] >= prg2.GetMem()[0]){
			super.NextPos(Lbl3);
		}else{
			super.NextPos();
		}
	}
}

class MUL extends Befehl
{
	MUL(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		String Lbl3 = (String)Prms.GetAt(2);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);
		Program prg3 = super.GetLabel(Lbl3);

		prg3.GetMem()[0] = prg1.GetMem()[0] * prg2.GetMem()[0];
		super.NextPos();
	}
}

class DIV extends Befehl
{
	DIV(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		String Lbl3 = (String)Prms.GetAt(2);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);
		Program prg3 = super.GetLabel(Lbl3);

		prg3.GetMem()[0] = prg1.GetMem()[0] / prg2.GetMem()[0];
		super.NextPos();
	}
}

class LD extends Befehl
{
	LD(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		String Lbl3 = (String)Prms.GetAt(2);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);
		Program prg3 = super.GetLabel(Lbl3);

		prg3.GetMem()[0] = prg1.GetMem()[prg2.GetMem()[0]];
		super.NextPos();
	}
}

class ST extends Befehl
{
	ST(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		String Lbl3 = (String)Prms.GetAt(2);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);
		Program prg3 = super.GetLabel(Lbl3);

		prg1.GetMem()[prg2.GetMem()[0]] = prg3.GetMem()[0];
		super.NextPos();
	}
}

class PSH extends Befehl
{
	PSH(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		String Lbl3 = (String)Prms.GetAt(2);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);
		Program prg3 = super.GetLabel(Lbl3);

		prg1.GetMem()[prg2.GetMem()[0]] = prg3.GetMem()[0];
		prg2.GetMem()[0]++;
		super.NextPos();
	}
}

class POP extends Befehl
{
	POP(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		String Lbl3 = (String)Prms.GetAt(2);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);
		Program prg3 = super.GetLabel(Lbl3);

		prg2.GetMem()[0]--;
		prg3.GetMem()[0] = prg1.GetMem()[prg2.GetMem()[0]];
		super.NextPos();
	}
}

class JSR extends Befehl
{
	JSR(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		String Lbl3 = (String)Prms.GetAt(2);
		String Lbl4 = (String)Prms.GetAt(3);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);
		int PrgNr3  = super.SearchLabel(Lbl3);

		prg1.GetMem()[prg2.GetMem()[0]] = PrgNr3;
		prg2.GetMem()[0]++;
		
		super.NextPos(Lbl4);
	}
}

class RET extends Befehl
{
	RET(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);

		prg2.GetMem()[0]--;
		int prgNr3 = prg1.GetMem()[prg2.GetMem()[0]];
		Program prg3 = (Program)programm.GetAt(prgNr3);
		String Lbl3 = prg3.GetLabel();
		super.NextPos(Lbl3);
	}
}

class OUT extends Befehl
{
	OUT(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);	
		Program prg1 = super.GetLabel(Lbl1);
		char sign = (char)(prg1.GetMem()[0]%255);
		System.out.print(sign);
		super.NextPos();
	}
}

class NAND extends Befehl
{
	NAND(MyList prgList){
		super(prgList);
	}
	public void StartCmd(MyList Prms){
		String Lbl1 = (String)Prms.GetAt(0);
		String Lbl2 = (String)Prms.GetAt(1);
		String Lbl3 = (String)Prms.GetAt(2);
		
		Program prg1 = super.GetLabel(Lbl1);
		Program prg2 = super.GetLabel(Lbl2);
		Program prg3 = super.GetLabel(Lbl3);

		prg3.GetMem()[0] = ~(prg1.GetMem()[0] & prg2.GetMem()[0]);
		super.NextPos();
	}
}