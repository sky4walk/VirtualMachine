//written by André Betz 
//http://www.andrebetz.de

import java.io.*;

class VM
{
	MyList cmdList = null;
	MyList PrgList = null;
	
	VM(String FileName){
		PrgList = new MyList();
		FillCmdList();
		StringBuffer File = Load(FileName);
		if(File.length()>0){
			Parse(File);
		}
		PrgList.SetFirst();
	}
	
	public void Step(){
		Program prg = (Program)PrgList.GetAct();
		prg.StartCmd();
	}
	
	protected boolean Parse(StringBuffer File){
		int automstate = 0;
		int LabelCounter = 0;
		int len = File.length();
		int spos = 0;
		char sign;
		int failureState = 100;
		boolean failure = false;
		char DelSigns[] = {' ','\n','\r','\t'};
		Program actPrg = null;
		while(spos<len) {
			spos = ParseHelper.DelNoSigns(File.toString(),spos,DelSigns);
			if(spos<len) {
				sign = File.charAt(spos);
				switch(automstate){
					case 0:
					{
						if (sign=='#'){
							spos = ParseHelper.DelComment(File.toString(),spos);
						}else {
							automstate = 1;
						}
						break;
					}
					case 1:
					{
						char EndSigns[] =  {'(',':','['};
						char DelSignsNoRet[] = {' ','\r','\t'};
						ScannerErg scnErg = ParseHelper.GetWord(File.toString(),spos,EndSigns,DelSignsNoRet);
						if(scnErg.getTermSignNr()>=0||spos==len){
							actPrg = new Program();
							PrgList.Add(actPrg);
							if(scnErg.getTermSignNr()==0){
								// Command
								Mnemonic PrmCnt = GetParams(scnErg.getWord().toUpperCase());
								if(PrmCnt!=null){
									actPrg.SetCmdNm(PrmCnt.GetMnemName());
									actPrg.SetFunc(PrmCnt.GetFunc());
									automstate = 2;
								} else {
									automstate = failureState;
								}
							} else if(scnErg.getTermSignNr()==1){
								// Label
								actPrg.SetLabel(scnErg.getWord().toUpperCase());
								automstate = 6;
							} else if(scnErg.getTermSignNr()==2){
								// Label mit Array
								actPrg.SetLabel(scnErg.getWord().toUpperCase());
								automstate = 3;
							}else{
								automstate = failureState;															
							}
						}else{
							automstate = failureState;							
						}
						spos = scnErg.getSpos();
						break;
					}
					case 2:
					{
						// Parameter
						char EndSigns[] = {',',')'};
						ScannerErg scnErg = ParseHelper.GetWord(File.toString(),spos,EndSigns,DelSigns);
						spos = scnErg.getSpos();
						if(scnErg.getTermSignNr()>=0){
							if(scnErg.getWord().length()!=0) {
								actPrg.AddParams(scnErg.getWord().toUpperCase());
							}
							if(scnErg.getTermSignNr()==0){
								automstate = 2; // noch eine Variable
							}
							else if(scnErg.getTermSignNr()==1){ 
								automstate = 0; //nächster Befehl
							}							
							else{
								automstate = failureState; 
							}
						} else {
							automstate = failureState; 
						}
						break;
					}
					case 3:
					{
						// Arraylaenge auslesen
						char EndSigns[] = {']'};
						ScannerErg scnErg = ParseHelper.GetWord(File.toString(),spos,EndSigns,DelSigns);
						spos = scnErg.getSpos();
						if(scnErg.getTermSignNr()==0){
							actPrg.SetMem(Integer.valueOf(scnErg.getWord().toUpperCase(),10).intValue());
							automstate = 4; 
						}else{
							automstate = failureState; 
						}
						break;
					}
					case 4:
					{
						char EndSigns[] = {':'};
						ScannerErg scnErg = ParseHelper.GetWord(File.toString(),spos,EndSigns,DelSigns);
						spos = scnErg.getSpos();
						if(scnErg.getTermSignNr()==0){
							automstate = 5; 
						}else{
							automstate = failureState; 
						}
						break;
					}
					case 5:
					{
						char EndSigns[] = {'\n'};
						char DelSignsNoRet[] = {' ','\r','\t'};
						ScannerErg scnErg = ParseHelper.GetWord(File.toString(),spos,EndSigns,DelSignsNoRet);
						spos = scnErg.getSpos();
						if(scnErg.getTermSignNr()==0){
							// Array
							String Zahlen = scnErg.getWord().toUpperCase() + '\n';
							char EndSigns2[] =  {'\n','\r',','};
							int ZahlPos = 0;
							int ZahlNum = 0;
							while(ZahlPos<Zahlen.length()&&ZahlNum<actPrg.GetMem().length){
								ScannerErg scnErg2 = ParseHelper.GetWord(Zahlen,ZahlPos,EndSigns2,DelSignsNoRet);
								ZahlPos = scnErg2.getSpos();
								actPrg.GetMem()[ZahlNum] = Integer.valueOf(scnErg2.getWord().toUpperCase(),10).intValue();
								ZahlNum++;
								if(scnErg2.getTermSignNr()>=0){
									if(scnErg2.getTermSignNr()<=1){
										break;
									}
								}else{
									automstate = failureState;
									break;														
								}
							}
							automstate = 0;
						}else{
							automstate = failureState; 
						}
						break;
					}
					case 6:
					{
						char EndSigns[] = {'\n','('};
						char DelSignsNoRet[] = {' ','\r','\t'};
						ScannerErg scnErg = ParseHelper.GetWord(File.toString(),spos,EndSigns,DelSignsNoRet);
						spos = scnErg.getSpos();
						if(scnErg.getTermSignNr()==0){
							// Label
							actPrg.SetMem(1);
							actPrg.GetMem()[0] = Integer.valueOf(scnErg.getWord().toUpperCase(),10).intValue();;
							automstate = 0;
						}else if(scnErg.getTermSignNr()==1){
							// Command
							Mnemonic PrmCnt = GetParams(scnErg.getWord().toUpperCase());
							if(PrmCnt!=null){
								PrgList.Add(actPrg);
								actPrg.SetCmdNm(PrmCnt.GetMnemName());
								actPrg.SetFunc(PrmCnt.GetFunc());
								automstate = 2;
							} else {
								automstate = failureState;
							}
						}else{
							automstate = failureState; 						
						}
						break;
					}
					default:
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	protected Mnemonic GetParams(String Name){
		cmdList.SetFirst();
		for(int i=0;i<cmdList.GetLength();i++){
			Mnemonic temp = (Mnemonic)cmdList.GetAct();
			cmdList.SetNext();
			if(Name.equals(temp.GetMnemName())){
				return temp;
			}
		}
		return null;
	}
	
	protected StringBuffer Load(String FileName){
		StringBuffer readinput = new StringBuffer();
		if(FileName==null){
			return null;
		}
		try {
			File f = new File(FileName);
			FileReader in = new FileReader(f);
			char[] buffer = new char[128];
			int len;
			while((len = in.read(buffer))!=-1) {
				readinput.append(buffer,0,len);
			}
		}
		catch(IOException e) {
			System.out.println("Dateifehler");
		}
		return readinput;  	
	}
	
	protected void FillCmdList()
	{
		cmdList = new MyList();
		cmdList.Add(new Mnemonic("JMP",1,new JMP(PrgList)));	
		cmdList.Add(new Mnemonic("CLR",1,new CLR(PrgList)));
		cmdList.Add(new Mnemonic("INC",1,new INC(PrgList)));	
		cmdList.Add(new Mnemonic("DEC",1,new DEC(PrgList)));	
		cmdList.Add(new Mnemonic("BNZ",2,new BNZ(PrgList)));	
		cmdList.Add(new Mnemonic("BZ", 2,new BZ(PrgList)));
		cmdList.Add(new Mnemonic("CPY",2,new CPY(PrgList)));
		cmdList.Add(new Mnemonic("ADD",3,new ADD(PrgList)));			
		cmdList.Add(new Mnemonic("SUB",3,new SUB(PrgList)));
		cmdList.Add(new Mnemonic("EQU",3,new EQU(PrgList)));			
		cmdList.Add(new Mnemonic("NEQ",3,new NEQ(PrgList)));			
		cmdList.Add(new Mnemonic("GT", 3,new GT(PrgList)));			
		cmdList.Add(new Mnemonic("GTE",3,new GTE(PrgList)));			
		cmdList.Add(new Mnemonic("MUL",3,new MUL(PrgList)));			
		cmdList.Add(new Mnemonic("DIV",3,new DIV(PrgList)));			
		cmdList.Add(new Mnemonic("LD", 3,new LD(PrgList)));			
		cmdList.Add(new Mnemonic("ST", 3,new ST(PrgList)));
		cmdList.Add(new Mnemonic("POP",3,new POP(PrgList)));			
		cmdList.Add(new Mnemonic("PSH",3,new PSH(PrgList)));			
		cmdList.Add(new Mnemonic("JSR",4,new JSR(PrgList)));			
		cmdList.Add(new Mnemonic("RET",2,new RET(PrgList)));
		cmdList.Add(new Mnemonic("OUT",1,new OUT(PrgList)));			
		cmdList.Add(new Mnemonic("NAND",3,new NAND(PrgList)));			
	}
	
	public static void main(String[] args) {
		if(args.length!=1){
			System.exit(0);
		}

		VM VirtMach = new VM(args[0]);
		while(true){
			VirtMach.Step();
		}
	}
}