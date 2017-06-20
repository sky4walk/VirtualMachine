//written by André Betz 
//http://www.andrebetz.de

public class FiFo {
	Element First;
	Element Last;
	
	FiFo()
	{
		Clear();
	}
	void Clear(){
		First = null;
		Last = null;
	}
	void Add(Object obj){
		if(First==null){
			First = new Element(obj);
			Last = First;
		}else{
			Last.set_Next(new Element(obj));
			Last = Last.get_Next();
		}
	}
	boolean IsEmpty(){
		if(First==null){
			return true;
		}else{
			return false;			
		}
	}
	Object GetOut(){
		Element Out = First;
		if(First!=null){
			First = First.get_Next();
		}
		if(Out!=null){
			return Out.get_value();
		}
		else{
			return null;
		}
	}
}
