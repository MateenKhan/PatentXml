import java.util.ArrayList;
import java.util.List;


public class Temp {
	public static void main(String[] args) {
		List<String> strList = new ArrayList<String>();
		for(int i=0;i<100;i++)
		strList.add(i+"");
		String str = strList.toString();
		str=str.substring(1,str.length()-1);
		System.out.println(str);
	}
}
