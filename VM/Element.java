//written by André Betz 
//http://www.andrebetz.de

class Element
{
	private Object value;
	private Element Next;
	private Element Last;
	Element(Object a){
		Next = null;
		Last = null;
		value = a;
	}
	public Object get_value(){
		return value;
	}
	public Element get_Next(){
		return Next;
	}
	public void set_Next(Element n){
		Next = n;
		if(n!=null){
			n.set_Last(this);
		}
	}
	public void set_Last(Element n){
		Last = n;
	}
	public Element get_Last(){
		return Last;
	}
}