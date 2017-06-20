//written by André Betz 
//http://www.andrebetz.de


class ParseHelper
{
	public static int DoesContain(char Sign, char[] SignList){
		int count = 0;
		while(count<SignList.length){
			if(Sign == SignList[count]){
				return count;	
			}
			count++;
		}
		return -1;
	}
	
	public static int DoesContain(String Val, String[] ValList){
		int count = 0;
		while(count<ValList.length){
			if(Val.equals(ValList[count])){
				return count;	
			}
			count++;
		}
		return -1;
	}
	
	public static boolean IsNumerical(char Sign){
		char a[] = {'0','1','2','3','4','5','6','7','8','9'};
		if(DoesContain(Sign,a)>=0){
			return true;
		}
		return false;	
	}
	
	public static boolean IsAlpha (char Sign)
	{
		char a[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
					'n','o','p','q','r','s','t','u','v','w','x','y','z',
					'A','B','C','D','E','F','G','H','I','J','K','L','M',
					'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
					'_'};
		if(DoesContain(Sign,a)>=0){
			return true;
		}
		return false;						
	}

	public static boolean IsAlphaNumeric (char Sign)
	{
		if(IsAlpha(Sign)||IsNumerical(Sign))
		{
			return true;	
		}
		return false;
	}
	
	public static int DelNoSigns(String Input, int spos, char[] NoSigns) {
		int newpos = spos;
		if(NoSigns==null){
			return spos;
		}
		int slen = Input.length();
		if(newpos<slen) {
			char sign = Input.charAt(newpos);
			while((DoesContain(sign,NoSigns)>=0)&&(newpos<slen)) {
				newpos++;
				if(newpos<slen) {
					sign = Input.charAt(newpos);
				}
			}
		}
		return newpos;
	}
	public static int DelComment(String Input, int spos) {
		int newpos = spos;
		int slen = Input.length();
		char sign = Input.charAt(newpos);
		while((sign!='\n')&&(newpos<slen)) {
			newpos++;
			if(newpos<slen) {
				sign = Input.charAt(newpos);
			}
		}
		return newpos;  		
	}

	public static int GetWord(String Data, int spos,char[] Endsigns, String[] EndWords,char[] DelSigns){
		ScannerErg scnErg;
		int res = -1;
		spos = DelNoSigns(Data.toString(),spos,DelSigns);		
		for(int i=0;i<EndWords.length;i++){
			scnErg = GetWord(Data,spos,Endsigns,null);
			if(EndWords[i].toUpperCase().equals(scnErg.getWord())){
				res = i;
				break;
			}
		}
		return res;
	}
		
	public static ScannerErg GetWord(String Data, int spos, char[] Endsigns,char[] DelSigns){
		char sign;
		char Change = '\\';
		ScannerErg scnErg = new ScannerErg();
		scnErg.setTermSignNr(-1);
		int endpos = Data.length();
		String Word = "";
		boolean NoEndNext = false;
		while(spos<endpos){
			spos = DelNoSigns(Data.toString(),spos,DelSigns);
			sign = Data.charAt(spos);
			int nr = DoesContain(sign,Endsigns);
			if(NoEndNext==true){
				NoEndNext = false;
			}else if(sign==Change){
				NoEndNext = true;
			}
			if(nr>=0){
				if(Endsigns[nr]==Change){
					NoEndNext = false;
				}
				if(NoEndNext==false){
					scnErg.setTermSignNr(nr);
					spos++;
					break;
				}
			}
			if(sign!=' '){
				Word += sign; 		
			}
			spos++;
		}
		scnErg.setSpos(spos);
		scnErg.setWord(Word);
		return scnErg;
	}
	
	// sucht nach str2 in einem grossen String-Array ab Position strPos1 
	public static int StringEqual(char[] str1, int strPos1, char[] str2,int len) 
	{
		int	k = 0;
		if(str1==null||str2==null)
		{
			return 0;
		}
		if(str1.length<str2.length)
		{
			return 0;
		}
		if(str2.length<len)
		{
			return 0;
		}
		while(str2[k]!=len) 
		{
			if ((str1[strPos1+k] != str2[k]))
			{
				return 0;
			}
			k++;
		}
		return k;
	}
	public static int StringEqual(char[] str1, int strPos1, char[] str2) 
	{
		return StringEqual(str1,strPos1,str2,str2.length);
	}
	public static int StringEqual(char[] str1, char[] str2) 
	{
		return StringEqual(str1,0,str2);
	}
	
	public static char GetChar(char[] Str,int Pos)
	{
		if(Str.length<=Pos){
			return 0;
		}
		return (char)(Str[Pos] & 127);
	}
	public static char GetNextChar(char[] Str,int Pos)
	{
		if(Str.length<=(Pos+1)){
			return 0;
		}
		return (char)(Str[Pos+1] & 127);
	}

}