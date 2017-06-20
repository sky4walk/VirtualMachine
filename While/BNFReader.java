//written by André Betz 
//http://www.andrebetz.de


class BNFReader
{
	MyList Rules;
	
	BNFReader(){
		Rules = new MyList();
	}
	
	public MyList GetRules(){
		return Rules;
	}
	
	public boolean Init(String BNF){
		if(!ParseBNF(BNF)){
			return false;
		}
		return true;
	}
	
	private boolean ParseBNF(String BNF){
		boolean bTerminal = false;
		int automstate = 0;
		int len = BNF.length();
		int spos = 0;
		char sign;
		int failureState = 100;
		char DelSigns[] = {' ','\n','\r','\t'};
		BNFRule actRule = null;
		while(spos<len){
			spos = ParseHelper.DelNoSigns(BNF.toString(),spos,DelSigns);
			if(spos<len) {
				sign = BNF.charAt(spos);
				switch(automstate){
					case 0:
					{
						actRule = null;
						if (sign=='#'){
							spos = ParseHelper.DelComment(BNF.toString(),spos);
						}else {
							automstate = 1;
						}
						break;
					}
					case 1:
					{
						char EndSigns[] =  {'-'};
						ScannerErg scnErg = ParseHelper.GetWord(BNF.toString(),spos,EndSigns,DelSigns);
						if(scnErg.getTermSignNr()==0){
							actRule = new BNFRule();
							actRule.SetStart(scnErg.getWord());
							Rules.Add(actRule);
							automstate = 2;	
						}else{
							automstate = failureState;
						}
						spos = scnErg.getSpos();
						break;
					}
					case 2:
					{
						if(spos<len && BNF.charAt(spos)=='>'){
							automstate = 3;
							spos++;
						}else{
							automstate = failureState;
						}
						break;
					}
					case 3:
					{
						char EndSigns[] = {',', '$', '|', '.'};
						ScannerErg scnErg = ParseHelper.GetWord(BNF.toString(),spos,EndSigns,DelSigns);
						if(scnErg.getTermSignNr()==0){
							actRule.SetNextToken(scnErg.getWord(),bTerminal);
							automstate = 3;
							bTerminal = false;
						}else if(scnErg.getTermSignNr()==1){
							bTerminal = true;
							automstate = 3;								
						}else if(scnErg.getTermSignNr()==2){
							if(scnErg.getWord().length()!=0){
								actRule.SetNextToken(scnErg.getWord(),bTerminal);
							}else{
								actRule.SetNextToken(scnErg.getWord(),true);
							}
							String StartSign = actRule.GetStart().GetToken();
							actRule = new BNFRule();
							actRule.SetStart(StartSign);
							Rules.Add(actRule);
							automstate = 3;
							bTerminal = false;								
						}else if(scnErg.getTermSignNr()==3){
							if(scnErg.getWord().length()!=0){
								actRule.SetNextToken(scnErg.getWord(),bTerminal);
							}else{
								actRule.SetNextToken(scnErg.getWord(),true);
							}
							automstate = 0;
							bTerminal = false;															
						}else{
							automstate = failureState;
						}
						spos = scnErg.getSpos();
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
}